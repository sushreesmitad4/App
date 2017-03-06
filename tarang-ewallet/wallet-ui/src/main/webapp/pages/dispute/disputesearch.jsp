<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>
<script type="text/javascript">

	function searchCall(dtype) {
		$('#errorMsg').html('');
		var reDate = <%=Common.RE_DATE_EXP%>;
		
		var sysDate = new Date(); // System DateTime
		
		var customerEmailId = $("#customerEmailId").val();
		var merchantEmailId = $("#merchantEmailId").val();
		var fromdate = $("#fromDate").val();  
		var todate = $("#toDate").val();
		var disputetype = $("#disputeType").val();
		
		var usertype = '<%=request.getSession().getAttribute("userType")%>';
		var urlpath ='';
		
		$('#suMsg').html('<p class=successmsg></p>');
		$('#erMsg').html('<p class=successmsg></p>');
		
		var customertype = '<%=GlobalLitterals.CUSTOMER_USER_TYPE %>';
		var merchanttype = '<%=GlobalLitterals.MERCHANT_USER_TYPE %>';
		var admintype = '<%=GlobalLitterals.ADMIN_USER_TYPE %>';
		
		if(dtype == "disputes"){			
			if(usertype == customertype){
				urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute?search=true';
			} 
			else if(usertype == merchanttype){ 				
				urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantdispute?search=true';
				if(customerEmailId != '') {
					urlpath += '&cemailid=' + customerEmailId;
				}
			} 
			else if(usertype == admintype){ 				
				urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admindispute?search=true';
				if(customerEmailId != '') {
					urlpath += '&cemailid=' + customerEmailId;
				}
			}
			
			if(disputetype != 0){
				urlpath += '&disputetype=' + disputetype;
			}
		}
		else if(dtype == "raise"){
			urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute/transactions?search=true';
		}	
		if(usertype != merchanttype){
			if(merchantEmailId != '') {
				urlpath += '&memailid=' + merchantEmailId;
			}	
		}
		
		if(fromdate != ''){
			var fdate = new Date(fromdate); 
			if( !fromdate.match(reDate)){
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.fromat.errmsg"/></p>');
				return;
			}
			urlpath += '&fdate=' + fromdate;
		}
		
		if(todate != ''){
			var tdate = new Date(todate);
			if( !todate.match(reDate)){
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="to.date.fromat.errmsg"/></p>');
				return;
			}
			urlpath += '&tdate=' + todate;
		}
	     
		if(fromdate!='' && todate!='' ){
			if(fdate >=sysDate || tdate >= sysDate){
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.to.date.greater.than.present.errmsg"/></p>');
			     return;
			}
		}
		
		if(fromdate != '' && todate != ''){
			if(tdate < fdate){
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
				return;
			}
		}
		window.location.href = urlpath;
	}
	
	function resetCall(){
		if(document.getElementById("customerEmailId") != null){
			document.getElementById("customerEmailId").value = "";
		}
		if(document.getElementById("merchantEmailId") != null){
			document.getElementById("merchantEmailId").value = "";
		}
		if(document.getElementById("fromDate") != null){
			document.getElementById("fromDate").value = "";
		}
		if(document.getElementById("toDate") != null){
			document.getElementById("toDate").value = "";
		}
		if(document.getElementById("disputeType") != null){
		document.getElementById("disputeType").value = 0;
		}
	 $('#errorMsg').html('');
		$('#suMsg').html('');
		$('#erMsg').html(''); 
		<%-- window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/'+callingURL+''; --%>
	}

</script>
<div class="centerstyle">
	<span id="errorMsg" class="searcherror"></span>
</div>
<form:form method="GET" commandName="disputeSearchForm" name="disputeSearchForm">
	<table class="form" style="width: 1100px;">
		<tr>
		  	<td style="width: 20%;">
			  <table>
			<% String userType = (String)request.getSession().getAttribute("userType");
				String txnpage = (String)request.getAttribute("isTxnpage");
			if(userType.equals(GlobalLitterals.CUSTOMER_USER_TYPE)){%>
			<tr >
				<td class="formtd" ><span class="formlebel"><spring:message code="merchant.email.lbl" /></span></td>
				<td class="formtd" ><form:input path="merchantEmailId" class = "searchinputver" />
			</tr>
			<% } else if(userType.equals(GlobalLitterals.MERCHANT_USER_TYPE)){ %>	
			<tr>
				<td class="formtd"><span class="formlebel"><spring:message code="customer.email.lbl" /></span></td>
				<td class="formtd"><form:input path="customerEmailId" class = "searchinputver"/>
			</tr>
			<% } else if(userType.equals(GlobalLitterals.ADMIN_USER_TYPE)){ %>	
			<tr>
				<td class="formtd"><span class="formlebel"><spring:message code="customer.email.lbl" /></span></td>
				<td class="formtd"><form:input path="customerEmailId" class = "searchinputver"/>
			</tr>
			<tr>
				<td class="formtd"><span class="formlebel"><spring:message code="merchant.email.lbl" /></span></td>
				<td class="formtd"><form:input path="merchantEmailId" class = "searchinputver"/>
			</tr>
			<% } %>
			<tr>
				<td class="formtd"><span class="formlebel"><spring:message code="fromdate.lbl" /></span></td>
				<td class="formtd"><form:input path="fromDate" class="tcal" autocomplete="off"/>
				</td>
			</tr>
			<tr>
				<td class="formtd"><span class="formlebel"><spring:message code="todate.lbl" /></span></td>
				<td class="formtd"><form:input path="toDate" class="tcal" autocomplete="off"/></td>
			</tr>
			<tr>
			<% if(txnpage == null){ %>
				<td class="formtd"><span class="formlebel"><spring:message code="dispute.type.lbl" /></span></td>
				<td class="formtd">
					<form:select path="disputeType" class="searchinputhor" style="width: 167px" >
					<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
					<c:if test="${disputeTypeList ne null }">
						<c:forEach items="${disputeTypeList}" var="entry">
					        <form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					    </c:forEach>
					</c:if>
					</form:select>
				</td>
			</tr>
			<% } %>
				</table>
			</td>
			<td style="width: 35%;">
				<form:hidden path="userType" id="utype"/>
				<% if(txnpage != null){ %>
				<input type="button" name="search" id="search" class="styled-button" 
					value='<spring:message code="search.lbl"/>' onclick="searchCall('raise')" />
				<% } else { %>
				<input type="button" name="search" id="search" class="styled-button" 
					value='<spring:message code="search.lbl"/>' onclick="searchCall('disputes')" />
				<% } %>
				<input type="button" name="search" id="search" class="styled-button" 
					value='<spring:message code="clear.lbl"/>' onclick="resetCall()" />
			</td>
		</tr>
	</table>
</form:form>