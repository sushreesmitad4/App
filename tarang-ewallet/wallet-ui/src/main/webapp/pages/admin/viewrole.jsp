<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<script type='text/javascript'>
	function cancelAction() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles';
	}
</script>

<div class="pageheading"><spring:message code="view.role.lbl" /></div>
<form:form commandName="roleForm" action="viewrole">
	
	<table class="form" style="width: 35%">
		<tr>
			<td colspan="2"><form:hidden path="id"></form:hidden></td>
		</tr>
		<tr>
			<td><form:label path="name" cssClass="formlebel"><spring:message code="name.lbl" /></form:label></td>
			<td><form:label path="name" cssClass="forminput">${roleForm.name}</form:label></td>
		</tr>
		<tr>
			<td><form:label path="description" cssClass="formlebel"><spring:message code="description.lbl" /></form:label></td>
			<td><form:label path="name" cssClass="forminput">${roleForm.description}</form:label></td>
		</tr>
		<tr>
			<td>
				<form:label path="menuDetails" cssClass="formlebel"><spring:message code="menu.details.lbl" /></form:label>
				<form:hidden path="menuDetails"></form:hidden>
			</td>
		</tr>
		<tr>
			<td colspan=2><jsp:include page="menus.jsp"></jsp:include></td>
		</tr>
	    <tr>
	    	<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="back.lbl" />' />
				</div>
			</td>
		</tr>
	</table>
</form:form>