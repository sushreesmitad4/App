<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.accounts.util.FundingAccountStatus,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">
	$(document).ready(function() {
		hideAddress();
	}); 
	var cardStatus = ${accountStatus};
	
	function forContinue(mode) {
		document.getElementById("mode").value = mode;
		submitFormData(document.myform);
	}
	
	function forCancel(){
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts';
		}
		confirmationDialog(yesAction, null, null, message);
	}
	
	function hideAddress() {
		var isBillingAdd = $('input[name="isSameAsProfileAddress"]:checked').val();
		if(isBillingAdd == 'true') {
			/* disabled the fields */
			document.getElementById("addrOne").readOnly = true;
			document.getElementById("addrTwo").readOnly = true;
			document.forms[0].country.disabled = true ;
			document.forms[0].state.disabled = true ;
			document.getElementById("city").readOnly = true;
			document.getElementById("postalCode").readOnly = true;
		}
		else if(isBillingAdd == 'false') {
			/* enable the fields */
			document.getElementById("addrOne").readOnly = false;
			document.getElementById("addrTwo").readOnly = false;
			document.forms[0].country.disabled = false ;
			document.forms[0].state.disabled = false ;
			document.getElementById("city").readOnly = false;
			document.getElementById("postalCode").readOnly = false;
		}
	}
</script>
<%
	pageContext.setAttribute("verifiedStatus", FundingAccountStatus.VERIFIED_STATUS);
%>
<div class="pageheading"><spring:message code="edit.card.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="myform" method="POST" commandName="addCardAccountForm" autocomplete="off" action="editcard">
			<table class="form" style="width: 45%">
		  	<c:choose>
		  	<c:when test="${accountStatus==verifiedStatus}">
		  		<tr class="formtr">
					<td class="formtd">
						<form:label path="accountHolderName" cssClass="formlebel">
							<spring:message code="card.holder.name.lbl"/>
						</form:label>
					</td>
					<td class="formtd">${addCardAccountForm.accountHolderName}</td>
				</tr>
		  	</c:when>
		  	<c:otherwise>
		  		<tr>
				<td></td>
				<td class="formerror"><form:errors path="accountHolderName" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="accountHolderName" cssClass="formlebel">
							<spring:message code="card.holder.name.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="accountHolderName" cssClass="forminput" /></td>
				</tr>
		  	</c:otherwise>
		  	</c:choose>
			<c:choose>
			<c:when test="${accountStatus==verifiedStatus}">
				<tr class="formtr">
				<td class="formtd"><form:label path="cardType" cssClass="formlebel">
						<spring:message code="card.type.lbl" />
					</form:label></td>
				<td class="formtd">${cardType}</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="cardType" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd"><form:label path="cardType" cssClass="formlebel">
							<spring:message code="card.type.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label></td>
					<td class="formtd"><form:select path="cardType" cssClass="formlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${cardTypes}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}"
									label="${entry.value}" />
							</c:forEach>
						</form:select></td>
				</tr>
			</c:otherwise>
			</c:choose>
			<c:choose>
			<c:when test="${accountStatus==verifiedStatus}">
				<tr class="formtr">
					<td class="formtd">
						<form:label path="cardNumber" cssClass="formlebel">
						<spring:message code="card.number.lbl" />
						</form:label>
					</td>
					<td class="formtd">${addCardAccountForm.cardNumber}</td>
				</tr>
				</c:when>
				<c:otherwise>
				<tr>
				<td></td>
				<td class="formerror"><form:errors path="cardNumber" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="cardNumber" cssClass="formlebel">
							<spring:message code="card.number.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd"><form:input path="cardNumber" cssClass="forminput" /></td>
				</tr>
			</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${accountStatus==verifiedStatus}">
				<%-- <tr class="formtr">
					<td class="formtd"><label id="issueDateMonth" class="formlebel">Issue Date</label></td>
					<td class="formtd">
					<c:if test="${addCardAccountForm.issueDateMonth != 0}">
						${addCardAccountForm.issueDateMonth}/${addCardAccountForm.issueDateYear}
					</c:if>
					</td>
				</tr> --%>
			</c:when>
			<c:otherwise>
				<%-- <tr>
					<td></td>
					<td class="formerror"><form:errors path="issueDateMonth" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<label id="issueDateMonth" class="formlebel"><spring:message code="issue.date.lbl" /></label>
					</td>
					<td class="formtd">
						<form:select path="issueDateMonth" cssClass="smallformlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${monthsList}" var="entry">
								<form:option value="${entry.key}" label="${entry.value}" />
							</c:forEach>
						</form:select>
						<form:select path="issueDateYear" cssClass="smallformlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${issueDateYears}" var="entry">
								<form:option value="${entry.key}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr> --%>
			</c:otherwise>
			</c:choose>
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="expireDateMonth" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<label id="expireDateMonth" class="formlebel"><spring:message code="expire.date.lbl" /><span class="mfield">&nbsp;*</span></label>
					</td>
					<td class="formtd">
						<form:select path="expireDateMonth" cssClass="smallformlist">
							<form:option value="0">
								<spring:message code="month.lbl" />
							</form:option>
							<c:forEach items="${monthsList}" var="entry">
								<form:option value="${entry.key}" label="${entry.value}" />
							</c:forEach>
						</form:select>
						<form:select path="expireDateYear" cssClass="smallformlist">
							<form:option value="0">
								<spring:message code="year.lbl" />
							</form:option>
							<c:forEach items="${expiryDateYears}" var="entry">
								<form:option value="${entry.key}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				<tr>
					<td></td>
					<td class="formerror" style="width: 60%"><form:errors path="cvv" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td class="formtd">
						<form:label path="cvv" cssClass="formlebel">
							<spring:message code="card.verification.number.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td class="formtd">
						<form:password path="cvv" cssClass="forminputcvv" autocomplete="off" />
						<jsp:include page="/pages/cvvpopup.jsp" />
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr class="formtr">
					<td class="formlebel" colspan="2">
							<spring:message code="billing.address.lbl" /><span class="mfield">&nbsp;*</span>
					</td>
				</tr>
				<c:if test="${accountStatus != verifiedStatus}">
					<tr class="formtr">
						<td class="formtd" colspan="2">
							<form:radiobutton path="isSameAsProfileAddress" value="true" onclick="hideAddress()"/>
							<form:label path="isSameAsProfileAddress" cssClass="formlebel"><spring:message code="old.address.for.edit.lbl" /></form:label>
						</td>
					</tr>
					<tr class="formtr">
						<td class="formtd"></td>
						<td class="formtd"><form:textarea path="profileAddress" cssClass="forminput" readonly="true"/></td>
					</tr>
					<tr class="formtr">
						<td class="formtd" colspan="2">
							<form:radiobutton path="isSameAsProfileAddress" value="false" onclick="hideAddress()"/>
							<form:label path="isSameAsProfileAddress" cssClass="formlebel"><spring:message code="update.address.lbl" /></form:label>
						</td>
					</tr>
				</c:if>
				<tr class="formtr">
					<td class="formtd" colspan="2"></td>
				</tr>
				<tr class="formtr">
					<td class="formtd" colspan="2">
						<jsp:include page="billingaddress.jsp"></jsp:include>
					</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:hidden path="mode" />
						<form:hidden path="country" />
						<c:if test="${accountStatus == verifiedStatus}">
							<form:hidden path="isSameAsProfileAddress" />
							<form:hidden path="cardType" />
						</c:if>
					</td>
					</tr>
					<tr>
					<td colspan=2 align="center">
				      <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="forContinue('edit');" /> 
						<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()" />
					  </div>
					</td>
				</tr>
			</table>
</form:form>