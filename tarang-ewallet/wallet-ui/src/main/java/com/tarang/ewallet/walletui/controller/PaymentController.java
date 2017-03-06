package com.tarang.ewallet.walletui.controller;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.PaymentDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.masterdata.util.PaymentMessageCode;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.MerchantPayInfo;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.transaction.business.TransactionWalletService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.FileService;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.form.LoginUserForm;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.PaymentConstants;
import com.tarang.ewallet.walletui.util.UIUtil;


/**
 * @Author : kedarnathd
 * @Date : March 05 2013
 * @Time : 10:09:24 AM
 * @Version : 1.0
 * @Comments: To handler payment request and responce for wallet api
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/payment")
public class PaymentController implements PaymentConstants, AttributeConstants {

	private static final Logger LOGGER = Logger.getLogger(PaymentController.class);
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private TransactionWalletService transactionWalletService;
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String loadPaymentPage(@Valid LoginUserForm loginUserForm, Map model, Locale locale, HttpServletRequest request) {
		
		Long language = request.getSession().getAttribute(LANGUAGE_ID) != null ? (Long) request.getSession().getAttribute(LANGUAGE_ID) :
			CommonConstrain.DEFAULT_LANGUAGE;
		PaymentDto paymentDto = (PaymentDto) request.getSession().getAttribute(PAYMENT_DETAILS);
		paymentDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				language, TypeOfRequest.WEB.getValue()));
		String back = request.getParameter("backtomerchant");
		if(back != null && "true".equals(back)){
			paymentDto.setErrorCode(PaymentMessageCode.USER_CANCEL_ACTION);
			paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.USER_CANCEL_ACTION));
			return navigateMerchantPage(model, request, PAYMENT_FAILED);
		}
		LOGGER.debug(" load payment login page ");
		try{
			Authentication custAuth = null;
			custAuth = loginService.login(loginUserForm.getEmail(), loginUserForm.getPassword(), GlobalLitterals.CUSTOMER_USER_TYPE,
					MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
							language, TypeOfRequest.WEB.getValue()));
			
			if(null == custAuth){
				model.put(ERROR_MESSAGE, context.getMessage(PAYMENT_AUTHENTICATION_FAIL_ERROR_MSG, null, locale));
			} else{
				paymentDto.setCustomerAuthId(custAuth.getId());
				if (UserStatusConstants.PENDING.equals(custAuth.getStatus())){
					paymentDto.setErrorCode(PaymentMessageCode.CUSTOMER_ACCOUNT_PENDIND_STATE);
					paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.CUSTOMER_ACCOUNT_PENDIND_STATE));
					return navigateMerchantPage(model, request, PAYMENT_FAILED);
				}
				
				UserWallet userWallet = commonService.getUserWallet(paymentDto.getCustomerAuthId(), paymentDto.getCurrencyId());
				
				if(userWallet != null){
					if (UserStatusConstants.LOCKED.equals(custAuth.getStatus())){
							paymentDto.setErrorCode(PaymentMessageCode.CUSTOMER_LOCKED);
							paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.CUSTOMER_LOCKED));
							return navigateMerchantPage(model, request, PAYMENT_FAILED);
					} else if(userWallet.getAmount() < paymentDto.getAmount() ){
						paymentDto.setErrorCode(PaymentMessageCode.INSUFFICIANT_BALANCE);
						paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.INSUFFICIANT_BALANCE));
						return navigateMerchantPage(model, request, PAYMENT_FAILED);
					} else {
						try{
							paymentDto.setIpAddress(UIUtil.getClientIpAddr(request));
							paymentDto = transactionWalletService.customerPaymentFromMerchant(paymentDto);
							if(paymentDto.getTxnId() == null){
								paymentDto.setErrorCode(PaymentMessageCode.TXN_FAILED);
								paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.TXN_FAILED));
								return navigateMerchantPage(model, request, PAYMENT_FAILED);
							} else {
								paymentDto.setSuccessCode(PaymentMessageCode.PAYMENT_SUCCESS);
								paymentDto.setSuccessMessage(context.getMessage(PaymentMessageCode.PAYMENT_SUCCESS, null, locale));
								paymentDto.setExpCheckOut(loginUserForm.getExpCheckOut());
								if(loginUserForm.getExpCheckOut()){
									paymentDto.setExpCheckOut(loginUserForm.getExpCheckOut());
									Address address = customerService.getCustomerAddress(customerService.getCustomerId(loginUserForm.getEmail(), "C"));
									paymentDto.setAddressOne(address.getAddressOne());
									paymentDto.setAddressTwo(address.getAddressTwo());
									paymentDto.setCity(address.getCity());
									
									paymentDto.setCountry( MasterDataUtil.getCountryName(request,
											MasterDataUtil.MD_COUNTRIES, address.getCountry()));
									
									paymentDto.setRegion(MasterDataUtil.getRegions(
											request.getSession().getServletContext(), 
											(Long) request.getSession(false).getAttribute(LANGUAGE_ID), 
											address.getCountry()).get(address.getRegion()) );
									paymentDto.setZipcode(address.getZipcode()) ;
								}
								return navigateMerchantPage(model, request, PAYMENT_SUCCESS);
							}
						} catch(Exception ex){
							LOGGER.error(ex.getMessage(), ex);
							paymentDto.setErrorCode(PaymentMessageCode.TXN_FAILED);
							paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.TXN_FAILED));
							return navigateMerchantPage(model, request, PAYMENT_FAILED);
						}
					}
				} else{
					paymentDto.setErrorCode(PaymentMessageCode.INSUFFICIANT_BALANCE);
					paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.INSUFFICIANT_BALANCE));
					return navigateMerchantPage(model, request, PAYMENT_FAILED);	
				}
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			if(e.getMessage() == null){
				model.put(ERROR_MESSAGE, context.getMessage(PAYMENT_AUTHENTICATION_FAIL_ERROR_MSG, null, locale));
			} else if(e.getMessage().contains(CommonConstrain.USER_BLOCK) 
					|| e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)
					|| e.getMessage().contains(CommonConstrain.USER_ACCOUNT_DELETED)
					|| e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)){
				String messageCode = null;
				if(e.getMessage().contains(CommonConstrain.ACCOUNT_REJECTED)){
					messageCode = PaymentMessageCode.CUSTOMER_ACCOUNT_REJ_ADMINISTRATOR;
				}else if(e.getMessage().contains(CommonConstrain.USER_ACCOUNT_DELETED)){
					messageCode = PaymentMessageCode.CUSTOMER_ACCOUNT_DELETED ; 
				}else if(e.getMessage().contains(CommonConstrain.USER_BLOCK)){
					messageCode = PaymentMessageCode.USER_LOCKED_EXCED_FAILURE_LIMIT;
				}else if(e.getMessage().contains(CommonConstrain.ACCOUNT_DEACTIVE)){
					messageCode = PaymentMessageCode.CUSTOMER_ACCOUNT_DEACTIVE;
				}
				paymentDto.setErrorCode(messageCode);
				paymentDto.setErrorMessage(getPaymentErrorMessage(request, messageCode));
				return navigateMerchantPage(model, request, PAYMENT_FAILED);
			} else if(e.getMessage().contains(CommonConstrain.LOGIN_STATUS)){
				model.put(ERROR_MESSAGE, context.getMessage(AUTH_FAIL_ERR_MSG_LOGIN_STATUS, null, locale));
			} else {
				model.put(ERROR_MESSAGE, context.getMessage(PAYMENT_AUTHENTICATION_FAIL_ERROR_MSG, null, locale));
			}
			request.setAttribute(IMAGE_ID ,loginUserForm.getPathImage());
		}
		return PAYMENT_LOGIN_VIEW;
	}
	
	/**
	 * Method to validate payment process from customer to merchant.
	 * 
	 * @param model
	 * @param locale
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String validatePaymentOne(Map model, Locale locale, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sesssionId = session.getId();
		String merSelUrlWithSession = (String)request.getParameter("selfurlsession");
		return "redirect:" + merSelUrlWithSession + "?jsessionid=" + sesssionId;
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String validatePayment(Map model, Locale locale, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sesssionId = session.getId();
		PaymentDto paymentDto = new PaymentDto();
		
		try {
			MerchantPayInfo merchantPayInfo = merchantService.getMerchantPayInfo((String)request.getParameter(REQ_MERCHANT_ID));
			if(merchantPayInfo != null && merchantPayInfo.getMerchantCode()!= null){
				paymentDto.setMerchantCodeFromDB(merchantPayInfo.getMerchantCode());
				paymentDto.setMerchantSuccessUrl(merchantPayInfo.getSuccessUrl());
				paymentDto.setMerchantFailureUrl(merchantPayInfo.getFailureUrl());
			} else{
				return PAYMENT_UNEXPECTED_RES_ONLINEPAYMENT;
			}
		} catch (WalletException e1) {
			LOGGER.error(e1.getMessage(), e1);
		}
		
		paymentDto.setMerchantCode((String)request.getParameter(MERCHANT_CODE));
		if(!sesssionId.equals((String)request.getParameter(REQ_JSESSIONID))){
			paymentDto.setErrorCode(PaymentMessageCode.MERCHANT_HAND_SHAKE_NOT_MATCHES);
			paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.MERCHANT_HAND_SHAKE_NOT_MATCHES));
			model.put(MERCHANT_FAILURE_URL , paymentDto.getMerchantFailureUrl());
			model.put(PAYMENT_DTO , paymentDto);
			return PAYMENT_FAILURE_VIEW;
		}

		Double requestedAmount = ZERO_DOUBLE;
		String stramount = (String)request.getParameter(REQ_AMOUNT);
		Boolean amountError = UIUtil.currencyValidator(stramount);
		if(!amountError){
			requestedAmount = Double.parseDouble(stramount);
		}
		paymentDto.setOrderId((String)request.getParameter(REQ_ORDER_ID));
		paymentDto.setMerchantLoginId((String)request.getParameter(REQ_MERCHANT_ID));
		paymentDto.setAmount(requestedAmount);
		paymentDto.setCurrency((String)request.getParameter(REQ_CURRENCY));
		
		if(amountError ){
			paymentDto.setErrorCode(PaymentMessageCode.INVALID_AMOUNT);
			paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.INVALID_AMOUNT));
			request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
			return navigateMerchantPage(model, request, PAYMENT_FAILED);
		} else if(validateInputFields(paymentDto)){
			paymentDto.setErrorCode(PaymentMessageCode.INVALID_INPUT_DATA);
			paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.INVALID_INPUT_DATA));
			request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
			return navigateMerchantPage(model, request, PAYMENT_FAILED);
		} else {
			try{
				Long requestedCurrency = getCurrencyIdFromCode(request, paymentDto.getCurrency());
				if(requestedCurrency == null){
					paymentDto.setErrorCode(PaymentMessageCode.CURRENCY_NOT_SUPPORT);
					paymentDto.setErrorMessage("currency.not.support");
				}
				if(paymentDto.getErrorCode() == null){
					validateMerchant(request, paymentDto);
				}
				if(paymentDto.getErrorCode() == null){
					paymentDto.setCurrencyId(requestedCurrency);
					request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
					model.put(PAYMENT_DTO , paymentDto);
					LoginUserForm loginUserForm = new LoginUserForm();
					loginUserForm.setMerchantName( merchantService.getLegalName((String)request.getParameter(REQ_MERCHANT_ID)));
					loginUserForm.setAmount((String)request.getParameter(REQ_AMOUNT));
					loginUserForm.setCurrency((String)request.getParameter(REQ_CURRENCY));
					String location = utilService.getUploadImageFileLocation();
					String imageFileName = fileService.getImageFileName(location, paymentDto.getAuthenticationId());
					loginUserForm.setPathImage(imageFileName);
					request.setAttribute(IMAGE_ID , imageFileName);
					model.put("loginUserForm", loginUserForm);
					return PAYMENT_LOGIN_VIEW;
				}
			} catch(Exception e){
				LOGGER.error(e.getMessage() , e);
				paymentDto.setErrorCode(PaymentMessageCode.UNKNOWN_ERROR);
				paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.UNKNOWN_ERROR));
			}
		}
		return navigateMerchantPage(model, request, PAYMENT_FAILED);
	}
	
	private void validateMerchant(HttpServletRequest request, PaymentDto paymentDto){
		try{
			//validate merchant
				Authentication merchantAuth = commonService.getAuthentication(paymentDto.getMerchantLoginId(),GlobalLitterals.MERCHANT_USER_TYPE);
				paymentDto.setAuthenticationId(merchantAuth.getId());

				Boolean isBlocked = merchantAuth.isBlocked()!= null?merchantAuth.isBlocked():false;
				Boolean isActive = merchantAuth.isActive()!= null?merchantAuth.isActive():false;
				Long status = merchantAuth.getStatus()!= null?merchantAuth.getStatus():new Long(0);
				
				if(isBlocked || !isActive || !UserStatusConstants.APPROVED.equals(status)) {
					String messageCode = null;
					if(UserStatusConstants.REJECTED.equals(status)){
						messageCode = PaymentMessageCode.MERCHANT_ACCOUNT_REJ_ADMINISTRATOR;
					}else if(UserStatusConstants.LOCKED.equals(status)){
						messageCode = PaymentMessageCode.MERCHANT_LOCKED; 
					}else if(UserStatusConstants.DELETED.equals(status)){
						messageCode = PaymentMessageCode.MERCHANT_ACCOUNT_DELETED; 
					}
					paymentDto.setErrorCode(messageCode);
					paymentDto.setErrorMessage(getPaymentErrorMessage(request, messageCode));
					request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
				} else if(!paymentDto.getMerchantCode().equals(paymentDto.getMerchantCodeFromDB())){
					paymentDto.setErrorCode(PaymentMessageCode.MERCHANT_CODE_NOT_MATCHES);
					paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.MERCHANT_CODE_NOT_MATCHES));
					request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
				} else{
					LOGGER.info(" merchant email id " + merchantAuth.getEmailId() + " is valid");
					paymentDto.setMerchantAuthId(merchantAuth.getId());
				}
		} catch(Exception e){
			LOGGER.error(e.getMessage() , e);
			paymentDto.setErrorCode(PaymentMessageCode.MERCHANT_NOT_FOUND);
			paymentDto.setErrorMessage(getPaymentErrorMessage(request, PaymentMessageCode.MERCHANT_NOT_FOUND));
			request.getSession().setAttribute(PAYMENT_DETAILS, paymentDto);
		}
	}

	private String getPaymentErrorMessage(HttpServletRequest request, String key){
		Map<String, String> dataMap = (Map<String, String>) request.getSession().getServletContext().getAttribute(MasterDataConstants.PAYMENT_MESSAGES);
		return dataMap.get(key);
	}
	
	/**
	 * Validate request input parameters
	 * @param paymentDto
	 * @return
	 */
	private Boolean validateInputFields(PaymentDto paymentDto){
		return paymentDto.getOrderId() == null 
				|| paymentDto.getAmount() == null
				|| paymentDto.getAmount().equals(ZERO_DOUBLE) 
				|| paymentDto.getCurrency() == null
				|| paymentDto.getMerchantLoginId() == null;
	}
	
	/**
	 * Get currency id from currency code
	 * @param request
	 * @param currencyCode
	 * @return
	 */
	private Long getCurrencyIdFromCode(HttpServletRequest request, String currencyCode){
		Map<Long, String> currencyMap = MasterDataUtil.getCurrencyNames(request.getSession().getServletContext(),
				(Long) request.getSession().getAttribute(LANGUAGE_ID));
		 Set keys = currencyMap.keySet();
	    for (Iterator i = keys.iterator(); i.hasNext();) {
		      Long key = (Long) i.next();
		      String value = (String) currencyMap.get(key);
		      if(value.equals(currencyCode)){
		    	  return key;
		      }
	    }
		return null;
	}	
	private String navigateMerchantPage(Map model, HttpServletRequest request, Integer status){
		PaymentDto paymentDto = (PaymentDto)request.getSession().getAttribute(PAYMENT_DETAILS);
		String view = null;
		if(PAYMENT_SUCCESS.equals(status)){
			model.put("merchantSuccessUrl", paymentDto.getMerchantSuccessUrl());
			view = PAYMENT_SUCCESS_VIEW;
			try{
				Authentication auth = commonService.getAuthentication(paymentDto.getCustomerAuthId());
				loginService.logout(auth.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
			} catch(Exception e){
				LOGGER.error(e.getMessage() , e);
			}
		} else {
			try{
				if(PaymentMessageCode.INSUFFICIANT_BALANCE.equals(paymentDto.getErrorCode())
						||PaymentMessageCode.CUSTOMER_LOCKED.equals(paymentDto.getErrorCode())
						||PaymentMessageCode.CUSTOMER_ACCOUNT_PENDIND_STATE.equals(paymentDto.getErrorCode())){
					Authentication auth = commonService.getAuthentication(paymentDto.getCustomerAuthId());
					loginService.logout(auth.getEmailId(), GlobalLitterals.CUSTOMER_USER_TYPE);
				}	
			} catch(Exception e){
				LOGGER.error(e.getMessage() , e);
			}
			model.put(MERCHANT_FAILURE_URL , paymentDto.getMerchantFailureUrl());
			view = PAYMENT_FAILURE_VIEW;
		}
		paymentDto.setStatus(status);
		model.put(PAYMENT_DTO , paymentDto);
		request.getSession().setAttribute(PAYMENT_DETAILS, null);
		return view;
	}
}
