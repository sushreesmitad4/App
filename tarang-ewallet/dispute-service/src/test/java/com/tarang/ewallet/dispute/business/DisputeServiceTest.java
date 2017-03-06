/**
 * 
 */
package com.tarang.ewallet.dispute.business;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.tarang.ewallet.dispute.business.DisputeService;
import com.tarang.ewallet.dto.CustomerDto;
import com.tarang.ewallet.dto.DisputeDto;
import com.tarang.ewallet.dto.DisputeGridDto;
import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.dto.MerchantDto;
import com.tarang.ewallet.dto.RequestMoneyDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.RequestMoney;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.transaction.business.RequestMoneyService;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author vasanthar
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class DisputeServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private DisputeService disputeService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private RequestMoneyService requestMoneyService;
	
	@Autowired
	private FeeMgmtService feeService;
	
	@Autowired
    private HibernateTemplate hibernateTemplate;
	
	@Test
	public void createDisputeTest() throws WalletException {
		DisputeDto disputeDto = new DisputeDto();
		DataPreparation.prepareDisputeDto(disputeDto);
		DisputeDto dispute = disputeService.createDispute(disputeDto);		
		Assert.assertNotNull( dispute );
		Assert.assertEquals(disputeDto.getApprovedamount(), dispute.getApprovedamount());
	}
	
	@Test
	public void getDisputesForCustomerTest() throws WalletException{
		CustomerDto dto = DataPreparation.getCustomerDto();	
		dto = customerService.newregistration(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull( dto.getId() );
		MerchantDto merchantDto = DataPreparation.getMerchantDto();
		merchantDto = merchantService.newregistration(merchantDto);
		Assert.assertNotNull(merchantDto.getId());
		
		List<Object> data = getMasterWalletsData();
    	Iterator<Object> iter = data.iterator();
    	while(iter.hasNext()){
    		hibernateTemplate.save(iter.next());
    	}
		
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
		reDto.setRequesterEmail(dto.getEmailId());
		RequestMoney receiveMoney = requestMoneyService.acceptRequestMoney(reDto);
		Assert.assertNotNull(receiveMoney.getId());
		Assert.assertNotNull(receiveMoney.getTransactionId());
		System.out.println("Transaction Id is : "+receiveMoney.getTransactionId());
		Assert.assertEquals(rMoney.getId(), receiveMoney.getId());
		Assert.assertEquals(rMoney.getStatus(), receiveMoney.getStatus());
		Assert.assertEquals(rMoney.getAmount(),receiveMoney.getAmount());	
		Assert.assertEquals(rMoney.getResponserMsg(), receiveMoney.getResponserMsg());	
		Assert.assertEquals(new Long(1), receiveMoney.getAttempts());
		
		DisputeDto disputeDto = new DisputeDto();
		DataPreparation.prepareDisputeDto(disputeDto);
		DisputeDto dispute = disputeService.createDispute(disputeDto);		
		Assert.assertNotNull( dispute );
		
		Date fromDate = DateConvertion.stirngToDate("11/11/2012");//MM/dd/YYYY;
    	Date toDate = new Date();//MM/dd/YYYY;
    	
    	List<DisputeGridDto> list = disputeService.getDisputesForCustomer(2, 1L, fromDate, toDate, 1L, 1L, 2L);
    	Assert.assertNotNull( list );
    	
    	list = disputeService.getDisputesForMerchant(2, 1L, fromDate, toDate, 1L, 1L, 2L);
    	Assert.assertNotNull( list );
    	
    	list = disputeService.getDisputesForAdmin(2, 1L, fromDate, toDate, 1L, 1L, 2L);	
    	Assert.assertNotNull( list );
 		
		list = disputeService.getCustomerTxnsForRaisedispute(2, fromDate, toDate, 2L, 1L, 2L);	
		Assert.assertNotNull( list );
		
		//list = disputeService.getCustomerRaiseOrUpdateDispute(2L, 1L);
		//Assert.assertNotNull( list );		
		
		//list = disputeService.getAdmineOrMerchantUpdateDispute(2L, 1L);
		//Assert.assertNotNull( list );

	}
	
	
	private static List<Object> getMasterWalletsData(){
		List<Object> list = new ArrayList<Object>();
		
		// MasterAmountWallet (id, currency, amount)
		list.add(new MasterAmountWallet(1L, 1L, 0.0));
		list.add(new MasterAmountWallet(2L, 2L, 0.0));
		list.add(new MasterAmountWallet(3L, 3L, 0.0));

		// MasterFeeWallet( id, currency, fee)
		list.add(new MasterFeeWallet(1L, 1L, 0.0));
		list.add(new MasterFeeWallet(2L, 2L, 0.0));
		list.add(new MasterFeeWallet(3L, 3L, 0.0));

		// MasterTaxWallet( id, currency, tax)
		list.add(new MasterTaxWallet(1L, 1L, 0.0));
		list.add(new MasterTaxWallet(2L, 2L, 0.0));
		list.add(new MasterTaxWallet(3L, 3L, 0.0));

		return list;
	}
	
	@Test
	public void getDisputeByIdTest() throws WalletException {
		DisputeDto disputeDto = new DisputeDto();
		DataPreparation.prepareDisputeDto(disputeDto);
		DisputeDto dispute = disputeService.createDispute(disputeDto);
		Assert.assertNotNull( dispute );
		Assert.assertEquals(disputeDto.getApprovedamount(), dispute.getApprovedamount());
		
		DisputeDto newDisputeDto = disputeService.getDisputeById(dispute.getId());
		Assert.assertNotNull( newDisputeDto );
		Assert.assertNotNull( newDisputeDto.getDtomessages() );
		System.out.println("Size ::: "+newDisputeDto.getDtomessages().size());
		
	}
	
	   @Test
	    public void getDisputesForCustomerTest1() throws WalletException{
		   	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
	    	Date toDate = DateConvertion.stirngToDate("02/29/2013");//MM/dd/YYYY;
	    	List<DisputeGridDto> list = disputeService.getDisputesForCustomer(50, 1L, fromDate, toDate, 10L, 35L, 1L);
	    	System.out.println("size of records: " + list.size());
	    	/*for(DisputeDto model: list){
				System.out.println("\n");
				model.displayCommonData();
			}*/
	    }
	   
	   @Test
	    public void getDisputesForMerchantTest() throws WalletException{
		   	Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
	    	Date toDate = DateConvertion.stirngToDate("02/28/2013");//MM/dd/YYYY;
	    	List<DisputeGridDto> list = disputeService.getDisputesForMerchant(50, 1L, fromDate, toDate, 35L, 10L, 1L);
	    	System.out.println("size of records: " + list.size());
	    	/*for(DisputeDto model: list){
				System.out.println("\n");
				model.displayCommonData();
			}*/
	    }
	   
	   @Test
	    public void getAdmineOrMerchantUpdateDisputeTest() throws WalletException{
		   /*List<DisputeGridDto> list = disputeService.getAdmineOrMerchantUpdateDispute(1L, 192L);
	    	System.out.println("size of records: " + list.size());
	    	for(DisputeDto model: list){
				System.out.println("\n");
				model.displayCommonData();
			}*/
	    }
	   
	   @Test
	    public void getCustomerTxnsForRaisedisputeTest() throws WalletException{
			Date fromDate = DateConvertion.stirngToDate("01/01/2013");//MM/dd/YYYY;
	    	Date toDate = DateConvertion.stirngToDate("02/29/2013");//MM/dd/YYYY;
		   List<DisputeGridDto> list = disputeService.getCustomerTxnsForRaisedispute(50, fromDate, toDate, 10L, 35L, 2L);
	    	System.out.println("size of records: " + list.size());
	    	/*for(DisputeDto model: list){
				System.out.println("\n");
				model.displayCommonData();
			}*/
	    }
	   
	   @Test
	   public void getMerchantToPayStatusDisputeIdsTest() throws WalletException{
		   	DisputeDto disputeDto = new DisputeDto();
		   	disputeDto.setTransactionId(1L);
		   	disputeDto.setApprovedAmount(1000.00);
		   	disputeDto.setApprovedamount(1000.00);
			disputeDto.setApprovedcurrency("1");
			disputeDto.setDisputetype("2");
			disputeDto.setMessage("dispute message");
			disputeDto.setPayeeemailid("vasanthar@tarangtech.com");
			disputeDto.setPayeremailid("kedarnathd@tarangtech.com");
			disputeDto.setRequestamount(1000.00);
			disputeDto.setRequestAmount(1000.00);
			disputeDto.setRequestcurrency("1");
			disputeDto.setRequestCurrency(1L);
			disputeDto.setType(2L);
			disputeDto.setStatus(3L);
			disputeDto.setTxnid(new BigDecimal("1"));
			disputeDto.setMessage("message_one");
			//disputeDto.setCreator(1L);
			disputeDto.setUpdationdate(DateConvertion.stirngToDate("02/01/2013"));
			disputeDto.setCreator(new BigDecimal(GlobalLitterals.CUSTOMER_USER_TYPE_ID.toString()));
			disputeDto.setCreationdate(new Date());	
		    disputeService.createDispute(disputeDto);
		    List<Long> list = disputeService.getMerchantToPayStatusDisputeIds(3L, DateConvertion.stirngToDate("02/05/2013"));
		    Assert.assertNotNull(list);
		    //System.out.println(list.get(0));
		   
	   }
	   @Test
	   public void isDisputeExistForTxnIdTest() throws WalletException{
		   
		    Boolean flag = disputeService.isDisputeExistForTxnId(1703L);
		    Assert.assertNotNull(flag);
		    System.out.println(flag);
		   
	   }
	   /*
	   @Test
	   public void updateDisputeTest()throws WalletException{
		   
		   CustomerDto dto = DataPreparation.getCustomerDto();		
		   dto = customerService.registration(dto);
		   UserWallet userWallet = commonService.getUserWallet(dto.getAuthenticationId(), 1L);
			if(userWallet != null){
				userWallet.setAmount(100000.00);
				commonService.updateUserWallet(userWallet);
			}
			MerchantDto merchantDto = DataPreparation.getMerchantDto();
			merchantDto = merchantService.registration(merchantDto);
			Assert.assertNotNull(merchantDto.getId());
			
			List<Object> data = getMasterWalletsData();
	    	Iterator<Object> iter = data.iterator();
	    	while(iter.hasNext()){
	    		hibernateTemplate.save(iter.next());
	    	}
	    	
		   SendMoneyDto sendMoneyDto = DataPreparation.getSendMoneyDto(dto, merchantDto);
		   sendMoneyDto = sendMoneyService.createSendMoney(sendMoneyDto);

		   DisputeDto disputeDto = new DisputeDto();
		   DataPreparation.prepareDisputeDto(disputeDto);
		   DisputeDto dispute = disputeService.createDispute(disputeDto);		
		   disputeDto =  disputeService.updateDispute(dispute.getId(), DisputeStatusConstants.APPROVED, "approved", 2L, dispute.getApprovedamount());
		   Assert.assertNotNull(disputeDto);
		   Assert.assertEquals(dispute.getId(),disputeDto.getId());
	   }
	*/
}
