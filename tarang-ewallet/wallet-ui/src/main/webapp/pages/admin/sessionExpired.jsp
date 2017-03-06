<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is to show Session Expired message.
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
	<div id="session_expired">
		<h1><spring:message code="session.expired.errmsg"/></h1>
		<p class="sessionfont"><spring:message code="session.expired.navigating.errmsg"/></p>
		<br/>
	</div>
<script type="text/javascript">
	window.setTimeout("url", <%=AttributeValueConstants.SETTIMEOUT%>); 
	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminlogin';
</script>
