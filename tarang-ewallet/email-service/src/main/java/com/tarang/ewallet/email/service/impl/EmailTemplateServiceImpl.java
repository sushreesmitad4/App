/**
 * 
 */
package com.tarang.ewallet.email.service.impl;

import java.util.Properties;

import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailTemplateUtil;
import com.tarang.ewallet.model.EmailTemplate;


/**
 * @author  : prasadj
 * @date    : Nov 20, 2012
 * @time    : 8:24:55 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class EmailTemplateServiceImpl implements EmailTemplateService {

	private EmailTemplateUtil templateUtil;
	
	public EmailTemplateServiceImpl(String templateFile, String props){
		templateUtil = new EmailTemplateUtil(templateFile, props);
	}

	/* (non-Javadoc)
	 * @see com.tarang.ewallet.email.service.EmailTemplateService#getTemplate(java.lang.Long, java.lang.String)
	 */
	@Override
	public EmailTemplate getTemplate(Long languageId, String templateName) {
		return templateUtil.getTemplate(languageId, templateName);
	}
	
	@Override
	public String getEmailBodyMessage(Long languageId, String templateName, Properties dvalues) {
		return templateUtil.getEmailBodyMessage(languageId, templateName, dvalues);
	}

	@Override
	public String getEmailSubjectMessage(Long languageId, String templateName, Properties dvalues) {
		return templateUtil.getEmailSubjectMessage(languageId, templateName, dvalues);
	}
	
	
}
