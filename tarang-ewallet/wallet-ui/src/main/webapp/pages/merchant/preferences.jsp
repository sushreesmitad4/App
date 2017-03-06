<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type='text/javascript'>

	function cancelAction() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant/dashboard';
	}
	
	function submitAction() {
		document.getElementById("userType").value = "M";
		submitFormData(document.preferencesform);
	}
</script>
<div class="pageheading"><spring:message code="preferences.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="preferencesform" action="preferences" method="POST" commandName="pereferencesForm">
			<table class="form" style="width: 40%">
				<!-- Commented Currency In Preferences -->
				<%-- 				
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="currency" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd" style="width:26%"><form:label path="currency" cssClass="formlebel"><spring:message code="currency.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
					<td class="formtd">
						<form:select path="currency" cssClass="formlist">
							<form:option value="0" ><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${currencyList}" var="std">
								<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				 --%>
 				<!-- Commented Currency In Preferences -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="language" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd" style="width:26%">
						<form:label path="language" cssClass="formlebel"><spring:message code="language.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:select path="language" cssClass="formlist">
							<form:option value="0" ><spring:message code="please.select.lbl" /></form:option>
							<c:forEach items="${languageId}" var="std">
								<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				<tr class="formtr">
					<td  colspan=2 align="center"> 
					<form:hidden path="id"/><form:hidden path="userType"></form:hidden>
					<div class="formbtns">
						<input type="button" onclick="submitAction()" class="styled-button" value='<spring:message code="setpreferences.lbl" />' />
						<input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="cancel.lbl" />' />
					</div>
					</td>
				</tr>
			</table>
</form:form>