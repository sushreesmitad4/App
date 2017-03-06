<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<div class="pagelayout">
<div>
	<br/><br/><br/>
	<table class="form">
		<tr>
			<td>
				<h3><spring:message code="reg.one.success.msg" /></h3>
				<h3><spring:message code="reg.two.success.msg" /></h3>
			</td>
			<td><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home"><spring:message code="home.lbl" /></a></td>
		</tr>
	</table>
</div>
</div>
