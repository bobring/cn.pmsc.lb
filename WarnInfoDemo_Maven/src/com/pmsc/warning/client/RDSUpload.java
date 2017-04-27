package com.pmsc.warning.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.pmsc.warning.XmlBean.*;

import cn.pmsc.lb.MyProperties;
import cn.pmsc.lb.MySimpleJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RDSUpload {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RDSUpload.class);
	
	
	public static void StrToMap(String mapdata, Map<String, String> map) {
		String[] lines = mapdata.split(System.lineSeparator());
		String[] rows;
		
		for(int i = 0; i < lines.length; i++) {
			rows = lines[i].trim().split("\\s+");
			if(rows.length >= 2) {
				map.put(rows[0], rows[1]);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("StrToMap(String, Map<String,String>) - ", "invalid line - " + rows); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("StrToMap(String, Map<String,String>) - map data insert: ", map.size() + " lines."); //$NON-NLS-1$ //$NON-NLS-2$
			
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, Exception {
		// TODO Auto-generated method stub
		if (args.length < 4) {
			throw new NullPointerException(
					"enter the Dest URL, area code list, URL list and the XML list");
		}
		
		WarnXML wx = null;
		JSONArray arr = new JSONArray();
		JSONObject job = null;
		
//		MyProperties area_list = null;
//		MyProperties url_list = null;
		
		Map<String, String> area_list = new HashMap<String, String>();
		Map<String, String> url_list = new HashMap<String, String>();
		
		StrToMap(args[1], area_list);
		StrToMap(args[2], url_list);
		
		String area = null;
		String url = null;
		String tmpstr = null;
		
//		area_list = new MyProperties(new FileInputStream(new File(args[1])));
//		url_list = new MyProperties(new FileInputStream(new File(args[2])));
		
//		try {
////			area_list = new MyProperties(new StringReader(args[1]));
////			url_list = new MyProperties(new StringReader(args[2]));
//			
////			area_list = new MyProperties(new FileInputStream(new File(args[1])));
////			url_list = new MyProperties(new FileInputStream(new File(args[2])));
//		} catch (Exception e) {
//			logger.error("main(String[]) - e=" + e, e); //$NON-NLS-1$
//			throw new RuntimeException(e); 
//		}
		
		String[] files = args[3].split("\\s+");
		for (int i = 0; i < files.length; i++) {
			try {
				wx = (WarnXML) XMLUtil.XmlFileToObject(WarnXML.class, files[i]);
				job = (JSONObject) Client.warnxmltoJSON(wx);
				
				area = area_list.get(files[i]);
				url = url_list.get(files[i]);

				if (area == null) {
					if (logger.isInfoEnabled()) {
						logger.info("file: " + files[i] + " have no area code, try to make one."); //$NON-NLS-1$
					}

					if ((tmpstr = wx.getSenderCode()).length() >= 6) {
						if (tmpstr.substring(2, 6).equals("0000")) {
							area = "Province";
						} else {
							area = "City";
						}
					}
				}
				
				if (url == null) {
					if (logger.isInfoEnabled()) {
						logger.info("file: " + files[i] + " have no URL address."); //$NON-NLS-1$
					}
				}
				
				job.put("type", area);
				job.put("referUrl", url);
				
				arr.add(job);
			} catch (Exception e) {
				logger.error("main(String[]) - e=" + e + System.lineSeparator() + files[i], e);
				e.printStackTrace();
			}
		}
		
		try {
			MySimpleJson.PostJSON(new URL(args[0]), arr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
