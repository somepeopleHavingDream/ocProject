package com.online.college.core.course.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseSection;

import java.util.List;

/**
 * 课程章节服务层类
 * 
 * @author yx
 * @createtime 2019/04/21 12:31
 */
public interface ICourseSectionService {

	/**
	*根据id获取
	**/
	CourseSection getById(Long id);

	/**
	*获取所有
	**/
	List<CourseSection> queryAll(CourseSection queryEntity);
	
	/**
	 * 获取课程章最大的sort
	 */
	Integer getMaxSort(Long courseId);
	
	/**
	*分页获取
	**/
	TailPage<CourseSection> queryPage(CourseSection queryEntity ,TailPage<CourseSection> page);

	/**
	*创建
	**/
	void createSelectivity(CourseSection entity);
	
	/**
	*批量创建
	**/
	void createList(List<CourseSection> entityList);

	/**
	*根据id更新
	**/
	void update(CourseSection entity);

	/**
	*根据id 进行可选性更新
	**/
	void updateSelectivity(CourseSection entity);

	/**
	*物理删除
	**/
	void delete(CourseSection entity);

	/**
	*逻辑删除
	**/
	void deleteLogic(CourseSection entity);



}

