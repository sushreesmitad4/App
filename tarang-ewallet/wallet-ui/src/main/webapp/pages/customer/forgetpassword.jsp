<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Forget Password module. This includes User Login module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">
	function cancelAction(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home/login';
	}

	function continueAction(){
		submitFormData(document.forgotPasswordForm);
	} 
	
</script>
<div class="pageheading"><spring:message code="customer.forgot.password.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" commandName="forgotPasswordForm" autocomplete="off" action="forgotpassword" name="forgotPasswordForm">
		<table class="form" style="width: 50%">
					<jsp:include page="/pages/forgetpassword.jsp"></jsp:include>
					<tr>
						<td colspan=2 align="center">
				           <div class="formbtns">
							 <input type="button" class="styled-button" value='<spring:message code="submit.lbl"/>' onclick="continueAction()" />
							 <input type="button" class="styled-button" value='<spring:message code="cancel.lbl"/>' onclick="cancelAction()" />
						   </div>
						</td>
					</tr>
		</table>
</form:form>