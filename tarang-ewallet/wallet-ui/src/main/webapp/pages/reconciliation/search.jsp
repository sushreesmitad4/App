
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>

<script type="text/javascript">

	function searchCall() {
		var sysDate = new Date(); // System DateTime
		
		$('#errorDate').html('');
		var reDate = <%=Common.RE_DATE_EXP%>;
		var fromdate = $("#fromDate").val();
		var todate = $("#toDate").val();
		var fdate = new Date(fromdate);
		var tdate = new Date(todate);
		
		$('#suMsg').html('<p class=successmsg></p>');
		$('#erMsg').html('<p class=successmsg></p>');
		
		if((fromdate == '' || todate == '') || (fromdate != '' && todate == '') || (fromdate == '' && todate != '')){
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.to.date.required.errmsg"/></p>');
		}
		else if(fromdate != '' && todate != '' && !fromdate.match(reDate) || !todate.match(reDate)){
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.to.date.fromat.errmsg"/></p>');
		}
		else if(fromdate != '' && todate != '' && fdate >=sysDate || tdate >= sysDate){
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.to.date.greater.than.present.errmsg"/></p>');
		}
		else if(fromdate != '' && todate != '' && tdate < fdate){
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
		}
		else if(fromdate != '' && todate != '' && fdate > tdate){
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.present.errmsg"/></p>');
		}
		else if(fromdate != '' && todate != ''){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reconciliation?search=true&fdate=' 
					+ fromdate+  '&tdate=' + todate;
		} 
	}
	
	function resetCall(){
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		$('#errorDate').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/' + callingURL + '';
	}

</script>

<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div class="centerstyle">
	<span id="errorDate" class="searcherror"></span>
</div>
<form:form method="POST" commandName="searchUserForm" name="searchForm">
	<table class="form" style="width: 1100px;">
		<tr class="formtr">
			<td class="formtd"><span class="formlebel"><spring:message code="fromdate.lbl" /></span></td>
			<td class="formtd"><form:input path="fromDate" id="fromDate"  class="tcal" />
			<td class="formtd"><span class="formlebel"><spring:message code="todate.lbl" /></span></td>
			<td class="formtd"><form:input path="toDate" id="toDate" class="tcal"/>
			<td class="formtdbtn">
				<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="search.lbl"/>' onclick="searchCall()" />
				<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="clear.lbl"/>' onclick="resetCall()" />
			</td>
			<td></td>
		</tr>
	</table>
</form:form>