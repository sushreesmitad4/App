package com.tarang.ewallet.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;


/**
* @author  : prasadj
* @date    : Oct 10, 2012
* @time    : 8:58:31 AM
* @version : 1.0.0
* @comments: Maps a jQgrid JSON query to a {@link JqgridFilter} instance
*
*/
public class JqgridObjectMapper {
	
	private static final Logger LOGGER = Logger.getLogger(JqgridObjectMapper.class);
	
	public static JqgridFilter map(String jsonString) {
		
    	if (jsonString != null) {
        	ObjectMapper mapper = new ObjectMapper();
        	
        	try {
				return mapper.readValue(jsonString, JqgridFilter.class);
        	} catch (Exception e) {
        		LOGGER.error(e.getMessage(), e);
			}
    	}
		return null;
	}
}
