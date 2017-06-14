package com.jx.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.config.SensoroConfig;
import com.jx.utils.HttpClient;
import com.jx.utils.JsonTools;

public class DeviceDataAPI extends BaseAPI{
	//获取传感器基本信息
	private static final String DEVICE_INFO = BaseURL.concat("/developers/device/");
	//获取传感器数据列表
	private static final String DEVICE_LIST = BaseURL.concat("/developers/device/list");
	//获取基站基本信息
	private static final String STATION_INFO = BaseURL.concat(" /developers/station/");
	//获取传感器数据列表
	private static final String STATION_LIST = BaseURL.concat("/developers/station/list");
	//获取传感器历史数据
	private static final String DEVICE_LOG_DATA = BaseURL.concat("/developers/device/");
	
	public DeviceDataAPI(SensoroConfig config) {
		super(config);
	}
	
	//根据sn 获取传感器基本信息
	public String getDeviceInfoBySN(String sn) {
		String device_info = DEVICE_INFO.concat(sn);
		String json = "{}";
		String result = HttpClient.httpSSLGet(device_info,getHeaders("GET",device_info,json));
		result = parseResult(result);
		return result;
	}
	
	//获取传感器数据列表 分页查询 page=1 count=100
	public String getDeviceList(int page, int count) {
		String device_list = DEVICE_LIST.concat("?").concat("page="+page).concat("&count="+count);
		String json = "{}";
		String result = HttpClient.httpSSLGet(device_list,getHeaders("GET",device_list,json));
		result = parseListResult(result);
		return result;
	}
	
	//根据sn 获取基站基本信息
	public String getStationInfoBySN(String sn) {
		String station_info = STATION_INFO.concat(sn);
		String json = "{}";
		String result = HttpClient.httpSSLGet(station_info,getHeaders("GET",station_info,json));
		result = parseResult(result);
		return result;
	}
	
	//获取基站数据列表 分页查询 page=1 count=100
	public String getStationList(int page, int count) {
		String device_list = STATION_LIST.concat("?").concat("page="+page).concat("&count="+count);
		String json = "{}";
		String result = HttpClient.httpSSLGet(device_list,getHeaders("GET",device_list,json));
		result = parseListResult(result);
		return result;
	}
	
	//根据sn 获取传感器历史数据 
	/**
	 *  startTime 否	Number	1479980803078	数据起始时间
		endTime	      否	Number	1479980811283	数据截止时间
		page	      否	Number	1	页数，默认为 1
		count	      否	Number	100	返回数据条数，默认为 100 条，不超过 1000
		order	      否	String	desc	排序方式。desc 为降序， asc 为升序，默认为降序
	 * @param sn
	 * @param start_time
	 * @param end_time
	 * @param page
	 * @param count
	 * @param order
	 * @return
	 */
	public String getDeviceLogDataBySN(String sn,long start_time,long end_time,int page,int count,String order) {
		String device_log_data = DEVICE_LOG_DATA.concat(sn).concat("/log").concat("?startTime="+start_time).concat("&endTime="+end_time).concat("&page="+page).concat("&count="+count).concat("&order="+order);
		String json = "{}";
		String result = HttpClient.httpSSLGet(device_log_data,getHeaders("GET",device_log_data,json));
		result = parseLogListResult(result);
		return result;
	}
	
	private String parseResult(String result){
		JSONObject obj = JsonTools.jsonStrToJsonObject(result);
		Integer code = obj.getInteger("err_code");
		JSONObject result_json = null;
		if(code == null){
			return "null";
		}
		if(code==0){
			result_json = obj.getJSONObject("data");
		}else{
			log.error("err_code:"+code+",,,result_json:"+result);
//			System.err.println("err_code:"+code);
			return "error";
		}
		return JsonTools.toJson(result_json);
	}
	
	private String parseListResult(String result){
		JSONObject obj = JsonTools.jsonStrToJsonObject(result);
		Integer code = obj.getInteger("err_code");
		JSONArray itemsArray = null;
		if(code == null){
			return "null";
		}
		if(code==0){
			itemsArray = obj.getJSONObject("data").getJSONArray("items");
		}else{
			log.error("err_code:"+code+",,,result_json:"+result);
//			System.err.println("err_code:"+code);
			return "error";
		}
		return JsonTools.toJson(itemsArray);
	}
	
	private String parseLogListResult(String result){
		JSONObject obj = JsonTools.jsonStrToJsonObject(result);
		Integer code = obj.getInteger("err_code");
		JSONArray itemsArray = null;
		if(code == null){
			log.error("result:"+result);
			return "null";
		}
		if(code==0){
			itemsArray = obj.getJSONArray("data");
		}else{
			log.error("err_code:"+code+",,,result_json:"+result);
//			System.err.println("err_code:"+code);
			return "error";
		}
		return JsonTools.toJson(itemsArray);
	}
	
	public static void main(String[] args) {
		SensoroConfig config = new SensoroConfig("AsZcfg1JrfnY","lCcujZd2qPSkQXHCANkjYnQxDYf1NX5sPb3AkJ800J1","ppalNIWrmVaPpw0a5n4mb81NwziPGqpM");
		DeviceDataAPI api = new DeviceDataAPI(config);
		String sn = "015A1117C625EC43";
		int page = 1;
		int count = 100;
		String json = api.getDeviceInfoBySN(sn);
		System.err.println(json);
		String deviceList = api.getStationList(page,count);
		long start_time = System.currentTimeMillis()-86400000l;
		long end_time = System.currentTimeMillis();
		String deviceLogDataBySN = api.getDeviceLogDataBySN(sn, start_time, end_time, page, count, "asc");
		System.err.println(deviceLogDataBySN);
	}
	
	

}
