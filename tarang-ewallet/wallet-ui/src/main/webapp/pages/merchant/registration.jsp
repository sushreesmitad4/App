<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.util.HelpLinkConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script>

	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	   	 					window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
	    				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function forSubmit() {
		submitFormData(document.merchantregistrationform);
	} 

</script>
<%@include file="/pages/helptipespopup.jsp" %>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="merchantregistrationform" method="POST" commandName="merchantForm" autocomplete="off" action="registration">
	<div class="pagelayout">
		<div class="block">
			<table class="form" style="width:60%">
				<tr class="formtr">
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="emailId" cssClass="error" /></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="emailNote" cssClass="errorsnote" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="emailId" cssClass="formlebel">
							<spring:message code="emailid.lbl"/>
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td style="padding:0px;">
     					<table><tr>
     						<td style="padding:0px;"><form:input path="emailId" cssClass="forminput" /></td>
     						<td><%=showHelpTipes(HelpLinkConstants.EMAIL_HELP_TIPS, request) %></td>
     						</tr></table>
    				</td>
				</tr>
				
			<%-- 	<tr>
					<td></td>
					<td><form:errors path="confirmEmailId" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="confirmEmailId" cssClass="formlebel">
							<spring:message code="confirmmailid.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="confirmEmailId" cssClass="forminput"></form:input></td>
				</tr> --%>
				
				<tr>
					<td></td>
					<td><form:errors path="phoneCountryCode1" cssClass="error" />
				</td>
				<tr>
					<td></td>
					<td><form:errors path="phoneNumber" cssClass="error" />
				</td>
				</tr>
				<tr>
					<td class="formtd">
					    <form:label path="phoneNumber" cssClass="formlebel">
							<spring:message code="phone.number.lbl"/>
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<%-- <form:select path="phoneCountryCode1" cssClass="formlist"  style="width: 100px">
							<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
								<c:forEach items="${phoneCodes}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select> --%>
					   <form:input path="phoneCountryCode1" class="phonecodeinput" ></form:input> -
					   <form:input path="phoneNumber" class="phoneinput" ></form:input> 
					</td>
				    <td>
				    </td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="password" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="password" cssClass="formlebel">
							<spring:message code="password.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td style="padding:0px;">
						<table><tr>
						<td style="padding:0px;"><form:password path="password" cssClass="forminput" /></td>
						<td><%=showHelpTipes(HelpLinkConstants.PASSWORD_TIPS, request) %></td>
						</tr></table>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="confirmPassword" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="confirmPassword" cssClass="formlebel">
							<spring:message code="confirmnewpassword.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:password path="confirmPassword" cssClass="forminput"></form:password></td>
				</tr>
				<jsp:include page="merchentcommonregistration.jsp"></jsp:include>
				<tr>
					<td colspan="2">
					<form:hidden path="mode" />
					<form:hidden path="ownerType" /> 
					<form:hidden path="businessLegalname" /> 
					<form:hidden path="address1BI" /> 
					<form:hidden path="address2BI" />
					<form:hidden path="cityOrTownBI" /> 
					<form:hidden path="stateorRegionBI" /> 
					<form:hidden path="countryBI" /> 
					<form:hidden path="postalCodeBI" /> 
					<%-- <form:hidden path="phoneCountryCode1" /> 
					<form:hidden path="phoneNumber" /> --%>
					<form:hidden path="businessCategory" /> 
					<form:hidden path="subCategory" /> 			
					<form:hidden path="businessEstablishedMonth" /> 
					<form:hidden path="businessEstablishedYear" /> 
					<form:hidden path="website" /> 
					<form:hidden path="currency" />
					<form:hidden path="averageTransactionAmount" /> 
					<form:hidden path="highestMonthlyVolume" /> 	
					<form:hidden path="percentageOfAnnualRevenueFromOnlineSales" /> 
					<form:hidden path="firstName" /> 
					<form:hidden path="lastName" /> 
					<form:hidden path="homeAddress" />
					<form:hidden path="address1BO" /> 
					<form:hidden path="address2BO" /> 
					<form:hidden path="cityOrTownBO" /> 
					<form:hidden path="stateOrRegionBO" /> 
					<form:hidden path="countryBO" /> 
					<form:hidden path="postalCodeBO" />
					<form:hidden path="emailCSI" /> 
					<form:hidden path="code" /> 	
					<form:hidden path="phone" />
					<form:hidden path="terms" />
					<form:hidden path="codeCheck" />
					<form:hidden path="merchantCode" /> 							
					<form:hidden path="successUrl" /> 							
					<form:hidden path="failureUrl" />
					<form:hidden path="existPersonPhoneNo" /> 
					<form:hidden path="existServicePhoneNo" /> 							
					<form:hidden path="oldEmailId" /> 							
					</td>
				</tr>
				<tr class="formtr">
					<td colspan=2 align="center">
						<div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="next.lbl" />' onclick="forSubmit()" />
						<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
						</div>
					</td>
			</table>
		</div>
	</div>
</form:form>