<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg"/>';
		var yesAction = function () {
	  	 					window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/list';
	   					};
		confirmationDialog(yesAction, null, null, message);	
	} 
	function forBack(){
		document.getElementById("mode").value = "back";
		forSubmitForm();
	}
	function forConfirm(){
		document.getElementById("mode").value = "confirm";
		forSubmitForm();
	}
	
	function forSubmitForm(){
		submitFormData(document.requestconf);
	}
</script>
<div class="pageheading"><spring:message code="money.request.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="requestconf" method="POST" commandName="requestMoneyForm" action="requestmoney">
		<table class="form" style="width: 40%">
				<tr class="formtr">
					<td class="formtd">
						<form:label path="emailId" cssClass="formlebel">
							<spring:message code="emailid.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.emailId}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="phoneNumber" cssClass="formlebel">
							<spring:message code="phone.number.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.phoneCode} - ${requestMoneyForm.phoneNumber}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="currency" cssClass="formlebel">
							<spring:message code="currency.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.currencyCodeName}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="amount" cssClass="formlebel">
							<spring:message code="amount.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.amount}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="requesterMsg" cssClass="formlebel">
							<spring:message code="message.lbl" />
						</form:label>
					</td>
					<td>${requestMoneyForm.requesterMsg}</td>
				</tr>
				<tr class="formtr">		
				   <td>
						<form:hidden path="emailId" />
						<form:hidden path="phoneCode" />
						<form:hidden path="phoneNumber" />
						<form:hidden path="currency" />
						<form:hidden path="amount" />
						<form:hidden path="requesterMsg" />
						<form:hidden path="mode" />
					</td>	
				</tr>
				<tr>	
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="confirm.lbl" />' onclick="forConfirm()" />
						<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack()" />
						<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()" />		
					 </div>
				   </td>
				</tr>
			</table>
</form:form>