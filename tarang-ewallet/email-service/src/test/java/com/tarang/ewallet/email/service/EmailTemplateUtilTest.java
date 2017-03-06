package com.tarang.ewallet.email.service;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.email.util.EmailTemplateUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml" })

public class EmailTemplateUtilTest {
	@Autowired
	private EmailTemplateUtil emailTemplateUtil;
	
	@Test
	public void getEmailMessageTest()
	{
		Properties dvalues=new Properties();
		dvalues.put("customerName","sivaram");
		dvalues.put("userId","siva@gmail.com");
		dvalues.put("newPassword","aaaa@1s");
		String s=emailTemplateUtil.getEmailBodyMessage(1L,EmailServiceConstants.RESET_PASSWORD, dvalues);
		System.out.println(s);
		Assert.notNull(s);
		
	}

	@Test
	public void registrationEmailTestTest()
	{
		Properties dvalues=new Properties();
		dvalues.put("customerName","sivaram");
		dvalues.put("authenticationID","55");
		String s=emailTemplateUtil.getEmailBodyMessage(1L,EmailServiceConstants.CUSTOMER_REGISTRATION, dvalues);
		System.out.println(s);
		Assert.notNull(s);
		
	}

}
