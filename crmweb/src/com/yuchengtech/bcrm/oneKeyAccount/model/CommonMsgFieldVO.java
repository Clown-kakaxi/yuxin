package com.yuchengtech.bcrm.oneKeyAccount.model;
/**
 * 请求报文的字段通用VO
 * @author wx
 *
 */
public class CommonMsgFieldVO {

	private String name;//字段的name
	private int length;//字段长度(-1表示变长)
	private String content;//字段内容
	private String tagType;//xml格式的报文中的标签类型：如<head><body>等
	private String desc;//字段描述
	
	public CommonMsgFieldVO() {
		
	}
	
	public CommonMsgFieldVO(String name, int length, String content, String tagType, String desc) {
		this.name = name;
		this.length = length;
		this.content = content;
		this.tagType = tagType;
		this.desc = desc;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
}
