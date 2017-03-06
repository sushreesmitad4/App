<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: March 05, 2013
  - @(#)
  - Description: This page is to submit response for payment request
 --%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<title><spring:message code="payment.lbl" /></title>
		<link href="../images/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
		<script type="text/javascript">
			$(document).ready(function() {
				document.payment.submit(); 
			});
		</script>
	</head>
	<body>
		<spring:message code="pls.wait.lbl" />
		<form:form name="payment" method="POST" commandName="paymentDto"  action="${merchantFailureUrl}"> 
			<form:hidden path="status"/>
			<form:hidden path="errorCode"/>
			<form:hidden path="errorMessage"/>
			<form:hidden path="amount"/>
			<form:hidden path="currency"/>
			<form:hidden path="orderId"/>
		</form:form>
	</body>
</html>
