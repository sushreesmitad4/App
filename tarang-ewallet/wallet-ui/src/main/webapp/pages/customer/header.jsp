<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="com.tarang.ewallet.walletui.controller.AttributeValueConstants"%>
<script type="text/javascript">
$(window).load(function () {   
	DateTime();
});

<%
String name = (String) session.getAttribute("name");
%>

var name = '<%=name%>';
$( document ).ready(function() {
   if(name== "null null")
	{
	   document.getElementById('nameNotNull').style.display = 'none'; 
	}else{
		 document.getElementById('nameNull').style.display = 'none'; 
	}
});
</script>

<ul class="inline-ul floatright">
   	<li><img src="<%=request.getContextPath()%>/img/img-profile.jpg" alt="Profile Pic" /></li>
    <li  class="tophead" id="nameNotNull"><spring:message code="hello.lbl"/>&nbsp;&nbsp;<%=session.getAttribute("name")%>&nbsp; |&nbsp;</li>
    <li  class="tophead" id="nameNull"><spring:message code="hello.lbl"/>&nbsp;&nbsp;<spring:message code="there.lbl" />&nbsp; |&nbsp;</li>
    <li class="tophead"><spring:message code="last.login.lbl"/>:&nbsp;<span id="GMTTimeDate"></span>&nbsp;|&nbsp;</li>
    <li>
    	<a class="customerheader" href="<%=request.getContextPath()%>/<%=AttributeValueConstants.URL_PATH_PREFIX%>/customerlogin/logout?selfinvoke=true">
       	<spring:message code="logout.lbl"/></a>&nbsp;&nbsp;
    </li> 
</ul>
	 