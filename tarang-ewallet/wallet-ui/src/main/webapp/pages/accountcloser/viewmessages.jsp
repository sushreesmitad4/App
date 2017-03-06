<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />

<script type='text/javascript'>
	$(document).ready(function() {
		var disptueDis = <%=(Boolean)request.getAttribute("activeDispute")%>;
		if( disptueDis!=null && disptueDis == true){
			document.getElementById("addmessage").disabled = true;
		}
	});

	function sendReqAction() {
		var id = document.getElementById("acountCloserId").value;
		document.location = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountcloser/addmessage?id=' + id;
		ajaxLoader($("body"));
	}
	
</script> 

<div class="pageheading"><spring:message code="account.closing.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form method="GET" commandName="accountCloserForm">
			<table class="form" style="width: 50%">
				<tr>
					<td colspan="2">
						<div><h2 class="tablesubheading"><spring:message code="message.lbl" /></h2></div>
						<div style="height:150px; overflow:auto;" >
						<%
				    		pageContext.setAttribute("CUSTOMER_USER_TYPE_ID", GlobalLitterals.CUSTOMER_USER_TYPE_ID);
							pageContext.setAttribute("MERCHANT_USER_TYPE_ID", GlobalLitterals.MERCHANT_USER_TYPE_ID);
							pageContext.setAttribute("ADMIN_USER_TYPE_ID", GlobalLitterals.ADMIN_USER_TYPE_ID);
							pageContext.setAttribute("ZERO", GlobalLitterals.ZERO_LONG);
						%>
						<table style="width: 410px" >
							<tr>
								<th style="width: 100px; text-align: left;">
									<spring:message code="date.lbl" />
								</th>
								<th style="width: 100px; text-align: left;">
									<spring:message code="from.lbl" />
								</th>
								<th style="width: 200px; text-align: left;">
									<spring:message code="message.lbl" />
								</th>
							 </tr>
							<c:if test="${fn:length(accountCloserForm.messageList) > ZERO}">
								<c:forEach items="${accountCloserForm.messageList}" var="messges">
								 <tr>
									<td><c:out value="${messges.messageDate}" /></td>
									<td>
										<c:if test="${messges.creator == CUSTOMER_USER_TYPE_ID}">
											<spring:message code="self.lbl" />
										</c:if>
										<c:if test="${messges.creator == MERCHANT_USER_TYPE_ID}">
											<spring:message code="self.lbl" />
										</c:if>
										<c:if test="${messges.creator == ADMIN_USER_TYPE_ID}">
											<spring:message code="admin.lbl" />
										</c:if>
									</td>
									<td><div style="width:200px;" class="linebreak" ><c:out value="${messges.message}" /></div></td>
								</tr>
								</c:forEach>
							</c:if>
						</table>
						</div>
					</td>
				</tr>
				<tr>
					<td><form:hidden path="acountCloserId" /></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" id="addmessage" onclick="sendReqAction()" class="styled-button" value='<spring:message code="add.message.lbl" />' />
					  </div>
					</td>
				</tr>
			</table>
</form:form>