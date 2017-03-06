<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />

<script type='text/javascript'>

	function cancelAction() {
		document.getElementById("mode").value = "cancel";
		document.sendfeedback.submit();
		ajaxLoader($("body"));
	}
	
	function sendAction() {
		document.getElementById("mode").value = "send";
		submitFormData(document.sendfeedback);
	}
	
</script>
<div class="pageheading"><spring:message code="feedbckform.lbl"/></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="sendfeedback" method="POST" commandName="feedbackForm" action="query">
			<table class="form" style="width: 50%">
				<tr>
				<td></td>
				<td class="formerror"><form:errors path="querryType" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="querryType" cssClass="formlebel">
							<spring:message code="select.type.lbl"/><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:radiobutton path="querryType" value="1" /><spring:message code="query.lbl"/></td>
				</tr>
				<tr>
					<td></td>
					<td><form:radiobutton path="querryType" value="2" /><spring:message code="feedback.lbl"/></td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="subject" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="subject" cssClass="formlebel">
							<spring:message code="subject.lbl"/><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:input path="subject" cssClass="forminput"></form:input></td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="message" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl"/><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:textarea path="message" cssClass="forminput" /></td>
				</tr>
				<tr class="formtr">
					<td><form:hidden path="mode"></form:hidden></td>
					</tr>
				<tr>
					<td colspan=2 align="center">
				       <div class="formbtns">
						 <input type="button" class="styled-button" value='<spring:message code="send.lbl" />' onclick="sendAction()" />
						 <input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancelAction()" />
					   </div>
					</td>
				</tr>
			</table>
</form:form>