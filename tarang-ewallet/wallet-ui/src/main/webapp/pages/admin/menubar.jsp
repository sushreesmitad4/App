<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="java.util.Iterator, java.util.List, java.util.Map,com.tarang.ewallet.walletui.util.MenuUtils,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<% List<Long> validMenuList = (List<Long>) session.getAttribute("validMenuList"); %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

 <div id="mbtnavbar">
	<ul id="mbtnav">
	<li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin"><spring:message code="my.account.lbl"/></a></li>
	<% if (MenuUtils.checkParent(MenuConstants.MANAGEMENT_M, validMenuList)) { %>	
	 <li class="top"><a href="#"><spring:message code="management.lbl"/></a>
		<ul class="sub">
			<% if (MenuUtils.checkChild(MenuConstants.ADMIN_USER_MANAGEMENT_MI,validMenuList)) { %>				
				<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminusermgmt"><spring:message code="admin.mgmt.lbl"/></a></li>
		    <% }%>		 		
			<% if (MenuUtils.checkChild(MenuConstants.CUSTOMER_MANAGEMENT_MI,validMenuList)) { %>			
				<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customermgmt"><spring:message code="customer.mgmt.lbl"/></a></li>
			<% }
			if (MenuUtils.checkChild(MenuConstants.MERCHANT_MANAGEMENT_MI,validMenuList)) { %>
				<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/merchantmgmt"><spring:message code="merchant.mgmt.lbl"/></a></li>
			<% } 			
			 if (MenuUtils.checkChild(MenuConstants.ADMIN_ROLE_MANAGEMENT_MI,validMenuList)) { %>			
				<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles"><spring:message code="role.mgmt.lbl"/></a></li>
			<%} 
			if (MenuUtils.checkChild(MenuConstants.TAX_MANAGEMENT_MI,validMenuList)) {%>	
				<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/tax"><spring:message code="tax.mgmt.lbl"/></a></li>	
			<%} 
			if (MenuUtils.checkParent(MenuConstants.FEE_MANAGEMENT_M,validMenuList)) {%>	
				<li><a href="#"><spring:message code="fee.mgmt.lbl"/></a>
				<ul class="sub">
				 <%if (MenuUtils.checkChild(MenuConstants.FEE_MANAGEMENT_F_MI,validMenuList)) { %>			
					<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?servicetype=1"><spring:message code="financial.service.lbl"/></a></li>
				<%} 
				 if (MenuUtils.checkChild(MenuConstants.FEE_MANAGEMENT_NF_MI,validMenuList)) { %>			
					<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?servicetype=2"><spring:message code="non.financial.service.lbl"/></a></li>
				<%} 
				 if (MenuUtils.checkChild(MenuConstants.FEE_MANAGEMENT_NFV_MI,validMenuList)) { %>			
					<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/feemgmt?servicetype=3"><spring:message code="financial.on.velocity.lbl"/></a></li>
				<% } %>				
				</ul>
				</li>
					
			<%} 
		    if (MenuUtils.checkChild(MenuConstants.TRANSACTION_THRESHOLD_MI,validMenuList)) { %>	
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/velocityandthreshold"><spring:message code="transaction.threshold.lbl"/></a></li>
			<%}
		    if (MenuUtils.checkChild(MenuConstants.WALLET_THRESHOLD,validMenuList)) { %>	
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/walletthreshold"><spring:message code="wallet.threshold.lbl"/></a></li>
			<%}
		    if (MenuUtils.checkChild(MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT,validMenuList)) { %>
		    <li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/accountclosermgmt"><spring:message code="account.closer.mgmt.lbl"/></a></li>
		    <%}%>
		</ul>
	</li>
   	<%   
    	} //ParentMenu end 
    	if (MenuUtils.checkParent(MenuConstants.TRANSACTION_MI,validMenuList)) { 
	%>	
	<%-- <li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin#"><spring:message code="transaction.lbl"/></a>
		<ul class="sub">
		<% if (MenuUtils.checkChild(10L,validMenuList)) { %>
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/underconstruction"><spring:message code="revocer.money.lbl"/></a></li>
		 <% }
		if (MenuUtils.checkChild(11L,validMenuList)) { 
		   %>	
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/underconstruction"><spring:message code="refund.lbl"/></a></li>
		 <% } %>
     	</ul>
	</li>   --%>
	<%
	} 
   	if (MenuUtils.checkParent(MenuConstants.DISPUTE,validMenuList)) {	  
	%>	
	<li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admindispute"><spring:message code="dispute.lbl"/></a>
	</li><%
	} 
	//if (MenuUtils.checkParent(MenuConstants.PROFILE_M,validMenuList)) {
	%>
	<li class="top"><a href="#"><spring:message code="profile.lbl"/></a>
		<ul class="sub">
		<%-- <%
         if (MenuUtils.checkChild(MenuConstants.CHANGE_PASSWORD_MI,validMenuList)) { %> --%>
		 	<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminlogin/changepassword"><spring:message code="change.password.lbl"/></a></li>
		 <%-- <%
		 } 
		 if (MenuUtils.checkChild(MenuConstants.PREFERENCES_MI,validMenuList)) { %>	 --%>
			<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminpreferences/adminpreferences"><spring:message code="preferences.lbl"/></a></li>
		<%-- <%} %> --%>
		</ul>
	</li>
	<% 
	//} 
	if (MenuUtils.checkParent(MenuConstants.QUERY_FEEDBACK,validMenuList)) { %>
		<li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/responsefeedback/feedbacklist"><spring:message code="queryor.feedback.lbl"/></a></li>
	<%}		

	if (MenuUtils.checkParent(MenuConstants.USER_REPORTS,validMenuList)) { %>	
		<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reports/admin"><spring:message code="reports.lbl"/></a></li>
	<% }%>
	
	
	<!-- Admin setup menu for reloading configuration properties -->
	<%-- 
	}
	if (MenuUtils.checkParent(MenuConstants.RECONCILIATION,validMenuList)) { %>	
	<li><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/reconciliation"><spring:message code="reconciliation.lbl"/></a></li>
	<% }%> 
	<% if (true) { %>
		<li class="top"><a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/admin/setup"><spring:message code="setup.lbl"/></a></li>
	<% }%>	 
	--%>
	</ul>
</div>
	<!-- dynamic menu testing start -->
<br></br> 