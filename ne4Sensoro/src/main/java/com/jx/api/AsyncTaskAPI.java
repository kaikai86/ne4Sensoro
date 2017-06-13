package com.jx.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.config.SensoroConfig;
import com.jx.utils.HttpClient;
import com.jx.utils.JsonTools;

public class AsyncTaskAPI extends BaseAPI{
	//传输周期设置
	private static final String INTERVAL_SETTING = BaseURL.concat("/developers/device/interval");
	//紧急报警设置
	private static final String THRESHOLD_SETTING = BaseURL.concat("/developers/device/threshold");
	//自定义数据下行--传输模块(自定义数据为十六进制数)
	private static final String CDATA_SETTING = BaseURL.concat("/developers/device/cdata");
	
	
	public AsyncTaskAPI(SensoroConfig config) {
		super(config);
	}
	
	//根据did和options查询设备特定信息 (待测试:802错误码，无权限)
	public String setInterval(List<String> sns,int time) {
		
		Map<String, Object> params = getParams(sns);
		Map<String,Object> interval = new HashMap<>();
		interval.put("interval",time);
		params.put("cfg", interval);
		String json = JsonTools.toJson(params);
		String result = HttpClient.httpSSLPostJson(INTERVAL_SETTING,json,getHeaders("POST",INTERVAL_SETTING,json));
		String result_json = getResult(result);
		return result_json;
	}
	
	private Map<String, Object> getParams(List<String> sns){
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("sns", sns.toArray());
		return params;
	}
	
	private String getResult(String result){
		JSONObject obj = JsonTools.jsonStrToJsonObject(result);
		Integer code = obj.getInteger("err_code");
		JSONObject result_json = null;
		if(code==0){
			result_json = obj.getJSONObject("data");
			result_json.get("unsupportSns");
			JSONArray unsupportArray = result_json.getJSONArray("unsupportSns");
			for(int i=0;i<unsupportArray.size();i++){
				Object ok_obj = unsupportArray.get(i);
				if(ok_obj instanceof JSONObject){
					String ok_sn = ((JSONObject) ok_obj).get("sn").toString();
//					String taskId = ((JSONObject) ok_obj).get("taskId").toString();
					log.info("ok_sn:"+ok_sn);
					System.err.println("ok_sn:"+ok_sn);
				}
			}
			JSONArray unexistArray = result_json.getJSONArray("unexistSns");
			
			for(int i=0;i<unexistArray.size();i++){
				Object ok_obj = unexistArray.get(i);
				if(ok_obj instanceof JSONObject){
					String ok_sn = ((JSONObject) ok_obj).get("sn").toString();
//					String taskId = ((JSONObject) ok_obj).get("taskId").toString();
					log.info("ok_sn:"+ok_sn);
					System.err.println("ok_sn:"+ok_sn);
				}
			}
			JSONArray okArray = result_json.getJSONArray("ok");
			
			for(int i=0;i<okArray.size();i++){
				Object ok_obj = okArray.get(i);
				if(ok_obj instanceof JSONObject){
					String ok_sn = ((JSONObject) ok_obj).get("sn").toString();
					String taskId = ((JSONObject) ok_obj).get("taskId").toString();
					log.info("ok_sn:"+ok_sn+"  ...taskId:"+taskId);
					System.err.println("ok_sn:"+ok_sn+"  ...taskId:"+taskId);
				}
			}
		}else{
			log.error("err_code:"+code+",,,result_json:"+result);
			System.err.println("err_code:"+code);
		}
		return JsonTools.toJson(result);
	}
	
	public static void main(String[] args) {
		SensoroConfig config = new SensoroConfig("AsZcfg1JrfnY","lCcujZd2qPSkQXHCANkjYnQxDYf1NX5sPb3AkJ800J1","ppalNIWrmVaPpw0a5n4mb81NwziPGqpM");
		AsyncTaskAPI api = new AsyncTaskAPI(config);
		List<String> list = new ArrayList<>();
		list.add("015A1117C6C9F6CD");
		String setInterval = api.setInterval(list, 200);
		System.err.println(setInterval);
	}
	
	

}
