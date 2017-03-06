<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants,com.tarang.ewallet.walletui.controller.constants.Reports" %>
<script type='text/javascript'>
$(document).ready(function() {
		var repSesUrl = '<%=request.getAttribute(Reports.REP_SESSION_URL)%>';
		var contextUrl = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>' + "/" + repSesUrl;
		window.opener.location.href = contextUrl;
		window.close();
});
</script>