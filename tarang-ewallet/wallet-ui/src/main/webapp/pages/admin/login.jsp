
  <!-- - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Login module. This includes User Login module
 -->

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">

	$(window).load(function () {   
		document.forms[0].email.focus();
	});

	function clearForm() {
		document.getElementById("email").value = "";
		document.getElementById("password").value = "";
		document.forms[0].email.focus();
		$('#suMsg').html('');
		$('#erMsg').html('');
	}
	
	function continueAction(){
		submitFormData(document.loginUserForm);
	}
	
</script>
	
<div class="pageheading"><spring:message code="admin.login.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form commandName="loginUserForm" autocomplete="off" method="POST" name="loginUserForm">
	<table class="form" style="width:40%;">
		<tr>
			<td></td>
		  	<td><form:errors path="email" cssClass="error"/></td>
		 </tr>
		 <tr>
		 	<td align="right">
		 		<form:label path="email" cssClass="formlebel"><spring:message code="emailid.lbl"/></form:label>&nbsp;&nbsp;
		 	</td>
		  	<td><form:input id="email" path="email" autocomplete="off" cssClass="forminput"/>
		</tr>
		<tr><td colspan="2"></td></tr>
		<tr>
			<td></td>
			<td><form:errors path="password" cssClass="error"/></td>
		</tr>
  		<tr>
			<td align="right">
				<form:label path="password" cssClass="formlebel"><spring:message code="password.lbl" /></form:label>&nbsp;&nbsp;
			</td>
			<td align="left"><form:password id="password" path="password" autocomplete="off" cssClass="forminput"/>
		</tr>
		<tr><td colspan="2"></td></tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
					<input id="login-button" type="submit" class="styled-button" value='<spring:message code="login.lbl"/>' onclick="continueAction()" />
					<input id="login-button" type="button" class="styled-button" value='<spring:message code="reset.lbl"/>' onclick="clearForm()" />
				</div>
			</td>
		</tr>
	</table>
</form:form>
