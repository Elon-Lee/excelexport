package com.test.tdq.uitl.https;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/* 
 * 利用HttpClient进行post请求的工具类 
 */
public class HttpClientUtil {
	public static String doPost(String url, Map<String, String> map, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
			}
			if (list.size() > 0) {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * HTTP Get 获取内容
	 * 
	 * @param url请求的url地址
	 *            ?之前的地址
	 * @param params请求的参数
	 * @param charset编码格式
	 * @return 页面内容
	 */
	public static String doGet(String url, String charSet) {
		HttpClient httpClient = null;
		HttpGet httpPost = null;
		String result = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpGet(url);
			// 设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			HttpResponse response = httpClient.execute(httpPost);
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charSet);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
//		String result = HttpClientUtil.doGet("http://tianqi.2345.com/t/wea_history/js/201604/60616_201604.js", "GBK");
//		System.err.println(result);
//		Map<String,String> val = new HashMap<String, String>();
//		val.put("jtysSignMembers", "511304198605173828");
//		String aaa = HttpClientUtil.doPost("http://192.168.9.91:8088/jkxjAppHisApi/jtys/getAllSignInfoByMembers", val, "utf-8");
//		System.out.println(aaa);

	}
}