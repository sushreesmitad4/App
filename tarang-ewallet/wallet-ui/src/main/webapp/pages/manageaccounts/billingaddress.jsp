<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for billing address.
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals"%>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="country" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td  class="formtd">
			<form:label path="country" cssClass="formlebel">
				<spring:message code="country.lbl"/>
			</form:label>
		</td>
		<%-- <td class="formtd">
			<form:select path="country" cssClass="formlist" onchange="getMasterData('state', 'regions', this.value);" >
				<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${countryList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td> --%>
		<td class="formtd"><c:out value="${addCardAccountForm.countryName}" /></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="addrOne" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="addrOne" cssClass="formlebel">
				<spring:message code="address1.lbl"/><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd"><form:textarea path="addrOne" cssClass="forminput"/></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="addrTwo" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="addrTwo" cssClass="formlebel"><spring:message code="address2.lbl"/></form:label></td>
		<td class="formtd"><form:textarea path="addrTwo" cssClass="forminput"/></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="city" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="city" cssClass="formlebel">
			<spring:message code="city.lbl"/><span class="mfield">&nbsp;*</span>
			</form:label>
		</td>
		<td class="formtd"><form:input path="city" cssClass="forminput" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="state" cssClass="formlebel"><spring:message code="stateorregion.lbl"/></form:label></td>
		<td class="formtd"><form:select path="state" cssClass="formlist">
		<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${stateList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}"
						label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="postalCode" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="postalCode" cssClass="formlebel"><spring:message code="zipcode.lbl"/></form:label></td>
		<td class="formtd"><form:input path="postalCode" cssClass="forminput" /></td>
	</tr>