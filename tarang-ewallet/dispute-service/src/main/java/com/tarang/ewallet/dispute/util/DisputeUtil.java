/**
 * 
 */
package com.tarang.ewallet.dispute.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.dto.DisputeMessageDto;
import com.tarang.ewallet.model.Dispute;
import com.tarang.ewallet.model.DisputeMessage;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
public class DisputeUtil implements GlobalLitterals{
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;
	private static final int NINE = 9;
	private static final int TEN = 10;
	private static final int ELEVEN = 11;
	private static final int TWELVE = 12;
	private static final int THIRTEEN = 13;
	private static final int FOURTEEN = 14;
	private static final int FIFTEEN = 15;
	
	public static void convertDisputeDtoToDispute(DisputeDto disputeDto, Dispute dispute){
		
		dispute.setTransactionId(Long.valueOf(disputeDto.getTxnid().toString()));
		dispute.setApprovedAmount(disputeDto.getApprovedamount());
		dispute.setApprovedCurrency(Long.valueOf(disputeDto.getApprovedcurrency()));
		dispute.setCreationDate(disputeDto.getCreationdate());
		dispute.setRequestAmount(disputeDto.getRequestamount());
		dispute.setRequestCurrency(Long.valueOf(disputeDto.getRequestcurrency()));
		dispute.setStatus(Long.valueOf(disputeDto.getStatus()));
		dispute.setType(Long.valueOf(disputeDto.getDisputetype()));
		dispute.setUpdationDate(disputeDto.getUpdationdate());
		
		Set<DisputeMessage> msglist = new HashSet<DisputeMessage>();
		DisputeMessage disputeMessage = new DisputeMessage();
		disputeMessage.setDispute(dispute);
		disputeMessage.setCreationDate( new Date());
		disputeMessage.setCreator(1L);
		disputeMessage.setMessage("message_one");
		msglist.add(disputeMessage);
		dispute.setMessages(msglist);
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static void convertDisputeToDisputeDto(Dispute dispute, DisputeDto disputeDto){
		disputeDto.setId(dispute.getId());
		disputeDto.setTransactionId(dispute.getTransactionId());
		disputeDto.setType(dispute.getType());
		disputeDto.setCreationdate(dispute.getCreationDate());
		disputeDto.setUpdationdate(dispute.getUpdationDate());
		disputeDto.setRequestamount(dispute.getRequestAmount());
		disputeDto.setRequestCurrency(dispute.getRequestCurrency());
		disputeDto.setApprovedamount(dispute.getApprovedAmount());
		disputeDto.setApprovedCurrency(dispute.getApprovedCurrency());
		disputeDto.setStatus(dispute.getStatus());
		
		List<DisputeMessageDto> disputeMessageDtoList = new ArrayList<DisputeMessageDto>();
		Set<DisputeMessage> disputeMessageSet = dispute.getMessages();
		List<DisputeMessage> disputeMessageList = new ArrayList<DisputeMessage>();
		disputeMessageList.addAll(disputeMessageSet);
		Collections.sort(disputeMessageList, new ShortDispute());
		Iterator iterator = disputeMessageList.iterator();
	    while(iterator.hasNext()){
	    	DisputeMessageDto disputeMessageDto = new DisputeMessageDto();
	    	DisputeMessage disputeMessage = (DisputeMessage)iterator.next();
	    	disputeMessageDto.setCreationDate(DateConvertion.dateTimeToString(disputeMessage.getCreationDate()));
	    	disputeMessageDto.setMessage(disputeMessage.getMessage());
	    	disputeMessageDto.setCreator(disputeMessage.getCreator());
	    	disputeMessageDtoList.add(disputeMessageDto);
	    }
	    disputeDto.setDtomessages(disputeMessageDtoList);
	}
	
	public static List<DisputeDto> getDtoObjectForGrid(List<Object[]> list){
		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setPayeeemailid((String)objects[ONE]);
			disputeDto.setTransactionamount((Double)objects[TWO]);
			disputeDto.setTransactioncurrency((String)objects[THREE]);
			disputeDto.setTransactiondate((Date)objects[FOUR]);
			disputeDto.setTransationDateAsString(DateConvertion.dateToString((Date)objects[FOUR]));
			disputeDtoList.add(disputeDto);	
		}
		return disputeDtoList;
	}
	
	public static List<DisputeDto> getModelToDto(List<Object[]> list){
		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setTransactiondate((Date)objects[ONE]);
			disputeDto.setTransactionamount((Double)objects[TWO]);
			disputeDto.setTransactioncurrency((String)objects[THREE]);
			disputeDto.setDisputetype((String)objects[FOUR]);
			disputeDto.setDisputelogdate((Date)objects[FIVE]);
			disputeDto.setUpdationdate((Date)objects[SIX]);
			disputeDto.setRequestamount((Double)objects[SEVEN]);
			disputeDto.setRequestcurrency((String)objects[EIGHT]);
			disputeDto.setApprovedamount((Double)objects[NINE]);
			disputeDto.setApprovedcurrency((String)objects[TEN]);
			disputeDto.setStatusname((String)objects[ELEVEN]);
			disputeDto.setType(Long.valueOf(objects[TWELVE].toString()));
			disputeDto.setRequestCurrency(Long.valueOf(objects[THIRTEEN].toString()));
			disputeDto.setId(Long.valueOf(objects[FOURTEEN].toString()));
			disputeDto.setStatus(Long.valueOf(objects[FOURTEEN].toString()));
			disputeDtoList.add(disputeDto);
		}
		return disputeDtoList;
	}
	
	public static List<DisputeDto> getModelToDto1(List<Object[]> list){
		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setTransactiondate((Date)objects[FOUR]);
			disputeDto.setDisputeid((BigDecimal)objects[TEN]);
			disputeDto.setPayeeemailid((String)objects[ONE]);
			disputeDto.setTransactionamount((Double)objects[TWO]);
			disputeDto.setTransactioncurrency((String)objects[THREE]);
			disputeDto.setStatusname((String)objects[SIX]);
			disputeDto.setDisputelogdate((Date)objects[SEVEN]);
			disputeDto.setDisputetype((String)objects[NINE]);
			disputeDto.setStatus(Long.parseLong(((BigDecimal)objects[FIVE]).toString()));
			disputeDto.setType(Long.parseLong(((BigDecimal)objects[EIGHT]).toString()));
			disputeDto.setTransationDateAsString(DateConvertion.dateToString((Date)objects[FOUR]));
			
			disputeDtoList.add(disputeDto);
		}
		return disputeDtoList;
	}
	
	public static List<DisputeDto> getModelToDtoForMerchantDisputeList(List<Object[]> list){
		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setPayeremailid((String)objects[ONE]);
			disputeDto.setDisputelogdate((Date)objects[TWO]);
			disputeDto.setType(Long.parseLong(((BigDecimal)objects[THREE]).toString()));
			disputeDto.setDisputetype((String)objects[FOUR]);
			disputeDto.setRequestamount((Double)objects[FIVE]);
			disputeDto.setRequestcurrency((String)objects[SIX]);
			disputeDto.setDisputeid((BigDecimal)objects[SEVEN]);
			disputeDto.setStatus(((BigDecimal)objects[EIGHT]).longValue());
			disputeDto.setStatusname((String)objects[NINE]);
			disputeDtoList.add(disputeDto);
		}
		return disputeDtoList;
	}
	
	public static List<DisputeDto> getModelToDtoForMerchantUpdate(List<Object[]> list){
		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setPayeremailid((String)objects[ONE]);
			disputeDto.setPayeeemailid((String)objects[TWO]);
			disputeDto.setPayeramount(((BigDecimal)objects[THREE]).doubleValue());
			disputeDto.setPayercurrency((String)objects[FOUR]);
			disputeDto.setTransactionamount(((BigDecimal)objects[THREE]).doubleValue());
			disputeDto.setTransactioncurrency((String)objects[FOUR]);
			disputeDto.setTransactiondate((Date)objects[FIVE]);
			disputeDto.setStatusname((String)objects[SIX]);
			disputeDto.setDisputelogdate((Date)objects[SEVEN]);
			disputeDto.setDisputetype((String)objects[EIGHT]);
			disputeDto.setRequestamount(((BigDecimal)objects[NINE]).doubleValue());
			disputeDto.setRequestcurrency((String)objects[TEN]);
			disputeDto.setType(((BigDecimal)objects[ELEVEN]).longValue());
			disputeDto.setStatus(((BigDecimal)objects[TWELVE]).longValue());
			disputeDto.setApprovedamount(((BigDecimal)objects[THIRTEEN]).doubleValue());
			disputeDto.setApprovedcurrency((String)objects[FOURTEEN]);
			disputeDto.setUpdationdate((Date)objects[FIFTEEN]);
			
			disputeDtoList.add(disputeDto);
		}
		return disputeDtoList;
	}
	
	public static DisputeDto papulateDisputeDtoForUpdate(DisputeDto updateDto, DisputeDto oldDto){
		
		updateDto.setTxnid(oldDto.getTxnid());
		updateDto.setPayeremailid(oldDto.getPayeremailid());
		updateDto.setPayeeemailid(oldDto.getPayeeemailid());
		updateDto.setPayeramount(oldDto.getPayeramount());
		updateDto.setPayercurrency(oldDto.getPayercurrency());
		updateDto.setTransactionamount(oldDto.getTransactionamount());
		updateDto.setTransactioncurrency(oldDto.getTransactioncurrency());
		updateDto.setTransactiondate(oldDto.getTransactiondate());
		updateDto.setStatusname(oldDto.getStatusname());
		updateDto.setDisputelogdate(oldDto.getDisputelogdate());
		updateDto.setDisputetype(oldDto.getDisputetype());
		updateDto.setRequestamount(oldDto.getRequestamount());
		updateDto.setRequestcurrency(oldDto.getRequestcurrency());
		updateDto.setType(oldDto.getType());
		updateDto.setStatus(oldDto.getStatus());
		updateDto.setApprovedamount(oldDto.getApprovedamount());
		updateDto.setApprovedcurrency(oldDto.getApprovedcurrency());
		updateDto.setUpdationdate(oldDto.getUpdationdate());
		return updateDto;
	}
	
	public static List<DisputeDto> getDtoObjectsForAdminDisputeGrid(List<Object[]> list){

		List<DisputeDto> disputeDtoList = new ArrayList<DisputeDto>();
		DisputeDto disputeDto = null;
		for(Object[] objects: list){
			disputeDto = new DisputeDto();
			disputeDto.setTxnid((BigDecimal)objects[ZERO]);
			disputeDto.setTransactiondate((Date)objects[ONE]);
			disputeDto.setDisputeid((BigDecimal)objects[TWO]);
			disputeDto.setPayeeemailid((String)objects[THREE]);
			disputeDto.setPayeremailid((String)objects[FOUR]);
			disputeDto.setTransactionamount((Double)objects[FIVE]);
			disputeDto.setTransactioncurrency((String)objects[SIX]);
			disputeDto.setStatusname((String)objects[SEVEN]);
			disputeDto.setDisputelogdate((Date)objects[EIGHT]);
			disputeDto.setDisputetype((String)objects[NINE]);
			disputeDto.setDisputetypeid((BigDecimal)objects[TEN]);
			disputeDto.setStatusid((BigDecimal)objects[ELEVEN]);
			disputeDtoList.add(disputeDto);
		}
		return disputeDtoList;
	}
	
	public static List<DisputeGridDto> getDtoObjectsForCMAGrid(List<Object[]> list,Long userType){

		List<DisputeGridDto> disputeGridDtoList = new ArrayList<DisputeGridDto>();
		DisputeGridDto disputeGridDto = null;
		for(Object[] objects: list){
		
			disputeGridDto = new DisputeGridDto();
			disputeGridDto.setTransactionId(((BigDecimal) objects[ZERO]).longValue());
			disputeGridDto.setTxnAmount(((BigDecimal) objects[ONE]).doubleValue());
			disputeGridDto.setTxnCurrencyName((String) objects[TWO]);
			disputeGridDto.setTxnDate((Date) objects[THREE]);
			disputeGridDto.setDisputeAmount(((BigDecimal) objects[FOUR]).doubleValue());
			disputeGridDto.setDisputeStatusId(((BigDecimal) objects[FIVE]).longValue());
			disputeGridDto.setDisputeStatusName((String) objects[SIX]);
			disputeGridDto.setDisputeLogDate((Date) objects[SEVEN]);
			disputeGridDto.setDisputeTypeId(((BigDecimal) objects[EIGHT]).longValue());
			disputeGridDto.setDisputeTypeName((String) objects[NINE]);
			disputeGridDto.setDisputeId(((BigDecimal) objects[TEN]).longValue());
			
			String customerEmail = null;
			String merchantEmail = null;
			if (userType.equals(CUSTOMER_USER_TYPE_ID)) {
				merchantEmail = (String) objects[ELEVEN];

			} else if (userType.equals(MERCHANT_USER_TYPE_ID)) {
				customerEmail = (String) objects[ELEVEN];

			} else if (userType.equals(ADMIN_USER_TYPE_ID)) {
				
				customerEmail = (String) objects[ELEVEN];
				merchantEmail = (String) objects[TWELVE];
			}
			
			disputeGridDto.setCustomerEmail(customerEmail);
			disputeGridDto.setMerchantEmail(merchantEmail);
			
			disputeGridDtoList.add(disputeGridDto);
		}
		return disputeGridDtoList;
	}
	
	public static List<DisputeGridDto> getDtoObjectsForTxnGrid(List<Object[]> list){

		List<DisputeGridDto> disputeGridDtoList = new ArrayList<DisputeGridDto>();
		DisputeGridDto disputeGridDto = null;
		for(Object[] objects: list){
		
			disputeGridDto = new DisputeGridDto();
			disputeGridDto.setTransactionId(((BigDecimal) objects[ZERO]).longValue());
			disputeGridDto.setMerchantEmail((String) objects[ONE]);
			disputeGridDto.setTxnAmount(((BigDecimal) objects[TWO]).doubleValue());
			disputeGridDto.setTxnCurrencyName((String) objects[THREE]);
			disputeGridDto.setTxnDate((Date) objects[FOUR]);

			disputeGridDtoList.add(disputeGridDto);
		}
		return disputeGridDtoList;
	}
}


