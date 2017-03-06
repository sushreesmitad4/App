<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" />
<html>
	<head>
		<meta http-equiv="content-type" content="text-html; charset=ISO-8859-1">
		<link href="<%=request.getContextPath()%>/img/customerlogo.png" rel="shortcut icon" type="image/x-icon"/>	
		<title><spring:message code="cvv.help.lbl" /></title>
			<script type="text/javascript">
			
				function changeImage() {
				    var a1=document.getElementsByTagName("img")[1].getAttribute("src");
					var b1=a1.lastIndexOf("/") + 1;
					var c1=a1.substr(b1);
				    if (c1 == "amex.png") {
				        document.getElementById("imgClickAndChange").src = "<%=request.getContextPath()%>/img/visa.png";
						document.getElementById("b").style.display='block';
						document.getElementById("a").style.display='none';
						document.getElementById("c").style.display='none';
					}
				    else {
				        document.getElementById("imgClickAndChange").src = "<%=request.getContextPath()%>/img/amex.png";
						document.getElementById("b").style.display='none';
						document.getElementById("c").style.display='none';
						document.getElementById("a").style.display='block';
					}
				}
			
			</script>
	</head>
	<body>
		<img src="<%=request.getContextPath()%>/img/customerlogo.png" class="walletlogoborder"></img>
		<div id="c">
			<h2 class="h2color"><spring:message code="card.security.code.lbl" /></h2>
			<p><spring:message code="security.code.info" /></p>
		  	<a href="javascript:void(0)" onclick="changeImage()"><font class="cvvhelpfont" ><spring:message code="master.or.visa.lbl" /></font></a>
		</div>
		<div id="a" style="display:none">
			<h2 class="h2color"><spring:message code="four.digit.card.security.code.lbl" /></h2>
			<p><spring:message code="security.code.info" /></p>
		  	<a href="javascript:void(0)" onclick="changeImage()"><font class="cvvhelpfont" ><spring:message code="master.or.visa.lbl" /></font></a>
		</div>
		<div id="b" style="display:none">
			<h2 class="h2color"><spring:message code="three.digit.code.lbl" /></h2>
			<p><spring:message code="security.verification.info" /></p>
			<a href="javascript:void(0)" onclick="changeImage()"><font class="cvvhelpfont" ><spring:message code="using.americanexpress.lbl" /></font></a>
		</div>
		<a href="javascript:void(0)" ><img class="img border" src="<%=request.getContextPath()%>/img/amex.png"  id="imgClickAndChange" /></a>
		<div class="divmargin"><p class="copyright">
			<p><spring:message code="copyright.lbl"/>&nbsp;&nbsp;<a href="javascript:void(0)" style="text-decoration: none;"><spring:message code="company.name.lbl" /></a>&nbsp;&nbsp;&nbsp;<spring:message code="allrights.reserved.lbl" /></p>
		</div>
	</body>
</html>