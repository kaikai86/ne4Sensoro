package com.jx.beans;

public class AiotToken extends BaseBeans{
	private static final long serialVersionUID = 1719980066364701104L;

	private String accessToken;
	private Integer expires_in;
	private String refreshToken;
	private String openId;

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "AiotToken [accessToken=" + accessToken + ", expires_in=" + expires_in + ", refreshToken=" + refreshToken
				+ ", openId=" + openId + "]";
	}
	
	
}
