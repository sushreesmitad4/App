package com.tarang.ewallet.usermgmt.business;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.tarang.ewallet.dto.FeeDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.util.QueryFilter;

/**
 * @author  : prasadj
 * @date    : Oct 17, 2012
 * @time    : 6:34:19 PM
 * @version : 1.0.0
 * @comments: 
 *
 */
@ContextConfiguration(locations = { "classpath*:/wallet-applicationContext.xml", 
		"classpath*:/wallettest-applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true)
public class FeeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private FeeMgmtService feeService;
	
	@Test
	public void createFeeWithSlabTest() throws WalletException{
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
	}
	
	@Test
	public void getFeeWithSlabTest() throws WalletException{
		
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		feeDto.setOperationType(1L);
		feeDto.setCountry(1L);
		feeDto.setCurrency(1L);
		FeeDto saveFeeDto = feeService.createFee(feeDto);
		
		FeeDto feeDto3 = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto3);
		feeDto3.setOperationType(3L);
		feeDto3.setCountry(3L);
		feeDto3.setCurrency(3L);
		feeService.createFee(feeDto3);
		
		FeeDto getFeeDto = feeService.getFee(feeDto.getId());
		
		Assert.assertNotNull(getFeeDto.getId());
		Assert.assertEquals(saveFeeDto.getFeeSlabs().get(0).getFixedChargeReceiver(), getFeeDto.getFeeSlabs().get(0).getFixedChargeReceiver());
		Assert.assertEquals(saveFeeDto.getFeeSlabs().get(0).getFixedChargeSender(), getFeeDto.getFeeSlabs().get(0).getFixedChargeSender());
		
	}
	
	@Test
	public void getFeeBySearchTest() throws WalletException{
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		feeService.createFee(feeDto);
		FeeDto feeDtoSaved = feeService.getFee(2L, 2L, 2L);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
	}

	@Test
	public void createTax() throws WalletException{
		Tax tax = new Tax();
		FeeServiceTest.dataPrepareForTax(tax);
		Tax txs = feeService.createTax(tax);
		Assert.assertNotNull(txs.getId());
		Assert.assertEquals(tax.getPercentage(), txs.getPercentage());
		
	}

	@Test
	public void getTax() throws WalletException{
		Tax tax = new Tax();
		FeeServiceTest.dataPrepareForTax(tax);
		tax = feeService.createTax(tax);
		Tax txs = feeService.getTax(tax.getId());
		Assert.assertNotNull(txs);
		Assert.assertEquals(tax.getId(), txs.getId());
		Assert.assertEquals(tax.getCountry(), txs.getCountry());
		Assert.assertEquals(tax.getPercentage(), txs.getPercentage());
		
	}
	
	@Test
	public void updateFeeTest() throws WalletException{
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		feeDto = feeService.createFee(feeDto);
		FeeServiceTest.dataPrepUpdateForFeeDto(feeDto);
		feeService.updateFee(feeDto);
		FeeDto updatedDto = feeService.getFee(feeDto.getId());
		Assert.assertEquals(feeDto.getId(), updatedDto.getId());
		Assert.assertEquals(feeDto.getUserType(), updatedDto.getUserType());
		Assert.assertEquals(feeDto.getServices(), updatedDto.getServices());
		Assert.assertEquals(feeDto.getOperationType(), updatedDto.getOperationType());
		Assert.assertEquals(feeDto.getCountry(), updatedDto.getCountry());
		Assert.assertEquals(feeDto.getCurrency(), updatedDto.getCurrency());
		Assert.assertEquals(feeDto.getPayingentity(), updatedDto.getPayingentity());
		Assert.assertEquals(feeDto.getFeeType(), updatedDto.getFeeType());
		Assert.assertEquals(feeDto.getFixCharSen(), updatedDto.getFixCharSen());
		Assert.assertEquals(feeDto.getPercentageSen(), updatedDto.getPercentageSen());
		Assert.assertEquals(feeDto.getFixCharRec(), updatedDto.getFixCharRec());
		Assert.assertEquals(feeDto.getPercentageRec(), updatedDto.getPercentageRec());
		Assert.assertEquals(feeDto.getFeeSlabs().get(0).getFixedChargeReceiver(), updatedDto.getFeeSlabs().get(0).getFixedChargeReceiver());
	}
	
	@Test
	public void deleteFee() throws WalletException{
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		FeeDto feeDtoSaved = feeService.createFee(feeDto);
		Assert.assertNotNull(feeDtoSaved.getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(0).getId());
		Assert.assertNotNull(feeDtoSaved.getFeeSlabs().get(1).getId());
		
		Boolean flag = feeService.deleteFee(feeDtoSaved.getId());
		Assert.assertEquals(Boolean.TRUE, flag);
	}
	
	@Test
	public void deleteFeeWithNull() throws WalletException{
		Boolean flag = feeService.deleteFee(null);
		Assert.assertEquals(Boolean.FALSE, flag);
	}
	
	@Test
	public void feeList() throws WalletException{
		FeeDto feeDto = new FeeDto();
		FeeServiceTest.dataPrepCreateForFeeDto(feeDto);
		feeService.createFee(feeDto);
		QueryFilter qf = new QueryFilter();
		qf.setPage(1);
		qf.setRows(10);
		qf.setSidx("services");
		qf.setSord("desc");
		
		List<FeeDto> listFeeDto=feeService.feeList(2L);
		Assert.assertEquals(listFeeDto.size(),1);
	}
	
	@Test
	public void updateTaxTest() throws WalletException{
		Tax tax = new Tax();
		FeeServiceTest.dataPrepareForTax(tax);
		tax = feeService.createTax(tax);
		feeService.updateTax(tax);
		Tax updatedDto = feeService.getTax(tax.getId());
		
		Assert.assertEquals(tax.getId(), updatedDto.getId());
		Assert.assertEquals(tax.getCountry(), updatedDto.getCountry());
	}
	
	@Test
	public void getTaxsTest() throws WalletException{
		Tax tax = new Tax();
		FeeServiceTest.dataPrepareForTax(tax);
		tax = feeService.createTax(tax);
		
		List<Tax> list = feeService.getTaxs();
		Assert.assertEquals(list.size(), 1);
	}

	@Test
	public void getTaxByCountryTest() throws WalletException{
		Tax tax = new Tax();
		FeeServiceTest.dataPrepareForTax(tax);
		tax = feeService.createTax(tax);
		
		Tax taxByCountry = feeService.getTaxByCountry(tax.getCountry());
		Assert.assertEquals(tax.getPercentage(),taxByCountry.getPercentage());
	}
	
	@Test
	public void calcuateDeductionsTest() throws WalletException{
		Double amount = 0.11292999996120853;
		Long countryId = 1L;
		Long transType = 32L;
		Long currencyId = 1L ;
		Boolean domestic = true ;
		
		Double calDeductions = feeService.calcuateDeductions(amount, countryId, currencyId, transType, domestic);
		Assert.assertNotNull(calDeductions);
	}
	
	private static void dataPrepUpdateForFeeDto(FeeDto feeDto){
		feeDto.setUserType(2L);
		feeDto.setServices(2L);
		feeDto.setOperationType(2L);
		feeDto.setCountry(2L);
		feeDto.setCurrency(2L);
		feeDto.setPayingentity(2L);
		feeDto.setFeeType(2L);
		feeDto.setFixCharSen(666.666);
		feeDto.setPercentageSen(666.666);
		feeDto.setFixCharRec(666.666);
		feeDto.setPercentageRec(666.666);
		List<FeeSlab> fsList = new ArrayList<FeeSlab>();
		fsList.add(new FeeSlab(6.0, 7.0, 8.0, 9.0, 10.0, 11.0));
		fsList.add(new FeeSlab(12.0, 13.0, 14.0, 15.0, 16.0, 17.0));
		feeDto.setFeeSlabs(fsList);
	}
	
	private static void dataPrepCreateForFeeDto(FeeDto feeDto){
		feeDto.setUserType(2L);
		feeDto.setServices(2L);
		feeDto.setOperationType(2L);
		feeDto.setCountry(2L);
		feeDto.setCurrency(2L);
		feeDto.setPayingentity(2L);
		feeDto.setFeeType(2L);
		feeDto.setFixCharSen(666.666);
		feeDto.setPercentageSen(666.666);
		feeDto.setFixCharRec(666.666);
		feeDto.setPercentageRec(666.666);
	
		List<FeeSlab> fsList = new ArrayList<FeeSlab>();
		fsList.add(new FeeSlab(0.0, 100.0, 500.0, 10.0, 500.0, 10.0));
		fsList.add(new FeeSlab(100.0, 1000.0, 1000.0, 20.0, 1000.0, 20.0));
		feeDto.setFeeSlabs(fsList);
	}
	
	private static void dataPrepareForTax(Tax tax){
		tax.setCountry(1L);
		tax.setPercentage(99.99);
	}

}
