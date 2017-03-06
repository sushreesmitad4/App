<%@page import="com.tarang.ewallet.walletui.form.SendMoneyMultipleForm"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants" %>

<script>

	$(document).ready(function() {
		checkTransaction();
	});

	function addRow(tableID) {
		//TODO has to add dynamically
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		var row = table.insertRow(rowCount);
		var newcell = row.insertCell(0);
		newcell.innerHTML = "<input type=\"checkbox\" name=\"chk\" />";
		newcell = row.insertCell(1);
		newcell.innerHTML = "<table id='divtable' class='form' style='width: 80%'> "
		+  "			<tr> "
		+  "				<td></td> "
		+  "				<td class='formerror' ></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td></td><td></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td class='formtd'> "
		+  "					<label for='emailId' class='formlebel'> "
		+  "						Email "
		+  "						<span class='mfield'>&nbsp;*</span> "
		+  "					</label> "
		+  "				</td> "
		+  "				<td class='formtd'><input id='emailId' name='emailId' class='forminput' type='text' value=''/></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td></td><td></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td class='formtd'> "
		+  "					<label for='amount' class='formlebel'> "
		+  "						Amount "
		+  "						<span class='mfield'>&nbsp;*</span> "
		+  "					</label> "
		+  "				</td> "
		+  "				<td class='formtd'><input id='amount' name='amount' class='forminput' type='text' value=''/></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td></td><td></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td class='formtd'> "
		+  "					<label for='message' class='formlebel'> "
		+  "					    Message "
		+  "					    <span class='mfield'>&nbsp;</span> "
		+  "					</label> "
		+  "		        </td> "
		+  "				<td class='formtd'><input id='message' name='message' class='forminput' type='text' value=''/></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td></td><td></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td class='formtd'> "
		+  "					<label for='currency' class='formlebel'> "
		+  "						Currency "
		+  "						<span class='mfield'>&nbsp;*</span> "
		+  "					</label> "
		+  "				</td> "
		+  "				<td class='formtd'> "
		+  "					<select id='currency' name='currency' class='formlist'> "
		+  "						<option value='0'>Please Select</option> "
		+  "						"
		+  "							<option title='JPY' value='2'>JPY</option> "
		+  "						"
		+  "							<option title='THB' value='3'>THB</option> "
		+  "						"
		+  "							<option title='USD' value='1'>USD</option> "
		+  "						"
		+  "					</select> "
		+  "				</td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td></td><td></td> "
		+  "			</tr> "
		+  "			<tr> "
		+  "				<td class='formtd'> "
		+  "					<label for='destinationType' class='formlebel'> "
		+  "						Destination Type "
		+  "						<span class='mfield'>&nbsp;*</span> "
		+  "					</label> "
		+  "				</td> "
		+  "				<td class='formtd'> "
		+  "					<select id='destinationType' name='destinationType' class='formlist'> "
		+  "						<option value='0'>Please Select</option> "
		+  "						"
		+  "							<option title='Registered Member' value='1'>Customer</option> "
		+  "						"
		+  "							<option title='Merchant' value='2'>Merchant</option> "
		+  "						"
		+  "							<option title='Non Registered Member' value='4'>Customer_NonRegistered</option> "
		+  "						 "
		+  "					</select> "
		+  "				</td> "
		+  "			</tr>	"
		+  "		</table>";
		
	}

	function deleteRow(tableID) {
		
		var nVer = navigator.appVersion;
		var nAgt = navigator.userAgent;
		var browserName  = navigator.appName;
		var fullVersion  = ''+parseFloat(navigator.appVersion); 
		var majorVersion = parseInt(navigator.appVersion,10);
		var nameOffset,verOffset,ix;

		// In Opera, the true version is after "Opera" or after "Version"
		if ( (verOffset = nAgt.indexOf("Opera")) != -1 ) {
			browserName = '<spring:message code="browser.opera.lbl" />';
			fullVersion = nAgt.substring(verOffset+6);
			if ( (verOffset = nAgt.indexOf("Version")) != -1 ) 
				fullVersion = nAgt.substring(verOffset + 8);
		}
		// In MSIE, the true version is after "MSIE" in userAgent
		else if ( (verOffset = nAgt.indexOf("MSIE")) != -1 ) {
			browserName = '<spring:message code="browser.ie.lbl" />';
			fullVersion = nAgt.substring(verOffset + 5);
		}
		// In Chrome, the true version is after "Chrome" 
		else if ( (verOffset = nAgt.indexOf("Chrome")) != -1 ) {
			browserName = '<spring:message code="browser.chrome.lbl" />';
			fullVersion = nAgt.substring(verOffset + 7);
		}
		// In Safari, the true version is after "Safari" or after "Version" 
		else if ( (verOffset = nAgt.indexOf("Safari")) != -1 ) {
			browserName = '<spring:message code="browser.safari.lbl" />';
			fullVersion = nAgt.substring(verOffset + 7);
			if ((verOffset=nAgt.indexOf("Version")) !=-1) 
				fullVersion = nAgt.substring(verOffset + 8);
		}
		// In Firefox, the true version is after "Firefox" 
		else if ( (verOffset = nAgt.indexOf("Firefox")) != -1 ) {
			browserName = '<spring:message code="browser.firefox.lbl" />';
			fullVersion = nAgt.substring(verOffset+8);
		}
		// In most other browsers, "name/version" is at the end of userAgent 
		else if ( (nameOffset = nAgt.lastIndexOf(' ') + 1) < (verOffset = nAgt.lastIndexOf('/')) ) {
			browserName = nAgt.substring(nameOffset,verOffset);
			fullVersion = nAgt.substring(verOffset+1);
			if (browserName.toLowerCase() == browserName.toUpperCase()) {
				browserName = navigator.appName;
			}
		}
		// trim the fullVersion string at semicolon/space if present
		if ((ix = fullVersion.indexOf(";")) != -1)
		   fullVersion = fullVersion.substring(0, ix);
		if ((ix = fullVersion.indexOf(" ")) != -1)
		   fullVersion = fullVersion.substring(0, ix);

		majorVersion = parseInt('' + fullVersion, 10);
		if (isNaN(majorVersion)) {
			fullVersion  = '' + parseFloat(navigator.appVersion); 
			majorVersion = parseInt(navigator.appVersion, 10);
		}

	    var totakchecks = 0;
		var table = document.getElementById(tableID);
		var rowCount = table.rows.length;
		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox=false;
			if(browserName == '<spring:message code="browser.ie.lbl" />'){
				chkbox = row.cells[0].childNodes[0];
			}
			else{
				try{
					chkbox = row.cells[0].childNodes[1];
					chkbox.checked;
				}
				catch (e) {
					chkbox = row.cells[0].childNodes[0];
				}
			}
			
			
			if (null != chkbox && true == chkbox.checked) {
				totakchecks = 1;
				if (rowCount <= 1) {
					warningDialog('<spring:message code="sendmoney.multiple.rowcount.error.msg" />');
					break;
				}
				table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
		if(totakchecks == 0){
			warningDialog('<spring:message code="sendmoney.multiple.totakchecks.error.msg" />');
		}
	}
	
	$(window).load(function () {   
		var currencies = document.getElementsByName("currency");
		var destinationTypes = document.getElementsByName("destinationType");
		if(currencies != null && currencies.length > 0){
			for( var i = 0; i < currencies.length; i++){
				var cval = document.getElementsByName("currency" + i)[0].value;
				var dval = document.getElementsByName("destinationType" + i)[0].value;
				var cid = 'currency[' + i + ']';
				var did = 'destinationType[' + i + ']';
				setSelectedObjectValue(currencies[i], cval);
				setSelectedObjectValue(destinationTypes[i], dval);
			}
		}
	});
	
	function setSelectedObjectValue( sObject, sValue){
		for (var i = 0; i < sObject.length; i++){
			if (sObject.options[i].value == sValue){
				sObject.selectedIndex = i;
				return;
			}
		}
	}
	
	function forCancel() {
		var message = '<spring:message code="cancel.confirm.msg" />';
		var yesAction = function () {
			var v = "cancel";
   	 		window.location.href='<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/tosingle?mode=' + v;
    	};
	 	confirmationDialog(yesAction, null, null, message);		
	} 
	
	function onCountinue() {
		submitFormData(document.tomultipleform);
	}
</script>
<div class="pageheading"><spring:message code="transfer.money.to.multiple.persons.lbl" /></div>
<jsp:include page="/pages/pageerrors.jsp"></jsp:include>
<form:form name="tomultipleform" method="POST" commandName="sendMoneyMultipleForm" action="tomultiple" >
			<!-- ADD and DELETE table START -->
			<table style="width: 60%" class='form'>
			<tr class="formtr">
                  <td><div class="fileupload">
                   <a href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/sendmoney/fileupload">
                   	<spring:message code="transaction.fileupload" />
                   </a> 
                  </div></td>
				</tr>
			</table>
			<table id="dataTable" style="width: 60%"  class="form" >
				<%
		   			SendMoneyMultipleForm mf = (SendMoneyMultipleForm)request.getAttribute("sendMoneyMultipleForm");
		   		   	int slabSize = mf.getSlabsize();
		   		   	if(slabSize <= 0){
				%>
		        <tr>
			        <td>
			             <input type="checkbox" name="chk"/>
					</td>
					<td>
				         <table id='divtable' class="form" style="width: 80%">
							<tr>
								<td></td>
								<td class="formerror" ><form:errors path="emailId" cssClass="error" /></td>
							</tr>
							<tr>
								<td></td><td><form:errors path="emailId" cssClass="error" /></td>
							</tr>
							<tr>
								<td class="formtd">
									<form:label path="emailId" cssClass="formlebel">
										<spring:message code="emailid.lbl"/>
										<span class="mfield">&nbsp;*</span>
									</form:label>
								</td>
								<td class="formtd"><form:input path="emailId" cssClass="forminput"></form:input></td>
							</tr>
							<tr>
								<td></td><td><form:errors path="amount" cssClass="error" /></td>
							</tr>
							<tr>
								<td class="formtd">
									<form:label path="amount" cssClass="formlebel">
										<spring:message code="amount.lbl" />
										<span class="mfield">&nbsp;*</span>
									</form:label>
								</td>
								<td class="formtd"><form:input path="amount" cssClass="forminput"></form:input></td>
							</tr>
							<tr>
								<td></td><td><form:errors path="message" cssClass="error" /></td>
							</tr>
							<tr>
								<td class="formtd">
									<form:label path="message" cssClass="formlebel">
									    <spring:message code="message.lbl" />
									    <span class="mfield">&nbsp;</span>
									</form:label>
						        </td>
								<td class="formtd"><form:input path="message" cssClass="forminput"></form:input></td>
							</tr>
							<tr>
								<td></td><td><form:errors path="currency" cssClass="error" /></td>
							</tr>
							<tr>
								<td class="formtd">
									<form:label path="currency" cssClass="formlebel" >
										<spring:message code="currency.lbl" />
										<span class="mfield">&nbsp;*</span>
									</form:label>
								</td>
								<td class="formtd">
									<form:select path="currency"  cssClass="formlist" multiple="false">
										<form:option value="0">
											<spring:message code="please.select.lbl" />
										</form:option>
										<c:forEach items="${currencyList}" var="std">
											<form:option value="${std.key}" title="${std.value}" label="${std.value}" />
										</c:forEach>
									</form:select> 
								</td>
							</tr>
							<tr>
								<td></td><td><form:errors path="destinationType" cssClass="error" /></td>
							</tr>
							<tr>
								<td class="formtd">
									<form:label path="destinationType" cssClass="formlebel">
										<spring:message code="destination.type.lbl" />
										<span class="mfield">&nbsp;*</span>
									</form:label>
								</td>
								<td class="formtd">
									<form:select path="destinationType" cssClass="formlist" multiple="false">
									<form:option value="0">
										<spring:message code="please.select.lbl" />
									</form:option>
										<c:forEach items="${destinationType}" var="entry">
											<form:option value="${entry.key}" title="${entry.value}" label="${entry.value}" />
										</c:forEach>
									</form:select>
								</td>
							</tr>	
						</table>
					</td>
				</tr>
				<%
		   		   	}
		   		   	else {
			   		   	for(int i = 0; i < slabSize; i++) {
			   		   		request.setAttribute("k", i);
				%>
				<tr >
			        <td>
			             <input type="checkbox" name="chk"/>
					</td>
					<td>
						<div>
				            <table id='divtable' class="form" style="width: 80%">
								<tr>
									<td></td><td class="formerror" ><form:errors path="emailId" cssClass="error" /></td>
								</tr>
								<tr>
									<td></td><td><form:errors path="emailId[${k}]" cssClass="error" /></td>
								</tr>
								<tr>
									<td class="formtd">
										<form:label path="emailId" cssClass="formlebel">
											<spring:message code="emailid.lbl"/>
											<span class="mfield">&nbsp;*</span>
										</form:label>
									</td>
									<td class="formtd"><input name="emailId" value="<%=mf.getEmailId()[i] %>" class="forminput"></input></td>
								</tr>
								<tr>
									<td></td><td><form:errors path="amount[${k}]" cssClass="error" /></td>
								</tr>
								<tr>
									<td class="formtd">
										<form:label path="amount" cssClass="formlebel">
											<spring:message code="amount.lbl" />
											<span class="mfield">&nbsp;*</span>
										</form:label>
									</td>
									<td class="formtd"><input name="amount" value="<%=mf.getAmount()[i] %>" class="forminput"></input></td>
								</tr>
								<tr>
									<td></td><td><form:errors path="message[${k}]" cssClass="error" /></td>
								</tr>
								<tr>
									<td class="formtd">
								    	<form:label path="message" cssClass="formlebel">
									    	<spring:message code="message.lbl" />
									    	<span class="mfield">&nbsp;</span>
								       </form:label>
							        </td>
						            <td class="formtd"><input name="message" value="<%=mf.getMessage()[i] %>" class="forminput"></input></td>
								</tr>
								<tr>
									<td></td><td><form:errors path="currency[${k}]" cssClass="error" /></td>
								</tr>
								<tr>
									<td class="formtd">
										<form:label path="currency" cssClass="formlebel">
											<spring:message code="currency.lbl" />
											<span class="mfield">&nbsp;*</span>
										</form:label></td>
									<td class="formtd">
										<input type="hidden" name="currency<%=i %>" value="<%=mf.getCurrency()[i] %>" />
										<select id="currency" name="currency" class="formlist" >
											<option value="0" ><spring:message code="please.select.lbl" /></option> 
											<c:forEach items="${currencyList}" var="std">
												<option value="${std.key}" title="${std.value}" >${std.value}</option>
											</c:forEach>
										</select> 
									</td>
								</tr>
								<tr>
									<td></td><td><form:errors path="destinationType[${k}]" cssClass="error" /></td>
								</tr>
								<tr>
									<td class="formtd">
										<form:label path="destinationType" cssClass="formlebel">
											<spring:message code="destination.type.lbl" />
											<span class="mfield">&nbsp;*</span>
										</form:label>
									</td>
									<td class="formtd">
										<input type="hidden" name="destinationType<%=i %>" value="<%=mf.getDestinationType()[i] %>"  />
										<select id="destinationType" name="destinationType" class="formlist" >
											<option value="0" ><spring:message code="please.select.lbl" /></option> 
											<c:forEach items="${destinationType}" var="entry">
												<option value="${entry.key}" title="${entry.value}" >${entry.value}</option>
											</c:forEach>
										</select>
									</td>
								</tr>	
							</table>
						</div>
					</td>
				</tr>
				<%	
						}
		   		   	}
				%>
			</table>
            <!-- ADD and DELETE table END -->
			<table class="form" style="width: 60%" id="buttonstable" >
			<tr>
				<td colspan=2 align="center">
					<div class="formbtns">
							<input type="button" class="styled-button" value='<spring:message code="addnew.lbl" />' onclick="addRow('dataTable')" /> 
							<input type="button" class="styled-button" value='<spring:message code="delete.lbl" />'  onclick="deleteRow('dataTable')" /> 
							<input type="button" class="styled-button" value='<spring:message code="transfer.lbl" />' onclick="onCountinue();" />
							<input type="button" class="styled-button" id="cancel" value='<spring:message code="cancel.lbl" />'  onclick="forCancel();" />
					</div>
				</td>
			</tr>				
			</table>
</form:form>