<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals" %>
 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>
    
<script>
    $(function() {
        $( "#fromDate" ).datepicker({
        	 changeYear: true,
        	changeMonth: true,
        	yearRange: 'c:c+50',
        	dateFormat: '<%=GlobalLitterals.DATE_PICKER_DATE_FORMAT%>', minDate: 0
        }); 
    });
</script>  
   
<tr>
	<td></td>
	<td><form:errors path="fromDate" cssClass="error" /></td>
</tr>

<tr>
	<td class="formtd">
		<form:label path="fromDate" cssClass="formlebel">
			<spring:message code="fromdate.lbl" />
			<span class="mfield">&nbsp;*</span>
		</form:label>
	</td>
	<td class="formtd" align="left">
		<form:input path="fromDate" id="fromDate" autocomplete="off" cssClass="forminput" onchange="forOccurence();" />
	</td>
</tr>