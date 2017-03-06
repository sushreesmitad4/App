<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.accounts.util.FundingAccountStatus,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<c:url value="${urlAccountList}" var="accountsUrl"/>
<script type="text/javascript">

	$(document).ready(function() {
		document.getElementById('edit').disabled = true; 
		document.getElementById('verify').disabled = true; 
		document.getElementById('setdefault').disabled = true; 
		document.getElementById('delete').disabled = true; 
	});

	function addcard() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts/addcard';
	}
	
	function addbank() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts/addbank';
	}
	function onAccountSelect(){
		var id = $('#accountstable').jqGrid('getGridParam', 'selrow');
		var accstatus = $('#accountstable').jqGrid('getCell', id, 'status');
		var aType = $('#accountstable').jqGrid('getCell', id, 'atype');
		var defAcc = $('#accountstable').jqGrid('getCell', id, 'defaultAccount');
		var editbtn = false;
		var setdefaultbtn = false;
		var deletebtn = false;
		var verifybtn = false;

		if(accstatus == '<%=FundingAccountStatus.NOT_VERIFIED_STATUS%>'){
			editbtn = false;
			verifybtn = false;
			setdefaultbtn = true;
			deletebtn = false;
		}
		else if(accstatus == '<%=FundingAccountStatus.PENDING_STATUS%>'){
			editbtn = true;
			verifybtn = false;
			setdefaultbtn = true;
			deletebtn = true;
		}
		else if(accstatus == '<%=FundingAccountStatus.REJECTED_STATUS%>'){
			editbtn = false;
			verifybtn = true;
			setdefaultbtn = true;
			deletebtn = false;
		}
		else if(accstatus == '<%=FundingAccountStatus.VERIFIED_STATUS%>'){
			verifybtn = true;
			if(defAcc == 'true'){
				setdefaultbtn = true;
				deletebtn = true;
				editbtn = true;
			}
			else {
				setdefaultbtn = false;
				deletebtn = false;
			}
		}

		document.getElementById('edit').disabled = editbtn; 
		document.getElementById('verify').disabled = verifybtn; 
		document.getElementById('setdefault').disabled = setdefaultbtn; 
		document.getElementById('delete').disabled = deletebtn; 
	}
	
	function accounturl(mtype) {
		var message = '<spring:message code="select.record.errmsg" />';
		if(jQuery("#accountstable").getGridParam('selrow') == null || jQuery("#accountstable").getGridParam('selrow') == 0){
			warningDialog(message);
		    return;
		}
		// Get the currently selected row
		var grid = $('#accountstable');		
		var row = grid.jqGrid('getGridParam', 'selrow');
		var acctype = grid.jqGrid('getCell', row, 'atype');
		var accstatus = grid.jqGrid('getCell', row, 'status');
		if(mtype == "edit") {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts/edit?id=' + row;
		}
		else {
			if(mtype == "setdefault"){
				setDefault(mtype,row);
			}else if(mtype == "delete"){
				forDelete(mtype,row);
			}else if(mtype == "verify"){
				if(acctype == "CARD"){
					if(accstatus == '<%=FundingAccountStatus.NOT_VERIFIED_STATUS%>'){
						window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
						+ '/manageaccounts/notverified?mtype=' + mtype + '&id=' + row;		
					}
					else if(accstatus == '<%=FundingAccountStatus.PENDING_STATUS%>'){
						window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
						+ '/manageaccounts/pending?mtype=' + mtype + '&id=' + row;		
					}
				}
				else{
					window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
					+ '/manageaccounts?mtype=' + mtype + '&id=' + row;
				}
			}		
		}
	}	
	
	function setDefault(mtype,row) {
		var message = '<spring:message code="setdefault.account.confirm.msg" />';
		var yesAction = function () {
        	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
        	+ '/manageaccounts?mtype=' + mtype + '&id=' + row;
        };
        confirmationDialog(yesAction, null, null, message);
	}//end setDefault
	
	function forDelete(mtype,row) {
		var message = '<spring:message code="delete.account.confirm.msg" />';
		var yesAction = function () {
        	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' 
        	+ '/manageaccounts?mtype=' + mtype + '&id=' + row;
        }
		confirmationDialog(yesAction, null, null, message);   

	}//end setDelete
	$(function() {
		$("#accountstable").jqGrid(
		{
			url : '${accountsUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="type.lbl" />', '<spring:message code="bank.type.lbl" />', 
			             '<spring:message code="bank.card.number.lbl" />', '<spring:message code="bank.card.provider.lbl" />', 
			             '<spring:message code="status.lbl" />', '<spring:message code="default.account.lbl" />','', '' ],
			colModel : [ {name : 'atype', index : 'atype', width : 70, editable : false, sortable : true, search : true}, 
			             {name : 'bankOrCardType', index : 'bankOrCardType', width : 80, editable : false, sortable : true, search : false}, 
			             {name : 'bankOrCardNumber', index : 'bankOrCardNumber', width : 150, editable : false, sortable : true, search : true}, 
			             {name : 'bankOrCardName', index : 'bankOrCardName', width : 150, editable : false,	sortable : true, search : true}, 
			             {name : 'statusName', index : 'status', width : 80, editable : false, sortable : true,	search : false}, 
			             {name : 'defaultValue', index : 'defaultAccount', width : 150, editable : false,	sortable : true, search : true},
			             {name : 'status', hidden : true},
			             {name : 'defaultAccount', hidden : true}
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 300,
			width : 1140,
			rownumbers : true,
			pager : '#accountspager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="accounts.lbl"/>',
			onSelectRow : function(){
				onAccountSelect();
		    },
			loadonce: true,
			gridComplete: function(){
				disablePaginationlink("#accountstable", 'accountspager');
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
		
		$("#accountstable").jqGrid('navGrid','#accountspager',
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
	
	function viewRow(id) {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts/edit?id=' + id;
	}
</script>
<div class="pageheading"><spring:message code="manage.accounts.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div id='accountsgriddiv'>
	<table id='accountstable'></table>
	<div id='accountspager'></div>
</div>
<table>
	<tr>
		<td colspan=2 align="center">
		   <div class="formbtns">
				<input type="button" class="styled-button" value='<spring:message code="addbank.lbl" />' onclick="addbank()" />
				<input type="button" class="styled-button" value='<spring:message code="addcard.lbl" />' onclick="addcard()" />
				<input type="button" id="setdefault" class="styled-button" value='<spring:message code="setdefault.lbl" />' onclick="accounturl('setdefault')" />
				<input type="button" id="edit" class="styled-button" value='<spring:message code="edit.lbl" />' onclick="accounturl('edit')" />
				<input type="button" id="verify" class="styled-button" value='<spring:message code="verify.lbl" />' onclick="accounturl('verify')" />
				<input type="button" id="delete" class="styled-button" value='<spring:message code="delete.lbl" />' onclick="accounturl('delete')" />
             </div>
	    </td>
	</tr>
</table>
