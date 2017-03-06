<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants"%>
<c:url value="${urlReccuringList}" var="reccuringUrl"/>

<script type='text/javascript'>
	var reccuringtable = "#reccuringtable";
	$(function() {
		$(reccuringtable).jqGrid({
		   	url:'${reccuringUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="jobname.lbl"/>','<spring:message code="creationdate.lbl"/>',
		   	          '<spring:message code="startdate.lbl"/>', '<spring:message code="enddate.lbl"/>',
		   	          '<spring:message code="updateddate.lbl"/>','<spring:message code="recentfiredtime.lbl"/>',
		   	          '<spring:message code="recentfiredstatus.lbl"/>',''],
		   	colModel:[
		   		{name:'userJobName', index:'userJobName', width:200, sortable:true, search:true, editable:false},
		   		{name:'creationDateStr', index:'creationDateStr', width:70, sortable:true, search:true, editable:false},
		   		{name:'startDateStr', index:'startDateStr', width:70, sortable:true, search:true, editable:false},
		   		{name:'endDateStr', index:'endDateStr', width:70, sortable:true, search:true, editable:false},
		   		{name:'updatedDateStr', index:'updatedDateStr', width:70, sortable:true, search:true, editable:false},
		   		{name:'recentFiredTimeStr', index:'recentFiredTimeStr', width:120, sortable:true, search:true, editable:false},
		   		{name:'sendMoneyTxnStatus', index:'sendMoneyTxnStatus', width:100, sortable:true, search:true, editable:false},
		   		{name:'sendMoneyId', hidden : true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,width:900,
			rownumbers: true,
		   	pager: '#reccuringpager',
		   	sortname: 'services',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption: "<spring:message code="reccuring.caption"/>",
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    loadonce:true,
		    onSelectRow:false,
		    gridComplete: function(){
		    	disablePaginationlink(reccuringtable, 'reccuringpager');
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
		$(reccuringtable).jqGrid('navGrid','#reccuringpager',
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
		Long userStatus = (Long)session.getAttribute("userStatus");
		System.out.println(!UserStatusConstants.APPROVED.equals(userStatus));
		if(UserStatusConstants.APPROVED.equals(userStatus)){
    	%>
		$("#reccuringtable").navButtonAdd('#reccuringpager',
				{ 	
					caption:'<spring:message code="edit.lbl"/>', 
					buttonicon:"ui-icon-pencil", 
					onClickButton: editRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		$("#reccuringtable").navButtonAdd('#reccuringpager',
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
		$("#reccuringtable").navButtonAdd('#reccuringpager',
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
		if(jQuery(reccuringtable).getGridParam('selrow') == null || jQuery(reccuringtable).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $(reccuringtable).jqGrid('getGridParam', 'selrow');
		var sendMoneyId = $(reccuringtable).jqGrid('getCell', row, 'sendMoneyId');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/scheduler/edit?sendMoneyId='+sendMoneyId;

	}
	
	function viewRow() {
		if(jQuery(reccuringtable).getGridParam('selrow') == null || jQuery(reccuringtable).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $(reccuringtable).jqGrid('getGridParam', 'selrow');
		var sendMoneyId = $(reccuringtable).jqGrid('getCell', row, 'sendMoneyId');
	
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/scheduler/view?sendMoneyId='+sendMoneyId;
	}

	function deleteRow() {
		if(jQuery(reccuringtable).getGridParam('selrow') == null || jQuery(reccuringtable).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		
		var message = 'Do you want to delete recurring';
		var yesAction = function () {
			var row = $(reccuringtable).jqGrid('getGridParam','selrow');
			var sendMoneyId = $(reccuringtable).jqGrid('getCell', row, 'sendMoneyId');
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/scheduler/deleterec?sendMoneyId='+sendMoneyId;
		}
		confirmationDialog(yesAction, null, null, message);  
	}

</script>
		
<div class="pageheading"><spring:message code="reccuringdetails.heading" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>

<div id='recurringriddiv'>
	<table id='reccuringtable'></table>
<div id='reccuringpager'></div>
</div>

