<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
jQuery(function( $ ){
	showHelpText("slabhelpcomp", "slabspan");
});
</script>

<div>
	<a id="slabhelpcomp" href="javascript:void(0)"><img src="/wallet-ui/img/help_circle.png" class="help-icon" /> </a>
	<div id="slabspan" class="help-icon-content">
		<span><spring:message code="slab.tips.lbl" /></span>
		<ul>
			<li><spring:message code="slablowerlimit.message1" /></li>
		</ul>
	</div>
</div>
				