<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<tr>
		<td></td>
		<td><form:errors path="ptitle" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td>
			<form:label path="ptitle" cssClass="formlebel"><spring:message code="title.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:select path="ptitle" cssClass="formlist">
		<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${titles}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr> 
	<tr>
		<td></td>
		<td><form:errors path="firstName" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td>
			<form:label path="firstName" cssClass="formlebel"><spring:message code="firstname.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:input path="firstName" cssClass="forminput" /></td>
	
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="lastName" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td>
			<form:label path="lastName" cssClass="formlebel"><spring:message code="lastname.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:input path="lastName" cssClass="forminput" /></td>
	</tr>
	<jsp:include page="/pages/datepicker.jsp" />
	<tr>
		<td></td>
		<td><form:errors path="country" cssClass="error" /></td>
	</tr>
<c:choose>
<c:when test="${isEdit == 'true'}">
	<tr class="formtr">
		<td>
			<form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:select path="country" cssClass="formlist" onchange="onCountryChange('state', 'regions', this.value);">
			<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${countryList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
</c:when>
<c:otherwise>
	<tr class="formtr">
		<td>
			<form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:select path="country" cssClass="formlist" onchange="getMasterData('state', 'regions', this.value);">
			<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${countryList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
</c:otherwise>
</c:choose>
	<tr>
		<td></td>
		<td><form:errors path="addrOne" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td>
			<form:label path="addrOne" cssClass="formlebel"><spring:message code="address1.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:textarea path="addrOne" cssClass="forminput" /></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="addrTwo" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td><form:label path="addrTwo" cssClass="formlebel"><spring:message code="address2.lbl"/></form:label></td>
		<td><form:textarea path="addrTwo" cssClass="forminput" /></td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="city" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td>
			<form:label path="city" cssClass="formlebel"><spring:message code="city.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><form:input path="city" cssClass="forminput" /></td>
	</tr>
	<tr class="formtr">
		<td><form:label path="state" cssClass="formlebel"><spring:message code="stateorregion.lbl"/></form:label></td>
		<td><form:select path="state" cssClass="formlist">
		<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${stateList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td><form:errors path="postalCode" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td><form:label path="postalCode" cssClass="formlebel"><spring:message code="zipcode.lbl"/></form:label></td>
		<td><form:input path="postalCode" cssClass="forminput" /></td>
	</tr>
	<c:if test="${isphoneupdate == 'update'}">
	<tr>
		<td></td>
		<td><form:errors path="phoneCode" cssClass="error" /></td>
	</tr>
 
	<tr>
		<td></td>
		<td><form:errors path="phoneNo" cssClass="error" /></td>
	</tr>
	 <tr class="formtr">
		<td>
			<form:label path="phoneNo" cssClass="formlebel"><spring:message code="phone.number.lbl"/><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td><%-- <form:select path="phoneCode" cssClass="formlist"  style="width: 100px">
			<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${phoneCodes}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>  --%>
			<form:input path="phoneCode" class="phonecodeinput" /> - <form:input  path="phoneNo" class="phoneinput" /></td>
	</tr>
	</c:if> 
