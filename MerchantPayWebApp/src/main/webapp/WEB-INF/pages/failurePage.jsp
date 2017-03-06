<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<form:form commandName="walletform" action="welcome"  >
<center>
	<table>
	<h2>Response From Wallet Book Shop Application</h2>
	<h2>
		<tr><td>ERROR CODE</td><td>${walletform.errorCode}</td></tr>
		<tr><td>ERROR MESSAGE</td><td>${walletform.errorMessage}</td></tr>
	</h2>	
	</table>
	</center>
</form:form>


