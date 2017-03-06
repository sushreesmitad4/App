<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants,com.tarang.ewallet.util.GlobalLitterals" %>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants" %>
<script type="text/javascript">
	<!-- disable browser back button -->
	function DisableBackButton() {
		window.history.forward()
	}
		DisableBackButton();
		window.onload = DisableBackButton;
		window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
		window.onunload = function() { void (0) }
   
<!--  Starts AJAX Implementation -->
	
	var httpObject = null;
	var ajaxElementId = null;
	function getHTTPObject() {   
		if(window.ActiveXObject){
			return new ActiveXObject("Microsoft.XMLHTTP");
		}
		else if(window.XMLHttpRequest) {
			return new XMLHttpRequest();
		}
		else {
			var message = '<spring:message code="browser.support.alert.error.msg" />';
			alert(message);
			return null;   
		}
	} 
	      
	function updateMasterDataElements() {    
		if(httpObject.readyState == 4) {
			var combo1 = document.getElementById(ajaxElementId);
			combo1.options.length = 0;           
			var response = httpObject.responseText;            
			var items = response.split(";");
			var count = items.length;                   
			for (var i = 0; i < count; i++){            
				if(items[i].length > 1){
					var options = items[i].split("^");
					combo1.options[i] = new Option(options[1], options[0]);
				}
			}
		}
	}  
	       
	function getMasterData(elementId, dataFor, id){
		if (id == "") {
			return; 
		}
		httpObject = getHTTPObject();
		if (httpObject != null){
			ajaxElementId = elementId;
			var url = "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax?dataFor=" + dataFor + "&id=" + id;
			httpObject.open("GET", url, true);
			httpObject.onreadystatechange = updateMasterDataElements;
			httpObject.send(null); 
		}
	}
	<!--  fee operation type -->
	function getMasterData(elementId, dataFor, id, userTypeId){
		if (id == "" && userTypeId == "") {
			return; 
		}
		httpObject = getHTTPObject();
		if (httpObject != null){
			ajaxElementId = elementId;
			var url = "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax?dataFor=" + dataFor + "&id=" + id + "&userTypeId=" + userTypeId;
			httpObject.open("GET", url, true);
			httpObject.onreadystatechange = updateMasterDataElements;
			httpObject.send(null); 
		}
	}
	<!-- For TotalOccurences -->
	var ajaxElementId1 = null;
	function getTotalOccurences(v, fromDate, toDate, frequency){
		if (fromDate == "" && toDate == "" && frequency == 0) {
			return; 
		}
		httpObject = getHTTPObject();
		if (httpObject != null){
			ajaxElementId1 = v;
			var url = "<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/ajax/totalOccurences?fromDate=" + fromDate + "&toDate=" + toDate + "&frequency=" + frequency;
			httpObject.open("GET", url, true);
			httpObject.onreadystatechange = updateTotalOccurences;
			httpObject.send(null); 
		}
	}
	
	function updateTotalOccurences() {    
		if(httpObject.readyState == 4) {
			var text1 = document.getElementById(ajaxElementId1);        
			var response = httpObject.responseText;            
			text1.value = response;
		}
	}
	<!-- For TotalOccurences End -->
	
	<!--  Ends AJAX Implementation -->
	
	function updateSecondQuestions(fQoptionsId, sQoptionsId) {
		//get fQselect value
		var fQs = document.getElementById(fQoptionsId);
		var sQs = document.getElementById(sQoptionsId);
		sQs.options.length = 0;
		
		for(var i = 0; i < fQs.length; i++){
			var option = fQs.options[i];
			if(!option.selected){
				sQs.add(new Option(option.text, option.value));
			}
		}
	}
	
	function confirmationDialog(yesAction, defNoAction, headingLabel, messageLabel) {
		var noAction = defNoAction;
		if(noAction == null){
			noAction = function () {
	            $(this).dialog("close");
	        };
		}
		
		var hlabel =  headingLabel;
		
		if(hlabel == null || hlabel == ""){
			hlabel = '<spring:message code="confirmation.lbl" />';
		}	
			
		var msglabel = messageLabel;
		
		if(msglabel == null || msglabel == ""){
			msglabel = '<spring:message code="proceed.confirm.msg" />';
		}	
			
       $('<div></div>').appendTo('body')
           .html('<div class="popuperror">' + msglabel + '</div>')
           .dialog({
                modal: true, title:hlabel, zIndex: 10000, autoOpen: true, 
                width: 'auto', resizable: false, 
               buttons: {
                   Yes: yesAction,
                   No : noAction
               },
               close: function (event, ui) {
                   $(this).remove();
               }
           });
	}
	
	function confirmationCloseDialog(yesAction, defNoAction, defClolseAction, headingLabel, messageLabel) {
		
		var noAction = defNoAction;
		if(noAction == null){
			noAction = function () {
	            $(this).dialog("close");
	        };
		}
		
		var hlabel =  headingLabel;
		
		if(hlabel == null || hlabel == ""){
			hlabel = '<spring:message code="confirmation.lbl" />';
		}	
			
		var msglabel = messageLabel;
		
		if(msglabel == null || msglabel == ""){
			msglabel = '<spring:message code="proceed.confirm.msg" />';
		}	
					
       $('<div></div>').appendTo('body')
           .html('<div class="popuperror">' + msglabel + '</div>')
           .dialog({
                modal: true, title:hlabel, zIndex: 10000, autoOpen: true, 
                width: 'auto', resizable: false, 
               buttons: {
                   Yes: yesAction,
                   No : noAction
               },
               close: defClolseAction
           });
	}
	
	function warningDialog(warningLabel) {
		
		var hLabel = 'Information'; 
		
       $('<div></div>').appendTo('body')
           .html('<div class="popuperror">' + warningLabel + '</div>')
           .dialog({
                modal: true, title:hLabel, zIndex: 10000, autoOpen: true, 
                width: 'auto', resizable: false, 
               	buttons: {
                	OK: function () {
       	            	$(this).dialog("close");
       	        	}
               },
               close: function (event, ui) {
                   $(this).remove();
               }
           });
	}
	
	function DateTime(){
		var  ab='<%=session.getAttribute("lastLoginTime")%>';
	 	var d = new Date(parseInt(ab));
	 	d.toLocaleString();
	 	var curr_date = d.getDate();
	 	var curr_month = d.getMonth() + 1; //Months are zero based
	 	var curr_year = d.getFullYear();
	 	var currTime = curr_month + "/" + curr_date + "/" + curr_year+ " " +d.getHours()+ ":" + d.getMinutes() + ":" + d.getSeconds();
	 	document.getElementById('GMTTimeDate').innerHTML = currTime;
	}
	
	function checkTransaction(){
		var  userType = '<%=session.getAttribute("userType")%>';
		var  userStatus = '<%=session.getAttribute("userStatus")%>';
	 	if(userType == '<%=GlobalLitterals.CUSTOMER_USER_TYPE%>' && userStatus != '<%=UserStatusConstants.APPROVED%>'){
	 		warningDialog('<spring:message code="not.autherised.transaction.errmsg" />'); 
	 		window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customer';
	 	}
		if(userType == '<%=GlobalLitterals.MERCHANT_USER_TYPE%>' && userStatus != '<%=UserStatusConstants.APPROVED%>'){
			warningDialog('<spring:message code="not.autherised.transaction.errmsg" />'); 
			window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant';
	 	}
	}

	function submitFormData(objForm){
		/* alert("in common"); */
		var inputs = document.getElementsByTagName("input");
		var vflag = false;
		for(var i=0; i < inputs.length; i++ ){
			var item = inputs.item(i);
			if( item.getAttribute("type") == "text" && item.value.length > 1000 ){
				vflag = true;
				break;
			}
			if( item.getAttribute("type") == "password" && item.value.length > 1000 ){
				vflag = true;
				break;
			}
		}
		
		inputs = document.getElementsByTagName("textarea");
		for(var i=0; i < inputs.length; i++ ){
			var item = inputs.item(i);
		 	if( item.value.length > 1000 ){
		 		vflag = true;
		 		break;
		 	}
		}
		
		if(vflag == true){
			var message = '<spring:message code="exceeds.max.data.errmsg" />';
			warningDialog(message);
			return;
		}
		objForm.submit();
		ajaxLoader($("body"));
	}
	
	function submitRoleFormData(objForm){
		var inputs = document.getElementsByTagName("input");
		var vflag = false;
		for(var i=0; i < inputs.length; i++ ){
			var item = inputs.item(i);
			if( item.getAttribute("type") == "text" && item.value.length > 1000 ){
				vflag = true;
				break;
			}
		}
		if(vflag == true){
			var message = '<spring:message code="exceeds.max.data.errmsg" />';
			alert(message);
			return;
		}
		objForm.submit();
		ajaxLoader($("body"));
	}
	
	function showHelpText(helpComponent, textComponent){
		$("#" + textComponent ).css('visibility', 'hidden');
		$('#' + helpComponent).focus(function() {  $("#" + textComponent ).css('visibility', 'visible') }); 
		$('#' + helpComponent).blur(function() {  $("#" + textComponent ).css('visibility', 'hidden') }); 
		$('#' + helpComponent).mouseover(function() { $("#" + textComponent ).css('visibility', 'visible') }); 
		$('#' + helpComponent).mouseout(function() { $("#" + textComponent ).css('visibility', 'hidden') }); 

	}
	function disablePaginationlink(tablename, pagername){
		var ids = $(tablename).jqGrid('getDataIDs');
	    if(ids.length == 0){
	    	$("#next_" + pagername).addClass('ui-state-disabled');
	        $("#last_" + pagername).addClass('ui-state-disabled');
	    } 
	}
	
	function hidepagerdiv(tablename, pagername){
		var ids = $(tablename).jqGrid('getDataIDs');
	    if(ids.length == 0){
	        document.getElementById(pagername).innerHTML = [];
	    } 
	}
	
	<!-- For error page start -->
	function expire(userType){
		var subUrl="";
		if(userType != ''){
			if(userType == '<%=GlobalLitterals.ADMIN_USER_TYPE%>'){
				subUrl = 'adminlogin';
			}
			else{
				subUrl = 'customerlogin';	
			}
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/'+subUrl+'/logout?selfinvoke=true';
		}
		else{
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
		}
	}
	<!-- For error page end -->
	
</script>	