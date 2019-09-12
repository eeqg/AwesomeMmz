package com.example.wp.awesomemmz.skill.font;

/**
 * Created by wp on 2019/9/12.
 */
public enum FontType {
	//C:\Windows\Fonts 可以获取到电脑系统的字体文件
	NORMAL(0, "Normal", "fonts/FZLanTingHeiS-L-GB-Regular.TTF"),//方正兰亭细黑简体
	BOLD(1, "Bold", "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF"),//方正兰亭中粗黑简体
	FUTURE(2, "Future", "fonts/Futura-CondensedMedium.ttf"),//拉丁
	LOBSTER(3, "Lobster", "fonts/Lobster-1.4.otf"),//龙虾字体
	LNKFREE(4, "Inkfree", "fonts/Inkfree.ttf"),//
	MONB(4, "Monb", "fonts/monbaiti.ttf"),//
	SIMFANG(4, "仿宋", "fonts/simfang.ttf"),//仿宋
	SIMKAI(5, "楷体", "fonts/simkai.ttf");//楷体
	
	private int index;
	private String name;
	private String path;
	
	FontType(int index, String name, String path) {
		this.index = index;
		this.name = name;
		this.path = path;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
}
