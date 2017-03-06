<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for to search records according from date and to date 
  - Admin user, merchant and customer management module. 
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.walletui.validator.common.CommonValidator,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>

<script type="text/javascript">

function searchCall() {
	var sysDate = new Date(); 
	$('#errorDate').html('');
	var reDate = <%=Common.RE_DATE_EXP%>;
	var fromdate = $("#fromDate").val();
	var todate = $("#toDate").val();
	var status = $("#status").val();
	var name = $("#name").val();
	var emailId = $("#emailId").val();
	var fdate = new Date(fromdate);
	var tdate = new Date(todate);
	var flag = true;
	
	$('#errorDate').html('');
	$('#errorName').html('');
	$('#errorEmailId').html('');
	$('#suMsg').html('');
	$('#erMsg').html('');
	if(fromdate == '' && todate == '' && status == 0 && name == '' && emailId == ''){
		$('#errorDate').html('<p class=\"searcherror\"><spring:message code="value.required.errmsg"/></p>');
		flag = false;
	}
	else {
		if(fromdate != '' && todate == '' || fromdate == '' && todate != ''){
			$('#errorDate').html('<p class=\"searcherror\"><spring:message code="from.to.date.required.errmsg"/></p>');
			flag = false;
		}
		else if(fromdate!='' && todate!='' && !fromdate.match(reDate) || !todate.match(reDate)){
			$('#errorDate').html('<p class=\"searcherror\"><spring:message code="from.to.date.fromat.errmsg"/></p>');
			flag = false;
		}
		else if(fromdate!='' && todate!='' && fdate >=sysDate || tdate >= sysDate){
			$('#errorDate').html('<p class=\"searcherror\"><spring:message code="from.to.date.greater.than.present.errmsg"/></p>');
			flag = false;
		}
		else if(fromdate!='' && todate!='' && tdate < fdate){
			$('#errorDate').html('<p class=\"searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
			flag = false;
		}
		else if(fromdate!='' && todate!='' && fdate > tdate){
			$('#errorDate').html('<p class=\"searcherror\"><spring:message code="from.date.greater.than.present.errmsg"/></p>');
			flag = false;
		}
				
		if(name != ''){
			var nameExpression = <%= Common.NAME_EXPRESSION_JAVA_SCRIPT%> ;
			if(name.length < Number("<%=Common.NAME_MIN_LENGTH%>") ){
				$('#errorName').html('<p class=\"searcherror\"><spring:message code="script.name.serch.mgmt.errmsg.minlengthrange"/></p>');
				flag = false;
			}
			else if(name.length > Number("<%=Common.NAME_MAX_LENGTH%>") ){
				$('#errorName').html('<p class=\"searcherror\"><spring:message code="script.name.serch.mgmt.errmsg.lengthrange"/></p>');
				flag = false;
			}
			else if(nameExpression.test(name) == false){
				
				var expEmailMsg = "<spring:message code='admin.name.pattern'/>";
				$('#errorName').html("<p class=\"searcherror\">" + expEmailMsg + "</p>");
				flag = false;
			}
		}
		if(emailId != ''){
			var emailExpression =  <%= Common.EMAIL_PATTERN_JAVA_SCRIPT %> ;
			if(emailId.length > Number("<%=Common.EMAIL_MAX_LENGTH%>") ){
				$('#errorEmailId').html('<p class=\"searcherror\"><spring:message code="email.max.length.errmsg"/></p>');
				flag = false;
			} else if(emailExpression.test(emailId) == false){
				$('#errorEmailId').html('<p class=\"searcherror\"><spring:message code="email.contains.errmsg"/></p>');
				return;
			} 
		}
	}
	
	if(flag == true ){
		submitFormData(document.searchForm);
	}
}
	
	
	
	function resetCall(){
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		document.getElementById("status").value = 0;
		$('#errorDate').html('');
		$('#errorName').html('');
		$('#errorEmailId').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/' + callingURL + '';
	}

</script>

<div class="centerstyle">
	<span id="errorDate" class="searcherror"></span>
	<span id="errorName" class="searcherror"></span>
	<span id="errorEmailId" class="searcherror"></span>
</div>
<form:form method="POST" commandName="searchUserForm" name="searchForm">
	<table class="form" style="width: 1140px;">
		<tr class="formtr">
			<td class="formtd"><span class="formlebel"><spring:message code="fromdate.lbl" /></span></td>
			<td class="formtd"><form:input path="fromDate" id="fromDate" class="tcal" size="10" />
			<td class="formtd"><span class="formlebel"><spring:message code="todate.lbl" /></span></td>
			<td class="formtd"><form:input path="toDate" id="toDate" class="tcal" size="10" />
			<td class="formtd"><span class="formlebel"><spring:message code="name.lbl" /></span></td>
			<td class="formtd"><form:input path="name" id="name" class="searchinputhor"/>
			<td class="formtd"><span class="formlebel"><spring:message code="emailid.lbl"/></span></td>
			<td class="formtd"><form:input path="emailId" id="emailId" class="searchinputhor"/>
			<td class="formtd"><span class="formlebel"><spring:message code="status.lbl"/></span></td>
			<td class="formtd">
				<form:select path="status" class="searchinputhor"  id="status">
					<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
					<c:if test="${statusList ne null }">
						<c:forEach items="${statusList}" var="entry">
					        <form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					    </c:forEach>
					</c:if>
				</form:select>
			</td>
			<td class="formtdbtn">
				<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="search.lbl"/>' onclick="searchCall()"/>
				<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="clear.lbl"/>' onclick="resetCall()"/>
			</td>
			<td></td>
		</tr>
	</table>
</form:form>