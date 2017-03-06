<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<form:form commandName="walletform" action="${walleturl};jsessionid=${walletform.jsessionid}"  >
	<center>
	<table>
	
		<h2>Wallet Merchant Confirmation Page</h2>
		<tr>
			<td>Book Name</td>
			<td>Quantity</td>
			<td>Unit Price</td>
			<td>Total</td><td>
		</tr>
		
		<tr>
			<td>${walletform.bookName}</td>
			<td>${walletform.qtyList}</td>
			<td>${walletform.unitPrice}</td>
			<td>${walletform.total}</td>
		</tr>

		<tr>
			<td>${walletform.bookName1}</td>
			<td>${walletform.qtyList1}</td>
			<td>${walletform.unitPrice1}</td>
			<td>${walletform.total1}</td>
		</tr>
		<tr>
			<td>All Book Cost Total :</td>
			<td></td>
			<td></td>
			<td>${walletform.amount}</td>
		</tr>
		<tr>
			<td colspan="4"></td>
		</tr>
		<tr>
			<td colspan="4">
				<form:hidden path="orderId" ></form:hidden>
				<form:hidden path="amount" ></form:hidden>
				<form:hidden path="currency" ></form:hidden>
				<form:hidden path="merchantId" ></form:hidden>
				<form:hidden path="merchantCode" ></form:hidden>
				<form:hidden path="jsessionid" ></form:hidden>
				<input type="submit" value="Submit"></input>
			</td>
		</tr>
		
		
	</table>
	
	</center>
</form:form>



