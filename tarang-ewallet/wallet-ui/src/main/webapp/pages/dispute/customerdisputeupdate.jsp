<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%  pageContext.setAttribute("ZERO", GlobalLitterals.ZERO_LONG);
	pageContext.setAttribute("CUSTOMER_TYPE_ID", GlobalLitterals.CUSTOMER_USER_TYPE_ID);
	pageContext.setAttribute("MERCHANT_TYPE_ID", GlobalLitterals.MERCHANT_USER_TYPE_ID);
	pageContext.setAttribute("ADMIN_TYPE_ID", GlobalLitterals.ADMIN_USER_TYPE_ID);
%>
<script type="text/javascript">
	function continueAction() {
		submitFormData(document.updatedisp);
	}
	function forBack(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerdispute';
	}
</script>

<c:choose>
		<c:when test="${disputeForm.distype == 1}">
			<div class="pageheading"><spring:message code="update.refund.lbl" /></div>
		</c:when>
		<c:otherwise>
			<div class="pageheading"><spring:message code="update.chargeback.lbl" /></div>
		</c:otherwise>
</c:choose>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="updatedisp" method="POST" commandName="disputeForm" action="updatedispute">
		<table class="form" style="width: 50%">
			<tr class="formtr" >
				<td class="formtd">
					<form:label path="transactionId" cssClass="formlebel">
						<spring:message code="transaction.id.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionId}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="transactionDate" cssClass="formlebel">
						<spring:message code="transaction.date.lbl" />
					</form:label>
				</td>
				<td class="formtd">${disputeForm.transactionDate}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="transactionAmount" cssClass="formlebel">
						<spring:message code="transaction.amount.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.transactionAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.transactionCurrency}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="disputeLogDate" cssClass="formlebel">
						<spring:message code="dispute.log.date.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.disputeLogDate}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="disputeUpdationDate" cssClass="formlebel">
						<spring:message code="dispute.updation.date" />
					</form:label></td>
				<td class="formtd">${disputeForm.disputeUpdationDate}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="disputeType" cssClass="formlebel">
						<spring:message code="dispute.type.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.disputeType}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="requestAmount" cssClass="formlebel">
						<spring:message code="requested.amount.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.requestAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.requestCurrency}</td>
			</tr>
			<tr class="formtr">
				<td class="formtd">
					<form:label path="approvedAmount" cssClass="formlebel">
						<spring:message code="approved.amount.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.approvedAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.approvedCurrency}</td>
			</tr>	
			<tr class="formtr">
				<td class="formtd">
					<form:label path="disputeStatus" cssClass="formlebel">
						<spring:message code="status.lbl" />
					</form:label></td>
				<td class="formtd">${disputeForm.disputeStatus}</td>
			</tr>
			<tr>
				<td></td>
				<td class="formerror"><form:errors path="message" cssClass="error" /></td>
			</tr>				
			<tr class="formtr">
				<td class="formtd">
					<form:label path="message" cssClass="formlebel">
						<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
					</form:label>
				</td>
				<td class="formtd"><form:textarea path="message" cssClass="forminput" /></td>
			</tr>
			<tr>
				<td colspan="2" >
					<form:hidden path="disputeType"/>	
					<form:hidden path="disputeId"/>
					<form:hidden path="status"/>
					<form:hidden path="approvedAmount"/>	
					<form:hidden path="transactionId"/>
					<form:hidden path="transactionDate"/>
					<form:hidden path="transactionAmount"/>
					<form:hidden path="transactionCurrency"/>
					<form:hidden path="disputeLogDate"/>
					<form:hidden path="disputeUpdationDate"/>
					<form:hidden path="requestCurrency"/>
					<form:hidden path="requestAmount"/>
					<form:hidden path="approvedCurrency"/>
					<form:hidden path="disputeStatus"/>
					<form:hidden path="distype"/>
					<form:hidden path="payeeEmailId"/>
					<form:hidden path="payerEmailId"/>
				</td>
			</tr>
			<tr>
				<td colspan=2 align="center">
				  <div class="formbtns">
					<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="continueAction()" />
					<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="forBack()" />
				  </div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<h2 class="tablesubheading"><spring:message code="message.lbl" /></h2>
						<div style="height:180px; overflow:auto;">
						<table  style="width: 410px" >
							<tr>
								<th style="width: 100px; text-align: left;">
									<spring:message code="date.lbl" />
								</th>
								<th style="width: 100px; text-align: left;">
									<spring:message code="from.lbl" />
								</th>
								<th style="width: 200px; text-align: left;">
									<spring:message code="message.lbl" />
								</th>
							 </tr>
							<c:if test="${fn:length(disputeForm.dtoMessages) > ZERO}">
								<c:forEach items="${disputeForm.dtoMessages}" var="messges">
								 <tr>
									<td><c:out value="${messges.creationDate}" /></td>
									<td><c:if test="${messges.creator eq CUSTOMER_TYPE_ID}">
											<spring:message code="self.lbl" />
										</c:if>
										<c:if test="${messges.creator eq MERCHANT_TYPE_ID}">
											<spring:message code="merchant.lbl" />
										</c:if>
										<c:if test="${messges.creator eq ADMIN_TYPE_ID}">
											<spring:message code="admin.lbl" />
										</c:if>
									</td>
									<td>
									<div style="width:200px;" class="linebreak" ><c:out value="${messges.message}" /></div>
									</td>
								</tr>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</td>
			</tr>
		</table>
</form:form>