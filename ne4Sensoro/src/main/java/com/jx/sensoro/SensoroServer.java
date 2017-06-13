package com.jx.sensoro;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;
import com.jx.utils.HttpClient;
import com.jx.utils.JsonTools;

public class SensoroServer {
	public static void main(String[] args) {
		String base_url = "http://yq.x86119.com";
		String url = base_url+"/aiot/msg/get.htm";
		JSONObject json = new JSONObject();
		json.put("msgType", "device_state");
		json.put("did", "lumi.158d0000f85256");
		json.put("state", 0);
		json.put("time", System.currentTimeMillis()/1000);
		HashMap<String,String> headers = new HashMap<>();
		headers.put("Appid", "9d0873ef3e433808f9a8c956");
		headers.put("Appkey", "ilaBllDGi24nc8NnmtqLAYLOO3kxhdUK");
		String postJson = HttpClient.postJson(url, JsonTools.toJson(json),headers);
		System.err.println(postJson);
	}

}
