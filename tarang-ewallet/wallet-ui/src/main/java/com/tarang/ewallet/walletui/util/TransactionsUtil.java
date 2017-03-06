/**
 * 
 */
package com.tarang.ewallet.walletui.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.dto.CustomerReportModel;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.form.RequestMoneyForm;


/**
 * @author vasanthar
 *
 */
public class TransactionsUtil implements AttributeConstants{
	
	public static void convertRequestMoneyFormToDto(RequestMoneyForm form, RequestMoneyDto dto){
		dto.setResponserEmail(form.getEmailId());
		dto.setAmount(Double.valueOf(form.getAmount()));
		dto.setCurrencyId(form.getCurrency());
		dto.setRequesterMsg(form.getRequesterMsg());
		dto.setPhoneCode(form.getPhoneCode());
		dto.setPhoneNumber(form.getPhoneNumber());
		dto.setStatus(WalletTransactionStatus.PENDING);
		dto.setRequestDate(new Date());
	}
	
	public static List<CustomerReportModel> convertWalletTransactionToCustomerReportModel(List<WalletTransaction> txns, HttpServletRequest request, CommonService commonService) throws WalletException{
		List<CustomerReportModel> crmList = new ArrayList<CustomerReportModel>();
		/*Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		for(WalletTransaction txn : txns){
			CustomerReportModel customerReportModel = new CustomerReportModel();
			customerReportModel.setTransactionId(txn.getId());
			customerReportModel.setStatusName(MasterDataUtil.getSimpleDataMap(request
				      .getSession().getServletContext(), (Long) request.getSession()
				      .getAttribute(LANGUAGE_ID),
				      MasterDataConstants.RECEIVE_MONEY_STATUS).get(txn.getStatus()));
			customerReportModel.setTransactionType(MasterDataUtil.getAllOperationTypes(request.getSession().getServletContext(), (Long) request.getSession()
				      .getAttribute(LANGUAGE_ID)).get(txn.getTypeOfTransaction()));
			customerReportModel.setDateToString(DateConvertion.dateToString(txn.getCreationDate()));
			if(txn.getPayee()!= null && authId.longValue() == txn.getPayee().longValue()){
				customerReportModel.setBalanceToString(UIUtil.getConvertedAmountInString(txn.getPayeeBalance()));
				Authentication auth = commonService.getAuthentication(txn.getPayer() != null ? txn.getPayer() : txn.getPayee());
				customerReportModel.setReceiverEmail(auth.getEmailId());
				customerReportModel.setAmountToString(UIUtil.getConvertedAmountInString(txn.getPayerAmount()));
			}
			else{
				Authentication ath = commonService.getAuthentication(txn.getPayee() != null ? txn.getPayee() : txn.getPayer());
				customerReportModel.setReceiverEmail(ath.getEmailId());
				customerReportModel.setBalanceToString(UIUtil.getConvertedAmountInString(txn.getPayerBalance()));
				customerReportModel.setAmountToString(UIUtil.getConvertedAmountInString(txn.getPayerAmount()));
			}
			crmList.add(customerReportModel);
		}*/
		return crmList;
	}

}
