/**
 * 
 */
package com.tarang.ewallet.transaction.business;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.ReloadMoneyDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.dto.SelfTransferDto;
import com.tarang.ewallet.dto.SendMoneyDto;
import com.tarang.ewallet.dto.VelocityAndThresholdDto;
import com.tarang.ewallet.dto.WalletThresholdDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.CancelTxn;
import com.tarang.ewallet.model.History;
import com.tarang.ewallet.model.NonRegisterWallet;
import com.tarang.ewallet.model.Payment;
import com.tarang.ewallet.model.RefundTxn;
import com.tarang.ewallet.model.ReloadMoney;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.model.SendMoneyRecurring;
import com.tarang.ewallet.model.SendMoneyTxn;
import com.tarang.ewallet.model.SettlementTxn;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.VelocityAndThreshold;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.model.WalletThreshold;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.util.DateConvertion;




/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class TransactionServicesTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private PGService pGService;
		
	@Autowired
	private RequestMoneyService requestMoneyService;
	
	@Autowired
	private ReloadMoneyService reloadMoneyService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	WalletFeeTaxService walletFeeTaxService;
	
    @Autowired
    private HibernateTemplate hibernateTemplate;
    
    @Autowired
    VelocityAndThresholdService velocityAndThresholdService;
    
    @Autowired
	private FeeMgmtService feeService;
    
    @Autowired
    private TransactionWalletService transactionWalletService;
    
    @Autowired
    private SendMoneyService sendMoneyService;
         
	@Test
	public void saveSuccessPGAuthTransactionTest() throws WalletException {
	
		History history = DataPreparation.getHistroyInfo();
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getAuthTxn().getId());		

	}

	@Test
	public void saveFailurePGAuthTransactionTest() throws WalletException{
		
		History history = DataPreparation.getHistroyInfo();
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());

	}
	
	@Test
	public void saveSuccessPGSettlementTxnTest() throws WalletException {
		SettlementTxn settlementTxn = DataPreparation.getSettlementTxn();
		
		History history = new History();
		history.setAccountId(1L);
		history.setOrderId(settlementTxn.getOrderId());
		history.setAmount(settlementTxn.getTxnAmount());
		history.setCardNumber("378282246310005");
		history.setCode(settlementTxn.getResponseCode());
		history.setCurrency(settlementTxn.getTxnCurrency());
		history.setDateAndTime(new Timestamp(1L));
		history.setIsSuccess(true);
		history.setMsg(settlementTxn.getResponseMessage());
		history.setNumberOfAttempts(0L);
		history.setTxnType("01");
		history.setReqType("01");
		history.setAuthMode("3D");
		history.setTranceNumber("52364");
		history.setSettlementTxn(settlementTxn);
		history.setNameOnCard("kedarnath das");
		
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getSettlementTxn().getId());		
	}
	
	@Test
	public void saveSuccessPGRefundTxnTest() throws WalletException {
		RefundTxn refundTxn = DataPreparation.getRefundTxn();
		
		History history = new History();
		history.setAccountId(1L);
		history.setOrderId("VSP21");//
		history.setAmount(refundTxn.getTxnAmount());
		history.setCardNumber("378282246310005");
		history.setCode(refundTxn.getResponseCode());
		history.setCurrency("USD");//
		history.setDateAndTime(new Timestamp(1L));
		history.setIsSuccess(true);
		history.setMsg(refundTxn.getResponseMessage());
		history.setNumberOfAttempts(0L);
		history.setTxnType("01");
		history.setReqType("01");
		history.setAuthMode("3D");
		history.setTranceNumber("52364");
		history.setRefundTxn(refundTxn);
		history.setNameOnCard("kedarnath das");
		
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getRefundTxn().getId());		

	}
	
	@Test
	public void saveSuccessPGCancelTxnTest() throws WalletException {
		CancelTxn cancelTxn = DataPreparation.getCancelTxn();
		
		History history = new History();
		history.setAccountId(1L);
		history.setOrderId("VSP21");//
		history.setAmount(11200D);
		history.setCardNumber("378282246310005");
		history.setCode("responsecode");
		history.setCurrency("USD");//
		history.setDateAndTime(new Timestamp(1L));
		history.setIsSuccess(true);
		history.setMsg("responseMessage");
		history.setNumberOfAttempts(0L);
		history.setTxnType("01");
		history.setReqType("01");
		history.setAuthMode("3D");
		history.setTranceNumber("52364");
		history.setCancelTxn(cancelTxn);
		history.setNameOnCard("kedarnath das");
		
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getCancelTxn().getId());		

	}

	@Test
	public void createReceiveMoneyTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		RequestMoneyDto receiveMny = new RequestMoneyDto();
		DataPreparation.getReceiveMoney(receiveMny);
		RequestMoney receiveMoney = requestMoneyService.createRequestMoney(receiveMny);
		Assert.assertNotNull(receiveMoney.getId());
		Assert.assertEquals(receiveMny.getResponserMsg(), receiveMoney.getResponserMsg());
	}
	
	@Test
	public void updateReceiveMoneyTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		RequestMoneyDto receiveMny = new RequestMoneyDto();
		DataPreparation.getReceiveMoney(receiveMny);
		RequestMoney rMoney = requestMoneyService.createRequestMoney(receiveMny);
		
		RequestMoneyDto redto = requestMoneyService.getRequestMoney(rMoney.getId());
		redto.setAmount(30000.00d);
		redto.setStatus(2L);
		redto.setResponserMsg("new responser message");
		redto.setTransactionId(1L);
		redto.setAttempts(1L);
		RequestMoney receiveMoney = requestMoneyService.updateRequestMoney(redto);
		Assert.assertNotNull(receiveMoney.getId());
		Assert.assertEquals(rMoney.getId(), receiveMoney.getId());
		Assert.assertEquals(rMoney.getStatus(), receiveMoney.getStatus());
		Assert.assertEquals(rMoney.getAmount(),receiveMoney.getAmount());	
		Assert.assertEquals(rMoney.getResponserMsg(), receiveMoney.getResponserMsg());	
	}
	
	@Test
	public void getReceiveMoneyByIdTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		RequestMoneyDto receiveMny = new RequestMoneyDto();
		DataPreparation.getReceiveMoney(receiveMny);
		RequestMoney rMoney = requestMoneyService.createRequestMoney(receiveMny);
		RequestMoneyDto receiveMoney = requestMoneyService.getRequestMoney(rMoney.getId());
		Assert.assertNotNull(receiveMoney.getId());
		Assert.assertEquals(receiveMny.getResponserMsg(), receiveMoney.getResponserMsg());
	}
	
	
	@Test
	public void getReceiveMoneyListByAuthIdTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		RequestMoneyDto receiveMny = new RequestMoneyDto();
		DataPreparation.getReceiveMoney(receiveMny);
		RequestMoney receiveMoney = requestMoneyService.createRequestMoney(receiveMny);
		List<RequestMoneyDto> receiveMoneyList = requestMoneyService.getRequestMoneyList(receiveMoney.getResponserId());		
		Assert.assertEquals(receiveMoneyList.size(), 1);
		RequestMoneyDto rdto = receiveMoneyList.get(0);
		System.out.println("id is :" + rdto.getId());
		Assert.assertEquals("apitest744@gmail.com", rdto.getEmail());
	}
	
	
	@Test
	public void acceptReceiveMoneyTest() throws WalletException {
		List<Object> data = DataPreparation.getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
		CustomerDto dto = DataPreparation.getCustomerDto();	
		dto = customerService.newregistration(dto);
		UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
		if(userWallet != null){
			userWallet.setAmount(100000.00);
			commonService.updateUserWallet(userWallet);
		}
		
		FeeDto feeDto = new FeeDto();
		DataPreparation.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
		
		RequestMoneyDto receiveMny = new RequestMoneyDto();
		DataPreparation.getReceiveMoney(receiveMny);
		RequestMoney rMoney = requestMoneyService.createRequestMoney(receiveMny);
		RequestMoneyDto reDto = requestMoneyService.getRequestMoney(rMoney.getId());
		reDto.setTypeOfRequest(2L); 
		RequestMoney receiveMoney = requestMoneyService.acceptRequestMoney(reDto);
		Assert.assertNotNull(receiveMoney.getId());
		Assert.assertNotNull(receiveMoney.getTransactionId());
		Assert.assertEquals(rMoney.getId(), receiveMoney.getId());
		Assert.assertEquals(rMoney.getStatus(), receiveMoney.getStatus());
		Assert.assertEquals(rMoney.getAmount(),receiveMoney.getAmount());	
		Assert.assertEquals(rMoney.getResponserMsg(), receiveMoney.getResponserMsg());	
		Assert.assertEquals(new Long(1), receiveMoney.getAttempts());
	}
	
	@Test
	public void createReloadMoneyTest() throws WalletException { 
		
		List<Object> data = DataPreparation.getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}

		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		ReloadMoneyDto rmdto = DataPreparation.getReloadMoney();
		rmdto.setAuthId(dto.getAuthenticationId());
		rmdto.setTypeOfRequest(1L);
		ReloadMoney reloadMoney = reloadMoneyService.createReloadMoney(rmdto);
		Assert.assertNotNull( reloadMoney );
		Assert.assertNotNull(reloadMoney.getId());
	}
	
	@Test
	public void createSendMoneyWithRecurringTest() throws WalletException{
		try{
		List<Object> data = DataPreparation.getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
    	
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		CustomerDto dto2 = DataPreparation.getCustomerDto();
		dto2.setEmailId("kedarnathd@tarangtech.com");
		dto2.setPhoneCode("22");
		dto2.setPhoneNo("123446");
		dto2 = customerService.newregistration(dto2);

		dto.setStatus(1L);
		dto.setStatusName("1");
		customerService.updateCustomerDetails(dto);
		dto2.setStatus(1L);
		dto2.setStatusName("1");
		customerService.updateCustomerDetails(dto2);

		FeeDto feeDto = new FeeDto();
		DataPreparation.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
		
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		sendMoneyDto.setEmailId(dto.getEmailId());
		sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		sendMoneyDto.setReceiverAuthId(dto2.getAuthenticationId());
		sendMoneyDto.setRequestedAmount(20000.00);
		sendMoneyDto.setRequestedCurrency(1L);
		sendMoneyDto.setMessage("hai");
		sendMoneyDto.setDestinationType(1L);
		sendMoneyDto.setRecurring(true);
		sendMoneyDto.setFromDate(new Date());
		sendMoneyDto.setToDate(new Date());
		sendMoneyDto.setFrequency(1L);
		sendMoneyDto.setTotalOccurences(12);
		sendMoneyDto.setTransactionType(2L);
		sendMoneyDto.setRequestDate(new Date());
		sendMoneyDto.setCurrencyCode("USD");
		sendMoneyDto.setTypeOfRequest(1L);
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
		}catch(WalletException ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Test
	public void createSendMoneyWithOutRecurringTest() throws WalletException{
		
		List<Object> data = DataPreparation.getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
    	
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		
		CustomerDto dto2 = DataPreparation.getCustomerDto();
		dto2.setEmailId("kedarnathd@tarangtech.com");
		dto2.setPhoneCode("22");
		dto2.setPhoneNo("123446");
		dto2 = customerService.newregistration(dto2);
		
		dto.setStatus(1L);
		dto.setStatusName("1");
		customerService.updateCustomerDetails(dto);
		dto2.setStatus(1L);
		dto2.setStatusName("1");
		customerService.updateCustomerDetails(dto2);
		
		FeeDto feeDto = new FeeDto();
		DataPreparation.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
		
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		
		sendMoneyDto.setEmailId(dto.getEmailId());
		sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		sendMoneyDto.setReceiverAuthId(dto2.getAuthenticationId());
		sendMoneyDto.setRequestedAmount(20000.00);
		sendMoneyDto.setRequestedCurrency(1L);
		sendMoneyDto.setActualAmount(20000.00);
		sendMoneyDto.setActualCurrency(1L);
		sendMoneyDto.setMessage("hai");
		sendMoneyDto.setDestinationType(1L);
		sendMoneyDto.setRecurring(false);
		sendMoneyDto.setCountryId(1L);
		sendMoneyDto.setTransactionType(2L);
		sendMoneyDto.setRequestDate(new Date());
		sendMoneyDto.setCurrencyCode("USD");
		sendMoneyDto.setTypeOfRequest(1L);
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
	}
	
	@Test
	public void getReceiveMoneyListTest() throws WalletException{
		
		List<Object> data = DataPreparation.getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
    	
		CustomerDto dto = DataPreparation.getCustomerDto();		
		dto = customerService.newregistration(dto);
		
		CustomerDto dto2 = DataPreparation.getCustomerDto();
		dto2.setEmailId("kedarnathd@tarangtech.com");
		dto2.setPhoneCode("22");
		dto2.setPhoneNo("123446");
		dto2 = customerService.newregistration(dto2);
		
		dto.setStatus(1L);
		dto.setStatusName("1");
		customerService.updateCustomerDetails(dto);
		dto2.setStatus(1L);
		dto2.setStatusName("1");
		customerService.updateCustomerDetails(dto2);
		
		FeeDto feeDto = new FeeDto();
		DataPreparation.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
		
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		sendMoneyDto.setEmailId(dto.getEmailId());
		sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		sendMoneyDto.setReceiverAuthId(dto2.getAuthenticationId());
		sendMoneyDto.setRequestedAmount(20000.00);
		sendMoneyDto.setRequestedCurrency(1L);
		sendMoneyDto.setMessage("hai kedar, send some money to yar");
		sendMoneyDto.setDestinationType(1L);
		sendMoneyDto.setRecurring(true);
		sendMoneyDto.setFrequency(1L);
		sendMoneyDto.setTotalOccurences(12);
		sendMoneyDto.setTransactionType(2L);
		sendMoneyDto.setCurrencyCode("USD");
		sendMoneyDto.setTypeOfRequest(1L);
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
		
		List<SendMoneyDto> dtos = sendMoneyService.getReceiveMoneyList(dto2.getAuthenticationId());
		Assert.assertNotNull(dtos);
		Assert.assertEquals(dto.getEmailId(), dtos.get(0).getEmailId());
		
	}
	
	@Test
	public void getVelocityTest() throws WalletException{
		VelocityAndThresholdDto velocity = DataPreparation.createVelocityAndThresholdDto();
		VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.createVelocityAndThreshold(velocity);
		Assert.assertNotNull(velocityAndThreshold.getId());	
		Assert.assertEquals(velocity.getCountry(), velocityAndThreshold.getCountry());
		Assert.assertEquals(velocity.getMaximumamount(), velocityAndThreshold.getMaximumamount());
		
		VelocityAndThreshold newvelocity = velocityAndThresholdService.getThreshold(velocityAndThreshold.getCountry(), 
				velocityAndThreshold.getCurrency(), velocityAndThreshold.getTransactiontype(), 
				velocityAndThreshold.getUserType());
		
		Assert.assertNotNull(newvelocity.getId());	
		Assert.assertEquals(velocity.getCountry(), newvelocity.getCountry());
		Assert.assertEquals(velocity.getMinimumamount(), newvelocity.getMinimumamount());
		Assert.assertEquals(velocity.getMaximumamount(), newvelocity.getMaximumamount());
	}
	
	@Test
	public void getVelocityTestFail() throws WalletException{
		VelocityAndThresholdDto velocity = DataPreparation.createVelocityAndThresholdDto();
		VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.createVelocityAndThreshold(velocity);
		Assert.assertNotNull(velocityAndThreshold.getId());	
		Assert.assertEquals(velocity.getCountry(), velocityAndThreshold.getCountry());
		Assert.assertEquals(velocity.getMaximumamount(), velocityAndThreshold.getMaximumamount());
		
		VelocityAndThreshold newvelocity = velocityAndThresholdService.getThreshold(velocityAndThreshold.getCountry(), 
				velocityAndThreshold.getCurrency(), velocityAndThreshold.getTransactiontype(), 
				null);
		Assert.assertNull(newvelocity);	
		
	}
	
	@Test
	public void createVelocityAndThresholdTest() throws WalletException{
		VelocityAndThresholdDto velocity = DataPreparation.createVelocityAndThresholdDto();
		VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.createVelocityAndThreshold(velocity);
		Assert.assertNotNull(velocityAndThreshold.getId());	
		Assert.assertEquals(velocity.getCountry(), velocityAndThreshold.getCountry());
		Assert.assertEquals(velocity.getMaximumamount(), velocityAndThreshold.getMaximumamount());
	}
	
	@Test
	public void updateVelocityAndThresholdTest() throws WalletException{
		VelocityAndThresholdDto velocitydto = DataPreparation.createVelocityAndThresholdDto();
		VelocityAndThreshold VelocityAndThreshold = velocityAndThresholdService.createVelocityAndThreshold(velocitydto);
		velocitydto.setId(VelocityAndThreshold.getId());
		velocitydto.setMaximumamount(1000.0);
		VelocityAndThreshold updateVelocityAndThreshold = velocityAndThresholdService.updateVelocityAndThreshold(velocitydto);
		Assert.assertEquals(VelocityAndThreshold.getId(), updateVelocityAndThreshold.getId());
		Assert.assertEquals(VelocityAndThreshold.getMinimumamount(), updateVelocityAndThreshold.getMinimumamount());
		Assert.assertEquals(VelocityAndThreshold.getMaximumamount(), updateVelocityAndThreshold.getMaximumamount());
	}
	
	@Test
	public void getVelocityAndThresholdTest() throws WalletException{
		VelocityAndThresholdDto velocity = DataPreparation.createVelocityAndThresholdDto();
		VelocityAndThreshold velocityAndThreshold = velocityAndThresholdService.createVelocityAndThreshold(velocity);
		VelocityAndThreshold getVelocityAndThreshold = velocityAndThresholdService.getVelocityAndThreshold(velocityAndThreshold.getId());		
		Assert.assertNotNull(getVelocityAndThreshold.getId());	
		Assert.assertEquals(velocityAndThreshold.getCurrency(), getVelocityAndThreshold.getCurrency());
		Assert.assertEquals(velocityAndThreshold.getMaximumamount(), getVelocityAndThreshold.getMaximumamount());
	}
	
	@Test
	public void getVelocityAndThresholdListTest() throws WalletException{
		VelocityAndThresholdDto velocity = DataPreparation.createVelocityAndThresholdDto();
		velocityAndThresholdService.createVelocityAndThreshold(velocity);
		velocity.setCountry(2L);
		velocity.setCurrency(2L);
		velocityAndThresholdService.createVelocityAndThreshold(velocity);
		List<VelocityAndThresholdDto> velocityList = velocityAndThresholdService.getVelocityAndThresholdList();		
		Assert.assertNotNull(velocityList);	
		Assert.assertEquals(2, velocityList.size());
		Assert.assertEquals(velocity.getCountry(), velocityList.get(1).getCountry());
		Assert.assertEquals(velocity.getCurrency(), velocityList.get(1).getCurrency());
	}
	
	@Test
	public void createWalletThresholdTest() throws WalletException{
		WalletThresholdDto threshold = DataPreparation.createWalletThresholdDto();
		WalletThreshold walletThreshold = velocityAndThresholdService.createWalletThreshold(threshold);
		Assert.assertNotNull(walletThreshold.getId());	
		Assert.assertEquals(threshold.getCountry(), walletThreshold.getCountry());
		Assert.assertEquals(threshold.getMaximumamount(), walletThreshold.getMaximumamount());
	}
	
	@Test
	public void updateWalletThresholdTest() throws WalletException{
		WalletThresholdDto thresholddto = DataPreparation.createWalletThresholdDto();
		WalletThreshold WalletThreshold = velocityAndThresholdService.createWalletThreshold(thresholddto);
		thresholddto.setId(WalletThreshold.getId());
		thresholddto.setMaximumamount(1000.0);
		WalletThreshold updateWalletThreshold = velocityAndThresholdService.updateWalletThreshold(thresholddto);
		Assert.assertEquals(WalletThreshold.getId(), updateWalletThreshold.getId());
		Assert.assertEquals(WalletThreshold.getMaximumamount(), updateWalletThreshold.getMaximumamount());
	}
	
	
	@Test
	public void getWalletThresholdTest() throws WalletException{
		WalletThresholdDto threshold = DataPreparation.createWalletThresholdDto();
		WalletThreshold walletThreshold = velocityAndThresholdService.createWalletThreshold(threshold);
		WalletThreshold getWalletThreshold = velocityAndThresholdService.getWalletThreshold(walletThreshold.getId());		
		Assert.assertNotNull(getWalletThreshold.getId());	
		Assert.assertEquals(walletThreshold.getCurrency(), getWalletThreshold.getCurrency());
		Assert.assertEquals(walletThreshold.getMaximumamount(), getWalletThreshold.getMaximumamount());
	}
	
	
	public void getWalletThresholdListTest() throws WalletException{
		WalletThresholdDto threshold = DataPreparation.createWalletThresholdDto();
		velocityAndThresholdService.createWalletThreshold(threshold);
		threshold.setCurrency(2L);
		velocityAndThresholdService.createWalletThreshold(threshold);
		List<WalletThresholdDto> velocityList = velocityAndThresholdService.getWalletThresholdList();		
		Assert.assertNotNull(velocityList);	
		Assert.assertEquals(2, velocityList.size());
		Assert.assertEquals(threshold.getCountry(), velocityList.get(1).getCountry());
		Assert.assertEquals(threshold.getCurrency(), velocityList.get(1).getCurrency());
	}
	
	@Test
	public void createPaymentTest() throws WalletException{
		Payment payment = transactionWalletService.createPayment(DataPreparation.dataPrePayment());
		Assert.assertNotNull(payment);
		Assert.assertNotNull(payment.getId());
	}
	
	@Test
	public void NonSettlementAuthIdsTest() throws WalletException{
		List<Long>  list = reloadMoneyService.getNonSettlementHistoryIds();
		Assert.assertNotNull(list);
	}
	
	@Test
	public void postNotSettlementTransactionsTest() throws WalletException{
           reloadMoneyService.postNonSettlementTransactions();
	}
	
	@Test
	public void getHistoryTest() throws WalletException {
		History history = DataPreparation.getHistroyInfo();
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getAuthTxn().getId());		
		
		History getHistoryValue= pGService.getHistory(history.getOrderId(), history.getAccountId(), history.getAmount());
		Assert.assertNotNull(getHistoryValue );
		Assert.assertEquals(history.getOrderId(), "123456789");
		Assert.assertEquals(history.getAmount(), 10000.0);
		
	    History getHistoryById =  pGService.getHistory(history.getId());
	    Assert.assertNotNull(getHistoryById );
	}
	
	@Test
	public void updateHistoryTest() throws WalletException {
		History history = DataPreparation.getHistroyInfo();
		History newhistory = pGService.saveTransaction(history);
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getAuthTxn().getId());		
		
		History getHistoryValue= pGService.getHistory(history.getOrderId(), history.getAccountId(), history.getAmount());
		Assert.assertNotNull(getHistoryValue );
		Assert.assertEquals(history.getOrderId(), "123456789");
		Assert.assertEquals(history.getAmount(), 10000.0);
		
		history.setAccountId(2L);
		history.setAmount(1000.0);
		history.setCurrency("2");
		
		History updateHistoryFlag = pGService.updateHistory(history);
		Assert.assertEquals(history.getAmount(), 1000.0);
		Assert.assertEquals(history.getCurrency(), "2");
		Assert.assertNotNull(updateHistoryFlag);
		
	}
	
	@Test
	public void settlementReloadMoneyTest() throws WalletException { 
		History history = DataPreparation.getHistroyInfo();
        History newhistory = pGService.saveTransaction(history);
		
		Assert.assertNotNull( newhistory );
		Assert.assertNotNull(newhistory.getId());
		Assert.assertNotNull(newhistory.getAuthTxn().getId());		
        
		ReloadMoneyDto rmdto = DataPreparation.getReloadMoney();
		rmdto.setTypeOfRequest(1L);
		rmdto.setOrderId(history.getOrderId());
		rmdto.setAccountId(history.getAccountId());
		rmdto.setReloadAmount(history.getAmount());
		
		reloadMoneyService.settlementReloadMoney(rmdto);
		Assert.assertEquals(rmdto.getReloadAmount(), 10000.00);
		Assert.assertEquals(rmdto.getOrderId(), "123456789");
		Assert.assertEquals(rmdto.getAccountId(), new Long(1L));
	}
	
	@Test
	public void validateThresholdLimitTest() throws WalletException { 
		SendMoneyDto sendMoneyDto = getSendMoneyDto();
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
		
		UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		Assert.assertNotNull(userWallet.getId());
		userWallet.setAmount(10000.00);
		userWallet = commonService.updateUserWallet(userWallet);
		Assert.assertNotNull(userWallet.getId());
		
		WalletThresholdDto threshold = DataPreparation.createWalletThresholdDto();
		WalletThreshold walletThreshold = velocityAndThresholdService.createWalletThreshold(threshold);
		Assert.assertNotNull(walletThreshold.getId());	
		Assert.assertEquals(threshold.getCountry(), walletThreshold.getCountry());
		Assert.assertEquals(threshold.getMaximumamount(), walletThreshold.getMaximumamount());
		
		Boolean validateThreshold = sendMoneyService.validateThresholdLimit(sendMoneyDto);
		Assert.assertEquals(validateThreshold, Boolean.TRUE);
		
		sendMoneyService.validateThresholdLimit(sendMoneyDto.getCountryId(), 1L, sendMoneyDto.getTransactionType(), 
				sendMoneyDto.getActualAmount(), 2L);
		sendMoneyService.validateThresholdLimitForSelfTransfer(sendMoneyDto.getCountryId(),1L, sendMoneyDto.getTransactionType()
				, sendMoneyDto.getActualAmount(), 2L);
		
		Assert.assertEquals(sendMoneyDto.getCountryId(), walletThreshold.getCountry());	
		Assert.assertEquals(sendMoneyDto.getActualAmount(), walletThreshold.getMaximumamount());
	}
	
	@Test
	public void acceptSendReceiveMoneyTest() throws WalletException {
		SendMoneyDto sendMoneyDto = getSendMoneyDto();
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
		
		sendMoneyDto.setMessage("Send Money Accept");
	    SendMoneyDto sendMoneyFlag = sendMoneyService.acceptReceiveMoney(sendMoneyDto);
	    
		Assert.assertNotNull(sendMoneyFlag);
		Assert.assertEquals(sendMoneyDto.getActualAmount(),100.00);
	}
	
	@Test
	public void rejectReceiveSendMoneyTest() throws WalletException {
		
		SendMoneyDto sendMoneyDto = getSendMoneyDto();
		SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		Assert.assertNotNull(sendMoneyToSingle);
		
		sendMoneyDto.setMessage("Send Money Reject");
		SendMoneyDto rejectFlag = sendMoneyService.rejectReceiveMoney(sendMoneyDto);
		
		Assert.assertNotNull(rejectFlag);
		Assert.assertEquals(sendMoneyDto.getActualAmount(),100.00);
	}
	
    @Test
 	public void cancelRequestMoneyTest() throws WalletException {
    	
	   List<Object> data = DataPreparation.getMasterWalletsData();
	     Iterator<Object> iter = data.iterator();
	     while(iter.hasNext()){
	      hibernateTemplate.save(iter.next());
	     }
	   CustomerDto dto = DataPreparation.getCustomerDto();  
	   dto = customerService.newregistration(dto);
	  
	   UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
	   if(userWallet != null){
	   userWallet.setAmount(100000.00);
	   commonService.updateUserWallet(userWallet);
	   }
	  
	  FeeDto feeDto = new FeeDto();
	  DataPreparation.dataPrepCreateForFeeDto(feeDto);
	  FeeDto feeDtoSaved = feeService.createFee(feeDto);
	  Assert.assertNotNull(feeDtoSaved.getId());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
	  
	  RequestMoneyDto receiveMny = new RequestMoneyDto();
	  DataPreparation.getReceiveMoney(receiveMny);
	  
	  RequestMoney rMoney = requestMoneyService.createRequestMoney(receiveMny);
	  RequestMoneyDto reDto = requestMoneyService.getRequestMoney(rMoney.getId());
	  reDto.setRequesterEmail(dto.getEmailId());
	  RequestMoney receiveMoney = requestMoneyService.cancelRequestMoney(reDto);
	  Assert.assertNotNull(receiveMoney.getId());
	}
		
    @Test
 	public void rejectRequestMoneyTest() throws WalletException {
	   List<Object> data = DataPreparation.getMasterWalletsData();
	     Iterator<Object> iter = data.iterator();
	     while(iter.hasNext()){
	      hibernateTemplate.save(iter.next());
	     }
	     
	   CustomerDto dto = DataPreparation.getCustomerDto();  
	   dto = customerService.newregistration(dto);
	  
	   UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
	   if(userWallet != null){
	   userWallet.setAmount(100000.00);
	   commonService.updateUserWallet(userWallet);
	   }
	  
	  FeeDto feeDto = new FeeDto();
	  DataPreparation.dataPrepCreateForFeeDto(feeDto);
	  FeeDto feeDtoSaved = feeService.createFee(feeDto);
	  
	  Assert.assertNotNull(feeDtoSaved.getId());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
	  Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
	  
	  RequestMoneyDto receiveMny = new RequestMoneyDto();
	  DataPreparation.getReceiveMoney(receiveMny);
	  RequestMoney rMoney = requestMoneyService.createRequestMoney(receiveMny);
	  RequestMoneyDto reDto = requestMoneyService.getRequestMoney(rMoney.getId());
	  reDto.setRequesterEmail(dto.getEmailId());
	  RequestMoney receiveMoney = requestMoneyService.rejectRequestMoney(reDto);
	  Assert.assertNotNull(receiveMoney.getId());
	}
    
    @Test
 	public void createSelfMoneyTest() throws WalletException {
    	
		 List<Object> data = DataPreparation.getMasterWalletsData();
	     Iterator<Object> iter = data.iterator();
	     while(iter.hasNext()){
	      hibernateTemplate.save(iter.next());
	     }
	   CustomerDto dto = DataPreparation.getCustomerDto();  
	   dto = customerService.newregistration(dto);
	   dto.setStatus(1L);
	   dto.setStatusName("1");
	   customerService.updateCustomerDetails(dto);
	   
	   UserWallet userWallet = null;
	   userWallet = commonService.createUserWallet(1L, 1L);
	   userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
	  
	   if(userWallet != null){
	   userWallet.setAmount(1000000.00);
	   commonService.updateUserWallet(userWallet);
	   }  
        SelfTransferDto selfTransferDto = DataPreparation.getselfTransferDto(dto);
        SelfTransferDto selfTransferFlag = sendMoneyService.createSelfMoney(selfTransferDto);
        Assert.assertNotNull(selfTransferFlag);
    }
    
	@Test
	public void getWalletTest() throws WalletException {
		CustomerDto dto = DataPreparation.getCustomerDto();  
		dto = customerService.newregistration(dto);
		  
		dto.setStatus(1L);
		dto.setStatusName("1");
	    customerService.updateCustomerDetails(dto);
	   
		UserWallet userWallet = null;
		userWallet = commonService.createUserWallet(1L, 1L);
		userWallet.setAmount(100000.0);
		commonService.updateUserWallet(userWallet);
	  
		WalletThresholdDto threshold = DataPreparation.createWalletThresholdDto();
		WalletThreshold walletThreshold = velocityAndThresholdService.createWalletThreshold(threshold);
		
		Assert.assertNotNull(walletThreshold.getId());	
		Assert.assertEquals(threshold.getCountry(), walletThreshold.getCountry());
		Assert.assertEquals(threshold.getMaximumamount(), walletThreshold.getMaximumamount());
		
		WalletThreshold getWalletThreshold = velocityAndThresholdService.getWallet(walletThreshold.getCountry(),  walletThreshold.getCurrency());
		
		Assert.assertEquals(walletThreshold.getCurrency(), getWalletThreshold.getCurrency());
		Assert.assertEquals(walletThreshold.getMaximumamount(), getWalletThreshold.getMaximumamount());
	}
	
	@Test
	public void addFeeTest() throws WalletException {
		
		WalletFee walletFee = DataPreparation.getAddFee();
		WalletFee addWalletFee = walletFeeTaxService.addFee(walletFee);
		Assert.assertNotNull(addWalletFee);
		Assert.assertEquals(walletFee.getAmount(), 1000.0);
	}
	
	@Test
	public void getFeeByIdTest() throws WalletException {
		
		WalletFee walletFee = DataPreparation.getAddFee();
		WalletFee addWalletFee = walletFeeTaxService.addFee(walletFee);
		
		Assert.assertNotNull(addWalletFee);
		Assert.assertEquals(walletFee.getAmount(), 1000.0);
		
		WalletFee getWalletFee = walletFeeTaxService.getFeeById(walletFee.getId());
		Assert.assertEquals(walletFee.getId(), getWalletFee.getId());
		Assert.assertEquals(walletFee.getAmount(), getWalletFee.getAmount());
		
		WalletFee walletFeeTrxId = walletFeeTaxService.getFeeByTransaction(walletFee.getTransactionId());
		Assert.assertEquals(walletFee.getTransactionId(), walletFeeTrxId.getTransactionId());
	}
	
	@Test
	public void addTaxTest() throws WalletException {
		
		WalletTax walletTax = DataPreparation.getAddTax();
		WalletTax addwalletTax = walletFeeTaxService.addTax(walletTax);
		
		Assert.assertNotNull(addwalletTax);
		Assert.assertEquals(addwalletTax.getAmount(), 1000.0);
	}
	
	@Test
	public void getTaxByIdTest() throws WalletException {
		
		WalletTax walletTax = DataPreparation.getAddTax();
		WalletTax addwalletTax = walletFeeTaxService.addTax(walletTax);
		Assert.assertNotNull(addwalletTax);
		Assert.assertEquals(addwalletTax.getAmount(), 1000.0);
		
		WalletTax getWalletTax = walletFeeTaxService.getTaxById(walletTax.getId());
		Assert.assertEquals(getWalletTax.getId(), walletTax.getId());
		Assert.assertEquals(getWalletTax.getAmount(), walletTax.getAmount());
		
		WalletTax walletTaxTrxId  =  walletFeeTaxService.getTaxByTransaction(walletTax.getTransactionId());
		Assert.assertNotNull(walletTaxTrxId);
		Assert.assertEquals(walletTax.getTransactionId(), walletTax.getTransactionId());
	}
	
	@Test
	public void startSendMoneyRecurringTest() throws WalletException {
		
	   UserWallet userWallet = null;
	   userWallet = commonService.createUserWallet(1L, 1L);
	   userWallet.setAmount(100000.0);
	   commonService.updateUserWallet(userWallet);
	   userWallet = commonService.getUserWallet(userWallet.getAuthId(), 1L);
		    if(userWallet != null){
		     userWallet.setAmount(100000.00);
		     commonService.updateUserWallet(userWallet);
		  }
		    
	   SendMoneyDto sendMoneyDto = getSendMoneyDto();
	   sendMoneyDto.setRecurring(true);
	   sendMoneyDto.setFromDate(new Date());
	   sendMoneyDto.setToDate(DateConvertion.getFutureDateByHours(24));
	   sendMoneyDto.setFrequency(1L);
	   sendMoneyDto.setTotalOccurences(2);
	   sendMoneyDto.setRequestDate(new Date());
	   
	   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
	   SendMoneyTxn sendMoneyTxn = new SendMoneyTxn();
	   sendMoneyTxn.setSendMoneyId(sendMoneyDto.getId());
	   sendMoneyTxn.setTransactionId(sendMoneyDto.getTransactionId());
	   sendMoneyTxn.setTransactionStatus(4L);
	   sendMoneyTxn.setOccurences(sendMoneyDto.getTotalOccurences());
	   sendMoneyTxn.setTriggerDate(new Date());
	   sendMoneyTxn.setFailureMessage("Not Failure");
	   
	   sendMoneyTxn = sendMoneyService.startSendMoneyRecurring(sendMoneyTxn.getSendMoneyId(), sendMoneyTxn.getOccurences());
	  
	   Assert.assertNotNull(sendMoneyTxn);
	   Assert.assertEquals(sendMoneyTxn.getSendMoneyId(), sendMoneyDto.getId());
	   Assert.assertEquals(sendMoneyTxn.getOccurences(), sendMoneyDto.getTotalOccurences());
	}
	
	@Test
	public void updateSendMoneyRecurringTest() throws WalletException {
		
	   UserWallet userWallet = null;
	   userWallet = commonService.createUserWallet(1L, 1L);
	   userWallet.setAmount(100000.0);
	   commonService.updateUserWallet(userWallet);
	   userWallet = commonService.getUserWallet(userWallet.getAuthId(), 1L);
		    if(userWallet != null){
		     userWallet.setAmount(100000.00);
		     commonService.updateUserWallet(userWallet);
		  }
		    
	   SendMoneyDto sendMoneyDto = getSendMoneyDto();
	   sendMoneyDto.setRecurring(true);
	   sendMoneyDto.setFromDate(new Date());
	   sendMoneyDto.setToDate(DateConvertion.getFutureDateByHours(24));
	   sendMoneyDto.setFrequency(1L);
	   sendMoneyDto.setTotalOccurences(2);
	   sendMoneyDto.setRequestDate(new Date());
	   
	   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
	   
	   SendMoneyRecurring sendMoneyRecurring = new SendMoneyRecurring();
	   sendMoneyRecurring.setFrequency(sendMoneyDto.getFrequency());
	   sendMoneyRecurring.setFromDate(sendMoneyDto.getFromDate());
	   sendMoneyRecurring.setTotalOccurences(3);
	   sendMoneyRecurring.setSendMoneyId(sendMoneyDto.getId());
	   sendMoneyRecurring.setToDate(sendMoneyDto.getToDate());
	   sendMoneyRecurring.setUserJobName("Update Recurring Details");
	   
	   Assert.assertNotNull(sendMoneyRecurring);
	   
	   SendMoneyRecurring updateRecurringFlag = sendMoneyService.updateSendMoneyRecurring(sendMoneyRecurring);
	   Assert.assertNotNull(updateRecurringFlag);
	  
	}
	
	@Test
	public void getSendMoneyByIdTest() throws WalletException {
	
		  UserWallet userWallet = null;
		   userWallet = commonService.createUserWallet(1L, 1L);
		   userWallet.setAmount(100000.0);
		   commonService.updateUserWallet(userWallet);
		   userWallet = commonService.getUserWallet(userWallet.getAuthId(), 1L);
			    if(userWallet != null){
			     userWallet.setAmount(100000.00);
			     commonService.updateUserWallet(userWallet);
			  }
			    
		   SendMoneyDto sendMoneyDto = getSendMoneyDto();
		   sendMoneyDto.setRecurring(true);
		   sendMoneyDto.setFromDate(new Date());
		   sendMoneyDto.setToDate(DateConvertion.getFutureDateByHours(24));
		   sendMoneyDto.setFrequency(1L);
		   sendMoneyDto.setTotalOccurences(2);
		   sendMoneyDto.setRequestDate(new Date());
		   
		   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
		   SendMoneyDto sendMoney= sendMoneyService.getSendMoneyById(sendMoneyDto.getId());
		   Assert.assertNotNull(sendMoney);
		   Assert.assertNotNull(sendMoney.getId());
	  }
	
	@Test
	public void getLastNdaysTransactionsCountDayWiseTest() throws WalletException {
		
		 UserWallet userWallet = null;
		   userWallet = commonService.createUserWallet(1L, 1L);
		   userWallet.setAmount(100000.0);
		   commonService.updateUserWallet(userWallet);
		   userWallet = commonService.getUserWallet(userWallet.getAuthId(), 1L);
			    if(userWallet != null){
			     userWallet.setAmount(100000.00);
			     commonService.updateUserWallet(userWallet);
			  }
		   SendMoneyDto sendMoneyDto = getSendMoneyDto();
		   sendMoneyDto.setRecurring(true);
		   sendMoneyDto.setFromDate(new Date());
		   sendMoneyDto.setToDate(DateConvertion.getFutureDateByHours(24));
		   sendMoneyDto.setFrequency(1L);
		   sendMoneyDto.setTotalOccurences(2);
		   sendMoneyDto.setRequestDate(new Date());
		   
		   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
		   
		   List<Object[]> transactinList = transactionWalletService.getLastNdaysTransactionsCountDayWise(
			   DateConvertion.getFromDate(1),DateConvertion.getToDate(2),  WalletTransactionTypes.P_TO_P);
		   Assert.assertNotNull(transactinList);
	}
	
	 @Test
	 public void updateSendMoneyForNonRegistersTest() throws WalletException {
	  
	  List<Object> data = DataPreparation.getMasterWalletsData();
	     Iterator<Object> iter = data.iterator();
	     while(iter.hasNext()){
	      hibernateTemplate.save(iter.next());
	     }
	     
	  CustomerDto dto = DataPreparation.getCustomerDto();  
	  dto = customerService.newregistration(dto);
	  
	  dto.setStatus(1L);
	  dto.setStatusName("1");
	  customerService.updateCustomerDetails(dto);
	  
	  UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
	    if(userWallet != null){
	     userWallet.setAmount(100000.00);
	     commonService.updateUserWallet(userWallet);
	  }
	  
	  SendMoneyDto sendMoneyDto = new SendMoneyDto();
	  
	  sendMoneyDto.setEmailId(dto.getEmailId());
	  sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
	  sendMoneyDto.setRequestedAmount(20000.00);
	  sendMoneyDto.setRequestedCurrency(1L);
	  sendMoneyDto.setActualAmount(20000.00);
	  sendMoneyDto.setActualCurrency(1L);
	  sendMoneyDto.setMessage("hai");
	  sendMoneyDto.setDestinationType(4L);
	  sendMoneyDto.setRecurring(false);
	  sendMoneyDto.setCountryId(1L);
	  sendMoneyDto.setTransactionType(WalletTransactionTypes.P_TO_NP);
	  sendMoneyDto.setRequestDate(new Date());
	  sendMoneyDto.setCurrencyCode("USD");
	  sendMoneyDto.setTypeOfRequest(2L);
	  
	  SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
	  Assert.assertNotNull(sendMoneyToSingle);
	  
	  CustomerDto nonRegCustomerDto = DataPreparation.getCustomerDto();
	  nonRegCustomerDto.setEmailId("abcd@tarangtech.com");
	  nonRegCustomerDto.setPhoneCode("22");
	  nonRegCustomerDto.setPhoneNo("123446");
	  nonRegCustomerDto = customerService.newregistration(nonRegCustomerDto);
	  
	  //call transaction update method for non register person txn records.
	  List<NonRegisterWallet> nonRegWallets = commonService.getMoneyFromTemporaryWallet(dto.getEmailId());
	  NonRegisterWallet nonRegWallet = nonRegWallets.get(0);
	  commonService.updateTemporaryWalletRecord(nonRegWallet);
	  sendMoneyService.updateSendMoneyForNonRegisters(nonRegWallet.getTxnId(), nonRegCustomerDto.getAuthenticationId());
   
	 }
	
	 @Test
	 public void isJobNameExistTest() throws WalletException {
		
		UserWallet userWallet = null;
		 userWallet = commonService.createUserWallet(1L, 1L);
		 userWallet.setAmount(100000.0);
		 commonService.updateUserWallet(userWallet);
		 userWallet = commonService.getUserWallet(userWallet.getAuthId(), 1L);
			 if(userWallet != null){
			  userWallet.setAmount(100000.00);
			  commonService.updateUserWallet(userWallet);
			  }
			    
		   SendMoneyDto sendMoneyDto = getSendMoneyDto();
		   sendMoneyDto.setUserJobName("Create Send Money");
		   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);
		   
		   Boolean sendMoneyJobFlag = sendMoneyService.isJobNameExist(sendMoneyDto.getSenderAuthId(), sendMoneyDto.getUserJobName());
		   Assert.assertNotNull(sendMoneyDto);
		   Assert.assertNotNull(sendMoneyJobFlag);	  	
		   Assert.assertEquals(sendMoneyJobFlag, Boolean.FALSE);
	 }
	 
	@Test
	public void cancelTransactionTest() throws WalletException { 
		  
		  List<Object> data = DataPreparation.getMasterWalletsData();
		     Iterator<Object> iter = data.iterator();
		     while(iter.hasNext()){
		      hibernateTemplate.save(iter.next());
		     }
		     
		  CustomerDto dto = DataPreparation.getCustomerDto();  
		  dto = customerService.newregistration(dto);
		  
		  dto.setStatus(1L);
		  dto.setStatusName("1");
		  customerService.updateCustomerDetails(dto);
		  
		  UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
		    if(userWallet != null){
		     userWallet.setAmount(100000.00);
		     commonService.updateUserWallet(userWallet);
		  }
		  
		  SendMoneyDto sendMoneyDto = new SendMoneyDto();
		  
		  sendMoneyDto.setEmailId(dto.getEmailId());
		  sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		  sendMoneyDto.setRequestedAmount(20000.00);
		  sendMoneyDto.setRequestedCurrency(1L);
		  sendMoneyDto.setActualAmount(20000.00);
		  sendMoneyDto.setActualCurrency(1L);
		  sendMoneyDto.setMessage("hai");
		  sendMoneyDto.setDestinationType(4L);
		  sendMoneyDto.setRecurring(false);
		  sendMoneyDto.setCountryId(1L);
		  sendMoneyDto.setTransactionType(WalletTransactionTypes.P_TO_NP);
		  sendMoneyDto.setRequestDate(new Date());
		  sendMoneyDto.setCurrencyCode("USD");
		  sendMoneyDto.setTypeOfRequest(2L);
		  
		  SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		  Assert.assertNotNull(sendMoneyToSingle);
		  
		  CustomerDto nonRegCustomerDto = DataPreparation.getCustomerDto();
		  nonRegCustomerDto.setEmailId("abcd@tarangtech.com");
		  nonRegCustomerDto.setPhoneCode("22");
		  nonRegCustomerDto.setPhoneNo("123446");
		  nonRegCustomerDto = customerService.newregistration(nonRegCustomerDto);
		  
		  //call transaction update method for non register person txn records.
		  List<NonRegisterWallet> nonRegWallets = commonService.getMoneyFromTemporaryWallet(dto.getEmailId());
		  NonRegisterWallet nonRegWallet = nonRegWallets.get(0);
		  commonService.updateTemporaryWalletRecord(nonRegWallet);
		  sendMoneyService.updateSendMoneyForNonRegisters(nonRegWallet.getTxnId(), nonRegCustomerDto.getAuthenticationId());
		  
		  transactionWalletService.cancelTransaction(nonRegWallet.getTxnId());
	}
	
	@Test
	public void rejectTransactionTest() throws WalletException { 
		  
		  List<Object> data = DataPreparation.getMasterWalletsData();
		     Iterator<Object> iter = data.iterator();
		     while(iter.hasNext()){
		      hibernateTemplate.save(iter.next());
		     }
		     
		  CustomerDto dto = DataPreparation.getCustomerDto();  
		  dto = customerService.newregistration(dto);
		  
		  dto.setStatus(1L);
		  dto.setStatusName("1");
		  customerService.updateCustomerDetails(dto);
		  
		  UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
		    if(userWallet != null){
		     userWallet.setAmount(100000.00);
		     commonService.updateUserWallet(userWallet);
		  }
		  
		  SendMoneyDto sendMoneyDto = new SendMoneyDto();
		  
		  sendMoneyDto.setEmailId(dto.getEmailId());
		  sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		  sendMoneyDto.setRequestedAmount(20000.00);
		  sendMoneyDto.setRequestedCurrency(1L);
		  sendMoneyDto.setActualAmount(20000.00);
		  sendMoneyDto.setActualCurrency(1L);
		  sendMoneyDto.setMessage("hai");
		  sendMoneyDto.setDestinationType(4L);
		  sendMoneyDto.setRecurring(false);
		  sendMoneyDto.setCountryId(1L);
		  sendMoneyDto.setTransactionType(WalletTransactionTypes.P_TO_NP);
		  sendMoneyDto.setRequestDate(new Date());
		  sendMoneyDto.setCurrencyCode("USD");
		  sendMoneyDto.setTypeOfRequest(2L);
		  
		  SendMoneyDto sendMoneyToSingle = sendMoneyService.createSendMoney(sendMoneyDto);
		  Assert.assertNotNull(sendMoneyToSingle);
		  
		  CustomerDto nonRegCustomerDto = DataPreparation.getCustomerDto();
		  nonRegCustomerDto.setEmailId("abcd@tarangtech.com");
		  nonRegCustomerDto.setPhoneCode("22");
		  nonRegCustomerDto.setPhoneNo("123446");
		  nonRegCustomerDto = customerService.newregistration(nonRegCustomerDto);
		  
		  //call transaction update method for non register person txn records.
		  List<NonRegisterWallet> nonRegWallets = commonService.getMoneyFromTemporaryWallet(dto.getEmailId());
		  NonRegisterWallet nonRegWallet = nonRegWallets.get(0);
		  commonService.updateTemporaryWalletRecord(nonRegWallet);
		  sendMoneyService.updateSendMoneyForNonRegisters(nonRegWallet.getTxnId(), nonRegCustomerDto.getAuthenticationId());
		  
		  transactionWalletService.rejectTransaction(nonRegWallet.getTxnId());
	}
	  
   private SendMoneyDto getSendMoneyDto() throws WalletException {
		 List<Object> data = DataPreparation.getMasterWalletsData();
	     Iterator<Object> iter = data.iterator();
	     while(iter.hasNext()){
	     hibernateTemplate.save(iter.next());
	     }	
		CustomerDto dto = DataPreparation.getCustomerDto();
		dto = customerService.newregistration(dto);
		
		dto.setStatus(1L);
		dto.setStatusName("1");
		customerService.updateCustomerDetails(dto);
		
		CustomerDto dto2 = DataPreparation.getCustomerDto();
		dto2.setEmailId("kedarnathd@tarangtech.com");
		dto2.setPhoneCode("22");
		dto2.setPhoneNo("123446");
		dto2 = customerService.newregistration(dto2);
		
		dto2.setStatus(1L);
		dto2.setStatusName("1");
		customerService.updateCustomerDetails(dto2);
		
		SendMoneyDto sendMoneyDto = new SendMoneyDto();
		
		sendMoneyDto.setEmailId(dto.getEmailId());
		sendMoneyDto.setSenderAuthId(dto.getAuthenticationId());
		sendMoneyDto.setReceiverAuthId(dto2.getAuthenticationId());
		sendMoneyDto.setRequestedAmount(100.00);
		sendMoneyDto.setRequestedCurrency(1L);
		sendMoneyDto.setActualAmount(100.00);
		sendMoneyDto.setActualCurrency(1L);
		sendMoneyDto.setMessage("hai");
		sendMoneyDto.setDestinationType(1L);
		sendMoneyDto.setRecurring(false);
		sendMoneyDto.setCountryId(1L);
		sendMoneyDto.setTransactionType(WalletTransactionTypes.P_TO_P);
		sendMoneyDto.setRequestDate(new Date());
		sendMoneyDto.setCurrencyCode("USD");
		sendMoneyDto.setTypeOfRequest(2L);
		sendMoneyDto.setImeiNumber("4444");
		sendMoneyDto.setSenderUserType(1L);
		sendMoneyDto.setStatus(Boolean.TRUE);
		sendMoneyDto.setId(1L);
		sendMoneyDto.setTransactionId(1L);
		return sendMoneyDto;
   }
}

