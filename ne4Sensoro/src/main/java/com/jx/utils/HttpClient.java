package com.jx.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpClient {
	private static final Logger log = LoggerFactory.getLogger(HttpClient.class);
	private HttpClient(){}

	public static String httpGet(String url, Map<String, String> params) {
		log.debug("start get {} with {}",url,JSON.toJSON(params));
		String body = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		List<NameValuePair> pars = null;
		if (null == params) {
			pars = new ArrayList<NameValuePair>();
		} else {
			pars = new ArrayList<NameValuePair>(params.size());
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				pars.add(pair);
			}
		}
		try {
			String paraString = EntityUtils.toString(new UrlEncodedFormEntity(pars));
			String uri = get.getURI().toString();
			if(uri.contains("?")){
				uri = uri.concat("&");
			}else{
				uri = uri.concat("?");
			}
			
			get.setURI(new URI(uri.concat(paraString)));
			log.debug("request method is GET and url is {}", get.getURI());
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException | IOException | URISyntaxException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("get response is {}", body);
		return body;
	}
	
	public static String httpPost(String url, Map<String, String> params) {
		log.debug("start post {} with {}",url,JSON.toJSON(params));
		String body = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
		Iterator<Entry<String, String>> it = params.entrySet().iterator();
		List<BasicNameValuePair> pars = new ArrayList<BasicNameValuePair>();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			pars.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(pars, "UTF-8"));
			post.setConfig(config);
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.debug("get response is {}", body);
		return body;
	}
	
	public static String httpSSLGet(String url,Map<String,String> headers) {
		String body = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = new SSLClient();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HttpGet get = new HttpGet(url);
		get.setHeader("Content-Type", "application/json");  
        if (headers != null) {  
        	Set<String> keySet = headers.keySet();
    		for(String key: keySet){
    			get.addHeader(key, headers.get(key));
    		}
        }  
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
        get.setConfig(config);
		try {
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		log.debug("get response is {}", body);
		return body;
	}
	
	
	
	public static String httpSSLPostJson(String url, String json,Map<String,String> headers) {
		log.debug("start post json {} with {}",url,JSON.toJSON(json));
		String body = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = new SSLClient();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");  
        if (headers != null) {  
        	Set<String> keySet = headers.keySet();
    		for(String key: keySet){
    			post.addHeader(key, headers.get(key));
    		}
        }  
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
		StringEntity s = new StringEntity(json.toString(),"utf-8");    
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setEntity(s);
		post.setConfig(config);
		try {
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		log.debug("get response is {}", body);
		return body;
	}


	public static String postJson(String url, String json) {
		log.debug("start post json {} with {}",url,JSON.toJSON(json));
		String body = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
		post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
		post.setConfig(config);
		try {
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		log.debug("get response is {}", body);
		return body;
	}
	
	public static String postJson(String url, String json,Map<String,String> headers) {
		log.debug("start post json {} with {}",url,JSON.toJSON(json));
		String body = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost post = new HttpPost(url);
		for(String key : headers.keySet()){
			post.addHeader(key, headers.get(key));
		}
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
		StringEntity s = new StringEntity(json.toString(),"utf-8");    
        s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        post.setEntity(s);
//		post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
//		post.setConfig(config);
		try {
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		log.debug("get response is {}", body);
		return body;
	}
	

	
	public static String postXML(String url, String data) {
		log.debug("start post xml {} with {}",url,JSON.toJSON(data));
		String body = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(10000).setConnectTimeout(10000).setSocketTimeout(10000).build();
		CloseableHttpResponse response = null;
		post.setEntity(new StringEntity(data, ContentType.APPLICATION_XML));
		post.setConfig(config);
		try {
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		log.debug("get response is {}", body);
		return body;
	}
}

