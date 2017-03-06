<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.tarang.ewallet.walletui.util.MenuConstants"%>

<style>
li.b {
	list-style-image:url('<%=request.getContextPath()%>/img/transparent_image.png');
	text-align: left;
	padding-left: 5px;
	text-display:block;
}
</style> 

<script type='text/javascript'>
	var a = true;
	var b = true;
	var c = true;
	$(document).ready(function () {
		var idstr = $('menuDetails').value;
		var ids = idstr.split(":");
		for(var i = 0; i < ids.length; i++){
			
			var id = ids[i];
			if(id != ""){
				var name = document.getElementsByName('f_' + id)[0];
				name.checked = true;
				
			}
		}
	});
	
	 
    function showChildren(obj) {    
    	var children = obj.immediateDescendants(); 
    	expandOrCollapse(obj);
    	for(var i = 0; i < children.length; i++)     {         
    		if(children[i].tagName.toLowerCase() == 'ul') {            
    			var v = children[i].toggle();  
    		}
    	} 
    }  
    
    function expandOrCollapse(obj){
    	if(obj.id == <%= MenuConstants.MANAGEMENT_M%> ){
    		a = !a;	
    	  	var ele = document.getElementById('image1' );
    			if(!a){
    				ele.src = '<%=request.getContextPath()%>/img/plus.png';
    			}
    			else{
    				ele.src = '<%=request.getContextPath()%>/img/minus.png';
    			}
    	}
    	if(obj.id == <%= MenuConstants.FEE_MANAGEMENT_M%> ){
    		b = !b;	
    	  	var ele = document.getElementById('image2');
    			if(!b){
    				ele.src = '<%=request.getContextPath()%>/img/plus.png';
    			}
    			else{
    				ele.src = '<%=request.getContextPath()%>/img/minus.png';
    			}
    	}
    	if(obj.id == <%= MenuConstants.PROFILE_M %> ){
    		c = !c;	
    	 	 var ele = document.getElementById('image3' );
    			if(!c){
    				ele.src = '<%=request.getContextPath()%>/img/plus.png';
    			}
    			else{
    				ele.src = '<%=request.getContextPath()%>/img/minus.png';
    			}
    	}
    }
    function viewOrManageCheck(obj, srcObj){
    	var check_id = obj.id.toString();
    	if(check_id.endsWith("_2") && document.getElementsByName('f_' + check_id )[0].checked ){
    		var p_id = check_id.split("_")[0]
    		document.getElementsByName('f_'+p_id+'_1')[0].checked = true;
    	}
    	if(check_id.endsWith("_1") && !document.getElementsByName('f_' + check_id )[0].checked){
    		var p_id = check_id.split("_")[0]
    		document.getElementsByName('f_'+p_id+'_2')[0].checked = false;
    	}
    	updateStatus(obj, srcObj);
    }
    function updateStatus(obj, srcObj){
    	if(!srcObj.checked){
    		checkPrent(obj, false, true);
    	}
    	if(srcObj.checked){
    		 if(common(obj)){
    			common1(obj);
			} 
    	}
    	checkChildren(obj,srcObj);
    }
    
    function common(obj){
  
    	var sb = $(obj.id).siblings();
    	sb[sb.length]=obj;
		var flag = false;
			for(var i= 0; i < sb.length; i++){
				var c = document.getElementsByName('f_' + sb[i].id )[0].checked;
				if(c){
					flag = true;
				}
				else{
					flag = false;
					break;
				}
			}
    	return flag;
    }
    
     function common1(obj){
    	var pe = obj.parentNode.parentNode;
    	if(pe.tagName.toLowerCase() == 'li' ){
    			lich = pe.childNodes;
    		for(var i = 0; i < lich.length; i++){
    			if(lich[i].tagName != null && lich[i].tagName.toLowerCase()=='input' && lich[i].type == 'checkbox'  ){  
    				if(common(obj)){
    				checkPrent(obj, true, false); 
    				}
        		}
    		}
    		obj = obj.parentNode.parentNode;
    	 	common1(obj);
       	}
    } 
     
    function checkPrent(obj, srcState, flag){
    	var pe = obj.parentNode.parentNode;
    	if(pe.tagName.toLowerCase() == 'li'){
    		var lich = pe.childNodes;
    		for(var i = 0; i < lich.length; i++){
    			if(lich[i].tagName != null && lich[i].tagName.toLowerCase()=='input' && lich[i].type == 'checkbox' ){             
    				lich[i].checked = srcState;      
        		}
    		}
    		if(flag){
    	 		checkPrent(pe, srcState, flag);
    		}
    	}
    }
    
    function checkChildren(obj,srcObj) {     
    	var children = obj.immediateDescendants();     
    	for(var i = 0; i < children.length; i++)     {         
    		if(children[i].tagName.toLowerCase() == 'input' && children[i].type == 'checkbox' && children[i] != srcObj){             
    			children[i].checked = srcObj.checked;      
    		}
    		checkChildren(children[i], srcObj);     
    	} 
    }
    
</script>

<script type='text/javascript' src='<%=request.getContextPath()%>/jq/prototype.js'></script>
<div class="rolemenus" style="height: 380px; overflow: auto;"> 
	<ul>
	  	<li class="b">
			<ul class="pmenu">		
				<li class="b"  id="<%=MenuConstants.MANAGEMENT_M%>">
				    <img  id="image1" src="<%=request.getContextPath()%>/img/minus.png" onclick='showChildren($("<%=MenuConstants.MANAGEMENT_M%>"));'/>
					<input type='checkbox' name='f_<%=MenuConstants.MANAGEMENT_M%>' onclick='updateStatus($("<%=MenuConstants.MANAGEMENT_M%>"),this);'>   
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.MANAGEMENT_M%>"));'><spring:message code="management.lbl"/></label>  
					<ul class="b">
						<li  class="b" id="<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>' onclick='updateStatus($("<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>"));'><spring:message code="admin.mgmt.lbl"/></label>
							<ul class="b">
								<li  class="b" id="<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li  class="b" id="<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ADMIN_USER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li  class="b" id="<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>' onclick='updateStatus($("<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>"),this);'>		
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>"));'><spring:message code="admin.role.management.lbl"/></label>
							<ul class="b">
								<li  class="b" id="<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>		
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li  class="b" id="<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ADMIN_ROLE_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>		
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li  class="b" id="<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>' onclick='updateStatus($("<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>"));'><spring:message code="merchant.mgmt.lbl"/></label>
							<ul class="b">
								<li  class="b" id="<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li  class="b" id="<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.MERCHANT_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>' onclick='updateStatus($("<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>"));'><spring:message code="customer.mgmt.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.CUSTOMER_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem"><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li  class="b" id="<%=MenuConstants.TAX_MANAGEMENT_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.TAX_MANAGEMENT_MI%>' onclick='updateStatus($("<%=MenuConstants.TAX_MANAGEMENT_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.TAX_MANAGEMENT_MI%>"));'><spring:message code="tax.mgmt.lbl"/></label>
							<ul class="b">
								<li  class="b" id="<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li  class="b" id="<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.TAX_MANAGEMENT_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.FEE_MANAGEMENT_M%>">
							<img  id="image2" src="<%=request.getContextPath()%>/img/minus.png" onclick='showChildren($("<%=MenuConstants.FEE_MANAGEMENT_M%>"));'/>
							<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_M%>' onclick='updateStatus($("<%=MenuConstants.FEE_MANAGEMENT_M%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.FEE_MANAGEMENT_M%>"));'><spring:message code="fee.mgmt.lbl"/></label>
							<ul  class="b">
								<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_F_MI%>">
									<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_F_MI%>' onclick='updateStatus($("<%=MenuConstants.FEE_MANAGEMENT_F_MI%>"),this);'>
									<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.FEE_MANAGEMENT_F_MI%>"));'><spring:message code="financial.service.lbl"/></label>
									<ul class="b">
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
										</li>
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_F_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
										</li>
									</ul>
								</li>
								<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>">
									<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>' onclick='updateStatus($("<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>"),this);'>
									<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>"));'><spring:message code="non.financial.service.lbl"/></label>
									<ul class="b">
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
										</li>
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_NF_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
										</li>
									</ul>
								</li>
								<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>">
									<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>' onclick='updateStatus($("<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>"),this);'>
									<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>"));'><spring:message code="financial.on.velocity.lbl"/></label>
									<ul class="b">
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
										</li>
										<li  class="b" id="<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
											<input type='checkbox' name='f_<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.FEE_MANAGEMENT_NFV_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
											<label class="rolemenutreeitem"><spring:message code="view.lbl"/></label>
										</li>
									</ul>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>' onclick='updateStatus($("<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>"));'><spring:message code="transaction.threshold.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.TRANSACTION_THRESHOLD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.WALLET_THRESHOLD%>">
							<input type='checkbox' name='f_<%=MenuConstants.WALLET_THRESHOLD%>' onclick='updateStatus($("<%=MenuConstants.WALLET_THRESHOLD%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.WALLET_THRESHOLD%>"));'><spring:message code="wallet.threshold.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.WALLET_THRESHOLD%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>">
							<input type='checkbox' name='f_<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>' onclick='updateStatus($("<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>"));'><spring:message code="account.closer.mgmt.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.ACCOUNT_CLOSURE_MANAGEMENT%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
					</ul>
				</li>
				<li class="b" id="<%=MenuConstants.DISPUTE%>">
				    <img src="<%=request.getContextPath()%>/img/transparent_image.png"/>
					<input type='checkbox' name='f_<%=MenuConstants.DISPUTE%>' onclick='updateStatus($("<%=MenuConstants.DISPUTE%>"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.DISPUTE%>"));'><spring:message code="dispute.lbl"/></label>
					<ul class="b">
						<li class="b" id="<%=MenuConstants.DISPUTE%>_<%=MenuConstants.MANAGE_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.DISPUTE%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.DISPUTE%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
						</li>
						<li class="b" id="<%=MenuConstants.DISPUTE%>_<%=MenuConstants.VIEW_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.DISPUTE%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.DISPUTE%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
						</li>
					</ul>
				</li>
				<%-- <li class="b" id="<%=MenuConstants.PROFILE_M%>">
					<img  id="image3" src="<%=request.getContextPath()%>/img/minus.png" onclick='showChildren($("<%=MenuConstants.PROFILE_M%>"));'/>
					<input type='checkbox' name='f_<%=MenuConstants.PROFILE_M%>' onclick='updateStatus($("<%=MenuConstants.PROFILE_M%>"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.PROFILE_M%>"));'><spring:message code="profile.lbl"/></label>
					<ul  class="b">
						<li class="b" id="<%=MenuConstants.CHANGE_PASSWORD_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.CHANGE_PASSWORD_MI%>' onclick='updateStatus($("<%=MenuConstants.CHANGE_PASSWORD_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.CHANGE_PASSWORD_MI%>"));'><spring:message code="change.password.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.CHANGE_PASSWORD_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
						<li class="b" id="<%=MenuConstants.PREFERENCES_MI%>">
							<input type='checkbox' name='f_<%=MenuConstants.PREFERENCES_MI%>' onclick='updateStatus($("<%=MenuConstants.PREFERENCES_MI%>"),this);'>
							<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.PREFERENCES_MI%>"));'><spring:message code="preferences.lbl"/></label>
							<ul class="b">
								<li class="b" id="<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
								</li>
								<li class="b" id="<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.VIEW_PERMISSION%>">
									<input type='checkbox' name='f_<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.PREFERENCES_MI%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
									<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
								</li>
							</ul>
						</li>
					</ul>
				</li> --%>
				<li class="b" id="<%=MenuConstants.USER_REPORTS%>">
					<img src="<%=request.getContextPath()%>/img/transparent_image.png"/>
					<input type='checkbox' name='f_<%=MenuConstants.USER_REPORTS%>' onclick='updateStatus($("<%=MenuConstants.USER_REPORTS%>"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.USER_REPORTS%>"));'><spring:message code="reports.lbl"/></label>
					<ul class="b">
						<li class="b" id="<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.MANAGE_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
						</li>
						<li class="b" id="<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.VIEW_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.USER_REPORTS%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
						</li>
					</ul>
				</li>
				<li class="b" id="<%=MenuConstants.QUERY_FEEDBACK%>">
					<img src="<%=request.getContextPath()%>/img/transparent_image.png"/>
					<input type='checkbox' name='f_<%=MenuConstants.QUERY_FEEDBACK%>' onclick='updateStatus($("<%=MenuConstants.QUERY_FEEDBACK%>"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.QUERY_FEEDBACK%>"));'><spring:message code="queryor.feedback.lbl"/></label>
					<ul class="b">
						<li class="b" id="<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.MANAGE_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.MANAGE_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.MANAGE_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="role.permission.manage.lbl"/></label>
						</li>
						<li class="b" id="<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.VIEW_PERMISSION%>">
							<input type='checkbox' name='f_<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.VIEW_PERMISSION%>' onclick='viewOrManageCheck($("<%=MenuConstants.QUERY_FEEDBACK%>_<%=MenuConstants.VIEW_PERMISSION%>"),this);'>
							<label class="rolemenutreeitem" ><spring:message code="view.lbl"/></label>
						</li>
					</ul>
				</li>
				<!-- <li class="b" id="<%=MenuConstants.RECONCILIATION%>">
					<img src="<%=request.getContextPath()%>/img/transparent_image.png"/>
					<input type='checkbox' name='f_<%=MenuConstants.RECONCILIATION%>' onclick='updateStatus($("<%=MenuConstants.RECONCILIATION%>"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("<%=MenuConstants.RECONCILIATION%>"));'><spring:message code="reconciliation.lbl"/></label>
				</li> -->
				
				<!-- Admin setup menu for reloading configuration properties 
				<li class="b" id="26">
					<img src="<%=request.getContextPath()%>/img/transparent_image.png"/>
					<input type='checkbox' name='f_26' onclick='updateStatus($("26"),this);'>
					<label class="rolemenutreeitem" onclick='showChildren($("26"));'><spring:message code="setup.lbl"/></label>
				</li>-->
			</ul>	
		</li>
	</ul>
</div>

