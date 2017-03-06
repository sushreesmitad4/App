package com.tarang.ewallet.walletui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuUtils implements MenuConstants{
	
	private static Map<Long, List<Long>> menuMap = new HashMap<Long, List<Long>>();
	
	static {
		
		//dashboard
		List<Long> l1 = new ArrayList<Long>();
		l1.add(DASHBOARD_MI);
		menuMap.put(DASHBOARD_MI, l1);  
		
		//For Management
		List<Long> l2 = new ArrayList<Long>();
		l2.add(ADMIN_USER_MANAGEMENT_MI); 
		l2.add(ADMIN_ROLE_MANAGEMENT_MI); 
		l2.add(MERCHANT_MANAGEMENT_MI); 
		l2.add(CUSTOMER_MANAGEMENT_MI); 
		l2.add(TAX_MANAGEMENT_MI); 
		l2.add(FEE_MANAGEMENT_M); 
		l2.add(TRANSACTION_THRESHOLD_MI);
		l2.add(WALLET_THRESHOLD);
		l2.add(ACCOUNT_CLOSURE_MANAGEMENT);
		l2.add(FEE_MANAGEMENT_F_MI); 
		l2.add(FEE_MANAGEMENT_NF_MI); 
		l2.add(FEE_MANAGEMENT_NFV_MI);
		menuMap.put(MANAGEMENT_M, l2);  

		List<Long> l2a = new ArrayList<Long>();
		l2a.add(FEE_MANAGEMENT_F_MI); 
		l2a.add(FEE_MANAGEMENT_NF_MI); 
		l2a.add(FEE_MANAGEMENT_NFV_MI);
		menuMap.put(FEE_MANAGEMENT_M, l2a);  

	    //For Transaction Menu
		List<Long> l3 = new ArrayList<Long>();
		l3.add(RECOVER_MONEY_MI); 
		l3.add(REFUND_MI); 
 		menuMap.put(TRANSACTION_MI, l3); 
		
		//For Transaction History
		List<Long> l4 = new ArrayList<Long>();
		l4.add(DISPUTE);
		menuMap.put(DISPUTE, l4); 
		
		//For Profile
		List<Long> l5 = new ArrayList<Long>();
		l5.add(CHANGE_PASSWORD_MI); 
		l5.add(PREFERENCES_MI); 
		menuMap.put(PROFILE_M, l5); 
		
		//For User Reports
		List<Long> l6 = new ArrayList<Long>();
		l6.add(USER_REPORTS);
		menuMap.put(USER_REPORTS, l6); 
		
		//For User Reports
		List<Long> l7 = new ArrayList<Long>();
		l7.add(QUERY_FEEDBACK);
		menuMap.put(QUERY_FEEDBACK, l7); 
		
		//For Reconciliation
         List<Long> l8 = new ArrayList<Long>();
         l8.add(RECONCILIATION);
         menuMap.put(RECONCILIATION, l8);
         
       //For Reconciliation
         List<Long> l9 = new ArrayList<Long>();
         l9.add(SETUP);
         menuMap.put(SETUP, l9);
	}	
		
	public static Boolean  checkParent(Long menuId, List<Long> selectedList){
		List<Long> parentChildList = new ArrayList<Long>();
		parentChildList = menuMap.get(menuId);
		Boolean displayMenu = false;
		/*If session out list is null*/
		if(selectedList != null){
			for(int j = 0; j < selectedList.size(); j++){
				for (int i = 0; i < parentChildList.size(); i++) {
					if (selectedList.get(j).equals( parentChildList.get(i)) ){
						displayMenu = true;
						return displayMenu;
					}
				} 
			}
		}
		return displayMenu;
	}
	 
	public static Boolean  checkChild(Long menuId,List<Long> selectedList){
		Boolean displayMenu = false; 
		for(int j = 0; j < selectedList.size(); j++){
			if ( selectedList.get(j).equals(menuId) ){
				displayMenu = true;
		 		return displayMenu;
			}
		}
		return displayMenu;
	} 
	
	public static List<String> adminMenuDetailsList(String menuDetails){	
		String[] menuDetailsArray = menuDetails.split(":");
		List<String> menuList = new ArrayList<String>();		
		for(int i = 0; i < menuDetailsArray.length; i++){
			menuList.add(menuDetailsArray[i]);
		}				
		return menuList;		
	}
	
	public static Boolean isAdminAccessRole(List<String> menuDetails, Long menuAction, Long menuPermission){
		String menu = menuAction + "_" + menuPermission;
		if(menuDetails.contains(menu)){
			return Boolean.TRUE;
		} else{
			return Boolean.FALSE;
		}		
	}

}