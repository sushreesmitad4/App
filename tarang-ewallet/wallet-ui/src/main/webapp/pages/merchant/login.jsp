<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Login module. This includes User Login module
 --%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
	<script type="text/javascript">
		
	$(window).load(function () {   
		document.forms[0].email.focus();
	});

	function cancelAction() {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
	}
	
	function submitAction() {
		submitFormData(document.mlogin);
	}
</script>
	
<div class="pageheading"><spring:message code="merchant.login.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="mlogin" commandName="loginUserForm" method="POST">
		
			<table class="form" style="width: 50%">
				<tr></tr>
				<tr>
					<td><form:hidden path="userType"></form:hidden></td>
				</tr>
				<tr></tr>
				<tr>
	      			<td></td>
		  			<td class="formerror"><form:errors path="email" cssClass="error"/></td>
		  		</tr>
		  		<tr>
					<td width="30%" align="right">
						<form:label path="email" cssClass="formlebel">
							<spring:message code="emailid.lbl"/>
						</form:label> 
						&nbsp; &nbsp; 
					</td>
					<td width="70%" align="left"><form:input id="emailId" path="email" autocomplete="off" cssClass="forminput"/></td>
				</tr>
				<tr>
	      			<td></td>
		  			<td class="formerror"><form:errors path="password" cssClass="error"/></td>
		  		</tr>
				<tr>
					<td align="right">
						<form:label path="password" cssClass="formlebel"><spring:message code="password.lbl" /></form:label> &nbsp; &nbsp;
					</td>
					<td align="left"><form:password id="passwordId" path="password" autocomplete="off" cssClass="forminput"/>
				</tr>
				<tr>
					<td></td>
					<td align="left">
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantlogin/forgotpassword">
							<spring:message code="forgot.password.lbl"/>
						</a>
						&nbsp; &nbsp; 
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant/registration">
							<spring:message code="signup.lbl"/>
						</a>
					</td>
				</tr>
				<tr>
					<td colspan=2></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
						<div class="formbtns">
						  <input id="login-button" type="button" class="styled-button" value='<spring:message code="login.lbl"/>' onclick="submitAction()" />
						  <input id="login-button" type="button" class="styled-button" value='<spring:message code="cancel.lbl"/>' onclick="cancelAction()" />
					    </div>
					</td>
				</tr>
			</table>
</form:form>