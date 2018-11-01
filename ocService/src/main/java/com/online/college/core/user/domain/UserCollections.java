package com.online.college.core.user.domain;

import com.online.college.common.orm.BaseEntity;


public class UserCollections extends BaseEntity{

	private static final long serialVersionUID = -3909997252117758595L;

	/**
	*用户id
	**/
	private Long userId;

	/**
	*用户收藏分类
	**/
	private Long classify;

	/**
	*用户收藏备注
	**/
	private String tips;

	public Long getUserId(){
		return userId;
	}
	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getClassify(){
		return classify;
	}
	public void setClassify(Long classify){
		this.classify = classify;
	}

	public String getTips(){
		return tips;
	}
	public void setTips(String tips){
		this.tips = tips;
	}



}

