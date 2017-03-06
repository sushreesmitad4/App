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
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.dto.ReconcileDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.reconcile.business.ReconcileService;
import com.tarang.ewallet.reconcile.util.ReconcileStatusConstants;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.walletui.controller.constants.ReconciliationConstants;
import com.tarang.ewallet.walletui.form.ReconciliationForm;
import com.tarang.ewallet.walletui.form.SearchUserForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.common.Common;


/**
 * @author sivaramar
 * @common This is to adjust amount for reconciliation by Admin in Admin reconcile module
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Controller
@RequestMapping("/reconciliation")
public class ReconciliationController implements ReconciliationConstants, AttributeConstants, AttributeValueConstants {
	
	private static final Logger LOGGER = Logger.getLogger(ReconciliationController.class);
	
	@Autowired
	private ReconcileService reconcileService;
	
	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showReconciliationPage(@Valid SearchUserForm searchUserForm, Map model, HttpServletRequest request, Locale locale) throws WalletException{
		LOGGER.debug( " Reconciliation dispute default page" );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.RECONCILIATION, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String sucMessage = (String)session.getAttribute(SUCCESS_MESSAGE);
		
		if(sucMessage != null){
			model.put(SUCCESS_MESSAGE, sucMessage);
			session.removeAttribute(SUCCESS_MESSAGE);
		}
		String search = request.getParameter(SEARCH);
		String url = null;
		if(search != null){
			String fromDate = (String)request.getParameter(F_DATE);
			String toDate = (String)request.getParameter(T_DATE);
			searchUserForm.setFromDate(fromDate);
			searchUserForm.setToDate(toDate);
			model.put("searchUserForm", searchUserForm);
			url = WALLET_PATH_PREFIX + "/reconciliation/list?search=true&fdate="+fromDate+"&tdate="+toDate;
		} else {
			url = WALLET_PATH_PREFIX + "/reconciliation/list?search=false";
		}
		request.setAttribute("urlReconciliationList", url);
		return RECONCILIATION_GRID;
	}
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = JSON_HEADER, produces = JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<ReconcileDto> disputeRecords( @Valid ReconciliationForm reconciliationForm, Locale locale, HttpServletRequest request) {
		List<ReconcileDto> displayList = null;
		String search = request.getParameter(SEARCH);
		JqgridResponse<ReconcileDto> response = new JqgridResponse<ReconcileDto>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}	
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.RECONCILIATION, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }
		if(search != null && "true".equals(search)){
			try{
				String fromDate = (String)request.getParameter(F_DATE);
				String toDate = (String)request.getParameter(T_DATE);
				displayList = reconcileService.getReconcileRecords(DateConvertion.getFromDate(DateConvertion.stirngToDate(fromDate)), DateConvertion.getToDate(DateConvertion.stirngToDate(toDate)),ReconcileStatusConstants.PENDING );
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
		} else if(search != null && "false".equals(search)){
			displayList = new ArrayList<ReconcileDto>();
		}
		response.setRows(displayList);
		int ps = DEFAULT_PAGE_SIZE;
		int n = displayList.size()/ps;
		if( displayList.size()/ps*ps != displayList.size()){
			n++;
		}		response.setTotal(EMPTY_STRING + n);
		response.setPage(EMPTY_STRING + 1);

		return response;
	}
	
	@RequestMapping(value = "/adjustAmount", method=RequestMethod.GET)
	public String amountAdjustmentForReconciliation(@Valid ReconciliationForm reconciliationForm, HttpServletRequest request) throws WalletException{
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.RECONCILIATION, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String fromDate = request.getParameter(F_DATE);
		String toDate = request.getParameter(T_DATE);
		Long id = Long.parseLong(request.getParameter("id"));
		ReconcileDto reconcileDto = reconcileService.getReconcileRecordById(id);
		convertReconcileDtoToReconciliationForm(reconcileDto, reconciliationForm);
		reconciliationForm.setFromDate(fromDate);
		reconciliationForm.setToDate(toDate);
		return RECONCILIATION_UPDATE_PAGE;
	}
	
	@RequestMapping(value = "/saveAdjustedAmount", method=RequestMethod.POST)
	public String saveAdjustmentedAmountForReconciliation(@Valid ReconciliationForm reconciliationForm, HttpServletRequest request, Locale locale){
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.RECONCILIATION, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		Long reconcileId = reconciliationForm.getId();
		Double finalAmount = reconciliationForm.getSelectedAmount();
		String mode = reconciliationForm.getMode();
		String fromDate = reconciliationForm.getFromDate();
		String toDate = reconciliationForm.getToDate();
		String url = "?fdate="+fromDate+"&tdate="+toDate+"&search=true";
		if("proceed".equals(mode)){
			try{
				reconcileService.updateReconcile(reconcileId, finalAmount, ReconcileStatusConstants.CLOSED);
				session.setAttribute(SUCCESS_MESSAGE, context.getMessage(RECONCILIATION_UPDATE_SUCCESS, null, locale));
			} catch(Exception e){
				session.setAttribute(ERROR_MESSAGE, context.getMessage(RECONCILIATION_UPDATE_FAILED, null, locale));
				LOGGER.error(e.getMessage(), e);
			}
		}
		return UIUtil.redirectPath(RECONCILIATION_PATH + url);
	}
	
	private void  convertReconcileDtoToReconciliationForm(ReconcileDto reconcileDto,ReconciliationForm reconciliationForm){
		
		reconciliationForm.setId(reconcileDto.getId());
		reconciliationForm.setActualAmount(reconcileDto.getActualAmount());
		reconciliationForm.setPgTxnId(reconcileDto.getPgTxnId());
		reconciliationForm.setScheme(reconcileDto.getScheme());
		reconciliationForm.setPurchaseDate(reconcileDto.getPurchaseDate());
		reconciliationForm.setPurchaseTime(reconcileDto.getPurchaseTime());
		reconciliationForm.setTxnAmount(reconcileDto.getTxnAmount());
		reconciliationForm.setFinalAmount(reconcileDto.getFinalAmount());
		reconciliationForm.setTxnCurrency(reconcileDto.getTxnCurrency());
		reconciliationForm.setCreationDate(reconcileDto.getCreationDate());
		reconciliationForm.setUpdateDate(reconcileDto.getUpdateDate());
		reconciliationForm.setStatus(reconcileDto.getStatus());
	}
}
