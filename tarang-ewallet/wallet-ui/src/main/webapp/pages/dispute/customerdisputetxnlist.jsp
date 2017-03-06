<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<c:url value="${urlTxnsList}" var="urlTxnsList"/>
<script type="text/javascript">
	
	$(document).ready(function(){
		document.getElementById("refund").disabled = true;
		document.getElementById("chargeback").disabled = true;
		document.getElementById("back").disabled = false;
	});


	function refundorchargeback(disputetype){
		
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#disputetable").getGridParam('selrow') == null || jQuery("#disputetable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		var grid = $('#disputetable');	
		var row = grid.jqGrid('getGridParam', 'selrow');
		var txnid = grid.jqGrid('getCell', row, 'transactionId');
		if(disputetype == "refund"){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
			+ '/customerdispute/refundorchargeback?type=refund&txnid=' + txnid;
		}
		else if(disputetype == "chargeback"){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
			+ '/customerdispute/refundorchargeback?type=chargeback&txnid=' + txnid;
		}
	}
	
	 function onDisputeSelect(){
		var disptueDis = <%=(Boolean)request.getAttribute("closeAccount")%>;
		if( disptueDis!=null && disptueDis == true){
			document.getElementById("refund").disabled = true;
			document.getElementById("chargeback").disabled = true;
		}else{
			document.getElementById("refund").disabled = false;
			document.getElementById("chargeback").disabled = false;
		}
		
	} 
	
	function forBack(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute';
	}

	var tableVar = '#disputetable';	
		$(function() {
		$('#disputetable').jqGrid(
				{
					url : '${urlTxnsList}',
					datatype : 'json',
					mtype : 'GET',
					colNames : ['<spring:message code="transaction.id.lbl" />',  '<spring:message code="transaction.date.lbl" />', 
					            '<spring:message code="merchant.email.lbl" />', '<spring:message code="transaction.amount.lbl" />', 
					            '<spring:message code="currency.lbl" />'],
					colModel : [ {name : 'transactionId', index : 'transactionId', width : 70, sortable : true, search : false},
					             {name : 'txnDateStr', index : 'txnDateStr', width : 100, sortable : true, search : true},
					             {name : 'merchantEmail', index : 'merchantEmail', width : 150, sortable : true, search : true}, 
					             {name : 'txnAmount', index : 'txnAmount', width : 80, 
					            	 formatter:'number', sorttype: 'number', sortable : true, search : true}, 
					             {name : 'txnCurrencyName', index : 'txnCurrencyName', width : 80, sortable : true, search : true}
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
					sortorder : "desc",
					caption : '<spring:message code="transactions.lbl" />',
					onSelectRow : function(){
						onDisputeSelect();
				    },
					loadonce: true,
					gridComplete: function(){
						disablePaginationlink(tableVar, 'disputepager');
					},
					jsonReader : {
				        root: "rows",
				        page: "page",
				        total: "total",
				        records: "records",
						repeatitems : false,
						cell : "cell",
						id : "transactionId"
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
<div class="pageheading"><spring:message code="transactions.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
  <jsp:include page="/pages/dispute/disputesearch.jsp"></jsp:include>
	<div id='disputegriddiv'>
		<table id='disputetable'></table>
		<div id='disputepager'></div>
	</div>
	<div id='clfmsgbox' title='' style='display:none'></div>
	<table>
			<tr>
				<td colspan=2 align="center">
				  <div class="formbtns">
					<input type="button" class="styled-button" id="refund" value='<spring:message code="dispute.refund.lbl" />' 
						onclick="refundorchargeback('refund')" />
					<input type="button" class="styled-button" id="chargeback" value='<spring:message code="dispute.chargeback.lbl" />' 
						onclick="refundorchargeback('chargeback')" />
					<input type="button" class="styled-button" id="back" value='<spring:message code="back.lbl" />' onclick="forBack()" />
			     </div>
			    </td>
			</tr>
		</table> 