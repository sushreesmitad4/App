<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">
	
	function loadProducts(){
		popupWindow = window.open('<%=request.getContextPath()%>/pages/terms.jsp', 'TermsAndCondition', 'scrollbars=yes, width=550, height=500');
	}

	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
				        	document.getElementById("mode").value = "";	
				            window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
				        };
		 confirmationDialog(yesAction, null, null, message);
	}
	
	function forRegister(){
		continueAction();
	}
	
	function forBack(){
		document.getElementById("mode").value = "back";		
		document.regtwo.submit();
		ajaxLoader($("body"));
	}
	
	function continueAction(){
		submitFormData(document.regtwo);
	} 
</script>

<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" commandName="customerRegFormTwo" action="registrationtwo" name="regtwo">
		<div class="block">
			<table class="form" style="width: 55%">
				<jsp:include page="/pages/customer/commoncustomerdetails.jsp" />
				<tr>
					<td></td>
					<td><form:errors path="terms" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:hidden path="emailId" />
						<%-- <form:hidden path="cemailId"/> --%>
						<form:hidden path="password"/>
						<form:hidden path="hintQuestion1"/>
						<form:hidden path="answer1"/>
						<form:hidden path="securityCode"/>
						<form:hidden path="oldEmailId" />
						<form:hidden path="phoneCode"></form:hidden>
					   <form:hidden path="phoneNo"></form:hidden>
					   <form:hidden path="mobileCode"></form:hidden>
					   <form:hidden path="mobileNo"></form:hidden>
					   <form:hidden path="terms"></form:hidden>
					</td>
					<%-- <td><spring:message code="yes.lbl" />
						<form:checkbox path="terms" />
						<spring:message code="agree.lbl" />
						<a href="javascript:void(o)" style="text-decoration: none;" onclick="loadProducts()">
							&nbsp;<spring:message code="termsandconditions.lbl" />
						</a>
					</td> --%>
					<td>
					<form:checkbox path="profileComplet" />
					<spring:message code="skip&continue.lbl" />
					</td>
				</tr>			
				<tr class="formtr">
					<td><form:hidden path="mode"></form:hidden></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
				        <div class="formbtns">
							<input type="button" class="styled-button" value='<spring:message code="register.lbl" />' onclick="forRegister();"/>
							<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack();" /> 
							<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel();" />
					    </div>
					</td>
				 </tr>
			</table>		
		</div>
</form:form>