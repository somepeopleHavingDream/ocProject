package com.online.college.core.course.domain;

import com.online.college.common.orm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 课程实体类
 *
 * @author yx
 * @createtime 2019/04/20 18:17
 */
@Setter
@Getter
public class Course extends BaseEntity {

	private static final long serialVersionUID = -935786327879089574L;

	/**
	 * 课程名称
	 **/
	private String name;

	/**
	 * 课程类型
	 **/
	private String type;

	/**
	 * 课程分类
	 **/
	private String classify;

	/**
	 * 课程分类名称
	 */
	private String classifyName;

	/**
	 * 课程二级分类
	 **/
	private String subClassify;

	/**
	 * 课程二级分类名称
	 */
	private String subClassifyName;

	/**
	 * 课程方向
	 **/
	private String direction;

	/**
	 * 归属人
	 **/
	private String username;

	/**
	 * 课程级别：1-初级，2-中级，3-高级
	 **/
	private Integer level;

	/**
	 * 是否免费：0-否，1-是
	 **/
	private Integer free;

	/**
	 * 课程价格
	 **/
	private BigDecimal price;

	/**
	 * 时长
	 **/
	private String time;

	/**
	 * 未上架（0）、上架（1）
	 **/
	private Integer onsale;

	/**
	 * 课程描述
	 **/
	private String brief;

	/**
	 * 课程图片
	 */
	private String picture;

	/**
	 * 未推荐（0）、推荐（1）
	 **/
	private Integer recommend;

	/**
	 * 权重
	 **/
	private Integer weight;

	/**
	 * 学习人数
	 **/
	private Integer studyCount;
}
