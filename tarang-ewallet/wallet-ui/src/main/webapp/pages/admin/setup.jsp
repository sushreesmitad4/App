<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.scheduler.util.SchedulerGroupNames" %>
<%@ page import="com.tarang.ewallet.walletui.controller.constants.Admin" %>

<script type="text/javascript">
	function reload(gType) {
		 var message = '<spring:message code="proceed.confirm.msg" />';
		 var yesAction = function () {
			document.setupform.method = "POST";
			document.getElementById("groupName").value = gType;
			document.setupform.submit(); 
			ajaxLoader($("body"));
	     }
	confirmationDialog(yesAction, null, null, message);  
	} 
</script>
	
<div class="pageheading"></div>	
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" name="setupform" action="setup">
	<table class="form" style="width: 35%">
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="cancel.non.register.wallet.txns.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.CANCEL_NON_REGISTER_WALLET_TXNS %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="dispute.force.withdrawal.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.DISPUTE_FORCE_WITHDRAWAL %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="non.settlement.for.reload.money.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.NON_SETTLEMENT %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="pending.account.closers.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.PENDING_ACCOUNT_CLOSERS %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="reconcilation.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.RECONCILATION %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="resent.failed.email.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="start.lbl" />' 
					onclick="reload('<%=SchedulerGroupNames.RESENT_FAILED_EMAIL %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="reload.config.props.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="reload.lbl" />' 
					onclick="reload('<%=Admin.ADMIN_RELOAD_POPERTIES %>')" />
			</td>
		</tr>
		<tr>
			<td>
				<h4 class="tablesubheading">
					<spring:message code="update.http.props.lbl" />
				</h4>
			</td>
			<td  class="formtdbtn">
				<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' 
					onclick="reload('<%=Admin.ADMIN_SETUP_HTTP_POPERTIES %>')" />
			</td>
		</tr>
		<tr>
			<td><input type="hidden" name="groupName" id="groupName" /></td>
			<td></td>
		</tr>
	</table>
</form:form>