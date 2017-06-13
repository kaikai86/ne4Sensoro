package com.jx.api;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jx.config.SensoroConfig;
import com.jx.utils.HMACTools;
import com.jx.utils.JsonTools;


public class BaseAPI {
	protected Logger log = LoggerFactory.getLogger(getClass());

	// 请求地址
	protected static final String BaseURL = "https://iot-api.sensoro.com";

	protected final SensoroConfig config;
	
	public BaseAPI(SensoroConfig config) {
		this.config = config;
	}
	
	protected Map<String,String> getHeaders(String request_method,String full_url,String json_body){
		Map<String,String> headers = new HashMap<String,String>();
		headers.put("X-ACCESS-ID", config.getAppid());
		long now_time = System.currentTimeMillis();
		headers.put("X-ACCESS-NONCE", String.valueOf(now_time));
		StringBuilder original = new StringBuilder();
		original.append(now_time).append(request_method.toUpperCase()).append(full_url).append(json_body);
		String secret = HMACTools.createHmac(config.getAppsecret(), original.toString());
		headers.put("X-ACCESS-SIGNATURE", secret);
		return headers;
	}
}
