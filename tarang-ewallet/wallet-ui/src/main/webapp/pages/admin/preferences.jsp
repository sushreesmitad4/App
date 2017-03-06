<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type='text/javascript'>

	function cancelAction() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin';
	}
	
	function submitAction() {
		document.getElementById("userType").value = "C";
		continueAction();
	}
	
	function continueAction(){
		submitFormData(document.fpereferences);
	}
	
</script>

<div class="pageheading"><spring:message code="preferences.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form action="adminpreferences" method="POST" commandName="pereferencesForm" name="fpereferences">	
	<table class="form" style="width: 45%">
		
		<tr>
			<td></td>
			<td><form:errors path="language" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="language" cssClass="formlebel"><spring:message code="language.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td>
				<form:select path="language" cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${languageId}" var="std">
						<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td><form:hidden path="userType" /><form:hidden path="currency" /><form:hidden path="id" /></td>
		</tr>
		<tr>		
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" onclick="submitAction()" class="styled-button"  value='<spring:message code="setpreferences.lbl" />' />
					<input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="cancel.lbl" />' />
				</div>
			</td>
		</tr>
	</table>
</form:form>