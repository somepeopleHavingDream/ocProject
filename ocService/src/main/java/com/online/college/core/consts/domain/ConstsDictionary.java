package com.online.college.core.consts.domain;

import com.online.college.common.orm.BaseEntity;

public class ConstsDictionary extends BaseEntity{
	
	private static final long serialVersionUID = -2306103209090733904L;

	/**
	*分组code
	**/
	private Long groupCode;

	/**
	*分组名称
	**/
	private String groupName;

	/**
	*键
	**/
	private String key;

	/**
	*值
	**/
	private String value;

	/**
	*排序
	**/
	private Long sort;

	public Long getGroupCode(){
		return groupCode;
	}
	public void setGroupCode(Long groupCode){
		this.groupCode = groupCode;
	}

	public String getGroupName(){
		return groupName;
	}
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}

	public String getValue(){
		return value;
	}
	public void setValue(String value){
		this.value = value;
	}

	public Long getSort(){
		return sort;
	}
	public void setSort(Long sort){
		this.sort = sort;
	}



}

