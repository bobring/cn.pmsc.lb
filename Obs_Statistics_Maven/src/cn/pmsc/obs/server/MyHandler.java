package cn.pmsc.obs.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.net.httpserver.Headers;  
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import spring_jdbc.obs.bean.Sta;
import spring_jdbc.obs.impl.StaServiceBean;

@SuppressWarnings("restriction")
public class MyHandler implements HttpHandler {
	
	private static int EffectedLines = 0; //统计每次请求过程中数据库写入行数，仅用于WriteDB函数，并在handle函数中初始化为零
	private ClassPathXmlApplicationContext context;
	
	public MyHandler(ClassPathXmlApplicationContext ctx) {
		this.context = ctx;
	}

	public String WriteDB(JSONObject job) {
		StringBuffer buf = new StringBuffer();
		int j = 0;
//		if(job.has("id") && job.has("dtime") && job.has("statistics") ) {
//			Obs obs = new Obs();
//			obs.setId(job.getString("id"));
//			obs.setDate(job.getString("dtime"));
//			obs.setStatistics(job.getString("statistics"));
//			
//			Obs rs = MyObsImpl.obsService.getObs(obs.getId(), obs.getDate());
//			
//			if(rs.equals(obs)) {
//				buf.append("data already exists in DB: " + obs.toString() + System.lineSeparator());
//			} else if(rs.same(obs)) {
//				j += MyObsImpl.obsService.update(obs);
//				buf.append("update data in DB, old: " + rs.toString() + 
//						"; new: " + obs.toString() + System.lineSeparator());
//			} else {
//				j += MyObsImpl.obsService.insert(obs);
//				buf.append("insert data to DB: " + obs.toString() + System.lineSeparator());
//			}
//		} else {
//			buf.append("unknown json object: " + job.toString() + System.lineSeparator());
//		}
		
		
		if(job.has("datatime") && job.has("checktime") && job.has("list") && job.has("count")) {
			Sta sta = new Sta();
			Sta b = null;
			
			try {
				sta.setDatatime(job.getString("datatime"));
			} catch(IllegalArgumentException e) {
				buf.append("illegal time format, like yyyy-mm-dd HH:MM:SS, error: " + job.getString("datatime").toString() + System.lineSeparator());
				return buf.toString();
			}
			
			try {
				sta.setchecktime(job.getString("checktime"));
			} catch(IllegalArgumentException e) {
				buf.append("illegal time format, like yyyy-mm-dd HH:MM:SS, error: " + job.getString("checktime").toString() + System.lineSeparator());
				return buf.toString();
			}
			
			sta.setList(job.getString("list"));
			sta.setCount(job.getLong("count"));
			
//			Sta rs = MyStaImpl.staService.getSta(sta.getDatatime(), sta.getchecktime());
			
//			context = new ClassPathXmlApplicationContext("Beans.xml");
			StaServiceBean staService = (StaServiceBean)context.getBean("StaServiceBean");
			
			List<Sta> rs = staService.getSta(sta.getDatatime(), sta.getchecktime());
			
			if(rs.size() == 1) {
				b = rs.get(0);
				
				if(b.same(sta)) {
					buf.append("data already exists in DB: " + sta.toString() + System.lineSeparator());
				} else {
					j += staService.update(sta);
					buf.append("update data in DB, " + System.lineSeparator() + 
							" old: " + b.toString() + System.lineSeparator() + 
							" new: " + sta.toString()  + System.lineSeparator());
				}
			} else if(rs.size() == 0) {
				j += staService.insert(sta);
				buf.append("insert data to DB: " + sta.toString() + System.lineSeparator());
			} else {
				buf.append("DB error, expect no more than 1, but " + rs.size() + 
						" records founded: " + rs.toString() + System.lineSeparator());
			}
			
		} else {
			buf.append("unknown json object: " + job.toString() + System.lineSeparator());
		}
		
		EffectedLines += j;
		return buf.toString();
	}
	
	public String JsonToDB(String str) {
		int i;
		StringBuffer buf = new StringBuffer();
		
		JSONArray arr = null;
		JSONObject job = null;
		
		Object json = new JSONTokener(str).nextValue();
		
		if (json instanceof JSONArray){
		    arr = (JSONArray)json;
		    for(i = 0; i < arr.size(); i++) {
		    	job = arr.getJSONObject(i);
		    	buf.append(WriteDB(job));
			}
		} else if(json instanceof JSONObject){
		    job = (JSONObject)json;
		    buf.append(WriteDB(job));
		} else {
			buf.append("no JSON format data. " + System.lineSeparator());
		}
		
		buf.append(EffectedLines + " lines effected in DB. ");
		
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
//				System.out.println(a.toString());
				a.printStackTrace();
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
