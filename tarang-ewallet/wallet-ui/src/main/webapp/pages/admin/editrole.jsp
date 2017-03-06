<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ page import="java.util.List,com.tarang.ewallet.walletui.controller.AttributeConstants,com.tarang.ewallet.walletui.util.MenuConstants,com.tarang.ewallet.walletui.util.MenuUtils"%>

<script type='text/javascript'>

	function cancelAction() {
		var var31=$('nameHidden').value;
		var var41=$('decHidden').value;
		var var3=document.getElementById("name").value;
		var var4=document.getElementById("description").value;
		var var1=$('menuDetails').value;
		var idObjs = document.getElementsByTagName('input');
		var details = "";
		var message = '<spring:message code="adminrole.editrolepage.confirmmsg.cancel" />';
		for(var i = 0; i < idObjs.length; i++){
			var Obj = idObjs[i];
			if(Obj.type == 'checkbox' && Obj.name.startsWith('f_') && Obj.checked){
				details += ":" + Obj.name.substring(2, Obj.name.length);
			}
		}
		var var2 = details.substring(1, details.length);
		if((var1==var2)&&(var31==var3)&&(var41==var4)){
			window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles';
		}else{
			if(confirm(message)){
				window.location.href = '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/adminroles';
			}
		}	
	}
	
	function onUpdateAction(){
		var message = '<spring:message code="update.record.confirm.msg" />';
		if(confirm(message)){
			submitAction();
			submitRoleFormData(document.editPage);
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
	}

</script>

<div class="pageheading"><spring:message code="edit.role.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form commandName="roleForm" action="editrole" name="editPage">  	
	<table class="form" style="width: 40%">
		<tr>
			<td colspan="2"><form:hidden path="id"></form:hidden></td>
		</tr>
		<tr>
			<td>
				<form:label path="name" cssClass="formlebel">
				<spring:message code="name.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td>${roleForm.name}
				<form:errors path="name" cssClass="error" />
				<form:hidden path="nameHidden"></form:hidden>
			</td>
		</tr>
		<tr>
	    	<td></td>
			<td><form:errors path="description" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="description" cssClass="formlebel">
				<spring:message code="description.lbl" /><span class="mfield">&nbsp;*</span></form:label>
			</td>
			<td>
				<form:input path="description" cssClass="forminput"></form:input>
				<form:hidden path="decHidden"></form:hidden>
			</td>
		</tr>
		<tr>
	    	<td></td>
			<td><form:errors path="menuDetails" cssClass="error" /></td>
		</tr>
		<tr>
			<td>
				<form:label path="menuDetails" cssClass="formlebel">
				<spring:message code="menu.details.lbl" /><span class="mfield">&nbsp;*</span></form:label>
				<form:hidden path="menuDetails"></form:hidden>
			</td>
		</tr>
		<tr>
			<td colspan=2>
				<jsp:include page="menus.jsp"></jsp:include>
			</td>
		</tr>
		<tr>
			<td><form:hidden path="name"></form:hidden></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<div class="formbtns">
				 <% 
						Boolean adminAccessCheck = MenuUtils.isAdminAccessRole((List<String>)session.getAttribute(AttributeConstants.MENU_DETAILS),
							MenuConstants.ADMIN_ROLE_MANAGEMENT_MI, MenuConstants.MANAGE_PERMISSION);
				    	if(adminAccessCheck){
		          %>
					<input type="button" onclick="onUpdateAction()" class="styled-button" value='<spring:message code="updateRole.lbl" />' />
				  <% } %>
					<input type="button" onclick="cancelAction()" class="styled-button"  value='<spring:message code="cancel.lbl" />' />
				</div>
			</td>
		</tr>
	</table>
</form:form>