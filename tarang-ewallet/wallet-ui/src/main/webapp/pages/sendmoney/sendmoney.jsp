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
		enableRecurring();
	});

	function forOccurence(){
		var v = 'totalOccurences';
		var fromDate = document.getElementById("fromDate").value;
		var toDate = document.getElementById("toDate").value;
		var frequency = document.getElementById("frequency").value;
		getTotalOccurences(v, fromDate, toDate, frequency); 
	}
   
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	                var v = "cancel";
  	 				window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingle?mode=' + v;
   				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function enableRecurring(){
		 if (document.forms[0].recurring.checked == false) {
			document.getElementById("jobName").disabled = true; 
			document.getElementById("fromDate").disabled = true; 
			document.getElementById("toDate").disabled = true; 
			document.forms[0].frequency.disabled = true ;
			document.getElementById("jobName").value = "";
			document.getElementById("fromDate").value = "";
			document.getElementById("toDate").value = "";
			document.getElementById("totalOccurences").value = "";
			document.forms[0].frequency.value = 0;
		}  
		else{
			document.getElementById("jobName").disabled = false; 
			document.getElementById("fromDate").disabled = false; 
			document.getElementById("toDate").disabled = false; 
			document.forms[0].frequency.disabled = false ;
		}
	}
	
	function forContinue() {
		submitFormData(document.tosingleform);
	}
</script>
<div class="pageheading"><spring:message code="moneytrnsf.singledestination" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="tosingleform" method="POST" commandName="sendMoneyForm" action="tosingle" >
			<table class="form" style="width:45%">
				<tr>
					<td></td>
					<td><form:errors path="emailId" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td class="formtd">
						<form:label path="emailId" cssClass="formlebel">
							<spring:message code="emailid.lbl"/>
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="emailId" cssClass="forminput"></form:input></td>
				</tr>
				
				<tr>
					<td></td>
					<td><form:errors path="requestedAmount" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td class="formtd">
						<form:label path="requestedAmount" cssClass="formlebel">
							<spring:message code="amount.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="requestedAmount" cssClass="forminput"></form:input></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="requestedCurrency" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="requestedCurrency" cssClass="formlebel">
							<spring:message code="currency.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label></td>
					<td class="formtd"><form:select path="requestedCurrency"  cssClass="formlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${currencyList}" var="std">
								<form:option value="${std.key}" title="${std.value}"
								label="${std.value}" />
							</c:forEach>
					</form:select> </td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="message" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl" />
							<span class="mfield">&nbsp;</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="message" cssClass="forminput"></form:input></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="destinationType" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="destinationType" cssClass="formlebel"><spring:message code="destination.type.lbl" />
							<span class="mfield" id="mandatory">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:select path="destinationType" cssClass="formlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${destinationType}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select></td>
				</tr>	
				<tr>
					<td></td>
					<td><form:errors path="recurring" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="recurring" cssClass="formlebel">
							<spring:message code="recurring.lbl" />
							<span class="mfield">&nbsp;</span>
						</form:label>
					</td>
					<td class="formtd" ><form:checkbox path="recurring" onclick="enableRecurring()"></form:checkbox></td>
				</tr>
				
				<tr>
					<td></td>
					<td><form:errors path="userJobName" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td class="formtd">
						<form:label path="userJobName" cssClass="formlebel">
							<spring:message code="jobname.lbl"/>
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="userJobName" id="jobName" cssClass="forminput"></form:input></td>
				</tr>
				
				
				<jsp:include page="/pages/sendmoney/fromdate.jsp" />
				<jsp:include page="/pages/sendmoney/todate.jsp" />
				<tr>
					<td></td>
					<td><form:errors path="frequency" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="frequency" cssClass="formlebel">
							<spring:message code="frequency.lbl" />
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd" align="left"><form:select path="frequency" cssClass="formlist" onchange="forOccurence();">
					<form:option value="0" label="Please Select" ></form:option>
					<c:forEach items="${durationList}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select></td>
				</tr>
				<tr>
					<td></td>
					<td><form:errors path="totalOccurences" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd">
						<form:label path="totalOccurences" cssClass="formlebel">
							<spring:message code="total.occurences.lbl" />
							<span class="mfield">&nbsp;</span>
						</form:label>
					</td>
					<td class="formtd" align="left">
						<form:input path="totalOccurences" cssClass="forminput" id="totalOccurences" readonly="true" ></form:input>
					</td>
				</tr>
				<input type="hidden" id="servicetype" name="servicetype" value="${servicetype}" ></input>
				<tr>
				  <td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="send.lbl" />' onclick="forContinue();" />
						<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
					</div>
				  </td>
				</tr>
			</table>
</form:form>