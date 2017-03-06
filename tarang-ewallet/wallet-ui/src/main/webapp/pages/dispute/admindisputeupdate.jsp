<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.tarang.ewallet.util.GlobalLitterals,com.tarang.ewallet.walletui.controller.AttributeValueConstants,com.tarang.ewallet.dispute.util.DisputeStatusConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>
<%  pageContext.setAttribute("ZERO", GlobalLitterals.ZERO_LONG);
	pageContext.setAttribute("CUSTOMER_TYPE_ID", GlobalLitterals.CUSTOMER_USER_TYPE_ID);
	pageContext.setAttribute("MERCHANT_TYPE_ID", GlobalLitterals.MERCHANT_USER_TYPE_ID);
	pageContext.setAttribute("ADMIN_TYPE_ID", GlobalLitterals.ADMIN_USER_TYPE_ID);
%>
<script type="text/javascript">
 var v;
   $(document).ready(function(){
   		v = document.getElementById("approvedAmount").value;
   });
	function onRadioChange(){
		if( $('input[name="formStatus"]:checked').val()== '<%=DisputeStatusConstants.ADMIN_REJECTED%>') {
			document.getElementById("approvedAmount").value = v;
		}
		
	}
	function forBack(){
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admindispute';
	}
	function continueAction() {
		var message = '<spring:message code="update.record.confirm.msg" />';
		var yesAction = function () {
							document.updatedisp.method = "POST";
							$(this).dialog("close");
							submitFormData(document.updatedisp);
	       				};
		confirmationDialog(yesAction, null, null, message);
	}

</script>

<div class="pageheading"><spring:message code="update.dispute.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="updatedisp" method="POST" commandName="disputeForm" action="updateDispute">			
			<table class="form" style="width: 50%">
				<tr class="formtr">
					<td>
						<form:label path="transactionId" cssClass="formlebel">
							<spring:message code="transaction.id.lbl" />
						</form:label></td>
					<td>${disputeForm.transactionId}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="transactionDate" cssClass="formlebel">
							<spring:message code="transaction.date.lbl" />
						</form:label></td>
					<td>${disputeForm.transactionDate}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="transactionDate" cssClass="formlebel">
							<spring:message code="customer.email.lbl" />
						</form:label></td>
					<td>${disputeForm.payerEmailId}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="transactionDate" cssClass="formlebel">
							<spring:message code="merchant.email.lbl" />
						</form:label></td>
					<td>${disputeForm.payeeEmailId}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="transactionDate" cssClass="formlebel">
							<spring:message code="transaction.amount.lbl" />
						</form:label></td>
					<td>${disputeForm.transactionAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.transactionCurrency}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="disputeLogDate" cssClass="formlebel">
							<spring:message code="dispute.log.date.lbl" />
						</form:label></td>
					<td>${disputeForm.disputeLogDate}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="disputeUpdationDate" cssClass="formlebel">
							<spring:message code="dispute.updation.date" />
						</form:label></td>
					<td>${disputeForm.disputeUpdationDate}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="disputeType" cssClass="formlebel">
							<spring:message code="dispute.type.lbl" />
						</form:label></td>
					<td>${disputeForm.disputeType}</td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="requestAmount" cssClass="formlebel">
							<spring:message code="requested.amount.lbl" />
						</form:label></td>
					<td>${disputeForm.requestAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.requestCurrency}</td>
				</tr>
				<c:choose>
					<c:when test="${adminApprovedAmountRequired == true}">
						<tr>
							<td></td>
							<td><form:errors path="approvedAmount" cssClass="error" /></td>
						</tr>	
						<tr class="formtr">
							<td>
							<form:label path="approvedAmount" cssClass="formlebel">
								<spring:message code="approved.amount.lbl" /><span class="mfield">&nbsp;*</span>
							</form:label></td>
							<td>
								<form:input path="approvedAmount" cssClass="forminputcvv" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.approvedCurrency}
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr class="formtr">
							<td>
							<form:label path="approvedAmount" cssClass="formlebel">
								<spring:message code="approved.amount.lbl" />
							</form:label></td>
							<td>${disputeForm.approvedAmount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${disputeForm.approvedCurrency}</td>
						</tr>
					</c:otherwise>
				</c:choose>
			 	<c:choose>
					<c:when test="${adminStatusRequired == true}">
						<tr class="formtr">
							<td>
								<form:label path="formStatus" cssClass="formlebel">
									<spring:message code="status.lbl" /><span class="mfield">&nbsp;*</span>
								</form:label>
							</td>
							<td><form:errors path="formStatus" cssClass="error" /></td>
						</tr>
						<c:if test="${disputeStatusMap ne null }">
							<c:forEach items="${disputeStatusMap}" var="entry">
							<tr class="formtr">
								<td></td>
		        				<td>
		        					<form:radiobutton path="formStatus" value="${entry.key}" title="${entry.value}" 
		        						label="${entry.value}" onclick="onRadioChange()"/>
		        				</td>
		        			</tr>
		    				</c:forEach> 
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="formtr">
							<td>
								<form:label path="formStatus" cssClass="formlebel">
									<spring:message code="status.lbl" />
								</form:label>
							</td>
							<td>${disputeForm.disputeStatus}</td>
						</tr>
					</c:otherwise>
				</c:choose> 
				<tr>
					<td></td>
					<td><form:errors path="message" cssClass="error" /></td>
				</tr>				
				<tr class="formtr">
					<td>
						<form:label path="message" cssClass="formlebel">
							<spring:message code="message.lbl" /><span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td><form:textarea path="message" cssClass="forminput" /></td>
				</tr>
				<tr>
					<td>
						<form:hidden path="disputeType"/>
					</td>
					<td>
						<form:hidden path="transactionId"/>
						<form:hidden path="payerEmailId"/>
						<form:hidden path="payeeEmailId"/>
						<form:hidden path="payerAmount"/>
						<form:hidden path="payerCurrency"/>
						<form:hidden path="transactionDate"/>
						<form:hidden path="disputeLogDate"/>
						<form:hidden path="disputeUpdationDate"/>
						<form:hidden path="requestCurrency"/>
						<form:hidden path="requestAmount"/>
						<form:hidden path="approvedCurrency"/>
						<form:hidden path="disputeId"/>
						<form:hidden path="distype"/>
						<form:hidden path="status"/>
						<c:if test="${adminApprovedAmountRequired == false}" >
							<form:hidden path="approvedAmount"/>
						</c:if>
						<c:if test="${adminStatusRequired == false}" >
							<form:hidden path="disputeStatus"/>
						</c:if>
					</td>
				</tr>			
				
				<tr class="formtr">
					<td colspan=2 align="center">
						<div class="formbtns">
						<% 
							Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
								MenuConstants.DISPUTE, MenuConstants.MANAGE_PERMISSION);
					    	if(adminAccessCheck){
                         %>
							<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="continueAction();" />
						 <% } %>
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
											<spring:message code="customer.lbl" />
										</c:if>
										<c:if test="${messges.creator eq MERCHANT_TYPE_ID}">
											<spring:message code="merchant.lbl" />
										</c:if>
										<c:if test="${messges.creator eq ADMIN_TYPE_ID}">
											<spring:message code="self.lbl" />
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