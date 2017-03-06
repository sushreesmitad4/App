<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: Oct 19, 2012
  - @(#)
  - Description: This page is for Login module. This includes User Login module
 --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(window).load(function () { 
		document.forms[0].email.focus();
	});
	
	function forSubmit() {
		submitFormData(document.paymentform);
	} 
	
	function cancelAction(){
		document.getElementById("backtomerchant").value = "true";
		document.paymentform.submit(); 
	}
</script>
<form:form name="paymentform" commandName="loginUserForm" autocomplete="off" method="POST" action="payment">
	<div class="pageheading"><spring:message code="login.lbl" /></div>
	<form:hidden path="merchantName"/>
	<form:hidden path="amount"/>
	<form:hidden path="currency"/>
	<form:hidden path="pathImage"/>
	<table class="form" style="width: 60%">
		<tr>
			<td colspan="2"><input type="hidden" id="backtomerchant" name="backtomerchant" value=""/></td>
		</tr>
	<c:if test="${errorMessage ne null}">
		<tr>
			<td colspan="2" align="center" class="error" id="errorMsg">${errorMessage}</td>
		</tr>
	</c:if>
		<tr>
			<td width="30%" align="right">
				<form:label path="email" cssClass="formlebel"><spring:message code="merchant.name.lbl"/> &nbsp; &nbsp; </form:label>
			</td>
			<td width="70%" align="left">
				${loginUserForm.merchantName}
			</td>
			<td rowspan="5">
        	     <img src="<%=request.getContextPath()%>/imgservlet/<%=(String)request.getAttribute("imageId")%>" height="100" width="100" />
           	</td>
		</tr>
		<tr>
			<td width="30%" align="right">
				<form:label path="email" cssClass="formlebel"><spring:message code="amount.lbl"/> &nbsp; &nbsp; </form:label>
			</td>
			<td width="70%" align="left">
				${loginUserForm.amount} &nbsp; &nbsp; ${loginUserForm.currency}
			</td>
			
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
  		<tr>
			<td width="30%" align="right">
				<form:label path="expCheckOut" cssClass="formlebel"><spring:message code="exp.check.out.lbl"/> &nbsp; &nbsp; </form:label>
			</td>
			<td width="70%" align="left">
				<form:checkbox path="expCheckOut" cssClass="formlebel"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
     		<td></td>
  			<td class="formerror"><form:errors path="email" cssClass="error"/></td>
  		</tr>
  		<tr>
			<td width="30%" align="right">
				<form:label path="email" cssClass="formlebel"><spring:message code="emailid.lbl"/> &nbsp; &nbsp; </form:label>
			</td>
			<td width="70%" align="left">
				<form:input id="emailId" path="email" autocomplete="off" cssClass="forminput"/>
			</td>
		</tr>
		<tr>
     		<td></td>
  			<td class="formerror"><form:errors path="password" cssClass="error"/>
  		</tr>
 		<tr>
			<td align="right">
				<form:label path="password" cssClass="formlebel"><spring:message code="password.lbl" /></form:label>&nbsp;&nbsp;
			</td>
			<td align="left">
				<form:password id="passwordId" path="password" autocomplete="off" cssClass="forminput"/>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
	           <div class="formbtns">
				 <input id="login-button" type="submit" class="styled-button" value='<spring:message code="login.lbl"/>' onclick="forSubmit()" />
				 <input id="login-button" type="button" class="styled-button" value='<spring:message code="cancel.lbl"/>' onclick="cancelAction()" />
			   </div>
			</td>
		</tr>
	</table>
</form:form>