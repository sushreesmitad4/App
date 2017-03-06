<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">	
	function cancel() {
			var message = '<spring:message code="cancel.confirm.msg" />';
			var yesAction = function () {
	        				document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/tax';
	        			};
	        confirmationDialog(yesAction, null, null, message);				
	} 
	function continueAction(){
		submitFormData(document.tax);
	}	
</script>
<div class="pageheading"><spring:message code="add.tax.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="POST" commandName="taxForm" action="add" name="tax">
	<table class="form" style="width: 40%">
		<tr>
			<td></td>
			<td><form:errors path="country" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="country" cssClass="formlebel">
					<spring:message code="country.lbl"/><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:select path="country" cssClass="formlist" >
				<form:option value="<%=GlobalLitterals.ZERO_LONG%>"><spring:message code="please.select.lbl" /></form:option>
				<c:forEach items="${countryList}" var="entry">
				<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
				</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="percentage" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td>
				<form:label path="percentage" cssClass="formlebel">
					<spring:message code="percentage.on.fee.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td><form:input path="percentage" cssClass="forminput" /></td>
		</tr>		
		<tr class="formtr" >
			<td colspan=2 align="center">
				<div class="formbtns">
				  <% 
						Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
							MenuConstants.TAX_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
				    	if(adminAccessCheck){
	              %>	
					<input type="button" class="styled-button" value='<spring:message code="create.lbl" />' onclick ="continueAction()" />
					<% } %>
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
				</div>
			</td>
		</tr>
	</table>
</form:form>