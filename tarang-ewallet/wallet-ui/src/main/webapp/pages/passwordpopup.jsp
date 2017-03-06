<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
jQuery(function( $ ){
	showHelpText("pwdhelpcomp", "pwdspan");
});
</script>

<div>
	<a id="pwdhelpcomp" href="javascript:void(0)"><img  src="/wallet-ui/img/help_circle.png" class="help-icon" /> </a>
	<div id="pwdspan" class="help-icon-content">
		<span><spring:message code="password.tips.lbl" /></span>
		<ul>
			<li><spring:message code="password.tips1.lbl" /></li>
			<li><spring:message code="password.tips2.lbl" /></li>
			<li><spring:message code="password.tips3.lbl" /></li>
			<li><spring:message code="password.tips4.lbl" /></li>
		</ul>
	</div>
</div>
				