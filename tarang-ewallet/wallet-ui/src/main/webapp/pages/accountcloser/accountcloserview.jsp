<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for admin user module. This includes admin user management module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%> 
<script type="text/javascript">
	function backAction() {
		 window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt';
	}
</script>
<div class="pageheading"><spring:message code="account.closer.lbl" /></div>
<form:form method="POST" commandName="accountCloserFormView">
	<table class="form" style="width: 50%">
		<tr>
			<td colspan="2">
				<jsp:include page="accountclosercommon.jsp"/>
			</td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="button" onclick="backAction()" class="styled-button" value='<spring:message code="back.lbl" />' />
				</div>
			</td>
		</tr>
	</table>
</form:form>
