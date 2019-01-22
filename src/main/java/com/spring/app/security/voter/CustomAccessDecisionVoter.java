package com.spring.app.security.voter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    	
		String userId = authentication.getName();
    	logger.info("USER ID : " + userId);

    	String requestURL = ((FilterInvocation) object).getRequestUrl();
    	logger.info("Request URL : " + requestURL);
    	
    	
    	/**  Spring Security가 체크하는 URI CASE확인. **/
    	if(!requestURL.startsWith("/secured")) {
    		return ACCESS_GRANTED;

    	}else{
    	
    		/** URI패턴이 있을 경우 REGEX처리한다. */
			String securedScrIdRegex = "\\/(scrId)\\/\\w+";
			String[] preScrIdArr     = null;
			String requestScrId      = "";
			
			
			Pattern pattern = Pattern.compile(securedScrIdRegex);
			Matcher matcher = pattern.matcher(requestURL);
			
			String mgroup = "";
			if(matcher.find()){
				mgroup       = matcher.group();
				preScrIdArr  = mgroup.split("/");
				requestScrId = preScrIdArr[2];
			}
    		
			
			
			/** TO DO : DB에서 URI를 관리할경우 URI리스트를 가져오는 로직을 추가한다. **/
    		Set<String> urlSet   = new HashSet();
			Set<String> scrIdSet = new HashSet();
			
			
			
			
    		/** 테스트 URL 세팅 **/
    		if(userId.equals("anonymousUser")) {
    			
    		}else if(userId.equals("admin")) {
    			scrIdSet.add("empInfo");
    			//urlSet.add("/secured/scrId/empInfo/emp/get/20");
    		}else if(userId.equals("dba")) {
    			//scrIdSet.add("empInfo");
    		}
    		/** 테스트 URL 세팅  끝**/
    		
    		
    		
    		/** ACCESS_GRANTED or ACCESS_DENIED 처리 **/
			if(urlSet.contains(requestURL)){
				return ACCESS_GRANTED;
			}else{
				
				if(!"".equals(requestScrId) && scrIdSet.contains(requestScrId)){
					return ACCESS_GRANTED;
				}else{
					return ACCESS_DENIED;
				}
				
			}
			
			
			
			
    		
    	}
    	
		
	}
	
	

	
}
