<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script type="text/javascript">
	function reloadmoneyurl(mtype) {
		document.getElementById("mode").value=mtype;
		submitFormData(document.freloadmoneyrequest);
	}	
	function forCancel() {
	    var message = '<spring:message code="cancel.confirm.msg"/>';
		var yesAction = function () {
      	window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reloadmoney';
       	};
   		 confirmationDialog(yesAction, null, null, message); 	
	} 	
	
</script>
<div class="pageheading"><spring:message code="reload.money.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="freloadmoneyrequest" method="POST" commandName="reloadMoneyForm" action="reloadmoneyrequest">
			<table class="form" style="width: 45%">
				<tr class="formtr">
					<td class="formtd">
						<form:label path="accountOrCardHolderName" cssClass="formlebel">
							<spring:message code="bank.card.holder.name.lbl" />
						</form:label>
					</td>
					<td class="formtd"><c:out value="${reloadMoneyForm.accountOrCardHolderName}" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="bankOrCardNumber" cssClass="formlebel">
							<spring:message code="bank.card.number.lbl" />
						</form:label>
					</td>
					<td class="formtd"><c:out value="${reloadMoneyForm.bankOrCardNumber}" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="bankOrCardName" cssClass="formlebel">
							<spring:message code="bank.card.provider.lbl" />
						</form:label>
					</td>
					<td class="formtd"><c:out value="${reloadMoneyForm.bankOrCardName}" /></td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="cvv" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="cvv" cssClass="formlebel">
							<spring:message code="card.verification.number.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:password path="cvv" cssClass="forminputcvv" autocomplete="off" maxlength="4"/>
						<jsp:include page="/pages/cvvpopup.jsp" />
					</td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="amount" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="amount" cssClass="formlebel"><spring:message code="amount.lbl" /><span class="mfield">&nbsp;*</span></form:label>
					</td>
					<td class="formtd">
						<form:input path="amount" id="amount" cssClass="forminputcvv" style="width: 170px"/>&nbsp;&nbsp;
						<c:out value="${reloadMoneyForm.currecnyCode}" />
					</td>
				</tr>
				<tr class="formtr">
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" id="reload" class="styled-button" value='<spring:message code="reload.money.lbl" />' onclick="reloadmoneyurl('reload')" />
						<input type="button" id="reload" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel();" />
				      </div>
				    </td>
				</tr>
				<tr>
					<td><form:hidden path="accountId"/></td>
					<td>
						<form:hidden path="mode" />
						<form:hidden path="accountOrCardHolderName" />
						<form:hidden path="bankOrCardNumber" />
						<form:hidden path="bankOrCardName" />
						<form:hidden path="currecnyCode" />
						<form:hidden path="cardType" />
					</td>
				</tr>
			</table>
</form:form>