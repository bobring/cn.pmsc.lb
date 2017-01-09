package cn.pmsc.lb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;


/**
 * json-lib-2.4-jdk15.jar is needed
 *
 */
public class MySimpleJson {
	
	private static JSONObject obj = new JSONObject();
	private static JSONArray arr = new JSONArray();
	
	
	public static JSONObject getJSONObject() {
		return obj;
	}
	
	public static JSONArray getJSONArray() {
		return arr;
	}
	
	public static void insertJSONElement(String key, Object value) {
		obj.element(key, value);
	}
	
	public static void insertJSONObject(JSONObject job) {
		arr.element(job);
	}
	
	public static void clear() {
		obj.clear();
		arr.clear();
	}
	
	public static void PostJSON(URL url, Object post_obj) throws Exception {
		HttpURLConnection connection = null;
		try {
			//创建连接
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
	        connection.setDoInput(true);
	        connection.setRequestMethod("POST");
	        connection.setUseCaches(false);
	        connection.setInstanceFollowRedirects(true);
	        connection.setRequestProperty("Content-Type", 
	        		"application/json;charset=utf-8");
	        connection.connect();
	        
	      //POST请求
	        DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.writeBytes(post_obj.toString());
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            System.out.println(sb);
            reader.close();
            // 断开连接
            connection.disconnect();
            
            //释放内存，清除已发送的JSON数据
            clear();
		} catch (MalformedURLException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
			throw e;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        	throw e;
        } catch (IOException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
        	throw e;
        }
        
	}

}
