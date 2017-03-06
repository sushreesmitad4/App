<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for admin users module. This includes admin user management module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<c:url value="${urlUserList}" var="adminuserUrl"/> 
<c:url value="/tarang/adminusermgmt/addadminuser" var="addadminuserUrl"/>
<c:url value="/tarang/adminusermgmt/editadminuser" var="editadminuserUrl"/>

<script type='text/javascript'>

var callingURL= 'adminusermgmt';

var tableVar= '#usertable';

	$(function() {
		$("#usertable").jqGrid({
		   	url:'${adminuserUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="id.lbl"/>', '<spring:message code="name.lbl"/>', 
		   	          '<spring:message code="userid.lbl"/>','<spring:message code="rolename.lbl"/>', 
		   	          '<spring:message code="creationdate.lbl"/>', '<spring:message code="status.lbl"/>',''],
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'fullName', index:'fullName', width:200, editable:false, sortable:true, search:true},
		   		{name:'emailId', index:'emailId', width:200, editable:false, sortable:true, search:true},
		   		{name:'roleName', index:'roleName', width:200, editable:false, sortable:true, search:true},
		   		{name:'creDate', index:'creDate', width:110, editable:false, sortable:true, search:false},
		   		{name:'activeName', index:'activeName', width:90, editable:false, sortable:true, search:true},
		   		{name : 'status', hidden : true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,
		   	width: 1140,
			rownumbers: true,
		   	pager: '#adminuserpager',
		   	sortname: 'creationDate',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:"<spring:message code="admin.users.lbl"/>",
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    onSelectRow: false,
		    loadonce: true,
		    gridComplete: function(){
		    	disablePaginationlink("#usertable", 'adminuserpager');
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
		$("#usertable").jqGrid('navGrid','#adminuserpager',
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
				MenuConstants.ADMIN_USER_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
        %>	
		$("#usertable").navButtonAdd('#adminuserpager',
			{ 	caption:"<spring:message code="add.lbl"/>", 
				buttonicon:"ui-icon-plus", 
				onClickButton: addRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		$("#usertable").navButtonAdd('#adminuserpager',
			{ 	caption:"<spring:message code="edit.lbl"/>", 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		<% } %>
		
		$("#usertable").navButtonAdd('#adminuserpager',
			{ 	caption:"<spring:message code="view.lbl"/>", 
				buttonicon:"ui-icon-document", 
				onClickButton: viewRow,
				position: "last", 
				title:"", 
				cursor: "pointer"	
			} 
		);
		$("#usertable").jqGrid('gridResize',{minWidth:1140,minHeight:400,
			  stop: function (grid, ev, ui) {
			    $(grid.srcElement).parent ().css ("height", null);
			  }
		});
	});
	
	function addRow() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt/addadminuser';
	}
	
	function editRow() {
		$('#errorDate').html('');
		$('#errorName').html('');
		$('#errorEmailId').html('');
		$('#suMsg').html('');
		$('#erMsg').html('');
		if(jQuery("#usertable").getGridParam('selrow') == null || jQuery("#usertable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		// Get the currently selected row
		var grid = jQuery('#usertable');
		var row = grid.jqGrid('getGridParam', 'selrow');
		var status = grid.jqGrid('getCell', row, 'status');
		if(status != '<%=UserStatusConstants.DELETED%>'){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt/editadminuser?id=' + row;
		}
		else{
			$('#errorDate').html('<p class="\searcherror\"><spring:message code="selected.record.deleted.errmsg"/></p>'); 
		}
	}
	
	function viewRow() {
		if(jQuery("#usertable").getGridParam('selrow') == null || jQuery("#usertable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
	    	return;
	   	}
	   	// Get the currently selected row
	   	var row = $('#usertable').jqGrid('getGridParam', 'selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt/viewadminuser?id=' + row;
	}
</script>

<div class="pageheading"><spring:message code="admin.mgmt.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<jsp:include page="search.jsp"></jsp:include>
<div id='adminusergriddiv'>
	<table id='usertable'></table>
	<div id='adminuserpager'></div>
</div>