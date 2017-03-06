/**
 * 
 */
package com.tarang.ewallet.reconcile.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tarang.ewallet.dto.ReconcileDto;
import com.tarang.ewallet.exception.WalletException;


/**
 * @author  : prasadj
 * @date    : Mar 11, 2013
 * @time    : 2:37:49 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class ReconciliationFileUtility {

	private static final Logger LOGGER = Logger.getLogger(ReconciliationFileUtility.class);

	private static final int TXN_ID_INDEX = 9;
	
	private static final int TXN_AMOUNT_INDEX = 20;
	
	private static final int PURCHANGE_DATE_INDEX = 17;
	
	private static final int PURCHANGE_TIME_INDEX = 18;
	
	private static final int CURRENCY_INDEX = 21;
	
	private String absolutePath;

	private List<ReconcileDto> reconcileList = null;
	
	public ReconciliationFileUtility(String absolutePath){
		this.absolutePath = absolutePath;
		reconcileList = loadReconcileFileData(absolutePath);
	}
	
	public List<ReconcileDto> getReconcileList(){
		return reconcileList;
	}
	
	public String getFileName() {
		File file = new File(absolutePath);
		//split file path and send filename only
		return file.getName();
	}

	public String getAbsolutePasth(){
		return absolutePath;
	}
	private List<ReconcileDto> loadReconcileFileData(String fileName){
		List<ReconcileDto> pgDataList = new ArrayList<ReconcileDto>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));
			String line = null;
			int lineNumber = 0;
			while (true) {
				line = reader.readLine();
				if (line == null){
					break;
				}
				lineNumber++;
				if(lineNumber == 1){
					continue;
				}
				pgDataList.add(getObject(line));
				
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		
		return pgDataList;
	}

	private ReconcileDto getObject(String line) {
		String splitData[] = line.split(",");
		ReconcileDto dto = null;
		if(splitData.length >= ReconcileConstants.FIXED_COLUMN){
			try{
				Long txnId = null;
				Double txnAmount = null;
				if(ReconcileUtil.numberValidator(splitData[TXN_ID_INDEX])){
					txnId = Long.parseLong(splitData[TXN_ID_INDEX]);
				} else{
					throw new WalletException("txn.id.invalid");
				}
				if(!ReconcileUtil.currencyValidator(splitData[TXN_AMOUNT_INDEX])){
					txnAmount = Double.parseDouble(splitData[TXN_AMOUNT_INDEX]);
				} else{
					throw new WalletException("txn.amount.invalid");
				}
				dto = new ReconcileDto();
				dto.setScheme(splitData[0]);
				dto.setPurchaseDate(splitData[PURCHANGE_DATE_INDEX]);
				dto.setPurchaseTime(splitData[PURCHANGE_TIME_INDEX]);
				dto.setTxnCurrency(splitData[CURRENCY_INDEX]);
				dto.setPgTxnId(txnId);
				dto.setTxnAmount(txnAmount);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		}
		return dto;
	}

}
