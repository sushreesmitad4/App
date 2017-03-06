<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

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
	
	function forBack() {
		document.fileuploadform.submit();
		ajaxLoader($("body"));
	}
	
</script>

<div class="pageheading"><spring:message code="moneytrnsf.multipledestination" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="fileuploadform" method="GET" commandName="sendMoneyMultipleForm" action="fileupload" >
			<table class="form" style="width:60%" >
				<c:if test="${fn:length(listofTrans) > 0}">
					<div class="fileuploadcnfm"><spring:message code="err.recordnotuploaded" /></div>
					<tr>
						<th class="fileuploadcnfmheading"><spring:message code="emailid.lbl" /></th>
						<th class="fileuploadcnfmheading"><spring:message code="amount.lbl" /></th>
						<th class="fileuploadcnfmheading"><spring:message code="message.lbl" /></th>
						<th class="fileuploadcnfmheading"><spring:message code="currency.lbl" /></th>
						<th class="fileuploadcnfmheading"><spring:message code="destination.type.lbl" /></th>
					</tr>
					<c:forEach items="${listofTrans}" var="sendmoney">
						<tr>
						<c:choose>
						<c:when test="${sendmoney.status}">
							<th class="fileuploadcnfminput"><c:out value="${sendmoney.emailId}"/></th>
				            <th class="fileuploadcnfminput"><c:out value="${sendmoney.requestedAmount}"/></th>
							<th class="fileuploadcnfminput"><c:out value="${sendmoney.message}"/></th>
							<th class="fileuploadcnfminput"><c:out value="${sendmoney.currencyCode}"/></th>
							<th class="fileuploadcnfminput"><c:out value="${sendmoney.destinationType}"/></th>
						</c:when>
						</c:choose>	
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${fn:length(listofTrans) == 0}">
					<tr>
						<td colspan="2" align="center"><span class="errormsg" id="erMsg"><spring:message code="export.csv.file" /></span></td>
					</tr>
				</c:if>
			</table>
			<table>
				<tr>
				     <td colspan=2 align="center">
						<div class="formbtns">
						  <input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack();" />
						  <input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
					   </div>
					</td>
				</tr>
			</table>
</form:form>