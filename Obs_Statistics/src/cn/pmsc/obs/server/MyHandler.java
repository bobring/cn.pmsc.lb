package cn.pmsc.obs.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sun.net.httpserver.Headers;  
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		// TODO Auto-generated method stub
		String requestMethod = exchange.getRequestMethod();
		if (requestMethod.equalsIgnoreCase("POST")) {
			Headers responseHeaders = exchange.getResponseHeaders();
//			responseHeaders.set("Content-Type", "text/plain");
			responseHeaders.set("Content-Type", "text/html; charset=utf-8");
			exchange.sendResponseHeaders(200, 0);
			
			OutputStreamWriter responseBody = new OutputStreamWriter(
					exchange.getResponseBody(), "UTF-8");
//			OutputStream responseBody = exchange.getResponseBody();
			Headers requestHeaders = exchange.getRequestHeaders();
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
		} else if(requestMethod.equalsIgnoreCase("GET")) {
			
		}
	}

}
