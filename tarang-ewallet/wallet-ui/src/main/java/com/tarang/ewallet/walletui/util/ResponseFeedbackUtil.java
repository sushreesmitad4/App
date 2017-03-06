package com.tarang.ewallet.walletui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tarang.ewallet.model.Feedback;
import com.tarang.ewallet.util.DateConvertion;
import com.tarang.ewallet.util.GlobalLitterals;
import com.tarang.ewallet.walletui.controller.AttributeConstants;


public class ResponseFeedbackUtil implements AttributeConstants,GlobalLitterals {
	public static List<FeedbackList> getFeedbackList(List<Feedback> feedBacks, HttpServletRequest request){
		
		List<FeedbackList> feedbackLists = new ArrayList<FeedbackList>();
		FeedbackList feedbackList = null;
		for(Feedback b: feedBacks) {
			feedbackList = new FeedbackList();
			feedbackList.setId(b.getId());
			feedbackList.setQuerryType(b.getQuerryType());
			Map<Long, String> qf = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.QUERY_OR_FEEDBACK);
			feedbackList.setQueryTypeName(qf.get(b.getQuerryType()));
			feedbackList.setSubject(b.getSubject());
			feedbackList.setDateAndTime(b.getDateAndTime());
			feedbackList.setUserType(b.getUserType());
			Map<Long, String> userTypeName = MasterDataUtil.getSimpleDataMap(
					request.getSession().getServletContext(), 
					(Long) request.getSession().getAttribute(LANGUAGE_ID),
					MasterDataConstants.MD_USER_TYPES);
			if(b.getUserType().equals(CUSTOMER_USER_TYPE)){
				feedbackList.setUserTypeName(userTypeName.get(CUSTOMER_USER_TYPE_ID));
			} else if(b.getUserType().equals(MERCHANT_USER_TYPE)){
				feedbackList.setUserTypeName(userTypeName.get(MERCHANT_USER_TYPE_ID));
			}
			feedbackList.setUserMail(b.getUserMail());
			if(b.getResponseDate() != null) {
				feedbackList.setResponseDate(DateConvertion.dateToString(b.getResponseDate()));
			}
			feedbackLists.add(feedbackList);
		}
		return feedbackLists;
	}

}
