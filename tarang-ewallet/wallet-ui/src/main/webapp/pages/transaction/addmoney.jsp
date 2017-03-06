<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="pagelayout">
	<div class="block" id="sample">
		<table class="form" style="width: 40%" align="center">
		<h2><span align="center"><spring:message code="addmoney.lbl"/></span></h2>
		<span align="center" id="suMsg1" class="successmsg"></span>
		<span align="center" id="amounterror" class="error"></span>
		<span align="center" id="erMsg" class="error"></span>
			<!-- Add Money -->
			<tr>
				<td class="formerror"></td>
			</tr>
			<tr><td class="formtd"></td>
				<td><label for="addmoney" cssClass="formlebel"><spring:message code="addmoney.lbl"/><span class="mfield">&nbsp;*</span>
				</label>&nbsp;&nbsp; <input type="text" class="forminput" name="addmoney" id="addmoney" />
			</tr>
			<tr>
				<td class="formtd"></td>
				<td class="formtd"></td>
			</tr>
			<tr>
				<td class="formtd"></td>
				<td class="formtd"></td>
			</tr>
			<tr>

				<td class="formtd"></td>
				<td colspan="2" align="center">
					<div class="formbtns">
						<input class="styled-button" id="rzp-button1" value="Add Money"
							type="button">
					</div>
			</tr>

			<jsp:include page="/pages/addMoneyErrorPage.jsp"></jsp:include>
		</table>
	</div>
</div>

<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script>
var amount = "";
$('#suMsg1').html('');
$('#amounterror').html('');
var regex  = /^[0-9]*$/;
document.getElementById('rzp-button1').onclick = function(e){
amount = document.getElementById('addmoney').value;

	if (!amount == '' && regex.test(amount)){
			 
		var userType='<%=session.getAttribute("userType")%>';
		var emailId='<%=session.getAttribute("userId")%>';
				
		$.ajax({		        		
	        type: 'GET',
	        url: '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/addmoney/threshold', 
	        data : {
	      	
	      	  amount :  amount.toString(),
	      	  name: '<%=session.getAttribute("name")%>',
	      	  userType:userType,
	      	  emailId:emailId,
	      	  requestedCurrency:'<%=session.getAttribute("defaultcurrency")%>',
	      	
				},
	        datatype: 'json',
	        success: function(result){
	      	 
	      	 if(result!=null)
	      		  {
	      			var erMessage = result.errorMessage;
					if (erMessage!=null)
					           {
						resetForError();
						$('#erMsg').html(erMessage);
						
								}
					else {
						addMoney();
						  }
					     								
	                   }
					}

				});
	         }
		else 
		
		    {
			document.getElementById('suMsg1').innerHTML = "";
			$('#erMsg').html('');
			document.getElementById("amounterror").innerHTML = '<p><spring:message code="invalid.amount.format"/></p>';
			document.getElementById("addmoney").value = "";
			return;
		   }
	     }

	function addMoney()
	        {
    	    amount = document.getElementById('addmoney').value;
		    if (!amount == '' && regex.test(amount))
		      {
			var orderId;
			var userType = '<%=session.getAttribute("userType")%>';
        	var emailId='<%=session.getAttribute("userId")%>';
	
	        var options = {
			
		    "key": "rzp_test_W3cnlRtinVCThn",
		    "amount": amount.toString() + "00", // 2000 paise = INR 20
		    "name": '<%=session.getAttribute("name")%>',
		    "description": "Add money in to your wallet account",
		    "image": '<%=request.getContextPath()%>/img/wallet.png',
		    "handler": function (response){
		     orderId = response.razorpay_payment_id;
		        
		     //Called server call here
		     if(orderId != '') {
		        	$('#errorMsg').html('');
		        	  $.ajax({		        		
				          type: 'POST',
				          url: '<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/addmoney/addmoneyrequest', 
				          data : {
				        	    	    amount :  amount.toString(),
				        	            name: '<%=session.getAttribute("name")%>',
										userType : userType,
										emailId : emailId,
										orderId : orderId,
										requestedCurrency : '<%=session.getAttribute("defaultcurrency")%>',
										
								 },
									
								 datatype : 'json',
								 // async: false,
								 success : function(result) {

								  if (result != null) {
											var erMessage = result.errorMessage;
								  if (erMessage != null) {
									          
												resetForError();
												$('#erMsg').html(erMessage);
								  } else {
												var succMsg = result.successMessage;
								  if (succMsg != null) {
									                resetForSuccess();
									               	$('#suMsg1').html(succMsg);

								   }			var urlRedirect = result.url;
								  if (urlRedirect != null) {
													window.location.href = urlRedirect;

								}
							}
						}

					}
				});

			}

				},
				"prefill" : {
				"name" : "<%=session.getAttribute("name")%>",
		        "email": "<%=session.getAttribute("userId")%>",
		        "contact": "<%=session.getAttribute("phoneNumber")%>"
	
				},
				
				"theme" : {
					
					"color" : "#4289f4"
				}
			};
			var rzp1 = new Razorpay(options);
			rzp1.open();
			e.preventDefault();

		} else {
			document.getElementById('suMsg1').innerHTML = "";
			$('#erMsg').html('');
			document.getElementById("amounterror").innerHTML = '<p><spring:message code="invalid.amount.format"/></p>';

			document.getElementById("addmoney").value = "";

			return;
		}

	}
	
	
	
	
	
</script>


<<script type="text/javascript">

function resetForError()
{
	document.getElementById('addmoney').value="";
	document.getElementById("amounterror").innerHTML="";
	$('#suMsg1').html('');
}


function resetForSuccess()
{
	document.getElementById('addmoney').value="";
	document.getElementById("amounterror").innerHTML="";
	$('#erMsg').html('');
}


function resetAll()
{
	document.getElementById('addmoney').value="";
	document.getElementById("amounterror").innerHTML="";
	$('#suMsg1').html('');
	$('#erMsg').html('');
}

//-->
</script>

