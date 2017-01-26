package cn.pmsc.obs.server;

import java.io.BufferedReader;
import java.io.IOException;
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
	
	public String JsonToDB(String str) {
		int i, j;
		StringBuffer buf = new StringBuffer();
		
		JSONArray arr = JSONArray.fromObject(str);
		JSONObject job = null;
		
		for(i = 0, j = 0; i < arr.size(); i++) {
			job = arr.getJSONObject(i);
			
			if(job.has("id") && job.has("dtime") && job.has("statistics")) {
				Obs obs = new Obs();
				obs.setId(job.getString("id"));
				obs.setDate(job.getString("dtime"));
				obs.setStatistics(job.getString("statistics"));
				
				Obs rs = MyObsImpl.obsService.getObs(obs.getId(), obs.getDate());
				
				if(rs.equals(obs)) {
					buf.append("data already exists in DB: " + obs.toString() + System.lineSeparator());
				} else if(rs.same(obs)) {
					j += MyObsImpl.obsService.update(obs);
					buf.append("update data in DB, old: " + rs.toString() + 
							"; new: " + obs.toString() + System.lineSeparator());
				} else {
					j += MyObsImpl.obsService.insert(obs);
					buf.append("insert data to DB: " + obs.toString() + System.lineSeparator());
				}
			} else {
				buf.append("unknown json object: " + job.toString() + System.lineSeparator());
			}
		}
		buf.append(j + " lines effected in DB. " + System.lineSeparator());
		
		return buf.toString();
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		String str = null;
		StringBuffer json_buf = new StringBuffer();
		StringBuffer buf = new StringBuffer();
		
		exchange.sendResponseHeaders(200, 0);
		OutputStream responseBody = exchange.getResponseBody();
		
		String requestMethod = exchange.getRequestMethod();
//			responseHeaders.set("Content-Type", "text/plain");		
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/html; charset=utf-8");
		
		
		Headers requestHeaders = exchange.getRequestHeaders();
		Set<String> keySet = requestHeaders.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				List<String> values = requestHeaders.get(key);
				String s = key + " = " + values.toString() + System.lineSeparator();
//				responseBody.write(s.getBytes());
//				responseBody.write(s);
				System.out.println(s);
				buf.append(s);
			}
//			responseBody.write("jdk自带轻量级http server例子".getBytes());
//			responseBody.write("jdk自带轻量级http server例子");
			buf.append("jdk自带轻量级http server例子" + System.lineSeparator());
			
		if (requestMethod.equalsIgnoreCase("POST")) {
			InputStreamReader in = new InputStreamReader(exchange.getRequestBody());
			BufferedReader br = new BufferedReader(in);
			
			while((str = br.readLine()) != null) {
				System.out.println(str);
				json_buf.append(str);
			}
			
			try {
				buf.append(JsonToDB(json_buf.toString()) + System.lineSeparator());
				System.out.println(buf.toString());
			} catch (Throwable a) {
				System.out.println(a.toString());
			}
			
			
//			exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
			
			
			responseBody.write(buf.toString().getBytes());
			
		} else if(requestMethod.equalsIgnoreCase("GET")) {
//			responseBody.append("jdk自带轻量级http server例子");

		} else {
			responseBody.write(("Invalid http request method: " + requestMethod).getBytes());
		}
//		responseBody.flush();
		responseBody.close();
		
//		exchange.close();
	}
}
