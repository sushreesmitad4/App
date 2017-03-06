<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for admin user module. This includes admin user management module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">
	
	function cancelAction() {
		 var message = '<spring:message code="cancel.confirm.msg" />';
		 var yesAction = function () {
			 window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt';
	     }
	confirmationDialog(yesAction, null, null, message);  
	}
	
	function continueAction() {
		submitFormData(document.accountclose);
	}
	
</script>
<div class="pageheading"><spring:message code="account.closer.lbl" /></div>
<form:form name="accountclose" method="POST" commandName="accountCloserFormView" action="update">
		<table class="form" style="width: 50%">
			<jsp:include page="accountclosercommon.jsp"></jsp:include>
			<tr>
				<td></td>
				<td class="formerror"><form:errors path="message" cssClass="error" /></td>
			</tr>
			
			<tr>
				<td>
					<form:label path="message" cssClass="formlebel">
						<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td ><form:textarea path="message" cssClass="forminput"></form:textarea></td>
			</tr>
			
			<tr>
				<td></td>
				<td class="formerror"><form:errors path="accCloserStatus" cssClass="error" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="accountCloserStatus" cssClass="formlebel">
						<spring:message code="account.closer.status.lbl" /><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td >
					<c:if test="${accountCloserStatus ne null }">
						<form:radiobuttons path="accCloserStatus" items="${accountCloserStatus}" />
					</c:if>
				</td>
			</tr>
			
		<tr>
			<td colspan=2 align="center">
			    <div class="formbtns">
			    <%
			               Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
			                 	MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT, MenuConstants.MANAGE_PERMISSION);
	                      	if(adminAccessCheck){
                   %>	
				  <input type="button" onclick="continueAction()" class="styled-button" value='<spring:message code="update.lbl" />' />
				  <% } %>
				  <input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="cancel.lbl" />' /> 
				 </div>
			</td>
				   <form:hidden path="id"/>
				  
		 </tr>
			<tr>
				<td colspan="2">
					<jsp:include page="messagelist.jsp"></jsp:include>
				</td>
			</tr>
		</table>
		<div class="clear"></div>
</form:form>
