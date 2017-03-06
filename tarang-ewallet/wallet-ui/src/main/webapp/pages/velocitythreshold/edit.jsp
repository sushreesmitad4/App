<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type="text/javascript">
function continueAction() {
	submitFormData(document.editvelocitythreshold);
}
function cancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
        				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/velocityandthreshold';
        			};
        confirmationDialog(yesAction, null, null, message);				
	} 
</script>
<div class="pageheading"><spring:message code="edit.velocity.thresold.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="editvelocitythreshold" method="POST" commandName="velocityAndThresholdForm" action="editvelocity">
		
			<table class="form" style="width: 40%">
			    <tr class="formtr">
					<td><form:label path="usertype" cssClass="formlebel"><spring:message code="user.type.lbl" /></form:label></td>
					<td>${velocityAndThresholdForm.userTypeName}</td>
				</tr> 
				<tr class="formtr">
					<td><form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/></form:label></td>
					<td>${velocityAndThresholdForm.countryName}</td>
				</tr>  
				<tr class="formtr">
					<td><form:label path="currency" cssClass="formlebel"><spring:message code="currency.lbl" /></form:label></td>
					<td>${velocityAndThresholdForm.currencyName}</td>
				</tr>  
				<tr class="formtr">
					<td>
						<form:label path="transactiontype" cssClass="formlebel"><spring:message code="transaction.type.lbl" /></form:label>
					</td>
					<td>${velocityAndThresholdForm.transactiontypeName}</td>
				</tr>  
				<tr>
					<td></td>
					<td><form:errors path="minimumamount" cssClass="error" /></td>
				</tr> 
				<tr class="formtr">
					<td>
						<form:label path="minimumamount" cssClass="formlebel">
							<spring:message code="minimum.amount.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:input path="minimumamount" cssClass="forminputcvv" /></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="maximumamount" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="maximumamount" cssClass="formlebel">
							<spring:message code="maximum.amount.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:input path="maximumamount" cssClass="forminputcvv" /></td>
				</tr>
				
				<tr class="formtr" >
					<td>
						<form:hidden path="id" />
						<form:hidden path="country" />
						<form:hidden path="currency" /> 
						<form:hidden path="transactiontype" />
						<form:hidden path="countryName" />
						<form:hidden path="currencyName" /> 
						<form:hidden path="transactiontypeName" />
						<form:hidden path="usertype" />
						<form:hidden path="userTypeName" />
					</td>
				</tr>
				<tr class="formtr" >
					<td colspan=2 align="center">
						<div class="formbtns">
						<% 
			                	Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
				               	MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
		    	                if(adminAccessCheck){
	                      %>
							<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="continueAction()" />
							<% } %>
							<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()"/>
						</div>
					</td>
				</tr>
			</table>
</form:form>