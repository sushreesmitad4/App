package com.tarang.controller;

import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.tarang.form.WalletForm;

@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@RequestMapping("/")
public class BaseController {

	@Autowired
	private ApplicationContext context;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(ModelMap model,@Valid WalletForm walletform, 
			HttpServletRequest request) {

		walletform.setBookName("Kaithy Siera");
		walletform.setUnitPrice(5.00);
		model.put("quantityList", getQty());

		walletform.setBookName1("Khalid Azeem Mugal");	
		walletform.setUnitPrice1(10.00);
		model.put("quantityList1", getQty());
		
		model.put("paymentList", getPayment());
		
		model.put("walletform", walletform);
		String jsessionid = (String)request.getParameter("jsessionid");
		walletform.setJsessionid(jsessionid);
		ConfigureConstants cc = (ConfigureConstants)context.getBean("confitureConstants");
		System.out.println("Wallet jsessionId :: " + jsessionid);
		if(walletform.getJsessionid()==null){
			return "redirect:" + cc.getWalletUrl().trim() + "?selfurlsession=" + cc.getSelfUrlSession().trim(); /*http://172.30.66.73:8080/wallet-ui/tarang/payment/login";*/		
		}
		// Spring uses InternalResourceViewResolver and return back index.jsp
		return "addbook";

	}

	@RequestMapping(value = "/welcome", method = RequestMethod.POST)
	public String welcomeName(Map model, @Valid WalletForm walletform, 
			BindingResult result, HttpServletRequest request) {
		ConfigureConstants cc = (ConfigureConstants)context.getBean("confitureConstants");
		walletform.setBookName("Kaithy Siera");
		walletform.setBookName1("Khalid Azeem Mugal");
		walletform.setUnitPrice(5.0);
		walletform.setUnitPrice1(10.0);
		walletform.setDispPayment(getPayment().get(walletform.getPayment()));
		walletform.setOrderId("WALBOOKSHOP" + new Date().getTime());
		walletform.setAmount("" + walletform.getAlltotal());	
		walletform.setCurrency(cc.getCurrency());
		walletform.setMerchantId(cc.getMerchantId());
		
		
		//walletform.setCustomerLoginId("customer1@gmail.com");
		//walletform.setCustomerPassword("aaaa1A@");
		walletform.setMerchantCode(cc.getMerchantCode());
		model.put("walletform", walletform);
		model.put("walleturl", cc.getWalletUrl());
		return "confirmviewbook";
	}

	@RequestMapping(value = "/fromMerchant", method = RequestMethod.POST)
	public String responseName( Map model, @Valid WalletForm walletform, 
			BindingResult result, HttpServletRequest request) {
		String successCode = (String)request.getParameter("successCode");
		String successMessage = (String)request.getParameter("successMessage");
		String errorCode = (String)request.getParameter("errorCode");
		String errorMessage = (String)request.getParameter("errorMessage");
		String amount = (String)request.getParameter("amount");
		String currency = (String)request.getParameter("currency");
		String txnId = (String)request.getParameter("txnId");
		String orderId = (String)request.getParameter("orderId");
		String responseStatus = (String)request.getParameter("status");
		Boolean expCheckOut = Boolean.parseBoolean((String)request.getParameter("expCheckOut"));
		String addressOne = (String)request.getParameter("addressOne");
		String addressTwo = (String)request.getParameter("addressTwo");
		String city = (String)request.getParameter("city");
		String region = (String)request.getParameter("region");
		String country = (String)request.getParameter("country");
		String zipcode = (String)request.getParameter("zipcode");

		/*Long merchantId = Long.parseLong(request.getParameter("merchantId"));*/

		walletform.setResponseStatus(responseStatus);
		walletform.setSuccessCode(successCode);
		walletform.setSuccessMessage(successMessage);
		walletform.setErrorCode(errorCode);
		walletform.setErrorMessage(errorMessage);
		walletform.setAmount(amount);
		walletform.setCurrency(currency);
		walletform.setTxnId(txnId);
		walletform.setOrderId(orderId);
		walletform.setExpCheckOut(expCheckOut);
		walletform.setAddressOne(addressOne);
		walletform.setAddressTwo(addressTwo);
		walletform.setCity(city); 
		walletform.setRegion(region);
		walletform.setCountry(country);
		walletform.setZipcode(zipcode) ;
		model.put("walletform", walletform);
		
		if("1".equals(responseStatus)){
			return "successPage";
		}
		else {
			return "failurePage";
		}
	}
	
	private Map<Long, String > getQty(){
		Map<Long, String > map = new HashMap<Long, String>();
		map.put(1L,"1");
		map.put(2L,"2");
		map.put(3L,"3");
		return map;
	}

	private Map<Long, String > getPayment(){
		Map<Long, String > map =new HashMap<Long, String>();
		map.put(1L,"Wallet");
		map.put(2L,"Bank");
		map.put(3L,"Card");
		return map;
	}



}