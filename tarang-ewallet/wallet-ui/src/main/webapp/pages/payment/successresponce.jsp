<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
		<title><spring:message code="payment.lbl" /></title>
		<link href="../images/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
		<script type="text/javascript">
			$(document).ready(function() {
				document.paymentresonse.submit(); 
			});
		</script>
	</head>
	<body>
		<spring:message code="pls.wait.lbl" />
		<form:form name="paymentresonse" method="POST" commandName="paymentDto"  action="${merchantSuccessUrl}"> 
			<form:hidden path="successMessage"/>
			<form:hidden path="successCode"/>
			<form:hidden path="amount"/>
			<form:hidden path="currency"/>
			<form:hidden path="txnId"/>
			<form:hidden path="orderId"/>
			<form:hidden path="status"/>
			<form:hidden path="expCheckOut"/>
			<form:hidden path="addressOne"/>
			<form:hidden path="addressTwo"/>
			<form:hidden path="city"/>
			<form:hidden path="region"/>
			<form:hidden path="country"/>
			<form:hidden path="zipcode"/>			
		</form:form>
	</body>
</html>
