<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<c:url value="${urlUserList}" var="merchantrecordsUrl"/>

<script type='text/javascript'>

	var callingURL= 'merchantmgmt';
	
	var tableVar = '#merchantstable';

	$(function() {
		$("#merchantstable").jqGrid({
		   	url:'${merchantrecordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="id.lbl"/>', '<spring:message code="legalname.lbl"/>', 
		   	          '<spring:message code="emailid.lbl"/>', '<spring:message code="creationdate.lbl"/>', 
		   	          '<spring:message code="status.lbl"/>',''],
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'legalName',index:'legalName', width:200, editable:false, sortable:true, search:true},
		   		{name:'emailId',index:'emailId', width:200, editable:false, sortable:true, search:true},
		   		{name:'creDate',index:'creDate', width:80, editable:false, sortable:true, search:false},
		   		{name:'statusName',index:'statusName', width:80, editable:false, sortable:true, search:false},
		   		{name : 'status', hidden : true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,
		   	width: 1140,
			rownumbers: true,
		   	pager: '#merchantspager',
		   	sortname: 'creationDate',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:'<spring:message code="merchants.lbl"/>',
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    loadonce: true,
		    gridComplete: function(){
		    	disablePaginationlink("#merchantstable", 'merchantspager');
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
		$("#merchantstable").jqGrid('navGrid','#merchantspager',
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
				MenuConstants.MERCHANT_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
	    %>	
		$("#merchantstable").navButtonAdd('#merchantspager',
			{ 	caption:'<spring:message code="edit.lbl"/>', 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);	
		<% } %>
		
		$("#merchantstable").navButtonAdd('#merchantspager',
			{ 	caption:'<spring:message code="view.lbl"/>', 
				buttonicon:"ui-icon-document", 
				onClickButton: viewRow,
				position: "last", 
				title:"", 
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
		if(jQuery("#merchantstable").getGridParam('selrow') == null || jQuery("#merchantstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		// Get the currently selected row
		var grid = jQuery('#merchantstable');
		var row = grid.jqGrid('getGridParam', 'selrow');
		var status = grid.jqGrid('getCell', row, 'status');
		if(status != '<%=UserStatusConstants.DELETED%>'){
		   	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantmgmt/edit?merchantId=' + row;
		}
		else{
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="selected.record.deleted.errmsg"/></p>'); 
		}
	}
	
	function viewRow() {
		if(jQuery("#merchantstable").getGridParam('selrow') == null || jQuery("#merchantstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		// Get the currently selected row
		var row = $('#merchantstable').jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantmgmt/view?merchantId=' + row;
	}
	
</script>

<div class="pageheading"><spring:message code="merchant.user.mgmt.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<jsp:include page="search.jsp"></jsp:include>
<div id='merchantsgriddiv'>
	<table id='merchantstable'></table>
	<div id='merchantspager'></div>
</div>
