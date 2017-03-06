<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg"/>';
		var yesAction = function () {
	  	 					window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/list';
	   					};
		confirmationDialog(yesAction, null, null, message);	
	} 
	function forSubmit() {
		document.getElementById("mode").value = "submit";
		submitFormData(document.requestform);
	} 
	
</script>

<div class="pageheading"><spring:message code="money.request.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="requestform" method="POST" commandName="requestMoneyForm" action="requestmoney">
	<table class="form" style="width: 40%;">
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="emailId" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="emailId" cssClass="formlebel">
					<spring:message code="emailid.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd"><form:input path="emailId" cssClass="forminput" /></td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="phoneCode" cssClass="error" /></td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="phoneNumber" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd"><form:label path="phoneNumber" cssClass="formlebel"><spring:message code="phone.number.lbl" /></form:label></td>
			<td>
				<form:input path="phoneCode" class="phonecodeinput" /> - <form:input path="phoneNumber" class="phoneinput" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="currency" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="currency" cssClass="formlebel">
					<spring:message code="currency.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd"><form:select path="currency"  cssClass="formlist">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${currencyList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="amount" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="amount" cssClass="formlebel">
					<spring:message code="amount.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd"><form:input path="amount" cssClass="forminput" /></td>
		</tr>
		<tr>
			<td><form:hidden path="mode" /></td>
			<td class="formerror"><form:errors path="requesterMsg" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="requesterMsg" cssClass="formlebel">
					<spring:message code="message.lbl" />
				</form:label>
			</td>
			<td class="formtd"><form:input path="requesterMsg" cssClass="forminput" /></td>
		</tr>
		<tr class="formtr">
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" class="styled-button" value='<spring:message code="request.lbl" />' onclick="forSubmit()"/>
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()"/>
				</div>
			</td>
		</tr>
	</table>
</form:form>