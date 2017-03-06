<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.util.HelpLinkConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">
	function pendingCardAction() {
		var amount = $("#amount").val();
		var code = $("#code").val();
		if(amount == '' && code == ''){
			$('#errorStatus').html('<p class=\"searcherror\"><spring:message code="amount.code.required.errmsg"/></p>');
		}
		else if(amount == ''){
			$('#errorStatus').html('<p class=\"searcherror\"><spring:message code="amount.required.errmsg"/></p>');
		}
		else if(code == ''){
			$('#errorStatus').html('<p class=\"searcherror\"><spring:message code="code.required.errmsg"/></p>');
		}
		else{
			submitFormData(document.pending);
		}
	}
	
	function cancleBtn() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts';
		}
		confirmationDialog(yesAction, null, null, message);
	}
</script>
<div class="block" ></div>
<%@include file="/pages/helptipespopup.jsp" %>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div class="centerstyle">
	<span id="errorStatus" class="searcherror"></span>
</div>
<form:form name="pending" method="POST" commandName="pendingForm" action="pending">
	<table class="form" style="width: 40%">
		<tr class="formtr">
			<td class="formtd">
				<form:label path="amount" cssClass="formlebel">
					<spring:message code="amount.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td style="padding:0px;">
				<table><tr>
				<td style="padding:0px;"><form:input path="amount" id="amount" cssClass="forminput" /></td>
				<td><%=showHelpTipes(HelpLinkConstants.CARD_VERIFICATION_TIPS, request) %></td>
				</tr></table>
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="formerror"><form:errors path="code" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td class="formtd">
				<form:label path="code" cssClass="formlebel">
					<spring:message code="code.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd">
				<form:input path="code" id="code" cssClass="forminput" />
			</td>
		</tr>
		<tr class="formtr">
			<td><form:hidden path="accountId"/></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
		       <div class="formbtns">
				<input type="button" class="styled-button" value='<spring:message code="submit.lbl" />' onclick="pendingCardAction()" />
				<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancleBtn()"/>
			   </div>
			</td>
		</tr>
		
	</table>
</form:form>			