package com.tarang.ewallet.accountcloser.util;

import java.util.Comparator;
import java.util.Date;

import com.tarang.ewallet.model.AccountCloserMessage;


public class ShortAccountCloserMessage implements Comparator<AccountCloserMessage>{

	@Override
	public int compare(AccountCloserMessage o1, AccountCloserMessage o2) {
		
		Date d1= o1.getMessageDate();
		Date d2= o2.getMessageDate();
		
		//ascending order
		//return d1.compareTo(d2);
 
		//descending order
		return d2.compareTo(d1);
	}

	

}
