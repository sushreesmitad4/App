package com.tarang.ewallet.util.service;

public interface UtilService {
  
	void reloadProperties();
	
	String getDataSourceDriverClassName();
	
	String getDataSourceUrl();
	
	String getDataSourceUserName();
	
	String getDataSourcePassword();
	
	String getHibernateDialect();
	
	String getMailHost();
	
	String getMailUserName();
	
	String getMailPassword();
	
	String getMailFromEmail();
	
	String getMailTemplateFile();
	
	String getMailPropsFile();
	
	String getEncryptedKey1();
	
	Integer getMaxPasswordHistory();
	
	String getSchedularPropsFile();
	
	String getWalletJobsPropsFile();
	
	String getHttpServiceFile();
	
	Integer getPennyDropAmountUsd();
	
	Integer getPennyDropAmountJpy();
	
	Integer getPennyDropAmountThb();
	
	Integer getReportLimit();
	
	Integer getDisputeDays();

	Integer getDisputeAllowedHours();
	
	Double getFeeMaxPercentage();
	
	Double getFeeMaxFlat();
	
	Double getFeeUpperLimit();
	
	Integer getMaxFundResources();
	
	Integer getAccountCloseDays();
	
	String getPgFileExtention();
	
	String getSourceFolderLocation();
	
	String getSuccessFileLocation();
	
	String getNullRecordsFileLocation();
	
	String getMissMathFileLocation();
	
	String getCurruptedFileLocation();
	
	String getFailureFileLocation();
	
	Integer getRegistrationAllowedHours();
	
	Integer getRegistrationAllowedAccounts();
	
	Integer getNumberOfDisputes();
	
	Integer getBankAllowedHours();

	Integer getBankAllowedAccounts();
	
	Integer getCardAllowedHours();

	Integer getCardAllowedAccounts();
	
	Integer getDefaultLastNTransactions();
	
    Integer getEmailPatternAllowedHours();
	
	Integer getEmailPatternAllowedAccounts();
	
	Integer getDormantAccountInterval();
	
	Integer getSendMoneyFileUploadRecordLimit();
	
	Integer getLoginAttemptsLimit();
	
	Integer getCancelNonregWalletTxnsAllowedDays();

	Integer getDaysForPendingAccountClosers();

	String getMessageForPendingAccountClosers();
	
	Integer getJointBankAccountsLimit();
	
	Integer getJointCardAccountsLimit();
	
	Integer getEmailPatternLength();
	
	String getUploadImageFileExtenstion();
	
	Integer getUploadImageFileSize();
	
	String getUploadImageFileLocation();
	
	String getUploadImageRelativeLocation();
	
	Integer getReportDays();
	
	String getDefaultImageFileName();
	
	Integer getOtpExpiredInMinutes();
	
	Integer getOtpLength();
	
	String getSmsGatewayUrl();
	
	String getSmsGatewayUserName();
	
	String getSmsGatewayPassword();
	
	String getSmsGatewaySenderId();
	
	String getSmsGatewayBypass();
	
	public String getMpinLength();
}
