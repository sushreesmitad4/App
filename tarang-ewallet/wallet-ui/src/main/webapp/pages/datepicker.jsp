<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>
    
<script>
$(function() {
    $( "#datepicker" ).datepicker({
        changeMonth: true,
       changeYear: true
    });
});
</script>
   
<tr>
	<td></td>
	<td class="formerror"><form:errors path="dateOfBirth" cssClass="error" /></td>
</tr>
<tr class="formtr">
	<td class="formtd"><form:label path="dateOfBirth" cssClass="formlebel"><spring:message code="dateofbirth.lbl" /><span class="mfield">&nbsp;*</span></form:label></td>
	<td class="formtd"><form:input path="dateOfBirth"  id="datepicker" autocomplete="off"/></td>
</tr>
