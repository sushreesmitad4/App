<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<script type="text/javascript">
	
	function cancelAction() {
		var servicetype = document.getElementById("services").value;
		window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?' + 'servicetype' + '=' + servicetype;
	}
	
</script>

<div class="pageheading"><spring:message code="fee.view.lbl" /></div>
<form:form method="POST" commandName="feeMgmtForm" name="viewfee">
	
		
			<form:hidden path="services"/>
			<table class="form" style="width: 40%">
				<tr class="formtr">
					<td><form:label path="userType" cssClass="formlebel"><spring:message code="user.type.lbl" /></form:label></td>
					<td>${feeMgmtForm.userTypeName}</td>
				</tr>
				<tr class="formtr">
					<td><form:label path="services" cssClass="formlebel"><spring:message code="services.lbl" /></form:label></td>
					<td>${feeMgmtForm.serviceName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="operationType" cssClass="formlebel"><spring:message code="operation.type.lbl" /></form:label></td>
					<td>${feeMgmtForm.operationTypeName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="country" cssClass="formlebel"><spring:message code="country.lbl"/></form:label></td>
					<td>${feeMgmtForm.countryName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="currency" cssClass="formlebel"><spring:message code="currency.lbl" /></form:label></td>
					<td>${feeMgmtForm.currencyName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="payingentity" cssClass="formlebel"><spring:message code="paying.entity.lbl" /></form:label></td>
					<td>${feeMgmtForm.payingentityName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="feeType" cssClass="formlebel"><spring:message code="fee.type.lbl" /></form:label></td>
					<td>${feeMgmtForm.feeTypeName}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="fixCharRec" cssClass="formlebel"><spring:message code="flat.fee.default.lbl" /></form:label></td>
					<td>${feeMgmtForm.fixCharRec}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="percentageRec" cssClass="formlebel"><spring:message code="percentage.default.lbl" /></form:label></td>
					<td>${feeMgmtForm.percentageRec}</td>
				</tr>
				
				<tr class="formtr">
					<td><form:label path="timeFreequency" cssClass="formlebel"><spring:message code="time.freequency.lbl" /></form:label></td>
					<td>${feeMgmtForm.timeFreequencyName}</td>
				</tr>
				
				<tr>
					<td colspan="50">
						<table>
						<c:if test="${feeMgmtForm.upperLimSlb1 ne null}">
							<tr>
								<form:label path="lowerLimSlb1" cssClass="formlebel">
									<spring:message code="slabs.lbl" />
								</form:label>
							</tr>
							<tr>
								<td>
									<form:label path="lowerLimSlb1" >
										<spring:message code="lower.limit.lbl" />	
									</form:label>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>		
								<td>
									<form:label path="upperLimSlb1" >
										<spring:message code="upper.limit.lbl" />	
									</form:label>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<form:label path="fixCharRecSlb1" >
										<spring:message code="flat.fee.lbl" />	
									</form:label>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>	
								<td>
									<form:label path="percentageRecSlb1" >
										<spring:message code="percentage.lbl" />	
									</form:label>&nbsp;&nbsp;&nbsp;&nbsp;
								</td>		
							</tr>
							<tr>
								<td></td><td></td>
							</tr>
							<tr>
								<td>${feeMgmtForm.lowerLimSlb1}</td>
								<td>${feeMgmtForm.upperLimSlb1}</td>
								<td>${feeMgmtForm.fixCharRecSlb1}</td>
								<td>${feeMgmtForm.percentageRecSlb1}</td>
							</tr>	
							<tr>
								<td></td><td></td>
							</tr>
							<tr>
								<td>${feeMgmtForm.lowerLimSlb2}</td>
								<td>${feeMgmtForm.upperLimSlb2}</td>
								<td>${feeMgmtForm.fixCharRecSlb2}</td>
								<td>${feeMgmtForm.percentageRecSlb2}</td>
							</tr>
							<tr>
								<td></td><td></td>
							</tr>
							<tr>
								<td>${feeMgmtForm.lowerLimSlb3}</td>
								<td>${feeMgmtForm.upperLimSlb3}</td>
								<td>${feeMgmtForm.fixCharRecSlb3}</td>
								<td>${feeMgmtForm.percentageRecSlb3}</td>
							</tr>
						</c:if>
						</table>
					</td>
				</tr>
				<tr class="formtr">
					<td colspan=2 align="center">
						<div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="back.lbl"/>' onclick="cancelAction()" />
						</div>
					</td>
					
				</tr>
			</table>
		
		<div class="clear"></div>
	
</form:form>