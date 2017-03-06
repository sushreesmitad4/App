<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.accounts.util.FundingAccountStatus,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<c:url value="${urlAccountList}" var="accountsUrl"/>

<script type="text/javascript">

$(document).ready(function() {
	document.getElementById('reload').disabled = true; 
	checkTransaction();
});
var tableVar = '#reloadmoneytable';
	function onAccountSelect(){
		var id = $(tableVar).jqGrid('getGridParam', 'selrow');
		var accstatus = $(tableVar).jqGrid('getCell', id, 'status');
		var aType = $(tableVar).jqGrid('getCell', id, 'atype');
		var defAcc = $(tableVar).jqGrid('getCell', id, 'defaultAccount');
		var uploadbtn = false;
		$('#errorStatus').html('');
		if(accstatus == '<%=FundingAccountStatus.NOT_VERIFIED_STATUS%>'){
			uploadbtn = true;
		}
		else if(accstatus == '<%=FundingAccountStatus.PENDING_STATUS%>'){
			uploadbtn = true;
		}
		else if(accstatus == '<%=FundingAccountStatus.REJECTED_STATUS%>'){
			uploadbtn = true;
		}
		else if(accstatus == '<%=FundingAccountStatus.VERIFIED_STATUS%>'){
			if(aType == "BANK"){ 
				uploadbtn = true;
			}
			else {
				uploadbtn = false;
			}
		}

		document.getElementById('reload').disabled = uploadbtn; 
		
	}
	
	function reloadmoneyurl(mtype) {
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery(tableVar).getGridParam('selrow') == null || jQuery(tableVar).getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		// Get the currently selected row
		var grid = $(tableVar);		
		var row = grid.jqGrid('getGridParam', 'selrow');
		var acctype = grid.jqGrid('getCell', row, 'atype');
		var accstatus = grid.jqGrid('getCell', row, 'status');
		if(mtype == "reload") {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reloadmoney/reloadmoneyrequest?mtype=' 
					+ mtype + '&id=' + row;
		}
	}	

	$(function() {
		$(tableVar).jqGrid(
				{
					url : '${accountsUrl}',
					datatype : 'json',
					mtype : 'GET',
					colNames : [ '<spring:message code="type.lbl" />', '<spring:message code="bank.card.provider.lbl" />', 
					             '<spring:message code="bank.card.number.lbl" />', '<spring:message code="status.lbl" />', 
					             '<spring:message code="default.account.lbl" />','', '' ],
					colModel : [ {name : 'atype', index : 'atype', width : 70, editable : false, sortable : true, search : true}, 
					             /* {name : 'bankOrCardType', index : 'bankOrCardType', width : 80, editable : false, sortable : true, search : false}, */
					             {name : 'bankOrCardName', index : 'bankOrCardName', width : 150, editable : false,	sortable : true, search : true},
					             {name : 'bankOrCardNumber', index : 'bankOrCardNumber', width : 150, editable : false,  sortable : true, search : true}, 
					             /* {name : 'bankOrCardName', index : 'bankOrCardName', width : 150, editable : false,	sortable : true, search : true}, */ 
					             {name : 'statusName', index : 'status', width : 80, editable : false, sortable : true,	search : false}, 
					             {name : 'defaultValue', index : 'defaultAccount', width : 150, editable : false,	sortable : true, search : true},
					             {name : 'status', hidden : true},
					             {name : 'defaultAccount', hidden : true}
								],
					postData : {},
					rowNum : 10,
					rowList : [ 10, 20, 40, 60 ],
					height : 300,
					width : 1000,
					rownumbers : true,
					pager : '#reloadmoneypager',
					sortname : '',
					viewrecords : true,
					sortorder : "desc",
					caption : '<spring:message code="reloadmoney.from.accounts.lbl"/>',
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
					gridComplete: function(){
						disablePaginationlink(tableVar, 'reloadmoneypager');
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
		
		$(tableVar).jqGrid('navGrid','#reloadmoneypager',
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
<div class="pageheading"><spring:message code="reloadmoney.from.accounts.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<div id='reloadmoneygriddiv'>
		<table id='reloadmoneytable'></table>
		<div id='reloadmoneypager'></div>
	</div>
<table>
	<tr>
		<td colspan=2 align="center">
           <div class="formbtns">
				<input type="button" id="reload" class="styled-button" value='<spring:message code="reload.money.lbl" />' onclick="reloadmoneyurl('reload')" />
		     </div>
		   </td>
		</tr>
	</table>



