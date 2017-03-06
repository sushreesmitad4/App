/**
 * 
 */
package com.tarang.ewallet.crypt.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import com.tarang.ewallet.crypt.dao.CryptDao;


/**
 * @author : prasadj
 * @date : Nov 21, 2012
 * @time : 2:05:09 PM
 * @version : 1.0.0
 * @comments:
 * 
 */
public class CryptDaoImpl implements CryptDao {

	private JdbcOperations jdbcOperations;

	private static final String QUERY_KEY1 = "select * from walletkey";
	private static final String KEY_COLUMN = "wallet";
	private static final int FIRST_REC = 0;

	public CryptDaoImpl(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	public String readKey() throws SQLException {
		
		return jdbcOperations.query(QUERY_KEY1, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return (String)rs.getString(KEY_COLUMN);
			}
		}).get(FIRST_REC);
	}

}
