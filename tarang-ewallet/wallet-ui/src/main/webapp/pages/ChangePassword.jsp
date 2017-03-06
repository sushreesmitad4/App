<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Change Password module. This includes User Login module
 --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.util.HelpLinkConstants,com.tarang.ewallet.walletui.controller.AttributeConstants" %>
<%@include file="/pages/helptipespopup.jsp" %>	
<script type="text/javascript">

	function forCancel(){

		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	                var v = "cancel";
	                document.getElementById("mode").value = "cancel";
	        		document.changepasswordsubmit.submit();
	        		ajaxLoader($("body"));
   				};
	 	confirmationDialog(yesAction, null, null, message);		
	
	}
	
	function forSubmit(){
		var isFirstTimeLogin = '<%=(Boolean)session.getAttribute(AttributeConstants.IS_RESET_PASSWORD)%>';
		if(isFirstTimeLogin != null && isFirstTimeLogin == 'false'){
			var message = '<spring:message code="change.password.confirm.msg" />';
			var yesAction = function () {
		                var v = "cancel";
		                document.getElementById("mode").value = "change";
		        		submitFormData(document.changepasswordsubmit);
	   				};
		 	confirmationDialog(yesAction, null, null, message);
		}
		else{
			submitFormData(document.changepasswordsubmit);
		}
	}

</script>

<div class="pageheading"><spring:message code="change.password.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="changepasswordsubmit" method="POST" commandName="changePasswordForm" autocomplete="off" action="changepassword">
	<table class="form" style="width: 45%">
	   	<tr class="formtr">
               <td><form:label path="emailId" cssClass="formlebel"><spring:message code="emailid.lbl"/></form:label></td>
			<td><form:input path="emailId" cssClass="forminput" readonly="true"/></td>
		</tr>
		<tr>
               <td></td>
               <td><form:errors path="oldPassword" cssClass="error" /></td>
           </tr>
		<tr class="formtr">
			<td>
				<form:label path="oldPassword" cssClass="formlebel"><spring:message code="oldpassword.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td><form:password path="oldPassword" cssClass="forminput" /></td>
		</tr>
		<tr>
              <td></td>
              <td><form:errors path="newPassword" cssClass="error" /></td>
              </tr>
        <tr class="formtr">
			<td>
				<form:label path="newPassword" cssClass="formlebel"><spring:message code="newpassword.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td style="padding:0px;">
				<table><tr>
				<td style="padding:0px;"><form:password path="newPassword" cssClass="forminput" /></td>
				<td><%=showHelpTipes(HelpLinkConstants.PASSWORD_TIPS, request) %></td>
				</tr></table>
			</td>
		</tr>				
		<tr>
           	<td></td>
           	<td><form:errors path="confirmNewPassword" cssClass="error" /></td>
           </tr>	
       	<tr class="formtr">
			<td>
				<form:label path="confirmNewPassword" cssClass="formlebel">
					<spring:message code="confirmnewpassword.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:password path="confirmNewPassword" cssClass="forminput" /></td>
		</tr>
	     <tr>
			<td colspan=2 align="center">
			   <div class="formbtns">
				  <input type="button" class="styled-button" id="change-password-button" value='<spring:message code="change.password.lbl" />' onclick="forSubmit()"/>
				  <input type="button" class="styled-button" name="cancel" value='<spring:message code="cancel.lbl" />' onclick="forCancel()"/>
			   </div>
			</td>
		</tr>	
	   <tr>
	       <form:hidden path="mode"/>
	   </tr>						
	</table>
</form:form>
	