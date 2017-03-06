<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.form.CustomerRegFormOne,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>

<script type="text/javascript">

	function update() {
		document.getElementById("mode").value = "update";
		submitFormData(document.customerRegFormTwo);
	}
	function cancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
        				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customer';
        			};
        confirmationDialog(yesAction, null, null, message);				
	} 
	
</script>

<div class="pageheading"><spring:message code="customer.details.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" commandName="customerRegFormTwo" action="updateprofile" name="customerRegFormTwo">
		<h3><span><spring:message code="hint.questions.lbl" /></span></h3>
		<table class="form" style="width: 55%">
			<tr class="formtr">
			<td class="formtd" colspan="2">
				<jsp:include page="/pages/customer/commonhintquestions.jsp"></jsp:include>
			</td>
			</tr>
		</table>
		<h3><span><spring:message code="personal.details.lbl" /></span></h3>
		<table class="form" style="width: 55%">
			<tr class="formtr">
			<td class="formtd" colspan="2">
				    <jsp:include page="/pages/customer/commoncustomerdetails.jsp"></jsp:include>
				</td>
			</tr>
		</table>
		<h3><span><spring:message code="authentication.details.lbl" /></span></h3>
		<table class="form" style="width: 55%">
			<tr class="formtr">
				<td class="formtd" style="width: 26%">
					<form:label path="emailId" cssClass="formlebel">
						<spring:message code="emailid.lbl"/><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td class="formtd"><form:input path="emailId" readonly="true" cssClass="forminput" /></td>
			</tr>
			<tr class="formtr">
				<td class="formtd"><form:label path="creationDate" cssClass="formlebel"><spring:message code="creationdate.lbl" /></form:label></td>
				<td>
					<form:hidden path="creationDate" readonly="true"  style="width: 10%"/>
					<c:out value="${customerRegFormTwo.creationDate}" />
				</td>
			</tr>
		</table>
		<table>
			<tr class="formtr">
				<td><form:hidden path="id" /><form:hidden path="mode" /></td>
			</tr>
			 <tr>
				<td colspan=2 align="center">
			      <div class="formbtns">
					<input type="button" class="styled-button" value='<spring:message code="update.lbl" />'	onclick="update()" />
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
				  </div>
				</td>
			</tr>
		</table>
</form:form>