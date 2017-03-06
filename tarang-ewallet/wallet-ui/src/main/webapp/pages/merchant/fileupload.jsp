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

	$(document).ready(function() {
		var flag = <%=(Boolean)request.getAttribute("flag")%>;
		if(flag){
			document.getElementById("fileId").disabled = true;						
			document.getElementById("uploadbrandId").disabled = true;
			document.getElementById("cancel").disabled = true;
		}else{
			document.getElementById("fileId").disabled = false;						
			document.getElementById("uploadbrandId").disabled = false;
			document.getElementById("cancel").disabled = false;
		}	
		
	});
	
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
	                var v = "cancel";
  	 				window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant/dashboard';
   				};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function forContinue() {
		submitFormData(document.imgUpload);
	}
	
</script>
<div class="pageheading"><spring:message code="upload.brand.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="imgUpload" method="POST" commandName="imageUploadForm" action="imageUpload" enctype="multipart/form-data">
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
					<td class="formtd"><input type="file" id = "fileId" class="txt_box" name="fileData"/></td>
				</tr>

				<tr>
					<td colspan=2 align="center">
						<div class="formbtns">
					      <input type="button" class="styled-button" id="uploadbrandId" value='<spring:message code="upload.brand.lbl" />' onclick="forContinue();" />
						  <input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
						</div>
					</td>
				</tr>
			</table>
</form:form>
</html>