package com.example.wp.awesomemmz.skill;

/**
 * Created by wp on 2019/8/20.
 */
public class MsgEvent {
	private int key;
	private String value;
	
	public MsgEvent(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
