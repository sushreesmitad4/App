/**
 * 
 */
package com.tarang.ewallet.walletui.util;


import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.walletui.form.DisputeForm;


/**
 * @author vasanthar
 *
 */
public class DisputeUtil {
	
	public static void convertdisputeDtoToForm(DisputeDto disputeDto, DisputeForm disputeForm){
		disputeForm.setApprovedAmount(UIUtil.getConvertedAmountInString(disputeDto.getApprovedamount()));
		disputeForm.setApprovedCurrency(disputeDto.getApprovedcurrency());
		disputeForm.setDisputeLogDate(DateConvertion.dateToString(disputeDto.getDisputelogdate()));
		
		/*Merchant and admin update page*/
		disputeForm.setStatus(disputeDto.getStatus());
		disputeForm.setDistype(disputeDto.getType());
		disputeForm.setPayerEmailId(disputeDto.getPayeremailid());
		disputeForm.setPayeeEmailId(disputeDto.getPayeeemailid());
		disputeForm.setPayerAmount(UIUtil.getConvertedAmountInString(disputeDto.getPayeramount()));
		disputeForm.setPayerCurrency(disputeDto.getPayercurrency());
		
		disputeForm.setDisputeType(disputeDto.getDisputetype());
		disputeForm.setDisputeUpdationDate(DateConvertion.dateToString(disputeDto.getUpdationdate()));
		disputeForm.setRequestAmount(UIUtil.getConvertedAmountInString(disputeDto.getRequestamount()));
		disputeForm.setRequestCurrency(disputeDto.getRequestcurrency());
		disputeForm.setTransactionAmount(UIUtil.getConvertedAmountInString(disputeDto.getTransactionamount()));
		disputeForm.setTransactionCurrency(disputeDto.getTransactioncurrency());
		disputeForm.setTransactionDate(DateConvertion.dateToString(disputeDto.getTransactiondate()));
		disputeForm.setTransactionId(disputeDto.getTransactionId());
		
		disputeForm.setDisputeStatus(disputeDto.getStatusname());
		disputeForm.setDisputeId(disputeDto.getId());
		disputeForm.setDtoMessages(disputeDto.getDtomessages());
	}
	
	public static void convertWalletTransactioToDisputeForm(WalletTransaction walletTransaction, DisputeForm disputeForm, HttpServletRequest request){
		disputeForm.setTransactionId(walletTransaction.getId());
		disputeForm.setTransactionDate(DateConvertion.dateToString(walletTransaction.getCreationDate()));
		disputeForm.setTransactionAmount(UIUtil.getConvertedAmountInString(walletTransaction.getPayerAmount()));
		disputeForm.setTransactionCurrency(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), walletTransaction.getPayerCurrency()));
		disputeForm.setRequestedCurrencyId(walletTransaction.getPayerCurrency());
		disputeForm.setDispRequestedCurrency(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), walletTransaction.getPayerCurrency()));
		
	}

}
