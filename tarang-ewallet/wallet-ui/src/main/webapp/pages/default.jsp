<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.common.util.UserStatusConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<c:url value="${urlAccountList}" var="accountsUrl"/>
<c:url value="${urlBalanceList}" var="balanceUrl"/>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.util.GlobalLitterals" %>

<script type="text/javascript">

<%
String name = (String) session.getAttribute("name");
%>
	$(function() {
		$("#accountstable").jqGrid(
			{
				url : '${accountsUrl}',
				datatype : 'json',
				mtype : 'GET',
				colNames : [ '<spring:message code="txn.id.lbl" />','<spring:message code="date.lbl" />',
							/* '<spring:message code="user.currency.lbl" />', */'<spring:message code="emailid.lbl" />',
							'<spring:message code="others.currency.lbl" />','<spring:message code="transaction.type.lbl" />',
							'<spring:message code="cr.dr.lbl" />','<spring:message code="balance.lbl" />',
							'<spring:message code="amount.lbl" />','<spring:message code="deduction.lbl" />'],
				colModel : [ {name : 'transactionid', index : 'transactionid', width : 55, editable : false, sortable : true, search : false},
				             {name : 'griddisplayupdateddate', index : 'griddisplayupdateddate', width : 95, editable : false, sortable : true, search : true},
							/* {name : 'selfcurrency', index : 'selfcurrency', width : 80, editable : false, sortable : true, search : false}, */
							{name : 'emailid', index : 'emailid', width : 120, editable : false, sortable : true, search : false},
							{name : 'otherscurrency', index : 'otherscurrency', width : 80, editable : false, sortable : true, search : false}, 
							{name : 'transactiontype', index : 'transactiontype', width : 90, editable : false, sortable : true, search : true}, 
							{name : 'balancestatus', index : 'balancestatus', width : 40, editable : false, sortable : true, search : false},
							{name : 'displayPayeeBalance', index : 'displayPayeeBalance', width : 60, editable : false, 
				            	sortable : true, formatter:'number', sorttype: 'number', search : true}, 
							{name : 'displayamount', index : 'displayamount', width : 60, editable : false, 
								sortable : true, formatter:'number', sorttype: 'number', search : true}, 
							{name : 'displaydeduction', index : 'displaydeduction', width : 60, editable : false,	
				            	sortable : true, formatter:'number', sorttype: 'number', search : true} 
							],
				postData : {},
				rowNum : 10,
				rowList : [ 10, 20, 40, 60 ],
				height : 140,
				width : 900,
				rownumbers : true,
				pager : '#accountspager',
				sortname : '',
				viewrecords : true,
				sortorder : "desc",
				caption : '<spring:message code="my.recent.activity.lbl" />',
				loadonce: true,
				 loadComplete: function() {
				    $("tr.jqgrow:odd").css("background", "#62aef0");
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
	});

	$(function() {
		$("#balancetable").jqGrid({
			url : '${balanceUrl}',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ '<spring:message code="currency.lbl" />', '<spring:message code="amount.lbl" />'],
			colModel : [ {name : 'currencyName', index : 'currencyName', width : 150, sortable : true}, 
			             {name : 'amountString', index : 'amountString', width : 150, sortable : true, formatter:'number', sorttype: 'number'} 
			          ],
			postData : {},
			height : 100,
			width : 450,
			rownumbers : true,
			sortname : '',
			viewrecords : true,
			sortorder : "desc",
			caption : '<spring:message code="balance.lbl" />',
			loadonce: true,
			loadComplete: function() {
			    $("tr.jqgrow:even").css("background", "#62aef0");
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
	});
	var name = '<%=name%>';
	$( document ).ready(function() {
	   if(name== "null null")
		{
		   document.getElementById('Namein').style.display = 'none'; 
		}else{
             document.getElementById('Nameout').style.display = 'none';
		}
	});
</script>

<div class="clear">
<table>
	<tr>
		<%-- <td colspan="2"><jsp:include page="/pages/pageerrors.jsp"></jsp:include></td> --%>
	</tr>
	<tr>
		<td align="left">
			<div id="Namein" class="cus_mer_dashboard_heading">
				<span class="cus_mer_welcome_heading"> &nbsp;<spring:message code="welcome.lbl" />, <%=session.getAttribute("name")%></span>
			</div>
			<div id="Nameout" class="cus_mer_dashboard_heading">
				<span class="cus_mer_welcome_heading"> &nbsp;<spring:message code="welcome.lbl" />, <spring:message code="pleasecomplete.lbl" /><a class="cus_mer_Null_welcome_heading" href="/wallet-ui/tarang/customer/updateprofile"> <spring:message code="profile.lbl" /></a></span>
			</div>
		</td>
		<td align="right">
			<div class="cus_mer_dashboard_heading">
				<%
					Long userStatus = (Long)session.getAttribute("userStatus");
					if(!UserStatusConstants.APPROVED.equals(userStatus)){
				%>
					<span class="cus_mer_welcome_heading"><spring:message code="status.lbl" /></span>:
					<span class="cus_mer_status_heading"> ${userstatusname} </span>
				<%} %>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="form">
				<div class="subheading">
					<spring:message code="wallet.balance.lbl" />: ${userwalletamount}&nbsp;${userwalletcurrency}&nbsp;		
				</div>	
				<div>
					<table id='balancetable'></table>
				</div>
			</div>	
		</td>
		<td>
			<table class="notificationsbox cus_mer_row2_col2">
				<tr>
					<th class="notificationbox_header">&nbsp;<spring:message code="notifications.lbl" /></th>
				</tr>
				<tr>
					<td>
						<div class="cus_mer_row2_col2_content">
							<a href="#" class="formlink"><spring:message code="update.credit.card.info.lbl" /></a>
							<br/><br/><a href="#" class="formlink"><spring:message code="policy.updates.lbl" /></a>
						</div>
					</td>
				</tr>
	   		</table>
		</td>
	</tr>
	<tr>
		<td colspan=2>
			<div class="dashboardtr2div2">
				<br>
				<% if(GlobalLitterals.CUSTOMER_USER_TYPE.equals((String)session.getAttribute(AttributeConstants.USER_TYPE))) {%>
					<div class="dashboardtrecentactivitydiv" >
						<span ><spring:message code="my.recent.activity.lbl" /></span>&nbsp;&nbsp; |&nbsp;&nbsp; 
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=3" class="formlink1">
							<spring:message code="payment.received.lbl" />
						</a>
						&nbsp;&nbsp; | &nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=4" class="formlink1">
							<spring:message code="payment.send.lbl" />
						</a>
						&nbsp;&nbsp;
					</div>	
					<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=1" class="formlink1 dashboardahref">
						<spring:message code="view.all.transactions.lbl" />
					</a>&nbsp;&nbsp;
				<% }
				else {%>
					<div class="dashboardtrecentactivitydiv">
						<span ><spring:message code="my.recent.activity.lbl" /></span>&nbsp;&nbsp; |&nbsp;&nbsp; 
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=8" class="formlink1">
							<spring:message code="payment.received.lbl" />
						</a>
						&nbsp;&nbsp; | &nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=9" class="formlink1">
							<spring:message code="payment.send.lbl" />
						</a>
						&nbsp;&nbsp; 
					</div>	
					<a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/cm?reporttype=7" class="formlink1 dashboardahref">
						<spring:message code="view.all.transactions.lbl" />
					</a>&nbsp;&nbsp;
				<% }  %> 
			</div>
		</td>
	</tr>
	<tr>
		<td colspan=2>
			<div class="form">        
				<table id='accountstable'></table>
			</div>
		</td>
	</tr>
</table>
</div>