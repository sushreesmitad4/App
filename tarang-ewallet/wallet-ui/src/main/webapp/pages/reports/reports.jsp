<%@page import="com.tarang.ewallet.walletui.controller.AttributeConstants"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.reports.util.ReportConstants,com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals" %>
<script type="text/javascript">
	$(document).ready(function(){
		var rtype = document.getElementById("reportType").value;
		if(rtype == 0 || rtype == <%=ReportConstants.CUSTOMER_MERCHANT_WISE_TRANSACTION%> || rtype == <%=ReportConstants.MERCHANT_CUSTOMER_WISE_TRANSACTION%> ||
				rtype == <%=ReportConstants.USER_CUSTOMER_WISE_TRANSACTION%> || rtype == <%=ReportConstants.USER_MERCHANT_WISE_TRANSACTION%>){
			
			var fdate = document.getElementById("fromDate").value;
			var tdate =  document.getElementById("toDate").value;
			if(fdate == "" && tdate == ""){
				document.getElementById("pdf").disabled = true; 
				document.getElementById("xls").disabled = true; 	
			} 
		}
	});

	function updateGrid() {
		var rtype = document.getElementById("reportType").value;
		var userType = '<%= session.getAttribute(AttributeConstants.USER_TYPE) %>';
		var subUrl="";
		if(userType == '<%=GlobalLitterals.ADMIN_USER_TYPE%>' ){
			subUrl = 'admin';
		}else{
			subUrl = 'cm';	
		}
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/'+subUrl+'?reporttype=' + rtype;
	
	}

	function exportAsFile(reporttype){
		var rtype = document.getElementById("reportType").value;
		var userType = '<%= session.getAttribute(AttributeConstants.USER_TYPE) %>';
		var subUrl="";
		if(userType == '<%=GlobalLitterals.ADMIN_USER_TYPE%>' ){
			subUrl = 'adminexportreports';
		}else{
			subUrl = 'cmexportreports';	
		}
		var urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/'+subUrl+'?reportid=' 
				+ rtype + '&reporttype=' + reporttype;
		var sysDate = new Date(); 
		$('#errorMsg').html('');
		var reDate = <%=Common.RE_DATE_EXP%>;
		if($("#fromDate") != null && $("#fromDate").val() != undefined ){
			var fromdate = $("#fromDate").val();
			var todate = $("#toDate").val();
			if(fromdate != '' || todate != ''){
				var fdate = new Date(fromdate);
				var tdate = new Date(todate);
				if( !fromdate.match(reDate) || !todate.match(reDate)){
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.to.date.fromat.errmsg"/></p>');
					return;
				}
				if(tdate < fdate){
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
					return;
				}
				urlpath += '&fdate=' + fromdate + '&tdate=' + todate;
			}
		}
		
		if($("#emailId") != null){
			var email = $("#emailId").val();
			if(email == '') {
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="email.required.errmsg"/></p>');
				return;
			}
			urlpath += '&emailId=' + email;
		}
		
		var disputtype = $("#disputeType").val();
		urlpath += '&disputetype='+disputtype;
		
		var cEmailid = $("#cEmailId").val();
		urlpath += '&cemail='+cEmailid;
		
		var mEmailid = $("#mEmailId").val();
		urlpath += '&memail='+mEmailid;
		
		//window.open(urlpath);
		window.location.href = urlpath; 
		return false;
	}
	
</script>

<div class="pageheading">${reportPageHeader}</div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="selectReport" method="GET" commandName="reportsForm" >
	<table class="form" style="width: 45%;padding: 0px" >
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="reportType" cssClass="error" /></td>
		</tr>
		<tr class="formtr" align="left">
			<td class="formtd"  style="width:90px" align="right">
				<form:label path="reportType" cssClass="formlebel" style="width:40px">
					<spring:message code="report.type.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd" style="width:150px">
				<form:select path="reportType" cssClass="formlist" onchange="updateGrid();">
					<form:option value="0">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${reportTypesList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr class="formtr" >
			<td class="formtd" colspan="2">
		<%
			String cid = (String) request.getParameter("reporttype");
			if(cid == null && request.getAttribute("typevalue") != null){
				cid = request.getAttribute("typevalue").toString();
			}
			boolean displayBtn = false;
			if ( cid != null && !cid.equals("") && !cid.equals("0")) {
				displayBtn = true;
		%>
		<jsp:include page="/pages/reports/report.jsp"></jsp:include>
		<%
			}
			else {
		%>
		<jsp:include page="/pages/reports/default.jsp"></jsp:include>
		<%
			}
		%></td>
		</tr>
	    <%
	       if(displayBtn) {
	    %>
		<tr class="formtr" id="pdfxls">
			<td class="formtd" colspan="2">
				<table class="reportpdfimg">
				<tr>
				<td width="80px;"></td>
				<td width="80px;"><img id="pdf" src="<%=request.getContextPath()%>/img/pdf.png" title='<spring:message code="export.pdf.lbl"/>' onclick="exportAsFile(2);"/></td>
				<td width="80px;"><img id="xls" src="<%=request.getContextPath()%>/img/excel.png" title='<spring:message code="export.excel.lbl"/>' onclick="exportAsFile(1);"/></td>
				</tr>
				</table>
			</td>
		</tr>
		<%
	       }
		%>
	</table>
</form:form>