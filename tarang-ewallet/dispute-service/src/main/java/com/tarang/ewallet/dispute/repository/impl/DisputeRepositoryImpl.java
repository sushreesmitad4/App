/**
 * 
 */
package com.tarang.ewallet.dispute.repository.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.util.CommonUtil;
import com.tarang.ewallet.dispute.dao.DisputeDao;
import com.tarang.ewallet.dispute.repository.DisputeRepository;
import com.tarang.ewallet.dispute.util.DisputeStatusConstants;
import com.tarang.ewallet.dispute.util.DisputeUtil;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Dispute;
import com.tarang.ewallet.model.DisputeMessage;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
public class DisputeRepositoryImpl implements DisputeRepository, GlobalLitterals{
	
	private static final Logger LOGGER = Logger.getLogger(DisputeRepositoryImpl.class);
	
	private DisputeDao disputeDao;

	private TransactionWalletService transactionWalletService;
	
	private EmailService emailService;
	
	private EmailTemplateService emailTemplateService;
	
	public DisputeRepositoryImpl(com.tarang.ewallet.dispute.dao.DisputeDao disputeDao,
			TransactionWalletService transactionWalletService, 
			EmailTemplateService emailTemplateService, EmailService emailService) {
		this.disputeDao = disputeDao;
		this.transactionWalletService = transactionWalletService;
		this.emailTemplateService = emailTemplateService;
		this.emailService = emailService;
		
	}
	
	@Override
	public DisputeDto createDispute(DisputeDto disputeDto) throws WalletException {
		Dispute dispute = new Dispute();
		dispute.setTransactionId(disputeDto.getTransactionId());
		dispute.setType(disputeDto.getType());
		dispute.setRequestAmount(disputeDto.getRequestAmount());
		dispute.setRequestCurrency(disputeDto.getRequestCurrency());
		dispute.setStatus(DisputeStatusConstants.PENDING);
		dispute.setCreationDate(new Date());
		dispute.setUpdationDate(new Date());
		dispute.setApprovedAmount(disputeDto.getRequestAmount());
		dispute.setApprovedCurrency(disputeDto.getRequestCurrency());
		
		Set<DisputeMessage> messages = new HashSet<DisputeMessage>();
		DisputeMessage dMessage = new DisputeMessage();
		dMessage.setMessage(disputeDto.getMessage());
		dMessage.setCreator(Long.valueOf(disputeDto.getCreator().toString()));
		dMessage.setDispute(dispute);
		dMessage.setCreationDate(new Date());
		messages.add(dMessage);
		dispute.setMessages(messages);
		sendEmailNotificationForRaiseDispute(disputeDto, EmailServiceConstants.CREATE_DISPUTE_MESSAGE_FOR_MERCHANT);
		return getDisputeById(disputeDao.createDispute(dispute).getId());
	}
	
	@Override
	public DisputeDto updateDispute(DisputeDto disputeDto) throws WalletException {
		Dispute dispute = disputeDao.getDisputeById(disputeDto.getId());
		DisputeMessage dMessage = new DisputeMessage();
		dMessage.setDispute(dispute);
		dMessage.setCreator(disputeDto.getCreator().longValue());
		dMessage.setCreationDate(new Date());
		dMessage.setMessage(disputeDto.getMessage());
		//to set updation date only when status is changed
		Long previousStatus = dispute.getStatus();
		if(!previousStatus.equals(disputeDto.getStatus())){
			dispute.setUpdationDate(new Date());
			dispute.setStatus(disputeDto.getStatus());
			if(disputeDto.getApprovedAmount() != null && (!disputeDto.getStatus().equals(DisputeStatusConstants.ADMIN_REJECTED) && 
					!disputeDto.getStatus().equals(DisputeStatusConstants.MERCHANT_REJECTED))){
				dispute.setApprovedAmount(disputeDto.getApprovedAmount());
			}
			
			if(disputeDto.getStatus().equals(DisputeStatusConstants.FORCE_WITHDRAWAL) || disputeDto.getStatus().equals(DisputeStatusConstants.APPROVED)){
				transactionWalletService.reversalTransaction(dispute.getTransactionId(),dispute.getType(), disputeDto.getApprovedAmount());
			}
		}
		
		disputeDao.addMessage(dMessage);
		if(!previousStatus.equals(disputeDto.getStatus())
				&& disputeDto.getStatus().equals(DisputeStatusConstants.FORCE_WITHDRAWAL)){ 
					sendEmailNotificationToMerchantForUpdateDispute(disputeDto, EmailServiceConstants.SYSTEM_ACTION_MESSAGE_FOR_MERCHANT);
					sendEmailNotificationToCustomerForUpdateDispute(disputeDto, EmailServiceConstants.SYSTEM_ACTION_MESSAGE_FOR_CUSTOMER);
		}
		else{
			sendEmailNotificationToMerchantForUpdateDispute(disputeDto, EmailServiceConstants.UPDATE_DISPUTE_MESSAGE_FOR_MERCHANT);
			sendEmailNotificationToCustomerForUpdateDispute(disputeDto, EmailServiceConstants.UPDATE_DISPUTE_MESSAGE_FOR_CUSTOMER);
		}
		
		return getDisputeById(disputeDto.getId());
	}

	@Override
	public DisputeDto getDisputeById(Long id) throws WalletException {
		Dispute dispute = disputeDao.getDisputeById(id);
		DisputeDto dto = null;
		if(dispute != null){
			dto = new DisputeDto();
			DisputeUtil.convertDisputeToDisputeDto(dispute, dto);
		}
		return dto;
	}

	@Override
	public DisputeDto getDisputeById(Long disputeId, Long languageId) throws WalletException {
		Dispute dispute = disputeDao.getDisputeById(disputeId);
		DisputeDto updateDto = null;
		if(dispute != null){
			updateDto = new DisputeDto();
			DisputeUtil.convertDisputeToDisputeDto(dispute, updateDto);
			List<DisputeDto> newDtoList = getAdmineOrMerchantUpdateDispute(languageId, dispute.getTransactionId());
			if(newDtoList != null && newDtoList.size() > 0){
				DisputeUtil.papulateDisputeDtoForUpdate(updateDto, newDtoList.get(0));
			}
		}
		return updateDto;
	}

	@Override
	public List<DisputeGridDto> getDisputesForCustomer(Integer limit, Long languageId, Date fromDate, Date toDate, 
			Long payeeId, Long payerId, Long disputeType) throws WalletException {	
		return disputeDao.getDisputesForCustomer(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getDisputesForMerchant(Integer limit, Long languageId, Date fromDate, Date toDate, Long payerId, 
			Long payeeId, Long disputeType) throws WalletException {
		return disputeDao.getDisputesForMerchant(limit, languageId, fromDate, toDate, payerId, payeeId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getDisputesForAdmin(Integer limit, Long languageId, Date fromDate,
			Date toDate, Long payeeId, Long payerId, Long disputeType) throws WalletException {
		return disputeDao.getDisputesForAdmin(limit, languageId, fromDate, toDate, payeeId, payerId, disputeType);
	}

	@Override
	public List<DisputeGridDto> getCustomerTxnsForRaisedispute(Integer limit, Date fromDate, Date toDate, 
			Long payeeId, Long payerId, Long typeOfTransaction) throws WalletException {
		return disputeDao.getCustomerTxnsForRaisedispute(limit, fromDate, toDate, payeeId, payerId, typeOfTransaction);
	}

	@Override
	public List<DisputeDto> getCustomerRaiseOrUpdateDispute(Long languageId, Long txnId) throws WalletException {
		return disputeDao.getCustomerRaiseOrUpdateDispute(languageId, txnId);
	}

	@Override
	public List<DisputeDto> getAdmineOrMerchantUpdateDispute(Long languageId, Long txnId) throws WalletException {
		return disputeDao.getAdmineOrMerchantUpdateDispute(languageId, txnId);
	}

	@Override
	public List<Long> getMerchantToPayStatusDisputeIds(Long status, Date date)
			throws WalletException {
		return disputeDao.getMerchantToPayStatusDisputeIds(status, date);
	}
	
	@Override
	public Boolean isDisputeExistForTxnId(Long txnid)throws WalletException {
		return disputeDao.isDisputeExistForTxnId(txnid);
	}
	
	/**
	 * This method will send confirmation mail to merchant for create dispute by customer
	 * @param disputeDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationForRaiseDispute(DisputeDto disputeDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put("payerName", disputeDto.getPayerName()!= null ? disputeDto.getPayerName() : EMPTY_STRING);
		dvalues.put("payeeName", disputeDto.getPayeeName()!= null ? disputeDto.getPayeeName() : EMPTY_STRING);
		dvalues.put("requestAmount", disputeDto.getRequestAmount()!= null? CommonUtil.getConvertedAmountInString(disputeDto.getRequestAmount()) : "" );
		dvalues.put("requestCurrency", disputeDto.getCurrencyCode() != null? disputeDto.getCurrencyCode() : EMPTY_STRING);
		dvalues.put("disputeType", disputeDto.getDisputetype() != null? disputeDto.getDisputetype() : EMPTY_STRING);
		dvalues.put("message", disputeDto.getMessage());
		sendEmailNotificationDispute(dvalues, disputeDto.getLanguageId(), disputeDto.getPayeeemailid(), type);
	 }
	
	/**
	 * This method will send confirmation mail to customer for uddate dispute by customer or merchant or admin
	 * @param disputeDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationToCustomerForUpdateDispute(DisputeDto disputeDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put("payerName", disputeDto.getPayerName() != null ? disputeDto.getPayerName() : EMPTY_STRING);
		dvalues.put("payeeName", disputeDto.getPayeeName() != null ? disputeDto.getPayeeName() : EMPTY_STRING);
		dvalues.put("updatedName", disputeDto.getUpdatedby() != null ? disputeDto.getUpdatedby() : EMPTY_STRING);
		dvalues.put("requestAmount", disputeDto.getRequestamount() != null? CommonUtil.getConvertedAmountInString(disputeDto.getRequestamount()) : EMPTY_STRING );
		dvalues.put("requestCurrency", disputeDto.getRequestcurrency() != null? disputeDto.getRequestcurrency() : EMPTY_STRING);
		dvalues.put("disputeType", disputeDto.getDisputetype() != null? disputeDto.getDisputetype() : EMPTY_STRING);
		dvalues.put("message", disputeDto.getMessage());
		sendEmailNotificationDispute(dvalues, disputeDto.getLanguageId(), disputeDto.getPayeremailid(), type);
	 }
	
	/**
	 * This method will send confirmation mail to merchant for uddate dispute by customer or merchant or admin
	 * @param disputeDto
	 * @throws WalletException 
	 */
	private void sendEmailNotificationToMerchantForUpdateDispute(DisputeDto disputeDto, String type) throws WalletException{
		Properties dvalues = new Properties();
		dvalues.put("payerName", disputeDto.getPayerName() != null ? disputeDto.getPayerName() : EMPTY_STRING);
		dvalues.put("payeeName", disputeDto.getPayeeName() != null ? disputeDto.getPayeeName() : EMPTY_STRING);
		dvalues.put("updatedName", disputeDto.getUpdatedby() != null ? disputeDto.getUpdatedby() : EMPTY_STRING);
		dvalues.put("requestAmount", disputeDto.getRequestamount() != null? CommonUtil.getConvertedAmountInString(disputeDto.getRequestamount()) : EMPTY_STRING );
		dvalues.put("requestCurrency", disputeDto.getRequestcurrency() != null? disputeDto.getRequestcurrency() : EMPTY_STRING);
		if(disputeDto.getStatus().equals(DisputeStatusConstants.APPROVED)){
			dvalues.put("approvedAmount", disputeDto.getApprovedAmount() != null? CommonUtil.getConvertedAmountInString(disputeDto.getApprovedAmount()) : EMPTY_STRING );
			dvalues.put("approvedCurrency", disputeDto.getApprovedcurrency() != null? disputeDto.getApprovedcurrency() : EMPTY_STRING);
		}
		else{
			dvalues.put("approvedAmount", EMPTY_STRING );
			dvalues.put("approvedCurrency", EMPTY_STRING);
		}
		dvalues.put("disputeType", disputeDto.getDisputetype() != null? disputeDto.getDisputetype() : EMPTY_STRING);
		dvalues.put("message", disputeDto.getMessage());
		sendEmailNotificationDispute(dvalues, disputeDto.getLanguageId(), disputeDto.getPayeeemailid(), type);
	 }
	
	/**
	 * Send mail notification
	 * @param dvalues
	 * @param languageId
	 * @param email
	 * @param type
	 * @throws WalletException
	 */
	private void sendEmailNotificationDispute(Properties dvalues, Long languageId, String email, String type) throws WalletException{
		try {
			Long langId = languageId != null ? languageId : 1L;
			String emailTo = email;
			String mailMsg = null;
			String mailSubject = null;
			mailMsg = emailTemplateService.getEmailBodyMessage(langId, type, dvalues);
			mailSubject = emailTemplateService.getEmailSubjectMessage(langId, type, dvalues);
			emailService.sendMessage(emailTo, mailMsg, mailSubject);
		}
		catch(WalletException ex){
			LOGGER.error(ex.getMessage());
		}
		catch(Exception e){
			LOGGER.error(EmailServiceConstants.EMAIL_SEND_FAILED, e);
		}
	 }

	@Override
	public Integer getDisputeCountForAccClose(Long authenticationId) {
		return disputeDao.getDisputeCountForAccClose(authenticationId);
	}

	@Override
	public Integer getActiveDisputeForMerchant(Long authenticationId) {
		return disputeDao.getActiveDisputeForMerchant(authenticationId);
	}
	
}
