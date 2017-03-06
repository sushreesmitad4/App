package com.tarang.ewallet.common.repository;

import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;

/**
 * @Author : kedarnath
 * @Date : Oct 17, 2012
 * @Time : 4:51:52 PM
 * @Version : 1.0
 * @Comments: Repository interface to provide Ewallet Login Services 
 * for Admin, Merchant and Customer.
 */
public interface LoginRepository {
	/**
	 * Service to login with email and password
	 * 
	 * @param email
	 * @param password
	 * @return AuthResponseData - token and user role
	 * @throws WalletException
	 */
	Authentication login(String email, String password, String userType, Long typeOfRequest)
			   throws WalletException;

	/**
	 * @param forgotPasswordDto
	 * @return Boolean
	 * @throws WalletException
	 */
	Boolean forgotPassword(ForgotPasswordDto forgotPasswordDto,String personName)throws WalletException;
	
	/**
	 * @param email
	 * @param userType
	 * @return Boolean
	 * @throws WalletException
	 */
	Boolean logout(String email, String userType)throws WalletException;
	/**
	 * @param changePasswordDto
	 * @return Boolean
	 * @throws WalletException
	 */
	Boolean changePassword(ChangePasswordDto changePasswordDto)throws WalletException;
	/**
	 * Service to login with email and mPin
	 * @param email
	 * @param mPin
	 * @return AuthResponseData - token and user role
	 * @throws WalletException
	 */
	Authentication loginWithDevice(String email, String mPin, String userType,
			Long typeOfRequest) throws WalletException;


	/**
	 * Service to change the MPIN for mobile user
	 * @param email
	 * @param userType
	 * @param oldMpin
	 * @param newMPin
	 * @param personName
	 * @param userTypeName
	 * @return
	 * @throws WalletException
	 */
	Boolean changeMPin(String email, String userType, String oldMpin, String newMPin, String personName, String userTypeName) throws WalletException;
	
	/**
	 * Service to get forgotten MPIN as email
	 * @param email
	 * @param userType
	 * @param msisdnNumber
	 * @param simNumber
	 * @param imeiNumber
	 * @param question
	 * @param hint
	 * @param personName
	 * @param userTypeName
	 * @return
	 * @throws WalletException
	 */
	Boolean forgotMpin(String email, String userType, String msisdnNumber, 
			String simNumber, String imeiNumber, Long question, String hint, 
			String personName, String userTypeName)	throws WalletException;
	
	/**
	 * Service to newMpin with authenticationId, newMPin
	 * @param newMPin
	 * @return Boolean 
	 * @throws WalletException
	 */
	Boolean newMPin(String email, String userType, String newMPin, String personName, String userTypeName) throws WalletException;
}
