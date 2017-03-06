package com.tarang.ewallet.email.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.email.util.EmailServiceConstants;
import com.tarang.ewallet.model.EmailTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml" })
public class EmailTemplateServiceTest {

	@Autowired
	private EmailTemplateService emailTemplateService;

	@Test
	public void getTemplateTest() {
		EmailTemplate data = emailTemplateService.getTemplate(1L, EmailServiceConstants.RESET_PASSWORD);
		Assert.assertNotNull(data != null);
	}
}
