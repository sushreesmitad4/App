<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type='text/javascript'>

	 function forProceed() {
		document.getElementById("mode").value = "proceed";
		submitFormData(document.reconcileconfirm);
	}
	
	function forBack() {
		document.getElementById("mode").value = "back";
		document.reconcileconfirm.submit();
		ajaxLoader($("body"));
	} 
	
	function forOnChange(v){
		if(v == 1){
			document.getElementById("selectedAmount").value = document.getElementById("actualAmount").value;
		}
		else if(v == 2){
			document.getElementById("selectedAmount").value = document.getElementById("txnAmount").value;
		}
	}
	
</script>

<div class="pageheading"><spring:message code="reconciliation.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form  method="POST" name="reconcileconfirm" commandName="reconciliationForm" action="saveAdjustedAmount">
		<div class="block">
			<table class="form" style="width: 50%">
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="actualAmount" cssClass="error" /></td>
				</tr>
				<tr>
					<td><input type="radio"  name="radio1"  onchange="forOnChange(1);"/>
						<form:label path="actualAmount" cssClass="formlebel"><spring:message code="db.amount.lbl" /></form:label>
					</td>
					<td>${reconciliationForm.actualAmount}  &nbsp; ${reconciliationForm.txnCurrency}</td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="txnAmount" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td><input type="radio" name="radio1"  onchange="forOnChange(2);"/>
						<form:label path="txnAmount" cssClass="formlebel"><spring:message code="file.amount.lbl" /></form:label>
					</td>
					<td>${reconciliationForm.txnAmount}  &nbsp; ${reconciliationForm.txnCurrency}</td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="selectedAmount" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="selectedAmount" cssClass="formlebel"><spring:message code="selected.amount.lbl" /></form:label>
					</td>
					<td><form:input path="selectedAmount"  readonly="true" ></form:input></td>
				</tr>
				<form:hidden path="id"/>
				<form:hidden path="fromDate"/>
				<form:hidden path="toDate"/>
				<form:hidden path="mode"/>
				<form:hidden path="actualAmount"/>
				<form:hidden path="txnAmount"/>
				</table>
				
				<table>
				<tr>
					<td colspan=2 align="center">
						<div class="formbtns">
						   <input type="button"  class="styled-button" value='<spring:message code="proceed.lbl"/>' onclick="forProceed()" />
						   <input type="button"  class="styled-button" value='<spring:message code="back.lbl"/>' onclick="forBack()" />
			    	  </div>
			    	</td>
				</tr>
				</table>
		</div>
</form:form>