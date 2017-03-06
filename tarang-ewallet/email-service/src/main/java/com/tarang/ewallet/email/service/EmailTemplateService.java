/**
 * 
 */
package com.tarang.ewallet.email.service;

import java.util.Properties;

import com.tarang.ewallet.model.EmailTemplate;


/**
 * @author  : prasadj
 * @date    : Nov 20, 2012
 * @time    : 8:18:37 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface EmailTemplateService {

	EmailTemplate getTemplate(Long languageId, String templateName);
	
	String getEmailBodyMessage(Long languageId, String templateName, Properties dvalues);
	
	String getEmailSubjectMessage(Long languageId, String templateName, Properties dvalues);

}
