<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">

    function submitBtn() {
    	var cvvNumber = $("#cvvNumber").val();
    	if(cvvNumber==''){
    		$('#errorStatus').html('<p class=error><spring:message code="cvv.required.errmsg"/></p>');
    	}
    	else{
    		submitFormData(document.notverified);
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
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<div class="centerstyle">
	<span id="errorStatus" class="searcherror"></span>
</div>
<form:form name="notverified" action="notverified" method="POST" commandName="notVerifiedForm">
	<table class="form" style="width: 40%">
		<tr class="formtr">
			<td class="formtd">
				<form:label path="cvvNumber" cssClass="formlebel">
					<spring:message code="card.verification.number.lbl" /><span class="mfield">&nbsp;*</span>
				</form:label>
			</td>
			<td class="formtd">
				<form:password path="cvvNumber" id="cvvNumber" cssClass="forminputcvv" autocomplete="off"/>
				<jsp:include page="/pages/cvvpopup.jsp" />
			</td>
		</tr>
		<tr class="formtr">
			<td><form:hidden path="accountId"/></td>
			</tr>
		<tr>
			<td colspan=2 align="center">
		       <div class="formbtns">
				<input type="button" class="styled-button" value='<spring:message code="submit.lbl" />' onclick="submitBtn()" />
				<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancleBtn()" />
			  </div>
			</td>
		</tr>
	</table>
</form:form>