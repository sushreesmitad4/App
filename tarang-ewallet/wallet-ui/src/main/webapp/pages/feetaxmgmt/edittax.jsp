<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">
	function cancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
        				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/tax';
        			};
        confirmationDialog(yesAction, null, null, message);				
	}
	function continueAction(){
		submitFormData(document.tax);
	}
</script>
<div class="pageheading"><spring:message code="edit.tax.lbl" /></div>
<form:form method="POST" commandName="taxForm" action="edit" name ="tax">	
	<table class="form" style="width: 40%">
		<tr class="formtr">
			<td><form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/></form:label></td>
			<td>${countryName}</td>
		</tr>  				
		<tr>
			<td></td>
			<td><form:errors path="percentage" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="percentage" cssClass="formlebel">
					<spring:message code="percentage.on.fee.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:input path="percentage" cssClass="forminput" /></td>
		</tr>
		<tr>
			<td>
				<form:hidden path="id" />
				<form:hidden path="country" /> 
			</td>
		</tr>		
		<tr class="formtr">			
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
						Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
							MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
				    	if(adminAccessCheck){
	              %>
					<input type = "button" class="styled-button" value='<spring:message code="update.lbl" />' onclick = "continueAction()" />
					<% } %>
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
				</div>
			</td>
		</tr>
	</table>
</form:form>