package com.tarang.ewallet.walletui.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.walletui.controller.AttributeConstants;


public class WalletSessionListener implements HttpSessionListener, AttributeConstants {
	
	private static final Logger LOGGER = Logger.getLogger(WalletSessionListener.class);

	private static int totalActiveSessions;
	 
    public static int getTotalActiveSession() {
          return totalActiveSessions;
    }
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.debug("WalletSessionListener :: Session ID  created at "+ event.getSession().getId()+ SPACE_STRING + new Date());
		totalActiveSessions++;
		LOGGER.debug("sessionCreated - add one session into counter");	
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		LOGGER.debug("WalletSessionListener :: sessionDestroyed :: Session ID  destroyed at "+ event.getSession().getId()+ SPACE_STRING + new Date());
		totalActiveSessions--;
		HttpSession session = event.getSession();
		LOGGER.debug("sessionDestroyed - deduct one session from counter");
        if(session.getAttribute(USER_ID) != null && session.getAttribute(AUTHENTICATION_ID) != null){
        	
        	ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    		
        	CommonService commonService = (CommonService) applicationContext.getBean("commonService");
        	
        	String emailid = session.getAttribute(USER_ID).toString();
        	Long authenticationId = Long.parseLong(session.getAttribute(AUTHENTICATION_ID).toString());
        	String userType = session.getAttribute(USER_TYPE).toString();
        	
        	LOGGER.debug(emailid + AUTHENTICATION_ID  + " : "+authenticationId);
			try {
				commonService.validateUserSession(authenticationId, userType);
				LOGGER.debug("WalletSessionListener:: Total session created : " + totalActiveSessions);
				session.invalidate();
			} catch (Exception e) {
				LOGGER.debug(emailid + AUTHENTICATION_ID +" : "+authenticationId);
				LOGGER.error(e.getMessage() ,  e);
			}
        } else{
        	LOGGER.debug("WalletSessionListener ::Else block :: Unable to update user account");
        }
	}
}
