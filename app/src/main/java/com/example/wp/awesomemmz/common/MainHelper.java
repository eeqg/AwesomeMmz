package com.example.wp.awesomemmz.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wp on 2019/2/13.
 */
public class MainHelper {
	private static MainHelper instance;
	
	public static MainHelper getInstance() {
		if (instance == null)
			instance = new MainHelper();
		return instance;
	}
	
	private MainHelper() {
	}
	
	public List<String> getTestData(int count) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			list.add(i + "");
		}
		return list;
	}
}
