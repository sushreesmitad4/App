<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
function cancelAction() {
		document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/scheduler';
	} 
</script>
<div class="pageheading"><spring:message code="recuringdetailview.heading"/></div>
<form:form method="POST" commandName="sendMoneyJobDetailsModel" name="view">	
	<table class="form" style="width: 40%">
		<jsp:include page="details.jsp" />		
		<tr class="formtr">
		<td colspan=2 align="center">
			<div class="formbtns">
				<input type="button" class="styled-button" value='<spring:message code="back.lbl"/>' onclick="cancelAction()" />
			</div>
			</td>
		</tr>
	</table>
</form:form>