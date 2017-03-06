<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>

	function update(){
		document.getElementById("mode").value = "edit";
		continueAction();
	}
	
	function resetPasword(){
		document.getElementById("mode").value = "resetpassword";
		continueAction();
	}
	
	function cancel(){
		document.getElementById("mode").value = "cancel";
	}
	
	function continueAction(){
		submitFormData(document.merchantform);
		
	}
</script>
<div class="pageheading"><spring:message code="business.information.lbl" /></div>
<form:form method="POST" commandName="merchantFormView" action="edit" name="merchantform">
		<table class="form" style="width:50%">
			<tr>
				<td>
				<form:label path="ownerType" cssClass="formlebel">
						<spring:message code="ownertype.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.ownerType}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="businessLegalname" cssClass="formlebel">
						<spring:message code="legalname.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.businessLegalname}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="address1BI" cssClass="formlebel">
						<spring:message code="address1.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.address1BI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="address2BI" cssClass="formlebel">
						<spring:message code="address2.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.address2BI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="countryBI" cssClass="formlebel">
						<spring:message code="country.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.countryBI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="stateorRegionBI" cssClass="formlebel">
						<spring:message code="stateorregion.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.stateorRegionBI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="cityOrTownBI" cssClass="formlebel">
						<spring:message code="city.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.cityOrTownBI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="postalCodeBI" cssClass="formlebel">
						<spring:message code="zipcode.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.postalCodeBI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="phoneCountryCode1" cssClass="formlebel">
						<spring:message code="code.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.phoneCountryCode1}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="phoneNumber" cssClass="formlebel">
						<spring:message code="phone.number.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.phoneNumber}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="businessCategory" cssClass="formlebel">
						<spring:message code="category.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.businessCategory}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="subCategory" cssClass="formlebel">
						<spring:message code="subcategory.lbl"/>
					</form:label></td>
				<td><c:out value="${merchantFormView.subCategory}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="businessEstablishedMonth" cssClass="formlebel">
						<spring:message code="establishedmonth.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.businessEstablishedMonth}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="businessEstablishedYear" cssClass="formlebel">
						<spring:message code="establishedyear.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.businessEstablishedYear}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="website" cssClass="formlebel">
						<spring:message code="website.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.website}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="currency" cssClass="formlebel">
						<spring:message code="avgtransamount.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.currency}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="averageTransactionAmount" cssClass="formlebel">
						<spring:message code="avgtransamount.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.averageTransactionAmount}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="highestMonthlyVolume" cssClass="formlebel">
						<spring:message code="higmonthvolume.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.highestMonthlyVolume}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="percentageOfAnnualRevenueFromOnlineSales" cssClass="formlebel">
						<spring:message
							code="percentage.of.annual.revenue.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.percentageOfAnnualRevenueFromOnlineSales}" /></td>
			</tr>
		</table>
	
		<div class="pageheading"><spring:message code="business.owner.information.lbl" /></div>
		<table class="form" style="width: 50%">
			<tr>
				<td>
					<form:label path="firstName" cssClass="formlebel">
						<spring:message code="firstname.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.firstName}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="lastName" cssClass="formlebel">
						<spring:message code="lastname.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.lastName}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="homeAddress" cssClass="formlebel">
						<spring:message code="home.address.sameas.business.address.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.homeAddress}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="address1BO" cssClass="formlebel">
						<spring:message code="address1.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.address1BO}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="address2BO" cssClass="formlebel">
						<spring:message code="address2.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.address2BO}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="countryBO" cssClass="formlebel">
						<spring:message code="country.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.countryBO}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="stateOrRegionBO" cssClass="formlebel">
						<spring:message code="stateorregion.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.stateOrRegionBO}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="cityOrTownBO" cssClass="formlebel">
						<spring:message code="city.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.cityOrTownBO}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="postalCodeBO" cssClass="formlebel">
						<spring:message code="zipcode.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.postalCodeBO}" /></td>
			</tr>
		</table>
	
	
		<div class="pageheading"><spring:message code="customer.service.information.lbl" /></div>
		<table class="form" style="width: 50%">
			<tr>
				<td>
					<form:label path="emailCSI" cssClass="formlebel">
						<spring:message code="emailid.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.emailCSI}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="code" cssClass="formlebel">
						<spring:message code="code.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.code}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="phone" cssClass="formlebel">
						<spring:message code="phone.number.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.phone}" /></td>
			</tr>
		</table>
	
	
		<div class="pageheading"><spring:message code="authentication.lbl" /></div>
		<table class="form" style="width: 50%">
			<tr>
				<td>
					<form:label path="emailId" cssClass="formlebel">
						<spring:message code="emailid.lbl"/>
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.emailId}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="creationDate" cssClass="formlebel">
						<spring:message code="creationdate.lbl" />
					</form:label>
				</td>
				<td><c:out value="${merchantFormView.creationDate}" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="active" cssClass="formlebel">
						<spring:message code="active.lbl"/>
					</form:label>
				</td>
				<td><form:checkbox path="active" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="deleted" cssClass="formlebel">
						<spring:message code="deleted.lbl"/>
					</form:label>
				</td>
				<td><form:checkbox path="deleted" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="blocked" cssClass="formlebel">
						<spring:message code="blocked.lbl" />
					</form:label>
				</td>
				<td><form:checkbox path="blocked" /></td>
			</tr>
			<tr>
				<td>
					<form:label path="status" cssClass="formlebel">
						<spring:message code="status.lbl"/>
					</form:label>
				</td>
				<td>
					<c:if test="${statusList ne null }">
						<form:radiobuttons path="status" items="${statusList}" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td><form:hidden path="id" /> <form:hidden path="emailId" /></td>
				<td><form:hidden path="mode" /></td>
			</tr>

  			<tr>
  				<td colspan=2 align="center">
					<div class="formbtns">
				<input type="button" class="formbutton" value='<spring:message code="update.lbl" />' onclick="update()" />
				<input type="button" class="formbutton" value='<spring:message code="resetpassword.lbl" />' onclick="resetPasword()" />
				<input type="button" class="formbutton" value='<spring:message code="cancel.lbl" />' onclick="cancel()" />
					</div>
				</td>
			</tr>
		</table>
	
</form:form>