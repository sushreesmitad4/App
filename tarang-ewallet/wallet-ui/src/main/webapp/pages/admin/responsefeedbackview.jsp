<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.model.Feedback,com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<script type="text/javascript">
	function forBack() {
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/responsefeedback/feedbacklist';	
 	} 
	
	function continueAction(){
		submitFormData(document.ffeedback);
	}
	
</script>
<div class="pageheading"><spring:message code="feedback.details.lbl" /></div>   
<form:form method="GET" commandName="feedbackForm" action="reply" name="ffeedback">         
			<table class="form tablefixed" style="width: 50%">
				<tr>
					<td style="width: 140px;">
						<form:label path="queryTypeName" cssClass="formlebel">
							<spring:message code="querry.type.lbl" />
						</form:label></td>
					<td>${feedbackForm.queryTypeName}</td>
				</tr>
				<tr>
					<td>
						<form:label path="userTypeName" cssClass="formlebel">
							<spring:message code="user.type.lbl" />
						</form:label></td>
					<td>${feedbackForm.userTypeName}</td>
				</tr>
				<tr>
					<td>
						<form:label path="userMail" cssClass="formlebel">
							<spring:message code="mailid.lbl" />
						</form:label></td>
					<td>${feedbackForm.userMail}</td>
				</tr>
				<tr>
					<td>
						<form:label path="dateAndTime" cssClass="formlebel">
							<spring:message code="feedback.date.lbl" />
						</form:label></td>
					<td>${feedbackForm.dateAndTime}</td>
				</tr>
				<tr>
					<td><form:label path="subject" cssClass="formlebel">
							<spring:message code="subject.lbl" /></form:label></td>
				    <td>${feedbackForm.subject}</td>					
                </tr>
                <tr>
					<td style="vertical-align:top;">
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl" /></form:label></td>
				    <td><div>${feedbackForm.message}</div></td>					
                </tr>
			</table>
			
			<c:forEach var="responses" items="${feedbackresponses}">
			<%
				Feedback feedback = (Feedback)pageContext.getAttribute("responses");
				if(feedback != null){
			  		String responseMessage = feedback.getResponseMessage();
			  		String responseDate = feedback.getResponseDateAsString().toString(); 
			  		String responseSenderMailId = feedback.getResponseSenderMailId();
 			%>
 				
			<br>
		    <table class="form tablefixed" style="width: 50%">
		    	<tr>
			  		<td style="width: 140px;"><label class="formlebel"><spring:message code="response.sender.lbl" /></label> </td>
			  		<td><%=responseSenderMailId%></td>
				</tr>
		    	<tr>
			  		<td><label class="formlebel"><spring:message code="response.date.lbl" /></label> </td>
			  		<td><%=responseDate%></td>
				</tr>
				<tr>
			  		<td style="vertical-align:top;"><label class="formlebel"><spring:message code="response.message.lbl" /></label> </td>
			  		<td> 
			  			<%if(responseMessage != null && responseMessage.length() > 65){ %>
			  				<div>
			  			<%	} %>
			  			<%=responseMessage%>
			  			<%if(responseMessage != null && responseMessage.length() > 65){ %>
			  				</div> 
			  			<%	} %>
			  		</td>
				</tr>
			</table>
			<%	} %>
			</c:forEach>
			<table style="width: 50%">
				<tr>
					<td><form:hidden path="id" /></td>
				</tr>
				<tr>
			  		<td colspan=2 align="center">
						<div class="formbtns">
						   <%
				               Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
				                 	MenuConstants.QUERY_FEEDBACK, MenuConstants.MANAGE_PERMISSION);
		                      	if(adminAccessCheck){
                            %>	
			    			<input type="button" class="styled-button"  value='<spring:message code="reply.lbl" />' onclick="continueAction()" />
							<% } %>
							<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack()" />
			  			</div>
			  		</td>			  		
				</tr>
			</table>	
</form:form>			
			