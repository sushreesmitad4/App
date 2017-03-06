<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.masterdata.util.CountryIds,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<% 	pageContext.setAttribute("USD_COUNTRY_ID", CountryIds.USD_COUNTRY_ID); 
	pageContext.setAttribute("JPY_COUNTRY_ID", CountryIds.JPY_COUNTRY_ID);
	pageContext.setAttribute("THAI_COUNTRY_ID", CountryIds.THAI_COUNTRY_ID); %>

<script type="text/javascript">

	function cancleBtn() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts';
	}

	function editBank(mode) {
		document.getElementById("mode").value = mode;
		submitFormData(document.editbank);
	}
	
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts';
		}
		confirmationDialog(yesAction, null, null, message);
	}

</script>

<div class="pageheading"><spring:message code="edit.bank.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="editbank" method="POST" commandName="addBankAccountForm" action="editbank">
	<table class="form" style="width: 60%">
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="country" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="country" cssClass="formlebel">
					<spring:message code="country.lbl"/>
					<span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd">${countryName}</td>
		</tr>
		<c:if test="${countrySelected == USD_COUNTRY_ID }">
			<jsp:include page="/pages/banks/us.jsp"></jsp:include>
		</c:if>
		<c:if test="${countrySelected == JPY_COUNTRY_ID }">
			<jsp:include page="/pages/banks/japan.jsp"></jsp:include>
		</c:if>
		<c:if test="${countrySelected == THAI_COUNTRY_ID }">
			<jsp:include page="/pages/banks/thailand.jsp"></jsp:include>
		</c:if>
		<tr class="formtr">
			<td><form:hidden path="mode" /><form:hidden path="country" /></td>
			</tr>
			<tr>
			<td colspan=2 align="center">
		       <div class="formbtns">
				 <input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="editBank('edit')" />
				 <input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()" />
			  </div>
			</td>
		</tr>
	</table>
</form:form>