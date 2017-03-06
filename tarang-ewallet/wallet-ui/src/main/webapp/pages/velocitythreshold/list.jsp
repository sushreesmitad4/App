<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<c:url value="${urlVelocityList}" var="velocityUrl"/>

<script type='text/javascript'>
	var velocityTable = "#velocitytable";
	$(function() {
		$(velocityTable).jqGrid({
		   	url:'${velocityUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="user.type.lbl" />', '<spring:message code="country.lbl"/>', 
		   	          '<spring:message code="currency.lbl" />', '<spring:message code="transaction.type.lbl" />',
		   	          '<spring:message code="minimum.amount.lbl" />', '<spring:message code="maximum.amount.lbl" />'],
		   	colModel:[
                {name:'userTypeName', index:'userTypeName', width:30, sortable:true, search:true, editable:false},
		   		{name:'countryName', index:'countryName', width:30, sortable:true, search:true, editable:false},
		   		{name:'currencyName', index:'currencyName', width:20, sortable:true, search:true, editable:false},
		   		{name:'transactiontypeName', index:'transactiontypeName', width:40, sortable:true, search:true, editable:false},
		   		{name:'strMinimumAmount', index:'strMinimumAmount', width:20, 
		   			formatter:'number', sorttype: 'number', sortable:true, search:false, editable:false},
		   		{name:'strMaximumAmount', index:'strMaximumAmount', width:20, 
		   			formatter:'number', sorttype: 'number', sortable:true, search:false, editable:false}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,width: 1000,
			rownumbers: true,
		   	pager: '#velocitypager',
		   	sortname: 'services',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:'<spring:message code="transaction.threshold.lbl" />',
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    loadonce:true,
		    onSelectRow:false,
		    gridComplete: function(){
		    	disablePaginationlink(velocityTable, 'velocitypager');
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
		$(velocityTable).jqGrid('navGrid','#velocitypager',
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
				MenuConstants.TRANSACTION_THRESHOLD_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
	   %>	
		$(velocityTable).navButtonAdd('#velocitypager',
			    {  
		  		caption:"<spring:message code="add.lbl"/>", 
			     buttonicon:"ui-icon-plus", 
			     onClickButton: addRow,
			     position: "last", 
			     title:"", 
			     cursor: "pointer"
			    } 
		);
		$(velocityTable).navButtonAdd('#velocitypager',
				{ 	caption:"<spring:message code="edit.lbl"/>", 
					buttonicon:"ui-icon-pencil", 
					onClickButton: editRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		<% } %>
		$(velocityTable).navButtonAdd('#velocitypager',
				{ 	caption:"<spring:message code="view.lbl"/>", 
					buttonicon:"ui-icon-document", 
					onClickButton: viewRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		 ); 
	});
	function addRow() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/addvelocity';
	} 
	
	function editRow() {
		
		if(jQuery(velocityTable).getGridParam('selrow') == null || jQuery(velocityTable).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $(velocityTable).jqGrid('getGridParam', 'selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/editvelocity?id=' + row ;
	}
	
	function viewRow() {
		if(jQuery(velocityTable).getGridParam('selrow') == null || jQuery(velocityTable).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $(velocityTable).jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/viewvelocity?id=' + row ;
	}	
	
</script>
		
	<div class="pageheading"><spring:message code="transaction.threshold.mgmt.lbl" /></div>
	<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<table id='velocitytable'></table>
	<div id='velocitypager'></div>
