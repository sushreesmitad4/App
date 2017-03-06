<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<c:url value="${urlCustomerReceiveViewList}" var="receiveMoneyUrl"/>
<script  src="<%=request.getContextPath()%>/jq/js/highcharts.js"></script>  

<script type="text/javascript">
$(document).ready(function() {
	checkTransaction();
});

<%-- 
$(document).ready(function() {
	document.getElementById('accept').disabled = true; 
	document.getElementById('reject').disabled = true; 
});

function onAccountSelect(){
	var id = $('#receivemoneytable').jqGrid('getGridParam', 'selrow');
	var status = $('#receivemoneytable').jqGrid('getCell', id, 'transactionStatus');
	
	var acceptbtn = false;
	var rejectbtn = false;
	
	if(status == 4){
		var acceptbtn = true;
		var rejectbtn = true;
	}
	else if(status == 3){
		var acceptbtn = true;
		var rejectbtn = true;
	}
	else if(status == 2){
		var acceptbtn = true;
		var rejectbtn = true;
	}
		
	document.getElementById('accept').disabled = acceptbtn; 
	document.getElementById('reject').disabled = rejectbtn; 
	
}

 function receivemoneyAction(type) {
	var message = '<spring:message code="select.record.errmsg" />';
	if(jQuery("#receivemoneytable").getGridParam('selrow') == null || jQuery("#receivemoneytable").getGridParam('selrow') == 0){
		warningDialog(message);
	    return;
	}
	// Get the currently selected row
	var grid = $('#receivemoneytable');	
	var id = $('#receivemoneytable').jqGrid('getGridParam', 'selrow');
	var row = grid.jqGrid('getGridParam', 'selrow');
	
	var payeeMail = $('#receivemoneytable').jqGrid('getCell', id, 'emailId');
	var currencyId = $('#receivemoneytable').jqGrid('getCell', id, 'requestedCurrency');
	var txnId = $('#receivemoneytable').jqGrid('getCell', id, 'transactionId');
	var status = $('#receivemoneytable').jqGrid('getCell', id, 'transactionStatus');
	var userType = $('#receivemoneytable').jqGrid('getCell', id, 'authUserType');
	
	if(type == 'accept'){
		var message = '<spring:message code="receivemoney.accept.confirm.msg" />';
		var yesAction = function () {
        	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/receivemoney/accept?txnId=' + txnId+ '&payeeMail=' + payeeMail +'&currencyId=' + currencyId +'&userType='+userType;
        };
        confirmationDialog(yesAction, null, null, message);
	}
	else if(type == 'reject'){
		
		var message = '<spring:message code="receivemoney.reject.confirm.msg" />';
		var yesAction = function () {
        	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/receivemoney/reject?txnId=' + txnId+ '&payeeMail=' + payeeMail +'&currencyId=' + currencyId +'&userType='+userType;
        };
        
        confirmationDialog(yesAction, null, null, message);
	}
} --%>
	
	//to display RECEIVE MONEY TRANSACTION
		var tableVar = '#receivemoneytable';
		$(function() {
		$("#receivemoneytable").jqGrid(
				{
					url : '${receiveMoneyUrl}',
					datatype : 'json',
					mtype : 'GET',
					colNames : ['<spring:message code="emailid.lbl" />', '<spring:message code="date.lbl" />', 
					            '<spring:message code="currency.lbl" />', '<spring:message code="amount.lbl" />', 
					            '<spring:message code="status.lbl" />','<spring:message code="message.lbl" />',
					            '','','','',''],
					colModel : [ {name : 'emailId', index : 'emailId', width : 150, editable : false, sortable : true, search : true}, 
					             {name : 'dateToString', index : 'dateToString', width : 90, editable : false, sortable : true, search : true}, 
					             {name : 'currencyCode', index : 'currencyCode', width : 80, editable : false, sortable : true, search : true}, 
					             {name : 'amountString', index : 'amountString', width : 80, editable : false, sortable : true, search : true}, 
					             {name : 'transactionStatusName', index : 'transactionStatusName', width : 80, editable : false, sortable : true, search : true},
					             {name : 'message', index : 'message', width : 120, editable : false, sortable : false, search : false},
					             {name : 'id', index : 'id', hidden : true},
					             {name : 'transactionStatus', hidden : true},
					             {name : 'requestedCurrency', hidden : true},
					             {name : 'transactionId', hidden : true},
					             {name : 'authUserType', hidden : true}
								],
					postData : {},
					rowNum : 10,
					rowList : [ 10, 20, 40, 60 ],
					height : 300,
					width : 950,
					rownumbers : true,
					pager : '#receivemoneypager',
					sortname : '',
					viewrecords : true,
					sortorder : "desc",
					caption : '<spring:message code="receive.money.lbl" />',
					 ondblClickRow: function(id){
					    	viewRow(id);
					    },
					onSelectRow : function(){
						onAccountSelect();
				    },
					loadError : function(xhr, st, str) {
						var str = "";
						for ( var prop in xhr) {
							str += prop + ",";
						}
					},
					loadonce: true,
					jsonReader : {
				        root: "rows",
				        page: "page",
				        total: "total",
				        records: "records",
						repeatitems : false,
						cell : "cell",
						id : "id"
					}
				});
		});
		
</script>
<div class="pageheading"><spring:message code="receive.money.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div class="block">
	<table>
		<tr>
			<td>
				<div id='receivemoneyviewgriddiv' style="margin-left: 20px;">
					<table id='receivemoneytable'></table>
					<div id='receivemoneypager'></div>
				</div>
			</td>
		</tr>
	</table>
		<%-- <div id='clfmsgbox' title='' style='display:none'></div>
		<table align = "center">
			<tr class="formtr"><td></td>
				<td class="formtdbtn">
					<input type="button" id="accept" class="styled-button" value='<spring:message code="accept.lbl" />' onclick="receivemoneyAction('accept')" />
					<input type="button" id="reject" class="styled-button" value='<spring:message code="reject.lbl" />' onclick="receivemoneyAction('reject')" />
			    </td>
			</tr>
		</table> --%>
</div>