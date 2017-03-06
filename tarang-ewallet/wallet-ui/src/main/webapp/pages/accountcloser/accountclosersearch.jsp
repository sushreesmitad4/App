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
<%@ page import="com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>

<script type="text/javascript">

	function searchCall() {
		var sysDate = new Date(); // System DateTime
		
		$('#errorDate').html('');
		var reDate = <%=Common.RE_DATE_EXP%>;
		var fromdate = $("#fromDate").val();
		var todate = $("#toDate").val();
		var userType = $("#userType").val();
		var fdate = new Date(fromdate);
		var tdate = new Date(todate);
		
		var urlpath ='';
		urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt?search=true';
		if(userType != 0){
			urlpath += '&userType=' + userType;
		}
		if(fromdate != ''){
			var fdate = new Date(fromdate); 
			if( !fromdate.match(reDate)){
				$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.date.fromat.errmsg"/></p>');
				return;
			}
			urlpath += '&fdate=' + fromdate;
		}
		
		if(todate != ''){
			var tdate = new Date(todate);
			if( !todate.match(reDate)){
				$('#errorDate').html('<p class="\searcherror\"><spring:message code="to.date.fromat.errmsg"/></p>');
				return;
			}
			urlpath += '&tdate=' + todate;
		}
	     
		if(fromdate!='' && todate!='' ){
			if(fdate >=sysDate || tdate >= sysDate){
				$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.to.date.greater.than.present.errmsg"/></p>');
			     return;
			}
		}
		
		if(fromdate != '' && todate != ''){
			if(tdate < fdate){
				$('#errorDate').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
				return;
			}
		}
		window.location.href = urlpath;
	}
	
	function resetCall(){
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		document.getElementById("userType").value = 0;
		$('#errorDate').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
		<%-- window.location.href = '<%=request.getContextPath()%>/tarang/'+callingURL+''; --%>
	}

</script>
<div class="centerstyle">
	<span id="errorDate" class="searcherror"></span>
</div>
<form:form method="GET" commandName="searchAccountCloserForm" name="searchForm" action="accountclosermgmt">
	
	<table class="form" style="width: 1100px;">
		<tr class="formtr">
			<td><span class="formlebel"><spring:message code="fromdate.lbl" /></span></td>
			<td><form:input path="fromDate" id="fromDate" class="tcal"/>
			<td><span class="formlebel"><spring:message code="todate.lbl" /></span></td>
			<td><form:input path="toDate" id="toDate" class="tcal"/>
			<td><span class="formlebel"><spring:message code="user.type.lbl" /></span></td>
			<td>
				<form:select path="userType" class="searchinputhor"  id="userType">
					<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
					<c:if test="${userTypeList ne null }">
						<c:forEach items="${userTypeList}" var="entry">
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