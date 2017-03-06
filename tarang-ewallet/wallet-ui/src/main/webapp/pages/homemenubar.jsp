<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<div class="mbtnavbar">
	<ul id="mbtnav">
		<li class="top"><a href="#"> <spring:message code="personal.lbl" /></a>
		<li class="top"><a href="#"> <spring:message code="business.lbl" /></a>		
		<li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home/login"> <spring:message code="login.lbl" /></a>
		<li class="top"><a href="#"><spring:message code="signup.lbl" /></a>
		<ul class="sub">
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant/registration"><spring:message code="merchant.registration.lbl" /></a></li>
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customer/registration"><spring:message code="customer.registration.lbl" /></a></li>
		</ul>
		</li>
	</ul>
</div>
