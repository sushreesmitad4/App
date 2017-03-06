<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.masterdata.util.BankAccountTypes"%>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="accountHolderName" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="accountHolderName" cssClass="formlebel">
				<spring:message code="account.holder.name.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd">
			<form:input path="accountHolderName" cssClass="forminput" />
		</td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="bankName" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="bankName" cssClass="formlebel">
				<spring:message code="bank.name.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label></td>
		<td class="formtd"><form:input path="bankName" cssClass="forminput" /></td>
	</tr>
	<tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="bankAddress" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="bankAddress" cssClass="formlebel">
				<spring:message code="bank.address.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd"><form:input path="bankAddress" cssClass="forminput" /></td>
	</tr>
	<tr>
	<tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="accountType" cssClass="error" /></td>
	</tr>
	<tr>
		<td class="formtd">
			<form:label path="accountType" cssClass="formlebel">
				<spring:message code="account.type.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td><form:radiobutton path="accountType" value="<%=BankAccountTypes.ACCOUNT_TYPE_THAILAND_CURRENT%>" /><spring:message code="current.lbl"/></td>
	</tr>
	<tr>
		<td></td>
		<td><form:radiobutton path="accountType" value="<%=BankAccountTypes.ACCOUNT_TYPE_THAILAND_SAVINGS%>" /><spring:message code="savings.lbl"/></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="sortCode" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="sortCode" cssClass="formlebel">
				<spring:message code="branch.code.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label></td>
		<td class="formtd"><form:input path="sortCode" cssClass="forminput" /></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="accountNumber" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="accountNumber" cssClass="formlebel">
				<spring:message code="account.number.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd"><form:input path="accountNumber" cssClass="forminput" /></td>
	</tr>
<c:if test="${isEditPage == 'false'}">
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="reEnterAccountNumber" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="reEnterAccountNumber" cssClass="formlebel">
				<spring:message code="re.enter.account.number.lbl" /><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd"><form:input path="reEnterAccountNumber" cssClass="forminput" /></td>
	</tr>
</c:if>