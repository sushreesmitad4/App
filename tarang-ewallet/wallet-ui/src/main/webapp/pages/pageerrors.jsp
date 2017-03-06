<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="center">
	<table>
		<tr class="formtr">
			<c:if test="${successMessage ne null }">
				<td><span class="successmsg" id="suMsg">${successMessage}</span></td>
			</c:if>
			<c:if test="${errorMessage ne null }">
				<td><span class="errormsg" id="erMsg">${errorMessage}</span></td>
			</c:if>
			<c:if test="${succMsg ne null }">
				<td><span class="success" id="suMsg1">${succMsg}</span></td>
			</c:if>
		</tr>
	</table>
</div>
