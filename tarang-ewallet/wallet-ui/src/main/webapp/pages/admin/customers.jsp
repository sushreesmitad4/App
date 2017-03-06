<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:url value="${urlUserList}" var="customerrecordsUrl"/>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type='text/javascript'>

	var callingURL= 'customermgmt';
	
	var tableVar= '#customerstable';
	
	$(function() {
		$("#customerstable").jqGrid({
		   	url:'${customerrecordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="id.lbl"/>', '<spring:message code="name.lbl"/>', 
		   	          '<spring:message code="userid.lbl"/>', '<spring:message code="creationdate.lbl"/>', 
		   	          '<spring:message code="status.lbl"/>',''], 
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'fullName', index:'fullName', width:200, editable:false, sortable:true, search:true},
		   		{name:'emailId', index:'emailId', width:200, editable:false, sortable:true, search:true},
		   		{name:'creDate', index:'creDate', width:80, editable:false, sortable:true, search:false},
		   		{name:'statusName', index:'statusName', width:80, editable:false, sortable:true, search:false},
		   		{name : 'status', hidden : true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,
		   	width: 1140,
			rownumbers: true,
		   	pager: '#customerspager',
		   	sortname: 'creationDate',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption: '<spring:message code="customers.lbl"/>',
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    onSelectRow: false,
		    loadonce: true,
		    gridComplete: function(){
		    	disablePaginationlink("#customerstable", 'customerspager');
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
		$("#customerstable").jqGrid('navGrid','#customerspager',
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
				MenuConstants.CUSTOMER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
		%>	
		$("#customerstable").navButtonAdd('#customerspager',
			{ 	caption: '<spring:message code="edit.lbl"/>', 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title: "", 
				cursor: "pointer"
			} 
		);
		<% } %>
		$("#customerstable").navButtonAdd('#customerspager',
		    {  
				caption: '<spring:message code="view.lbl"/>', 
			     buttonicon:"ui-icon-document", 
			     onClickButton: viewRow,
			     position: "last", 
			     title: "", 
			     cursor: "pointer"
		    } 
		);
	});
	
	function editRow() {
		$('#errorDate').html('');
		$('#errorName').html('');
		$('#errorEmailId').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
		if(jQuery("#customerstable").getGridParam('selrow') == null || jQuery("#customerstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
	   }
	   // Get the currently selected row
	   var grid = jQuery('#customerstable');
	   var row = grid.jqGrid('getGridParam', 'selrow');
	   var status = grid.jqGrid('getCell', row, 'status');
	   if(status != '<%=UserStatusConstants.DELETED%>'){
		   	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customermgmt/edit?id=' + row;						
	   }
	   else{
		   $('#errorDate').html('<p class="\searcherror\"><spring:message code="selected.record.deleted.errmsg"/></p>'); 
	   }
	}
	
	function viewRow() {
		if(jQuery("#customerstable").getGridParam('selrow') == null || jQuery("#customerstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
	   	// Get the currently selected row
	   	var row = $('#customerstable').jqGrid('getGridParam', 'selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customermgmt/view?id=' + row;
	}
	
</script>

<div class="pageheading"><spring:message code="customer.user.mgmt.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<jsp:include page="search.jsp"></jsp:include>
<div id='customersgriddiv'>
	<table id='customerstable'></table>
	<div id='customerspager'></div>
</div>
