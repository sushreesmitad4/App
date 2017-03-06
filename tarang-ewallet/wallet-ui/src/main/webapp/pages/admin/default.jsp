<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:url value="${allWalletsUrl}" var="userAmountsUrl"/>
<c:url value="${adminWalletUrl}" var="adminAmountUrl"/>


<script  src="<%=request.getContextPath()%>/jq/js/highcharts.js"></script>  
<script type="text/javascript">
//to display TOTAL BALANCE IN ALL WALLETS
	$(function() {
		$("#totalbalancetable").jqGrid({
			url : '${userAmountsUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="currency.lbl" />', '<spring:message code="total.amount.lbl" />', 
			             '<spring:message code="customer.amount.lbl" />', '<spring:message code="merchant.amount.lbl" />' ],
			colModel : [ {name : 'currencyName', index : 'currencyName', width : 70, sortable : true}, 
			             {name : 'amountString', index : 'amountString', width : 100, formatter:'number', sorttype: 'number', sortable : true}, 
			             {name : 'customerAmountString', index : 'customerAmountString', width : 120, formatter:'number', sorttype: 'number', sortable : true}, 
			             {name : 'merchantAmountString', index : 'merchantAmountString', width : 120, formatter:'number', sorttype: 'number', sortable : true}   
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 70,
			width : 430,
			rownumbers : true,
			pager : '#accountspager',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="total.balance.in.all.wallets.lbl" />',
			shrinkToFit: true,
			loadonce: true,
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
	});
	
	//to display BALANCE IN WALLET
	$(function() {
		$("#walletbalancetable").jqGrid({
			url : '${adminAmountUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="currency.lbl" />', '<spring:message code="wallet.amount.lbl" />', 
			             '<spring:message code="fee.lbl" />', '<spring:message code="tax.lbl" />', 
			             '<spring:message code="total.grid.lbl" />' ],
			colModel : [ {name : 'currencyName', index : 'currencyName', width : 70, sortable : true}, 
			             {name : 'amountString', index : 'amountString', width : 100, formatter:'number', sorttype: 'number', sortable : true},
			             {name : 'feeString', index : 'feeString', width : 100, formatter:'number', sorttype: 'number', sortable : true},
			             {name : 'taxString', index : 'taxString', width : 100, formatter:'number', sorttype: 'number', sortable : true},
			             {name : 'totalString', index : 'totalString', width : 100, formatter:'number', sorttype: 'number', sortable : true}
						],
			postData : {},
			rowNum : 10,
			rowList : [ 10, 20, 40, 60 ],
			height : 70,
			width : 420,
			rownumbers : true,
			pager : '#accountspager1',
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="balance.in.wallet.lbl" />',
			loadonce: true,
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
	});

</script> 
<script type="text/javascript">
	//to display graph  
	var sp = <%= "'" + request.getAttribute("p2p").toString() + "'" %>;
	var sm = <%= "'" + request.getAttribute("p2m").toString() + "'" %>;
	var p2pArray = new Array();
	var p2mArray = new Array();
	
	function strVarray(vstr, varr, asize){
		var v = vstr.split(";");
		var emptyObjSize = 0;
		
		if(v.length < asize){
			emptyObjSize = asize - v.length;
			for(var i = 0; i < emptyObjSize; i++){
				varr[i] = parseFloat(0);	    
			}
		}
		for(var i = emptyObjSize; i < asize; i++){
			varr[i] = parseFloat(v[i - emptyObjSize]);	    
		}	 
	}
	
	function pickArray(orgArray, pickSize){
		var returnArray = new Array();
		var tempIndex = 0;
		if(orgArray.length > pickSize){
			tempIndex = orgArray.length - pickSize; 
		}
		for(var i = tempIndex; i < orgArray.length; i++){
			returnArray[i - tempIndex] = orgArray[i];
		}
		return returnArray;
	}
	
	function function2(v){	
		var title = "";
	    var days = new Array(v);
		var t1 = pickArray(p2pArray, v);
		var t2 = pickArray(p2mArray, v);
		var interval = 0;
 
	    for(i = 0; i < v; i++){
	    	days[i] = i + 1;
	    }
	    if(v == 30){
	    	interval = 10;
	    	title = '<spring:message code="admin.chart.past.thirty.lbl"/>';
		    document.getElementById("30days").style.visibility = "hidden";
		    document.getElementById("7days").style.visibility = "visible";
	    }
	    else if(v == 7){
	    	interval = 5;
	    	title = '<spring:message code="admin.chart.past.seven.lbl"/>';
	    	document.getElementById("7days").style.visibility = "hidden";
	        document.getElementById("30days").style.visibility = "visible";
	    }
	    var chart = new Highcharts.Chart({
	        chart: { renderTo: 'container', type: 'line'},
	        title: {text: title},
	        xAxis: {categories: days},
	        yAxis: {title: {text: '<spring:message code="transaction.lbl"/>'}, tickInterval: interval, min: 0},
	        tooltip: {
	            enabled: true,
	            formatter: function() {
	                return '<b>'+ this.series.name +'</b><br/>'+
	                    this.x + ': ' + this.y + '';
	            }
	        },
	        plotOptions: {line: { dataLabels: {enabled: true}, enableMouseTracking: true}},
	        series: [{name: '<spring:message code="p2p.lbl"/>', data: t1}, {name: '<spring:message code="p2m.lbl"/>', data: t2}]
	    });

	}

	$(document).ready(function() {
	    strVarray(sp, p2pArray, 30);
	    strVarray(sm, p2mArray, 30);
        function2(30);
	});
   
</script>
<div class="clear">
<table>
	<tr>
		<td colspan="2"><jsp:include page="/pages/pageerrors.jsp"></jsp:include></td>
	</tr>
	<tr>
		<td>
			<div class="form">
				<table id='totalbalancetable'></table>
			</div>
		</td>
		<td>
			<div class="form">
				<table id='walletbalancetable'></table>
			</div>
		</td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<a id="7days" href="javascript:function2(7)"><spring:message code="graph.7days.lbl"/></a>
				&nbsp;<a id="30days" href="javascript:function2(30)"><spring:message code="graph.30days.lbl"/>
			</a>
		</td>
	</tr>	
	<tr> 
       	<td colspan="2"> 
			<div id="container" class="admingraph"></div> 	
		</td>
	</tr> 
            
	<tr>
		<td>	
			<table width="470px" class="notificationsbox" >
				<tr>
					<th class="notificationbox_header">&nbsp;<spring:message code="numberof.new.customer.accounts.lbl"/></th>
				</tr>
				<tr>
					<td class="notificationbox_body">
						<br/><spring:message code="last.day.lbl"/>&nbsp; ${lastdayRegisteredCustomersCount}
						<br/><br/><br/><spring:message code="last.week.lbl"/>&nbsp; ${lastweekRegisteredCustomersCount}
						<br/><br/><br/><spring:message code="total.lbl"/>&nbsp; ${totalRegisteredCustomersCount}
						<br/>
					</td>
				</tr>
	   		</table>
		</td>
		<td>
			<table width="470px" class="notificationsbox">
				<tr>
					<th class="notificationbox_header">&nbsp;<spring:message code="numberof.new.merchant.accounts.lbl"/></th>
				</tr>
				<tr>
					<td class="notificationbox_body">
						<br/><spring:message code="last.day.lbl"/>&nbsp; ${lastdayRegisteredMerchantsCount} 
						<br/><br/><br/><spring:message code="last.week.lbl"/>&nbsp; ${lastweekRegisteredMerchantsCount} 
						<br/><br/><br/><spring:message code="total.lbl"/>&nbsp; ${totalRegisterdMerchantsCount} 
						<br/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>