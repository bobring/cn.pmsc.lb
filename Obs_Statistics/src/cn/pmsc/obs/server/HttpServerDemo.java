package cn.pmsc.obs.server;

import com.sun.net.httpserver.Headers;  
import com.sun.net.httpserver.HttpExchange;  
import com.sun.net.httpserver.HttpHandler;  
import com.sun.net.httpserver.HttpServer;  
  
import java.io.IOException;  
import java.io.OutputStream;  
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;  
import java.util.List;  
import java.util.Set;  
import java.util.concurrent.Executors;

public class HttpServerDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
			throw new NullPointerException(
					"Please enter the URL address");
		}
		
		int port = -1;
		
		try {
			URL url = new URL(args[0]);
			
			port = url.getPort();
			if(port == -1) {
				port = url.getDefaultPort();
			}
			
			InetSocketAddress addr = new InetSocketAddress(port);
			HttpServer server = HttpServer.create(addr, 0);
			
			//server.createContext("/server", new MyHandler());
			server.createContext(url.getPath(), new MyHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();
			System.out.println("Server is listening on port: " + port);
			System.out.println("URL = " + args[0]);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

}
