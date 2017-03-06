<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<!-- Hide and show update reason filed -->
<jsp:include page="commonreasonupdate.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>
<script type="text/javascript">
	     
	var countrySelected = ${countrySelected};
	
	function onCountryChange(elementId, dataFor, id){
		var message = '<spring:message code="change.country.confirm.msg" />';
		if(id != '0' && countrySelected != id){
			var yesAction = function () {
					        	getMasterData(elementId, dataFor, id);
					        	countryChanged(elementId, dataFor, id);
					        	 $(this).remove();
	        				};
			var noAction = function(){
    			        		document.getElementById("country").value = countrySelected;
    			        		$(this).remove();
							};						
			var closeAction = function(){
			        			document.getElementById("country").value = countrySelected;
			        			$(this).remove();
							};						

			confirmationCloseDialog(yesAction, noAction, closeAction, null, message);	
		}
		else{
			countryChanged(elementId, dataFor, id);
		}
	}
	
	function countryChanged(elementId, dataFor, id){
		if(id != '0' && countrySelected != id){
			document.getElementById("addrOne").value = "";
			document.getElementById("addrTwo").value = "";
			document.getElementById("city").value = "";
			document.getElementById("state").value = 0;
			document.getElementById("postalCode").value = "";
		}
		else{
			document.getElementById("addrOne").value = document.getElementById("add1").value;
			document.getElementById("addrTwo").value = document.getElementById("add2").value;
			document.getElementById("city").value = document.getElementById("oldCity").value;
			document.getElementById("postalCode").value = document.getElementById("zip").value;
			getMasterData(elementId, dataFor, id);
			setTimeout(function() { document.getElementById("state").value = document.getElementById("oldState").value;}, 90);
		}
	}
	function update() {
		var message = '<spring:message code="update.record.confirm.msg" />';
		var yesAction = function () {
			document.getElementById("mode").value = "update";
			continueAction(); 
		}
		confirmationDialog(yesAction, null, null, message);
	}
	
	function resetPasword() {
		var fname = document.getElementById("firstName").value;
		var lname = document.getElementById("lastName").value;
		var name = fname +" "+lname;
		var message = '<spring:message code="reset.password.confirm.msg" />' + ' ' + name + '?';
		var yesAction = function () {
			document.getElementById("mode").value = "resetpassword";
			document.customermgmtform.submit();
			ajaxLoader($("body"));
		}
		confirmationDialog(yesAction, null, null, message);
	}
    
	function cancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customermgmt';
		}
		confirmationDialog(yesAction, null, null, message);
	} 
	function continueAction(){
		submitFormData(document.customermgmtform);
	}
</script>

<div class="pageheading"><spring:message code="customer.details.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" commandName="customerRegFormTwo" action="edit" name="customermgmtform">
	
	<table class="form" style="width: 45%">
	<tr>
		<td colspan="2">
		<jsp:include page="/pages/customer/commoncustomerdetails.jsp"></jsp:include>
		</td>
	</tr>
	</table>
	<div class="pageheading"><spring:message code="authentication.details.lbl" /></div>
	<table class="form" style="width: 45%">
		<tr>
			<td style="width: 26%">
				<form:label path="emailId" cssClass="formlebel"><spring:message code="emailid.lbl"/><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td><form:input path="emailId" readonly="true" cssClass="forminput" /></td>
			<td><form:errors path="emailId"  cssClass="error" /></td>       
		</tr>
		<tr>
			<td><form:label path="creationDate" cssClass="formlebel"><spring:message code="creationdate.lbl" /></form:label></td>
			<td><form:hidden path="creationDate" readonly="true"  style="width: 10%"/>
				<c:out value="${customerRegFormTwo.creationDate}" /></td>
		</tr>
		<c:choose>
			<c:when test="${hideDeleteStatus}">
				<tr>
					<td><form:label path="deleted" cssClass="formlebel"><spring:message code="deleted.lbl"/></form:label></td>
					<td><form:checkbox path="deleted" onclick="deleteAction()" /></td>
				</tr>	
			</c:when>
			<c:otherwise>
				<tr>
					<td><form:hidden path="deleted" /></td>
				</tr>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${hideAccountStatus}">
				<tr>
					<td><form:hidden path="status" /></td>
					<td><form:hidden path="active" /></td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td><form:label path="active" cssClass="formlebel"><spring:message code="active.lbl"/></form:label></td>
					<td><form:checkbox path="active" cssClass="formlebel" onclick="activeAndStatusAction()" /></td>
				</tr>
				<tr>
					<td><form:label path="status" cssClass="formlebel"><spring:message code="status.lbl"/></form:label></td>
					<td><c:if test="${statusList ne null }"><form:radiobuttons path="status" items="${statusList}" onclick="activeAndStatusAction()" /></c:if></td>
				</tr>			
			</c:otherwise>
		</c:choose>
		<tr id="rejectErr">
			<td></td>
			<td><form:errors path="updateReason" cssClass="error" /></td>
		</tr>
		<tr id="rejecttext">
			<td><form:label path="updateReason" cssClass="formlebel"><spring:message code="update.reason.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
			<td><form:textarea path="updateReason" cssClass="forminput" /></td>
		</tr>
		<tr>
			<td><form:label path="kycRequired" cssClass="formlebel"><spring:message code="kycrequired.lbl" /></form:label></td>
			<td><form:checkbox path="kycRequired" cssClass="formlebel"/></td>
		</tr>
		<tr>
			<td><form:label path="ipAddressCheck" cssClass="formlebel"><spring:message code="ipaddresscheck.lbl" /></form:label></td>
			<td><form:checkbox path="ipAddressCheck"/></td>
		</tr>
		<tr>
			<td><form:label path="emailPatternCheck" cssClass="formlebel"><spring:message code="emailpatterncheck.lbl" /></form:label></td>
			<td><form:checkbox path="emailPatternCheck"/></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
		        %>	
					<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="update()" />
					<input type="button" class="styled-button" value='<spring:message code="resetpassword.lbl" />' onclick="resetPasword()" />
					<% } %>
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
		    	</div>
			</td>
		</tr>
		<tr>    
            <td>
	            <form:hidden path="id"/>
				<form:hidden path="mode"/>
				<form:hidden path="oldActive" />
				<form:hidden path="oldDeleted" />
				<form:hidden path="oldStatus" />
				<input type="hidden" name="hideStatus" id="hideStatus" value="${hideAccountStatus}" />
			</td>
			<td>
				<input type="hidden" name="add1" id="add1" value="${customerRegFormTwo.addrOne}"/>
				<input type="hidden" name="add2" id="add2" value="${customerRegFormTwo.addrTwo}"/>
				<input type="hidden" name="oldState" id="oldState" value="${customerRegFormTwo.state}"/>
				<input type="hidden" name="oldCity" id="oldCity" value="${customerRegFormTwo.city}"/>
				<input type="hidden" name="zip" id="zip" value="${customerRegFormTwo.postalCode}"/>
			</td>	               
		</tr>
	</table>
</form:form>