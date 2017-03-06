/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.transaction.business.SendMoneyService;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.walletui.controller.constants.ReceiveMoneyConstants;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.UserValidator;


/**
 * @author  : prasadj
 * @date    : Jan 28, 2013
 * @time    : 7:40:56 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@Controller
@RequestMapping("/receivemoney")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ReceiveMoneyController implements ReceiveMoneyConstants, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(ReceiveMoneyController.class);
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SendMoneyService sendMoneyService;
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String receiveMoneyViewPage( Map model, Locale locale, HttpServletRequest request) {
		
		LOGGER.debug( " Receive Money Page " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String url = WALLET_PATH_PREFIX + "receivemoney/viewrecords";
		model.put("urlCustomerReceiveViewList", url);			
		return getView(session, RECEIVE_MONEY_VIEW);
		
	}
	
	@RequestMapping(value = "/viewrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<SendMoneyDto> receiveMoneyRecords(
			@RequestParam("_search") Boolean search,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "sidx", required = false) String sidx,
			@RequestParam(value = "sord", required = false) String sord,
			Map model, Locale locale, HttpSession session,
			HttpServletRequest request) {

		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord);
		
		List<SendMoneyDto> sendMoneyDtos = new ArrayList<SendMoneyDto>();
		try {
			sendMoneyDtos = sendMoneyService.getReceiveMoneyList((Long)session.getAttribute(AUTHENTICATION_ID));
			for(SendMoneyDto dto:sendMoneyDtos){
				dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), dto.getRequestedCurrency()));
			    dto.setAmountString(UIUtil.getConvertedAmountInString(dto.getRequestedAmount()));			    
			    dto.setDateToString(DateConvertion.dateToString(dto.getRequestDate()));
			    dto.setTransactionStatusName(MasterDataUtil.getSimpleDataMap(
			    		request.getSession().getServletContext(), 
			    		(Long) request.getSession().getAttribute(LANGUAGE_ID),
			    		MasterDataConstants.RECEIVE_MONEY_STATUS).get(dto.getTransactionStatus()));
			   }
		} catch (Exception e) {
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("TransactionRecords", e);
		}
		
		JqgridResponse<SendMoneyDto> response = new JqgridResponse<SendMoneyDto>();
		response.setRows(sendMoneyDtos);
		int ps = DEFAULT_PAGE_SIZE;
		int n = sendMoneyDtos.size()/ps;
		if( sendMoneyDtos.size()/ps*ps != sendMoneyDtos.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);
		return response;
	}	
	
	/*@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public String acceptedReceivedMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		LOGGER.debug(" acceptedReceivedMoney :: Entering controller ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		//To whom you have received money, that persons mail id
		String payeeMail = request.getParameter("payeeMail");
		Long transactionId = Long.valueOf(request.getParameter("txnId"));
		Long currency = Long.valueOf(request.getParameter("currencyId"));
		String userType = request.getParameter(USER_TYPE);
			try{
				SendMoneyDto sendMoneyDto = new SendMoneyDto();
				sendMoneyDto.setRequestedCurrency(currency);
				sendMoneyDto.setEmailId(payeeMail);
				sendMoneyDto.setTransactionId(transactionId);
				papulateDefultSendMoneyDtoInfo(request, sendMoneyDto, userType);
				sendMoneyService.acceptReceiveMoney(sendMoneyDto);
				return UIUtil.redirectPath(RECEIVE_MONEY_SUCCESS_PATH);
			}catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(e.getMessage());
				if(e.getMessage().contains(WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE)){
					errorMessage = WalletTransactionConstants.ERROR_USER_WALLET_NOT_ENOUGH_BALANCE;
				}
				else if(e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)){
					errorMessage = WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT;
				}else{
					errorMessage = "merchant.console.general.error.msg";
				}
				model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				String url = WALLET_PATH_PREFIX + RECEIVE_MONEY_SUCCESS_PATH;
				model.put("urlCustomerReceiveViewList", url);			
				viewPage = RECEIVE_MONEY_VIEW;
			}	
		return getView(session, viewPage);
	 }
	
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public String rejectedReceivedMoney(Map model, HttpServletRequest request, Locale locale,
			@Valid RequestMoneyForm requestMoneyForm, BindingResult result) {
		LOGGER.debug(" receiveMoney :: SUCCESS ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		//To whom you have received money, that persons mail id
		String payeeMail = request.getParameter("payeeMail");
		Long transactionId = Long.valueOf(request.getParameter("txnId"));
		Long currency = Long.valueOf(request.getParameter("currencyId"));
		String userType = request.getParameter(USER_TYPE);
		try{
			SendMoneyDto sendMoneyDto = new SendMoneyDto();
			sendMoneyDto.setRequestedCurrency(currency);
			sendMoneyDto.setEmailId(payeeMail);
			sendMoneyDto.setTransactionId(transactionId);
			papulateDefultSendMoneyDtoInfo(request, sendMoneyDto ,userType);
			sendMoneyService.rejectReceiveMoney(sendMoneyDto);
			return UIUtil.redirectPath(RECEIVE_MONEY_SUCCESS_PATH);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			errorMessage = "merchant.console.general.error.msg";
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			String url = WALLET_PATH_PREFIX + RECEIVE_MONEY_SUCCESS_PATH;
			model.put("urlCustomerReceiveViewList", url);			
			viewPage = RECEIVE_MONEY_VIEW;
		}
		return getView(session, viewPage);
	 }
	
	private void papulateDefultSendMoneyDtoInfo(HttpServletRequest request, SendMoneyDto dto, String userType) throws WalletException{
		
		dto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		dto.setCurrencyCode(MasterDataUtil.getCurrencyNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(dto.getRequestedCurrency()));
		String personName = null;
		if(userType != null && userType.equals(AttributeConstants.CUSTOMER_USER_TYPE)){
			personName = customerService.getPersonName(dto.getEmailId(), AttributeConstants.CUSTOMER_USER_TYPE);
		}
		else{
			personName = merchantService.getPersonName(dto.getEmailId(), AttributeConstants.MERCHANT_USER_TYPE);
		}
		
		dto.setPayeeName(personName);
		try{
			dto.setPayerName((String)request.getSession().getAttribute(NAME));
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
	}*/
	
	private String getView(HttpSession session, String view){
		String uview = view;
		String uType = (String)session.getAttribute(USER_TYPE);
		
		if(CUSTOMER_USER_TYPE.equals(uType)){
			uview = CUSTOMER_VIEW + view;
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			uview = MERCHANT_VIEW + view;
		}
		return uview;
	}

}
