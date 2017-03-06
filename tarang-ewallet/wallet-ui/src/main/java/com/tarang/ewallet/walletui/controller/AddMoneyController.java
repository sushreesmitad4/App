package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.accounts.business.AccountsService;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AddMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.masterdata.util.CountryIds;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.business.AddMoneyService;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.controller.constants.ReloadMoney;
import com.tarang.ewallet.walletui.form.AddMoneyForm;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.util.DisplayAccount;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.ManageAccountUtil;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.mwallet.rest.services.util.ServerProcessorStatus;


@SuppressWarnings({"rawtypes", "unchecked"}) 
@Controller
@RequestMapping("/addmoney")
public class AddMoneyController implements ReloadMoney, AttributeConstants, AttributeValueConstants, AccountsConstants, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(AddMoneyController.class);

	@Autowired
	private AccountsService accountsService;
	
	
	@Autowired
	private MerchantService merchantService;
	
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AddMoneyService addMoneyService;
	
	@Autowired
	private VelocityAndThresholdService velocityAndThresholdService;

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String reloadMoneyPage(Map model, HttpServletRequest request, Locale locale) {
		LOGGER.debug(" reloadMoneyPage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String msgkey = (String)session.getAttribute(ERROR_MESSAGE);
		if(msgkey != null){
			model.put(ERROR_MESSAGE, context.getMessage(msgkey, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			session.removeAttribute(SUCCESS_MESSAGE);
			
		}
		String url = WALLET_PATH_PREFIX + RELOAD_MONEY_GRID;
		request.setAttribute(URLACCOUNT_LIST, url);
		return getView(session, ADD_MONEY);
		
	}
	
	@RequestMapping(value = "/accountrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<DisplayAccount> accountRecords(Map model, Locale locale, HttpServletRequest request) {

		List<DisplayAccount> displayList = new ArrayList<DisplayAccount>();
		try {
			Map<Long,String> moneyAccountStatusMap = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.MONEY_ACCOUNT_STATUSES);
			
			Map<Long, Object> bankAccountTypeMap = MasterDataUtil.getObjectDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.BANK_ACCOUNT_TYPES);
			
			Map<Long, String> cardTypeMap = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.MD_CARD_TYPES);
			
			displayList = ManageAccountUtil.getDisplayAccounts(
					accountsService.getAccounts((Long)request.getSession().getAttribute(AUTHENTICATION_ID)),
					moneyAccountStatusMap, bankAccountTypeMap, cardTypeMap);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
		}
		JqgridResponse<DisplayAccount> response = new JqgridResponse<DisplayAccount>();
		response.setRows(displayList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = displayList.size()/ps;
		if( displayList.size()/ps*ps != displayList.size()){
			n++;
		}
		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);

		return response;
	}
	
	@RequestMapping(value = "/addmoneyrequest", method = RequestMethod.GET)
	public String addMoneyRequestPage(@Valid ReloadMoneyForm reloadMoneyForm, Map model, 
			HttpServletRequest request, Locale locale) {
		LOGGER.debug(" addMoneyRequestPage ");
		String viewPage = "";
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			session.removeAttribute(SUCCESS_MESSAGE);
			
		}
		viewPage = ADD_MONEY;
		String url = WALLET_PATH_PREFIX + RELOAD_MONEY_GRID;
		request.setAttribute(URLACCOUNT_LIST , url);
		return getView(session, viewPage);
	}
	
	
	@RequestMapping(value = "/threshold", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject thresholdCheck(@Valid AddMoneyForm addMoneyForm, BindingResult result, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug(" thresholdCheck ");
		JSONObject obj = new JSONObject();
		String amount = (String) request.getParameter("amount");
		String userType = (String) request.getParameter("userType");
		String emailId = (String) request.getParameter("emailId");
		String requestedCurrency = (String) request.getParameter("requestedCurrency");
		String errMsg = (String) context.getMessage(INVALID_AMOUNT_FORMAT, null, locale);
		Authentication authentication = null;
		UserWallet userWallet = null;
		WalletThreshold walletThreshold = null;
		LOGGER.info("emailId: " + emailId + "userType: " + userType + " amount: " + amount + "requestedCurrency: "
				+ requestedCurrency);

		try {

			authentication = commonService.getAuthentication(emailId, userType);

			AddMoneyDto addMnyDto = getAddMoneyDto(request, emailId, userType);

			addMnyDto.setAuthId(authentication.getId());

			VelocityAndThreshold velocity = velocityAndThresholdService.getThreshold(addMnyDto.getCountryId(),
					addMnyDto.getCurrencyId(), addMnyDto.getTypeOfTransaction(), addMnyDto.getUserType());

			if (amount == null || amount.equals(EMPTY_STRING)) {

				errMsg = (String) context.getMessage(INVALID_AMOUNT_FORMAT, null, locale);
				obj.put(ERROR_MESSAGE, errMsg);

				return obj;
			} else {
				addMnyDto.setAddMoneyAmount(Double.parseDouble(amount));
			}
			if ((velocity != null && String.valueOf(addMnyDto.getAddMoneyAmount()) != null)
					&& !(velocity.getMinimumamount() <= addMnyDto.getAddMoneyAmount()
							&& velocity.getMaximumamount() >= addMnyDto.getAddMoneyAmount())) {
				errMsg = (String) context.getMessage(WalletTransactionConstants.ERROR_LIMIT_THRESHOLD_ADD_AMOUNT, null,
						locale);
				obj.put(ERROR_MESSAGE, errMsg);
				return obj;
			}
			userWallet = commonService.getUserWallet(authentication.getId(), addMnyDto.getCurrencyId());

			walletThreshold = velocityAndThresholdService.getWallet(addMnyDto.getCountryId(),
					addMnyDto.getCurrencyId());

			if (userWallet != null && walletThreshold != null && userWallet.getAmount() != null
					&& walletThreshold.getMaximumamount() != null) {
				Double userAddAmount = userWallet.getAmount() + Double.valueOf(amount);
				if (Double.valueOf(userAddAmount) > walletThreshold.getMaximumamount()
						|| userWallet.getAmount() > walletThreshold.getMaximumamount()) {

					errMsg = (String) context
							.getMessage(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_ADD_AMOUNT, null, locale);
					obj.put(ERROR_MESSAGE, errMsg);
					return obj;
				}
			}

		}

		catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			String em = null;
			if (e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)) {
				em = ServerProcessorStatus.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT.getValue();
			}
			errMsg = (String) context.getMessage(em, null, locale);
			obj.put(ERROR_MESSAGE, errMsg);
		}

		return obj;
	}
	
	@RequestMapping(value = "/addmoneyrequest", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addmoneyrequestRequestSavePage(@Valid AddMoneyForm addMoneyForm, BindingResult result,
			Locale locale, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug(" addmoneyrequestSavePage ");
		JSONObject obj = new JSONObject();
		HttpSession session = request.getSession();
		if (!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)) {

			obj.put("url", UIUtil.redirectPath(HOME_LOGIN_PATH));
			return obj;
		}

		String amount = (String) request.getParameter("amount");
		String userType = (String) request.getParameter("userType");
		String emailId = (String) request.getParameter("emailId");
		String orderId = (String) request.getParameter("orderId");
		String requestedCurrency = (String) request.getParameter("requestedCurrency");
		String errMsg = (String) context.getMessage(INVALID_AMOUNT_FORMAT, null, locale);

		Authentication authentication = null;

		LOGGER.info("emailId: " + emailId + "userType: " + userType + "orderId: " + orderId + " amount: " + amount
				+ "requestedCurrency: " + requestedCurrency);

		try {

			authentication = commonService.getAuthentication(emailId, userType);

			AddMoneyDto addMnyDto = getAddMoneyDto(request, emailId, userType);
			addMnyDto.setOrderId(orderId);
			addMnyDto.setAuthId(authentication.getId());

			if (amount == null || amount.equals(EMPTY_STRING)) {

				session.setAttribute(ERROR_MESSAGE, INVALID_AMOUNT_FORMAT);
				errMsg = (String) context.getMessage(INVALID_AMOUNT_FORMAT, null, locale);
				obj.put(ERROR_MESSAGE, errMsg);

				return obj;

			} else {
				if (!amount.equals(EMPTY_STRING)) {
					Boolean amountError = UIUtil.currencyValidator(amount);
					if (amountError) {

						session.setAttribute(ERROR_MESSAGE, INVALID_AMOUNT_FORMAT);
						errMsg = (String) context.getMessage(INVALID_AMOUNT_FORMAT, null, locale);
						obj.put(ERROR_MESSAGE, errMsg);
						return obj;

					} else {
						addMnyDto.setAddMoneyAmount(Double.parseDouble(amount));
					}

				}

			}

			AddMoneyDto addMoneyDto = addMoneyService.createAddMoney(addMnyDto);
			auditTrailService.createAuditTrail(addMnyDto.getAuthId(), AuditTrailConstrain.MODULE_ADD_MONEY_CREATE,
					AuditTrailConstrain.STATUS_CREATE, addMnyDto.getPayeeEmail(), userType);

			if (addMnyDto.getOrderId() != null && addMoneyDto != null && addMoneyDto.getIsReloadMoneySucc()) {

				String sucMsg = (String) context.getMessage(ADD_MONEY_SUCCESS_MESSAGE, null, locale);
				obj.put(SUCCESS_MESSAGE, sucMsg);
				return obj;

			} else {

				errMsg = (String) context.getMessage(FAILED_TO_ADD_MONEY, null, locale);
				obj.put(ERROR_MESSAGE, errMsg);
				return obj;

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			String em = null;
			if (e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)) {
				em = ServerProcessorStatus.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT.getValue();
			} else if (e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)) {
				em = ServerProcessorStatus.PG_SERVICE_IS_NOT_ESTABLISH.getValue();
			} else if (e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)) {
				em = ServerProcessorStatus.PG_SERVICE_IS_NOT_ESTABLISH.getValue();
			} else if (e.getMessage().equals(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT)) {
				em = ServerProcessorStatus.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT.getValue();
			} else {
				em = ServerProcessorStatus.RELOAD_FAILED_MESSAGE.getValue();
			}
			errMsg = (String) context.getMessage(em, null, locale);
			obj.put(ERROR_MESSAGE, errMsg);
		}

		return obj;

	}

	private String getView(HttpSession session, String view){
		String uview = view;
		if (session.getAttribute(USER_TYPE) == null) {
			return Login.VIEW_RESOLVER_SESSION_EXPIRED;
		}
		String uType = (String)session.getAttribute(USER_TYPE);
		
		if(CUSTOMER_USER_TYPE.equals(uType)){
			if(uview == null){
				uview = AttributeValueConstants.CUSTOMER_PATH;
			} else{
				uview = Accounts.CUSTOMER_VIEW + view;
			}
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			if(uview == null){
				uview = AttributeValueConstants.MERCHANT_PATH;
			} else {
				uview = Accounts.MERCHANT_VIEW + view;
			}
		}
		return uview;
	}
	
	private AddMoneyDto getAddMoneyDto(HttpServletRequest request, String emailId, String userType) throws WalletException{
		AddMoneyDto addMoneyDto = new AddMoneyDto();
		if(CUSTOMER_USER_TYPE.equals(userType)){
			addMoneyDto.setTypeOfTransaction(WalletTransactionTypes.ADD_MONEY_FROM_CARD_CUSTOMER);
			addMoneyDto.setUserType(USERTYPE_CUSTOMER);
		} else if(MERCHANT_USER_TYPE.equals(userType)){
			addMoneyDto.setTypeOfTransaction(WalletTransactionTypes.ADD_MONEY_FROM_CARD_MERCHANT);
			addMoneyDto.setUserType(USERTYPE_MERCHANT);
		}
		addMoneyDto.setCountryId(CountryIds.IND_COUNTRY_ID);
		addMoneyDto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(
				request.getSession().getServletContext(), CountryIds.IND_COUNTRY_ID));
		addMoneyDto.setCurrencyId(MasterDataUtil.getCountryCurrencyId(
				request.getSession().getServletContext(), CountryIds.IND_COUNTRY_ID));
		addMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.MOBILE.getValue()));
		String personName = null;
		if(GlobalLitterals.CUSTOMER_USER_TYPE.equals(userType)){
			personName = customerService.getPersonName(emailId, userType);
			LOGGER.info("Customer Name :: " + personName);
		} else if (GlobalLitterals.MERCHANT_USER_TYPE.equals(userType)){
			personName = merchantService.getPersonName(emailId, userType);
			LOGGER.info("Merchant Name :: " + personName);
		}
		addMoneyDto.setPayeeName(personName);
		addMoneyDto.setPayeeEmail(emailId);
		//Time being take it as a null
		addMoneyDto.setLanguageId(null);
		addMoneyDto.setAccountOrCardHolderName(personName);
		return addMoneyDto;
	}
}