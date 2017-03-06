package com.tarang.ewallet.walletui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.common.util.TypeOfRequest;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.AccountDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.transaction.business.FraudCheckService;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.service.UtilService;
import com.tarang.ewallet.walletui.controller.constants.Accounts;
import com.tarang.ewallet.walletui.controller.constants.Login;
import com.tarang.ewallet.walletui.form.AddBankAccountForm;
import com.tarang.ewallet.walletui.form.AddCardAccountForm;
import com.tarang.ewallet.walletui.form.NotVerifiedForm;
import com.tarang.ewallet.walletui.form.PendingForm;
import com.tarang.ewallet.walletui.util.DisplayAccount;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.ManageAccountUtil;
import com.tarang.ewallet.walletui.util.ManageAccountsHelper;
import com.tarang.ewallet.walletui.util.MasterDataConstants;
import com.tarang.ewallet.walletui.util.MasterDataUtil;
import com.tarang.ewallet.walletui.util.MerchantDataUtil;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.BankValidator;
import com.tarang.ewallet.walletui.validator.CardValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;


@SuppressWarnings({"rawtypes", "unchecked"}) 
@Controller
@RequestMapping("/manageaccounts")
public class ManageAccountsController implements Accounts, AttributeConstants, AttributeValueConstants, AccountsConstants, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(ManageAccountsController.class);

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private AccountsService accountsService;

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private BankValidator bankValidator;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private CardValidator cardValidator;
	
	@Autowired
	private UtilService utilService;
	
	@Autowired
	private FraudCheckService fraudCheckService;

	@RequestMapping(method = RequestMethod.GET)
	public String accountManagement(Map model, HttpServletRequest request, Locale locale) {
		LOGGER.debug(" accountManagement ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String errorMessage = null;
		Long authId = (Long)request.getSession().getAttribute(AUTHENTICATION_ID);
		String type = (String)request.getParameter("mtype");
		String id =(String)request.getParameter(ID);
		
		String cardAddSuccMsg = (String)session.getAttribute(SUCCESS_MESSAGE);
		if(cardAddSuccMsg != null){
			model.put(SUCCESS_MESSAGE, context.getMessage(cardAddSuccMsg, null, locale));
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		
		String errorMsgCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorMsgCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorMsgCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		if(type != null && id != null){
			LOGGER.info("Type is :" + type + "Id is :" + id);
			AccountDto dto = null;
			try {
				dto = accountsService.getAccount(Long.parseLong(id));
				if(null == dto){
					session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
					return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
				return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
			}
			if("setdefault".equalsIgnoreCase(type)){
				try {
					if(dto.getDefaultAccount()){
						session.setAttribute(ERROR_MESSAGE, ERROR_MSG_ALREADY_DEFAULT_ACCOUNT);
						return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
					} else if(!FundingAccountStatus.VERIFIED_STATUS.equals(dto.getStatus())){
						session.setAttribute(ERROR_MESSAGE, ERROR_MSG_VERIFIED_ACCOUNT_REQUIRED);
						return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
					} else{
						accountsService.setAsDefaultAccount(authId, Long.parseLong(id));
						model.put(SUCCESS_MESSAGE, context.getMessage(ACCOUNT_SETDEFAULT_SUCCESS_MESSAGE, null, locale));
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e);
					errorMessage = ACCOUNT_SETDEFAULT_ERROR_MESSAGE;
					model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				}				
			} else if("delete".equalsIgnoreCase(type)){
				try {
					if(dto.getDefaultAccount()){
						session.setAttribute(ERROR_MESSAGE, ERROR_MSG_UNABLE_DELETE_DEFAULT_ACCOUNT);
						return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
					} else if(!FundingAccountStatus.NOT_VERIFIED_STATUS.equals(dto.getStatus()) && 
							!FundingAccountStatus.VERIFIED_STATUS.equals(dto.getStatus())){
						session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_VERIFIED_AND_VERIFIED_ACCOUNT_REQUIRED);
						return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
					} else{
						accountsService.delete(Long.parseLong(id), MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
								(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
						model.put(SUCCESS_MESSAGE, context.getMessage(ACCOUNT_DELETE_SUCCESS_MESSAGE, null, locale));
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e);
					errorMessage = ACCOUNT_DELETE_ERROR_MESSAGE;
					model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				}
			} else if("verify".equalsIgnoreCase(type)){
				try {
					if(FundingAccountStatus.VERIFIED_STATUS.equals(dto.getStatus())){
						session.setAttribute(ERROR_MESSAGE, ERROR_MSG_ACCOUNT_ALREADY_VERIFIED);
						return UIUtil.redirectPath(MANAGE_ACCOUNTS_PATH);
					}
					accountsService.updateStatus(Long.parseLong(id), FundingAccountStatus.VERIFIED_STATUS, 1L);
					model.put(SUCCESS_MESSAGE, context.getMessage(ACCOUNT_VERIFY_SUCCESS_MESSAGE, null, locale));
				} catch (Exception e) {
					LOGGER.error(e.getMessage() , e);
					errorMessage = ACCOUNT_VERIFY_ERROR_MESSAGE;
					model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
				}
			}
		}

		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		
		String url = WALLET_PATH_PREFIX + "manageaccounts/accountrecords";
		request.setAttribute(URLACCOUNT_LIST , url);
		return getView(session, ACC_MANAGEMENT_VIEW);
	}

	@RequestMapping(value = "/accountrecords", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<DisplayAccount> accountRecords(Map model, Locale locale, HttpServletRequest request) {
		
		String pgErrorCode = (String)request.getSession().getAttribute(ERROR_MESSAGE);
		if(pgErrorCode != null){
			model.put(ERROR_MESSAGE, pgErrorCode);
			request.getSession().removeAttribute(ERROR_MESSAGE);
		}

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
			model.put(ERROR_MESSAGE, context.getMessage(UserValidator.NO_RECORDS_FOUND, null, locale));
			LOGGER.error("accountRecords", e);
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
	
	@RequestMapping(value = "/addbank", method = RequestMethod.GET)
	public String addBank(HttpServletRequest request, AddBankAccountForm addBankAccountForm, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("Entering:: ManageAccountsConroller:: addBank() :: GET ");
		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		String eMsg = accountsCountVerification(request);
		if(eMsg != null){
			if(SESSION_EXPIRED_MSG.equals(eMsg)){
				return Login.VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
			} else {
				session.setAttribute(ERROR_MESSAGE, eMsg);
				return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
			}
		}

		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(IS_EDIT_BANK_PAGE, Boolean.FALSE);
		return getView(session, Accounts.ADD_BANK_VIEW);
	}

	@RequestMapping(value = "/addbank", method = RequestMethod.POST)
	public String addBankDetails(HttpServletRequest request, @Valid AddBankAccountForm addBankAccountForm,
			BindingResult result, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("Entering:: ManageAccountsConroller:: addBankDetails() :: POST ");
		String eMsg = accountsCountVerification(request);
		if(eMsg != null){
			if(SESSION_EXPIRED_MSG.equals(eMsg)){
				return Login.VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
			} else {
				model.put(ERROR_MESSAGE, context.getMessage(eMsg, null, locale));
				return getView(session, ACC_MANAGEMENT_VIEW);
			}
		}
		
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		model.put(IS_EDIT_BANK_PAGE, Boolean.FALSE);
		
		if(addBankAccountForm.getMode() != null && "countryChanged".equals(addBankAccountForm.getMode())) {
			return getView(session, Accounts.ADD_BANK_VIEW);
		}
		if(addBankAccountForm.getMode() != null &&  "add".equals(addBankAccountForm.getMode())) {
			bankValidator.validate(addBankAccountForm, result);
			if(result.hasErrors()){
				return getView(session, Accounts.ADD_BANK_VIEW);
			}
			
			try{
				//************   JOINT ACCOUNT CHECK START  ***************//
				Integer totalExistBankAccouts = accountsService.getTotalBankAccouts(addBankAccountForm.getAccountNumber(), 
						addBankAccountForm.getSortCode(), (String)session.getAttribute(USER_TYPE), AccountsConstants.NOT_DELETE);
				
				if(session.getAttribute(USER_TYPE).equals(CUSTOMER_USER_TYPE)){
					if(!totalExistBankAccouts.equals(GlobalLitterals.ZERO_INTEGER)){
						if(addBankAccountForm.getJointAccount()){
							if(totalExistBankAccouts.intValue() >= utilService.getJointBankAccountsLimit()){
								model.put(ERROR_MESSAGE, context.getMessage(JOINT_BANK_ACCOUNT_EXCEEDED, null,locale));
								return getView(session, Accounts.ADD_BANK_VIEW);
							} else {
								Boolean bankExistByAuthId = accountsService.bankAccountExistWithSameUser(addBankAccountForm.getAccountNumber(), 
										addBankAccountForm.getSortCode(), (Long)session.getAttribute(AUTHENTICATION_ID), AccountsConstants.NOT_DELETE);
								if(bankExistByAuthId.equals(Boolean.TRUE)){
									model.put(ERROR_MESSAGE, context.getMessage(JOINT_BANK_ACCOUNT_EXISTWITHAUTHID, null,locale));
									return getView(session, Accounts.ADD_BANK_VIEW);
								}
							}
						} else {
							model.put(ERROR_MESSAGE, context.getMessage(Common.DUPLICATE_BANK_ACCOUNT, null,locale));
							return getView(session, Accounts.ADD_BANK_VIEW);
						}
					}
				} else if(session.getAttribute(USER_TYPE).equals(MERCHANT_USER_TYPE) && !totalExistBankAccouts.equals(GlobalLitterals.ZERO_INTEGER)){
					Boolean bankExistByAuthId = accountsService.bankAccountExistWithSameUser(addBankAccountForm.getAccountNumber(), 
							addBankAccountForm.getSortCode(), (Long)session.getAttribute(AUTHENTICATION_ID), AccountsConstants.NOT_DELETE);
					if(bankExistByAuthId.equals(Boolean.TRUE)){
						model.put(ERROR_MESSAGE, context.getMessage(JOINT_BANK_ACCOUNT_EXISTWITHAUTHID, null,locale));
						return getView(session, Accounts.ADD_BANK_VIEW);
					} else {
						model.put(ERROR_MESSAGE, context.getMessage(Common.DUPLICATE_BANK_ACCOUNT, null,locale));
						return getView(session, Accounts.ADD_BANK_VIEW);
					}
				}
				//************   JOINT ACCOUNT CHECK CLOSED  ***************//
				
				//************   FRAUD CHECK START-8   ***************//
				Integer hours = utilService.getBankAllowedHours();
				Integer noOfAccountsBank = utilService.getBankAllowedAccounts();
				Date fromDateBank= DateConvertion.getPastDateByHours(hours);
				Boolean flag = fraudCheckService.fundingSourcePatternCheck(addBankAccountForm.getSortCode(), fromDateBank, new Date(), noOfAccountsBank);
				if(flag){
					model.put(ERROR_MESSAGE, context.getMessage(BANK_FRAUD_CHECK_FAILED, null,locale));
					return getView(session, Accounts.ADD_BANK_VIEW);
				}
				//************   FRAUD CHECK END   ***************//
				
				//************   FRAUD CHECK START-2   ***************//
				Boolean  fraudFlag = Boolean.FALSE;
				if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.MERCHANT_USER_TYPE)){
					fraudFlag = accountsService.isExistBankAccount(addBankAccountForm.getAccountNumber(), 
							addBankAccountForm.getSortCode(), GlobalLitterals.MERCHANT_USER_TYPE);
				 if(fraudFlag){
						model.put(ERROR_MESSAGE, context.getMessage(MERCHANT_ALREADY_ADDED_ACCOUNT_NUMBER, null,locale));
					}
				}
				 //************   FRAUD CHECK END   ***************//
				
				Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
				saveBank(request, addBankAccountForm, authId, fraudFlag);
				String userType = (String)session.getAttribute(USER_TYPE);
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.ACCOUNT_MODULE_BANK_CREATE, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), userType);
				if(session.getAttribute(USER_TYPE).equals(GlobalLitterals.MERCHANT_USER_TYPE) && fraudFlag){
					return getView(session, Accounts.ADD_BANK_VIEW);
				}
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex);
				if(ex.getMessage().equals(GlobalLitterals.DB_DUPLICATE_ENTRY)){
					model.put(ERROR_MESSAGE, context.getMessage(Common.DUPLICATE_BANK_ACCOUNT, null,locale));
					return getView(session, Accounts.ADD_BANK_VIEW);
				}
			}
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		}
		return getView(session, Accounts.ADD_BANK_VIEW);
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(HttpServletRequest request, Map model,AddCardAccountForm addCardAccountForm,
			AddBankAccountForm addBankAccountForm, Locale locale) {
		LOGGER.debug("editBank");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		Long id = null;
		try {
			id = Long.valueOf((String)request.getParameter(ID));
			AccountDto account = accountsService.getAccount(id);
			if(account.getDefaultAccount()){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_UNABLE_EDIT_DEFAULT_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
			if(!FundingAccountStatus.NOT_VERIFIED_STATUS.equals(account.getStatus()) && 
					!FundingAccountStatus.VERIFIED_STATUS.equals(account.getStatus())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_VERIFIED_AND_VERIFIED_ACCOUNT_REQUIRED);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
			request.getSession().setAttribute(ID, id);
			String avp = null;
			if( AccountsConstants.BANK_ACCOUNT.equals(account.getAtype()) ){
				addBankAccountForm.convertDtoToBankForm(account);
				model.put(COUNTRY_NAME, MasterDataUtil.getCountryNames(
						request.getSession().getServletContext(), 
						(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(account.getCountry()));
				model.put(COUNTRY_SELECTED, account.getCountry());
				model.put(IS_EDIT_BANK_PAGE, Boolean.TRUE);
				avp = Accounts.EDIT_BANK_VIEW;
			} else {
				addCardAccountForm.setProfileAddress(papulateProfileAddress(request, addCardAccountForm, Boolean.FALSE));
				addCardAccountForm.convertDtoToCardForm(account, Boolean.FALSE, Boolean.TRUE);
				papulateMasterData(request, model, account, account.getCountry());
				addCardAccountForm.setCountryName(MasterDataUtil.getCountryName(request,
						MasterDataUtil.MD_COUNTRIES, account.getCountry()));
				//on edit default option is option two
				addCardAccountForm.setIsSameAsProfileAddress(CommonConstrain.FALSE);
				avp = Accounts.EDIT_CARD_VIEW;
			}
			return getView(session, avp);
		} catch(NumberFormatException ex){
			LOGGER.error(ex.getMessage() , ex );
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		} catch(WalletException ex){
			LOGGER.error(ex.getMessage() , ex );
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		} catch(Exception ex){
			LOGGER.error(ex.getMessage() , ex );
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		}
	}

	@RequestMapping(value = "/editbank", method = RequestMethod.POST)
	public String editBankDetails(HttpServletRequest request, 
			@Valid AddBankAccountForm addBankAccountForm, BindingResult result, 
			Map model, Locale locale) throws WalletException {
		
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String errorMsg = null;
		LOGGER.debug("Entering:: ManageAccountsConroller:: editBankDetails() :: POST ");
		if(addBankAccountForm.getMode() != null && "edit".equals(addBankAccountForm.getMode())) {
			
			Long id = (Long)(request.getSession().getAttribute(ID));
			AccountDto account = accountsService.getAccount(id);
			model.put(COUNTRY_NAME, MasterDataUtil.getCountryNames(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID)).get(account.getCountry()));
			model.put(COUNTRY_SELECTED,account.getCountry());
			model.put(IS_EDIT_BANK_PAGE, Boolean.TRUE);
			bankValidator.validate(addBankAccountForm, result);
			if(result.hasErrors()){
				return getView(session, Accounts.EDIT_BANK_VIEW);
			}
			
			try{
				AccountDto oldAccountDto = (AccountDto)account.clone();
				convertFormtoDtoForBank(addBankAccountForm,account);
				accountsService.updateAccount(account);
				Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
				String userType = (String)session.getAttribute(USER_TYPE);
				
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.ACCOUNT_MODULE_BANK_EDIT, AuditTrailConstrain.STATUS_EDIT, 
						(String)session.getAttribute(USER_ID), userType,oldAccountDto,account);
			} catch(CloneNotSupportedException ex){
				LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, ex);
				errorMsg = "bank.update.errmsg";
				model.put(ERROR_MESSAGE, context.getMessage(errorMsg, null, locale));				
				session.setAttribute(ERROR_MESSAGE, errorMsg);
				return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex );
				if(ex.getMessage().equals(GlobalLitterals.DB_DUPLICATE_ENTRY)){
					errorMsg = Common.DUPLICATE_BANK_ACCOUNT;
				} else{
					errorMsg = "bank.update.errmsg";
				}
				model.put(ERROR_MESSAGE, context.getMessage(errorMsg, null, locale));
				return getView(session, Accounts.EDIT_BANK_VIEW);
			}
			session.setAttribute(ERROR_MESSAGE, errorMsg);
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		}
		return getView(session, Accounts.EDIT_BANK_VIEW);
	}
	
	@RequestMapping(value = "/editcard", method = RequestMethod.POST)
	public String editCardDetails(HttpServletRequest request, 
			@Valid AddCardAccountForm addCardAccountForm, BindingResult result, 
			Map model, Locale locale) throws WalletException {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("Entering:: ManageAccountsConroller:: addBank() :: POST ");
		Long id = (Long)(request.getSession().getAttribute(ID));
		AccountDto account = accountsService.getAccount(id);
		
		/*For PG HTTP service call*/
		String userAgent = request.getHeader(USER_AGENT);
		String clientIpAddress = UIUtil.getClientIpAddr(request);	
		account.setUserAgent(userAgent);
		account.setClientIpAddress(clientIpAddress);
		account.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
		String errorMsg = null;
		AccountDto oldAccountDto = null;
		try{
			oldAccountDto = (AccountDto)account.clone();
		} catch (CloneNotSupportedException ex) {
			LOGGER.error(ex.getMessage() , ex );
			errorMsg = AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR;
		}
		Address profileAddress = getProfileAddress(request);
		addCardAccountForm.setProfileAddress(papulateProfileAddress(request, addCardAccountForm, Boolean.FALSE));
		addCardAccountForm.setStatus(account.getStatus());
		
		if(addCardAccountForm.getMode() != null && "edit".equals(addCardAccountForm.getMode())) {
			if(account.getStatus().equals(CARD_STATUS_VARIFIED)){
				cardValidator.validate(addCardAccountForm, result);
				if(result.hasErrors()){
					if(addCardAccountForm.getIsSameAsProfileAddress()){
						addCardAccountForm.convertDtoToCardForm(account, Boolean.TRUE, Boolean.TRUE);
					} else{
						addCardAccountForm.convertDtoToCardForm(account, Boolean.TRUE, Boolean.FALSE);
					}
					papulateMasterData(request, model, account, addCardAccountForm.getCountry());
					addCardAccountForm.setCountryName(MasterDataUtil.getCountryName(request,
								MasterDataUtil.MD_COUNTRIES, addCardAccountForm.getCountry()));
					return getView(session, Accounts.EDIT_CARD_VIEW);
				} else{
					account.setCvv(addCardAccountForm.getCvv());
					account.setCardExpairyDate( addCardAccountForm.getExpireDateMonth() + "/" + addCardAccountForm.getExpireDateYear() );
					if(addCardAccountForm.getIsSameAsProfileAddress()){
						papulateProfileAddressToDto(account, profileAddress);
					} else{
						addCardAccountForm.convertCardFormToDtoForAddress(account);
					}
				}
			} else{
				cardValidator.validate(addCardAccountForm, result);
				if(result.hasErrors()){
					if(addCardAccountForm.getIsSameAsProfileAddress()){
						addCardAccountForm.convertDtoToCardFormForAddress(account);
					}
					papulateMasterData(request, model, account, addCardAccountForm.getCountry());
					addCardAccountForm.setCountryName(MasterDataUtil.getCountryName(request,
							MasterDataUtil.MD_COUNTRIES, addCardAccountForm.getCountry()));
					return getView(session, Accounts.EDIT_CARD_VIEW);
				} else{
					account.setCvv(addCardAccountForm.getCvv());
					convertFormtoDtoForCard(addCardAccountForm, account, profileAddress);
				}
			}
			try {
				account.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
						account.getCountry()));
				Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), addCardAccountForm.getCountry());
				account.setCurrency(defultCurrency);
				AccountDto rstDto = accountsService.updateAccount(account);
				
				Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
				String userType = (String)session.getAttribute(USER_TYPE);
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.ACCOUNT_MODULE_CARD_EDIT, AuditTrailConstrain.STATUS_EDIT, 
						(String)session.getAttribute(USER_ID), userType, oldAccountDto, account);
				UIUtil.papulatePgResponseCode(session, context, model, locale, rstDto.getPgResponseCode());
				if(HttpServiceConstants.RESPONSE_DECISION_SUCCESS.equals(rstDto.getPgResponseCode())){
					session.setAttribute(SUCCESS_MESSAGE, Common.CARD_UPDATED_SUCCESS_MSG);
				}
			} catch(NullPointerException e){
				LOGGER.error(e.getMessage(), e);
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex );
				if(ex.getMessage().equals(GlobalLitterals.DB_DUPLICATE_ENTRY)){
					errorMsg = Common.DUPLICATE_CARD_ACCOUNT;
				} else if(ex.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
					errorMsg = ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH;
				} else if(ex.getMessage().equals(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT)){
					errorMsg = ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT;
				} else{
					errorMsg = "card.update.errmsg";
				}
				if(account.getStatus().equals(CARD_STATUS_VARIFIED)){
					addCardAccountForm.setAccountHolderName(account.getAccountHolderName());
					addCardAccountForm.setCardType(account.getCardType());
					addCardAccountForm.setCardNumber(account.getCardNumber());
				}
				papulateMasterData(request, model, account, addCardAccountForm.getCountry());
				addCardAccountForm.setCountryName(MasterDataUtil.getCountryName(request,
						MasterDataUtil.MD_COUNTRIES, addCardAccountForm.getCountry()));
				//on edit default option is option two
				addCardAccountForm.setIsSameAsProfileAddress(CommonConstrain.FALSE);
				model.put(ERROR_MESSAGE, context.getMessage(errorMsg, null,locale));
				return getView(session, Accounts.EDIT_CARD_VIEW);
			}
			
			model.put(ACCOUNT_STATUS, account.getStatus());
			if(errorMsg != null){
				session.setAttribute(ERROR_MESSAGE, errorMsg);
			}
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		}
		return getView(session, Accounts.EDIT_CARD_VIEW);
	}
	
	@RequestMapping(value = "/addcard", method = RequestMethod.GET)
	public String addCard(HttpServletRequest request, AddCardAccountForm addCardAccountForm, Map model, Locale locale) {
		LOGGER.debug(" addCard ");
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		
		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		String eMsg = accountsCountVerification(request);
		if(eMsg != null){
			if(SESSION_EXPIRED_MSG.equals(eMsg)){
				return Login.VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
			} else {
				session.setAttribute(ERROR_MESSAGE, eMsg);
				return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
			}
		}
		
		getView(session, ACC_MANAGEMENT_VIEW);
		addCardAccountForm.setProfileAddress(papulateProfileAddress(request, addCardAccountForm, Boolean.TRUE));
		papulateMasterData(request, model, null, addCardAccountForm.getCountry());
		//on add option one is default
		addCardAccountForm.setIsSameAsProfileAddress(CommonConstrain.TRUE);
		return getView(session, Accounts.ADD_CARD_VIEW);
	}
   
	private String accountsCountVerification(HttpServletRequest request){
		String eMsg = null;
		try {
			
			Long authId = Long.valueOf(request.getSession().getAttribute(AUTHENTICATION_ID).toString());
			if(authId == null){
				eMsg = SESSION_EXPIRED_MSG;
			} else if(accountsService.getAccountsCount(authId) >=  utilService.getMaxFundResources()){
				eMsg = ACCOUNTS_SIZE_EXCEEDS_ERROR_MESSAGE;
			}
		} catch (Exception ex){
			LOGGER.error(ex.getMessage() , ex ); 
			eMsg = ACCOUNTS_SIZE_UNKNOWN_ERROR_MESSAGE;
		}
		return eMsg;
	}
	
	@RequestMapping(value = "/addcard", method = RequestMethod.POST)
	public String addCardDetails(HttpServletRequest request,HttpServletResponse response,
			@Valid AddCardAccountForm addCardAccountForm,BindingResult result, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		LOGGER.debug("addCardDetails :: POST ");
		String eMsg = accountsCountVerification(request);
		if(eMsg != null){
			if(SESSION_EXPIRED_MSG.equals(eMsg)){
				return Login.VIEW_RESOLVER_SESSION_EXPIRED_CUSTOMER;
			} else {
				model.put(ERROR_MESSAGE, context.getMessage(eMsg, null, locale));
				return getView(session, ACC_MANAGEMENT_VIEW);
			}
		}

		addCardAccountForm.setStatus(FundingAccountStatus.NOT_VERIFIED_STATUS);
		if(addCardAccountForm.getMode() != null && "add".equals(addCardAccountForm.getMode())) {
			cardValidator.validate(addCardAccountForm, result);
			if(result.hasErrors()){
				addCardAccountForm.setProfileAddress(papulateProfileAddress(request, addCardAccountForm, Boolean.TRUE));
				papulateMasterData(request, model, null, addCardAccountForm.getCountry());
				return getView(session, Accounts.ADD_CARD_VIEW);
			}
			try{
				//************   JOINT ACCOUNT CHECK START  ***************//
				Integer totalExistCardAccouts = accountsService.getTotalCardAccounts(addCardAccountForm.getCardNumber(), 
						(String)session.getAttribute(USER_TYPE), AccountsConstants.NOT_DELETE);	
				if(session.getAttribute(USER_TYPE).equals(CUSTOMER_USER_TYPE)){
					if(!totalExistCardAccouts.equals(GlobalLitterals.ZERO_INTEGER)){
						if(addCardAccountForm.getJointAccount()){
							if(totalExistCardAccouts.intValue() >= utilService.getJointCardAccountsLimit()){
								model.put(ERROR_MESSAGE, context.getMessage(JOINT_CARD_ACCOUNT_EXCEEDED, null,locale));
								papulateMasterData(request, model, null, addCardAccountForm.getCountry());
								return getView(session, Accounts.ADD_CARD_VIEW);
							} else {
								Boolean cardExistByAuthId = accountsService.cardAccountExistWithSameUser(addCardAccountForm.getCardNumber(), 
										(Long)session.getAttribute(AUTHENTICATION_ID), AccountsConstants.NOT_DELETE);
								if(cardExistByAuthId.equals(Boolean.TRUE)){
									model.put(ERROR_MESSAGE, context.getMessage(JOINT_CARD_ACCOUNT_EXISTWITHAUTHID, null,locale));
									papulateMasterData(request, model, null, addCardAccountForm.getCountry());
									return getView(session, Accounts.ADD_CARD_VIEW);
								}
							}
						} else {
							model.put(ERROR_MESSAGE, context.getMessage(Common.DUPLICATE_CARD_ACCOUNT, null,locale));
							papulateMasterData(request, model, null, addCardAccountForm.getCountry());
							return getView(session, Accounts.ADD_CARD_VIEW);
						}
					}
				} else if(session.getAttribute(USER_TYPE).equals(MERCHANT_USER_TYPE) && !totalExistCardAccouts.equals(GlobalLitterals.ZERO_INTEGER)){			
					Boolean cardExistByAuthId = accountsService.cardAccountExistWithSameUser(addCardAccountForm.getCardNumber(), 
							(Long)session.getAttribute(AUTHENTICATION_ID), AccountsConstants.NOT_DELETE);
					if(cardExistByAuthId.equals(Boolean.TRUE)){
						model.put(ERROR_MESSAGE, context.getMessage(JOINT_CARD_ACCOUNT_EXISTWITHAUTHID, null,locale));
						papulateMasterData(request, model, null, addCardAccountForm.getCountry());
						return getView(session, Accounts.ADD_CARD_VIEW);
					} else {
						model.put(ERROR_MESSAGE, context.getMessage(Common.DUPLICATE_CARD_ACCOUNT, null,locale));
						papulateMasterData(request, model, null, addCardAccountForm.getCountry());
						return getView(session, Accounts.ADD_CARD_VIEW);
					}
				}
				//************   JOINT ACCOUNT CHECK CLOSED  ***************//
				//************   FRAUD CHECK START -8 ***************//
				Integer hours = utilService.getCardAllowedHours();
				Integer noOfAccountsCard = utilService.getCardAllowedAccounts();
				Date fromDateCard = DateConvertion.getPastDateByHours(hours);
				Boolean flag = fraudCheckService.fundingSourcePatternCheckCard(addCardAccountForm.getCardNumber().substring(0, CARD_BIN_LENGTH), 
						fromDateCard, new Date(), noOfAccountsCard);
				if(flag){
					model.put(ERROR_MESSAGE, context.getMessage("card.fraud.check.failed", null,locale));
					return getView(session, Accounts.ADD_BANK_VIEW);
				}
				//************   FRAUD CHECK END   ***************//
				
				Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
				Address profileAddress = getProfileAddress(request);
				AccountDto rstDto = saveCard(request, addCardAccountForm, profileAddress, authId);
				//Audit Trail service
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.ACCOUNT_MODULE_CARD_CREATE, 
						AuditTrailConstrain.STATUS_CREATE, (String)session.getAttribute(USER_ID), 
						(String)session.getAttribute(USER_TYPE));
				if(!AccountsConstants.OLD_ACCOUNT.equals(rstDto.getPgResponseCode())){
					UIUtil.papulatePgResponseCode(session, context, model, locale, rstDto.getPgResponseCode());
					if(HttpServiceConstants.RESPONSE_DECISION_SUCCESS.equals(rstDto.getPgResponseCode())){
						session.setAttribute(SUCCESS_MESSAGE, Common.CARD_REGISTER_SUCCESS_MSG);
					}
				}
			} catch(NullPointerException e){
				LOGGER.error(e.getMessage(), e);
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
			} catch(Exception ex){
				LOGGER.error(ex.getMessage() , ex );
				addCardAccountForm.setProfileAddress(papulateProfileAddress(request, addCardAccountForm, Boolean.TRUE));
				papulateMasterData(request, model, null, addCardAccountForm.getCountry());
				if(ex.getMessage().equals(GlobalLitterals.DB_DUPLICATE_ENTRY)){
					eMsg = Common.DUPLICATE_CARD_ACCOUNT;
				} else if(ex.getMessage().equals(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH)){
					eMsg = ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH;
				} else if(ex.getMessage().equals(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT)){
					eMsg = ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT;
				} else{
					eMsg = "card.save.errmsg";
				}
				model.put(ERROR_MESSAGE, context.getMessage(eMsg, null, locale));
				return getView(session, Accounts.ADD_CARD_VIEW);
			}
			return REDIRECT_PREFIX + getView(session, Accounts.ACC_MANAGEMENT_VIEW);
		}
		return getView(session, Accounts.ADD_CARD_VIEW);

    }
	
	@RequestMapping(value = "/notverified", method = RequestMethod.GET)
	public String notVerifiedStatusView(HttpServletRequest request, @Valid NotVerifiedForm notVerifiedForm, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		Long id = Long.valueOf(request.getParameter(ID));
		try {
			AccountDto dto = accountsService.getAccount(id);
			if(null == dto){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
			if(!FundingAccountStatus.NOT_VERIFIED_STATUS.equals(dto.getStatus())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_VERIFIED_STATUS_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
			
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e);
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
			return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
		}
		// return not verified page
		notVerifiedForm.setAccountId(id);
		model.put("notVerifiedForm", notVerifiedForm);
		return getView(session, Accounts.NON_VERIFIED_CARD_VIEW);
	}
	
	@RequestMapping(value = "/notverified", method = RequestMethod.POST)
	public String notVerifiedStatus(HttpServletRequest request, Locale locale,
			@Valid NotVerifiedForm notVerifiedForm, Map model) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		try{
			String userAgent = request.getHeader(USER_AGENT);
			String clientIpAddress = UIUtil.getClientIpAddr(request);
			AccountDto dto = accountsService.getAccount(notVerifiedForm.getAccountId());
			dto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
			dto.setId(notVerifiedForm.getAccountId());
			dto.setCvv(notVerifiedForm.getCvvNumber());
			dto.setUserAgent(userAgent);
			dto.setClientIpAddress(clientIpAddress);
			dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					dto.getCountry()));
			Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), dto.getCountry());
			dto.setCurrency(defultCurrency);
			AccountDto rstDto = accountsService.varifyCard(dto);
			UIUtil.papulatePgResponseCode(session, context, model, locale, rstDto.getPgResponseCode());
			if(HttpServiceConstants.RESPONSE_DECISION_SUCCESS.equals(rstDto.getPgResponseCode())){
				session.setAttribute(SUCCESS_MESSAGE, Common.CARD_REGISTER_SUCCESS_MSG);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			model.put(ERROR_MESSAGE, context.getMessage(CARD_VARIFICATION_FAILED, null, locale));
			return getView(session, NON_VERIFIED_CARD_VIEW);
		}
		String url = WALLET_PATH_PREFIX + "manageaccounts/accountrecords";
		request.setAttribute(URLACCOUNT_LIST , url);
		return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
	}
	
	@RequestMapping(value = "/pending", method = RequestMethod.GET)
	public String pendingStatusView(HttpServletRequest request, @Valid PendingForm pendingForm, Map model, Locale locale) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		String errorCode = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorCode != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorCode, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}

		Long id = Long.valueOf(request.getParameter(ID));
		AccountDto dto;
		try {
			dto = accountsService.getAccount(id);
			if(null == dto){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
			if(!FundingAccountStatus.PENDING_STATUS.equals(dto.getStatus())){
				session.setAttribute(ERROR_MESSAGE, ERROR_MSG_NOT_PENDING_STATUS_ACCOUNT);
				return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
			}
		} catch (WalletException e) {
			LOGGER.error(e.getMessage() , e );
			session.setAttribute(ERROR_MESSAGE, ERROR_MSG_FAILS_TO_GET_ACCOUNT);
			return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
		}
		
		pendingForm.setAccountId(id);
		model.put("pendingForm", pendingForm);
		return getView(session, PENDING_CARD_VIEW);
	}
	
	@RequestMapping(value = "/pending", method = RequestMethod.POST)
	public String pendingStatus(HttpServletRequest request, Locale locale,
			@Valid PendingForm pendingForm, Map model) {
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, CUSTOMER_USER_TYPE, MERCHANT_USER_TYPE)){
			return UIUtil.redirectPath(HOME_LOGIN_PATH);
		}
		try{
			String userAgent = request.getHeader(USER_AGENT);
			String clientIpAddress = UIUtil.getClientIpAddr(request);
			AccountDto dto = accountsService.getAccount(pendingForm.getAccountId());
			dto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
			dto.setOrderId(pendingForm.getCode());
			dto.setTransactionAmount(pendingForm.getAmount());
			dto.setId(pendingForm.getAccountId());
			dto.setUserAgent(userAgent);
			dto.setClientIpAddress(clientIpAddress);
			dto.setCurrencyCode(MasterDataUtil.getCountryCurrencyCode(request.getSession().getServletContext(), 
					dto.getCountry()));
			String userType=(String)session.getAttribute(USER_TYPE);
			if(userType.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)){
				dto.setTransactionType(WalletTransactionTypes.PENNY_DROP_FROM_CARD_CUSTOMER);	
			}else{
				dto.setTransactionType(WalletTransactionTypes.PENNY_DROP_FROM_CARD_MERCHANT);	
			}
			Long defultCurrency = MasterDataUtil.getCountryCurrencyId(request.getSession().getServletContext(), dto.getCountry());
			dto.setCurrency(defultCurrency);
			AccountDto rstDto = accountsService.varifyCard(dto);
			UIUtil.papulatePgResponseCode(session, context, model, locale, rstDto.getPgResponseCode());

		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e );
			String errorMessage = null;
			if(e.getMessage().contains(AccountsConstants.ERROR_MSG_WRONG_INFO)){
				errorMessage = ERROR_MSG_WRONG_CARD_INFO;
			} else{
				errorMessage = CARD_VARIFICATION_FAILED;
			}
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			return getView(session, PENDING_CARD_VIEW);
		}
		String url = WALLET_PATH_PREFIX + "manageaccounts/accountrecords";
		request.setAttribute(URLACCOUNT_LIST , url);
		return REDIRECT_PREFIX + getView(session, ACC_MANAGEMENT_VIEW);
	}
	
	private AccountDto saveCard(HttpServletRequest request, AddCardAccountForm addCardAccountForm, 
			Address profileAddress, Long authId) throws WalletException{
		
		AccountDto dto = new AccountDto();
		dto = ManageAccountsHelper.getAccountDto(request, addCardAccountForm, profileAddress, authId, TypeOfRequest.WEB);
		return accountsService.createAccount(dto);
	}
	
	private void saveBank(HttpServletRequest request, AddBankAccountForm addBankAccountForm, 
			Long authId, Boolean fraudFlag) throws WalletException{
        AccountDto dto = new AccountDto();
		dto.setAuthId(authId);
		dto.setAccountHolderName(addBankAccountForm.getAccountHolderName());
		dto.setStatus(FundingAccountStatus.NOT_VERIFIED_STATUS);
		dto.setDefaultAccount(Boolean.FALSE);
		dto.setDeletedStatus(AccountsConstants.NOT_DELETE);
		dto.setAtype(AccountsConstants.BANK_ACCOUNT);
		dto.setJointAccount(addBankAccountForm.getJointAccount());
		dto.setCountry(addBankAccountForm.getCountry());
		dto.setBankAccountType(addBankAccountForm.getAccountType());
		dto.setSortCode(addBankAccountForm.getSortCode());
		dto.setBankName(addBankAccountForm.getBankName());
		dto.setAccountNumber(addBankAccountForm.getAccountNumber());
		dto.setBankAddress(addBankAccountForm.getBankAddress());
		dto.setHoldBank(fraudFlag);
		dto.setTypeOfRequest(MasterDataUtil.getTypeOfRequest(request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), TypeOfRequest.WEB.getValue()));
		accountsService.createAccount(dto);
	}
	
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
	
	private void convertFormtoDtoForCard(AddCardAccountForm addCardAccountForm, AccountDto account, Address profileAddress){
		account.setAccountHolderName(addCardAccountForm.getAccountHolderName());
		account.setCardType(addCardAccountForm.getCardType());
		account.setCardNumber(addCardAccountForm.getCardNumber());
		account.setCardExpairyDate(addCardAccountForm.getExpireDateMonth() + "/" + addCardAccountForm.getExpireDateYear());
		account.setCvv(addCardAccountForm.getCvv());
		if(addCardAccountForm.getIsSameAsProfileAddress()){
			papulateProfileAddressToDto(account, profileAddress);
		} else{
			addCardAccountForm.convertCardFormToDtoForAddress(account);
		}
	}
	
	private void convertFormtoDtoForBank(AddBankAccountForm addBankAccountForm,AccountDto account){
		account.setCountry(addBankAccountForm.getCountry());
		account.setAccountHolderName(addBankAccountForm.getAccountHolderName());
		account.setBankName(addBankAccountForm.getBankName());
		account.setBankAccountType(addBankAccountForm.getAccountType());
		account.setSortCode(addBankAccountForm.getSortCode());
		account.setAccountNumber(addBankAccountForm.getAccountNumber());
		account.setBankAddress(addBankAccountForm.getBankAddress());
	}

	private void papulateMasterData(HttpServletRequest request, Map model, AccountDto account, Long countryId){
		
		if(account != null){
			model.put(ACCOUNT_STATUS, account.getStatus());
			model.put(CARD_TYPE, MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.MD_CARD_TYPES).get(account.getCardType()));
		}
		
		model.put(MONTHS_LIST, MerchantDataUtil.getMonthMap());
		model.put(CARD_ISSUE_DATE_LIST, MerchantDataUtil.getIssueDateYearMap());
		model.put(CARD_EXP_DATE_LIST, MerchantDataUtil.getExpiryDateYearMap());
		model.put(CARD_TYPES_LIST, MasterDataUtil.getSimpleDataMap(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), 
				MasterDataUtil.MD_CARD_TYPES));
		model.put(COUNTRY_LIST, MasterDataUtil.getCountryNames(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID)));
		if(countryId != null){
		model.put(STATE_LIST, MasterDataUtil.getRegions(
				request.getSession().getServletContext(), 
				(Long) request.getSession().getAttribute(LANGUAGE_ID), countryId));
		}
	}
	
	private String papulateProfileAddress(HttpServletRequest request, AddCardAccountForm addCardAccountForm, 
			Boolean isCountryNameRequired){
		String profileAddress = "";
		Address address = getProfileAddress(request);
		if(address != null){
			if(isCountryNameRequired){
				addCardAccountForm.setCountry(address.getCountry());
				addCardAccountForm.setCountryName(MasterDataUtil.getCountryName(request,
						MasterDataUtil.MD_COUNTRIES, address.getCountry()));
			}
			profileAddress = papulateProfileAddress(request, address);
		}
		return profileAddress;
	}
	
	private Address getProfileAddress(HttpServletRequest request){
		HttpSession session = request.getSession();
		Address profileAddress = null;
		String userType = (String) session.getAttribute(USER_TYPE);
		try {
			if(userType.equalsIgnoreCase(GlobalLitterals.CUSTOMER_USER_TYPE)){
				Long customerId = (Long) session.getAttribute(DB_ID);
				profileAddress = customerService.getCustomerAddress(customerId);
			} else{
				Long merchantId = (Long) session.getAttribute(DB_ID);
				profileAddress = merchantService.getMerchantAddress(merchantId);
			}
		} catch(Exception e){
			LOGGER.error(" addCard :: unable to retrive profile address details", e);
		}
		return profileAddress;
	}
	
	private String papulateProfileAddress(HttpServletRequest request, Address address){
		StringBuffer strBufAddress = new StringBuffer();
		
		strBufAddress.append(address.getAddressOne());
		strBufAddress.append("\n");
		strBufAddress.append(checkAddressNullValues(address.getAddressTwo()));
		strBufAddress.append(address.getCity());
		strBufAddress.append("\n");
		String abc = MasterDataUtil.getRegions(request.getSession().getServletContext(), 
				(Long) request.getSession(Boolean.FALSE).getAttribute(LANGUAGE_ID), 
				address.getCountry()).get(address.getRegion());
		if(abc != null){
			strBufAddress.append(abc);
			strBufAddress.append("\n");
		}
		strBufAddress.append(MasterDataUtil.getCountryName(request,
				MasterDataUtil.MD_COUNTRIES, address.getCountry()));
		strBufAddress.append("\n");
		strBufAddress.append(checkAddressNullValues(address.getZipcode()));
		return strBufAddress.toString();
	}
	
	private String checkAddressNullValues(String value){
		if(value != null){
			return value + "\n";
		}
		return EMPTY_STRING;
	}
	
	private void papulateProfileAddressToDto(AccountDto account, Address profileAddress){
		account.setCountry(profileAddress.getCountry());
		account.setAddrOne(profileAddress.getAddressOne());
		account.setAddrTwo(profileAddress.getAddressTwo());
		account.setCity(profileAddress.getCity());
		account.setState(profileAddress.getRegion());
		account.setPostalCode(profileAddress.getZipcode());
	}
}