<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function backbtn() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/list';
	}
</script>
<div class="pageheading"><spring:message code="money.request.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="GET" commandName="requestMoneyForm" action="list">
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
						<form:label path="status" cssClass="formlebel">
							<spring:message code="status.lbl" />
						</form:label>
					</td>
					<td class="formtd">${requestMoneyForm.statusName}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="requesterMsg" cssClass="formlebel">
							<spring:message code="requester.message.lbl" />
						</form:label>
					</td>
					<td>${requestMoneyForm.requesterMsg}</td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="responserMsg" cssClass="formlebel">
							<spring:message code="responser.message.lbl" />
						</form:label>
					</td>
					<td>${requestMoneyForm.responserMsg}</td>
				</tr>
				<tr>
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="backbtn()" />
					  </div>
					</td>
				</tr>
			</table>
</form:form>