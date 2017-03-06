<%@page import="com.tarang.ewallet.util.GlobalLitterals"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.masterdata.util.CountryIds,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">

	function cancleBtn() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/manageaccounts';
		}
		confirmationDialog(yesAction, null, null, message);
	}

	function addBank(mode) {
		document.getElementById("mode").value = mode;
		submitFormData(document.addbank);
	}

</script>

<div class="pageheading"><spring:message code="addbank.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="addbank" method="POST" commandName="addBankAccountForm" action="addbank">
			<table class="form" style="width: 50%">
				<tr>
					<td></td>
					<td class="formerror"><form:errors path="country" cssClass="error" /></td>
				</tr>
				<tr class="formtr">
					<td>
						<form:label path="country" cssClass="formlebel">
							<spring:message code="country.lbl"/>
							<span class="mfield">&nbsp;*</span>
						</form:label>
					</td>
					<td>
						<form:select path="country" onchange="addBank('countryChanged')" cssClass="formlist">
							<form:option value="0">
								<spring:message code="please.select.lbl" />
							</form:option>
							<c:forEach items="${countryList}" var="entry">
								<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
							</c:forEach>
						</form:select>
					</td>
				</tr>
				<%	String cid = (String) request.getParameter("country");
					if(cid != null && !cid.equals("0") && request.getSession().getAttribute("userType").equals(GlobalLitterals.CUSTOMER_USER_TYPE)){ %>
				<tr class="formtr">
					<td>
						<form:label path="jointAccount" cssClass="formlebel"><spring:message code="joint.account.lbl" /></form:label>
					</td>
					<td><form:checkbox path="jointAccount" /></td>
				</tr>			
				<% } if (cid == null) {
				%>
				<jsp:include page="/pages/banks/default.jsp"></jsp:include>
				<%
					}
					else if (request.getParameter("country").equals(CountryIds.USD_COUNTRY_ID.toString())) {
				%>
				<jsp:include page="/pages/banks/us.jsp"></jsp:include>
				<%
					}
					else if (request.getParameter("country").equals(CountryIds.JPY_COUNTRY_ID.toString())) {
				%>
				<jsp:include page="/pages/banks/japan.jsp"></jsp:include>
				<%
					}
					else if (request.getParameter("country").equals(CountryIds.THAI_COUNTRY_ID.toString())) {
				%>
				<jsp:include page="/pages/banks/thailand.jsp"></jsp:include>
				<%
					}
					else {
				%>
				<jsp:include page="/pages/banks/default.jsp"></jsp:include>
				<%
					}
			       if( request.getParameter("country") != null &&
			    		   ( request.getParameter("country").equals(CountryIds.USD_COUNTRY_ID.toString()) 
						    		  || request.getParameter("country").equals(CountryIds.JPY_COUNTRY_ID.toString())
						    		  || request.getParameter("country").equals(CountryIds.THAI_COUNTRY_ID.toString()) ) ) {
			    %>
			    <tr>
				<td colspan=2 align="center">
				  <div class="formbtns">
						<input type="button" class="styled-button" value='<spring:message code="add.lbl" />' onclick="addBank('add')" />
						<input type="button" class="styled-button" value='<spring:message code="cancel.lbl" />' onclick="cancleBtn()" />
					</div>
				</td>
				</tr>
				<% } %>
			</table>
			<form:hidden path="mode" />
</form:form>