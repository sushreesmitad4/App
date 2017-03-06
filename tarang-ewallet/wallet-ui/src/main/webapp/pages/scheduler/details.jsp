<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	 <tr class="formtr">
	        <td><form:label path="userJobName" cssClass="formlebel"><spring:message code="jobname.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.userJobName}</td>
		</tr > 
		<tr class="formtr">
			<td><form:label path="payeeEmailId" cssClass="formlebel"><spring:message code="receiverid.lbl" /></form:label></td>
			<td>${sendMoneyJobDetailsModel.payeeEmailId}</td>
		</tr> 
		<tr class="formtr">
			<td><form:label path="amount" cssClass="formlebel"><spring:message code="amount.lbl" /></form:label></td>
			<td>${amountStr}</td>
		</tr>  
		<tr class="formtr">
			<td><form:label path="currency" cssClass="formlebel"><spring:message code="currency.lbl" /></form:label></td>
			<td>${sendMoneyJobDetailsModel.currency}</td>
		</tr>  
		<tr class="formtr">
			<td><form:label path="message" cssClass="formlebel"><spring:message code="message.lbl" /></form:label></td>
			<td>
	      		<div style="width: 200px;" class="linebreak" >${sendMoneyJobDetailsModel.message}</div>
         	</td>
		</tr>  
		<tr class="formtr">
	        <td><form:label path="creationDate" cssClass="formlebel"><spring:message code="creationdate.lbl" /></form:label></td>
            <td>${creationDateStr}</td>
		</tr >
		<tr class="formtr">
	        <td><form:label path="startDate" cssClass="formlebel"><spring:message code="startdate.lbl" /></form:label></td>
            <td>${startDateStr}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="endDate" cssClass="formlebel"><spring:message code="enddate.lbl" /></form:label></td>
            <td>${endDateStr}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="nextOccurrenceDate" cssClass="formlebel"><spring:message code="nextOccurrenceDate.lbl" /></form:label></td>
            <td>${nextOccurenceDateStr}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="frequency" cssClass="formlebel"><spring:message code="frequency.lbl" /></form:label></td>
            <td>${frequencyStr}</td>
		</tr> 
		<tr class="formtr">
	        <td><form:label path="totalOccurences" cssClass="formlebel"><spring:message code="total.occurences.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.totalOccurences}</td>
		</tr > 
		<%-- <tr class="formtr">
	        <td><form:label path="recentFiredTime" cssClass="formlebel">RecentFired Time</form:label></td>
            <td>${sendMoneyJobDetailsModel.recentFiredTime}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="recentFiredStatus" cssClass="formlebel">RecentFired Status</form:label></td>
            <td>${sendMoneyJobDetailsModel.recentFiredStatus}</td>
		</tr >  --%>
		<tr class="formtr">
	        <td><form:label path="totalOccurences" cssClass="formlebel"><spring:message code="completedoccurences.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.completedOccurrences}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="totalOccurences" cssClass="formlebel"><spring:message code="successfulOccurrences.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.successfulOccurrences}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="totalOccurences" cssClass="formlebel"><spring:message code="failureOccurrences.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.failureOccurrences}</td>
		</tr > 
		<tr class="formtr">
	        <td><form:label path="totalOccurences" cssClass="formlebel"><spring:message code="remainingOccurrences.lbl" /></form:label></td>
            <td>${sendMoneyJobDetailsModel.remainingOccurrences}</td>
		</tr > 
		<tr class="formtr">
		<td><form:label path="failureMessage" cssClass="formlebel"><spring:message code="failureMessage.lbl" /></form:label></td>
			<c:set var="firedStatus"  value="${sendMoneyJobDetailsModel.recentFiredStatus}"/>
			<c:choose>
				<c:when test="${firedStatus == 6}">
           			 <td>${sendMoneyJobDetailsModel.failureMessage}</td>
				</c:when>
				<c:otherwise>
       			 	<td>Success</td>
   				</c:otherwise>
			</c:choose>
		</tr > 
		