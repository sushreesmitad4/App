<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals" %>
 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>
    
<script>
    $(function() {
        $( "#toDate" ).datepicker({
            changeMonth: true,
            changeYear: true,
            yearRange: 'c:c+50',
            dateFormat: '<%=GlobalLitterals.DATE_PICKER_DATE_FORMAT%>', minDate: 0
        });
    });
 </script>  
   
<tr>
	<td></td>
	<td><form:errors path="toDate" cssClass="error" /></td>
</tr>
<tr>
	<td class="formtd">
		<form:label path="toDate" cssClass="formlebel">
			<spring:message code="todate.lbl" />
			<span class="mfield">&nbsp;*</span>
		</form:label>
	</td>
	<td class="formtd" align="left">
		<form:input path="toDate" id="toDate" autocomplete="off" cssClass="forminput" onchange="forOccurence();" />
	</td>
</tr>