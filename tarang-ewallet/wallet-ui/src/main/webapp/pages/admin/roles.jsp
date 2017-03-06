<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<c:url value="/tarang/adminroles/rolerecords" var="rolerecordsUrl"/>
<c:url value="/tarang/adminroles/addrole" var="addroleUrl"/>
<c:url value="/tarang/adminroles/editrole" var="editroleUrl"/>

<script type='text/javascript'>
	var sessionRoleId = <%=session.getAttribute("roleId")%>
	$(function() {
		$("#rolestable").jqGrid({
		   	url:'${rolerecordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="id.lbl"/>', '<spring:message code="name.lbl"/>', '<spring:message code="description.lbl"/>'],
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'name', index:'name', width:200, sortable:true, search: true, editable:false},
		   		{name:'description', index:'description', width:300, sortable:true, search:true, editable:false}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,width: 1000,
			rownumbers: true,
		   	pager: '#rolespager',
		   	sortname: 'name',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:'<spring:message code="roles.lbl"/>',
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    onSelectRow:false,
		    loadonce: true,
		    gridComplete: function(){
		    	disablePaginationlink("#rolestable", 'rolespager');
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
		$("#rolestable").jqGrid('navGrid','#rolespager',
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
				MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
		%>	
		$("#rolestable").navButtonAdd('#rolespager',
			{  
				caption:'<spring:message code="add.lbl"/>', 
				buttonicon:"ui-icon-plus", 
				onClickButton: addRow,
				position: "last", 
				title: "", 
				cursor: "pointer"
			} 
		);
		$("#rolestable").navButtonAdd('#rolespager',
			{ 	
				caption:'<spring:message code="edit.lbl"/>', 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		$("#rolestable").navButtonAdd('#rolespager',
			{ 	
				caption:'<spring:message code="delete.lbl"/>', 
				buttonicon:"ui-icon-trash", 
				onClickButton: deleteRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		<% } %>
		
		$("#rolestable").navButtonAdd('#rolespager',
			{ 	
				caption:'<spring:message code="view.lbl"/>', 
				buttonicon:"ui-icon-document", 
				onClickButton: viewRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		 ); 
	});
	
	function addRow() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles/addrole';
	} 
	
	function editRow() {
		if(jQuery("#rolestable").getGridParam('selrow') == null || jQuery("#rolestable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>'); 
			return;
		}
		// Get the currently selected row
		var row = $('#rolestable').jqGrid('getGridParam','selrow');
		if(sessionRoleId == row){
			warningDialog('<spring:message code="self.role.edit.errmsg"/>');
		}
		else{
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles/editrole?rid=' + row;
		}
	}
	
	function viewRow() {
		if(jQuery("#rolestable").getGridParam('selrow') == null || jQuery("#rolestable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $('#rolestable').jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles/viewrole?rid=' + row;
	}
	
	function deleteRow() {
		if(jQuery("#rolestable").getGridParam('selrow') == null || jQuery("#rolestable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var message = '<spring:message code="delete.role.confirm.msg" />';
		if(confirm(message)){
			var row = $('#rolestable').jqGrid('getGridParam','selrow');
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles/deleterole?rid=' + row;
		}	
	}
	
</script>
<div class="pageheading"><spring:message code="roles.mgmt.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div id='rolesgriddiv'>
	<table id='rolestable'></table>
	<div id='rolespager'></div>
</div>
