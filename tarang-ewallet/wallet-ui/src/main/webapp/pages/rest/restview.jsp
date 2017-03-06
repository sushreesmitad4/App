<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script>

	function continueAction(){
		document.getElementById("restRequest").value = document.getElementById("restRequest").value;
		document.getElementById("restResponse").value = "";
		submitFormData(document.restForm);
	}
	
	function onReset()
	{
		window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/restrequest';
		return;
	}
	
	function onHome()
	{
		window.location.href ='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/home';
		return;
	}
	
	function setJson(){
		$("#restResponse").css("background-color", "red");
		var jsonValue ;
		var restOperationId = document.getElementById("restOperation").value;
		
		if("/devicehome/activate" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","password":"aaaa1A@","userType":"C", "msisdnNumber":"111113","simNumber":"33333","imeiNumber":"44444"}';
		}
		
		else if("/devicehome/mpingeneration" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","password":"aaaa1A@","userType":"C", "msisdnNumber":"111113","simNumber":"33333","imeiNumber":"44444","mPin":"1234"}';
		}
	
		else if("/devicehome/devicechangempin" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","password":"aaaa1A@","userType":"C", "msisdnNumber":"111113","simNumber":"33333","imeiNumber":"44444","mPin" :"1234","newMPin":"4321"}';
		}
		
		else if("/devicehome/forgotmpin" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C", "msisdnNumber":"111113","simNumber":"33333","imeiNumber":"44444","question":"1","answer":"abc"}';
		}
		
		else if("/devicehome/devicelogin" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C","simNumber":"33333", "imeiNumber":"44444", "mPin" :"1234"}';
		}
		
		else if("/devicehome/logout" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C","msisdnNumber":"111113","simNumber":"33333", "imeiNumber":"44444"}';
		}
		
		else if("/devicehome/deactivate" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C","msisdnNumber":"111113","simNumber":"33333", "imeiNumber":"44444","mPin":"1234"}';
		}
		
		else if("/feedback" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C","msisdnNumber":"111113","simNumber":"33333", "imeiNumber":"44444","queryType":"1","subject":"feedback","message":"feedback"}';
		}
		
		else if("/profilemgmt" == restOperationId){
			jsonValue = '{"email":"document.testing.003@gmail.com","userType":"C","msisdnNumber":"111113","simNumber":"33333", "imeiNumber":"44444"}';
		}
		
		//addcard
		else if("/manageaccounts/addcard" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","accountHolderName":"varugowda","userType":"C","cardType":"1","cardNumber":"4916786722456935","expireDateYear":"2030","expireDateMonth":"12","cvv":"456","isSameAsProfileAddress":"true","jointAccount":"false"}';
		}
		//verify card
		else if("/manageaccounts/verifycards" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","cvv":"123","accountId":"164","amount":"10","code":"261213034427"}';
		}
		//edit card
		else if("/manageaccounts/editcard" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","cvv":"123","accountId":"108","accountHolderName":"varugowda","cardNumber":"5193241027421599","expireDateYear":"2031","expireDateMonth":"12"}';
	   }
	  // delete card
	   else if("/manageaccounts/delete" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","accountId":"216"}';
	   }
	   //Set Default Card
	   else if("/manageaccounts/setdefault" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","accountId":"231"}';
	   }
	   //List Of Cards	
	   else if("/manageaccounts/accountrecords" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C"}';
	   }
	   //List Of Card Types	
	   else if("/masterdatarestservice/typesofcards" == restOperationId){
			jsonValue = '{"msisdnNumber":"8431021017","simNumber":"88888888888","imeiNumber":"999999999","email":"mwalletusers@gmail.com","userType":"C"}';
	   }
	   //View Wallet Balance
	   else if("/viewwalletbalace" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C"}';
	   }
	   //View Wallet Last 5 Transactions 
	   else if("/viewlastntransactions" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C"}';
	   }
	   //List Of Supporting Wallets
	   else if("/wallettransaction/listofwallets" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","userType" : "C","email" : "varugowda22@gmail.com"}';
	   }
		//Self Transfer
	   else if("/wallettransaction/selftransfer" == restOperationId){
			jsonValue = '{"email":"varugowda22@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","requestedAmount":"100","fromWallet":"1","towallet":"2"}';
	   }
	 //Self Transfer Confirmation
	   else if("/wallettransaction/selftransferconfirm" == restOperationId){
			jsonValue = '{"email":"varugowda22@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","requestedAmount":"100","actualAmount":"100.06","fromWallet":"1","towallet":"2","mPin":"1234"}';
	   }
	  //Transfer Money To Single
	   else if("/wallettransaction/tosingle" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","requestedAmount":"50","receiverEmail":"varalakshmi@tarangtech.com","destinationType":"1","requestedCurrency":"1","message":"P2P"}';
	   }
	 //Transfer Money To Single Confirmation
	   else if("/wallettransaction/tosingleconfirm" == restOperationId){
			jsonValue = '{"msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","email":"varugowda22@gmail.com","userType":"C","requestedAmount":"50","receiverEmail":"varalakshmi@tarangtech.com","destinationType":"1","requestedCurrency":"1","message":"P2P","mPin":"1234"}';
	   }
		else if("/reloadmoney/relmonservice" == restOperationId){
			jsonValue = '{\"email\":\"reload.merchant@gmail.com\",\"userType\":\"M\",\"accountId\":\"654\",\"amount\":\"1000\",\"cvv\":\"123\",\"msisdnNumber\":\"22222222\",\"simNumber\":\"2222222222\",\"imeiNumber\":\"3333333333\"}';
		}
		else if("/masterdatarestservice/destinationtypes" == restOperationId){
			jsonValue = '{"email":"apitest744@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444"}';
		}
		else if("/masterdatarestservice/listofcurrency" == restOperationId){
			jsonValue = '{"email":"apitest744@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444"}';
		}
		else if("/masterdatarestservice/hintquestions" == restOperationId){
			jsonValue = '{"email":"apitest744@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444"}';
		}
		else if("/manageaccounts/getaccountdetails" == restOperationId){
			jsonValue = '{"email":"apitest744@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444","accountId":"216"}';
		}
		else if("/masterdatarestservice/destinationtypesandcurrency" == restOperationId){
			jsonValue = '{"email":"apitest744@gmail.com","userType":"C","msisdnNumber":"9886484280","simNumber":"55555555555555","imeiNumber":"4444444444"}';
		}
		document.getElementById("restRequest").value = jsonValue;
	}

			
</script>

<div class="pageheading">Rest Web Service Test</div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form commandName="restTestForm" action="restrequest" name="restForm" >
	<table class="form" style="width: 80%">
		<tr>
			<td></td>
			<td><form:errors path="restUrl" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="restUrl" cssClass="formlebel">API Url <span class="mfield">&nbsp;*</span></form:label></td>
			<td><form:input path="restUrl" cssClass="forminput"/></td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="restOperation" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="restOperation" cssClass="formlebel">Rest Operation <span class="mfield">&nbsp;*</span></form:label></td>
			<td><form:select path="restOperation" onchange="setJson();" cssClass="formlist">
					<form:option value="0L">
						<spring:message code="please.select.lbl" />
					</form:option>
					<c:forEach items="${operationList}" var="entry">
						<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
					</c:forEach>
				</form:select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td><form:errors path="restRequest" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="restRequest" cssClass="formlebel">Rest Request <span class="mfield">&nbsp;*</span></form:label></td>
			<td><form:textarea rows="7" cols="70" path="restRequest" /></td>
		</tr>		
	
		<tr>
			<td></td>
			<td><form:errors path="restResponse" cssClass="error" /></td>
		</tr>
		<tr class="formtr">
			<td><form:label path="restResponse" onclick="onResetResponse();" cssClass="formlebel">Rest Response</form:label></td>
			<td><textarea rows="10" cols="70" readonly="readonly">${responseList}</textarea>
		 </tr>		
		  <tr class="formtr">
			<td colspan=2 align="center">
				<div class="formbtns">
					<input type="submit" value="Submit" class="styled-button" onclick ="continueAction()"/>
					<input type="button" id="resetvalue" class="styled-button" value="Reset" onclick ="onReset()"/>
					<input type="button" id="homevalue" class="styled-button" value="Home" onclick ="onHome()"/>
				</div>
			</td>
		</tr>
		
	</table>

</form:form>