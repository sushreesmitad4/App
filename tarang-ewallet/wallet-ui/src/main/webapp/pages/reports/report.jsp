<%@page import="java.util.Date"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.reports.ReportData,com.tarang.ewallet.walletui.reports.ReportUtil" %>
<%@ page import="com.tarang.ewallet.dto.CustomerReportModel,com.tarang.ewallet.reports.util.ReportConstants" %>
<c:url value="${urlReportsList}" var="reportsUrl"/>

<script type="text/javascript">

<% 
	ReportData reportData = (ReportData)request.getAttribute("reportsData");
	if(reportData != null){
%>
	var tableVar = '#reportstable';
	$(function() {
		$("#reportstable").jqGrid(
		{
			url : '${reportsUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames :[
						<% 
						String[] reportNames = reportData.getHeaderNames();
						String[] reportFields = reportData.getHeaderFields();
						float[] columnWidths = reportData.getColumnWidths();
						for(int i = 0; i < reportNames.length; i++){ %>
						'<%=reportNames[i]%>',					
						<% } %> 
						''],
			colModel : [ 
						<% for(int i = 0; i < reportFields.length; i++){
							String fname = reportFields[i];
							Class c = null;
							String dataType = CustomerReportModel.class.getSimpleName();
							if( CustomerReportModel.class.getSimpleName().equals(reportData.getDataType()) ){
								c = ReportUtil.getFieldType(CustomerReportModel.class, fname);
							}
							if(c == java.util.Date.class) {
							%>
							{name : 'griddisplay<%=reportFields[i]%>', width : <%=50*columnWidths[i]%>,	sortable : true},
							<% } 
							else {
							%>
							{name : '<%=reportFields[i]%>', width : <%=50*columnWidths[i]%>, 
								<%if(c == java.lang.Double.class) {%>
									formatter:'number', sorttype: 'number',
								<% } if(c == java.math.BigInteger.class) {%>
									formatter:'int', sorttype: 'int',
								<% } %>
								sortable : true},
								
							<% }
							}%>
							
						{name : 'defaultValue', hidden : true}
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 300,
			width : 1100, 
			loadComplete: function(){
				if(jQuery("#reportstable").getDataIDs().length > 0){
					$("#pdfxls").show();
				}else{
					$("#pdfxls").hide();
				}
            },
			rownumbers : true,
			pager : '#reportspager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : "<%=reportData.getTitleName()%>",
			loadonce: true,
		    gridComplete: function(){
		    	hidepagerdiv("#reportstable", 'reportspager');
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
		$("#reportstable").jqGrid('navGrid','#reportspager',
			{edit:false, add:false, del:false, search:true},
			{}, {}, {}, 
			{ 	// search
				sopt:['cn', 'eq', 'ne', 'bw', 'ew'],
				closeOnEscape: true, 
				multipleSearch: true, 
				closeAfterSearch: true,
				resize: false
			});
		});
<% } %>
</script>


	<% int typeValue = ((Long)request.getAttribute("typevalue")).intValue(); 
	if( !(typeValue == 0 
		|| typeValue == ReportConstants.CUSTOMER_LAST_N_TRANSACTIONS 
		|| typeValue ==  ReportConstants.MERCHANT_LAST_N_TRANSACTIONS 
		|| typeValue == ReportConstants.USER_LAST_N_TRANSACTIONS 
		|| typeValue == ReportConstants.USER_CUSTOMER_BALANCES 
		|| typeValue == ReportConstants.USER_MERCHANT_BALANCES 
		|| typeValue == ReportConstants.USER_CUSTOMER_HAVING_OVER_LIMIT 
		|| typeValue == ReportConstants.FRAUDULENT_TRANSACTION) ){ %>
	<div>
	<jsp:include page="/pages/reports/searchbody.jsp"></jsp:include>
	</div>
	<% } %>
	<div id='reportsgriddiv'>
		<table id='reportstable'></table>
		<div id='reportspager'></div>
	</div>