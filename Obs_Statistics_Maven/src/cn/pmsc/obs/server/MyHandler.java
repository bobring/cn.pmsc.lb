package cn.pmsc.obs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.Headers;  
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import spring_jdbc.obs.bean.Obs;
import spring_jdbc.obs.impl.MyObsImpl;

@SuppressWarnings("restriction")
public class MyHandler implements HttpHandler {
	
	public void JsonToDB(String str) {
		int i, j;
		
		JSONArray arr = JSONArray.fromObject(str);
		JSONObject job = null;
		
		for(i = 0; i < arr.size(); i++) {
			job = arr.getJSONObject(i);
			
			if(job.has("id") && job.has("dtime") && job.has("statistics")) {
				Obs obs = new Obs();
				obs.setId(job.getString("id"));
				obs.setDate(job.getString("dtime"));
				obs.setStatistics(job.getString("statistics"));
				
				if(MyObsImpl.obsService.getObss(obs.getId(), obs.getDate()).size() == 0) {
					j = MyObsImpl.obsService.insert(obs);
				} else {
					j = MyObsImpl.obsService.update(obs);
				}
			} else {
				System.out.println("unknown json object: " + job.toString());
			}
		}
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = null;
		Headers requestHeaders = null;
		String str = null;
		StringBuffer buf = new StringBuffer();
		
		if (requestMethod.equalsIgnoreCase("POST")) {
			responseHeaders = exchange.getResponseHeaders();
//			responseHeaders.set("Content-Type", "text/plain");
			responseHeaders.set("Content-Type", "text/html; charset=utf-8");
			exchange.sendResponseHeaders(200, 0);
			
			OutputStreamWriter responseBody = new OutputStreamWriter(
					exchange.getResponseBody(), "UTF-8");
//			OutputStream responseBody = exchange.getResponseBody();
			requestHeaders = exchange.getRequestHeaders();
			Set<String> keySet = requestHeaders.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				List<String> values = requestHeaders.get(key);
				String s = key + " = " + values.toString() + "\n";
//				responseBody.write(s.getBytes());
				responseBody.append(s);
			}
//			responseBody.write("jdk自带轻量级http server例子".getBytes());
			responseBody.append("jdk自带轻量级http server例子");
			
			InputStreamReader in = new InputStreamReader(exchange.getRequestBody());
			BufferedReader br = new BufferedReader(in);
			
			while((str = br.readLine()) != null) {
				buf.append(str);
			}
			
			JsonToDB(buf.toString());
			
			responseBody.close();
		} else if(requestMethod.equalsIgnoreCase("GET")) {
			responseHeaders = exchange.getResponseHeaders();
//			responseHeaders.set("Content-Type", "text/plain");
			responseHeaders.set("Content-Type", "text/html; charset=utf-8");
			exchange.sendResponseHeaders(200, 0);
			
			OutputStreamWriter responseBody = new OutputStreamWriter(
					exchange.getResponseBody(), "UTF-8");
//			OutputStream responseBody = exchange.getResponseBody();
			requestHeaders = exchange.getRequestHeaders();
			Set<String> keySet = requestHeaders.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				List values = requestHeaders.get(key);
				String s = key + " = " + values.toString() + "\n";
//				responseBody.write(s.getBytes());
				responseBody.append(s);
			}
//			responseBody.write("jdk自带轻量级http server例子".getBytes());
			responseBody.append("jdk自带轻量级http server例子");
			
			
			
			responseBody.close();
		} else {
			System.out.println("Invalid http request method: " + requestMethod);
		}
	}

}
