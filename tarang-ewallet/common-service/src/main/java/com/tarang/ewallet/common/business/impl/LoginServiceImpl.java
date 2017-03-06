/**
 * 
 */
package com.tarang.ewallet.common.business.impl;

import com.tarang.ewallet.common.business.LoginService;
import com.tarang.ewallet.common.repository.LoginRepository;
import com.tarang.ewallet.dto.ChangePasswordDto;
import com.tarang.ewallet.dto.ForgotPasswordDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.Authentication;


/**
 * @Author : kedarnath
 * @Date : Oct 17, 2012
 * @Time : 4:51:52 PM
 * @Version : 1.0
 * @Comments: Service Implementation class to provide Ewallet Login Services 
 * for admin, merchant and customer.
 */
public class LoginServiceImpl implements LoginService {

	private LoginRepository loginRepository;

	public LoginServiceImpl(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Override
	 public Authentication login(String email, String password, String userType, Long typeOfRequest)
	   throws WalletException {
	  return loginRepository.login(email, password, userType, typeOfRequest);
	 }

	@Override
	public Boolean forgotPassword(ForgotPasswordDto forgotPasswordDto,String personName)
			throws WalletException {
		return loginRepository.forgotPassword(forgotPasswordDto, personName);
	}

	@Override
	public Boolean logout(String email, String userType) throws WalletException {
		return loginRepository.logout(email, userType);
	}

	@Override
	public Boolean changePassword(ChangePasswordDto changePasswordDto)
			throws WalletException {
		return loginRepository.changePassword(changePasswordDto);
	}

	@Override
	public Authentication loginWithDevice(String email, String mPin,
			String userType, Long typeOfRequest) throws WalletException {
		return loginRepository.loginWithDevice(email, mPin, userType, typeOfRequest);
	}

	@Override
	public Boolean changeMPin(String email, String userType, String oldMpin, 
			String newMPin, String personName, String userTypeName) throws WalletException {
		return loginRepository.changeMPin(email, userType, oldMpin, newMPin, personName, userTypeName);
	}

	@Override
	public Boolean forgotMpin(String email, String userType, String msisdnNumber, 
			String simNumber, String imeiNumber, Long question, String hint, 
			String personName, String userTypeName)	throws WalletException {
		return loginRepository.forgotMpin(email, userType, msisdnNumber, simNumber, imeiNumber, 
				question, hint, personName, userTypeName);
	}

	@Override
	public Boolean newMPin(String email, String userType, String newMPin, String personName, String userTypeName)
			throws WalletException {
		return loginRepository.newMPin(email, userType, newMPin, personName, userTypeName);
	}

}
