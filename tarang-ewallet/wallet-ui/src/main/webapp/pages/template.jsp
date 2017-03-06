<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8" buffer="20kb"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text-html; charset=ISO-8859-1">
		
		<link rel="shortcut icon" type="image/ico" href="<%=request.getContextPath()%>/img/favicon.ico"/>
		<link rel="stylesheet" type="text/css" media="screen" href='<%=request.getContextPath()%>/jq/css/jquery-ui/pepper-grinder/jquery-ui-1.8.16.custom.css'/>
		<link rel="stylesheet" type="text/css" media="screen" href='<%=request.getContextPath()%>/jq/css/ui.jqgrid-4.3.1.css'/>
		
		<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/wallet.css" />
		<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/header.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/nav.css" media="screen" />
		<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/menubar.css" />
		<link rel="StyleSheet" type="text/css" href="<%=request.getContextPath()%>/css/footer.css" />
		
		<script type='text/javascript' src='<%=request.getContextPath()%>/jq/js/jquery-1.6.4.min.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/jq/js/jquery-ui-1.8.16.custom.min.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/jq/js/grid.locale-en-4.3.1.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/jq/js/jquery.jqGrid.min.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/jq/js/custom.js'></script>
		<script id="loader" src='<%=request.getContextPath()%>/jq/js/script.js' type="text/javascript"></script>
		
		<title><tiles:insertAttribute name="title" /></title>
		<jsp:include page="/pages/commonscript.jsp"></jsp:include>
		<style type="text/css">
		/* Ajax Loader styles */
		.ajax_loader {background: url("/wallet-ui/img/spinner_squares_circle.png") no-repeat center center transparent;width:100%;height:100%;}
		</style>
	</head>
	<body>
		<noscript><h2><span class="errormsg"><spring:message code="javascript.template.errmsg" /></span></h2></noscript>
		<div class="container">
			<div class="site_header">
				<div class="grid_hd">
					<%-- <div class="floatleft"><img src="<%=request.getContextPath()%>/img/customerlogo.png"></div> --%>
					<div class="floatleft"><img src="<%=request.getContextPath()%>/img/eWallet_logo.png"></div>
					<div class="floatright">
						<tiles:insertAttribute name="header" />
					</div>
				</div>
			</div>
			<div class="site_navigation">
				<tiles:insertAttribute name="menubar" />
			</div>
			<div class="site_body">
				<center>
					<tiles:insertAttribute name="body" />
				</center>
			</div>
			<div class="bottom_container">
				<tiles:insertAttribute name="footer" />
			</div>
			<!-- Ajax Loader styles -->
			<div style="display:none">
				<img src="<%=request.getContextPath()%>/img/spinner_squares_circle.png" />
			</div>
		</div>
	</body>
</html>