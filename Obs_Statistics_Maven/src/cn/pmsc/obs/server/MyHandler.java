package cn.pmsc.obs.server;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
//import java.sql.SQLException;
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
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MyHandler.class);
	
	private static StringBuffer logbuf = new StringBuffer(); //用于存储调用过程中产生的记录,并作为HTTP回显打印出来
	private static int EffectedLines = 0; //统计每次请求过程中数据库写入行数，仅用于WriteDB函数，并在handle函数中初始化为零
	private static int response_status = HttpURLConnection.HTTP_INTERNAL_ERROR;
	
	private ClassPathXmlApplicationContext context;
	
	public MyHandler(ClassPathXmlApplicationContext ctx) {
		this.context = ctx;
	}

	public boolean WriteDB(JSONObject job) {
		int j = 0;
		String str = null;
		
		if(job.has("datatime") && job.has("checktime") && job.has("list") && job.has("count")) {
			Sta sta = new Sta();
			Sta b = null;
			
			try {
				sta.setDatatime(job.getString("datatime"));
			} catch(IllegalArgumentException e) {
				str = "illegal time format, like yyyy-mm-dd HH:MM:SS, error: " + job.getString("datatime").toString();
				logger.error("WriteDB(JSONObject) - " + str, e); //$NON-NLS-1$
				logbuf.append(str + System.lineSeparator());
				return false;
			}
			
			try {
				sta.setchecktime(job.getString("checktime"));
			} catch(IllegalArgumentException e) {
				str = "illegal time format, like yyyy-mm-dd HH:MM:SS, error: " + job.getString("checktime").toString();
				logger.error("WriteDB(JSONObject) - " + str, e); //$NON-NLS-1$
				logbuf.append(str + System.lineSeparator());
				return false;
			}
			
			sta.setList(job.getString("list"));
			sta.setCount(job.getLong("count"));
			
			StaServiceBean staService = (StaServiceBean)context.getBean("StaServiceBean");
			
			List<Sta> rs = staService.getSta(sta.getDatatime(), sta.getchecktime());
			
			if(rs.size() == 1) {
				b = rs.get(0);
				
				if(b.same(sta)) {
					str = "data already exists in DB: " + sta.toString();
					
					if (logger.isDebugEnabled()) {
						logger.debug("WriteDB(JSONObject) - " + str); //$NON-NLS-1$
					}
					logbuf.append(str + System.lineSeparator());
				} else {
					j += staService.update(sta);
					str = "update data in DB, " + System.lineSeparator() + 
							" old: " + b.toString() + System.lineSeparator() + 
							" new: " + sta.toString();

					if (logger.isDebugEnabled()) {
						logger.debug("WriteDB(JSONObject) - " + str); //$NON-NLS-1$
					}
					logbuf.append(str + System.lineSeparator());
				}
			} else if(rs.size() == 0) {
				j += staService.insert(sta);
				str = "insert data to DB: " + sta.toString();
				if (logger.isDebugEnabled()) {
					logger.debug("WriteDB(JSONObject) - " + str); //$NON-NLS-1$
				}
				logbuf.append(str + System.lineSeparator());
			} else {
				str = "DB error, expect no more than 1, but " + rs.size() + 
						" records founded: " + rs.toString();
				logger.error("WriteDB(JSONObject) - " + str, null);
				logbuf.append(str + System.lineSeparator());
				return false;
			}
			
		} else {
			str = "unknown json object: " + job.toString();
			logger.error("WriteDB(JSONObject) - " + str, null);
			logbuf.append(str + System.lineSeparator());
			return false;
		}
		
		EffectedLines += j;
		return true;
	}
	
	public void JsonToDB(String str) {
		int i, success;
		
		success = 0;
		
		JSONArray arr = null;
		JSONObject job = null;
		
		Object json = new JSONTokener(str).nextValue();
		
		if (json instanceof JSONArray){
		    arr = (JSONArray)json;
		    for(i = 0; i < arr.size(); i++) {
		    	job = arr.getJSONObject(i);
		    	if(WriteDB(job)) {
		    		success++;
		    	}
			}
		} else if(json instanceof JSONObject){
		    job = (JSONObject)json;
		    if(WriteDB(job)) {
	    		success++;
	    	}
		} else {
			logger.error("JsonToDB(String) - no JSON format data.", null); //$NON-NLS-1$
			logbuf.append("no JSON format data. " + System.lineSeparator());
			response_status = HttpURLConnection.HTTP_BAD_REQUEST;
		}

		if (logger.isInfoEnabled()) {
			logger.info("JsonToDB(String) - " + EffectedLines + " lines effected in DB."); //$NON-NLS-1$
		}

		logbuf.append(EffectedLines + " lines effected in DB." + System.lineSeparator());
		
		if (success != 0) {
			response_status = HttpURLConnection.HTTP_OK;
		}
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		logbuf.setLength(0);
		EffectedLines = 0;
		response_status = HttpURLConnection.HTTP_INTERNAL_ERROR;
		
		String str = null;
		StringBuffer json_buf = new StringBuffer();
		
		OutputStream responseBody = exchange.getResponseBody();
		
		String requestMethod = exchange.getRequestMethod();
//			responseHeaders.set("Content-Type", "text/plain");		
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/html; charset=utf-8");
		
		str = exchange.getProtocol() + " request from " + exchange.getRemoteAddress() + " to "
				+ exchange.getLocalAddress();
		logbuf.append(str + System.lineSeparator());

		if (logger.isInfoEnabled()) {
			logger.info("handle(HttpExchange) - " + str); //$NON-NLS-1$
		}
		
		Headers requestHeaders = exchange.getRequestHeaders();
		Set<String> keySet = requestHeaders.keySet();
			Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				List<String> values = requestHeaders.get(key);
				String s = key + " = " + values.toString();
				System.out.println(s);
				logbuf.append(s + System.lineSeparator());
			}
			logbuf.append("jdk自带轻量级http server例子" + System.lineSeparator());
			
		if (requestMethod.equalsIgnoreCase("POST")) {
			InputStreamReader in = new InputStreamReader(exchange.getRequestBody());
			BufferedReader br = new BufferedReader(in);
			
			while((str = br.readLine()) != null) {
//				System.out.println(str);
				json_buf.append(str);
			}
			
			try {
				JsonToDB(json_buf.toString());
			} catch (Throwable a) {
				logger.error("handle(HttpExchange) - a=" + a, a); //$NON-NLS-1$
				logbuf.append(a.getStackTrace().toString());
				response_status = HttpURLConnection.HTTP_INTERNAL_ERROR;
			}
			
			exchange.sendResponseHeaders(response_status, 0);
//			exchange.sendResponseHeaders(200, buf.toString().getBytes().length);
			
			
			responseBody.write(logbuf.toString().getBytes());
			
		} else if(requestMethod.equalsIgnoreCase("GET")) {
//			responseBody.append("jdk自带轻量级http server例子");

		} else {
			str = "Invalid http request method: " + requestMethod;
			logger.error(str, null); //$NON-NLS-1$
			responseBody.write(str.getBytes());
		}
//		responseBody.flush();
		responseBody.close();
		
//		exchange.close();
	}
}
