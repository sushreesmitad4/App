<%@page import="com.tarang.ewallet.walletui.controller.AttributeConstants"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page import="com.tarang.ewallet.walletui.validator.common.Common,com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.reports.util.ReportConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/tcal.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jq/date/tcal.js"></script>

<script type="text/javascript">

	var rtype = document.getElementById("reportType").value;
	var userType = '<%= session.getAttribute(AttributeConstants.USER_TYPE) %>';
	var subUrl="";
	if(userType == "A" ){
		subUrl = 'adminsearch';
	}else{
		subUrl = 'cmsearch';	
	}
	var urlpath = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/'+subUrl+'?reportid=' + rtype;
	
	function validateEmail(){
		var cUserType = '<%=GlobalLitterals.CUSTOMER_USER_TYPE%>';
		var mUserType = '<%=GlobalLitterals.MERCHANT_USER_TYPE%>';
		if(rtype == <%=ReportConstants.USER_DISPUTED_TRANSACTION%> ){
			if($("#cEmailId").val() != null && $("#mEmailId").val() != null){
				var cEmail = $("#cEmailId").val();
				var mEmail = $("#mEmailId").val();
				var wrongCMail = false;//todisplay both C & M error msg
				var flag1 = false;
				var flag2 = false;
				if(cEmail != '') {
					$.ajax({
				          url: "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax/checkemail?email=" 
				        		  + cEmail + "&utype=" + cUserType , 
				          type: 'GET',
				          datatype: 'json',
				          async: false,
				          success: function(result){
					     	var rsltmsg = result.msg;
							if(rsltmsg.length != 0){  
								$('#errorMsg').html('<spring:message code="error.msg.notexist.customer.emailid"/>');
								clearGridData();
								wrongCMail = true;
							    return;
							}
							else if(rsltmsg.length == 0){
								flag1 = true;
							}  
					    }
					  });
				}
				else if(cEmail == ''){
					flag1 = true;
				}
				if(mEmail != '') {
					$.ajax({
				          url: "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax/checkemail?email=" 
				        		  + mEmail + "&utype=" +mUserType , 
				          type: 'GET',
				          datatype: 'json',
				          async: false,
				          success: function(result){
					     	var rsltmsg = result.msg;
							if(rsltmsg.length != 0){
								if(wrongCMail == true){
									$('#errorMsg').html('<spring:message code="error.msg.notexist.customerAndMerchant.emailid"/>');
									clearGridData();
								}
								else{
									$('#errorMsg').html('<spring:message code="error.msg.notexist.merchant.emailid"/>');
									clearGridData();
								}
								return;
							}
							else if(rsltmsg.length == 0){
								flag2 = true;
							}  
					    }
					  });
				}
				else if(mEmail == ''){
					flag2 = true;
				}
			}
			if((flag1 == true) && (flag2 == true) ){
				searchCall();
			}
		}
		else if(rtype == <%=ReportConstants.MERCHANT_DISPUTED_TRANSACTION%>){
			if($("#cEmailId").val() != null){
				var cEmail = $("#cEmailId").val();
				if(cEmail != '') {
					$.ajax({
				    	url: "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax/checkemail?email=" 
				    			+ cEmail + "&utype=" +cUserType, 
				        type: 'GET',
				        datatype: 'json',
				        success: function(result){
					    	var rsltmsg = result.msg;
							if(rsltmsg.length != 0){  
								$('#errorMsg').html('<spring:message code="error.msg.notexist.customer.emailid"/>');
								clearGridData();
									return;
								}
								else if(rsltmsg.length == 0){
									searchCall();
								}   
					    	}
					  });
				}
				else if(cEmail == ''){
					searchCall();
				}
			}
			
		}
		else{
			if($("#emailId").val() != null){
				var email = $("#emailId").val();
				var utype;
				if(rtype == <%=ReportConstants.MERCHANT_CUSTOMER_WISE_TRANSACTION%> || rtype == <%=ReportConstants.USER_CUSTOMER_WISE_TRANSACTION%> ){
					utype = '<%=GlobalLitterals.CUSTOMER_USER_TYPE%>';
				}
				else if(rtype == <%=ReportConstants.CUSTOMER_MERCHANT_WISE_TRANSACTION%> || rtype == <%=ReportConstants.USER_MERCHANT_WISE_TRANSACTION%>){
					utype = '<%=GlobalLitterals.MERCHANT_USER_TYPE%>';
				}
				if(email == '') {
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="email.required.errmsg"/></p>');
					clearGridData();
					return;
				}
				else{
					$.ajax({
			         	url: "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax/checkemail?email=" + email + "&utype=" + utype, 
			         	type: 'GET',
			         	datatype: 'json',
			         	success: function(result){
				     	var rsltmsg = result.msg;
							if(rsltmsg.length != 0){  
								if(utype == '<%=GlobalLitterals.CUSTOMER_USER_TYPE%>'){
							    	$('#errorMsg').html('<spring:message code="error.msg.notexist.customer.emailid"/>');
							    	clearGridData();
								}
								else if(utype == '<%=GlobalLitterals.MERCHANT_USER_TYPE%>'){
									$('#errorMsg').html('<spring:message code="error.msg.notexist.merchant.emailid"/>');
									clearGridData();
								}
							    return;
							}
							else if(rsltmsg.length == 0){
								urlpath += '&emailId=' + email;
								searchCall();
							}
				    	}
				  });
				}
			}
			else{
				searchCall();
			}
		}
	}
	
	function clearGridData(){
		$('#reportstable').jqGrid('clearGridData');
		if(jQuery("#reportstable").getDataIDs().length > 0){
			$("#pdfxls").show();
		}else{
			$("#pdfxls").hide();
		}
	}
	
	function searchCall() {
		var sysDate = new Date(); // System DateTime
		
		$('#errorMsg').html('');
		var reDate = <%=Common.RE_DATE_EXP%>;
		if($("#fromDate") != null){
			var fromdate = $("#fromDate").val();
			var todate = $("#toDate").val();
			if(rtype == '<%=ReportConstants.LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME %>'){
				todate = fromdate;
			}
			if(fromdate == '' || todate == ''){
				if(rtype == '<%=ReportConstants.LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME %>'){
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.required.errmsg"/></p>');
				}
				else{
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.to.date.required.errmsg"/></p>');
				}
				return;
			}
			
			var fdate = new Date(fromdate);
			var tdate = new Date(todate);
			var sysDate = new Date();
			if( !fromdate.match(reDate) || !todate.match(reDate)){
				if(rtype == '<%=ReportConstants.LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME %>'){
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.fromat.errmsg"/></p>');
				}
				else{
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.to.date.fromat.errmsg"/></p>');
				}
				return;
			}
			
			if(fromdate!='' && todate!='' ){
				if(fdate >=sysDate || tdate >= sysDate){
					$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.to.date.greater.than.present.errmsg"/></p>');
				     return;
				}
			}
			if(tdate < fdate){
				$('#errorMsg').html('<p class="\searcherror\"><spring:message code="from.date.greater.than.to.errmsg"/></p>');
				return;
			}
			urlpath += '&fdate=' + fromdate + '&tdate=' + todate;
			urlpath += '&disputetype=' + $("#disputeType").val();
			urlpath += '&cemail=' + $("#cEmailId").val();
			urlpath += '&memail=' + $("#mEmailId").val();
		}
		
		window.location.href = urlpath;
	}
	
	function resetCall(){
		document.getElementById("fromDate").value = "";
		document.getElementById("toDate").value = "";
		if(document.getElementById("emailId") != null){
			document.getElementById("emailId").value = "";
		}
		if(document.getElementById("cEmailId") != null){
			document.getElementById("cEmailId").value = "";
		}
		if(document.getElementById("mEmailId") != null){
			document.getElementById("mEmailId").value = "";
		}
		if(document.getElementById("disputeType") != null){
			document.getElementById("disputeType").value = 0;
		}
		$('#errorMsg').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
	}

</script>
<div class="centerstyle">
	<span id="errorMsg" class="searcherror"></span> 
</div>
<table class="form" style="width: 1100px;">
	<tr>
		<td style="width: 20%;">
			<table>
				<%  Long typeValue = (Long)request.getAttribute("typevalue");
				if(typeValue.equals(ReportConstants.USER_DISPUTED_TRANSACTION) || typeValue.equals(ReportConstants.MERCHANT_DISPUTED_TRANSACTION)){ %>
					<tr>	
					<td><span class="formlebel"><spring:message code="customer.email.lbl" /></span></td>
					<td><form:input path="cEmailId" id="cEmailId" class = "searchinputver"/></td>	
					</tr>	
				<% }
				if(typeValue.equals(ReportConstants.USER_DISPUTED_TRANSACTION)){ %>	
					<tr>	
					<td><span class="formlebel"><spring:message code="merchant.email.lbl" /></span></td>
					<td><form:input path="mEmailId" id="mEmailId" class = "searchinputver"/></td>	
					</tr>	
				<% }%>	
					
				<tr>
				<% if(typeValue.equals(ReportConstants.CUSTOMER_MERCHANT_WISE_TRANSACTION) || typeValue.equals(ReportConstants.USER_MERCHANT_WISE_TRANSACTION)){ %>
					<td><span class="formlebel"><spring:message code="merchant.email.lbl" /><span class="mfield">&nbsp;*</span></span></td>
					<td><form:input path="emailId" id="emailId" class = "searchinputver"/></td>
				<% }
				else if(typeValue.equals(ReportConstants.MERCHANT_CUSTOMER_WISE_TRANSACTION) || typeValue.equals(ReportConstants.USER_CUSTOMER_WISE_TRANSACTION)){ %>
					<td><span class="formlebel"><spring:message code="customer.email.lbl" /><span class="mfield">&nbsp;*</span></span></td>
					<td><form:input path="emailId" id="emailId" class = "searchinputver"/></td>
				<% }%>
				</tr>
				
				<tr>
					<td><span class="formlebel"><spring:message code="fromdate.lbl" /><span class="mfield">&nbsp;*</span></span></td>
					<td><form:input path="fromDate" id="fromDate" class="tcal" autocomplete="off"/></td>
				</tr>
				<% if(typeValue.equals(ReportConstants.LIST_OF_ACCOUNT_NOT_USED_IN_X_TIME)){ %>
					<tr><td><form:hidden path="toDate" id="toDate" class="tcal"/></td></tr>
				<% }else { %>
				<tr>
					<td><span class="formlebel"><spring:message code="todate.lbl" /><span class="mfield">&nbsp;*</span></span></td>
					<td><form:input path="toDate" id="toDate" class="tcal" autocomplete="off"/></td>
				</tr>
				<% } %>		
							
				<tr>
				<% if(typeValue.equals(ReportConstants.USER_DISPUTED_TRANSACTION) || typeValue.equals(ReportConstants.MERCHANT_DISPUTED_TRANSACTION)){ %>
					<td><span class="formlebel"><spring:message code="dispute.type.lbl" /></span></td>
					<td>
						<form:select path="disputeType" id="disputeType" class="searchinputhor" style="width: 167px" >
						<form:option value="0"><spring:message code="please.select.lbl" /></form:option>
						<c:if test="${disputeTypeList ne null }">
							<c:forEach items="${disputeTypeList}" var="entry">
						        <form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
						    </c:forEach>
						</c:if>
						</form:select>
					</td>
					<% } %>
				</tr>	
				<tr><td><form:hidden path="userType" id="utype"/></td></tr>
			</table>
		</td>					
		<td style="width: 35%;">
			<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="search.lbl"/>' onclick="validateEmail()" />
			<input type="button" name="search" id="search" class="styled-button" value='<spring:message code="clear.lbl"/>' onclick="resetCall()" />
		</td>			
	</tr>
</table>