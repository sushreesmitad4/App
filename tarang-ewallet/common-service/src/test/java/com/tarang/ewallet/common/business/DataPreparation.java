/**
 * 
 */
package com.tarang.ewallet.common.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.model.NonRegisterWallet;


/**
 * @author prasadj
 *
 */
public class DataPreparation {

	public static List<Object> getDataPreparation(){
		
		List<Object> list = new ArrayList<Object>();
		
    	
    	
    	return list;
	}
	
	public static NonRegisterWallet getNonRegisterWallet(){ 
		NonRegisterWallet nonRegisterWallet = new NonRegisterWallet();
		nonRegisterWallet.setEmail("kedarnathd@tarangtech.com");
		nonRegisterWallet.setCurrency(1L);
		nonRegisterWallet.setCreationDate(new Date());
		nonRegisterWallet.setAmount(1000.00);
		nonRegisterWallet.setRegister(1L);
		nonRegisterWallet.setTxnId(1L);
		return nonRegisterWallet;
	}
	
	public static Feedback getFeedbackData(){
		Feedback feedback = new Feedback();
		feedback.setLanguageId(1L);
		feedback.setSubject("You subject");
		feedback.setReciverName("reciverName");
		feedback.setUserMail("kedarnathd@tarangtech.com");
		feedback.setQueryTypeName("Query");
		feedback.setMessage("Old Message");
		feedback.setResponseMessage("responseMessage");
		feedback.setDateAndTime(new Date());
		return feedback;
	}
}
