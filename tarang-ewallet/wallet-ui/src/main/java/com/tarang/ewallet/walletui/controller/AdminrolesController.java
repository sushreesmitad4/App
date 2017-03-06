/**
 * 
 */
package com.tarang.ewallet.walletui.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tarang.ewallet.audittrail.business.AuditTrailService;
import com.tarang.ewallet.audittrail.util.AuditTrailConstrain;
import com.tarang.ewallet.common.util.CommonConstrain;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Role;
import com.tarang.ewallet.usermgmt.business.UserService;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.util.QueryFilter;
import com.tarang.ewallet.walletui.controller.constants.Admin;
import com.tarang.ewallet.walletui.form.RoleForm;
import com.tarang.ewallet.walletui.util.JqgridResponse;
import com.tarang.ewallet.walletui.util.MenuConstants;
import com.tarang.ewallet.walletui.util.MenuUtils;
import com.tarang.ewallet.walletui.util.UIUtil;
import com.tarang.ewallet.walletui.validator.RoleValidator;
import com.tarang.ewallet.walletui.validator.UserValidator;
import com.tarang.ewallet.walletui.validator.common.Common;



/**
* @author  : prasadj
* @date    : Oct 8, 2012
* @time    : 8:52:17 AM
* @version : 1.0.0
* @comments: AdminrolesController is a controller class for manage admin roles 
* 			 by admin user
*/
@SuppressWarnings({"unchecked", "rawtypes"})
@Controller
@RequestMapping("/adminroles")
public class AdminrolesController implements Admin, AttributeConstants, AttributeValueConstants {

	private static final Logger LOGGER = Logger.getLogger(AdminrolesController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuditTrailService auditTrailService;
	
	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(method = RequestMethod.GET)
	public String rolesListPage(HttpServletRequest request, Map model, Locale locale){
		
		LOGGER.debug( " rolesListPage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		String errorMessage = (String)session.getAttribute(ERROR_MESSAGE);
		if(errorMessage != null){
			model.put(ERROR_MESSAGE, context.getMessage(errorMessage, null, locale));
			session.removeAttribute(ERROR_MESSAGE);
		}
		return ROLES_VIEW;
	}

	@RequestMapping(value="/addrole", method = RequestMethod.GET)
	public String addRolePage(Map model, @Valid RoleForm roleForm, HttpServletRequest request){
		
		LOGGER.debug( " addRolePage " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
		return ROLE_ADD_VIEW;
	}

	@RequestMapping(value="/addrole", method = RequestMethod.POST)
	public String addRole(Map model, @Valid RoleForm roleForm, BindingResult result, HttpServletRequest request,Locale locale){
		
		LOGGER.debug( " addRole  POST " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
	    String userId = (String)session.getAttribute(USER_ID);
		Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
		String viewPage = ROLE_ADD_VIEW;
		try{
			
			RoleValidator roleValidator = new RoleValidator();
			roleValidator.validate(roleForm, result);
			if(!result.hasErrors()){
				Role role  = new Role();
				role.setName(roleForm.getName());
				role.setDescription(roleForm.getDescription());
				role.setMenuDetails(roleForm.getMenuDetails());
				roleForm.setNameHidden(role.getName());
				roleForm.setDecHidden(role.getDescription());
				addRole(role);
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.MODULE_ROLE_CREATION, 
						AuditTrailConstrain.STATUS_CREATE, userId, GlobalLitterals.ADMIN_USER_TYPE);
				viewPage = ROLES_VIEW;
			}
		} catch (org.springframework.dao.DataIntegrityViolationException ex) {
			LOGGER.error(ex.getMessage() , ex );
			result.rejectValue(RoleValidator.NAME_VAR, RoleValidator.NAME_UNIQUE);
		} catch (Exception ex) {
			LOGGER.error("add role page", ex);
			if(ex.getMessage().contains(CommonConstrain.DUPLICATE_ENTRY)){
				result.rejectValue(RoleValidator.NAME_VAR, RoleValidator.NAME_UNIQUE);
			} else {
				result.rejectValue(RoleValidator.NAME_VAR, RoleValidator.NAME_UNIQUE);
			}
		}
		model.put(SUCCESS_MESSAGE, context.getMessage(ADD_ROLE_SUCCESS_MSG, null, locale));
		return viewPage;
	}
	
	private void addRole(Role role) throws WalletException {
		LOGGER.debug( " addRole " );
		userService.createRole(role);
	}
	
	@RequestMapping(value="/editrole", method = RequestMethod.GET)
	public String editRolePage(Map model, @Valid RoleForm roleForm, Long rid, HttpServletRequest request){
		
		LOGGER.debug( " editRolePage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }				
		try{
			Role role = userService.getRole(rid);
			roleForm.setId(role.getId());
			roleForm.setName(role.getName());
			roleForm.setDescription(role.getDescription());
			roleForm.setMenuDetails(role.getMenuDetails());
			roleForm.setNameHidden(role.getName());
			roleForm.setDecHidden(role.getDescription());
		} catch(Exception ex){
			LOGGER.error("edit role page", ex);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_ROLE_PATH);
		}
		return ROLE_EDIT_VIEW;
	}
	
	@RequestMapping(value="/viewrole", method = RequestMethod.GET)
	public String viewRolePage(Map model, @Valid RoleForm roleForm, Long rid, HttpServletRequest request){
		
		LOGGER.debug( " viewRolePage " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }							
		try{
			Role role = userService.getRole(rid);
			roleForm.setId(role.getId());
			roleForm.setName(role.getName());
			roleForm.setDescription(role.getDescription());
			roleForm.setMenuDetails(role.getMenuDetails());
			roleForm.setNameHidden(role.getName());
			roleForm.setDecHidden(role.getDescription());
			
		} catch(Exception ex){
			LOGGER.error("view role page", ex);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_ROLE_PATH);
		}
		return ROLE_VIEW_PAGE;
	}
	
	@RequestMapping(value="/deleterole", method = RequestMethod.GET)
	public String deleterole(Map model, @Valid RoleForm roleForm, Long rid, HttpServletRequest request,Locale locale){
		
		LOGGER.debug( " delete Role  " );
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		HttpSession session = request.getSession();
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }				
		String viewpage=ROLES_VIEW;
		try{
			Role role = userService.getRole(rid);
			Boolean flag=userService.roleExistInAdminUser(rid);
			if(flag){
				model.put(ERROR_MESSAGE, context.getMessage(DELETE_ROLE_ERR_MSG, null, locale));
				return viewpage;
			}
			model.put(SUCCESS_MESSAGE, context.getMessage(DELETE_ROLE_SUCCESS_MSG, null, locale));
			
			userService.deleteRole(role);
			//Audit Trail
			auditTrailService.createAuditTrailForRole(Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString()), 
					AuditTrailConstrain.MODULE_ROLE_DELETED, AuditTrailConstrain.STATUS_DELETE, 
					session.getAttribute(USER_ID).toString(), GlobalLitterals.ADMIN_USER_TYPE, role.getId());
			
		} catch(Exception ex){
			LOGGER.error("delete role page", ex);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_ROLE_PATH);
		}
		return viewpage;
	}
	
	@RequestMapping(value="/editrole", method = RequestMethod.POST)
	public String updateRole(Map model, @Valid RoleForm roleForm, 
			BindingResult result, HttpServletRequest request,Locale locale){
		
		LOGGER.debug( " updateRole " );
		HttpSession session = request.getSession();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return UIUtil.redirectPath(ADMIN_LOGIN_PATH);
		}
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return UIUtil.redirectPath(AttributeValueConstants.ADMIN_PATH);	
	    }
	    String userId = (String)session.getAttribute(USER_ID);
		Long authId = Long.valueOf(session.getAttribute(AUTHENTICATION_ID).toString());
		String viewPage = ROLE_EDIT_VIEW;
		try{
			RoleValidator roleValidator= new RoleValidator();
			roleValidator.validate(roleForm, result);
			if(!result.hasErrors()){
				Role role = userService.getRole(roleForm.getId());
				Role oldRole = (Role)role.clone();
				role.setName(roleForm.getName());	
				role.setDescription(roleForm.getDescription());
				role.setMenuDetails(roleForm.getMenuDetails());
				roleForm.setNameHidden(role.getName());
				roleForm.setDecHidden(role.getDescription());
				editRole(role);
				//Audit Trail
				auditTrailService.createAuditTrail(authId, AuditTrailConstrain.MODULE_ROLE_EDIT, AuditTrailConstrain.STATUS_UPDATE, 
						userId, GlobalLitterals.ADMIN_USER_TYPE, oldRole, role);
				viewPage = ROLES_VIEW;
				model.put(SUCCESS_MESSAGE, context.getMessage(UPDATE_ROLE_SUCCESS_MSG, null, locale));
			}
		} catch(CloneNotSupportedException ex){
			LOGGER.error(AuditTrailConstrain.AUDITTRAIL_CLONING_ERROR, ex);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_ROLE_PATH);
		} catch(Exception ex){
			LOGGER.error("update role page", ex);
			session.setAttribute(ERROR_MESSAGE, UserValidator.UNABLE_TO_PROCESS_MSG);
			return UIUtil.redirectPath(AttributeValueConstants.ADMIN_ROLE_PATH);
		}
		return viewPage;
	}		

	private void editRole(Role dto) throws WalletException {
		LOGGER.debug( " editRole " );
		userService.updateRole(dto);
	}

	@RequestMapping(value="/rolerecords", method = RequestMethod.GET, headers=JSON_HEADER, produces=JSON_PRODUCER)
	@ResponseBody 
	public JqgridResponse<Role> roleRecords(
    		@RequestParam("_search") Boolean search,
    		@RequestParam(value="filters", required=false) String filters,
    		@RequestParam(value="page", required=false) Integer page,
    		@RequestParam(value="rows", required=false) Integer rows,
    		@RequestParam(value="sidx", required=false) String sidx,
    		@RequestParam(value="sord", required=false) String sord,
    		HttpSession session, HttpServletRequest request) throws WalletException {
		
		JqgridResponse<Role> response = new JqgridResponse<Role>();
		if(!UIUtil.isAuthrised(request, ADMIN_USER_TYPE)){
			return response;
		}		
		Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(MENU_DETAILS),
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.VIEW_PERMISSION);
	    if(!adminAccessCheck){
	    	session.setAttribute(ERROR_MESSAGE, Common.AUTHORIZATION_FAILURE_ERROR_MSG);
	    	return response;	
	    }
	    
		QueryFilter qf = new QueryFilter();
		qf.setFilterString(filters);
		qf.setPage(page);
		qf.setRows(rows);
		qf.setSidx(sidx);
		qf.setSord(sord);
	    
	    List<Role> roles = userService.rolesList(qf);
		
		for(Role role :roles){
			if(AttributeValueConstants.ADMIN_ROLE_NAME.equalsIgnoreCase(role.getName())){
				roles.remove(role);
				break;
			}
		}
		int size = roles.size();
		int ps = DEFAULT_PAGE_SIZE;
		int n = size / ps;
		if( size / ps * ps != size){
			n++;
		}
		response.setRows(roles);
		response.setRecords(EMPTY_STRING + size);
		response.setTotal(EMPTY_STRING + n);
		response.setPage(qf.getPage().toString());
		return response;
	}
}