/**
 * 
 */
package com.tarang.ewallet.email.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.tarang.ewallet.model.EmailTemplate;
import com.tarang.ewallet.util.CommonProjectUtil;


/**
 * @author  : prasadj
 * @date    : Nov 20, 2012
 * @time    : 8:29:05 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class EmailTemplateUtil implements EmailTemplateConstants {

	private List<EmailTemplate> allTemplates;
	
	private Properties commonProperties;
	
	private static final Logger LOGGER = Logger.getLogger(EmailTemplateUtil.class);
	
	public EmailTemplateUtil(String fileSource, String emailPropsFile){
		loadTemplates(fileSource);
		commonProperties = CommonProjectUtil.loadProperties(emailPropsFile);
	}
	
	public EmailTemplate getTemplate(Long languageId, String name) {
		
		for( EmailTemplate template: allTemplates){
			if( template.getLanguage().equals(languageId) && template.getName().equals(name) ){
				return template;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public String getEmailBodyMessage(Long languageId, String name, Properties dvalues) {
		
		EmailTemplate template = getTemplate(languageId, name);
		String msg = template.constructMail();

		//dynamic properties replacing 
		Enumeration e = dvalues.keys();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = dvalues.getProperty(key);
			msg = msg.replace(getReplacementKey(key), value);
		}
		
		//configurable properties replacing 
		e = commonProperties.keys();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = commonProperties.getProperty(key);
			msg = msg.replace(getReplacementKey(key), value);
		}
		return msg;
	}

	@SuppressWarnings("rawtypes")
	public String getEmailSubjectMessage(Long languageId, String name, Properties dvalues) {
		
		EmailTemplate template = getTemplate(languageId, name);
		String msg = template.getSubject();

		//dynamic properties replacing 
		Enumeration e = dvalues.keys();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = dvalues.getProperty(key);
			msg = msg.replace(getReplacementKey(key), value);
		}
		
		//configurable properties replacing 
		e = commonProperties.keys();
		while(e.hasMoreElements()){
			String key = (String)e.nextElement();
			String value = commonProperties.getProperty(key);
			msg = msg.replace(getReplacementKey(key), value);
		}
		return msg;
	}

	
	private String getReplacementKey(String key){
		return "{" + key + "}";
	}
	private void loadTemplates(String fileSource){
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			InputStream in = new FileInputStream(new File(CommonProjectUtil.getAbsolutePath(fileSource)));
			Document doc = dBuilder.parse(in);
			doc.getDocumentElement().normalize();
			NodeList templates = doc.getElementsByTagName(TEMPLATE);

			allTemplates = new ArrayList<EmailTemplate>();
			
			for (int temp = 0; temp < templates.getLength(); temp++) {
				Element template = (Element)templates.item(temp);
				String templateName = getAttributeValue(NAME, template);
				NodeList emails = template.getElementsByTagName(EMAIL);
				for(int i = 0; i < emails.getLength(); i++){
					Node nNode = emails.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						EmailTemplate emailTemplate = new EmailTemplate();
						emailTemplate.setName(templateName);
						emailTemplate.setLanguage(new Long(getAttributeValue(LANGUAGE_CODE, eElement)));
						emailTemplate.setSubject(getTagValue(SUBJECT, eElement));
						emailTemplate.setGreeting(getTagValue(GREETING, eElement));
						emailTemplate.setBody(getTagValue(BODY, eElement));
						emailTemplate.setSignature(getTagValue(SIGNATURE, eElement));
						emailTemplate.setDisclaimer(getTagValue(DISCLAIMER, eElement));
						allTemplates.add(emailTemplate);
					}
				}
			}
		}
		catch(Exception ex){
			LOGGER.error("loadTemplates", ex);
		}
	}
	
	private String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}

	private String getAttributeValue(String attribute, Element eElement) {
		return eElement.getAttribute(attribute);
	}
	
}
