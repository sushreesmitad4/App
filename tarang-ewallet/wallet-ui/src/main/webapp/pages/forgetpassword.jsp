<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Forget Password module. This includes User Login module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="emailId" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="emailId" cssClass="formlebel"><spring:message code="emailid.lbl"/><span class="mfield">&nbsp;*</span></form:label></td>
		<td class="formtd"><form:input path="emailId" cssClass="forminput"></form:input></td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="question1" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="question1" cssClass="formlebel"><spring:message code="hintquestion.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
		<td class="formtd"><form:select path="question1" cssClass="formlist"> 
				<form:option value="0">
					<spring:message code="please.select.lbl" />
				</form:option>
				<c:forEach items="${questions}" var="std">
					<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="hint1" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd"><form:label path="hint1" cssClass="formlebel"><spring:message code="hintanswer.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
		<td class="formtd"><form:input path="hint1" cssClass="forminput"></form:input></td>
	</tr>