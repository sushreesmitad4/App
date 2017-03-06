<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.util.GlobalLitterals" %>
<%@page isErrorPage="true" %>

<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/wallet.css" />
<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />
<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/menubar.css" />
<link rel="shortcut icon" type="image/ico" href="<%=request.getContextPath()%>/img/favicon.ico"/>
<title><spring:message code="wallet.error.page.lbl" /></title>
<jsp:include page="/pages/commonscript.jsp"></jsp:include>

<div><img src="<%=request.getContextPath()%>/img/customerlogo.png" alt="logo" />
<h1><span><spring:message code="oops.lbl" /></span></h1>
<table border="1" class="form" style="width:80%">
	<tr valign="top">
		<td width="15%"><b><spring:message code="err.lbl" /></b></td>
		<td class="formdataleft">${pageContext.exception}</td>
	</tr>
	<tr>
		<td></td>
		<td class="formdataleft">
			<% 
			if(session.getAttribute(AttributeConstants.USER_TYPE) != null){
				if(session.getAttribute(AttributeConstants.USER_TYPE).equals(GlobalLitterals.ADMIN_USER_TYPE)){
				%>  
				<spring:message code="return.to.lbl" /><a href="javascript:void(o)" onclick="expire('<%=GlobalLitterals.ADMIN_USER_TYPE%>');"><spring:message code="admin.login.lbl" /></a>
				<%
				}
				else{
					%>  
				 	<spring:message code="return.to.lbl" /><a href="javascript:void(o)" onclick="expire('<%=GlobalLitterals.CUSTOMER_USER_TYPE%>');"><spring:message code="login.lbl" /></a>
				 	<%
				}
			}
			else{
				%>  
			 	<spring:message code="return.to.lbl" /><a href="javascript:void(o)" onclick="expire('');"><spring:message code="home.lbl" /></a>
			 	<%
			}
			%>
		</td>
	</tr>
	<tr valign="top">
		<td width="15%"><b><spring:message code="uri.lbl" /></b></td>
		<td class="formdataleft">${pageContext.errorData.requestURI}</td>
	</tr>
	<tr valign="top">
		<td width="15%"><b><spring:message code="status.code.lbl" /></b></td>
		<td class="formdataleft">${pageContext.errorData.statusCode}</td>
	</tr>
	<tr valign="top">
		<td width="15%"><b><spring:message code="stack.trace.lbl" /></b></td>
		<td class="formdataleft">
			<c:forEach var="trace" 
			         items="${pageContext.exception.stackTrace}">
			<p>${trace}</p>
			</c:forEach>
		</td>
	</tr>
</table>
</div>