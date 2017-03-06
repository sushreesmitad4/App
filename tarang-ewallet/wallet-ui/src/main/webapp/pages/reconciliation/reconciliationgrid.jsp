<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<c:url value="${urlReconciliationList}" var="reconciliationListUrl"/>
<script type="text/javascript">
	$(document).ready(function() {
		document.getElementById('update').disabled = true; 
	});
 
	function forNext(){
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#reconciliationtable").getGridParam('selrow') == null || jQuery("#reconciliationtable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		var row = $(tableVar).jqGrid('getGridParam', 'selrow');
		var id = $(tableVar).jqGrid('getCell', row, 'id');
		var fromDate = document.getElementById("fromDate").value;
		var toDate = document.getElementById("toDate").value;
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reconciliation/adjustAmount?id=' 
				+ id + '&fdate=' + fromDate + '&tdate=' + toDate;
	}
	
	function onSelection(){
		document.getElementById('update').disabled = false; 
	}
	
	var tableVar = '#reconciliationtable';

	$(function() {
		$(tableVar).jqGrid({
			url : '${reconciliationListUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="txn.id.lbl" />', '<spring:message code="card.type.lbl" />', 
			             '<spring:message code="purchase.date.lbl" />', '<spring:message code="purchase.time.lbl" />', 
			             '<spring:message code="file.amount.lbl" />', '<spring:message code="db.amount.lbl" />', 
			             '<spring:message code="currency.lbl" />','' ],
			colModel : [{name : 'pgTxnId', index : 'pgTxnId', width : 70, sortable : true, search : true},
			            {name : 'scheme', index : 'scheme', width : 70, sortable : true, search : true},
			            {name : 'purchaseDate', index : 'purchaseDate', width : 70, sortable : true, search : true},
			            {name : 'purchaseTime', index : 'purchaseTime', width : 70, sortable : true,  search : true},
			            {name : 'txnAmount', index : 'txnAmount', width : 70, sortable : true, formatter:'number', sorttype: 'number',search : true},
			            {name : 'actualAmount', index : 'actualAmount', width : 70, sortable : true, formatter:'number', sorttype: 'number',search : true},
			            {name : 'txnCurrency', index : 'txnCurrency', width : 70, sortable : true, search : true},
			            {name : 'id', index : 'id', hidden : true} 
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 300,
			width : 1100,
			rownumbers : true,
			pager : '#reconciliationpager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="reconciliation.lbl" />',
			onSelectRow : function(){
				onSelection();
		    },
			loadonce: true,
			gridComplete: function(){
				disablePaginationlink(tableVar, 'reconciliationpager');
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
		$(tableVar).jqGrid('navGrid','#reconciliationpager',
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
<div class="pageheading"><spring:message code="reconcilation.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<jsp:include page="/pages/reconciliation/search.jsp"></jsp:include>
	<div id='reconciliationgriddiv'>
		<table id='reconciliationtable'></table>
		<div id='reconciliationpager'></div>
	</div>
	<div id='clfmsgbox' title='' style='display:none'></div>
	<table>
		  <tr>
		       <td colspan=2 align="center">
			     <div class="formbtns">
				   <input type="button" id="update" class="styled-button" value='<spring:message code="update.lbl" />' onclick="forNext()" />
		         </div>
		    </td>
		 </tr>
	</table> 
