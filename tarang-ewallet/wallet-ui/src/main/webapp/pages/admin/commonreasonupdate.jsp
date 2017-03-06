<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		var hdel = ${hideDeleteStatus};
		var hstatus = document.getElementById("hideStatus").value;
		if(hdel){
			deleteAction();
		}
		if(hstatus == "false"){
			activeAndStatusAction();
		}
		/*account closer request is approved by admin but it is not closed*/
		if((hstatus == "true") && (hdel == false)){
			enableReasonField(false);
		}
	}); 
	
	function activeAndStatusAction(){
		var oldStatus = document.getElementById("oldStatus").value;
		var oldActive = document.getElementById("oldActive").value;
		if(oldActive == "true"){
			oldActive = 1;
		}
		else{
			oldActive = 0;
		}
		var status = document.getElementsByName("status");
		var active = document.getElementsByName("active");
		
		var newStatus = oldStatus;
		for(i=0; i < status.length; i++){
			if(status[i].checked){
				newStatus = status[i].value;
			}
		}
		var newActive = 0;
		for(i=0; i < active.length; i++){
			if(active[i].checked){
				newActive = 1;
			}
		}
		if(oldStatus != newStatus  && (oldStatus == '<%=UserStatusConstants.PENDING%>' 
				&& newStatus == '<%=UserStatusConstants.REJECTED%>')){
			enableReasonField(true);
			return;
		}
		if(oldStatus != newStatus  && (oldStatus != '<%=UserStatusConstants.PENDING%>')){
			enableReasonField(true);
			return;
		}
		if(oldActive != newActive){
			enableReasonField(true);
			return;
		}
		enableReasonField(false);
	}
		
	function deleteAction(){
		var oldDeleted = document.getElementById("oldDeleted").value;
		if(oldDeleted == "true"){
			oldDeleted = 1;
		}
		else{
			oldDeleted = 0;
		}
		var deletedField = document.getElementsByName("deleted");
		var newDeleted = 0;
		for(i=0; i < deletedField.length; i++){
			if(deletedField[i].checked){
				newDeleted = 1;
			}
		}
		
		if(oldDeleted != newDeleted){
			enableReasonField(true);
			return;
		}
		enableReasonField(false);
 	}
	
	function enableReasonField(flag){
		if(flag == true){
			$("#rejecttext").show();
			$("#rejectErr").show();
		}
		else if(flag == false){
			$("#rejecttext").hide();
			$("#rejectErr").hide();
		}
	}
</script>