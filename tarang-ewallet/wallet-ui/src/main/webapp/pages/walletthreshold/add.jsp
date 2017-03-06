<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type="text/javascript">	
	function continueAction() {
		submitFormData(document.addwalletthresholdcode);
	}
	
	function cancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	       				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/walletthreshold';
	    };
	    confirmationDialog(yesAction, null, null, message);				
	}

</script>
<div class="pageheading"><spring:message code="add.wallet.threshold.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="addwalletthresholdcode" method="POST" commandName="walletThresholdForm" action="addwalletthreshold">
		
			<table class="form" style="width: 50%">			
				<tr>
					<td></td>
					<td><form:errors path="country" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="country" cssClass="formlebel">
							<spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td>
						<form:select path="country" cssClass="formlist" >
							<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${countryList}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="currency" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="currency" cssClass="formlebel">
							<spring:message code="currency.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td>
						<form:select path="currency" cssClass="formlist" >
							<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${currencyList}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
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
					<td colspan=2 align="center">
						<div class="formbtns">
						<%
			               Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
			                 	MenuConstants.WALLET_THRESHOLD, MenuConstants.MANAGE_PERMISSION);
	                      	if(adminAccessCheck){
                        %>	
							<input type="button" class="styled-button" value='<spring:message code="save.lbl" />' onclick="continueAction()" />
							 <% } %>
							<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
						</div>
					</td>
				</tr>
			</table>
</form:form>
