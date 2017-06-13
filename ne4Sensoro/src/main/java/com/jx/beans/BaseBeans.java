package com.jx.beans;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class BaseBeans implements Serializable{
	
	private static final long serialVersionUID = -7298213510049209381L;
	
	private Integer code;
	private String errmsg;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String toJSON(){
		return JSON.toJSONString(this);
	}

}
