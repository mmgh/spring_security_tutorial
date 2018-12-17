package com.spring.app.security.voter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;

public class CustomAccessDecisionVoter implements AccessDecisionVoter<Object> {

	private static final Logger logger = LoggerFactory.getLogger(CustomAccessDecisionVoter.class);
	
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
    	
		String authName = authentication.getName();
    	logger.info("auth name : " + authName);

    	String requestUrl = ((FilterInvocation) object).getRequestUrl();
    	logger.info("Request URL : " + requestUrl);
    	
    	if(!requestUrl.startsWith("/secured")) {
    		return ACCESS_GRANTED;

    	}else{
    	
    		String[] processing = requestUrl.split("/");
    		String processedUrl = processing[3];
    		
    		Set<String> urlSet = new HashSet<String>();
    		
    		/** 테스트 URL 세팅 **/
    		if(authName.equals("anonymousUser")) {
    			
    		}else if(authName.equals("admin")) {
    			urlSet.add("empInfo");
    			//urlSet.add("/secured/scrId/empInfo/emp/get/20");
    		}else if(authName.equals("dba")) {

    		}
    		
    		
    		if (urlSet.contains(processedUrl)) {
    			logger.info("ACCESS_GRANTED");
    			return ACCESS_GRANTED;
    		}else {
    			logger.info("ACCESS_DENIED");
    			return ACCESS_DENIED;
    		}
    		
    	}
		
		
	}
	
	
	public Set urlProcessing(Set urlSet) {
		for (Iterator<Integer> itr = urlSet.iterator(); itr.hasNext();) {
			
			String url = itr.next().toString();
			String[] processing = url.split("/");
			String processedUrl = processing[2];
			
			urlSet.remove(url);
			urlSet.add(processedUrl);
		}

		return urlSet;
	}

	
}
