<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<c:url value="${urlCustomerReceiveList}" var="receiveMoneyUrl"/>

<script type="text/javascript">

	function request() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/requestmoney';
	}
	
	$(document).ready(function() {
		checkTransaction();
		document.getElementById('accept').disabled = true; 
		document.getElementById('reject').disabled = true; 
		document.getElementById('view').disabled = true; 
		document.getElementById('cancel').disabled = true; 
	});
	
	function onAccountSelect(){
		var id = $('#requestmoneytable').jqGrid('getGridParam', 'selrow');
		var status = $('#requestmoneytable').jqGrid('getCell', id, 'status');
		var isSelfRequest = $('#requestmoneytable').jqGrid('getCell', id, 'isSelfRequest');
		var viewbtn = false;
		var acceptbtn = false;
		var rejectbtn = false;
		var cancelbtn = false;
		 
		 if(isSelfRequest == "self" ){
				var viewbtn = false;
				var acceptbtn = true;
				var rejectbtn = true;
				if(status == 1){
					var cancelbtn = false;
				}
				else{
					var cancelbtn = true;
				}
		}
		else{
			var viewbtn = false;
			var acceptbtn = false;
			var rejectbtn = false;
			var cancelbtn = true;
		}
		 
		if(status == 2){
			var viewbtn = false;
			var acceptbtn = true;
			var rejectbtn = true;
			var cancelbtn = true;
		}
		else if(status == 3){
			var viewbtn = false;
			var acceptbtn = true;
			var rejectbtn = true;
			var cancelbtn = true;
		}
		else if(status == 4){
			var viewbtn = false;
			var acceptbtn = true;
			var rejectbtn = true;
			var cancelbtn = true;
		}
		
		document.getElementById('accept').disabled = acceptbtn; 
		document.getElementById('reject').disabled = rejectbtn; 
		document.getElementById('view').disabled = viewbtn; 
		document.getElementById('cancel').disabled = cancelbtn; 
	}
	
	function requestmoneyAction(type) {
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#requestmoneytable").getGridParam('selrow') == null || jQuery("#requestmoneytable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		// Get the currently selected row
		var grid = $('#requestmoneytable');	
		var id = $('#requestmoneytable').jqGrid('getGridParam', 'selrow');
		var requesterEmail = $('#requestmoneytable').jqGrid('getCell', id, 'email');
		var ids = $('#requestmoneytable').jqGrid('getCell', id, 'id');
		if(type == 'view'){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/viewrequestmoney?id=' 
					+ ids + '&requesterEmail=' + requesterEmail;
		}
		else if(type == 'accept'){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/accept?id=' 
					+ ids + '&requesterEmail=' + requesterEmail;
		}
		else if(type == 'reject'){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/reject?id=' 
					+ ids + '&requesterEmail=' + requesterEmail;		
		}
		else if(type == 'cancel'){
			var message = '<spring:message code="receivemoney.alert.cancel" />';
			var yesAction = function () {
	        	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/requestmoney/cancel?id=' 
	        			+ ids + '&requesterEmail=' + requesterEmail;
	        	ajaxLoader($("body"));
	        };
	        confirmationDialog(yesAction, null, null, message);
		}
		
	}

	var tableVar = '#requestmoneytable';
	
	$(function() {
		$("#requestmoneytable").jqGrid(
			{
				url : '${receiveMoneyUrl}',
				datatype : 'json',
				mtype : 'GET',
				colNames : [ '<spring:message code="emailid.lbl" />', '<spring:message code="date.lbl" />', 
				             '<spring:message code="currency.lbl" />', '<spring:message code="amount.lbl" />', 
				             '<spring:message code="is.self.request.lbl" />', '<spring:message code="status.lbl" />','','' ],
				colModel : [ {name : 'email', index : 'email', width : 150, editable : false, sortable : true, search : true}, 
				             {name : 'dateToString', index : 'dateToString', width : 100, editable : false, sortable : true, search : true}, 
				             {name : 'currencyName', index : 'currencyName', width : 120, editable : false, sortable : true, search : true}, 
				             {name : 'amountString', index : 'amountString', width : 120, editable : false, 
				            	 formatter:'number', sorttype: 'number', sortable : true, search : true}, 
				             {name : 'isSelfRequest', index : 'isSelfRequest', width : 120, editable : false, sortable : true,	search : true}, 
				             {name : 'statusName', index : 'statusName', width : 120, editable : false,	sortable : true, search : true},
				             {name : 'id', index : 'id', hidden : true},
				             {name : 'status', index : 'status', hidden : true}
							],
				postData : {},
				rowNum : 10,
				rowList : [ 10, 20, 40, 60 ],
				height : 300,
				width : 950,
				rownumbers : true,
				pager : '#requestmoneypager',
				sortname : '',
				viewrecords : true,
				sortorder : "desc",
				caption : '<spring:message code="money.requests.lbl" />',
				loadonce:true,
				onSelectRow : function(){
					onAccountSelect();
			    },
				loadError : function(xhr, st, str) {
					var str = "";
					for ( var prop in xhr) {
						str += prop + ",";
					}
				},
				gridComplete: function(){
					disablePaginationlink("#requestmoneytable", 'requestmoneypager');
				},
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
		$("#requestmoneytable").jqGrid('navGrid','#requestmoneypager',
				{edit:false, add:false, del:false, search:true},
				{}, {}, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true,
					resize: false
				}
			);
	});
</script>

<div class="pageheading"><spring:message code="money.requests.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<div id='requestmoneygriddiv'>
		<table id='requestmoneytable'></table>
		<div id='requestmoneypager'></div>
	</div>
	<table>
	<tr>
		<td colspan=2 align="center">
			<div class="formbtns">
				<input type="button" class="styled-button" value='<spring:message code="request.lbl" />' onclick="request()" />
				<input type="button" id="view" class="styled-button" value='<spring:message code="view.lbl" />' onclick="requestmoneyAction('view')" />
				<input type="button" id="accept" class="styled-button" value='<spring:message code="accept.lbl" />' onclick="requestmoneyAction('accept')" />
				<input type="button" id="reject" class="styled-button" value='<spring:message code="reject.lbl" />' onclick="requestmoneyAction('reject')" />
				<input type="button" id="cancel" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="requestmoneyAction('cancel')" />
		      </div>
		   </td>
	</tr>
	</table>