<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is to show when logout is clicked.
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
	<h2><spring:message code="logging.out.lbl"/></h2>
<script type="text/javascript">
	window.setTimeout("url", 15000);
	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminlogin';
</script>
