<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script>
	function cancelAction() {
		var servicetype = document.getElementById("services").value;
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?' 
				 + 'servicetype' + '=' + servicetype;
		}
		confirmationDialog(yesAction, null, null, message);  
	} 

	function ajaxCallDropDown(){
		var feeSerId = document.getElementById("services").value;
		var userTypeId = document.getElementById("userType").value;
		getMasterData('operationType', 'operationtypes', feeSerId, userTypeId);
	}
	
	function continueAction(){
		submitFormData(document.feeForm);
	}

</script>
<div class="pageheading"><spring:message code="fin.add.fee.non.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form commandName="feeMgmtForm" action="addfee"  name="feeForm">
	<form:hidden path="services" ></form:hidden>
	<form:hidden path="currency" ></form:hidden>
	<form:hidden path="payingentity" ></form:hidden>
	<form:hidden path="feeType" ></form:hidden>
	<form:hidden path="validation" ></form:hidden>
		
	<table class="form" style="width: 45%">
		<!-- User Type -->
		<tr class="formtr">
			<td>
				<form:label path="userType" cssClass="formlebel">
					<spring:message code="user.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td>
				<form:select path="userType" onchange="ajaxCallDropDown();" cssClass="formlist">
					<form:option value="0" >
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
			<td><form:errors path="operationType" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="operationType" cssClass="formlebel">
					<spring:message code="operation.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="operationType"  cssClass="formlist">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${operationTypeList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		 <!-- Country --> 
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
			<td><form:select path="country"  cssClass="formlist">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${countryList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<!-- Fee Type -->
		<!-- Fixed Charge  -->
		<tr>
			<td></td>
				<td><form:errors path="fixCharSen" cssClass="error" /></td>
			</tr>
			<tr class="formtr">
				<td><form:label path="fixCharSen" cssClass="formlebel">
						<spring:message code="flat.fee.lbl" /><span id = "fcs" class="mfield">&nbsp;*</span>
					</form:label></td>
				<td><form:input path="fixCharSen" cssClass="forminput" /></td>
		</tr>
		<!-- time period -->
		<tr>
			<td></td>
			<td><form:errors path="timeFreequency" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="timeFreequency" cssClass="formlebel">
					<spring:message code="time.freequency.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="timeFreequency"  cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${timePeriodList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr class="formtr">
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.FEE_MANAGEMENT_NF_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
				%>
				<input type="button" class="styled-button" value='<spring:message code="save.lbl" />' onclick = "continueAction()" />
				<% } %>
				<input type="button" class="styled-button" value='<spring:message code="cancel.lbl"/>' onclick="cancelAction()" />
				</div>
			</td>
		</tr>
	</table>

</form:form>