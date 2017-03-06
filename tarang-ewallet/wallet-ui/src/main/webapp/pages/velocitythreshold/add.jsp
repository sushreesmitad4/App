<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type="text/javascript">	
function continueAction() {
	submitFormData(document.addvelocitythreshold);
}
function cancel() {
	var message = '<spring:message code="cancel.confirm.msg" />';
	var yesAction = function () {
       				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/velocityandthreshold';
    };
    confirmationDialog(yesAction, null, null, message);				
}
function ajaxCallDropDown(){
	/*for velocity feeSerId always one i.e. financial*/
	var userTypeId = document.getElementById("usertype").value;
	getMasterData('transactiontype', 'operationtypes', 1, userTypeId);
}
</script>
<div class="pageheading"><spring:message code="add.velocity.thresold.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="addvelocitythreshold" method="POST" commandName="velocityAndThresholdForm" action="addvelocity">
		
			<table class="form" style="width: 40%">
			
			<tr>
			<td></td>
			<td><form:errors path="usertype" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="usertype" cssClass="formlebel">
					<spring:message code="user.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td>
				<form:select path="usertype" onchange="ajaxCallDropDown();" cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${userTypeList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}"  label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
				
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
					<td><form:select path="country" cssClass="formlist" >
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
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
					<td><form:select path="currency" cssClass="formlist" >
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${currencyList}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="transactiontype" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="transactiontype" cssClass="formlebel">
							<spring:message code="transaction.type.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:select path="transactiontype" cssClass="formlist" >
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${operationTypeList}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
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
					<td colspan=2 align="center">
						<div class="formbtns">
						 <% 
			                	Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
				               	MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
		    	                if(adminAccessCheck){
	                      %>	
							<input type="button" class="styled-button" value='<spring:message code="save.lbl" />' onclick="continueAction()" />
							<% } %>
							<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()"/>
						</div>
					</td>
				</tr>
			</table>
</form:form>