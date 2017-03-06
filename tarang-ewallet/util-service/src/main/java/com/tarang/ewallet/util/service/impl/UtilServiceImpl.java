package com.tarang.ewallet.util.service.impl;

import java.util.Properties;

import com.tarang.ewallet.util.CommonProjectUtil;
import com.tarang.ewallet.util.service.UtilService;

public class UtilServiceImpl implements UtilService {
	
	private Properties commonProperties;
	
	private String fileName;
	
	public UtilServiceImpl(String props){
		this.fileName = props;
		commonProperties = CommonProjectUtil.loadProperties(props);
	}
	
	
	public void reloadProperties(){
		this.commonProperties = CommonProjectUtil.loadProperties(fileName);
	}
	
	
	public String getDataSourceDriverClassName(){
		return commonProperties.getProperty("dataSource.driverClassName");	
	}
	
	
	public String getDataSourceUrl(){
		return commonProperties.getProperty("dataSource.url");
	}
	
	
	public String getDataSourceUserName(){
		return commonProperties.getProperty("dataSource.username");
	}
	
	
	public String getDataSourcePassword(){
		return commonProperties.getProperty("dataSource.password");
	}
	
	
	public String getHibernateDialect(){
		return commonProperties.getProperty("hibernate.dialect");
	}
	
	
	public String getMailHost(){
		return commonProperties.getProperty("mail.host");
	}
	
	
	public String getMailUserName(){
		return commonProperties.getProperty("mail.username");
	}
	
	
	public String getMailPassword(){
		 return commonProperties.getProperty("mail.password");
	}
	
	
	public String getMailFromEmail(){
		return commonProperties.getProperty("mail.fromEmail");
	}
	
	
	public String getMailTemplateFile(){
		return commonProperties.getProperty("mail.template.file");
	}
	
	
	public String getMailPropsFile(){
		return commonProperties.getProperty("mail.props.file");
	}
	
	
	public String getEncryptedKey1(){
		return commonProperties.getProperty("encriptedKey1");
	}
	
	
	public Integer getMaxPasswordHistory(){
		return Integer.parseInt(commonProperties.getProperty("maxPasswordHistory").trim());
	}
	
	
	public String getSchedularPropsFile(){
		return commonProperties.getProperty("scheduler.props.file");
	}
	
	
	public String getWalletJobsPropsFile(){
		return commonProperties.getProperty("wallet.jobs.props.file");
	}
	
	
	public String getHttpServiceFile(){
		return commonProperties.getProperty("http.service.file");
	}

	
	public Integer getPennyDropAmountUsd(){
		return Integer.parseInt(commonProperties.getProperty("penny.drop.amount.usd").trim());
	}
	
	
	public Integer getPennyDropAmountJpy(){
		return Integer.parseInt(commonProperties.getProperty("penny.drop.amount.jpy").trim());
	}
	
	
	public Integer getPennyDropAmountThb(){
		return Integer.parseInt(commonProperties.getProperty("penny.drop.amount.thb").trim());
	}
	
	
	public Integer getReportLimit(){
		return Integer.parseInt(commonProperties.getProperty("report.limit").trim());
	}
	
	
	public Integer getDisputeDays(){
		return Integer.parseInt(commonProperties.getProperty("dispute.days").trim());
	}
	
	
	public Integer getDisputeAllowedHours(){
		return Integer.parseInt(commonProperties.getProperty("dispute.allowed.hours").trim());
	}
	
	
	public Double getFeeMaxPercentage(){
		return Double.parseDouble(commonProperties.getProperty("fee.max.percentage").trim());
	}
	
	
	public Double getFeeMaxFlat(){
		return Double.parseDouble(commonProperties.getProperty("fee.max.flat").trim());
	}
	
	
	public Double getFeeUpperLimit(){
		return Double.parseDouble(commonProperties.getProperty("fee.upperlimit").trim());
	}
	
	
	public Integer getMaxFundResources(){
		return Integer.parseInt(commonProperties.getProperty("max.fund.resources").trim());
	}
	
	
	public Integer getAccountCloseDays(){
		return Integer.parseInt(commonProperties.getProperty("accountclose.days").trim());
	}

	
	public String getPgFileExtention() {
		return commonProperties.getProperty("pg.file.extension");
	}

	
	public String getSourceFolderLocation() {
		return commonProperties.getProperty("source.folder.location");
	}

	
	public String getSuccessFileLocation() {
		return commonProperties.getProperty("success.file.location");
	}

	
	public String getNullRecordsFileLocation() {
		return commonProperties.getProperty("nullrecords.file.location");
	}

	
	public String getMissMathFileLocation() {
		return commonProperties.getProperty("missmatch.file.location");
	}

	
	public String getCurruptedFileLocation() {
		return commonProperties.getProperty("currupted.file.location");
	}

	
	public String getFailureFileLocation() {
		return commonProperties.getProperty("failure.file.location");
	}
	
	public Integer getRegistrationAllowedHours() {
		return Integer.parseInt(commonProperties.getProperty("registration.allowed.hours").trim());
	}

   
	public Integer getRegistrationAllowedAccounts() {
		return Integer.parseInt(commonProperties.getProperty("registration.allowed.accounts").trim());
	}
	
	
	public Integer getNumberOfDisputes() {
		return Integer.parseInt(commonProperties.getProperty("fraud.disputes").trim());
	}
	
	
	public Integer getBankAllowedHours() {
		return Integer.parseInt(commonProperties.getProperty("bank.allowed.hours").trim());
	}
	
	
	public Integer getBankAllowedAccounts() {
		return Integer.parseInt(commonProperties.getProperty("bank.allowed.accounts").trim());
	}
	
	
	public Integer getCardAllowedHours() {
		return Integer.parseInt(commonProperties.getProperty("card.allowed.hours").trim());
	}
	
	
	public Integer getCardAllowedAccounts() {
		return Integer.parseInt(commonProperties.getProperty("card.allowed.accounts").trim());
	}
	
	
	public Integer getDefaultLastNTransactions(){
		return Integer.parseInt(commonProperties.getProperty("default.last.n.transactions").trim());
	}
	
	
	public Integer getEmailPatternAllowedHours() {
		return Integer.parseInt(commonProperties.getProperty("email.pattern.allowed.hours").trim());
	}
	
	
	public Integer getEmailPatternAllowedAccounts() {
		return Integer.parseInt(commonProperties.getProperty("email.pattern.allowed.accounts").trim());
	}
	
	
	public Integer getDormantAccountInterval(){
		return Integer.parseInt(commonProperties.getProperty("dormant.account.interval").trim());
	}
	
	
	public Integer getSendMoneyFileUploadRecordLimit(){
		return Integer.parseInt(commonProperties.getProperty("fileupload.record.limit").trim());
	}
	
	public Integer getLoginAttemptsLimit() {
		return Integer.parseInt(commonProperties.getProperty("login.attempts.limit").trim());
	}

	
	public Integer getCancelNonregWalletTxnsAllowedDays() {
		return Integer.parseInt(commonProperties.getProperty("cancel.nonreg.wallet.txns.allowed.days").trim());
	}

	
	public Integer getDaysForPendingAccountClosers() {
		return Integer.parseInt(commonProperties.getProperty("days.for.pending.account.closers").trim());
	}

	
	public String getMessageForPendingAccountClosers() {
		return commonProperties.getProperty("message.for.pending.account.closers");
	}

	
	public Integer getJointBankAccountsLimit() {
		return Integer.parseInt(commonProperties.getProperty("joint.account.bank.limit").trim());
	}

	
	public Integer getJointCardAccountsLimit() {
		return Integer.parseInt(commonProperties.getProperty("joint.account.card.limit").trim());
	}

	
	public Integer getEmailPatternLength() {
		return Integer.parseInt(commonProperties.getProperty("email.pattern.length").trim());
	}

	
	public String getUploadImageFileExtenstion() {
		return commonProperties.getProperty("file.upload.brand.extension").trim();
	}

	
	public Integer getUploadImageFileSize() {
		return Integer.parseInt(commonProperties.getProperty("file.upload.brand.size").trim());
	}

	
	public String getUploadImageFileLocation() {
		return commonProperties.getProperty("file.upload.brand.location").trim();
	}

	
	public String getUploadImageRelativeLocation() {
		return commonProperties.getProperty("file.upload.brand.relativepath").trim();
	}

	
	public Integer getReportDays() {
		return Integer.parseInt(commonProperties.getProperty("default.reports.past.days").trim());
	}

	
	public String getDefaultImageFileName() {
		return commonProperties.getProperty("default.merchant.logo").trim();
	}

	
	public Integer getOtpExpiredInMinutes() {
		return Integer.valueOf(commonProperties.getProperty("otp.expired.in.minutes").trim());
		
	}
	
	
	public Integer getOtpLength() {
		return Integer.valueOf(commonProperties.getProperty("otp.length").trim());
	}

	
	public String getSmsGatewayUrl() {
		return commonProperties.getProperty("mwallet.sms.gateway.url").trim();
	}
	
	
	public String getSmsGatewayUserName() {
		return commonProperties.getProperty("mwallet.sms.gateway.user.name").trim();
	}
	
	
	public String getSmsGatewayPassword() {
		return commonProperties.getProperty("mwallet.sms.gateway.password").trim();
	}
	
	
	public String getSmsGatewaySenderId() {
		return commonProperties.getProperty("mwallet.sms.gateway.senderid").trim();
	}
	
	
	public String getSmsGatewayBypass() {
		return commonProperties.getProperty("mwallet.sms.gateway.bypass").trim();
		
	}
	
	public String getMpinLength() {
		return commonProperties.getProperty("mwallet.mpin.length").trim();
		
	}
	
	
}
