<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>
<script type="text/javascript">
	function Cancel() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customermgmt';
	}
</script>
<div class="pageheading"><spring:message code="customer.details.lbl" /></div>
<form:form method="GET" commandName="customerRegFormTwo" action="customerviewcancel">	
	<table class="form" style="width: 40%">
		<tr>
			<td>
				<form:label path="firstName" cssClass="formlebel">
					<spring:message code="firstname.lbl" />
				</form:label></td>
			<td>${customerRegFormTwo.firstName}</td>
		</tr>
		<tr>
			<td>
				<form:label path="lastName" cssClass="formlebel">
					<spring:message code="lastname.lbl" />
				</form:label></td>
		    <td>${customerRegFormTwo.lastName}</td>
		</tr>
		<tr>
			<td>
				<form:label path="dateOfBirth" cssClass="formlebel">
					<spring:message code="dateofbirth.lbl" />
				</form:label></td>
			<td>${dateOfBirth}</td>
			<td>${displayDate}</td>
		</tr>
		<tr>
			<td>
				<form:label path="emailId" cssClass="formlebel">
					<spring:message code="emailid.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.emailId}</td>
		</tr>
		<tr>
			<td>
				<form:label path="country" cssClass="formlebel">
					<spring:message code="country.lbl"/>
				</form:label></td>
			<td>${countryName}</td>
		</tr>
		<tr>
			<td>
				<form:label path="addrOne" cssClass="formlebel">
					<spring:message code="address1.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.addrOne}</td>
		</tr>
		<tr>
			<td>
				<form:label path="addrTwo" cssClass="formlebel">
					<spring:message code="address2.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.addrTwo}</td>
		</tr>
		<tr>
			<td>
				<form:label path="city" cssClass="formlebel">
					<spring:message code="city.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.city}</td>
		</tr>
		<tr>
			<td>
				<form:label path="state" cssClass="formlebel">
					<spring:message code="stateorregion.lbl"/>
				</form:label></td>
			<td>${stateName}</td>
		</tr>
		<tr>
			<td>
				<form:label path="postalCode" cssClass="formlebel">
					<spring:message code="zipcode.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.postalCode}</td>
		</tr>
		<tr>
			<td>
				<form:label path="phoneNo" cssClass="formlebel">
					<spring:message code="phone.number.lbl"/>
				</form:label></td>
			<td>${customerRegFormTwo.phoneCode}-${customerRegFormTwo.phoneNo}</td>
		</tr>
		<tr>
			<td>
				<form:label path="status" cssClass="formlebel">
					<spring:message code="status.lbl"/>
				</form:label></td>
			<td>${statusName}</td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" class="styled-button" onclick="Cancel()" value='<spring:message code="back.lbl" />' />
				</div>
			</td>
		</tr>
		<tr><form:hidden path="mode"/></tr>
	</table>
</form:form>