<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals"%>
<% pageContext.setAttribute("CUSTOMER_USER_TYPE", GlobalLitterals.CUSTOMER_USER_TYPE); %>

<tr>
	<td><form:label path="emailid" cssClass="formlebel"><spring:message code="emailid.lbl"/>&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.emailid}" /></td>
</tr>


<%
	pageContext.setAttribute("CUSTOMER_USER_TYPE", GlobalLitterals.CUSTOMER_USER_TYPE);
%>

<c:choose>
	<c:when test="${accountCloserFormView.userType eq CUSTOMER_USER_TYPE}">
		<tr>
			<td><form:label path="userType" cssClass="formlebel"><spring:message code="user.type.lbl" />&nbsp;</form:label></td>
			<td ><spring:message code="customer.lbl" /></td>
		</tr>
	</c:when>
	<c:otherwise>
		<tr>
			<td><form:label path="userType" cssClass="formlebel"><spring:message code="user.type.lbl" />&nbsp;</form:label></td>
			<td ><spring:message code="merchant.lbl" /></td>
		</tr>
	</c:otherwise>
</c:choose>

<tr>
	<td>
		<form:label path="personOrOrganisationName" cssClass="formlebel"><spring:message code="person.or.organisation.name.lbl" />&nbsp;</form:label>
	</td>
	<td ><c:out value="${accountCloserFormView.personOrOrganisationName}" /></td>
</tr>


<tr>
	<td><form:label path="registrationDate" cssClass="formlebel"><spring:message code="registration.date.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.registrationDateAsString}" /></td>
</tr>


<tr>
	<td valign="top"><form:label path="userWalletDetails" cssClass="formlebel"><spring:message code="wallet.balances.lbl" />&nbsp;</form:label></td>
	<td>
		<c:if test="${accountCloserFormView.userWalletDetails ne null }">
			<c:forEach items="${accountCloserFormView.userWalletDetails}" var="entry">
		        <c:out value="${entry.value}" /><br></br>
		    </c:forEach>
		</c:if>
	</td>
</tr> 


<tr>
	<td><form:label path="lastTransactionDate" cssClass="formlebel"><spring:message code="last.transaction.date.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.lastTransactionDateAsString}" /></td>
</tr>


<tr>
	<td><form:label path="userStatus" cssClass="formlebel"><spring:message code="user.status.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.userStatus}" /></td>
</tr>


<tr>
	<td><form:label path="accountCloserStatus" cssClass="formlebel"><spring:message code="account.closer.status.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.accountCloserStatus}" /></td>
</tr>


<tr>
	<td><form:label path="isRecurringPaymentExist" cssClass="formlebel"><spring:message code="is.recurring.payment.exist.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.isRecurringPaymentExist}" /></td>
</tr>


<tr>
	<td><form:label path="requestedDate" cssClass="formlebel"><spring:message code="requested.date.lbl" />&nbsp;</form:label></td>
	<td ><c:out value="${accountCloserFormView.requestedDateAsString}" /></td>
</tr>



<c:if test="${isViewAccountClose == true}">
	<tr>
		<td colspan="2">
			<jsp:include page="messagelist.jsp"></jsp:include>
		</td>
	</tr>
</c:if>
