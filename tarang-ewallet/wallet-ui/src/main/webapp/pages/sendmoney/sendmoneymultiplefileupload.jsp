<?xml version="1.0" encoding="iso-8859-1"?>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE"/>
<meta http-equiv="PRAGMA" content="NO-CACHE"/>
<meta name="robots" content="noindex,nofollow" />
<html xmlns="http://www.w3.org/1999/xhtml">

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery-ui.css" />
<script src="<%=request.getContextPath()%>/jq/date/jquery-1.8.3.js"></script>
<script src="<%=request.getContextPath()%>/jq/date/jquery-ui.js"></script>

<script>
    
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	                var v = "cancel";
  	 				window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingle?mode=' + v;
   				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function forContinue() {
		submitFormData(document.msendform);
	}
	
</script>

<div class="pageheading"><spring:message code="moneytrnsf.multipledestination" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="msendform" method="POST" commandName="sendMoneyMultipleForm" action="fileupload" enctype="multipart/form-data">
<body>
			<table class="form" style="width:40%">
				<tr>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="formtd">
						<spring:message code="file.location.lbl" />
						<span class="mfield">&nbsp;*</span>
					</td>
					<td class="formtd"><input type="file" class="txt_box" name="fileData"/></td>
				</tr>
				<tr>
					<td colspan=2 align="center">
						<div class ="formbtns">
							<input type="button" class="styled-button" value='<spring:message code="transfer.lbl" />' onclick="forContinue();" />
							<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
						</div>
					</td>
				</tr>
			</table>
	</body>
</form:form>
</html>