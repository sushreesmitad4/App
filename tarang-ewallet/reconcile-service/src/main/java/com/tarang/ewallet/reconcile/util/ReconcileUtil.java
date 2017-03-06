package com.tarang.ewallet.reconcile.util;
/**
 * @author  : kedarnathd
 * @date    : March 07, 2013
 * @time    : 9:58:50 AM
 * @version : 1.0.0
 * @comments: 
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.log4j.Logger;

import com.tarang.ewallet.dto.ReconcileDto;
import com.tarang.ewallet.model.Reconcile;
import com.tarang.ewallet.util.GlobalLitterals;


public class ReconcileUtil {
	private static final Logger LOGGER = Logger.getLogger(ReconcileUtil.class);
	
	public static Reconcile preparedReconcileModelForCreate(ReconcileDto oldData){
		Reconcile reconcile = new Reconcile();
		reconcile.setPgTxnId(oldData.getPgTxnId()); 
		reconcile.setScheme(oldData.getScheme());
		reconcile.setPurchaseTime(oldData.getPurchaseTime());
		reconcile.setPurchaseDate(oldData.getPurchaseDate());
		reconcile.setTxnAmount(oldData.getTxnAmount());
		reconcile.setTxnCurrency(oldData.getTxnCurrency());
		reconcile.setCreationDate(new Date());
		reconcile.setStatus(ReconcileStatusConstants.PENDING);
		return reconcile;
	}
	

	public static List<ReconcileDto> covertReconcileListToReconcileDtoList(List<Reconcile> reconcileList){
		List<ReconcileDto> reconcileDtoList = new ArrayList<ReconcileDto>();
		
		for(int i = 0; i <reconcileList.size(); i++){
			Reconcile reconcile = (Reconcile)reconcileList.get(i);
			ReconcileDto reconcileDto = new ReconcileDto();
			reconcileDto.setId(reconcile.getId());
			reconcileDto.setActualAmount(reconcile.getActualAmount());
			reconcileDto.setPgTxnId(reconcile.getPgTxnId());
			reconcileDto.setScheme(reconcile.getScheme());
			reconcileDto.setPurchaseDate(reconcile.getPurchaseDate());
			reconcileDto.setPurchaseTime(reconcile.getPurchaseTime());
			reconcileDto.setTxnAmount(reconcile.getTxnAmount());
			reconcileDto.setFinalAmount(reconcile.getFinalAmount());
			reconcileDto.setTxnCurrency(reconcile.getTxnCurrency());
			reconcileDto.setCreationDate(reconcile.getCreationDate());
			reconcileDto.setUpdateDate(reconcile.getUpdateDate());
			reconcileDto.setStatus(reconcile.getStatus());
			reconcileDtoList.add(reconcileDto);	
		}
		return reconcileDtoList;
	}
	
	public static ReconcileDto convetReconileToReconcileDto(Reconcile reconcile){
		ReconcileDto reconcileDto = new ReconcileDto();
		reconcileDto.setId(reconcile.getId());
		reconcileDto.setActualAmount(reconcile.getActualAmount());
		reconcileDto.setPgTxnId(reconcile.getPgTxnId());
		reconcileDto.setScheme(reconcile.getScheme());
		reconcileDto.setPurchaseDate(reconcile.getPurchaseDate());
		reconcileDto.setPurchaseTime(reconcile.getPurchaseTime());
		reconcileDto.setTxnAmount(reconcile.getTxnAmount());
		reconcileDto.setFinalAmount(reconcile.getFinalAmount());
		reconcileDto.setTxnCurrency(reconcile.getTxnCurrency());
		reconcileDto.setCreationDate(reconcile.getCreationDate());
		reconcileDto.setUpdateDate(reconcile.getUpdateDate());
		reconcileDto.setStatus(reconcile.getStatus());
		return reconcileDto;
	}

	public static List<String> getReadableReconcileFileNames(String sourcePath, String fileExtn) {
		String fileName = null;
		List<String> fileNames = new ArrayList<String>();
		try{
			File[] files = new File(sourcePath).listFiles();
			if(files != null) {
				for(File file : files){
					if(file != null && file.isFile() && file.canRead()){
						fileName = file.getName();
						if(fileName.lastIndexOf(fileExtn) + fileExtn.length() == fileName.length()){
							fileNames.add(file.getAbsolutePath());
						}
					}
				}
			}
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return fileNames;
	}

	public static void createFiles(String fileName, String fileLocation, List<Integer> datas){
		PrintWriter printWriter = null ;
		try {
			printWriter = new PrintWriter (new FileWriter(fileLocation + File.separator + fileName, true));
			printWriter.println(ReconcileConstants.COLUMN_NAME_LINE_NUMBER);
			for(Integer data:datas){
				printWriter.println (data.toString() + "\t");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally{
			if (printWriter != null) {
				try {
					printWriter.close();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	 public static void moveFile(String fileName, String fromLocation, String toLocation){
	    BufferedReader bufferedReader = null;
	    PrintWriter printWriter = null;
	       try{
	         bufferedReader =  new BufferedReader(new FileReader(fromLocation + File.separator + fileName));
	         printWriter = new PrintWriter(toLocation + File.separator + new File(fromLocation + File.separator + fileName).getName());
	         String line = null;
	         StringBuffer sb =new StringBuffer();
	         while (true) {
	        	 line = bufferedReader.readLine();
	        	 if (line == null ){
	        		 break;
	        	 }
	        	 sb.append(line);
	        	 sb.append("\n");
	         }
	         printWriter.print(sb.toString());
	         bufferedReader.close();
	         printWriter.close();
	         //delete the original file
	         new File(fromLocation + File.separator + fileName).delete();
	       } catch(IOException e){
	    	   LOGGER.error(e.getMessage(), e);
	       }
	   }
	
	 public static String constructFileName(String fileName, String fileExtn){
		 StringBuffer sb = new StringBuffer();
		 String newFileName = fileName.replace(fileExtn, "");
		 sb.append(newFileName);
		 sb.append("_");
		 sb.append(Calendar.getInstance().getTime().getTime());
		 sb.append(fileExtn);
		 return sb.toString();
	 }
	 
	/**
	 * Validate currency formate
	 * @param value
	 * @return
	 */
	public static Boolean currencyValidator(String value){
		Boolean isError = false;
		try{
			if(!expressionPattern(ReconcileConstants.CURRENCY_EXPRESSION, value)){
				isError = true;
			} else{
				if(Double.parseDouble(value) > ReconcileConstants.CURRENCY_MAX_LIMIT){
					isError = true;
				}
				if( GlobalLitterals.ZERO_DOUBLE.equals(Double.parseDouble(value)) ){
					isError = true;
				}
			}
		} catch (Exception e) {
			isError = true;
			 LOGGER.error(e.getMessage(), e);
		}
		return isError;
	}
	
	/**
	 * @param value
	 * @return
	 */
	public static Boolean numberValidator(String value){
		return expressionPattern(ReconcileConstants.NUMBER_EXPRESSION, value);
	}
	
	 /**
     *  verifies the value is valid for given expression pattern.
     * @param expressionStr
     * @param value
     * @return
     */
    private static boolean expressionPattern(String expressionStr, String value){
        try{
            return Pattern.matches(expressionStr, value);
        }  catch(PatternSyntaxException pse){
        	 LOGGER.error(pse.getMessage(), pse);
            return true;
        }
    }
}
