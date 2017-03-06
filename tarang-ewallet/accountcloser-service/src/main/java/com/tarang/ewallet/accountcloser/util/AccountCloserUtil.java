/**
 * 
 */
package com.tarang.ewallet.accountcloser.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tarang.ewallet.dto.AccountCloserDto;
import com.tarang.ewallet.dto.AccountCloserMessageDto;
import com.tarang.ewallet.model.AccountCloser;
import com.tarang.ewallet.model.AccountCloserMessage;
import com.tarang.ewallet.util.DateConvertion;


/**
 * @author  : prasadj
 * @date    : Feb 20, 2013
 * @time    : 11:29:19 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class AccountCloserUtil {

	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;

	public static AccountCloser getModelObjectToCreate(AccountCloserDto dto){
		AccountCloser accountCloser = new AccountCloser();
		accountCloser.setAuthId(dto.getAuthId());
		accountCloser.setUserType(dto.getUserType());
		accountCloser.setStatus(dto.getStatus());
		accountCloser.setRequestedDate(new Date());
		
		Set<AccountCloserMessage> listOfMessages = new HashSet<AccountCloserMessage>();
		AccountCloserMessage acm = new AccountCloserMessage(); 
		acm.setCreator(dto.getCreator());
		acm.setMessage(dto.getMessage());
		acm.setMessageDate(new Date());
		acm.setAccountCloser(accountCloser);
		listOfMessages.add(acm);
		accountCloser.setMessages(listOfMessages);
		return accountCloser;
	}
	
	public static AccountCloserDto getDto(AccountCloser model){
		
		if(model != null){
			AccountCloserDto dto = new AccountCloserDto();
			dto.setId(model.getId());
			dto.setAuthId(model.getAuthId());
			dto.setUserType(model.getUserType());
			dto.setStatus(model.getStatus());
			dto.setRequestedDate(model.getRequestedDate());
			Set<AccountCloserMessage> setMessages = model.getMessages();
			List<AccountCloserMessage> listMessages = new ArrayList<AccountCloserMessage>(); 
			listMessages.addAll(setMessages);
			Collections.sort(listMessages, new ShortAccountCloserMessage());
			List<AccountCloserMessageDto> messageDtos = new ArrayList<AccountCloserMessageDto>();
			AccountCloserMessageDto messageDto = null;
			for(AccountCloserMessage message: listMessages){
				if(message == null){
					continue;
				}
				messageDto = new AccountCloserMessageDto();
				messageDtos.add(messageDto);
				messageDto.setCreator(message.getCreator());
				messageDto.setMessage(message.getMessage());
				messageDto.setMessageDate(DateConvertion.dateTimeToString(message.getMessageDate()));
			}
			dto.setMessageList(messageDtos);
			return dto;
		}
		return null;
	}
	
	public static List<AccountCloserDto> getDtoObjectsForGrid(List<Object[]> list){

		List<AccountCloserDto> rst = new ArrayList<AccountCloserDto>();
		AccountCloserDto dto = null;
		for(Object[] objects: list){
			dto = new AccountCloserDto();
			
			BigInteger idBigInteger=(BigInteger)objects[ZERO];
			Long id = idBigInteger.longValue();
			String emailId = (String)objects[ONE];
			Date requestedDate = (Date)objects[TWO];
			Date registrationDate = (Date)objects[THREE];
			Date lastTransactionDate = (Date)objects[FOUR];
			String status = (String)objects[FIVE];
			
			dto.setId(id);
			dto.setEmailId(emailId);
			dto.setRequestedDate(requestedDate);
			dto.setRegistrationDate(registrationDate);
			dto.setLastTransactionDate(lastTransactionDate);
			dto.setStatusStr(status);
			
			rst.add(dto);
			
		}
		return rst;
	}
	
	
	public static List<AccountCloserDto> getDtoObjectsForView(List<Object[]> list){

		List<AccountCloserDto> rst = new ArrayList<AccountCloserDto>();
			AccountCloserDto dto = null;
			for(Object[] objects: list){
				dto = new AccountCloserDto();
				
			String emailId = (String)objects[ZERO];
			String usertype = (String)objects[ONE];
			String personOrOrganisationName = (String)objects[TWO];
			Date requestedDate = (Date)objects[THREE];
			Date registrationDate = (Date)objects[FOUR];
			Date lastTransactionDate = (Date)objects[FIVE];
			String userStatus = (String)objects[SIX];
			String accountCloserStatus = (String)objects[SEVEN];
				
			dto.setEmailId(emailId);
			dto.setUserType(usertype);
			dto.setPersonOrOrganisationName(personOrOrganisationName);
			dto.setRequestedDate(requestedDate);
			dto.setRegistrationDate(registrationDate);
			dto.setLastTransactionDate(lastTransactionDate);
			dto.setUserStatus(userStatus);
			dto.setStatusStr(accountCloserStatus);
				
			rst.add(dto);
				
			}
			return rst;
	}
	
	public static List<AccountCloserDto> getGridDataForAccountCloserList(List<Object[]> list){
		List<AccountCloserDto> accountCloserDtoList = new ArrayList<AccountCloserDto>();
		  AccountCloserDto accountCloserDto = null;
			for(Object[] objects: list){
				accountCloserDto = new AccountCloserDto();
				accountCloserDto.setId(((BigDecimal)(objects[0])).longValue());
				accountCloserDto.setEmailId((String)objects[1]);
				accountCloserDto.setRequestedDate((Date)objects[2]);
				accountCloserDto.setRegistrationDate((Date)objects[3]);
				accountCloserDto.setLastTransactionDate((Date)objects[4]);
				accountCloserDto.setStatusStr(((String)objects[5]));
				accountCloserDtoList.add(accountCloserDto);
			}	
			return accountCloserDtoList;
	}

	public static List<AccountCloserDto> getGridDataForgetAccountCloserForView(List<Object[]> list){
		  List<AccountCloserDto> accountCloserDtoList = new ArrayList<AccountCloserDto>();
		  AccountCloserDto accountCloserDto = null;
			for(Object[] objects: list){
				accountCloserDto = new AccountCloserDto();
				accountCloserDto.setEmailId((String)objects[0]);
				accountCloserDto.setUserType((String)objects[1]);
				accountCloserDto.setPersonOrOrganisationName((String)objects[2]);
				accountCloserDto.setRequestedDate((Date)objects[3]);
				accountCloserDto.setRegistrationDate((Date)objects[4]);
				accountCloserDto.setLastTransactionDate((Date)objects[5]);
				accountCloserDto.setUserStatus((String)objects[6]);
				accountCloserDto.setStatusStr((String)objects[7]);
				accountCloserDtoList.add(accountCloserDto);
			}
		 return accountCloserDtoList;
	}
}
