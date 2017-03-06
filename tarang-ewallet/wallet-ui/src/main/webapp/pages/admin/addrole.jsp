<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type='text/javascript'>

	function cancelAction() {
		if(confirm("Are you sure you want to exit without saving?")){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles';
		}
	} 
 	
	function submitAction(){
		var idObjs = document.getElementsByTagName('input');
		var details = "";
		for(var i = 0; i < idObjs.length; i++){
			var Obj = idObjs[i];
			if(Obj.type == 'checkbox' && Obj.name.startsWith('f_') && Obj.checked){
				details += ":" + Obj.name.substring(2, Obj.name.length);
			}
		}
		$('menuDetails').value = details.substring(1, details.length);
		submitRoleFormData(document.role);
	}

</script>
<div class="pageheading"><spring:message code="add.role.lbl" /></div>
<form:form commandName="roleForm" action="addrole" name = "role">	
	<table class="form" style="width: 50%;">
		<tr>
			<td><form:hidden path="id"></form:hidden></td>
		</tr>
		<tr>
	    	<td></td>
			<td><form:errors path="name" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="name" cssClass="formlebel"><spring:message code="name.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td><form:input path="name" cssClass="forminput"></form:input>
		</tr>
		<tr>
	    	<td></td>
			<td><form:errors path="description" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="description" cssClass="formlebel"><spring:message code="description.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td><form:input path="description" cssClass="forminput"></form:input>
		</tr>
		<tr>
	    	<td></td>
			<td><form:errors path="menuDetails" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="menuDetails" cssClass="formlebel"><spring:message code="menu.details.lbl" /><span class="mfield">&nbsp;*</span></form:label>
				<form:hidden path="menuDetails"></form:hidden>
			</td>
		</tr>  
		<tr>
           <td colspan=2>
				<jsp:include page="menus.jsp"></jsp:include>
			</td>
			
		</tr>
		<tr>
			<td colspan=2 align="center">
			   <div class="formbtns">
			   <% 
					Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
						MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
			    	if(adminAccessCheck){
		       %>	
				<input type="button" onclick="submitAction()" class="styled-button"  value='<spring:message code="createRole.lbl" />' />
				<% } %>
				<input type="button" onclick="cancelAction()"  class="styled-button" value='<spring:message code="cancel.lbl" />' />
			   </div>
			</td>
		</tr>
	</table>
</form:form>