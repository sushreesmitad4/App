/**
 * 
 */
package com.tarang.ewallet.crypt.dao;

import java.sql.SQLException;

/**
 * @author  : prasadj
 * @date    : Nov 21, 2012
 * @time    : 2:04:02 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
public interface CryptDao {

	String readKey() throws SQLException;
}
