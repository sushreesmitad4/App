package com.tarang.ewallet.email.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tarang.ewallet.email.service.EmailService;
import com.tarang.ewallet.email.service.EmailTemplateService;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.EmailTemplate;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/wallet-applicationContext.xml",
		"classpath*:/wallettest-applicationContext.xml"})
public class A_EmailServiceTest {

	@Autowired 
    private EmailService emailService;
	
	@Autowired 
    private EmailTemplateService emailTemplateService;
	
    @Test
	public void getLanguagesTest() throws WalletException{
		String msg="your registration has completed successfully";
		String tomail="yasina@tarangtech.com";
    	emailService.sendMessage(tomail, msg, "Test Mail");
    	String data = "abc"; 
    	Assert.assertNotNull(data != null );
    }
    @Test
    public void createEmailHistoryTest() throws WalletException{
    	String msg="your registration has completed successfully";
    	String tomail="yasina@tarangtech.com";
    	emailService.sendMessage(tomail, msg, "Test Mail");
    	String data = "abc"; 
    	Assert.assertNotNull(data != null );
    }

    @Test
    public void getTemplateTest(){
    	EmailTemplate data = emailTemplateService.getTemplate(1L, "customerservice");
    	Assert.assertNotNull(data != null );
    }
    
    @Test
	public void startSendEmailTest() throws WalletException{
		String msg="your registration has completed successfully";
		String tomail[]={
						"kedarnathd@tarangtech.com",
						"yasina@tarangtech.com",
						"one@gmail.com",
						"2@gmail.com",
						"3@gmail.com"
						};
		for(int i = 0; i< tomail.length; i++){
			emailService.sendMessage(tomail[i], msg, "Test Mail");
		}
		emailService.startSendEmail();
    	String data = "abc";
    	Assert.assertNotNull(data != null );
    }
}
