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
 *  要使程序可以运行必须引入JSON-lib包，JSON-lib包同时依赖于以下的JAR包：

      1.commons-lang.jar
      2.commons-beanutils.jar
      3.commons-collections.jar
      4.commons-logging.jar 
      5.ezmorph.jar
      6.json-lib-2.4-jdk15.jar
      
      首先要去官方下载json-lib工具包
下载地址：
http://sourceforge.net/projects/json-lib/files/json-lib/json-lib-2.4/

依赖包的下载地址：
ezmorph 1.0.6：
http://ezmorph.sourceforge.net/
http://morph.sourceforge.net/
jakarta commons-lang 2.4 ：
http://commons.apache.org/lang/download_lang.cgi
jakarta commons-beanutils 1.7.0 ：
http://commons.apache.org/beanutils/download_beanutils.cgi
jakarta commons-collections 3.2 ：
http://commons.apache.org/collections/download_collections.cgi
jakarta commons-logging 1.1.1 ：
http://commons.apache.org/logging/download_logging.cgi
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
		obj.put(key, value);
	}
	
	public static void insertJSONObject(JSONObject job) {
		arr.add(job);
	}
	
	public static void clear() {
		obj.clear();
		arr.clear();
	}
	
	public static String PostJSON(URL url, Object post_obj) throws Exception {
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
            
            return sb.toString();
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
