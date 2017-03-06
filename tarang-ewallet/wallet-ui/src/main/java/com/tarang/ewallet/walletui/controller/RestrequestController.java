package com.tarang.ewallet.walletui.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.form.RestTestForm;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping("/restrequest")
public class RestrequestController implements GlobalLitterals{

	private static final Logger LOGGER = Logger.getLogger(RestrequestController.class);
	
	private static String REST_SERVER_URL = "";
	
	String REST_RESPONSE = "responseList";
	
	String DEAFULT_VEIW = "rest.default.view";

	@RequestMapping(method = RequestMethod.GET)
	public String showDefaultView(Map model, @Valid RestTestForm restTestForm,
			HttpServletRequest request) {
		try {
			REST_SERVER_URL = "http://" + InetAddress.getLocalHost().getHostName() + ":" + request.getLocalPort() + "/wallet-ui/rest";
			LOGGER.debug("REST_SERVER_URL " + REST_SERVER_URL);
		} catch (UnknownHostException e) {
			LOGGER.error(e.getMessage(), e); 
		}
		LOGGER.info(" showDefaultView ");
		restTestForm.setRestUrl(REST_SERVER_URL);
		model.put(OPERATION_LIST, getOperationMap());
		model.put("restTestForm", restTestForm);
		return DEAFULT_VEIW;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitRestView(HttpServletRequest request,
			@Valid RestTestForm restTestForm, BindingResult result, Map model) {
		try {
			LOGGER.debug(" submitRestView ");
			model.put(OPERATION_LIST, getOperationMap());
			String url = restTestForm.getRestUrl();
			String inputValue = restTestForm.getRestRequest();
			String restOperation = restTestForm.getRestOperation();
			if(getOperationMap().get(restOperation).equals(EMPTY_STRING)){
				model.put(REST_RESPONSE, "No Service Is Available");
				return DEAFULT_VEIW;
			}
			String serviceURL = url + restOperation;
			LOGGER.info(" Rest Url: " + serviceURL + " Rest Request: " + inputValue
					+ " Rest Operaton : " + restOperation);

			Client client = Client.create();
			WebResource webResource = client.resource(serviceURL);

			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, inputValue);

			String output = response.getEntity(String.class);
			model.put(REST_RESPONSE, output);
			LOGGER.info("The Rest Response is " + model.get(REST_RESPONSE));

			if (response.getStatus() == 200) {
				LOGGER.info(" Got success responce from Wallet Application : " + output);
			} else if (response.getStatus() == 500) {
				LOGGER.info(" Got failure responce from Wallet Application : " + output);
			}

		} catch (Exception e) {
			LOGGER.error(" Unknown error occured at the time rest api call ", e);
			model.put(REST_RESPONSE, e.getMessage());
		}
		return DEAFULT_VEIW;
	}

	/**
	 * 25 RestFull API services
	 * @return
	 */
	public Map<String, String> getOperationMap() {
		Map<String, String> operationMap = new HashMap<String, String>();
		operationMap.put("/devicehome/customerregistration", "Customer Registration");
		operationMap.put("/devicehome/devicelogin", "Device Login");
		operationMap.put("/devicehome/activate", "Activate Device");
		operationMap.put("/devicehome/devicechangempin", "Change MPIN");
		operationMap.put("/devicehome/mpingeneration", "MPIN Generation");
		operationMap.put("/devicehome/forgotmpin", "Forgot MPIN");
		operationMap.put("/devicehome/logout", "Device Logout");
		operationMap.put("/devicehome/deactivate", "De-Activate Device");
		operationMap.put("/feedback", "Feedback/Query");
		operationMap.put("/profilemgmt", "Profile Management");
		operationMap.put("/manageaccounts/addcard", "Add Card");
		operationMap.put("/manageaccounts/editcard", "Edit Card");
		operationMap.put("/manageaccounts/verifycards", "Verify Card");
		operationMap.put("/manageaccounts/delete", "Delete Card");
		operationMap.put("/manageaccounts/accountrecords", "List Of Cards");
		operationMap.put("/manageaccounts/setdefault", "Set Default Card");
	  //operationMap.put("", "Load Fund");
		operationMap.put("/viewwalletbalace", "View Wallet Balance");
		operationMap.put("/viewlastntransactions", "View Last 5 Transactions");
	  //operationMap.put("", "Search specific transaction");
		operationMap.put("/wallettransaction/selftransfer", "Self Transfer");
		operationMap.put("/wallettransaction/selftransferconfirm", "Self Transfer Confirmation");
		operationMap.put("/wallettransaction/tosingle", "Transfer Money To Single");
		operationMap.put("/wallettransaction/tosingleconfirm", "Transfer Money To Single Confirmation");
		operationMap.put("/wallettransaction/listofwallets", "List Of Supporting Wallets");
	  //operationMap.put("", "List Of Supporing Transactions");
		operationMap.put("/reloadmoney/relmonservice", "Reload Money");
		operationMap.put("/masterdatarestservice/listofcurrency", "List Of Currency");
		operationMap.put("/masterdatarestservice/destinationtypes", "List Of Destination Types");
	 /* operationMap.put("", "Account Approva");
		operationMap.put("", "Account Lock");
		operationMap.put("", "Account Deletions");
		operationMap.put("", "Confirm Account Closure"); */
		operationMap.put("/masterdatarestservice/typesofcards", "List Of Card Types");
		operationMap.put("/masterdatarestservice/hintquestions", "List Of Hint Questions");
		operationMap.put("/manageaccounts/getaccountdetails", "View Account Details");
		operationMap.put("/masterdatarestservice/destinationtypesandcurrency", "List Of DestinationTypes And Currency");
		return sortByValue(operationMap);
	}
	
	private	static Map sortByValue(Map map) {
		try{
		     List list = new LinkedList(map.entrySet());
		     Collections.sort(list, new Comparator() {
		          public int compare(Object o1, Object o2) {
		               return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
		          }
		     });

		    Map result = new LinkedHashMap();
		    for (Iterator it = list.iterator(); it.hasNext();) {
		        Map.Entry entry = (Map.Entry)it.next();
		        result.put(entry.getKey(), entry.getValue());
		    }
		    return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage() , e);
			return map;
		}
	}

}
