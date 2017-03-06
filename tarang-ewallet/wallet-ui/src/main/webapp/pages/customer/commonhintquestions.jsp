<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<tr>
		<td></td>
		<td class="formerror"><form:errors path="hintQuestion1" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd" style="width:26%">
			<form:label path="hintQuestion1" cssClass="formlebel"><spring:message code="hintquestion.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td class="formtd">
		<form:select path="hintQuestion1" cssClass="formlist">
			<form:option value="0" ><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${questions}" var="std">
					<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
				</c:forEach>
			</form:select>
		</td>
	</tr>
	<tr>
		<td></td>
		<td class="formerror"><form:errors path="answer1" cssClass="error" /></td>
	</tr>
	<tr class="formtr">
		<td class="formtd">
			<form:label path="answer1" cssClass="formlebel"><spring:message code="hintanswer.lbl" /><span class="mfield">&nbsp;*</span></form:label>
		</td>
		<td class="formtd"><form:input path="answer1" cssClass="forminput" /></td>
	</tr>	