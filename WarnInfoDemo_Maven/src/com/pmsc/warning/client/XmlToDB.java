package com.pmsc.warning.client;

import java.net.MalformedURLException;
import java.net.URL;

import com.pmsc.warning.XmlBean.*;

import cn.pmsc.lb.MySimpleJson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class XmlToDB {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 2) {
			throw new NullPointerException(
					"enter the Dest URL and the XML list");
		}
		
		WarnXML wx = null;
		JSONArray arr = new JSONArray();
		
		String[] files = args[1].split("\\s+");
		for (int i = 0; i < files.length; i++) {
			wx = (WarnXML) XMLUtil.XmlFileToObject(WarnXML.class, files[i]);
			arr.add((JSONObject) Client.warnxmltoJSON(wx));
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
