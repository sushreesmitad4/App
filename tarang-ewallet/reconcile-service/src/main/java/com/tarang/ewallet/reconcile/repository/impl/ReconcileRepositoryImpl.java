package com.tarang.ewallet.reconcile.repository.impl;
/**
 * @author  : kedarnathd
 * @date    : March 07, 2013
 * @time    : 9:58:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.tarang.ewallet.dto.ReconcileDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.Reconcile;
import com.tarang.ewallet.reconcile.dao.ReconcileDao;
import com.tarang.ewallet.reconcile.repository.ReconcileRepository;
import com.tarang.ewallet.reconcile.util.ReconcileConstants;
import com.tarang.ewallet.reconcile.util.ReconcileUtil;
import com.tarang.ewallet.reconcile.util.ReconciliationFileUtility;
import com.tarang.ewallet.transaction.repository.PGRepository;
import com.tarang.ewallet.util.service.UtilService;





public class ReconcileRepositoryImpl implements ReconcileRepository{
	
	private static final Logger LOGGER = Logger.getLogger(ReconcileRepositoryImpl.class);
	
	private HibernateTransactionManager transactionManager;
	
	private ReconcileDao reconcileDao;
	
	private PGRepository pGRepository;
	
	private UtilService utilService;
	
	public ReconcileRepositoryImpl(HibernateTransactionManager transactionManager, 
			ReconcileDao reconcileDao, PGRepository pGRepository, UtilService utilService) {
		this.transactionManager = transactionManager;
		this.reconcileDao = reconcileDao;
		this.pGRepository = pGRepository;
		this.utilService = utilService;
	}

	@Override
	public void startReconcile() {
		LOGGER.info("Reconcile process started at " + new Date());
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();         

		List<String> filePaths = ReconcileUtil.getReadableReconcileFileNames(utilService.getSourceFolderLocation(), utilService.getPgFileExtention());
		try{
			if(filePaths != null && filePaths.size() > 0){
				TransactionStatus txstatus = transactionManager.getTransaction(transactionDefinition);
				for(String fname: filePaths){
					ReconciliationFileUtility rfUtililty = new ReconciliationFileUtility(fname);
					List<ReconcileDto> pgDataList = rfUtililty.getReconcileList();
					List<Integer> missMatchRecords = new ArrayList<Integer>();
					List<Integer> nullRecords = new ArrayList<Integer>();
					for(int i = 0; i < pgDataList.size(); i++){
						ReconcileDto dto = pgDataList.get(i);
						if(dto != null){
							reconcileRecord(dto);
							if(dto.getProcessStatus() != null){
								missMatchRecords.add(i+2);
							}
						} else {
							nullRecords.add(i+2);
						}
					}
					if(pgDataList == null || pgDataList.size() == 0){
						//move file to currupted location
						ReconcileUtil.moveFile(rfUtililty.getFileName(), utilService.getSourceFolderLocation(), utilService.getCurruptedFileLocation());
					} else if(missMatchRecords.size() == 0 && nullRecords.size() == 0){
						//move file to success location
						ReconcileUtil.moveFile(rfUtililty.getFileName(), utilService.getSourceFolderLocation(), utilService.getSuccessFileLocation());
					} else {
						//move file to failure location
						if(missMatchRecords.size() != 0){
							//add line numbers in new file
							ReconcileUtil.createFiles(ReconcileUtil.constructFileName(rfUtililty.getFileName(), utilService.getPgFileExtention()), 
									utilService.getMissMathFileLocation(), missMatchRecords);
						}
						if(nullRecords.size() != 0){
							//add line numbers in new file
							ReconcileUtil.createFiles(ReconcileUtil.constructFileName(rfUtililty.getFileName(), utilService.getPgFileExtention()), 
									utilService.getNullRecordsFileLocation(), nullRecords);
						}
						ReconcileUtil.moveFile(rfUtililty.getFileName(), utilService.getSourceFolderLocation(), utilService.getFailureFileLocation());
					}
				}
				transactionManager.commit(txstatus);
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	@Override
	public List<ReconcileDto> getReconcileRecords(Date fromDate, Date toDate, Long status) throws WalletException {
		List<Reconcile> reconcileList =  reconcileDao.getReconcileRecords(fromDate, toDate, status);
		return ReconcileUtil.covertReconcileListToReconcileDtoList(reconcileList);
	}
	
	@Override
	public Reconcile createReconcile(Reconcile reconcile) throws WalletException {
		return reconcileDao.createReconcile(reconcile);
	}

	@Override
	public ReconcileDto getReconcileRecordById(Long id) throws WalletException {
		Reconcile reconcile =  reconcileDao.getReconcileRecordById(id);
		return ReconcileUtil.convetReconileToReconcileDto(reconcile);
	}


	@Override
	public Reconcile updateReconcile(Long reconcileId, Double finalAmount, Long status) throws WalletException {
		return reconcileDao.updateReconcile(reconcileId, finalAmount, status);
	}
	
	private void reconcileRecord(ReconcileDto dto) throws WalletException{

		History existingHistory = null;
		Reconcile reconcile = null;
		try{
			existingHistory = pGRepository.getHistoryBypgTxnId(dto.getPgTxnId());
			if( existingHistory != null){
				if(dto.getTxnAmount().equals(existingHistory.getAmount())){
					History updateHistory = pGRepository.getHistory(existingHistory.getId());
					updateHistory.setReconcileDate(new Date());
					updateHistory.setReconciledStatus(Boolean.TRUE);
					updateHistory = pGRepository.updateHistory(updateHistory);
				} else{
					dto.setProcessStatus(ReconcileConstants.MISS_MATCHED_WITH_HISTORY);
					reconcile = ReconcileUtil.preparedReconcileModelForCreate(dto);
					reconcile.setActualAmount(existingHistory.getAmount());
				}
			} else {
				dto.setProcessStatus(ReconcileConstants.NOT_EXISTS_IN_HISTORY);
				reconcile = ReconcileUtil.preparedReconcileModelForCreate(dto);
				reconcile.setActualAmount(0.0);
			}
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
		if(reconcile != null){
			try{
				//create dispute records
				reconcileDao.createReconcile(reconcile);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
				if(ex.getMessage().contains("insert into Reconcile")){
					LOGGER.error(ReconcileConstants.ERROR_PGTXNID_EXIST, ex);
				} else if(ex instanceof org.hibernate.exception.ConstraintViolationException){
					LOGGER.error(ReconcileConstants.ERROR_PGTXNID_EXIST, ex);
				} else{
					throw new WalletException(ReconcileConstants.CREATE_RECONCILE_EXCEPTION, ex);
				}
			}
		}
	}
	
}
