<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Login module. This includes User Login module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	$(window).load(function () {   
		document.forms[0].email.focus();
	});
	function continueAction() {
		submitFormData(document.logincode);
	}
	function cancelAction() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
	}
	function checkForgotURL(){
		var users = $('input[name="userType"]:checked').val();
		if(users == "Customer"){
			window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerlogin/forgotpassword';
		}
		else{
			window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantlogin/forgotpassword';
		}
    }
	function checkSignURL(){
		var users = $('input[name="userType"]:checked').val();
		if(users == "Customer"){
			window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customer/registration';
		}
		else{
			window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant/registration';
		}
    }
	function Merchantlbl()
	{
	    document.getElementById("emaillbl").innerHTML="<spring:message code="emailid.lbl"/>";
	    document.getElementById("passlabl").innerHTML="<spring:message code="password.lbl"/>";
	}
	function customerlbl()
	{
	    document.getElementById("emaillbl").innerHTML="<spring:message code="mobile.number.lbl"/>";
	    document.getElementById("passlabl").innerHTML="<spring:message code="mpin.lbl" />";
	}
</script>
<div class="pageheading"><spring:message code="login.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="logincode" commandName="loginUserForm" method="POST" autocomplete="off" action="login">
			<table class="form" style="width: 50%">
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr><td colspan="2"></td></tr>
				<tr>
	      			<td></td>
		  			<td class="formerror"><form:errors path="email" cssClass="error"/></td>
		  		</tr>
		  		<tr>
					<td width="30%" align="right">
						<form:label path="email" id="emaillbl" cssClass="formlebel"><spring:message code="mobile.number.lbl"/> &nbsp;</form:label>
					</td>
					<td width="70%" align="left">
						<form:input id="emailId" path="email" autocomplete="off" cssClass="forminput"/>
					</td>
				</tr>
				<tr>
	      			<td></td>
		  			<td class="formerror"><form:errors path="password" cssClass="error"/>
		  		</tr>
		 		<tr>
					<td align="right">
						<form:label path="password" id="passlabl" cssClass="formlebel"><spring:message code="mpin.lbl" /></form:label>&nbsp;
					</td>
					<td align="left">
						<form:password id="passwordId" path="password" autocomplete="off" cssClass="forminput"/>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td align="left">
						<form:radiobutton title="Login as Customer" path="userType" onClick="customerlbl()"id="userTypeId" value="Customer" checked="true"/><spring:message code="customer.lbl" />
						<form:radiobutton title="Login as Merchant" path="userType" onClick="Merchantlbl()" id="userTypeId" value="Merchant"/><spring:message code="merchant.lbl" />
						<span><font color="red"><form:errors path="userType" /></font></span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td align="left">
						<a href="javascript:void(0)" onclick="checkForgotURL()"><spring:message code="forgot.password.lbl" /></a>&nbsp; &nbsp; 
						<a href="javascript:void(0)" onclick="checkSignURL()"><spring:message code="signup.lbl"/></a>
					</td>
				</tr>
				<tr><td colspan="2"></td></tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
						<div class="formbtns">
							<input id="login-button" type="submit" class="styled-button" value='<spring:message code="login.lbl"/>' onclick="continueAction()" />
							<input id="login-button" type="button" class="styled-button" value='<spring:message code="cancel.lbl"/>' onclick="cancelAction()" />
						</div>	
					</td>
				</tr>
		</table>
</form:form>
