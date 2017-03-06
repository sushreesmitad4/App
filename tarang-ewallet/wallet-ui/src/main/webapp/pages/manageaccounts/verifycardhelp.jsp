<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
jQuery(function( $ ){
	showHelpText("cardvercomp", "cardverspan");
});
</script>

<div>
	<a id="cardvercomp" href="javascript:void(0)"><img src="/wallet-ui/img/help_circle.png" class="help-icon" /> </a>
	<div id="cardverspan" class="help-icon-content">
	<span><spring:message code="card.verification.tips.lbl" /></span>
		<ul>
			<li><spring:message code="card.verification.help.info" /></li>
		</ul>
	</div>
</div>
			