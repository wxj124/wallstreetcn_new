package com.longau.wallstreetcn.bean;

import java.io.Serializable;

public class CountryBean implements Serializable{
	//id,createtime,
	//private String source;  //信息来源
//	private String district;//内容所属地区

	private String content;  //信息内容
	private String type;     //信息属性news 或   data
	private String categorySet;   //类型设置 
	public CountryBean() {
	}
	public String getCategorySet() {
		return categorySet;
	}
	public void setCategorySet(String string) {
		this.categorySet = string;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
//	public String getSource() {
//		return source;
//	}
//	public void setSource(String source) {
//		this.source = source;
//	}
//	public String getDistrict() {
//		return district;
//	}
//	public void setDistrict(String district) {
//		this.district = district;
//	}

}
