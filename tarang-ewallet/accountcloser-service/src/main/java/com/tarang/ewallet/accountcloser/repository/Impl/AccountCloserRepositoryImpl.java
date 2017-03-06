package com.tarang.ewallet.accountcloser.repository.Impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.tarang.ewallet.accountcloser.dao.AccountCloserDao;
import com.tarang.ewallet.accountcloser.repository.AccountCloserRepository;
import com.tarang.ewallet.accountcloser.util.AccountCloserStatus;
import com.tarang.ewallet.accountcloser.util.AccountCloserUtil;
import com.tarang.ewallet.accountcloser.util.ShortAccountCloserMessage;
import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.common.util.UserStatusConstants;
import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.AccountCloserMessageDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.model.AccountCloser;
import com.tarang.ewallet.model.AccountCloserMessage;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.util.DateConvertion;


public class AccountCloserRepositoryImpl implements AccountCloserRepository, AccountCloserStatus{

	private AccountCloserDao accountCloserDao; 
	private CommonService commonService; 
	
	public AccountCloserRepositoryImpl(AccountCloserDao accountCloserDao,CommonService commonService) {
		this.accountCloserDao = accountCloserDao;
		this.commonService = commonService;
	}
	
	@Override
	public AccountCloserDto createAccountCloser(AccountCloserDto accountCloserDto)throws WalletException {
		return AccountCloserUtil.getDto( accountCloserDao.createAccountCloser(AccountCloserUtil.getModelObjectToCreate(accountCloserDto)) );
	}

	@Override
	public AccountCloserDto getAccountCloserById(Long id) throws WalletException {
		return AccountCloserUtil.getDto(accountCloserDao.getAccountCloserById(id));
	}

	@Override
	public AccountCloserDto addMessage(Long accountCloserId, String message, Long creator) throws WalletException {
        AccountCloserMessage accountCloserMessage = new AccountCloserMessage(); 
		accountCloserMessage.setCreator(creator);
		accountCloserMessage.setMessage(message);
		accountCloserMessage.setMessageDate(new Date());
		AccountCloser accountCloser = accountCloserDao.getAccountCloserById(accountCloserId);
		if(accountCloser.getStatus().equals(REJECT)){
			accountCloser.setStatus(PENDING);
		}
		accountCloserMessage.setAccountCloser(accountCloser);
		accountCloserDao.addMessage(accountCloserMessage);
		return AccountCloserUtil.getDto(accountCloserDao.getAccountCloserById(accountCloserId));
	}
	
	
	@Override
	public AccountCloserDto addMessageByAdmin(Long accountCloserId, String message, Long creator, Long status) throws WalletException {
        AccountCloserMessage accountCloserMessage = new AccountCloserMessage(); 
		accountCloserMessage.setCreator(creator);
		accountCloserMessage.setMessage(message);
		accountCloserMessage.setMessageDate(new Date());
		AccountCloser accountCloser = accountCloserDao.getAccountCloserById(accountCloserId);
		accountCloser.setStatus(status);
		accountCloserMessage.setAccountCloser(accountCloser);
		accountCloserDao.addMessage(accountCloserMessage);
		if(status.equals(AccountCloserStatus.APPROVAL)){
			Authentication  authentication = commonService.getAuthentication(accountCloser.getAuthId());
			authentication.setStatus(UserStatusConstants.LOCKED);
			commonService.updateAuthentication(authentication);
		}
		
		return AccountCloserUtil.getDto(accountCloserDao.getAccountCloserById(accountCloserId));
	}
	
	@Override
	public AccountCloserDto getAccountCloserByUser(Long authId) throws WalletException {
		return AccountCloserUtil.getDto( accountCloserDao.getAccountCloserByUser(authId) );
	}

	@Override
	public List<AccountCloserDto> getAccountCloserList(Integer noOfRecords,
			Long languageId, Date fromDate, Date toDate, String userType)
			throws WalletException {
		return accountCloserDao.getAccountCloserList(noOfRecords, languageId, fromDate, toDate, userType);
	}

	@Override
	public AccountCloserDto getAccountCloserForView(Long accountCloserId,
			Long languageId) throws WalletException {
		
		AccountCloserDto accountCloserDto = accountCloserDao.getAccountCloserForView(accountCloserId, languageId);
		if(accountCloserDto != null){
			accountCloserDto.setId(accountCloserId);
			AccountCloser  accountCloser = accountCloserDao.getAccountCloserById(accountCloserId);
			if(accountCloser != null){
				Set<AccountCloserMessage> setMessages = accountCloser.getMessages();
				List<AccountCloserMessage> listMessages = new ArrayList<AccountCloserMessage>(); 
				listMessages.addAll(setMessages);
				Collections.sort(listMessages, new ShortAccountCloserMessage());
				
				List<AccountCloserMessageDto> listDto = new ArrayList<AccountCloserMessageDto>();
				accountCloserDto.setAuthId(accountCloser.getAuthId());
				AccountCloserMessageDto messageDto = null;
				for(AccountCloserMessage message: listMessages){
					if(message == null){
						continue;
					}
					messageDto = new AccountCloserMessageDto();
					listDto.add(messageDto);
					messageDto.setCreator(message.getCreator());
					messageDto.setMessage(message.getMessage());
					messageDto.setMessageDate(DateConvertion.dateTimeToString(message.getMessageDate()));
				}
		 
				accountCloserDto.setMessageList(listDto);
			}
		}
		return accountCloserDto;
		
	}

	@Override
	public Boolean getAccountCloserStatusById(Long authId) throws WalletException {
		return accountCloserDao.getAccountCloserStatusById(authId);
	}

	@Override
	public AccountCloserDto addMessageByScheduler(Long accountCloserId, String message, Long creator, Long status) throws WalletException {
		AccountCloserMessage accountCloserMessage = new AccountCloserMessage(); 
		accountCloserMessage.setCreator(creator);
		accountCloserMessage.setMessage(message);
		accountCloserMessage.setMessageDate(new Date());
		AccountCloser accountCloser = accountCloserDao.getAccountCloserById(accountCloserId);
		accountCloser.setStatus(status);
		accountCloserMessage.setAccountCloser(accountCloser);
		accountCloserDao.addMessage(accountCloserMessage);
		/* bug fix no: 4366 is commented, it is taken care at client side */
		Authentication closedAuth = commonService.getAuthentication(accountCloser.getAuthId()); 
		//Account has been closed, so mark as inactive user 
		closedAuth.setActive(Boolean.FALSE);
		commonService.updateAuthentication(closedAuth);
		return AccountCloserUtil.getDto(accountCloserDao.getAccountCloserById(accountCloserId));
	}

	@Override
	public List<Long> getApprovalAccountClosers(Long status, Date date) throws WalletException {
		return	accountCloserDao.getApprovalAccountClosers(status, date);
	}

	

	

}
