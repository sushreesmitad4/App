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
	function forReject() {
		document.getElementById("mode").value = "accept";
		submitFormData(document.rejectform);
	}
</script>
<div class="pageheading"><spring:message code="reject.request.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="rejectform" method="POST" commandName="requestMoneyForm" action="reject">
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
						<form:label path="requestDate" cssClass="formlebel">
							<spring:message code="request.date.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.requestDate}</td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="responserMsg" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="responserMsg" cssClass="formlebel">
							<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:textarea path="responserMsg" cssClass="forminput" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:hidden path="id" />
						<form:hidden path="emailId" />
						<form:hidden path="currency" />
						<form:hidden path="amount" />
						<form:hidden path="mode" />
						<form:hidden path="requestDate" />
						<form:hidden path="currencyCodeName" />
					</td>
				</tr>
				<tr>
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="reject.lbl" />' onclick="forReject()" />
						<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()" />
					  </div>
					</td>
				</tr>
			</table>
</form:form>