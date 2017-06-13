package com.jx.config;

import java.io.Serializable;

public class OAuthConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String GRANTCODE = "authorization_code";
	
	public static final String GRANTTOKEN = "refresh_token";
	
	private final String client_id;
	private final String client_secret;
	private String redirect_uri;
	private String state;//可选
	private String scope;//可选
	
	public OAuthConfig(String client_id,String redirect_uri) {
		this(client_id,null,redirect_uri);
	}
	
	public OAuthConfig(String client_id,String client_secret,String redirect_uri) {
		this.client_id = client_id;
		this.client_secret = client_secret;
		this.redirect_uri = redirect_uri;
	}
	

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getClient_id() {
		return client_id;
	}

	public String getClient_secret() {
		return client_secret;
	}
	
	
	
	
	
	
}
