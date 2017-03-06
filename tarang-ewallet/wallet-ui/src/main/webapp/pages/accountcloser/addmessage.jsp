<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />

<script type="text/javascript">
	function continueAction() {
		submitFormData(document.addmsg);
	}
</script>
	
<div class="pageheading"><spring:message code="account.closing.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="addmsg" method="POST" commandName="accountCloserForm" action="addmessage">
	<table class="form" style="width: 40%">
				<tr>
					<td></td>
					<td><form:errors path="message" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="message" cssClass="formlebel"><spring:message code="message.lbl"/><span class="mfield">&nbsp;*</span></form:label>
					</td>
					<td><form:textarea path="message" cssClass="forminput" /></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
				       <div class="formbtns">
						<input type="button" onclick="continueAction()" class="styled-button" value='<spring:message code="send.lbl" />' />
					   </div>
					</td>
				</tr>
				<tr>
					<td><form:hidden path="acountCloserId" /></td>
					<td></td>
				</tr>
			</table>
</form:form>