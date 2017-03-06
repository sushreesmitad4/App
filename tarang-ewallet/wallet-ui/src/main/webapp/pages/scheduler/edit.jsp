<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.tarang.ewallet.util.GlobalLitterals" %>
<%@ page import="com.tarang.ewallet.walletui.validator.common.Common" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>

<script type='text/javascript'>

	$(window).load(function () { 
		forOccurence();
	});

	$(function() {
	    $( "#newEndDate" ).datepicker({
	    	 changeYear: true,
	    	changeMonth: true,
	    	yearRange: 'c:c+50',
	    	dateFormat: '<%=GlobalLitterals.DATE_PICKER_DATE_FORMAT%>', minDate: 0
	    }); 
	});

    $(function() {
        $( "#newStartDate" ).datepicker({
        	 changeYear: true,
        	changeMonth: true,
        	yearRange: 'c:c+50',
        	dateFormat: '<%=GlobalLitterals.DATE_PICKER_DATE_FORMAT%>', minDate: 0
        }); 
    });
  
    function editAction(){
    	var message = '<spring:message code="update.recurring.edit.confirm.msg" />';
		var yesAction = function () {
			updateRecurringAction();
			$(this).dialog("close");
		}
		confirmationDialog(yesAction, null, null, message); 
    }
    
	function updateRecurringAction(){
		
		$('#newStartDates').html('');
		$('#newEndDates').html('');
		$('#totalOcc').html('');
		$('#freque').html('');
		
		var flag = false;
		var currentDate = $("#currentDate").val();
		var newStartDate = $("#newStartDate").val();
		var newEndDate = $("#newEndDate").val();
		var frequency = $("#frequency").val();
		var totaloccurences = $("#totalOccurences").val();
		
		var reDate = <%=Common.RE_DATE_EXP%>;
		
		if(newStartDate == ''){
			$('#newStartDates').html('<p class=\"searcherror\"><spring:message code="newstartdate.required.errmsg"/></p>');
			flag = true;
		}
		
		if(newEndDate == ''){
			$('#newEndDates').html('<p class=\"searcherror\"><spring:message code="newenddate.required.errmsg"/></p>');
			flag = true;
		}
		
		if(!newStartDate.match(reDate)){
			$('#newStartDates').html('<p class=\"searcherror\"><spring:message code="valid.date.errmsg"/></p>');
			flag = true;
		}
		else if( (new Date(newStartDate).getTime() <= new Date(currentDate).getTime()) ){
			$('#newStartDates').html('<p class=\"searcherror\"><spring:message code="startdate.graterthan.currentdate.errmsg"/></p>');
			flag = true;
		}
		
		if(!newEndDate.match(reDate)){
			$('#newEndDates').html('<p class=\"searcherror\"><spring:message code="valid.date.errmsg"/></p>');
			flag = true;
		}
		else if( (new Date(newEndDate).getTime() <= new Date(currentDate).getTime())){
			$('#newEndDates').html('<p class=\"searcherror\"><spring:message code="enddate.graterthan.currentdate.errmsg"/></p>');
			flag = true;
		}
		else if( (new Date(newEndDate).getTime() <= new Date(newStartDate).getTime())){
			$('#newEndDates').html('<p class=\"searcherror\"><spring:message code="newenddate.graterthan.newstartdate.errmsg"/></p>');
			flag = true;
		}
		
		if(frequency == 0){
			$('#freque').html('<p class=\"searcherror\"><spring:message code="time.frequency.required.errmsg"/></p>');
			flag = true;
		} 
		
		if(totaloccurences < 1){
			$('#totalOcc').html('<p class=\"searcherror\"><spring:message code="occurences.required.errmsg"/></p>');
			flag = true;
		}
			
		if(!flag){
			submitFormData(document.sendMoneyJobDetailsModel);
		}	
	}
	
	function cancelAction() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/scheduler';
		}
		confirmationDialog(yesAction, null, null, message);  
	} 

	function forOccurence(){
		var v = 'totalOccurences';
		var fromDate = document.getElementById("newStartDate").value;
		var toDate = document.getElementById("newEndDate").value;
		var frequency = document.getElementById("frequency").value;
		getTotalOccurences(v, fromDate, toDate, frequency); 
	}

</script>
<div class="pageheading"><spring:message code="editreccuring.heading" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method = "POST" commandName="sendMoneyJobDetailsModel" action="edit" name="sendMoneyJobDetailsModel">
<input type="hidden" id="currentDate" value="<%=request.getAttribute("currentDate") %>"/>
<form:hidden path="sendMoneyId"  />

	<table class="form" style="width: 50%;">
		<jsp:include page="details.jsp"></jsp:include>
		<tr>
			<td></td>
			<td><span id="newStartDates" class="searcherror"></span></td>
		</tr>
		<tr>
			<td class="formtd">
				<form:label path="newStartDate" cssClass="formlebel">
					<spring:message code="newstartdate.lbl" />
					<span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd" align="left">
				<input type="text" name="newStartDate" id="newStartDate" value ="<%=request.getAttribute("newStartDate") %>" class="forminput" onchange="forOccurence();" />
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td><span id="newEndDates" class="searcherror"></span></td>
		</tr>
		<tr>
			<td class="formtd">
				<form:label path="newEndDate" cssClass="formlebel">
					<spring:message code="newenddate.lbl" />
					<span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd" align="left">
				<input type="text" name="newEndDate" id="newEndDate" value ="<%=request.getAttribute("newEndDate") %>" class="forminput" onchange="forOccurence();" />
			</td>
		</tr>
		
		<tr>
			<td></td>
			<td><span id="freque" class="searcherror"></span></td>
		</tr>

		<tr class="formtr">
			<td>
				<form:label path="frequency" cssClass="formlebel"><spring:message code="newfrequency.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="frequency"  cssClass="formlist" onchange="forOccurence();">
					<form:option value="<%=GlobalLitterals.ZERO_LONG%>">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${durationList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>

		<tr>
			<td></td>
			<td><span id="totalOcc" class="searcherror"></span></td>
		</tr>
		<tr>
			<td class="formtd">
				<form:label path="totalOccurences" cssClass="formlebel">
					<spring:message code="upcomingoccurences.lbl" />
					<span class="mfield">&nbsp;</span>
				</form:label>
			</td>
			<td class="formtd" align="left">
				<form:input path="totalOccurences" cssClass="forminput" id="totalOccurences" readonly="true" ></form:input>
			</td>
		</tr>		
		
		<tr>
			<td colspan=2 align="center">
			   <div class="formbtns">
				<input type="button" onclick="editAction()" class="styled-button"  value='<spring:message code="update.lbl" />' />
				<input type="button" onclick="cancelAction()"  class="styled-button" value='<spring:message code="cancel.lbl" />' />
			   </div>
			</td>
		</tr>
	</table>
</form:form>