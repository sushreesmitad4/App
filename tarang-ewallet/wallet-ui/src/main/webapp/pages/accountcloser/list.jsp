<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<c:url value="${urlUserList}" var="accountCloserRecordsUrl"/>

<script type='text/javascript'>

	var callingURL= 'accountclosermgmt';
	
	var tableVar = '#accountcloserstable';

	$(function() {
		$("#accountcloserstable").jqGrid({
		   	url:'${accountCloserRecordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['', '<spring:message code="registration.date.lbl"/>', 
		   	          '<spring:message code="emailid.lbl"/>', '<spring:message code="last.transaction.date.lbl"/>', 
		   	          '<spring:message code="status.lbl"/>', '<spring:message code="requested.date.lbl"/>'],
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'registrationDateStr', index:'registrationDateStr', width:90, editable:false, sortable:true, search:false},
		   		{name:'emailId', index:'emailId', width:200, editable:false, sortable:true, search:true}, 
		   		{name:'lastTransactionDateStr', index:'lastTransactionDateStr', width:90, editable:false, sortable:true, search:false},
		   		{name:'statusStr', index:'statusStr', width:90, editable:false, sortable:true, search:true},
		   		{name:'requestedDateStr', index:'requestedDateStr', width:90, editable:false, sortable:true, search:false}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,width: 1100,
			rownumbers: true,
		   	pager: '#accountcloserspager',
		   	sortname: 'creationDate',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:"<spring:message code="account.closer.lbl"/>",
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    onSelectRow: false,
		    loadonce: true,
		    gridComplete: function(){
		    	disablePaginationlink("#accountcloserstable", 'accountcloserspager');
		    },
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell", 
		        id: "id"
		    }
		});
		$("#accountcloserstable").jqGrid('navGrid','#accountcloserspager',
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
		<% 
			Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
				MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
        %>	
		$("#accountcloserstable").navButtonAdd('#accountcloserspager',
			{ 	caption:"<spring:message code="edit.lbl"/>", 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);	
		<% } %>
		$("#accountcloserstable").navButtonAdd('#accountcloserspager',
			{ 	caption:"<spring:message code="view.lbl"/>", 
				buttonicon:"ui-icon-document", 
				onClickButton: viewRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
	});
	
	 function editRow() {
		 if(jQuery("#accountcloserstable").getGridParam('selrow') == null || jQuery("#accountcloserstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		// Get the currently selected row
		var row = $('#accountcloserstable').jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt/editOrView?accountcloserId=' 
				+ row + '&mode=edit';
	}
	
	function viewRow() {
		if(jQuery("#accountcloserstable").getGridParam('selrow') == null || jQuery("#accountcloserstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		// Get the currently selected row
		var row = $('#accountcloserstable').jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt/editOrView?accountcloserId=' 
				+ row + '&mode=view';
	} 
	
</script>

	<div class="pageheading"><spring:message code="account.closer.mgmt.lbl" /></div>
	<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<jsp:include page="accountclosersearch.jsp"></jsp:include>
	<div id='accountclosersgriddiv'>
		<table id='accountcloserstable'></table>
		<div id='accountcloserspager'></div>
	</div>
