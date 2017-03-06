<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<script type="text/javascript">
	
	function cancelAction() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt';
	}
	
</script>

<div class="pageheading"><spring:message code="admin.user.view.lbl" /></div>
<form:form method="POST" commandName="adminUserForm" name="adminUser">
	
	<table class="form" style="width: 40%">
		<tr>
			<td></td>
			<td><form:hidden path="mode"></form:hidden> <span>${successMessage}</span>
			</td>
		</tr>
		<tr>
			<td><form:label path="firstName" cssClass="formlebel"><spring:message code="firstname.lbl" /></form:label></td>
			<td>${adminUserForm.firstName}</td>
		</tr>
		<tr>
			<td><form:label path="lastName"  cssClass="formlebel"><spring:message code="lastname.lbl" /></form:label></td>
			<td>${adminUserForm.lastName}</td>
		</tr>
		<tr>
			<td><form:label path="countryId"  cssClass="formlebel"><spring:message code="country.lbl"/></form:label></td>
			<td>${countryName}</td>
		</tr>
		<tr>
			<td><form:label path="addressOne"  cssClass="formlebel"><spring:message code="address1.lbl"/></form:label></td>
			<td>${adminUserForm.addressOne}</td>
		</tr>
		<tr>
			<td><form:label path="addressTwo"  cssClass="formlebel"><spring:message code="address2.lbl"/></form:label></td>
			<td>${adminUserForm.addressTwo}</td>
		</tr>
		<tr>
			<td><form:label path="city"  cssClass="formlebel"><spring:message code="city.lbl"/></form:label></td>
			<td>${adminUserForm.city}</td>
		</tr>
		<tr>
			<td><form:label path="stateId"  cssClass="formlebel"><spring:message code="stateorregion.lbl"/></form:label></td>
			<td>${stateName}</td>
		</tr>
		<tr>
			<td><form:label path="zipcode"  cssClass="formlebel"><spring:message code="zipcode.lbl"/></form:label></td>
			<td>${adminUserForm.zipcode}</td>
		</tr>
		<tr>
			<td><form:label path="phoneNo" cssClass="formlebel"><spring:message code="phone.number.lbl"/></form:label></td>
			<td>${adminUserForm.phoneCode}-${adminUserForm.phoneNo}</td>
		</tr>
		<tr>
			<td><form:label path="emailId"  cssClass="formlebel"><spring:message code="emailid.lbl"/></form:label></td>
			<td>${adminUserForm.emailId}</td>
		</tr>
		<tr>
			<td><form:label path="active"  cssClass="formlebel"><spring:message code="isactive.lbl" /></form:label></td>
			<c:if test="${adminUserForm.active == true}">
					<td><spring:message code="yes.lbl" /></td>
				</c:if>
				<c:if test="${adminUserForm.active == false}">
					<td><spring:message code="no.lbl" /></td>
				</c:if>
		</tr>
		<tr>
			<td><form:label path="roleId"  cssClass="formlebel"><spring:message code="role.lbl"/></form:label></td>
			<td>${adminUserForm.roleName}</td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="back.lbl"/>' />
				</div>
			</td>			
		</tr>
	</table>
</form:form>