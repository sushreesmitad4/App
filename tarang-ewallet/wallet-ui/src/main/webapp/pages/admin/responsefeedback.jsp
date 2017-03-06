<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<c:url value="${urlFeedbackList}" var="feedbackUrl"/>
<script type="text/javascript">
	var tableVar = '#feedbackstable';
	$(function() {
		$("#feedbackstable").jqGrid({
			url : '${feedbackUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="id.lbl"/>', '<spring:message code="querry.type.lbl"/>', 
			             '<spring:message code="subject.lbl"/>', '<spring:message code="created.date.lbl"/>', 
			             '<spring:message code="user.type.lbl"/>', '<spring:message code="user.email.lbl"/>',
			             '<spring:message code="response.date.lbl"/>' ],
			colModel : [ {name : 'id', hidden : true}, 
			             {name : 'queryTypeName', index : 'queryTypeName', width : 80, editable : false, sortable : true, search : false}, 
			             {name : 'subject', index : 'subject', width : 150, editable : false, sortable : true, search : true}, 
			             {name : 'dateAndTime', index : 'dateAndTime', width : 150, editable : false,	sortable : true, search : true}, 
			             {name : 'userTypeName', index : 'userTypeName', width : 80, editable : false, sortable : true,	search : false}, 
			             {name : 'userMail', index : 'userMail', width : 150, editable : false,	sortable : true, search : true},
			             {name : 'responseDate', index : 'responseDate', width : 150, editable : false,	sortable : true, search : true}
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 350, 
			width : 1000,
			rownumbers : true,
			pager : '#accountspager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="feedback.lbl"/>',
			ondblClickRow: function(id){
				viewRow(id);
			},
			loadonce: true,
			gridComplete: function(){
				disablePaginationlink("#feedbackstable", 'accountspager');
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
		
		$("#feedbackstable").jqGrid('navGrid','#accountspager',
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
	
	function viewRow() {
		if(jQuery("#feedbackstable").getGridParam('selrow') == null || jQuery("#customerstable").getGridParam('selrow') == 0){
			warningDialog('<spring:message code="select.record.errmsg"/>');
		    return;
		   }
		   // Get the currently selected row
		   var row = $('#feedbackstable').jqGrid('getGridParam','selrow');
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/responsefeedback/view?id=' + row;
	}
</script>	

<div class="pageheading"><spring:message code="query.feedbck.lbl" /></div>
<div id='accountsgriddiv'>
	<table id='feedbackstable'></table>
	<div id='accountspager'></div>
</div>
