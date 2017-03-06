 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script>

	function update(){
		document.getElementById("mode").value = "update";
		submitFormData(document.merchantupdateform);
	}
	
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
					 	document.location='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchant';
			    	};
    	confirmationDialog(yesAction, null, null, message);
	} 
	
</script>
<div class="pageheading"><spring:message code="update.profile.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="merchantupdateform" method="POST" commandName="merchantForm" action="updateprofile">
	<h3><span><spring:message code="authentication.lbl" /></span></h3>
	<div class="pagelayout">
		<table class="form" style="width: 50%">
			<tr class="formtr">
				<td class="formtd" style="width:35%"><form:label path="emailId" cssClass="formlebel">
						<spring:message code="emailid.lbl"/>
					</form:label>
				</td>
				<td class="formtd"><c:out value="${merchantForm.emailId}" /></td>
			</tr>
	        <jsp:include page="/pages/merchant/merchentcommonregistration.jsp"></jsp:include>
	    </table>
	</div>
	<jsp:include page="/pages/admin/editmerchant.jsp"></jsp:include>
	<table>
		<tr class="formtr">
			<td class="formtd">
				<form:hidden path="id" /> 
				<form:hidden path="emailId" /> 
				<form:hidden path="creationDateAsMMDDYYY" /> 
				<form:hidden path="status2" />
				<form:hidden path="existPersonPhoneNo" /> 
				<form:hidden path="existServicePhoneNo" /> 
				<input type="hidden" name="adminOrMerchant" value="merchant">
			</td>
			<td class="formtd"><form:hidden path="mode" /></td>
		</tr>
	</table>
	<center>
	<table>
		<tr>
			<td  colspan=2 align="center">
				<div class="formbtns">
      				<input type="button" class="styled-button" value='<spring:message code="update.lbl" />' onclick="update()" />
					<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="forCancel()" />
				</div>	
			</td>
		</tr>       
	</table>
	</center>
</form:form>