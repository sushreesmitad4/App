<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for admin user module. This includes admin user management module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals"%>
	<tr>
		<td >
			<form:hidden path="mode"></form:hidden> 
		</td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="firstName" cssClass="error" /></td>
	</tr>
	<tr>
		<td>
			<form:label path="firstName" cssClass="formlebel"><spring:message code="firstname.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:input path="firstName" cssClass="forminput"></form:input></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="lastName" cssClass="error" /></td>
	</tr>
	<tr>
		<td>
			<form:label path="lastName" cssClass="formlebel"><spring:message code="lastname.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td ><form:input path="lastName" cssClass="forminput"></form:input></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="countryId" cssClass="error" /></td>
	</tr>
<c:choose>
	<c:when test="${editPage}">
	<tr>
		<td>
			<form:label path="countryId" cssClass="formlebel"><spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td >
			<form:select path="countryId" cssClass="formlist" onchange="onCountryChange('stateId', 'regions', this.value);">
				<form:option value="<%=GlobalLitterals.ZERO_INTEGER %>"><spring:message code="please.select.lbl" /></form:option>
				<c:if test="${countryList ne null }">
					<c:forEach items="${countryList}" var="entry">
				        <form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
				    </c:forEach>
				</c:if>
			</form:select>
		</td>
	</tr>
	</c:when>
<c:otherwise>
	<tr>
		<td>
			<form:label path="countryId" cssClass="formlebel"><spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td >
			<form:select path="countryId" cssClass="formlist" onchange="getMasterData('stateId', 'regions', this.value);">
			<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
				<spring:message code="please.select.lbl" />
			</form:option>
			<c:if test="${countryList ne null }">
				<c:forEach items="${countryList}" var="entry">
			        <form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
			    </c:forEach>
			</c:if>
			</form:select>
		</td>
	</tr>
	</c:otherwise>
</c:choose>
	<tr>
		<td></td>
		<td><form:errors path="addressOne" cssClass="error" /></td>
	</tr>
	<tr>
	 	<td>
	 		<form:label path="addressOne" cssClass="formlebel"><spring:message code="address1.lbl"/><span class="mfield">&nbsp;*</span></form:label>
	 	</td>
	 	<td ><form:textarea path="addressOne" cssClass="forminput" cols="2"></form:textarea></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="addressTwo" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="addressTwo" cssClass="formlebel"><spring:message code="address2.lbl"/></form:label></td>
		<td ><form:textarea path="addressTwo" cssClass="forminput" cols="2"></form:textarea></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="city" cssClass="error" /></td>
	</tr>
	<tr>
		<td>
			<form:label path="city" cssClass="formlebel"><spring:message code="city.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td ><form:input path="city" cssClass="forminput"></form:input></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="stateId" cssClass="error" /></td>
	</tr>
	<tr>
		<td>
			<form:label path="stateId" cssClass="formlebel"><spring:message code="stateorregion.lbl"/></form:label>
		</td>
		<td ><form:select path="stateId" cssClass="formlist">
			<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
				<spring:message code="please.select.lbl" />
			</form:option>
			<c:if test="${stateList ne null }">
				<c:forEach items="${stateList}" var="entry">
					<form:option value="${entry.key}" title="${std.name}"
						label="${entry.value}" />
				</c:forEach>
			</c:if>
			</form:select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="zipcode" cssClass="error" /></td>
	</tr>
	<tr>
		<td><form:label path="zipcode" cssClass="formlebel"><spring:message code="zipcode.lbl"/></form:label></td>
		<td ><form:input path="zipcode" cssClass="forminput"></form:input></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="phoneCode" cssClass="error" /></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="phoneNo" cssClass="error" /></td>
	</tr>
	<tr>
		<td>
			<form:label path="phoneNo" cssClass="formlebel"><spring:message code="phone.number.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><%-- <form:select path="phoneCode" cssClass="formlist"  style="width: 100px">
		<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${phoneCodes}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select> --%>
			<form:input path="phoneCode" class="phonecodeinput" /> - <form:input  path="phoneNo" class="phoneinput" /></td>
	</tr>

	<tr>
		<td></td>
		<td><form:errors path="roleId" cssClass="error" /></td>
	</tr>
<c:choose>
 	<c:when test="${editPage && adminUserForm.emailId eq sessionScope.userId}">
	<tr>
		<td>
			<form:label path="roleName" cssClass="formlebel"><spring:message code="role.lbl"/></form:label>
		</td>
		<td >${adminUserForm.roleName}</td>
	</tr>
	</c:when>
	<c:otherwise>
	<tr>
		<td>
			<form:label path="roleId" cssClass="formlebel"><spring:message code="role.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td >
			<form:select path="roleId" cssClass="formlist">
			<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
				<spring:message code="please.select.lbl" />
			</form:option>
				<c:if test="${rolesList ne null }">
				<c:forEach items="${rolesList}" var="role">
			        <form:option value="${role.id}" title="${role.description}" label="${role.name}" />
			    </c:forEach>
			    </c:if>
			</form:select>
		</td>
	</tr>
	</c:otherwise>
</c:choose>