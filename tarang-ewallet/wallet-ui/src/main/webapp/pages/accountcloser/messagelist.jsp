<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>			
<%@ page import="com.tarang.ewallet.util.GlobalLitterals"%>
			
<tr>
	<td colspan="2">
		<h2><spring:message code="message.lbl" /></h2>
		<div style="height:150px; overflow:auto;" >
		<%
			pageContext.setAttribute("CUSTOMER_USER_TYPE_ID", GlobalLitterals.CUSTOMER_USER_TYPE_ID);
			pageContext.setAttribute("MERCHANT_USER_TYPE_ID", GlobalLitterals.MERCHANT_USER_TYPE_ID);
			pageContext.setAttribute("ADMIN_USER_TYPE_ID", GlobalLitterals.ADMIN_USER_TYPE_ID);
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
			<c:if test="${fn:length(accountCloserFormView.messageList) > 0}">
                <c:forEach items="${accountCloserFormView.messageList}" var="messges">
				<tr>
					<td><c:out value="${messges.messageDate}" /></td>
					<td><c:if test="${messges.creator == CUSTOMER_USER_TYPE_ID}">
							<spring:message code="customer.lbl" />
						</c:if>
						<c:if test="${messges.creator == MERCHANT_USER_TYPE_ID}">
							<spring:message code="merchant.lbl" />
						</c:if>
						<c:if test="${messges.creator == ADMIN_USER_TYPE_ID}">
							<spring:message code="self.lbl" />
						</c:if>
					</td>
					<td>
					<div style="width:200px;" class="linebreak" ><c:out value="${messges.message}" /></div>
					</td>
				</tr>
				</c:forEach>
			</c:if>
		</table>
	</div>
	</td>
</tr>