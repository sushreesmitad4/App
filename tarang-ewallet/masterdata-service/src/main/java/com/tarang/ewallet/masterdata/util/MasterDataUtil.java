/**
 * 
 */
package com.tarang.ewallet.masterdata.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tarang.ewallet.model.BankAccountType;
import com.tarang.ewallet.model.Region;
import com.tarang.ewallet.model.ReportType;


/**
 * @author  : prasadj
 * @date    : Nov 28, 2012
 * @time    : 11:04:08 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class MasterDataUtil {

    public static Map<Long, Map<Long, String>> getRegionsMap(List<Region> list){
    	Map<Long, Map<Long,String>> totalMap = new HashMap<Long, Map<Long,String>>();
    	Map<Long, String> map = null;
    	if(list != null){
    		for(Region region: list){
    			map = totalMap.get(region.getCountryId());
    			if(map == null) {
    				map = new HashMap<Long, String>();
    				totalMap.put(region.getCountryId(), map);
    			}
				map.put(region.getId(), region.getRegion());
			}
    	}
    		
    	return totalMap;
    }
    
    public static Map<Long, Map<Long, String>> getBankAccountTypesMap(List<BankAccountType> list){
    	Map<Long, Map<Long,String>> totalMap = new HashMap<Long, Map<Long,String>>();
    	Map<Long, String> map = null;
    	if(list != null){
    		for(BankAccountType type: list){
    			map = totalMap.get(type.getCountryId());
    			if(map == null) {
    				map = new HashMap<Long, String>();
    				totalMap.put(type.getCountryId(), map);
    			}
				map.put(type.getId(), type.getType());
			}
    	}
    		
    	return totalMap;
    }

	public static Map<Long, Map<Long, String>> getReportTypesMap(List<ReportType> list) {
		Map<Long, Map<Long,String>> totalMap = new HashMap<Long, Map<Long,String>>();
		Map<Long, String> map = null;
		if(list != null){
    		for(ReportType type: list){
    			map = totalMap.get(type.getUserTypeId());
    			if(map == null) {
    				map = new HashMap<Long, String>();
    				totalMap.put(type.getUserTypeId(), map);
    			}
				map.put(type.getId(), type.getName());
			}
    	}
		return totalMap;
	}

}
