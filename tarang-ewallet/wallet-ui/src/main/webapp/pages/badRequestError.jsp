<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.util.GlobalLitterals" %>

<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/wallet.css" />
<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />
<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/menubar.css" />
<link rel="shortcut icon" type="image/ico" href="<%=request.getContextPath()%>/img/favicon.ico"/>
<title><spring:message code="wallet.error.page.lbl" /></title>
<jsp:include page="/pages/commonscript.jsp"></jsp:include>
<center>
	<div>
		<div>
			<div><img src="<%=request.getContextPath()%>/img/customer_logo_errorpage.png" alt="logo" /></div>
				<div>
					<h1> ${pageContext.errorData.statusCode} - <spring:message code="pagenotfound.lbl"/></h1>
					<span class="formdata"><spring:message code="script.error.pleasecheckaddress.lbl"/> <br/>
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
					</span>
				</div>
		</div>
	</div>
</center>
	

