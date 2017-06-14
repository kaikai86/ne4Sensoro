package com.jx.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HMACTools {
	private static Logger log = LoggerFactory.getLogger(HMACTools.class);
	
	public static String createHmac(String app_secret,String message){
		String hash = "";
		try {
		     Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		     SecretKeySpec secret_key = new SecretKeySpec(app_secret.getBytes(), "HmacSHA256");
		     sha256_HMAC.init(secret_key);
		     hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
		    }
	    catch (Exception e){
	    	System.err.println("解析失败");
	    }
		return hash;
	}
}
