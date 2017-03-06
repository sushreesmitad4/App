package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.accounts.business.AccountsService;
import com.tarang.ewallet.accounts.util.AccountsConstants;
import com.tarang.ewallet.accounts.util.FundingAccountStatus;
import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.transaction.business.ReloadMoneyService;
import com.tarang.ewallet.transaction.business.VelocityAndThresholdService;
import com.tarang.ewallet.transaction.util.WalletTransactionConstants;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.controller.constants.ReloadMoney;
import com.tarang.ewallet.walletui.form.ReloadMoneyForm;
import com.tarang.ewallet.walletui.util.DisplayAccount;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.ManageAccountUtil;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.ReloadMoneyValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;


@SuppressWarnings({"rawtypes", "unchecked"}) 
@Controller
@RequestMapping("/reloadmoney")
public class ReloadMoneyController implements ReloadMoney, AttributeConstants, AttributeValueConstants, AccountsConstants, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(ReloadMoneyController.class);

	@Autowired
	private AccountsService accountsService;
	
	@Autowired
	private ReloadMoneyService reloadMoneyService;
	
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
		String url = WALLET_PATH_PREFIX + RELOAD_MONEY_GRID;
		request.setAttribute(URLACCOUNT_LIST, url);
		return getView(session, RELOAD_MONEY);
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
	
	@RequestMapping(value = "/reloadmoneyrequest", method = RequestMethod.GET)
	public String reloadMoneyRequestPage(@Valid ReloadMoneyForm reloadMoneyForm, Map model, 
			HttpServletRequest request, Locale locale) {
		LOGGER.debug(" reloadMoneyRequestPage ");
		String viewPage = "";
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String sucMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(sucMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(sucMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		String accountId = (String)request.getParameter(ID);
		String type = (String)request.getParameter("mtype");
		if(type != null && accountId != null){
			LOGGER.info("Type is : " + type + " AccountId is : " + accountId);
			if(RELOAD.equalsIgnoreCase(type)){
				try {
					AccountDto accountDto = accountsService.getAccount(Long.parseLong(accountId));
					if(accountDto != null){
						if(!CARD_ACCOUNT.equals(accountDto.getAtype())){
							session.setAttribute(ERROR_MESSAGE, ACCOUNT_TYPE_IS_NOT_CARD);
							return UIUtil.redirectPath(RELOAD_MONEY_PATH);
						}
						if(!FundingAccountStatus.VERIFIED_STATUS.equals(accountDto.getStatus())){
							session.setAttribute(ERROR_MESSAGE, ACCOUNT_IS_NOT_VARIFIED);
							return UIUtil.redirectPath(RELOAD_MONEY_PATH);
						}
						convertReloadMoneyFormFromAccountDto(request, reloadMoneyForm, accountDto);
						Long defaultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(),accountDto.getCountry());
						String currencyCode = getCurrencyCode(request,defaultCurrency);
						model.put(RELOADMONEY_FORM , reloadMoneyForm);
						viewPage = RELOAD_MONEY_REQUEST;
						reloadMoneyForm.setCurrecnyCode(currencyCode);
						reloadMoneyForm.setAccountId(accountDto.getId().toString());
					}
				} catch (NumberFormatException e) {
					LOGGER.error(e.getMessage() , e);
					model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_ACCOUNT_INFO_RELOAD_MONEY, null, locale));
					viewPage = RELOAD_MONEY;
				} catch (WalletException e) {
					LOGGER.error(e.getMessage() , e);
					model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_ACCOUNT_INFO_RELOAD_MONEY, null, locale));
					viewPage = RELOAD_MONEY;
				}
			}
		} else{
			model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_ACCOUNT_INFO_RELOAD_MONEY, null, locale));
			viewPage = RELOAD_MONEY;
		}
		String url = WALLET_PATH_PREFIX + RELOAD_MONEY_GRID;
		request.setAttribute(URLACCOUNT_LIST , url);
		return getView(session, viewPage);
	}
	
	@RequestMapping(value = "/reloadmoneyrequest", method = RequestMethod.POST)
	public String reloadMoneyRequestSavePage(@Valid ReloadMoneyForm reloadMoneyForm, BindingResult result,
			Map model, HttpServletRequest request, Locale locale) {
		LOGGER.debug(" reloadMoneyRequestSavePage ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String type = reloadMoneyForm.getMode();
		String viewPage = RELOAD_MONEY;
		if(type != null && reloadMoneyForm.getAccountId() != null && RELOAD.equalsIgnoreCase(type)){
			ReloadMoneyValidator rmvalidator =  new ReloadMoneyValidator();
			rmvalidator.validate(reloadMoneyForm, result);
			AccountDto accountDto = null;
			ReloadMoneyDto reloadMnyDto = null;
			try {
				accountDto = accountsService.getAccount(Long.parseLong(reloadMoneyForm.getAccountId()));
				//Threshold validation check
				reloadMnyDto = getReloadMoneyDto(request, reloadMoneyForm);
				VelocityAndThreshold velocity = velocityAndThresholdService.getThreshold(reloadMnyDto.getCountryId(), 
						reloadMnyDto.getCurrencyId(), reloadMnyDto.getTypeOfTransaction(), 
						reloadMnyDto.getUserType());
				if(!reloadMoneyForm.getAmount().equals(EMPTY_STRING)){
					Boolean amountError = UIUtil.currencyValidator(reloadMoneyForm.getAmount());
					if(!amountError){
						reloadMnyDto.setReloadAmount(Double.parseDouble(reloadMoneyForm.getAmount()));
					}
					if((velocity != null && reloadMnyDto.getReloadAmount() != null) &&
							!(velocity.getMinimumamount() <= reloadMnyDto.getReloadAmount() && 
							velocity.getMaximumamount() >= reloadMnyDto.getReloadAmount())){
						String messageOne = context.getMessage(ReloadMoneyValidator.MONEY_EXCEEDS_THRESHOLD_MSG_ONE, null, locale);
						String messageTwo = context.getMessage(ReloadMoneyValidator.MONEY_EXCEEDS_THRESHOLD_MSG_TWO, null, locale);
						result.rejectValue(ReloadMoneyValidator.RELOAD_MONEY_AMOUNT_FIELD, null, 
								messageOne + SPACE_STRING + UIUtil.getConvertedAmountInString(velocity.getMinimumamount()) + " - " 
						+ UIUtil.getConvertedAmountInString(velocity.getMaximumamount()) + SPACE_STRING + messageTwo);
					}
				}
			} catch (NumberFormatException e1) {
				LOGGER.error(e1.getMessage(), e1);
				viewPage = RELOAD_MONEY;
				model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_ACCOUNT_INFO_RELOAD_MONEY, null, locale));
			} catch (WalletException e1) {
				LOGGER.error(e1.getMessage(), e1);
				viewPage = RELOAD_MONEY;
				model.put(ERROR_MESSAGE, context.getMessage(UNABLETOLOAD_ACCOUNT_INFO_RELOAD_MONEY, null, locale));
			}
			
			if (result.hasErrors()) {
				viewPage = RELOAD_MONEY_REQUEST;
				if(accountDto != null){
					convertReloadMoneyFormFromAccountDto(request, reloadMoneyForm, accountDto);
				}
				model.put(RELOADMONEY_FORM , reloadMoneyForm);
			} else{
				try {
					ReloadMoneyDto reloadMoneyDto = reloadMoneyService.createReloadMoney(reloadMnyDto);
					//Audit Trail service
					auditTrailService.createAuditTrail(reloadMnyDto.getAuthId(), AuditTrailConstrain.MODULE_RELAOD_MONEY_CREATE, 
							AuditTrailConstrain.STATUS_CREATE, reloadMnyDto.getPayeeEmail(), session.getAttribute(USER_TYPE).toString());
					
					if(reloadMoneyDto != null && reloadMoneyDto.getIsReloadMoneySucc()){
						session.setAttribute(SUCCESS_MESSAGE, RELOAD_SUCCESS_MESSAGE);
						return UIUtil.redirectPath("reloadmoney/reloadmoneyrequest?mtype=reload&id="+accountDto.getId());
					} else {
					  //failed
						UIUtil.papulatePgResponseCode(session, context, model, locale, reloadMoneyDto.getPgResponseCode());
						viewPage = RELOAD_MONEY_REQUEST;
						String pgErrorCode = (String)request.getSession().getAttribute(ERROR_MESSAGE);
						if(pgErrorCode != null){
							model.put(ERROR_MESSAGE, context.getMessage(pgErrorCode, null, locale));
							request.getSession().removeAttribute(ERROR_MESSAGE);
						}
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					String em = null;
					if(e.getMessage().contains(WalletTransactionConstants.ERROR_OVER_LIMIT_THRESHOLD_AMOUNT)){
						em = ERROR_OVER_LIMIT_THRESHOLD_AMOUNT;
					}
					if(e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
						em = ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH;
					}
					if(e.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
						em = ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH;
					}
					if(e.getMessage().equals(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT)){
						em = ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT;
					} else {
						em = RELOAD_FAILED_MESSAGE;
					}
					viewPage = RELOAD_MONEY_REQUEST;
					model.put(ERROR_MESSAGE, context.getMessage(em, null, locale));
				}
			}
		}
		
		String url = WALLET_PATH_PREFIX + RELOAD_MONEY_GRID;
		request.setAttribute(URLACCOUNT_LIST, url);
		return getView(session, viewPage);
	}
	
	private ReloadMoneyDto getReloadMoneyDto(HttpServletRequest request, ReloadMoneyForm reloadMoneyForm) throws WalletException{
		ReloadMoneyDto reloadMoneyDto = new ReloadMoneyDto();
		String userAgent = request.getHeader(USER_AGENT);
		String clientIpAddress = UIUtil.getClientIpAddr(request);
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		String uType = (String)request.getSession().getAttribute(USER_TYPE);
		if(CUSTOMER_USER_TYPE.equals(uType)){
			reloadMoneyDto.setTypeOfTransaction(WalletTransactionTypes.RELOAD_MONEY_FROM_CARD_CUSTOMER);
			reloadMoneyDto.setUserType(1L);
		} else if(MERCHANT_USER_TYPE.equals(uType)){
			reloadMoneyDto.setTypeOfTransaction(WalletTransactionTypes.RELOAD_MONEY_FROM_CARD_MERCHANT);
			reloadMoneyDto.setUserType(2L);
		}
		AccountDto acccountDto = accountsService.getAccount(Long.parseLong(reloadMoneyForm.getAccountId()));
		
		reloadMoneyDto.setUserAgent(userAgent);
		reloadMoneyDto.setClientIpAddress(clientIpAddress);
		reloadMoneyDto.setAuthId(authId);
		reloadMoneyDto.setAccountId(acccountDto.getId());
		reloadMoneyDto.setCvv(reloadMoneyForm.getCvv());
		reloadMoneyDto.setCardNumber(acccountDto.getCardNumber());
		reloadMoneyDto.setCardExpairyDate(acccountDto.getCardExpairyDate());
		reloadMoneyDto.setCountryId(acccountDto.getCountry());
		reloadMoneyDto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(
				request.getSession().getServletContext(), acccountDto.getCountry()));
		reloadMoneyDto.setCurrencyId(MasterDataUtil.getCountryCurrencyId(
				request.getSession().getServletContext(), acccountDto.getCountry()));
		reloadMoneyDto.setPayeeName((String)request.getSession().getAttribute(NAME));
		reloadMoneyDto.setPayeeEmail((String)request.getSession().getAttribute(USER_ID));
		reloadMoneyDto.setLanguageId((Long) request.getSession().getAttribute(LANGUAGE_ID));
		reloadMoneyDto.setAccountOrCardHolderName(acccountDto.getAccountHolderName());
		reloadMoneyDto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
		return reloadMoneyDto;
	}
	
	/**
	 * For reload money request page, to display static values
	 * @param request
	 * @param reloadMoneyForm
	 * @param accountDto
	 */
	private void convertReloadMoneyFormFromAccountDto(HttpServletRequest request, ReloadMoneyForm reloadMoneyForm, AccountDto accountDto){
		
		Map<Long, String> cardTypeMap = MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID),
				MasterDataConstants.MD_CARD_TYPES);
		
		String bankOrCardNumber = null;
		String bankOrCardName = null;
		
		if( accountDto.getAtype().equals(Accounts.BANK_ACCOUNT)){
			bankOrCardNumber = accountDto.getAccountNumber();
			bankOrCardName = accountDto.getBankName();
		} else {
			bankOrCardNumber = accountDto.getCardNumber();
			bankOrCardName = cardTypeMap.get(accountDto.getCardType());
		}
		reloadMoneyForm.setAccountOrCardHolderName(accountDto.getAccountHolderName());
		reloadMoneyForm.setBankOrCardNumber(ManageAccountUtil.xNumber(bankOrCardNumber));
		reloadMoneyForm.setBankOrCardName(bankOrCardName);
		reloadMoneyForm.setCardType(accountDto.getCardType());

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
	
	private String getCurrencyCode(HttpServletRequest request,Long currencyId) {
		return	MasterDataUtil.getCurrencyNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(currencyId);
	}
}