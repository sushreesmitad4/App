<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<c:url value="${urlDisputeList}" var="cusDisputListUrl"/>
<script  src="<%=request.getContextPath()%>/jq/js/highcharts.js"></script>  

<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById('update').disabled = true; 
	});

	function onDisputeSelect(){
		var id = $('#disputetable').jqGrid('getGridParam', 'selrow'); 
		var disputeStatusId = $('#disputetable').jqGrid('getCell', id, 'disputeStatusId'); 
		document.getElementById('update').disabled = false; 
	}

	function forUpdateDispute(){
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#disputetable").getGridParam('selrow') == null || jQuery("#disputetable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		var grid = $('#disputetable');	
		var row = grid.jqGrid('getGridParam', 'selrow');
		var id = grid.jqGrid('getCell', row, 'disputeId');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantdispute/update?id=' + id;
	}
	
	var tableVar = '#disputetable';
	$(function() {
	$(tableVar).jqGrid({
			url : '${cusDisputListUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : ['<spring:message code="transaction.id.lbl" />', '<spring:message code="transaction.date.lbl" />',
			            '<spring:message code="customer.email.lbl" />', '<spring:message code="transaction.amount.lbl" />', 
			            '<spring:message code="currency.lbl" />', '<spring:message code="dispute.date.lbl" />',
			            '<spring:message code="dispute.amount.lbl" />', '<spring:message code="dispute.status.lbl" />',
			            '<spring:message code="dispute.type.lbl" />', '', '', ''],
			colModel : [ {name : 'transactionId', index : 'transactionId', width : 70, sorttype: 'number', sortable : true, search : true}, 
			             {name : 'txnDateStr', index : 'txnDateStr', width : 110, sortable : true, search : true},
			             {name : 'customerEmail', index : 'customerEmail', width : 150, sortable : true, search : true}, 
			             {name : 'txnAmount', index : 'txnAmount', width : 110, 
			            	 formatter:'number', sorttype: 'number', sortable : true, search : true}, 
			             {name : 'txnCurrencyName', index : 'txnCurrencyName', width : 110, sortable : true, search : true}, 
			             {name : 'disputeLogDateStr', index : 'disputeLogDateStr', width : 110, sortable : true, search : true},
			             {name : 'disputeAmount', index : 'disputeAmount', width : 110, 
			            		 formatter:'number', sorttype: 'number', sortable : true, search : true},
			             {name : 'disputeStatusName', index : 'disputeStatusName', width : 130, sortable : true, search : true},
			             {name : 'disputeTypeName', index : 'disputeTypeName', width : 110, sortable : true, search : true}, 
			             {name : 'dispuateTypeId', index : 'dispuateTypeId', hidden : true}, 
			             {name : 'disputeId', index : 'disputeId', hidden : true}, 
			             {name : 'disputeStatusId', index : 'disputeStatusId', hidden : true} 
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 300,
			width : 1100,
			rownumbers : true,
			pager : '#disputepager',
			sortname : '',
			viewrecords : true,
			gridComplete: function(){
				disablePaginationlink(tableVar, 'disputepager');
			},
			sortorder : "desc",
			caption : '<spring:message code="dispute.lbl" />',
			onSelectRow : function(){
				onDisputeSelect();
		    },
			loadonce: true,
			jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
				repeatitems : false,
				cell : "cell",
				id : "disputeId"
			}
		});
		$(tableVar).jqGrid('navGrid','#disputepager',
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
<div class="pageheading"><spring:message code="disputes.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<jsp:include page="/pages/dispute/disputesearch.jsp"></jsp:include>
	<div id='disputegriddiv'>
		<table id='disputetable'></table>
		<div id='disputepager'></div>
	</div>
	<div id='clfmsgbox' title='' style='display:none'></div>
	<table>
		<tr>
			<td  colspan=2 align="center">
				<div class="formbtns">
					<input type="button" id="update" class="styled-button" value='<spring:message code="update.dispute.lbl" />' onclick="forUpdateDispute()" />
				</div>
			</td>
		</tr>
	</table> 
