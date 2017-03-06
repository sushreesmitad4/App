<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form commandName="walletform" action="welcome"  >
<center>
	<table>
	<h2>Response From Wallet Book Shop Application</h2>
	<h2>
		<tr><td>SUCCESS CODE</td><td>${walletform.successCode}</td></tr>
		<tr><td>SUCCESS MESSAGE</td><td>${walletform.successMessage}</td></tr>
		<tr><td>AMOUNT</td><td>${walletform.amount} &nbsp; &nbsp; ${walletform.currency}</td></tr>
		<tr><td>TXNID</td><td>${walletform.txnId}</td></tr>
		<tr><td>ORDERID</td><td>${walletform.orderId}</td></tr>
		<c:if test="${walletform.expCheckOut eq true}">
				<tr><td>ADDRESS 1</td><td>${walletform.addressOne}</td></tr>
				<tr><td>ADDRESS 2</td><td>${walletform.addressTwo}</td></tr>
				<tr><td>CITY</td><td>${walletform.city}</td></tr>
				<tr><td>REGION</td><td>${walletform.region}</td></tr>
				<tr><td>COUNTRY</td><td>${walletform.country}</td></tr>
				<tr><td>ZIPCODE</td><td>${walletform.zipcode}</td></tr>
		</c:if>
		
	</h2>	
	</table>
	</center>
</form:form>


