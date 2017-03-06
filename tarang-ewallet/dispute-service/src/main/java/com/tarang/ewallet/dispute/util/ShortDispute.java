package com.tarang.ewallet.dispute.util;

import java.util.Comparator;
import java.util.Date;

import com.tarang.ewallet.model.DisputeMessage;


public class ShortDispute implements Comparator<DisputeMessage>{

	@Override
	public int compare(DisputeMessage o1, DisputeMessage o2) {
		
		Date d1= o1.getCreationDate();
		Date d2= o2.getCreationDate();
		
		//ascending order
		//return d1.compareTo(d2);
 
		//descending order
		return d2.compareTo(d1);
	}

	

}
