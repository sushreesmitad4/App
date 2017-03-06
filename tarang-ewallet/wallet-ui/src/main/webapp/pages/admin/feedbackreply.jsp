<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.model.Feedback,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">

	$(document).ready(function() {
		document.getElementById("responsemsg").value = "";
	});

	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/responsefeedback/feedbacklist';
	    }
		confirmationDialog(yesAction, null, null, message);
 	} 
	
	function continueAction(){
		submitFormData(document.feedform);
	} 
	
</script>
<div class="pageheading"><spring:message code="reply.form.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="feedform" method="POST" commandName="feedbackForm" action="reply" >        	  
	<table class="form" style="width: 45%">
		<tr>
			<td>
				<form:label path="queryTypeName" cssClass="formlebel">
					<spring:message code="querry.type.lbl" />
				</form:label></td>
				<td><form:input path="queryTypeName" cssClass="forminput" readonly="true"/></td>
		</tr>
		<tr>
			<td>
				<form:label path="userTypeName" cssClass="formlebel">
					<spring:message code="user.type.lbl" />
				</form:label></td>
				<td><form:input path="userTypeName" cssClass="forminput" readonly="true"/></td>
		</tr>
		<tr>
			<td>
				<form:label path="userMail" cssClass="formlebel">
					<spring:message code="mailid.lbl" />
				</form:label></td>
				<td><form:input path="userMail" cssClass="forminput" readonly="true"/></td>
		</tr>
		<tr>
			<td>
				<form:label path="dateAndTime" cssClass="formlebel">
					<spring:message code="feedback.date.lbl" />
				</form:label></td>
				<td><form:input path="dateAndTime" cssClass="forminput" readonly="true"/></td>
		</tr>
		<tr>
			<td>
				<form:label path="subject" cssClass="formlebel">
					<spring:message code="subject.lbl" /></form:label>
			</td>
			<td><form:textarea path="subject" cssClass="forminput" readonly="true"/></td>				
              </tr>
              <tr>
			<td>
				<form:label path="message" cssClass="formlebel">
					<spring:message code="message.lbl" />
				</form:label>
			</td>
			<td><form:textarea path="message" cssClass="forminput" readonly="true"/></td>				
               </tr>
               <tr>
			<td></td>
			<td>
				<form:errors path="responseMessage" cssClass="error" />
			</td>
		</tr>
      	<tr>
			<td>
				<form:label path="responseMessage" cssClass="formlebel">
					<spring:message code="response.message.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
		    <td><form:textarea id="responsemsg" path="responseMessage" cssClass="forminput" /></td>				
         </tr>
         <tr>
         <td><form:hidden path="id"/></td>
         </tr>
          <tr>
	     	<td colspan=2 align="center">
				<div class="formbtns">
				      <%
			               Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
			                 	MenuConstants.QUERY_FEEDBACK, MenuConstants.MANAGE_PERMISSION);
	                      	if(adminAccessCheck){
                      %>	
	    			<input type="button" class="styled-button"  value='<spring:message code="submit.lbl" />' onclick="continueAction()"/>
	  				<% } %>
	  				<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()"/>
	  			</div>
	  		</td>
		 </tr>
	</table>
</form:form>