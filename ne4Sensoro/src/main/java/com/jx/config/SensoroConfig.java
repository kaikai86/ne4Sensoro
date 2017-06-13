package com.jx.config;

import java.io.Serializable;


public class SensoroConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final String appid;
	private final String appkey;
	private final String appsecret;
	
	public SensoroConfig(String appid, String appkey,String appsecret) {
		this.appid = appid;
		this.appkey = appkey;
		this.appsecret = appsecret;
	}

	public String getAppid() {
		return appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public String getAppsecret() {
		return appsecret;
	}
	
	
}
