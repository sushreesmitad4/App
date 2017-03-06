<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function cancelAction() {
		document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/walletthreshold';
	} 
</script>
<div class="pageheading"><spring:message code="view.wallet.threshold.lbl" /></div>
<form:form method="POST" commandName="walletThresholdForm" action="editvelocity">
			<table class="form" style="width: 40%">
				<tr class="formtr">
					<td><form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/></form:label></td>
					<td>${walletThresholdForm.countryName}</td>
				</tr>  
				<tr class="formtr">
					<td><form:label path="currency" cssClass="formlebel"><spring:message code="currency.lbl" /></form:label></td>
					<td style="width: 60%">${walletThresholdForm.currencyName}</td>
				</tr>  
						
				<tr class="formtr">
			        <td><form:label path="maximumamount" cssClass="formlebel"><spring:message code="maximum.amount.lbl" /></form:label></td>
		            <td>${walletThresholdForm.maximumamount}</td>
				</tr> 
				<tr class="formtr">
					<td colspan=2 align="center">
						<div class="formbtns">
							<input type="button" class="styled-button" value='<spring:message code="back.lbl"/>' onclick="cancelAction()" />
						</div>
					</td>
				</tr>
			</table>
</form:form>