<%-- 
  - Author(s): Kedarnath tArAng Software Technologies
  - Date: June 26, 2013
  - @(#)
  - Description: This page is for common help tipes for all help icone. 
 --%>
 <%@page import="javax.servlet.http.HttpServletRequest, java.util.Locale"%>
<%@ page import="com.tarang.ewallet.walletui.util.HelpLinkConstants, org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%!	  
	public String showHelpTipes(Integer helpTipes, HttpServletRequest request){

		Locale locale = request.getLocale();
		ApplicationContext appcontext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()); 
		StringBuilder sb = new StringBuilder();
		String linkId = "";
		String contentId = "";
		String content = "";
		if(HelpLinkConstants.PASSWORD_TIPS.equals(helpTipes)){	
			linkId = "pwdhelpcomp";
			contentId = "pwdspan";
			sb.append(" <span> ")
				.append( appcontext.getMessage("password.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> ")
				.append(" <li>")
				.append(appcontext.getMessage("password.tips1.lbl", null, locale))
				.append("</li> " )
				.append(" <li>")
				.append(appcontext.getMessage("password.tips2.lbl", null, locale))
				.append("</li> ")
				.append(" <li>")
				.append(appcontext.getMessage("password.tips3.lbl", null, locale))
				.append("</li> ")
				.append(" <li>")
				.append(appcontext.getMessage("password.tips4.lbl", null, locale))
				.append("</li> ")
				.append(" </ul> ");
			content = sb.toString();
		}
		else if(HelpLinkConstants.AVERAGE_TRANSACTION_AMOUNT_TIPS.equals(helpTipes)){
			linkId = "avgtxncomp";
			contentId = "avgtxnspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("avgtxnamount.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul>")
				.append(" <li>")
				.append(appcontext.getMessage("avgtxnamount.tips1.lbl", null, locale))
				.append(" </li> ")
				.append(" <li>")
				.append(appcontext.getMessage("avgtxnamount.tips2.lbl", null, locale))
				.append(" </li> ")
				.append(" </ul> ");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.HIGHEST_MONTHLY_VOLUME_TIPS.equals(helpTipes)){
			linkId = "highestcomp";
			contentId = "highestspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("monthlyvolume.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> ")
				.append("	<li>")
				.append(appcontext.getMessage("monthlyvolume.tips1.lbl", null, locale))
				.append(" </li> ")
				.append("	<li>")
				.append(appcontext.getMessage("monthlyvolume.tips2.lbl", null, locale))
				.append(" </li> ")
				.append(" </ul> ");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.PERCENTAGE_OF_ANNUAL_REVENUE_FROM_ONLINE_SALES_TIPS.equals(helpTipes)){
			linkId = "annualcomp";
			contentId = "annualspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("annualrevenuepopup.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> ")
				.append(" <li>")
				.append(appcontext.getMessage("annualrevenuepopup.tips1.lbl", null, locale))
				.append( " </li> ")
				.append(" </ul> ");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.CARD_VERIFICATION_TIPS.equals(helpTipes)){
			linkId = "cardvercomp";
			contentId = "cardverspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("card.verification.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> ")
				.append(" <li>")
				.append(appcontext.getMessage("card.verification.help.info", null, locale))
				.append(" </li> ")
				.append(" </ul>");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.FEE_SLABS_TIPS.equals(helpTipes)){
			linkId = "slabtipcomp";
			contentId = "slabtipspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("slab.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> " )
				.append(" <li>")
				.append(appcontext.getMessage("slablowerlimit.message1", null, locale))
				.append("</li> " )
				.append(" </ul> ");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.SERVICE_PHONE_NO_TIPS.equals(helpTipes)){
			linkId = "servicephonetipcomp";
			contentId = "servicephonetipspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("service.phone.number.tips.lbl", null, locale))
				.append(" </span> ")
				.append(" <ul> " )
				.append(" <li>")
				.append(appcontext.getMessage("service.phone.number.tip1.lbl", null, locale))
				.append("</li> " )
				.append(" </ul> ");
			content = sb.toString();					
		}
		else if(HelpLinkConstants.EMAIL_HELP_TIPS.equals(helpTipes)){ 
			linkId = "emailcomp";
			contentId = "emailspan";
			sb.append(" <span> ")
				.append(appcontext.getMessage("email.tips.help", null, locale))
			    .append(" </span> ")
			    .append(" <ul> ")
			    .append(" <li>")
			    .append(appcontext.getMessage("email.helps.tips1.lbl", null, locale))
			    .append("</li> " )
			    .append(" <li>")
			    .append(appcontext.getMessage("email.helps.tips2.lbl", null, locale))
			    .append("</li> ")
			    .append(" <li>")
			    .append(appcontext.getMessage("email.helps.tips3.lbl", null, locale));
			content = sb.toString();
		}
		else {
			return "";
		}
		sb = new StringBuilder();
		sb.append(" <script type='text/javascript'> ")
		.append(" jQuery(function( $ ){ ")
		.append("	showHelpText('")
		.append(linkId)
		.append("', '")
		.append(contentId + "'); ")
		.append("	}); ")
		.append(" </script> ")
		.append("	<div> " )
		.append(" <a id='")
		.append(linkId)
		.append("' href='javascript:void(0)'><img  src='/wallet-ui/img/help_circle.png' class='help-icon' /> </a> ")
		.append(" <div id='")
		.append(contentId)
		.append("' class='help-icon-content'> ")
		.append(content)
		.append(" </div> ")
		.append(" </div> ");
		return sb.toString();
}
%>

				