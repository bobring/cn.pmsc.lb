package com.pmsc.warning.client;

import java.util.HashMap;
import java.util.Map;

public class MyWarnMap {
	protected Map<String, String> map = new HashMap<String, String>();
	
	public MyWarnMap(String mapdata) {
		String[] lines = mapdata.split(System.lineSeparator());
		String[] rows;
		
		for(int i = 0; i < lines.length; i++) {
			rows = lines[i].trim().split("\\s+");
			if(rows.length >= 2) {
				map.put(rows[0], rows[1]);
			} else {
				System.out.println("invalid line - " + rows);
			}
		}
	}
	
	public String getValue(String key) {
		return map.get(key);
	}
	
	public void insert(String key, String value) {
		map.put(key, value);
	}
}
