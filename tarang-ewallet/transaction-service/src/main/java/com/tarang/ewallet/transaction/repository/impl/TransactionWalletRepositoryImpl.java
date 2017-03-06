/**
 * 
 */
package com.tarang.ewallet.transaction.repository.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.tarang.ewallet.common.business.CommonService;
import com.tarang.ewallet.customer.service.CustomerService;
import com.tarang.ewallet.dto.PaymentDto;
import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.feemgmt.business.FeeMgmtService;
import com.tarang.ewallet.merchant.business.MerchantService;
import com.tarang.ewallet.model.Address;
import com.tarang.ewallet.model.Authentication;
import com.tarang.ewallet.model.Customer;
import com.tarang.ewallet.model.FeeSlab;
import com.tarang.ewallet.model.MasterAmountWallet;
import com.tarang.ewallet.model.MasterFeeWallet;
import com.tarang.ewallet.model.MasterTaxWallet;
import com.tarang.ewallet.model.Merchant;
import com.tarang.ewallet.model.Payment;
import com.tarang.ewallet.model.Tax;
import com.tarang.ewallet.model.UserWallet;
import com.tarang.ewallet.model.WalletFee;
import com.tarang.ewallet.model.WalletTax;
import com.tarang.ewallet.model.WalletTransaction;
import com.tarang.ewallet.transaction.dao.TransactionWalletDao;
import com.tarang.ewallet.transaction.repository.TransactionWalletRepository;
import com.tarang.ewallet.transaction.repository.WalletFeeTaxRepository;
import com.tarang.ewallet.transaction.util.CurrencyConversion;
import com.tarang.ewallet.transaction.util.ReversalType;
import com.tarang.ewallet.transaction.util.WalletPayerEntity;
import com.tarang.ewallet.transaction.util.WalletTransactionStatus;
import com.tarang.ewallet.transaction.util.WalletTransactionTypes;
import com.tarang.ewallet.transaction.util.WalletTransactionUtil;
import com.tarang.ewallet.util.FeeUtil;
import com.tarang.ewallet.util.GlobalLitterals;


/**
 * @author  : prasadj
 * @date    : Jan 12, 2013
 * @time    : 10:44:15 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
public class TransactionWalletRepositoryImpl implements TransactionWalletRepository, GlobalLitterals {

	private static final Logger LOGGER = Logger.getLogger(TransactionWalletRepositoryImpl.class);
	
	private static final String ERROR_KEY_1 = "masteramount.wallet.not.available";
	
	private static final String ERROR_KEY_2 = "user.wallet.not.available";
	
	private static final String ERROR_KEY_3 = "mastertax.wallet.not.available";
	
	private static final String ERROR_KEY_4 = "cancel.transaction.unknown.ex";
	
	private static final String ERROR_KEY_5 = "cancel.transaction.not.support";
	
	private static final String ERROR_KEY_6 = "masterfee.wallet.not.available";
	
	private TransactionWalletDao transactionWalletDao;
	
	private CustomerService customerService;
	
	private MerchantService merchantService;
	
	private CommonService commonService;
		
	private FeeMgmtService feeMgmtService;
	
	private WalletFeeTaxRepository walletFeeTaxRepository;
	
	TransactionWalletRepositoryImpl(TransactionWalletDao transactionWalletDao, CustomerService customerService,  
			MerchantService merchantService, CommonService commonService, FeeMgmtService feeMgmtService, WalletFeeTaxRepository walletFeeTaxRepository){
		this.transactionWalletDao = transactionWalletDao;
		this.customerService = customerService;
		this.merchantService = merchantService;
		this.commonService = commonService;
		this.feeMgmtService = feeMgmtService;
		this.walletFeeTaxRepository = walletFeeTaxRepository;
		
	}

	@Override
	public WalletTransaction initiateTransaction(WalletTransaction transaction) throws WalletException {
		
		// get the country based on the authid
		// if authid is null ignore corresponding fee and tax directly assign 0.0 as fee and tax
		// get the feedto by passing transaction type, sender country, sender currency, and is international
		// calculate sender fee and tax in sender transaction currency 
		// get the conversion rate from CurrencyConverter
		// calculate receiver fee and tax in receiver transaction currency  
		// update wallet(add amount based on the conversion rate), add entry in wallet fee(sender fee) and add entry in wallet tax(sender tax)
		// update sender wallet with amount + fee + tax( by deducting)
		// update sender balance in transaction
		// add entry in transaction table with above updates
		
		Long payercountryId = null;
		Long payeecountryId = null;
		Double convertionRate = null;
		Double payerBalance = null;
		
		if(transaction.getPayer() != null){
			payercountryId = getCountryIdByAuthId(transaction.getPayer());
		}
		if(transaction.getPayee() != null){
			payeecountryId = getCountryIdByAuthId(transaction.getPayee());
		}

		Long feeTaxCountryId = payercountryId;
		
		if(null == feeTaxCountryId){
			feeTaxCountryId = payeecountryId;
		}
		
		if(transaction.getPayerCurrency() != null && transaction.getPayeeCurrency() != null){
			convertionRate = CurrencyConversion.getInstance().getConvertionFactor(transaction.getPayerCurrency(), transaction.getPayeeCurrency());
		}else {
			convertionRate = 1.0;
		}
		
		Double destinationCurrencyAmount = transaction.getPayerAmount()*convertionRate;

		Double payerFee = ZERO_DOUBLE;
		Double payerTax = ZERO_DOUBLE;
		Double payeeFee = ZERO_DOUBLE;
		Double payeeTax = ZERO_DOUBLE;
		
		//calculate payer & payee of fee & tax
		if(feeTaxCountryId != null){
			FeeSlab feeSlab = FeeUtil.getFeeSlabs(feeMgmtService.getFee(transaction.getTypeOfTransaction(), feeTaxCountryId, 
					transaction.getPayerCurrency() ), transaction.getPayerAmount());
			if(transaction.getPayer() != null && feeSlab != null){
				if(feeSlab.getPercentageOfSender() != null && !feeSlab.getPercentageOfSender().equals(ZERO_DOUBLE)){
					payerFee = transaction.getPayerAmount() * feeSlab.getPercentageOfSender()/PERCENTAGE_FACTOR;
				}
				if(feeSlab.getFixedChargeSender() != null && !feeSlab.getFixedChargeSender().equals(ZERO_DOUBLE)){
					payerFee += feeSlab.getFixedChargeSender();
				}
			}
			if(transaction.getPayee() != null && feeSlab != null){
				if(feeSlab.getPercentageOfReceiver() != null && !feeSlab.getPercentageOfReceiver().equals(ZERO_DOUBLE)){
					payeeFee = destinationCurrencyAmount * feeSlab.getPercentageOfReceiver()/PERCENTAGE_FACTOR;
				}
				if(feeSlab.getFixedChargeReceiver() != null && !feeSlab.getFixedChargeReceiver().equals(ZERO_DOUBLE)){
					payeeFee += feeSlab.getFixedChargeReceiver();
				}
			}
			
			if(payerFee > ZERO_DOUBLE){
				Tax tax = feeMgmtService.getTaxByCountry(payercountryId);
				if(tax != null){
					payerTax = payerFee*tax.getPercentage()/PERCENTAGE_FACTOR;
				}
			}
			if(payeeFee > ZERO_DOUBLE){
				Tax tax = feeMgmtService.getTaxByCountry(payeecountryId);
				if(tax != null){
					payeeTax = payeeFee*tax.getPercentage()/PERCENTAGE_FACTOR;
				}
			}
		}
		
		if(transaction.getPayer() != null){
			//update payer wallet
			UserWallet userWallet = commonService.getUserWallet(transaction.getPayer(), transaction.getPayerCurrency());
			if(userWallet != null){
				double taxC =0.0;
				double deductAmount = transaction.getPayerAmount() + payerFee + payerTax;
				userWallet.deductAmount(deductAmount);				
				
				if (String.valueOf(payerTax).contains("E")) {

					String t = WalletTransactionUtil.convertFromScientificNotation(payerTax);

					taxC = Double.valueOf(t);
					userWallet.deductAmount(transaction.getPayerAmount() + payerFee + taxC);
				}
				payerBalance = userWallet.getAmount();
				commonService.updateUserWallet(userWallet);
			}else {
				throw new WalletException(ERROR_KEY_2);
			}
		}
		
		// update master amount wallet
		MasterAmountWallet aw = commonService.getMasterAmountWallet(transaction.getPayeeCurrency());
		if( aw != null){
			aw.addAmount(destinationCurrencyAmount);
			commonService.updateMasterAmountWallet(aw);
		}else {
			throw new WalletException(ERROR_KEY_1);
		}
		
		transaction.setCreationDate(new Date());
		transaction.setUpdatededDate(new Date());
		transaction.setStatus(WalletTransactionStatus.PENDING);
		transaction.setConvertionRate(convertionRate);
		transaction.setPayerFee(payerFee);
		transaction.setPayerTax(payerTax);
		transaction.setPayeeFee(payeeFee);
		transaction.setPayeeTax(payeeTax);
		transaction.setPayerBalance(payerBalance);
		WalletTransaction tempTx = transactionWalletDao.createTransaction(transaction);
		
		if(payerFee > ZERO_DOUBLE){
			// add fee in master fee table
			MasterFeeWallet fw = commonService.getMasterFeeWallet(transaction.getPayerCurrency());
			if( fw != null){
				fw.addFee(payerFee);
				commonService.updateMasterFeeWallet(fw);
			}else {
				throw new WalletException(ERROR_KEY_6);
			}
			// add fee entry
			WalletFee walletFee = new WalletFee();
			walletFee.setTransactionId(tempTx.getId());
			walletFee.setAmount(payerFee);
			walletFee.setCountry(feeTaxCountryId);
			walletFee.setCurrency(tempTx.getPayerCurrency());
			walletFee.setType(WalletPayerEntity.SENDER);
			walletFee.setPayDate(transaction.getCreationDate());
			walletFee.setReversal(false);
			walletFeeTaxRepository.addFee(walletFee);
		}
		
		if(payerTax > ZERO_DOUBLE){
			// add tax in master tax table
			MasterTaxWallet tw = commonService.getMasterTaxWallet(transaction.getPayerCurrency());
			if( tw != null){
				tw.addTax(payerTax); 
				commonService.updateMasterTaxWallet(tw);
			}else {
				throw new WalletException(ERROR_KEY_3);
			}
			// add tax entry
			WalletTax walletTax = new WalletTax();
			walletTax.setTransactionId(tempTx.getId());
			walletTax.setAmount(payerTax); 
			walletTax.setCountry(feeTaxCountryId);
			walletTax.setCurrency(tempTx.getPayerCurrency());
			walletTax.setType(WalletPayerEntity.SENDER);
			walletTax.setPayDate(transaction.getCreationDate());
			walletTax.setReversal(false);
			walletFeeTaxRepository.addTax(walletTax);
		}
		
		return tempTx;
	}
	
	@Override
	public WalletTransaction settleTransaction(Long transactionId) throws WalletException {
		
		// get the initial transaction based on the transactionId
		// create settleTransaction
		// update wallet (deduct amount based on the conversion rate)
		// update receiver wallet with amount based on conversion rate - fee - tax
		// update receiver balance in settleTransaction
		// add entry in transaction table with above updates
		// add entry in wallet fee(receiver fee) and add entry in wallet tax(receiver tax) in the form of receiver currency
		
		WalletTransaction initialTx = getTransaction(transactionId);
		Double deductingAmount = initialTx.getPayerAmount()*initialTx.getConvertionRate();
		Double payeeFee = ZERO_DOUBLE;
		Double payeeTax = ZERO_DOUBLE;
		Double payeeBalance = null;
		// update master amount wallet
		MasterAmountWallet aw = commonService.getMasterAmountWallet(initialTx.getPayeeCurrency());
		if( aw != null){
			aw.deductAmount(deductingAmount);
			commonService.updateMasterAmountWallet(aw);
		}else {
			throw new WalletException(ERROR_KEY_1);
		}
		
		if(initialTx.getPayee() != null){
			
			//update payee wallet
			UserWallet userWallet = commonService.getUserWallet(initialTx.getPayee(), initialTx.getPayeeCurrency());
			if(null == userWallet){
				userWallet = commonService.createUserWallet(initialTx.getPayee(), initialTx.getPayeeCurrency());
			}
			
			if(initialTx.getPayeeFee() != null ){
				payeeFee = initialTx.getPayeeFee();
			}
				
			if( payeeFee > ZERO_DOUBLE && initialTx.getPayeeTax() != null){
				payeeTax = initialTx.getPayeeTax();
			}
			
			if(userWallet != null){
				userWallet.addAmount( deductingAmount - (payeeFee + payeeTax) );
				payeeBalance = userWallet.getAmount();
				commonService.updateUserWallet(userWallet);
			}else {
				throw new WalletException(ERROR_KEY_2);
			}
			
			Long feeTaxCountryId = null;
			
			if(initialTx.getPayer() != null){
				getCountryIdByAuthId(initialTx.getPayer());
			}
			
			if(null == feeTaxCountryId){
				feeTaxCountryId = getCountryIdByAuthId(initialTx.getPayee());
			}
			if(payeeFee > ZERO_DOUBLE){
				// add fee in master fee table
				MasterFeeWallet fw = commonService.getMasterFeeWallet(initialTx.getPayeeCurrency());
				if( fw != null){
					fw.addFee(payeeFee);
					commonService.updateMasterFeeWallet(fw);
				}else {
					throw new WalletException(ERROR_KEY_6);
				}
				// add fee entry
				WalletFee walletFee = new WalletFee();
				walletFee.setTransactionId(initialTx.getId());
				walletFee.setAmount(payeeFee);
				walletFee.setCountry(feeTaxCountryId);
				walletFee.setCurrency(initialTx.getPayeeCurrency());
				walletFee.setType(WalletPayerEntity.RECEIVER);
				walletFee.setPayDate(initialTx.getCreationDate());
				walletFee.setReversal(false);
				walletFeeTaxRepository.addFee(walletFee);
			}
			
			if(payeeTax > ZERO_DOUBLE){
				// add tax in master tax table
				MasterTaxWallet tw = commonService.getMasterTaxWallet(initialTx.getPayeeCurrency());
				if( tw != null){
					tw.addTax(payeeTax);
					commonService.updateMasterTaxWallet(tw);
				}else {
					throw new WalletException(ERROR_KEY_3);
				}
				// add tax entry
				WalletTax walletTax = new WalletTax();
				walletTax.setTransactionId(initialTx.getId());
				walletTax.setAmount(payeeTax);
				walletTax.setCountry(feeTaxCountryId);
				walletTax.setCurrency(initialTx.getPayeeCurrency());
				// it may required to convert tax in payee country default currency 
				walletTax.setType(WalletPayerEntity.RECEIVER);
				walletTax.setPayDate(initialTx.getCreationDate());
				walletTax.setReversal(false);
				walletFeeTaxRepository.addTax(walletTax);
			}
		}
		return transactionWalletDao.settleTransaction(initialTx.getId(), WalletTransactionStatus.SUCCESS, payeeBalance);
	}
	
	@Override
	public WalletTransaction cancelTransaction(Long transactionId) throws WalletException {
		return cancelOrRejectTransaction(transactionId, WalletTransactionStatus.CANCEL);
	}

	@Override
	public WalletTransaction rejectTransaction(Long transactionId) throws WalletException {
		return cancelOrRejectTransaction(transactionId, WalletTransactionStatus.REJECT);
	}

	@Override
	public WalletTransaction getTransaction(Long transactionId) {
		return transactionWalletDao.getTransaction(transactionId);
	}


	@Override
	public List<Object[]> getLastNdaysTransactionsCountDayWise(
			Date fromdate, Date todate, Long transactionType) throws WalletException {
		return transactionWalletDao.getLastNdaysTransactionsCountDayWise(fromdate, todate, transactionType);
	}
	
	private Long getCountryIdByAuthId(Long authId) throws WalletException{
		Authentication authentication = commonService.getAuthentication(authId);
		if(authentication != null && authentication.getUserType().equals(GlobalLitterals.CUSTOMER_USER_TYPE)){
			Customer customer = customerService.getCustomerByAuthId(authId);
			Address address = commonService.getAddress(customer.getAddressId());
			return address.getCountry();
		}else{
			Merchant merchant = merchantService.getMerchantByAuthId(authId);
			Address address = merchantService.getMerchantAddress(merchant.getId());
			return address.getCountry();
		}
	}

	@Override
	public void updateTransactionForNonRegisters(Long txnId, Long authId) throws WalletException {
		WalletTransaction transaction = transactionWalletDao.getTransaction(txnId);
		if(transaction != null){
			transaction.setPayee(authId);
			calucatePayeeFeeAndTaxForNonRegTxn(transaction);
		}
	}

	private WalletTransaction cancelOrRejectTransaction(Long transactionId, Long status) throws WalletException {
		WalletTransaction initialTx = getTransaction(transactionId);
		Double deductingAmount = initialTx.getPayerAmount()*initialTx.getConvertionRate();
		Double deductingFee = initialTx.getPayerFee()*initialTx.getConvertionRate();
		Double deductingTax = initialTx.getPayerTax()*initialTx.getConvertionRate();
		Double addingAmount = null;
		Long transType = initialTx.getTypeOfTransaction();
		if(initialTx.getPayee() != null || transType.equals(WalletTransactionTypes.P_TO_NP) || transType.equals(WalletTransactionTypes.M_TO_NP)){
			
			addingAmount = initialTx.getPayerAmount() + initialTx.getPayerFee() + initialTx.getPayerTax();
			
			// update master amount wallet
			MasterAmountWallet aw = commonService.getMasterAmountWallet(initialTx.getPayerCurrency());
			if( aw != null){
				aw.deductAmount(deductingAmount);
				commonService.updateMasterAmountWallet(aw);
			}else {
				throw new WalletException(ERROR_KEY_1);
			}

			MasterFeeWallet mfee = commonService.getMasterFeeWallet(initialTx.getPayerCurrency());
			if( mfee != null){
				mfee.deductFee(deductingFee);
				commonService.updateMasterFeeWallet(mfee);
			}else {
				throw new WalletException(ERROR_KEY_1);
			}
			MasterTaxWallet mtax = commonService.getMasterTaxWallet(initialTx.getPayerCurrency());
			if( mtax != null){
				mtax.deductTax(deductingTax);
				commonService.updateMasterTaxWallet(mtax);
			}else {
				throw new WalletException(ERROR_KEY_1);
			}
			
			try {
				//update payer wallet
				UserWallet userWallet = commonService.getUserWallet(initialTx.getPayer(), initialTx.getPayerCurrency());
				userWallet.addAmount(addingAmount);
				commonService.updateUserWallet(userWallet);
				
				initialTx = transactionWalletDao.cancelOrRejectTransaction(initialTx.getId(), status, userWallet.getAmount());
	
				WalletFee oldFee = walletFeeTaxRepository.getFeeByTransaction(initialTx.getId());
				if(oldFee != null){
					WalletFee reverseFee = new WalletFee();
					oldFee.populate(reverseFee);
					reverseFee.setTransactionId(initialTx.getId());
					reverseFee.setAmount(oldFee.getAmount()*(-1.0));
					reverseFee.setPayDate(initialTx.getCreationDate());
					reverseFee.setParentId(oldFee.getId());
					reverseFee.setReversal(Boolean.TRUE);
					walletFeeTaxRepository.addFee(reverseFee);
				}
				WalletTax oldTax = walletFeeTaxRepository.getTaxByTransaction(initialTx.getId());
				if(oldTax != null){
					WalletTax reverseTax = new WalletTax();
					oldTax.populate(reverseTax);
					reverseTax.setTransactionId(initialTx.getId());
					reverseTax.setAmount(oldTax.getAmount()*(-1.0));
					reverseTax.setPayDate(initialTx.getCreationDate());
					reverseTax.setParentId(oldTax.getId());
					reverseTax.setReversal(Boolean.TRUE);
					walletFeeTaxRepository.addTax(reverseTax);
				}
			}catch(Exception ex){
				LOGGER.error(ex.getMessage(), ex);
				throw new WalletException(ERROR_KEY_4, ex);
			}
		}else {
			throw new WalletException(ERROR_KEY_5);
		}
		return initialTx;
	}
		
	private void calucatePayeeFeeAndTaxForNonRegTxn(WalletTransaction transaction) throws WalletException{
		
		Long feeTaxCountryId = getCountryIdByAuthId(transaction.getPayer());
		Double destinationCurrencyAmount = transaction.getPayerAmount();
		Double payeeFee = ZERO_DOUBLE;
		Double payeeTax = ZERO_DOUBLE;
		
		//calculate payee of fee & tax
		if(feeTaxCountryId != null){
			FeeSlab feeSlab = FeeUtil.getFeeSlabs(feeMgmtService.getFee(transaction.getTypeOfTransaction(), feeTaxCountryId, 
					transaction.getPayerCurrency()), transaction.getPayerAmount());
		
			if(transaction.getPayee() != null && feeSlab != null){
				if(feeSlab.getPercentageOfReceiver() != null && !feeSlab.getPercentageOfReceiver().equals(ZERO_DOUBLE)){
					payeeFee = destinationCurrencyAmount * feeSlab.getPercentageOfReceiver()/PERCENTAGE_FACTOR;
				}
				if(feeSlab.getFixedChargeReceiver() != null && !feeSlab.getFixedChargeReceiver().equals(ZERO_DOUBLE)){
					payeeFee += feeSlab.getFixedChargeReceiver();
				}
			}
			if(payeeFee > ZERO_DOUBLE){
				Tax tax = feeMgmtService.getTaxByCountry(feeTaxCountryId);
				if(tax != null){
					payeeTax = payeeFee*tax.getPercentage()/PERCENTAGE_FACTOR;
				}
			}
		}
		transaction.setPayeeFee(payeeFee);
		transaction.setPayeeTax(payeeTax);
		
		UserWallet userWallet = commonService.getUserWallet(transaction.getPayee(), transaction.getPayeeCurrency());
		if(null == userWallet){
			userWallet = commonService.createUserWallet(transaction.getPayee(), transaction.getPayeeCurrency());
		}
		Double deductingAmount = transaction.getPayerAmount() * transaction.getConvertionRate();
		Double payeeBalance = null;
		
		if(payeeFee > ZERO_DOUBLE){
			// add fee in master fee table
			MasterFeeWallet fw = commonService.getMasterFeeWallet(transaction.getPayeeCurrency());
			if( fw != null){
				fw.addFee(payeeFee);
				commonService.updateMasterFeeWallet(fw);
			}else {
				throw new WalletException(ERROR_KEY_6);
			}
			// add fee entry
			WalletFee walletFee = new WalletFee();
			walletFee.setTransactionId(transaction.getId());
			walletFee.setAmount(payeeFee);
			walletFee.setCountry(feeTaxCountryId);
			walletFee.setCurrency(transaction.getPayeeCurrency());
			walletFee.setType(WalletPayerEntity.RECEIVER);
			walletFee.setPayDate(new Date());
			walletFee.setReversal(false);
			walletFeeTaxRepository.addFee(walletFee);
		}
		
		if(payeeTax > ZERO_DOUBLE){
			// add tax in master tax table
			MasterTaxWallet tw = commonService.getMasterTaxWallet(transaction.getPayeeCurrency());
			if( tw != null){
				tw.addTax(payeeTax);
				commonService.updateMasterTaxWallet(tw);
			}else {
				throw new WalletException(ERROR_KEY_3);
			}
			// add tax entry
			WalletTax walletTax = new WalletTax();
			walletTax.setTransactionId(transaction.getId());
			walletTax.setAmount(payeeTax);
			walletTax.setCountry(feeTaxCountryId);
			walletTax.setCurrency(transaction.getPayeeCurrency());
			// it may required to convert tax in payee country default currency 
			walletTax.setType(WalletPayerEntity.RECEIVER);
			walletTax.setPayDate(new Date());
			walletTax.setReversal(false);
			walletFeeTaxRepository.addTax(walletTax);
		}
		
		//update user wallet
		if(userWallet != null){
			userWallet.addAmount( deductingAmount - (payeeFee + payeeTax) );
			payeeBalance = userWallet.getAmount();
			transaction.setPayeeBalance(payeeBalance);
			commonService.updateUserWallet(userWallet);
		}else {
			throw new WalletException(ERROR_KEY_2);
		}
		
		// update master amount wallet
		MasterAmountWallet aw = commonService.getMasterAmountWallet(transaction.getPayeeCurrency());
		if( aw != null){
			aw.deductAmount(deductingAmount);
			commonService.updateMasterAmountWallet(aw);
		}else {
			throw new WalletException(ERROR_KEY_1);
		}
		transaction.setStatus(WalletTransactionStatus.SUCCESS);
		transactionWalletDao.updateTransaction(transaction);
	}

	@Override
	public Payment createPayment(Payment payment) throws WalletException {
		return transactionWalletDao.createPayment(payment);
	}

	@Override
	public PaymentDto customerPaymentFromMerchant(PaymentDto paymentDto) throws WalletException {
		
		WalletTransaction transaction = new WalletTransaction();
		transaction.setIpAddress(paymentDto.getIpAddress());
		transaction.setPayer(paymentDto.getCustomerAuthId());
		transaction.setPayee(paymentDto.getMerchantAuthId());
		transaction.setPayerAmount(paymentDto.getAmount());
		transaction.setPayeeAmount(paymentDto.getAmount());
		transaction.setPayerCurrency(paymentDto.getCurrencyId());
		transaction.setPayeeCurrency(paymentDto.getCurrencyId());
		transaction.setTypeOfTransaction(WalletTransactionTypes.P_TO_M);
		transaction.setTypeOfRequest(paymentDto.getTypeOfRequest());
		Payment payment =  new Payment();
		try {
			transaction = initiateTransaction(transaction);
			settleTransaction(transaction.getId());
			paymentDto.setTxnId(transaction.getId());
			paymentDto.setStatus(1);
		}catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
			paymentDto.setStatus(2);
		}
		payment.setTxnId(paymentDto.getTxnId());
		payment.setStatus(paymentDto.getStatus());
		payment.setOrderId(paymentDto.getOrderId());
		payment.setAmount(paymentDto.getAmount());
		payment.setCurrency(paymentDto.getCurrency()) ;
		payment.setCustomerAuthId( paymentDto.getCustomerAuthId());
		payment.setMerchantAuthId(paymentDto.getMerchantAuthId());
		payment.setIpAddress(paymentDto.getOrderId());
		payment.setOrderDate(new Date());
		transactionWalletDao.createPayment(payment);
		return paymentDto;
	}
	
	@Override
    public void reversalTransaction(Long transactionId, Long reversalType, Double approvedAmount) throws WalletException {
		
		WalletTransaction  walletTransactionOld = getTransaction(transactionId);
		Double amount = ZERO_DOUBLE;
		Double payerAmount = ZERO_DOUBLE;
		Long transactionType = null;
		Double credits = ZERO_DOUBLE;
		Double deductions = ZERO_DOUBLE;
		WalletFee payerwalletFee = null;
		WalletFee payeewalletFee = null;
		WalletTax payerwalletTax = null;
		WalletTax payeewalletTax = null;
		MasterFeeWallet fw = null;
		MasterTaxWallet tw = null;
			
		if(reversalType.equals(ReversalType.REFUND)){
			amount = approvedAmount;
			payerAmount = approvedAmount;
			transactionType = WalletTransactionTypes.REFUND;
			credits = approvedAmount;	
			deductions = approvedAmount;
		}else if(reversalType.equals(ReversalType.CHARGE_BACK)){
			
			amount = walletTransactionOld.getPayerAmount();
			payerAmount = walletTransactionOld.getPayeeAmount();
			transactionType = WalletTransactionTypes.CHARGE_BACK;

			credits = walletTransactionOld.getPayerAmount()
						+ walletTransactionOld.getPayerFee()
						+ walletTransactionOld.getPayerTax();	
		
			deductions = walletTransactionOld.getPayeeAmount()
						- walletTransactionOld.getPayeeFee()
						- walletTransactionOld.getPayeeTax();

			// add fee entry for payer
			if(walletTransactionOld.getPayerFee() > ZERO_DOUBLE){
				payerwalletFee = new WalletFee();
				payerwalletFee.setAmount(-walletTransactionOld.getPayerFee());
				payerwalletFee.setCountry(getCountryIdByAuthId(walletTransactionOld.getPayer()));
				payerwalletFee.setCurrency(walletTransactionOld.getPayerCurrency());
				payerwalletFee.setType(WalletPayerEntity.SENDER);
				payerwalletFee.setPayDate(new Date());
				payerwalletFee.setReversal(Boolean.TRUE);
				
				fw = commonService.getMasterFeeWallet(walletTransactionOld.getPayerCurrency());
				if( fw != null){
					fw.deductFee(walletTransactionOld.getPayerFee());
					commonService.updateMasterFeeWallet(fw);
				}else {
					throw new WalletException(ERROR_KEY_6);
				}
			}
			// add fee entry for payee
			if(walletTransactionOld.getPayeeFee() > ZERO_DOUBLE){
				payeewalletFee = new WalletFee();
				payeewalletFee.setAmount(-walletTransactionOld.getPayeeFee());
				payeewalletFee.setCountry(getCountryIdByAuthId(walletTransactionOld.getPayee()));
				payeewalletFee.setCurrency(walletTransactionOld.getPayeeCurrency());
				payeewalletFee.setType(WalletPayerEntity.RECEIVER);
				payeewalletFee.setPayDate(new Date());
				payeewalletFee.setReversal(Boolean.TRUE);
				
				fw = commonService.getMasterFeeWallet(walletTransactionOld.getPayeeCurrency());
				if( fw != null){
					fw.deductFee(walletTransactionOld.getPayeeFee());
					commonService.updateMasterFeeWallet(fw);
				}else {
					throw new WalletException(ERROR_KEY_6);
				}
			}
					
			// add tax entry for payer
			if(walletTransactionOld.getPayerTax() > ZERO_DOUBLE){
				payerwalletTax = new WalletTax();
				payerwalletTax.setAmount(-walletTransactionOld.getPayerTax());
				payerwalletTax.setCountry(getCountryIdByAuthId(walletTransactionOld.getPayer()));
				payerwalletTax.setCurrency(walletTransactionOld.getPayerCurrency());
				payerwalletTax.setType(WalletPayerEntity.SENDER);
				payerwalletTax.setPayDate(new Date());
				payerwalletTax.setReversal(Boolean.TRUE);
				
				tw = commonService.getMasterTaxWallet(walletTransactionOld.getPayerCurrency());
				if( tw != null){
					tw.deductTax(walletTransactionOld.getPayerTax()); 
					commonService.updateMasterTaxWallet(tw);
				}else {
					throw new WalletException(ERROR_KEY_3);
				}
			}
			// add tax entry for payee
			if(walletTransactionOld.getPayeeTax() > ZERO_DOUBLE){
				payeewalletTax = new WalletTax();
				
				payeewalletTax.setAmount(-walletTransactionOld.getPayeeTax());
				payeewalletTax.setCountry(getCountryIdByAuthId(walletTransactionOld.getPayee()));
				payeewalletTax.setCurrency(walletTransactionOld.getPayeeCurrency());
				payeewalletTax.setType(WalletPayerEntity.RECEIVER);
				payeewalletTax.setPayDate(new Date());
				payeewalletTax.setReversal(Boolean.TRUE);
				
				tw = commonService.getMasterTaxWallet(walletTransactionOld.getPayeeCurrency());
				if( tw != null){
					tw.deductTax(walletTransactionOld.getPayeeTax()); 
					commonService.updateMasterTaxWallet(tw);
				}else {
					throw new WalletException(ERROR_KEY_3);
				}
			}
			
		}
		
		UserWallet originalPayerUserWallet = commonService.getUserWallet(walletTransactionOld.getPayer(), walletTransactionOld.getPayerCurrency());
		UserWallet originalPayeeUserWallet = commonService.getUserWallet(walletTransactionOld.getPayee(), walletTransactionOld.getPayeeCurrency());
		
		WalletTransaction walletTransactionNew = new WalletTransaction ();
		/*TypeOfRequest, will hold the information about transaction is down from web/mobile*/
		walletTransactionNew.setTypeOfRequest(walletTransactionOld.getTypeOfRequest());
		walletTransactionNew.setCreationDate(new Date());
		walletTransactionNew.setConvertionRate(ZERO_DOUBLE);
		walletTransactionNew.setParentId(walletTransactionOld.getId());
		walletTransactionNew.setPayee(walletTransactionOld.getPayer());
		walletTransactionNew.setPayeeAmount(amount);
		walletTransactionNew.setPayeeBalance(originalPayerUserWallet.getAmount() + credits);
		walletTransactionNew.setPayeeCurrency(walletTransactionOld.getPayerCurrency());
		if(reversalType.equals(ReversalType.REFUND)){
			walletTransactionNew.setPayeeFee(-ZERO_DOUBLE);
			walletTransactionNew.setPayeeTax(-ZERO_DOUBLE);
		}else if(reversalType.equals(ReversalType.CHARGE_BACK)){
			walletTransactionNew.setPayeeFee(-walletTransactionOld.getPayerFee());
			walletTransactionNew.setPayeeTax(-walletTransactionOld.getPayerTax());
		}
		walletTransactionNew.setPayer(walletTransactionOld.getPayee());
		walletTransactionNew.setPayerAmount(payerAmount);
		walletTransactionNew.setPayerBalance(originalPayeeUserWallet.getAmount() - deductions);
		walletTransactionNew.setPayerCurrency(walletTransactionOld.getPayeeCurrency());
		if(reversalType.equals(ReversalType.REFUND)){
			walletTransactionNew.setPayerFee(-ZERO_DOUBLE);
			walletTransactionNew.setPayerTax(-ZERO_DOUBLE);
		}else if(reversalType.equals(ReversalType.CHARGE_BACK)){
			walletTransactionNew.setPayerFee(-walletTransactionOld.getPayeeFee());
			walletTransactionNew.setPayerTax(-walletTransactionOld.getPayeeTax());
		}
		walletTransactionNew.setStatus(WalletTransactionStatus.SUCCESS);
		walletTransactionNew.setReversalType(reversalType);
		//need to set Type of Transaction here
		walletTransactionNew.setTypeOfTransaction(transactionType);
		walletTransactionNew.setUpdatededDate(new Date());
		WalletTransaction walletTransactionNewUpdate = transactionWalletDao.createTransaction(walletTransactionNew);

		originalPayerUserWallet.setAmount(originalPayerUserWallet.getAmount() + credits);
		commonService.updateUserWallet(originalPayerUserWallet);	
		
		originalPayeeUserWallet.setAmount(originalPayeeUserWallet.getAmount() - deductions);
		commonService.updateUserWallet(originalPayeeUserWallet);
		
		if(reversalType.equals(ReversalType.CHARGE_BACK)){
			//papulate transaction id and perent id on walletfee table
			if(walletTransactionOld.getPayerFee() > ZERO_DOUBLE){
				payerwalletFee.setTransactionId(walletTransactionNewUpdate.getId());
				payerwalletFee.setParentId(walletTransactionOld.getId());
				walletFeeTaxRepository.addFee(payerwalletFee);
			}
			if(walletTransactionOld.getPayerTax() > ZERO_DOUBLE){
				payerwalletTax.setTransactionId(walletTransactionNewUpdate.getId());
				payerwalletTax.setParentId(walletTransactionOld.getId());
				walletFeeTaxRepository.addTax(payerwalletTax);
			}
			
			//payee
			if(walletTransactionOld.getPayeeFee() > ZERO_DOUBLE){
				payeewalletFee.setTransactionId(walletTransactionNewUpdate.getId());
				payeewalletFee.setParentId(walletTransactionOld.getId());
				walletFeeTaxRepository.addFee(payeewalletFee);
			}
			if(walletTransactionOld.getPayeeTax() > ZERO_DOUBLE){
				payeewalletTax.setTransactionId(walletTransactionNewUpdate.getId());
				payeewalletTax.setParentId(walletTransactionOld.getId());
				walletFeeTaxRepository.addTax(payeewalletTax);
			}
		}

	}
}