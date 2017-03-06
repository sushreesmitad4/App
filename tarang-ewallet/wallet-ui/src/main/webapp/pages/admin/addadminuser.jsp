<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for admin user module. This includes admin user management module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%> 
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">

	function createAction() {
		document.getElementById("mode").value = "create";
		submitFormData(document.addadminuser);
	}
	function cancelAction() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt';
		}
		confirmationDialog(yesAction, null, null, message);  
	} 
	 
	function clearForm() {
		document.getElementById("firstName").value = "";
		document.getElementById("lastName").value = "";
		document.getElementById("countryId").value = 0;
		document.getElementById("stateId").value = 0;
		document.getElementById("city").value = "";
		document.getElementById("addressOne").value = "";
		document.getElementById("addressTwo").value = "";
		document.getElementById("emailId").value = "";
		document.getElementById("zipcode").value = "";
		document.getElementById("roleId").value = 0;
		document.getElementById("phoneCode").value = "";
		document.getElementById("phoneNo").value = "";
	}
	
</script>

<div class="pageheading"><spring:message code="admin.user.rigistration.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="addadminuser" method="POST" commandName="adminUserForm">
	<table class="form" style="width:50%">
		<tr>
			<td colspan=2><jsp:include page="adminuser.jsp"></jsp:include></td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="emailId" cssClass="error" /></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="emailNote" cssClass="errorsnote" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="emailId" cssClass="formlebel"><spring:message code="emailid.lbl"/><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td ><form:input path="emailId" cssClass="forminput"></form:input></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
                %>	
					<input type="button" onclick="createAction()" class="styled-button" value='<spring:message code="create.lbl" />' />
				<% } %>
					<input type="button" onclick="cancelAction()"  class="styled-button" value='<spring:message code="cancel.lbl" />' /> 
					<input type="button" onclick="clearForm()" class="styled-button" value='<spring:message code="clear.lbl" />' />
				</div>
			</td>
		</tr>
	</table>
</form:form>