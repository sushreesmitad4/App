<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script>
function addUnitPrice(){
	var qtyListOne = document.getElementById("qtyList").value;
	document.getElementById("total").value = qtyListOne*5.0;
	onclickOfTotal();
}
function addUnitPrice1(){
	var qtyListTwo = document.getElementById("qtyList1").value;
	document.getElementById("total1").value = qtyListTwo*10.0;
	onclickOfTotal();
}

function onclickOfTotal(){
	var total = document.getElementById("total").value;
	
	var total1 = document.getElementById("total1").value;

	document.getElementById("alltotal").value = Number(total) + Number(total1);
}

</script>
<form:form commandName="walletform" action="welcome"  >
	<center>
	<table>
	
	<h2>Wallet Merchant</h2>
	<tr>
		<td>Book Name</td>
		<td>Quantity</td>
		<td>Unit Price</td>
		<td>Total</td>
	</tr>
	
	<tr>
		<td>${walletform.bookName}</td>
		<td>
			<form:select path="qtyList" onchange="addUnitPrice()">
				<form:option value="0">
					0
				</form:option>
				<c:forEach items="${quantityList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
		<td>${walletform.unitPrice}</td>
		<td><form:input path="total" /></td>
	</tr>
	
	<tr>
		<td>${walletform.bookName1}</td>
		<td>
			<form:select path="qtyList1" onchange="addUnitPrice1()">
				<form:option value="0">
					0
				</form:option>
				<c:forEach items="${quantityList1}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
				</c:forEach>
			</form:select>
		</td>
		<td>${walletform.unitPrice1}</td>
		<td><form:input path="total1" /></td>
	</tr>
	
	<tr>
		<td>All Book Cost Total :</td>
		<td></td>
		<td></td>
		<td><form:input path="alltotal" onclick="onclickOfTotal()" /></td>
	</tr>
	<tr></tr>
	<tr></tr>
	<tr><td><form:label path="payment" >Payment Options </form:label></td></tr>
	<tr>
		<td><span><input id="payment1" name="payment" checked="checked" type="radio" value="1"/><label for="payment1">Wallet</label></span>
		<span><input id="payment2" name="payment" type="radio" value="2"/><label for="payment2">Bank</label></span>
		<span><input id="payment3" name="payment" type="radio" value="3"/><label for="payment3">Card</label></span>
	</td>
	</tr>
	<tr>
		<td>
			<input type="submit" value="Submit"></input>
		</td>
	</tr>
	<form:hidden path="jsessionid" ></form:hidden>	
	</table>
	</center>
</form:form>



