<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="com.tarang.ewallet.walletui.util.HelpLinkConstants,java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<%@include file="/pages/helptipespopup.jsp" %>

<script>
	$(document).ready(function() {
		pedrop();
	});
	 
	function cancelAction() {
		var servicetype = document.getElementById("services").value;
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?' 
					+ 'servicetype' + '=' + servicetype;
		}
		confirmationDialog(yesAction, null, null, message);  
	} 
	
	function ajaxCallDropDown(){
		var feeSerId = document.getElementById("services").value;
		var userTypeId = document.getElementById("userType").value;
		getMasterData('operationType', 'operationtypes', feeSerId, userTypeId);
	}
	
	function hideAllStar(){
		$('#fcr').hide();
		$('#pr').hide();
	}
	
	function pedrop(){
		var radio = document.getElementById("payingentity").value;
		if(radio == 2){
			hideAllStar();
			radio2dropDown123();
		}
	}
	
	function onClickLowerLimSlb2(){
		 var v = document.getElementById("upperLimSlb1").value;
		 if(v != ""){
		 	document.getElementById("lowerLimSlb2").value = ">" + v;
		 }	
	}
	
	function onClickLowerLimSlb3() {
		 var v = document.getElementById("upperLimSlb2").value;
		 if(v != ""){
		 	document.getElementById("lowerLimSlb3").value = ">" + v;
		 }	
	}
	
	function radio2dropDown123(){
		var dropDown = document.getElementById("feeType").value;
		if(dropDown==0){
			disableTextField();
		}
		else if(dropDown == 1){
			$('#fcr').show();
			document.getElementById("fixCharRec").disabled = false;
			document.getElementById("percentageRec").disabled = true;
			//for slabs	
			for(var i = 1; i <= 3; i++){
				document.getElementById("fixCharRecSlb" + i).disabled = false;
				document.getElementById("percentageRecSlb" + i).disabled = true;
			}
		}
		else if(dropDown == 2){
			$('#pr').show();
			document.getElementById("fixCharRec").disabled = true;
			document.getElementById("percentageRec").disabled = false;
			//for slabs
			for(var i = 1; i <= 3; i++){
				document.getElementById("fixCharRecSlb" + i).disabled = true;
				document.getElementById("percentageRecSlb" + i).disabled = false;
			}
		}
		else if(dropDown == 3){
			$('#fcr').show();
			$('#pr').show();
			document.getElementById("fixCharRec").disabled = false;
			document.getElementById("percentageRec").disabled = false;
			//for slabs	
			for(var i = 1; i <= 3; i++){
				document.getElementById("fixCharRecSlb" + i).disabled = false;
				document.getElementById("percentageRecSlb" + i).disabled = false;
			}
		}
	}	
	
	function pleaseSelectShowPS(){
		document.getElementById("feeType").value = 0;
		disableTextField();
	}
	
	function disableTextField(){
		document.getElementById("fixCharRec").disabled = true;
		$('#fcr').hide();
		document.getElementById("percentageRec").disabled = true;
		$('#pr').hide();
		//for slabs	
		for(var i = 1; i <= 3; i++){
			document.getElementById("fixCharRecSlb" + i).disabled = true;
			document.getElementById("percentageRecSlb" + i).disabled = true;
		}
	}
		
	function continueAction(){
		submitFormData(document.feeForm);
	}

</script>
<div class="pageheading"><spring:message code="fin.add.fee.non.per.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form commandName="feeMgmtForm" action="addfee" name="feeForm" >
	<table class="form" style="width: 50%">
		<!-- User Type -->
		<tr>
			<td></td>
			<td><form:errors path="userType" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="userType" cssClass="formlebel"><spring:message code="user.type.lbl" /></form:label>
				<form:hidden path="userType" ></form:hidden>
			</td>
			<td>${feeMgmtForm.userTypeName}</td>
		</tr>		
		<!-- Services  -->
		<form:hidden path="services" ></form:hidden>
		<form:hidden path="payingentity" ></form:hidden>
		<form:hidden path="currency" ></form:hidden>
		<form:hidden path="validation" ></form:hidden>
		<!-- Operation Type --> 
        <tr>
			<td></td>
			<td><form:errors path="operationType" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="operationType" cssClass="formlebel">
					<spring:message code="operation.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="operationType"  cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${operationTypeList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		 <!-- Country --> 
        <tr>
			<td></td>
			<td><form:errors path="country" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="country" cssClass="formlebel">
					<spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="country"  cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${countryList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<!-- Currency -->
		<tr>
			<td></td>
			<td><form:errors path="timeFreequency" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="timeFreequency" cssClass="formlebel">
				<spring:message code="time.freequency.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="timeFreequency"  cssClass="formlist">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${timePeriodList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<!-- Recipient -->
		<!-- Fee Type -->
		<tr>
			<td></td>
			<td><form:errors path="feeType" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="feeType" cssClass="formlebel">
					<spring:message code="fee.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="feeType"  onchange="pedrop();" cssClass="formlist">
				<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
					<spring:message code="please.select.lbl" />
				</form:option>
				<c:forEach items="${feeTypeList}" var="entry">
					<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
				</c:forEach>
				</form:select>
			</td>
		</tr>
		<!-- Fixed Charge (Sender) -->
		<!-- Percentage (Sender) -->
		<!-- Fixed Charge (Reciever) -->
		<tr>
			<td></td>
			<td><form:errors path="fixCharRec" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="fixCharRec" cssClass="formlebel">
					<spring:message code="flat.fee.default.lbl" /><span id="fcr" class="mfield">&nbsp;*</span>
				</form:label></td>
			<td><form:input path="fixCharRec" cssClass="forminput" /></td>
		</tr>
		<!-- Percentage -->
		<tr>
			<td></td>
			<td><form:errors path="percentageRec" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="percentageRec" cssClass="formlebel">
					<spring:message code="percentage.default.lbl" />
					<span id="pr" class="mfield">&nbsp;*</span>
				</form:label></td>
			<td><form:input path="percentageRec" cssClass="forminput" /></td>
		</tr>
	    <!--  Slabs table -->
		<tr>
			<td colspan="2">
				<table>
					<tr>
						<td colspan="2">
							<table><tr>
								<td style="padding:0px;"><form:label path="lowerLimSlb1" cssClass="formlebel"><spring:message code="slabs.lbl" /></form:label>
								<td><%=showHelpTipes(HelpLinkConstants.FEE_SLABS_TIPS, request) %></td>
							</tr></table>
						</td>
					</tr>
					<tr class="centerstyle">
						<td>
							<form:label path="lowerLimSlb1" >
								<spring:message code="lower.limit.lbl" />	
							</form:label>
						</td>			
						<td>
							<form:label path="upperLimSlb1" >
								<spring:message code="upper.limit.lbl" />	
							</form:label>
						</td>
						<td>
							<form:label path="fixCharRecSlb1" >
								<spring:message code="flat.fee.lbl" />	
							</form:label>
						</td>	
						<td>
							<form:label path="percentageRecSlb1" >
								<spring:message code="percentage.lbl" />	
							</form:label>
						</td>		
					</tr>
					<tr>
						<td><form:errors path="lowerLimSlb1"  cssClass="error" /></td>
						<td><form:errors path="upperLimSlb1" cssClass="error" /></td>
						<td><form:errors path="fixCharRecSlb1" cssClass="error" /></td>
						<td><form:errors path="percentageRecSlb1" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:input path="lowerLimSlb1" /></td>
						<td><form:input path="upperLimSlb1"/></td>
						<td><form:input path="fixCharRecSlb1"/></td>
						<td><form:input path="percentageRecSlb1"/></td>
					</tr>	
					<tr>
						<td width="5"><form:errors path="lowerLimSlb2"  cssClass="error" /></td>
						<td width="5"><form:errors path="upperLimSlb2" cssClass="error" /></td>
						<td width="5"><form:errors path="fixCharRecSlb2" cssClass="error" /></td>
						<td width="5"><form:errors path="percentageRecSlb2" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:input path="lowerLimSlb2" readonly="true" onclick="onClickLowerLimSlb2()"/></td>
						<td><form:input path="upperLimSlb2"/></td>
						<td><form:input path="fixCharRecSlb2"/></td>
						<td><form:input path="percentageRecSlb2"/></td>
					</tr>
					<tr>
					<td width="5"><form:errors path="lowerLimSlb3"  cssClass="error" /></td>
					<td width="5"><form:errors path="upperLimSlb3" cssClass="error" /></td>
					<td width="5"><form:errors path="fixCharRecSlb3" cssClass="error" /></td>
					<td width="5"><form:errors path="percentageRecSlb3" cssClass="error" /></td>
					</tr>
					<tr>
						<td><form:input path="lowerLimSlb3" readonly="true" onclick="onClickLowerLimSlb3()"/></td>
						<td><form:input path="upperLimSlb3"/></td>
						<td><form:input path="fixCharRecSlb3"/></td>
						<td><form:input path="percentageRecSlb3"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="formtr">
			<td colspan=2 align="center">
				<div class="formbtns">
				<% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.FEE_MANAGEMENT_NFV_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
				%>
				<input type="button" onclick = "continueAction()" class="styled-button" value='<spring:message code="save.lbl" />' />
				<% } %>
				<input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="cancel.lbl"/>' />
				</div>
			</td>
		</tr>
	</table>

</form:form>