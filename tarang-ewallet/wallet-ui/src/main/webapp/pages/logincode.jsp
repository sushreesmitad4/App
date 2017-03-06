<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Login module. This includes User Login module
 --%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function continueAction() {
		submitFormData(document.formsubmitcode);
	}
	
	function cancelAction() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
		}
		confirmationDialog(yesAction, null, null, message);
	}	
</script>

<div class="pageheading"><spring:message code="authentication.code.lbl"/></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form  commandName="loginUserForm" action="logincode" method="POST" name="formsubmitcode">
		<table class="form" style="width: 30%">
			<tr>
      			<td></td>
	  			<td class="formerror"><form:errors path="code"  cssClass="error"/></td>
	  		</tr>
			<tr>
				<td align="right"><form:label path="code" cssClass="formlebel"><spring:message code="code.lbl" /></form:label> &nbsp; &nbsp;</td>
				<td align="left"><form:password path="code" autocomplete="off" cssClass="forminputcvv"/>
			</tr>
			<tr>
				<td colspan=2 align="center">
				  <div class="formbtns">
					 <input type="button" onclick="continueAction()" class="styled-button" value='<spring:message code="submit.lbl" />' />
					 <input type="button" onclick="cancelAction()" class="styled-button" value='<spring:message code="cancel.lbl"/>' />
				  </div>
				</td>
			</tr>
		</table>
</form:form>