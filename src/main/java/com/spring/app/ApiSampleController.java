package com.spring.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiSampleController {

	
	
	@RequestMapping(value = "/secured/scrId/apiSample", method= {RequestMethod.POST, RequestMethod.GET})
	public Map apiSampleMethod(@RequestBody Map<String, Object> requestData) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		responseData.put("data", requestData);
		return responseData;
	}
	
}

