<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">

	$(document).ready(function(){
		document.getElementById("duration").disabled=true;
		document.getElementById("sequence").disabled=true;
	});
	
	function forEnable(){
		if(document.forms[0].recurring.checked==true){
		document.getElementById("duration").disabled=false;
		document.getElementById("sequence").disabled=false;
		}
		else if(document.forms[0].recurring.checked==false){
			document.getElementById("duration").disabled=true;
			document.getElementById("sequence").disabled=true;
			}
	}

	function forCancel() {
	    var message = '<spring:message code="cancel.confirm.msg"/>';
		var yesAction = function () {
      	 				window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customer';
       				};
   		 confirmationDialog(yesAction, null, null, message); 	
	} 
	
	function forCountinue() {
		submitFormData(document.pform);
	}
</script>


<form:form name="pform" method="POST" commandName="customerTransactionForm" action="#">
	<div class="pagelayout">
		<div class="block" id="sample">
			<h2><span align="center"><spring:message code="send.money.lbl" /></span></h2>
			<table class="form" style="width: 40%">
				<!-- transactionType -->
				<tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="transactionType" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd"></td>
					<td><form:radiobutton path="transactionType" value="1" /><spring:message code="p2p.lbl" />&nbsp;&nbsp;
						<form:radiobutton path="transactionType" value="2" /><spring:message code="p2m.lbl" /></td>
				</tr>
				<tr>
					<!-- send to -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="sendTo" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="sendTo" cssClass="formlebel">
						<spring:message code="send.to.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:select path="sendTo"  cssClass="formlist">
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
							<spring:message code="please.select.lbl" />
						</form:option>
						<c:forEach items="${transactionTypeList}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select>
					</td>
				</tr>
				<!-- emailId -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="emailId" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="emailId" cssClass="formlebel">
							<spring:message code="emailid.lbl"/><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:input path="emailId" cssClass="forminput" />
					</td>
				</tr>
				<!-- name -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="name" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="name" cssClass="formlebel">
							<spring:message code="name.lbl" />
						</form:label>
					</td>
					<td class="formtd">
						<form:input path="name" cssClass="forminput" />
					</td>
				</tr>
				<!-- phoneNo -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="phoneCode" cssClass="error" /></td>
				</tr>
 
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="phoneNo" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd"><form:label path="phoneNo" cssClass="formlebel"><spring:message code="phone.number.lbl"/></form:label></td>
					<td><form:input  path="phoneCode" class="phonecodeinput" />-<form:input  path="phoneNo" class="phoneinput" /></td>
				</tr>
				<!-- currency -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="currency" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="currency" cssClass="formlebel">
							<spring:message code="currency.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:select path="currency"  cssClass="formlist">
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
							<spring:message code="please.select.lbl" />
						</form:option>
						<c:forEach items="${currencyList}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select>
					</td>
				</tr>
				<!-- amount -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="amount" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="amount" cssClass="formlebel">
							<spring:message code="amount.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:input path="amount" cssClass="forminput" />
					</td>
				</tr>
				<!-- recurring -->
				<tr>
					<td></td>
					<td class="formtd"><form:errors path="recurring" cssClass="error" /></td>
				</tr>
				<tr>
					<td class="formtd" style="width: 36%">
						<form:label path="recurring" cssClass="formlebel"> 
			      			<spring:message code="recurring.lbl" />
						</form:label>
					</td>
					<td class="formtd"><form:checkbox path="recurring" onclick="forEnable();"/></td>
	        	</tr> 
	        	<!-- duration -->
	       		 <tr>
					<td></td>
					<td class="formerror"><form:errors path="duration" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="duration" cssClass="formlebel">
						<spring:message code="duration.lbl" />
						</form:label>
					</td>
					<td class="formtd"><form:select path="duration"  cssClass="formlist">
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
							<spring:message code="please.select.lbl" />
						</form:option>
						<c:forEach items="${durationList}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select>
					</td>
				</tr>
				<!--sequence -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="sequence" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="sequence" cssClass="formlebel">
						<spring:message code="sequence.lbl" />
						</form:label>
					</td>
					<td class="formtd"><form:select path="sequence"  cssClass="formlist">
						<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
							<spring:message code="please.select.lbl" />
						</form:option>
						<c:forEach items="${sequence}" var="entry">
							<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						</c:forEach>
					</form:select>
					</td>
				</tr>
				<!-- message -->
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="message" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:input path="message" cssClass="forminput" />
					</td>
				</tr>
				<tr class="formtr"><td></td>
					<td class="formtdbtn">
						<input type="button" class="styled-button" value='<spring:message code="pay.lbl" />' onclick="forCountinue();" />
						<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel();" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</form:form>			