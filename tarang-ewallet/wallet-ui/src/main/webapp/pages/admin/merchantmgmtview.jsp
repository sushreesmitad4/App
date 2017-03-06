<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>

<script>

	function cancel(){
		document.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantmgmt';
	}

</script>
<form:form method="POST" commandName="merchantMgmtView" action="view">
	
		<div class="pageheading"><spring:message code="business.information.lbl" /></div>
		<table class="form" style="width:55%">
			<tr class="formtr">
				<td style="width:40%">
					<form:label path="ownerType" cssClass="formlebel">
						<spring:message code="ownertype.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.ownerType}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="businessLegalname" cssClass="formlebel">
						<spring:message code="legalname.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.businessLegalname}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="countryBI" cssClass="formlebel">
						<spring:message code="country.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.countryBI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="address1BI" cssClass="formlebel">
						<spring:message code="address1.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.address1BI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="address2BI" cssClass="formlebel">
						<spring:message code="address2.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.address2BI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="cityOrTownBI" cssClass="formlebel">
						<spring:message code="city.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.cityOrTownBI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="stateorRegionBI" cssClass="formlebel">
						<spring:message code="stateorregion.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.stateorRegionBI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="postalCodeBI" cssClass="formlebel">
						<spring:message code="zipcode.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.postalCodeBI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="phoneNumber" cssClass="formlebel">
						<spring:message code="phone.number.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.phoneCountryCode1}" />-<c:out value="${merchantMgmtView.phoneNumber}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="businessCategory" cssClass="formlebel">
						<spring:message code="category.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.businessCategory}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="subCategory" cssClass="formlebel">
						<spring:message code="subcategory.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.subCategory}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="businessEstablishedMonth" cssClass="formlebel">
						<spring:message code="establishedmonth.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.businessEstablishedMonth}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="businessEstablishedYear" cssClass="formlebel">
						<spring:message code="establishedyear.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.businessEstablishedYear}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="website" cssClass="formlebel">
						<spring:message code="website.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.website}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="currency" cssClass="formlebel">
						<spring:message code="currency.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.currency}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="averageTransactionAmount" cssClass="formlebel">
						<spring:message code="avgtransamount.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.averageTransactionAmount}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="highestMonthlyVolume" cssClass="formlebel">
						<spring:message code="higmonthvolume.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.highestMonthlyVolume}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="percentageOfAnnualRevenueFromOnlineSales" cssClass="formlebel">
						<spring:message code="percentage.of.annual.revenue.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.percentageOfAnnualRevenueFromOnlineSales}" /></td>
			</tr>
		
			<tr class="formtr">
				<td>
					<form:label path="codeCheck" cssClass="formlebel">
						<spring:message code="is.online.payment.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.codeCheck}" /></td>
			</tr>
			
			<c:if test="${merchantMgmtView.codeCheck eq true}">
			
			<tr class="formtr">
				<td>
					<form:label path="merchantCode" cssClass="formlebel">
						<spring:message code="payment.code.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.merchantCode}" /></td>
			</tr>
			
			<tr class="formtr">
				<td>
					<form:label path="successUrl" cssClass="formlebel">
						<spring:message code="payment.successurl.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.successUrl}" /></td>
			</tr>
			
			<tr class="formtr">
				<td>
					<form:label path="failureUrl" cssClass="formlebel">
						<spring:message code="payment.failureurl.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.failureUrl}" /></td>
			</tr>
			</c:if>
			
		</table>
	
	
		<div class="pageheading"><spring:message code="business.owner.information.lbl" /></div>
		<table class="form" style="width:55%">
			<tr class="formtr">
				<td style="width:40%">
					<form:label path="firstName" cssClass="formlebel">
						<spring:message code="firstname.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.firstName}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="lastName" cssClass="formlebel">
						<spring:message code="lastname.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.lastName}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="homeAddress" cssClass="formlebel">
						<spring:message code="home.address.sameas.business.address.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.homeAddress}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="countryBO" cssClass="formlebel">
						<spring:message code="country.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.countryBO}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="address1BO" cssClass="formlebel">
						<spring:message code="address1.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.address1BO}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="address2BO" cssClass="formlebel">
						<spring:message code="address2.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.address2BO}" /></td>
			</tr>
			
			<tr class="formtr">
				<td>
					<form:label path="cityOrTownBO" cssClass="formlebel">
						<spring:message code="city.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.cityOrTownBO}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="stateOrRegionBO" cssClass="formlebel">
						<spring:message code="stateorregion.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.stateOrRegionBO}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="postalCodeBO" cssClass="formlebel">
						<spring:message code="zipcode.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.postalCodeBO}" /></td>
			</tr>
		</table>
	
	
		<div class="pageheading"><spring:message code="customer.service.information.lbl" /></div>
		<table class="form" style="width:55%">
			<tr class="formtr">
				<td style="width:40%">
					<form:label path="emailCSI" cssClass="formlebel">
						<spring:message code="emailid.lbl"/>
					</form:label></td>
				<td><c:out value="${merchantMgmtView.emailCSI}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="phone" cssClass="formlebel">
						<spring:message code="phone.number.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.code}" />-<c:out value="${merchantMgmtView.phone}" /></td>
			</tr>
		</table>
	
	
		<div class="pageheading"><spring:message code="authentication.lbl" /></div>
		<table class="form" style="width:55%">
			<tr class="formtr">
				<td style="width:40%">
					<form:label path="emailId" cssClass="formlebel">
						<spring:message code="emailid.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.emailId}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="creationDate" cssClass="formlebel">
						<spring:message code="creationdate.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.creationDate}" /></td>
			</tr>

			<tr class="formtr">
				<td>
					<form:label path="active" cssClass="formlebel">
						<spring:message code="active.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.active}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="deleted" cssClass="formlebel">
						<spring:message code="deleted.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.deleted}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="blocked" cssClass="formlebel">
						<spring:message code="blocked.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantMgmtView.blocked}" /></td>
			</tr>
			<tr class="formtr">
				<td>
					<form:label path="status" cssClass="formlebel">
						<spring:message code="status.lbl"/>
					</form:label>
				</td>
					<td><c:out value="${merchantMgmtView.status}" /></td>
			</tr>
			<tr class="formtr">
				<td><form:hidden path="id" /> <form:hidden path="emailId" /></td>
				<td><form:hidden path="mode" /></td>
			</tr>
 			<tr class="formtr">
 				<td colspan=2 align="center">
			  		<div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="back.lbl" />' onclick="cancel()" />
					</div>
				</td>
			</tr>
		</table>
	
</form:form>

