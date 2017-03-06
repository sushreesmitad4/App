<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div align="center">
	<table>
		<tr class="formtr">
			
			<c:if test="${succMsg ne null }">
				<td><span class="success" id="suMsg1">${succMsg}</span></td>
			</c:if>
		</tr>
	</table>
</div>