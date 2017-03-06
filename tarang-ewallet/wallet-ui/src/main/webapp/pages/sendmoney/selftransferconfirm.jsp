<%@page import="com.tarang.ewallet.walletui.form.SendMoneyForm"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script>

	$(document).ready(function() {
		enableRecurring();
	});
	
	function forConfirm(){
		document.getElementById("mode").value ="confirm";
		submitForm();
	}
	function forBack(){
		document.getElementById("mode").value ="back";
		submitForm();
	}
	function forCancel() {
	
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
   	 					window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingleconfirmcancel';
    				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function submitForm(){
		submitFormData(document.selftransform);
	}
</script>
<div class="pageheading"><spring:message code="self.transfer.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="selftransform" method="POST" commandName="selfTransferForm" action="selfTransferConfirm" >
		<table class="form" style="width:40%">
			<tr class="formtr">
				<td></td>
				<td>
			</tr>
			<tr>
				<td class="formtd">
					<form:label path="balance" cssClass="formlebel">
						<spring:message code="requested.amount.lbl" />
					</form:label>
				</td>
				<td class="formtd">${requestedAmount }</td>
			</tr>
			<tr>
				<td class="formtd">
					<form:label path="balance" cssClass="formlebel">
						<spring:message code="actual.amount.lbl" />
					</form:label>
				</td>
				<td class="formtd">${actualAmount }</td>
			</tr>
			<tr>
				<td class="formtd">
					<form:label path="balance" cssClass="formlebel">
						<spring:message code="taxfee.lbl" />
					</form:label></td>
				<td class="formtd">${taxfee }</td>
			</tr>
			<tr>
				<td class="formtd">
					<form:label path="balance" cssClass="formlebel">
						<spring:message code="total.deductions.lbl" />
					</form:label></td>
				<td class="formtd">${totalDeductions }</td>
			</tr>
			<tr class="formtr">
				  <td><form:hidden path="actualAmount"/><form:hidden path="transactionType"/></td>
			</tr>
				<tr>
				  <td colspan=2 align="center">
				      <div class="formbtns">
					     <input type="button" class="styled-button" value='<spring:message code="confirm.lbl" />' onclick="return forConfirm();" />
					     <input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack();" />
					     <input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
				   </div>
				</td>
			</tr>
		</table>
       <form:hidden path="fromWallet"></form:hidden>
       <form:hidden path="balance"></form:hidden>
       <form:hidden path="requestedAmount"></form:hidden>
       <form:hidden path="towallet"></form:hidden>
       <form:hidden path="mode"></form:hidden>
</form:form>