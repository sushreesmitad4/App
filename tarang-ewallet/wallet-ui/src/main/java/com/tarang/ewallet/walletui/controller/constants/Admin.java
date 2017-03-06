/**
 * 
 */
package com.tarang.ewallet.walletui.controller.constants;

import com.tarang.ewallet.walletui.controller.AttributeValueConstants;

/**
 * @author prasadj
 * 
 */
public interface Admin extends AttributeValueConstants{

	String DEFAULT_VIEW = "admin";

	String LOGIN_VIEW = "admin.login";

	String MERCHANT_MGMT_EDIT = "admin.merchant.mgmt.edit";
	
	String CUSTOMER_UPDATE_PROFILE = "admin.customer.update.profile";
	
	String MERCHANT_MGMT_VIEW = "admin.merchant.mgmt.view";

	String CUSTOMER_MGMT_DEFAULT_VIEW = "admin.customers";

	String CUSTOMER_DETAILS_VIEW = "admin.customer";

	String MERCHANT_MGMT_DEFAULT_VIEW = "admin.merchants";

	String USER_MGMT_DEFAULT_VIEW = "admin.users";
	
	String USER_MGMT_DETAILS_EDIT = "admin.edituser";
	
	String USER_MGMT_DETAILS_VIEW = "admin.viewuser";
	
	String USER_MGMT_DETAILS_ADD = "admin.adduser";
	
	String USER_SAVE_SUCCESS_VIEW = "admin.users";
	
	String USER_SAVE_FAIL_VIEW = "admin.users";
	
	String USER_MGMT_FAIL_EDIT = "admin.edituser";
	
	String CUSTOMER_DETAILS = "customer.details.lbl";
	
	String CUSTOMER_MGMT = "customer.mgmt";

	String ROLES_VIEW = "admin.roles";

	String ROLE_ADD_VIEW = "admin.addrole";

	String ROLE_EDIT_VIEW = "admin.editrole";
	
	String ROLE_VIEW_PAGE = "admin.viewrole";

	String SUCCESS_VIEW = "admin.success";
	
	String LOGOUT = "admin.logout";
	
	String VIEW_RESOLVER_LOGIN_SUCCESS = "admin";
	
	String CUSTOMER_MGMT_EDIT = "admin.customer.mgmt.edit";
	
	String CUSTOMER_MGMT_VIEW = "admin.customer.mgmt.view";
	
	String WALLET_LOGOUT = "wallet.logout";
	
	String VIEW_UNDERCONSTRUCTION = "admin.view.under.const";
	
	/**
	 * constant for displaying the view in fee managements
	 */
	String FEE_GRID_VIEW = "admin.fees";
	
	String FEE_FIN_ADD = "admin.fee.fin";
	
	String FEE_NONFIN_ADD = "admin.fee.nonfin";
	
	String FEE_NONFINVARY_ADD = "admin.fee.fin.per";

	String FEE_FIN_EDIT = "admin.editfee.fin";

	String FEE_NONFIN_EDIT = "admin.editfee.nonfin";

	String FEE_NONFINVARY_EDIT = "admin.editfee.nonfinper";

	String FEE_FIN_VIEW = "admin.viewfee.fin";

	String FEE_NONFIN_VIEW = "admin.viewfee.nonfin";

	String FEE_NONFINVARY_VIEW = "admin.viewfee.nonfinper";

	String TAXS_DEFAULT_VIEW = "taxs";
	
	String TAX_ADD_VIEW = "addtax";
	
	String TAX_EDIT_VIEW = "edittax";
	
	String FEE_OPE_COU_CUR_COM_EXIST = "fee.ope.cou.cur.com.exist";
	
	String FEE_FAILS_TO_CREATE = "fee.fails.tocreate.errmsg";
	
	String VELOCITY_THRESHOLD_GRID = "admin.velocitythreshold.grid";
	
	String WALLET_THRESHOLD_GRID = "admin.walletthreshold.grid";
	
	String VELOCITY_THRESHOLD_ADD = "admin.velocitythreshold.add";
	
	String WALLET_THRESHOLD_ADD = "admin.walletthreshold.add";
	
	String VELOCITY_THRESHOLD_EDIT = "admin.velocitythreshold.edit";
	
	String WALLET_THRESHOLD_EDIT = "admin.walletthreshold.edit";
	
	String VELOCITY_THRESHOLD_VIEWPAGE = "admin.velocitythreshold.view";
	
	String WALLET_THRESHOLD_VIEWPAGE = "admin.walletthreshold.view";
	
	String TRANSACTION_THRESHOLD_ALREADY_EXISTS = "transaction.threshold.already.exists.errmsg";
	
	String TRANSACTION_THRESHOLD_FAILS_TO_CREATE = "transaction.threshold.fails.tocreate.errmsg";
	
	String TRANSACTION_THRESHOLD_FAILS_TO_UPDATE = "transaction.threshold.fails.toupdate.errmsg";
	
	String TRANSACTION_THRESHOLD_UPDATE_SUC_MSG = "transaction.threshold.update.success.msg";
	
	String TRANSACTION_THRESHOLD_CREATE_SUCCESS_MSG = "transaction.threshold.create.success.msg";
	
	String TRANSACTION_THRESHOLD_FAILS_TO_LOAD = "transaction.threshold.fails.toload.errmsg";
	
	String WALLET_THRESHOLD_FAILS_TO_LOAD = "wallet.threshold.fails.toload.errmsg";
	
	String WALLET_THRESHOLD_ALREADY_EXISTS = "wallet.threshold.already.exists.errmsg";
	
	String WALLET_THRESHOLD_CREATE_SUCCESS_MSG = "wallet.threshold.create.success_msg";
	
	String WALLET_THRESHOLD_FAILS_TO_CREATE = "wallet.threshold.fails.tocreate.errmsg";
	
	String WALLET_THRESHOLD_UPDATE_SUCCESS_MSG = "wallet.threshold.update.success.msg";
	
	String WALLET_THRESHOLD_FAILS_TO_UPDATE = "wallet.threshold.fails.toupdate.errmsg";
	
	String FEE_SUCC_UPDATED = "fee.updated.success.msg";
	
	String TAX_COU_EXIST = "tax.country.unique.errmsg";
	
	String ACCOUNT_CLOSER_LIST_VIEW = "admin.accountcloser.list";
	
	String ACCOUNT_CLOSER_VIEW = "admin.accountcloser.view";

	String ACCOUNT_CLOSER_UPDATE = "admin.accountcloser.update";

	String VELOCITY_RECORDS_URL = WALLET_PATH_PREFIX + "admin/velocityrecords";
	
	String ADMIN_RELOAD_PROPERTIES_VIEWPAGE = "admin.setup.view";
	
	String ADMIN_RELOAD_POPERTIES_SUCCESS_MSG = "admin.setup.properties.msg";
	
	String ADMIN_RELOAD_POPERTIES_FAILED = "admin.setup.properties.failed.msg";
	
	String ADMIN_RELOAD_POPERTIES = "admin.setup.properties";
	
	String GROUP_NAME = "groupName";
	
	String DISPUTE_FORCE_WITHDRAWAL_FAILED = "admin.setup.dispute.force.withdrawal.failed.msg";
	
	String DISPUTE_FORCE_WITHDRAWAL_SUCCESS_MSG = "admin.setup.dispute.force.withdrawal.success.msg";
	
	String RECONCILATION_SUCCESS_MSG = "admin.setup.reconcilation.success.msg";
	
	String RECONCILATION_FAILED = "admin.setup.reconcilation.failed.msg";
	
	String NON_SETTLEMENT_SUCCESS_MSG = "admin.setup.nonsettlement.success.msg";
	
	String NON_SETTLEMENT_FAILED = "admin.setup.nonsettlement.failed.msg";
	
	String CANCEL_NON_REGISTER_WALLET_TXNS_SUCCESS_MSG = "admin.setup.cancel.nonregister.wallet.txns.success.msg";
	
	String CANCEL_NON_REGISTER_WALLET_TXNS_FAILED = "admin.setup.cancel.nonregister.wallet.txns.failed.msg";
	
	String PENDING_ACCOUNT_CLOSERS_SUCCESS_MSG = "admin.setup.pending.account.closers.success.msg";
	
	String PENDING_ACCOUNT_CLOSERS_FAILED = "admin.setup.pending.account.closers.failed.msg";
	
	String RESENT_FAILED_EMAIL_SUCCESS_MSG = "admin.setup.resent.failed.email.success.msg";
	
	String RESENT_FAILED_EMAIL_FAILED = "admin.setup.resent.failed.email.failed";

	String DELETE_ROLE_ERR_MSG = "delete.role.errmsg";
	
	String DELETE_ROLE_SUCCESS_MSG = "delete.role.success.msg";
	
	String ADD_ROLE_SUCCESS_MSG = "add.role.success.msg";
	
	String UPDATE_ROLE_SUCCESS_MSG = "update.role.success.msg";
	
	String ACTIVE_LBL = "active.lbl";
	
	String IN_ACTIVE_LBL = "inactive.lbl";
	
	String TAX_ADD_SUCCESS_MSG = "tax.add.success.msg";
	
	String TAX_EDIT_SUCCESS_MSG = "tax.edit.success.msg";
	
	String TAX_CREATION_FAILED_ERRMSG = "tax.creation.failed.errmsg";
	
	String TAX_FAILED_TO_LOAD = "tax.failed.to.load.error.msg";
	
	String TAX_EDIT_ERROR_MSG = "tax.edit.error.msg";
	
	String USER_APPROVE_STATUS_DEFAULT_MSG = "user.approve.status.default.msg";
	
	String HTTP_POPERTIES_UPDATE_SUCCESS_MSG = "admin.setup.http.properties.succ.msg";
	
	String HTTP_POPERTIES_UPDATE_FAILED = "admin.setup.http.properties.failed.msg";
	
	String ADMIN_SETUP_HTTP_POPERTIES = "admin.setup.http.properties";
	
	String FAILED_TO_DELETE_FEE = "failed.to.delete.fee";
	
	String FEE_DELETED_SUC = "fee.deleted.successfully";
	
}
