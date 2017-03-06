<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<c:url value="${urlTaxList}" var="taxUrl"/>
<script type='text/javascript'>

	var taxTableVar= "#taxtable";
	
	$(function() {
		$(taxTableVar).jqGrid({
		   	url:'${taxUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="id.lbl"/>', '<spring:message code="country.lbl"/>', 
		   	          '<spring:message code="percentage.on.fee.lbl"/>'],
		   	colModel:[
		   		{name:'id', hidden : true},
		   		{name:'countryName',index:'countryName', width:100, editable:false, sortable:true, search:true},
		   		{name:'percentageAmount',index:'percentageAmount', width:100, editable:false, 
		   			formatter:'number', sorttype: 'number', sortable:true, search:true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 300,
		   	width: 600,
			rownumbers: true,
		   	pager: '#taxpager',
		   	sortname: 'countryName',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:'<spring:message code="tax.lbl"/>',
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    loadonce:true,
		    gridComplete: function(){
		    	disablePaginationlink(taxTableVar, 'taxpager');
			},
		    onSelectRow: false,
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
		$(taxTableVar).jqGrid('navGrid','#taxpager',
			{edit:false, add:false, del:false, search:false},
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
				MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
	    	if(adminAccessCheck){
	    %>	
		$(taxTableVar).navButtonAdd('#taxpager',
		    {  
	  			caption:'<spring:message code="add.lbl"/>', 
		     	buttonicon:"ui-icon-plus", 
		     	onClickButton: addtax,
		     	position: "last", 
		     	title:"", 
		     	cursor: "pointer"
		    } 
		);
		$(taxTableVar).navButtonAdd('#taxpager',
			{ 	
				caption:'<spring:message code="edit.lbl"/>', 
				buttonicon:"ui-icon-pencil", 
				onClickButton: edittax,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		<% } %>
	});
	
	function addtax() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/tax/add';
	} 
	function edittax() {
		if(jQuery(taxTableVar).getGridParam('selrow') == null || jQuery(taxTableVar).getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		}
		var grid = jQuery(taxTableVar);
		var row = grid.jqGrid('getGridParam', 'selrow');	
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/tax/edit?taxId=' + row;
	}
</script>

 	<div class="pageheading"><spring:message code="tax.mgmt.lbl" /></div>
 	<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
	<div id='taxgriddiv'>
		<table id='taxtable'></table>
		<div id='taxpager'></div>
	</div>
