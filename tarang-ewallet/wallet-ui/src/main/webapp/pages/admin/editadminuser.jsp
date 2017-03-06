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

	var countrySelected = ${countrySelected};
	
	$(document).ready(function() {
		/* For hide and show reason text field */
		var email = '${adminUserForm.emailId}';
		var userId = '${sessionScope.userId}';
		if(email != userId){
			activeAnddeleteAction();
		}
		else{
			enableReasonField(false);
		}
	});
	
	function onCountryChange(elementId, dataFor, id){
		var message = '<spring:message code="change.country.confirm.msg" />';
		if(id != '0' && countrySelected != id){
			var yesAction= function () {
				           	getMasterData(elementId, dataFor, id);
				           	countryChanged(elementId, dataFor, id);
				           	$(this).remove();
           				};
           	var noAction = function(){
			        		document.getElementById("countryId").value = countrySelected;
			        		$(this).remove();
						};						
			var closeAction = function(){
		        			document.getElementById("countryId").value = countrySelected;
		        			$(this).remove();
						};						

			confirmationCloseDialog(yesAction, noAction, closeAction, null, message);	
		}
		else{
			countryChanged(elementId, dataFor, id);
		}
	}
	
	function countryChanged(elementId, dataFor, id){
		if(id != '0' && countrySelected != id){
			document.getElementById("addressOne").value = "";
			document.getElementById("addressTwo").value = "";
			document.getElementById("city").value = "";
			document.getElementById("stateId").value = 0;
			document.getElementById("zipcode").value = "";
		}
		else{
			document.getElementById("addressOne").value = document.getElementById("add1").value;
			document.getElementById("addressTwo").value = document.getElementById("add2").value;
			document.getElementById("city").value = document.getElementById("oldCity").value;
			document.getElementById("zipcode").value = document.getElementById("zip").value;
			getMasterData(elementId, dataFor, id);
			setTimeout(function() { 
				document.getElementById("stateId").value = document.getElementById("oldState").value;}, 90);
		}
	}
	
	function updateAction() {
		var message = '<spring:message code="update.record.confirm.msg" />';
		var yesAction = function () {
			                document.getElementById("mode").value = "update";
							document.adminUser.method = "POST";
							$(this).dialog("close");
							submitFormData(document.adminUser);
           				};
		confirmationDialog(yesAction, null, null, message);
	}  
	  
	function cancelAction() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			 window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt';
        }
		confirmationDialog(yesAction, null, null, message);   
	}
	
	function resetPasswordAction() {
		var email = document.getElementById("emailId").value;
		var fname = document.getElementById("firstName").value;
		var lname = document.getElementById("lastName").value;
		
		var name = fname +" "+lname;
		var message = '<spring:message code="with.out.question.reset.password.confirm.msg" />' + ' ' + name + '?';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt/resetpasswordadminuser?name=' 
					+ name + '&email=' + email;
			ajaxLoader($("body"));
        }
		confirmationDialog(yesAction, null, null, message); 
	}
	/* For hide and show reason text field */
	function activeAnddeleteAction(){
		var oldDeleted = document.getElementById("oldDeleted").value;
		var oldActive = document.getElementById("oldActive").value;
		if(oldActive == "true"){
			oldActive = 1;
		}
		else{
			oldActive = 0;
		}
		
		if(oldDeleted == "true"){
			oldDeleted = 1;
		}
		else{
			oldDeleted = 0;
		}
		var deletedField = document.getElementsByName("deleted");
		var active = document.getElementsByName("active");
		if(deletedField != null && active != null){
			var newDeleted = 0;
			for(i=0; i < deletedField.length; i++){
				if(deletedField[i].checked){
					newDeleted = 1;
				}
			}
			if(oldDeleted != newDeleted){
				enableReasonField(true);
				return;
			}
			var newActive = 0;
			for(i=0; i < active.length; i++){
				if(active[i].checked){
					newActive = 1;
				}
			}
			if(oldActive != newActive){
				enableReasonField(true);
				return;
			}
			enableReasonField(false);
		}
 	}
	
	function enableReasonField(flag){
		if(flag == true){
			$("#rejecttext").show();
			$("#rejectErr").show();
		}
		else if(flag == false){
			$("#rejecttext").hide();
			$("#rejectErr").hide();
		}
	}
</script>
	
<div class="pageheading"><spring:message code="admin.user.update.lbl" /></div>
<form:form method="POST" commandName="adminUserForm" name="adminUser">
	
	<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<table class="form" style="width:50%">
		<tr>
			<td colspan=2><jsp:include page="adminuser.jsp"></jsp:include></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="active" cssClass="error" /></td>
		</tr>
		<tr>
			<td><form:label path="active"  cssClass="formlebel">
				<spring:message code="isactive.lbl" /></form:label>
			</td>
			<c:choose>
					<c:when test="${adminUserForm.emailId eq sessionScope.userId}">
						<c:if test="${adminUserForm.active}">
							<td><spring:message code="yes.lbl" /></td>
						</c:if>
						<c:if test="${!adminUserForm.active}">
							<td><spring:message code="no.lbl" /></td>
						</c:if>
				</c:when>
				<c:otherwise>
					<td><form:checkbox path="active" id="active" onclick="activeAnddeleteAction()" /></td>
				</c:otherwise>
			</c:choose>
		</tr>
		<c:if test="${adminUserForm.emailId ne sessionScope.userId}">
			<tr>
				<td><form:label path="deleted"  cssClass="formlebel"><spring:message code="deleted.lbl" /></form:label></td>
				<td><form:checkbox path="deleted" id="deleted" onclick="activeAnddeleteAction()" /></td>
			</tr>
		</c:if>
		<tr id="rejectErr">
			<td></td>
			<td><form:errors path="updateReason" cssClass="error" /></td>
		</tr>
		<tr id="rejecttext">
			<td><form:label path="updateReason" cssClass="formlebel"><spring:message code="update.reason.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
			<td><form:textarea path="updateReason" cssClass="forminput" /></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="emailId" cssClass="error" /></td>
		</tr>
		<tr>
			<td><form:label path="emailId" cssClass="formlebel">
				<spring:message code="emailid.lbl"/></form:label>
			</td>
			<td >${adminUserForm.emailId}</td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
                %>	
					<input type="button" onclick="updateAction()" class="styled-button" value='<spring:message code="update.lbl" />' />
					<c:if test="${adminUserForm.emailId ne sessionScope.userId}">
						<input type="button" onclick="resetPasswordAction()"  class="styled-button" value='<spring:message code="resetpassword.lbl"/>' />
					</c:if>
				<% } %>
					<input type="button" onclick="cancelAction()"  class="styled-button" value='<spring:message code="cancel.lbl" />' />
			    </div>
		    </td>
		</tr>
		<tr>
			<td>
				<form:hidden path="emailId" />
				<form:hidden path="existphone" />
				<form:hidden path="block" />
				<form:hidden path="userId" />
				<form:hidden path="status" />
				<form:hidden path="oldActive" />
				<form:hidden path="oldDeleted" />
				<c:if test="${adminUserForm.emailId eq sessionScope.userId}">
					<form:hidden path="roleId" />
					<form:hidden path="active" />
					<form:hidden path="roleName" />
					<form:hidden path="deleted" />
				</c:if>
			<td>
				<input type="hidden" name="add1" id="add1" value="${adminUserForm.addressOne}"/>
				<input type="hidden" name="add2" id="add2" value="${adminUserForm.addressTwo}"/>
				<input type="hidden" name="oldState" id="oldState" value="${adminUserForm.stateId}"/>
				<input type="hidden" name="oldCity" id="oldCity" value="${adminUserForm.city}"/>
				<input type="hidden" name="zip" id="zip" value="${adminUserForm.zipcode}"/>
			</td>
		</tr>
	</table>
</form:form>