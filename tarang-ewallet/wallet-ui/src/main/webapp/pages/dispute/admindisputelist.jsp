<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<c:url value="${urlDisputeList}" var="cusDisputListUrl"/>
<script  src="<%=request.getContextPath()%>/jq/js/highcharts.js"></script>  

<script type="text/javascript">
	$(document).ready(function() {
		if(document.getElementById('update') != null){
			document.getElementById('update').disabled = true;
		}
	});

	function forUpdateDispute(){
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#adminDisputetable").getGridParam('selrow') == null || jQuery("#adminDisputetable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		var grid = $('#adminDisputetable');	
		var row = grid.jqGrid('getGridParam', 'selrow');
		var disputeid = grid.jqGrid('getCell', row, 'disputeId');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admindispute/update?disputeid=' + disputeid;
	}
	
	function onAdminDisputeSelect(){
		var id = $('#adminDisputetable').jqGrid('getGridParam', 'selrow');
		var disputestatus = $('#adminDisputetable').jqGrid('getCell', id, 'disputeStatusId');
		var updatebtn = false;
		if(document.getElementById('update') != null){
			document.getElementById('update').disabled = updatebtn;
		}
	}
	
	//to display grid
	var tableVar = '#adminDisputetable';
	$(function() {
	$(tableVar).jqGrid({
			url : '${cusDisputListUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : ['<spring:message code="transaction.id.lbl" />', '<spring:message code="transaction.date.lbl" />',
			            '<spring:message code="customer.email.lbl" />', '<spring:message code="merchant.email.lbl" />', 
			            '<spring:message code="transaction.amount.lbl" />', '<spring:message code="currency.lbl" />', 
			            '<spring:message code="dispute.date.lbl" />', '<spring:message code="dispute.amount.lbl" />',
			            '<spring:message code="dispute.status.lbl" />', '<spring:message code="dispute.type.lbl" />', '', '', ''],
			colModel : [ {name : 'transactionId', index : 'transactionId', width : 50, sortable : true, sorttype: 'number', search : true}, 
			             {name : 'txnDateStr', index : 'txnDateStr', width : 110, sortable : true, search : true},
			             {name : 'customerEmail', index : 'customerEmail', width : 200, sortable : true, search : true}, 
			             {name : 'merchantEmail', index : 'merchantEmail', width : 200, sortable : true, search : true}, 
			             {name : 'txnAmount', index : 'txnAmount', width : 110, sortable : true, formatter:'number', sorttype: 'number', search : true}, 
			             {name : 'txnCurrencyName', index : 'txnCurrencyName', width : 90, sortable : true, search : true}, 
			             {name : 'disputeLogDateStr', index : 'disputeLogDateStr', width : 110, sortable : true, search : true},
			             {name : 'disputeAmount', index : 'disputeAmount', width : 110, sortable : true, formatter:'number', sorttype: 'number', search : true},
			             {name : 'disputeStatusName', index : 'disputeStatusName', width : 130, sortable : true, search : true},
			             {name : 'disputeTypeName', index : 'disputeTypeName', width : 130, sortable : true, search : true}, 
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
			pager : '#adminDisputepager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="dispute.lbl" />',
			onSelectRow : function(){
				onAdminDisputeSelect();
		    },
			loadonce: true,
			gridComplete: function(){
				disablePaginationlink("#adminDisputetable", 'adminDisputepager');
			},
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
	
		$("#adminDisputetable").jqGrid('navGrid','#adminDisputepager',
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
		<table id='adminDisputetable'></table>
		<div id='adminDisputepager'></div>
	</div>
	<table>
		<tr>
			<td colspan=2 align="center">
			   <div class="formbtns">
			   <% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.DISPUTE, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
                %>	
				 <input type="button" class="styled-button" id="update" value='<spring:message code="update.dispute.lbl" />'  onclick="forUpdateDispute()" />
		       <% } %>
		       </div>
		    </td>
		</tr>
	</table> 

