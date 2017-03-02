package com.yuhuayuan.tool.net.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class HttpUtils {
	public static String postJson(String url, JSONObject jo) throws Exception {
		String content = jo.toString();
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(content, "utf-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(stringEntity);

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String returnVal = EntityUtils.toString(entity);
			return returnVal;
		} finally {
			// System.out.println("ok!");
			if (httpPost != null)
				httpPost.releaseConnection();
		}
	}

	/**
	 * 向一个URL提交字符串内容
	 * 
	 * @param url
	 * @param content
	 */
	public static String postString(String url, String content) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(content, "utf-8");
			httpPost.addHeader("Content-Type", "text/xml");
			httpPost.setEntity(stringEntity);

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String returnVal = EntityUtils.toString(entity);
			return returnVal;
		} finally {
			// System.out.println("ok!");
			if (httpPost != null)
				httpPost.releaseConnection();
		}
	}

	/**
	 * post请求，返回JSONObject
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static JSONObject post4JsonObject(String url, Map<String, String> params) throws Exception {
		String returnVal = post4String(url, params);
		JSONObject jo = (JSONObject) JSONObject.parse(returnVal);
		return jo;
	}

	/**
	 * Post请求，返回JSONArray
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static JSONArray post4JsonArray(String url, Map<String, String> params) throws Exception {
		String returnVal = post4String(url, params);
		JSONArray jo = (JSONArray) JSONArray.parse(returnVal);
		return jo;
	}

	/**
	 * POST请求，返回String
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String post4String(String url, Map<String, String> params) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			if (params != null && params.size() > 0) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				String key, value;
				for (Iterator<String> keys = params.keySet().iterator(); keys.hasNext();) {
					key = keys.next();
					value = params.get(key);
					nvps.add(new BasicNameValuePair(key, value));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
			}

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String returnVal = EntityUtils.toString(entity);
			return returnVal;
		} finally {
			// System.out.println("ok!");
			if (httpPost != null)
				httpPost.releaseConnection();
		}
	}

	/**
	 * GET请求，返回String
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static JSONObject get4JsonObject(String url, Map<String, String> params) throws Exception {
		String returnVal = get4String(url, params);
		JSONObject jo = (JSONObject) JSONObject.parse(returnVal);
		return jo;
	}

	/**
	 * Post请求，返回JSONArray
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static JSONArray get4JsonArray(String url, Map<String, String> params) throws Exception {
		String returnVal = get4String(url, params);
		JSONArray jo = (JSONArray) JSONArray.parse(returnVal);
		return jo;
	}

	/**
	 * GET请求，返回String
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String get4String(String url, Map<String, String> params) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpGet httpGet = null;
		try {

			String composedUrl = url;
			for (Iterator<String> keys = params.keySet().iterator(); keys.hasNext();) {
				String key = keys.next();
				String value = params.get(key);
				if (composedUrl.contains("?")) {
					composedUrl = composedUrl + "&" + key + "=" + value;
				} else {
					composedUrl = composedUrl + "?" + key + "=" + value;
				}
			}
			httpGet = new HttpGet(composedUrl);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String returnVal = EntityUtils.toString(entity);
			return returnVal;
		} finally {
			if (httpGet != null)
				httpGet.releaseConnection();
		}
	}

	public static String fillUrlParams(String url, String[] values) {
		int i = 0;
		String s = url;
		int idx = 0;
		while ((idx = s.indexOf("[?]")) != -1) {
			s = s.substring(0, idx) + values[i] + s.substring(idx + 3);
			i++;
		}
		return s;
	}

	public static String post2WebService(String url, String xml) throws Exception {
		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost httpPost = null;
		try {

			httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(xml, "utf-8");
			httpPost.addHeader("Content-Type", "text/xml; charset=UTF-8");
			httpPost.addHeader("SOAPAction", "http://tempuri.org/SendSMS");
			httpPost.setEntity(stringEntity);

			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String returnVal = EntityUtils.toString(entity);
			return returnVal;
		} finally {
			if (httpPost != null)
				httpPost.releaseConnection();
		}
	}

	 /** 
     * 上传图片 
     *  
     * @param urlStr 
     * @param textMap 
     * @param fileMap 
     * @return 
     */  
    public static String formUpload(String urlStr, Map<String, String> textMap,  
            Map<String, String> fileMap) {  
        String res = "";  
        HttpURLConnection conn = null;  
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符  
        try {  
            URL url = new URL(urlStr);  
            conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setReadTimeout(30000);  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            conn.setUseCaches(false);  
            conn.setRequestMethod("POST");  
            conn.setRequestProperty("Connection", "Keep-Alive");  
            conn  
                    .setRequestProperty("User-Agent",  
                            "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");  
            conn.setRequestProperty("Content-Type",  
                    "multipart/form-data; boundary=" + BOUNDARY);  
  
            OutputStream out = new DataOutputStream(conn.getOutputStream());  
            // text  
            if (textMap != null) {  
                StringBuffer strBuf = new StringBuffer();  
                Iterator iter = textMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                            "\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\""  
                            + inputName + "\"\r\n\r\n");  
                    strBuf.append(inputValue);  
                }  
                out.write(strBuf.toString().getBytes());  
            }  
  
            // file  
            if (fileMap != null) {  
                Iterator iter = fileMap.entrySet().iterator();  
                while (iter.hasNext()) {  
                    Map.Entry entry = (Map.Entry) iter.next();  
                    String inputName = (String) entry.getKey();  
                    String inputValue = (String) entry.getValue();  
                    if (inputValue == null) {  
                        continue;  
                    }  
                    File file = new File(inputValue);  
                    String filename = file.getName();  
       
                    String  contentType = "image/png";  

                    if (contentType == null || contentType.equals("")) {  
                        contentType = "application/octet-stream";  
                    }  
  
                    StringBuffer strBuf = new StringBuffer();  
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append(  
                            "\r\n");  
                    strBuf.append("Content-Disposition: form-data; name=\""  
                            + inputName + "\"; filename=\"" + filename  
                            + "\"\r\n");  
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");  
  
                    out.write(strBuf.toString().getBytes());  
  
                    DataInputStream in = new DataInputStream(  
                            new FileInputStream(file));  
                    int bytes = 0;  
                    byte[] bufferOut = new byte[1024];  
                    while ((bytes = in.read(bufferOut)) != -1) {  
                        out.write(bufferOut, 0, bytes);  
                    }  
                    in.close();  
                }  
            }  
  
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
            out.write(endData);  
            out.flush();  
            out.close();  
  
            // 读取返回数据  
            StringBuffer strBuf = new StringBuffer();  
            BufferedReader reader = new BufferedReader(new InputStreamReader(  
                    conn.getInputStream()));  
            String line = null;  
            while ((line = reader.readLine()) != null) {  
                strBuf.append(line).append("\n");  
            }  
            res = strBuf.toString();  
            reader.close();  
            reader = null;  
        } catch (Exception e) {  
            System.out.println("发送POST请求出错。" + urlStr);  
            e.printStackTrace();  
        } finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }  
        return res;  
    }  
	public static void main(String[] args) throws Exception {
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=REkjU1mzx-Oj6vs9mobKRsNDgY5-ftZowitPty7BNmeRurq5VZbf_K61l81ZMQpTUmGWw0GBXheo18MIxtmp75uH7_OEch_B8QK_hBDTin2_USduYNF6o3O6FHLcU0znRLTaAJAXRZ";
		String filepath="/Users/chenlian/Downloads/善美/clhead.png";  

        Map<String, String> textMap = new HashMap<String, String>();  
        Map<String, String> fileMap = new HashMap<String, String>();  
          
        fileMap.put("userfile", filepath);  
		
		String str = HttpUtils.formUpload(url, textMap, fileMap);
		System.out.println(str);
	}

}
