<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is to show Session Expired message.
 --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function goToLogin() {
		try {
			parent.logoutThis();
		}catch(e){
			
		}
	}
	
	function onLoad() {
		setTimeout("goToLogin()", 100000);
	}
	window.setTimeout("url", 100000);
	window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/login';
</script>
<body onload="onLoad();" >
	<div id="session_expired">
		<h1><spring:message code="session.expired.errmsg"/></h1>
		<p class="sessionfont"><spring:message code="session.expired.navigating.errmsg"/></p>
		<br/>
	</div>	
</body>
