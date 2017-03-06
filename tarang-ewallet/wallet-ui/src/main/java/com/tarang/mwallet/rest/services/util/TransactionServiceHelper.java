package com.tarang.mwallet.rest.services.util;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.walletui.controller.AttributeConstants;
import com.tarang.ewallet.walletui.controller.constants.SendMoney;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.validator.common.Common;

/**
 * @author hari.santosh
 *
 */
public class TransactionServiceHelper implements AttributeConstants{
	
	 /**
	 * @param request
	 * @param e
	 * @return errorCode
	 */
	public static String papulateErrorMessage(Exception e) {
		
		 String errorCode = Common.UNABLE_TO_PROCESS_MSG;
			if(e.getMessage().contains(SendMoney.C_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorCode = ServerProcessorStatus.C_EMAIL_NOT_REGISTERED_ERR_MSG.getValue();
			} else if(e.getMessage().contains(SendMoney.M_EMAIL_NOT_REGISTERED_ERR_MSG)){
				errorCode = ServerProcessorStatus.M_EMAIL_NOT_REGISTERED_ERR_MSG.getValue();
			} else if(e.getMessage().contains(SendMoney.USER_NOT_HAVE_BALANCE_ERR_MSG)){
				errorCode = ServerProcessorStatus.USER_NOT_HAVE_BALANCE_ERR_MSG.getValue();
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_DELETED)){
				errorCode = ServerProcessorStatus.USER_DELETED.getValue();
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_LOCKED)){
				errorCode = ServerProcessorStatus.USER_LOCKED.getValue();
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_REJECTED)){
				errorCode = ServerProcessorStatus.USER_REJECTED.getValue();
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_INACTIVE)){
				errorCode = ServerProcessorStatus.USER_INACTIVE.getValue();
			} else if(e.getMessage().contains(WalletTransactionConstants.USER_NOT_APPROVED)){
				errorCode = ServerProcessorStatus.USER_NOT_APPROVED.getValue();
			} else if(e.getMessage().contains(CommonConstrain.USER_NOT_REGISTER_AS_MOBILE_WALLET)){
				errorCode = ServerProcessorStatus.USER_NOT_REGISTER_AS_MOBILE_WALLET.getValue();
			} else if(e.getMessage().contains(CommonConstrain.MPIN_MATCH_FAIL)){
				errorCode = ServerProcessorStatus.MPIN_MATCH_FAIL.getValue();
			} else if(e.getMessage().contains(CommonConstrain.USER_DOES_NOT_EXIST)){
				errorCode = ServerProcessorStatus.USER_DOES_NOT_EXIST.getValue();
			} else{
				errorCode = ServerProcessorStatus.TRANSACTION_FAILED.getValue();
			}
		 
		 return errorCode;
	 }
	
	
	/**
	 * @param request
	 * @param currencyId
	 * @return String
	 */
	public static String getCurrencyCode(HttpServletRequest request, Long currencyId) {
		return MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(currencyId);
	}
	
	/**
	 * Return the code which has received the payment gateway response
	 * @param pgResponseCode
	 * @return
	 */
	public static String papulatePgResponseCode(String pgResponseCode){
		String defaultMessage = ServerProcessorStatus.ERROR_MSG_UNKNOWN_PG_RES.getValue();
		if(null == pgResponseCode){
			return defaultMessage;
		} else{
			if(!pgResponseCode.equals(HttpServiceConstants.RESPONSE_DECISION_SUCCESS)){
				String pgCode = HttpServiceConstants.PG_ERROR_PRIFIX + pgResponseCode;
				return pgCode;
			}
			return ServerProcessorStatus.MONEY_RELOADED_SUCCESSFULLY.getValue();
		}
	}
}
