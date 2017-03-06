<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>

<script>
	$(document).ready(function() {
		checkTransaction();
	});

	function avlBalance(){
    	var stringBalances = document.getElementById('walletBalances').value;
    	var arrayBalances = stringBalances.split(",");
    	for (var i = 0; i < arrayBalances.length; i++){
    		var fromWallet = document.getElementById("fromWallet").value;
    		var towallet = arrayBalances[i].split(":");
    		
    		if(fromWallet == (towallet[0])){
    			 document.getElementById('balance').value = towallet[1]; 
    			 return;
    		}
    		else{
    			document.getElementById('balance').value = ""; 
    		}
    	}
   }
   
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	                var v = "cancel";
  	 				window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingle?mode=' + v;
   				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function forContinue() {
		submitFormData(document.selfform);
	}
	
</script>

<div class="pageheading"><spring:message code="self.transfer.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="selfform" method="POST" commandName="selfTransferForm" action="selftransfer">
	<table class="form" style="width: 45%">
		<tr>
			<td></td>
			<td><form:errors path="fromWallet" cssClass="error" /></td>
		</tr>
		<tr>
			<td class="formtd"><form:label path="fromWallet"
					cssClass="formlebel">
					<spring:message code="fromwallet.lbl" />
					<span class="mfield">&nbsp;*</span>
				</form:label></td>
			<td class="formtd"><form:select path="fromWallet"
					cssClass="formlist"
					onchange="getMasterData('towallet', 'selftranscurrency', this.value);"
					onclick="avlBalance()">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${fromwallets}" var="std">
						<form:option value="${std.key}" title="${std.value}"
							label="${std.value}" />
					</c:forEach>
				</form:select></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="balance" cssClass="error" /></td>
		</tr>
		<tr>
			<td class="formtd"><form:label path="balance"
					cssClass="formlebel">
					<spring:message code="balance.lbl" />
					<span class="mfield">&nbsp;</span>
				</form:label></td>
			<td class="formtd" align="left"><form:input path="balance"
					cssClass="forminput" id="balance" readonly="true"></form:input></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="towallet" cssClass="error" /></td>
		</tr>
		<tr>
			<td class="formtd"><form:label path="towallet"
					cssClass="formlebel">
					<spring:message code="towallet.lbl" />
					<span class="mfield">&nbsp;*</span>
				</form:label></td>
			<td class="formtd"><form:select path="towallet"
					cssClass="formlist">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${towallets}" var="std">
						<form:option value="${std.key}" title="${std.value}"
							label="${std.value}" />
					</c:forEach>
				</form:select></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="requestedAmount" cssClass="error" /></td>
		</tr>
		<tr>
			<td class="formtd"><form:label path="requestedAmount"
					cssClass="formlebel">
					<spring:message code="requested.amount.lbl" />
					<span class="mfield">&nbsp;*</span>
				</form:label></td>
			<td class="formtd"><form:input path="requestedAmount"
					cssClass="forminput"></form:input></td>
		</tr>
		<tr class="formtr">
			<td><form:hidden path="walletBalances" id="walletBalances" /></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" class="styled-button" value='<spring:message code="transfer.lbl" />' onclick="forContinue();" /> 
					<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />' onclick="forCancel();" />
				</div>
			</td>
		</tr>
	</table>
</form:form>