<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.util.FeeTaxConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<% pageContext.setAttribute("FEE_FINANTIAL_TYPE", FeeTaxConstants.FEE_FINANTIAL_TYPE);
	pageContext.setAttribute("FEE_NON_FINANTIAL_TYPE", FeeTaxConstants.FEE_NON_FINANTIAL_TYPE); 
	pageContext.setAttribute("FEE_NON_FINANTIAL_VARY_TYPE", FeeTaxConstants.FEE_NON_FINANTIAL_VARY_TYPE); %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<c:url value="${urlFeedbackList}" var="feedbackUrl"/>

<script type='text/javascript'>
	function changeCaptionBasedOnServices(){
		var messages=document.getElementById("servicetype").value;
		if(messages == 1){
			return '<spring:message code="financial.service.lbl" />';
		}
		else if (messages == 2){
			return '<spring:message code="non.financial.service.lbl" />';
		}
		else if (messages == 3){
			return '<spring:message code="financial.per.services.lbl" />';
		}
	}
	$(function() {
		$("#feetable").jqGrid({
		   	url:'${feedbackUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['<spring:message code="user.type.lbl" />', '<spring:message code="operation.type.lbl" />',
		   	          '<spring:message code="country.lbl"/>', '<spring:message code="currency.lbl" />', ''],
		   	colModel:[
		   		{name:'userTypeName', index:'userTypeName', width:60, sortable:true, search:true, editable:false},
		   		{name:'operationTypeName', index:'operationTypeName', width:80, sortable:true, search:true, editable:false},
		   		{name:'countryName', index:'countryName', width:50, sortable:true, search:true, editable:false},
		   		{name:'currencyName', index:'currencyName', width:60, sortable:true, search:true, editable:false},
		   		{name:'id', hidden : true}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 350,width: 1000,
			rownumbers: true,
		   	pager: '#feepager',
		   	sortname: 'services',
		   	viewrecords: true,
		    sortorder: "desc",
		    caption:changeCaptionBasedOnServices(),
		    ondblClickRow: function(id){
		    	viewRow(id);
		    },
		    loadonce:true,
		    gridComplete: function(){
		    	disablePaginationlink("#feetable", 'feepager');
			},
		    onSelectRow:false,
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
		$("#feetable").jqGrid('navGrid','#feepager',
			{edit:false, add:false, del:false, search:true}, {}, {}, {}, 
			{ 	// search
				sopt:['cn', 'eq', 'ne', 'bw', 'ew'],
				closeOnEscape: true, 
				multipleSearch: true, 
				closeAfterSearch: true,
				resize: false
			}
		);
		<% 
		   Long FEE_MANAGEMENT_ID = Long.valueOf(request.getParameter("servicetype"));
		   if(FEE_MANAGEMENT_ID.equals(FeeTaxConstants.FEE_FINANTIAL_TYPE)){
		    FEE_MANAGEMENT_ID = MenuConstants.FEE_MANAGEMENT_F_MI;
		   }
		   else if(FEE_MANAGEMENT_ID.equals(FeeTaxConstants.FEE_NON_FINANTIAL_TYPE)){
		    FEE_MANAGEMENT_ID = MenuConstants.FEE_MANAGEMENT_NF_MI;
		   }
		   else if(FEE_MANAGEMENT_ID.equals(FeeTaxConstants.FEE_NON_FINANTIAL_VARY_TYPE)){
		    FEE_MANAGEMENT_ID = MenuConstants.FEE_MANAGEMENT_NFV_MI;
		   }
		   Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
		    FEE_MANAGEMENT_ID, MenuConstants.MANAGE_PERMISSION);
		      if(adminAccessCheck){
        %>
		$("#feetable").navButtonAdd('#feepager',
		    {  
	  			caption:'<spring:message code="add.lbl"/>', 
		     	buttonicon:"ui-icon-plus", 
		     	onClickButton: addRow,
		     	position: "last", 
		     	title:"", 
		     	cursor: "pointer"
		    } 
		);
		$("#feetable").navButtonAdd('#feepager',
			{ 	
				caption:'<spring:message code="edit.lbl"/>', 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);
		$("#feetable").navButtonAdd('#feepager',
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
		$("#feetable").navButtonAdd('#feepager',
			{ 	caption:'<spring:message code="view.lbl"/>', 
				buttonicon:"ui-icon-document", 
				onClickButton: viewRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		 ); 
	});
	function addRow() {
		var servicetype = document.getElementById("servicetype").value;
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt/addfee?servicetype=' + servicetype;
	} 
	
	function editRow() {
		if(jQuery("#feetable").getGridParam('selrow') == null || jQuery("#feetable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $('#feetable').jqGrid('getGridParam', 'selrow');
		var servicetype = document.getElementById("servicetype").value;
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt/editfee?id=' 
				+ row + '&servicetype=' + servicetype;
	}
	
	function deleteRow() {
		if(jQuery("#feetable").getGridParam('selrow') == null || jQuery("#feetable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $('#feetable').jqGrid('getGridParam', 'selrow');
		var servicetype = document.getElementById("servicetype").value;
		var message = '<spring:message code="delete.fee.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt/deletefee?id=' 
				+ row + '&servicetype=' + servicetype;
			ajaxLoader($("body"));
        }
		confirmationDialog(yesAction, null, null, message); 
	}
	
	function viewRow() {
		if(jQuery("#feetable").getGridParam('selrow') == null || jQuery("#feetable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
			return;
		}
		// Get the currently selected row
		var row = $('#feetable').jqGrid('getGridParam', 'selrow');
		var servicetype = document.getElementById("servicetype").value;
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt/viewfee?id=' 
				+ row + '&servicetype=' + servicetype;
	}	
	
</script>

	<div id='feegriddiv'>
			<c:if test="${servicetype eq FEE_FINANTIAL_TYPE}">
				<div class="pageheading"><spring:message code="financial.service.mgmt.lbl" /></div>
			</c:if>
			<c:if test="${servicetype eq FEE_NON_FINANTIAL_TYPE}">
				<div class="pageheading"><spring:message code="non.financial.service.mgmt.lbl" /></div>
			</c:if>
			<c:if test="${servicetype eq FEE_NON_FINANTIAL_VARY_TYPE}">
				<div class="pageheading"><spring:message code="financial.velocity.mgmt.lbl" /></div>
			</c:if>
			<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
			<span id="errorDate" class="error"></span>
		<table id='feetable'></table>
		<div id='feepager'></div>
	</div>
	<div id='clfmsgbox' title='' style='display:none'></div>
	<input type="hidden" id="servicetype" name="servicetype" value="${servicetype}" ></input>