/**
 * 
 */
package com.tarang.ewallet.http.repository.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import com.tarang.ewallet.exception.WalletException;
import com.tarang.ewallet.http.repository.PGRequest;
import com.tarang.ewallet.http.util.ErrorCodeConstants;
import com.tarang.ewallet.http.util.HttpServiceConstants;
import com.tarang.ewallet.util.CommonProjectUtil;
import com.tarang.ewallet.util.service.UtilService;


/**
 * @author  : prasadj
 * @date    : Dec 27, 2012
 * @time    : 11:28:09 AM
 * @version : 1.0.0
 * @comments: 
 *
 */

public class PGRequestImpl implements PGRequest, HttpServiceConstants {
	
	private static final Logger LOGGER = Logger.getLogger(PGRequestImpl.class);
	
	private Properties commonProperties = new Properties();
	
	private String serviceURL;
	
	private DefaultHttpClient httpClient;
	
	private UtilService utilService;
	
	public PGRequestImpl(UtilService utilService){
		this.utilService = utilService;
		try{
			initialize();
		} catch(Exception ex){
			LOGGER.error(ex.getMessage(), ex);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void initialize() throws WalletException {
		try {
			LOGGER.debug("Initializing PG connection...");
			commonProperties = CommonProjectUtil.loadProperties(utilService.getHttpServiceFile());
			serviceURL = commonProperties.getProperty(SERVICE_URL);
			HttpParams params = new BasicHttpParams();
			
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			if(commonProperties.getProperty(IS_SSL).equalsIgnoreCase(Boolean.TRUE.toString())) {
				int portNumber = Integer.valueOf(commonProperties.getProperty(SERVER_HTTPS_PORT));
				
				SSLSocketFactory sslSocketFactory = getSSLSocketFactory(commonProperties.getProperty(SSL_KEYSTORE_FILE_PATH), commonProperties.getProperty(SSL_PASSWORD));
				sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				schemeRegistry.register(new Scheme(HTTPS, portNumber, sslSocketFactory));
			} else {
				int portNumber = Integer.valueOf(commonProperties.getProperty(SERVER_HTTP_PORT));
				schemeRegistry.register(new Scheme(HTTP, portNumber, PlainSocketFactory.getSocketFactory()));
			}
			ClientConnectionManager clientConnManager = new PoolingClientConnectionManager(schemeRegistry);
			
			params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 
					Integer.valueOf(commonProperties.getProperty(CONNECTION_TIME_OUT)) );
			params.setParameter(CoreConnectionPNames.SO_TIMEOUT, 
					Integer.valueOf(commonProperties.getProperty(READ_TIME_OUT)) );
			
			httpClient = new DefaultHttpClient(clientConnManager, params);
			if(httpClient != null){
				LOGGER.debug("Initialized PG connection  succefully...");
			}
		} catch (Exception ex){
			LOGGER.error(ex.getMessage());
			throw new WalletException(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH, ex);
		}
	}
	
	private SSLSocketFactory getSSLSocketFactory(String keyStoreFilePath, String keyStorePassword) throws WalletException {
		
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream keyStoreStream = new FileInputStream(new File(CommonProjectUtil.getAbsolutePath(keyStoreFilePath)));
			keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
			return new SSLSocketFactory(keyStore, keyStorePassword);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new WalletException(e.getMessage(), e);
		}
		
	}
	
	private HttpGet addCustomHeadersForGetMethod(HttpGet httpGet) {
		httpGet.setHeader(USER_AGENT, HTTP_CLIENT_VERSION);
		httpGet.setHeader(ACCEPT, TEXT_OR_HTML);
		httpGet.setHeader(CONTENT_TYPE, TEXT_OR_HTML);
		return httpGet;
	}

	@Override
	public String connectGetMethod(String requestData) throws WalletException {

		String responseData = null;
		HttpGet httpGet = null;
		HttpResponse response = null;
		try {
			httpGet = new HttpGet(serviceURL);
			addCustomHeadersForGetMethod(httpGet);
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseData = IOUtils.toString(entity.getContent());
			} else {
				throw new Exception("" + statusCode);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new WalletException (ex.getMessage(), ex);
		} finally {
			if (httpGet != null && !httpGet.isAborted()) {
				httpGet.abort();
			}
		}
		return responseData;
	}
	
	@Override
	public String connectPostMethod(JSONObject jsonObject) throws WalletException {
		String responseData = null;
		HttpPost httpPost = null;
		HttpResponse response = null;
		try {
			HashMap<String, String> postParam = new HashMap<String, String>();
			jsonObject.put(MERCHANT_ID, commonProperties.get(EWALLET_MERCHANT));
			jsonObject.put(USER_ID, commonProperties.get(EWALLET_USER));
			//LOGGER.info("Sending PG Request:: "+jsonObject.toString());
			postParam.put(JSON_DATA, jsonObject.toString());
			httpPost = new HttpPost(serviceURL);
			//LOGGER.info("Sending PG Request httpPost:: "+httpPost.toString());
			List<NameValuePair> nvps = new ArrayList <NameValuePair>();
			for (Entry<String, String> entry : postParam.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	        }
		    httpPost.setEntity(new UrlEncodedFormEntity(nvps, org.apache.http.protocol.HTTP.DEF_CONTENT_CHARSET ));
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				responseData = IOUtils.toString(entity.getContent());
			} else {
				LOGGER.error("" +  statusCode);
				throw new Exception("" +  statusCode);
			}
		} catch (HttpHostConnectException ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(ErrorCodeConstants.PG_SERVICE_IS_NOT_ESTABLISH);
		} catch (ConnectTimeoutException ex){
			LOGGER.error(ex.getMessage(), ex);
			throw new WalletException(ErrorCodeConstants.COMMUNICATION_WITH_PAYMENT_SYSTEM_TIMED_OUT);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new WalletException(e.getMessage(), e);
		} finally {
			if (httpPost != null && !httpPost.isAborted()) {
				httpPost.abort();
			}
		}
		return responseData;
	}

	@Override
	public String getEwalletMerchantId() {
		return commonProperties.getProperty(EWALLET_MERCHANT);
	}

	@Override
	public String getEwalletUserId() {
		return commonProperties.getProperty(EWALLET_USER);
	}	
	
}
