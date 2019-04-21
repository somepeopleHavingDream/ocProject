package com.online.college.common.orm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 基础实体类
 *
 * @author yx
 * @createtime 2019/04/21 12:56
 */
@Getter
@Setter
public class BaseEntity extends LongModel{
	private static final long serialVersionUID = 968132587307913395L;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 创建人(username)
	 */
	private String createUser;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 最后一位更新人(username)
	 */
	private String updateUser;
	
	/**
	 * 逻辑删除
	 */
	private Integer del = 0;
}

