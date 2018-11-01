package com.online.college.common.web.auth;

import java.io.Serializable;
import java.util.Set;

/**
 * 权限用户
 */
public class SessionUser implements Serializable{
	private static final long serialVersionUID = 5235871075980402075L;
	
	private Long userId;//ID
	private String userName;//用户名
	private String realName;//真实姓名
	private Integer status;//用户的状态，根据业务自由处理；
	private String password;//密码
	private String headPhoto;//头像
	private Set<String> permissions;//权限
	
	public Long getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getRealName() {
		return realName;
	}


	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public void setId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	
}
