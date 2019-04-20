package com.online.college.core.course.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;

import java.util.List;

/**
 * 课程服务层
 *
 * @author yx
 * @createtime 2019/04/20 18:00
 */
public interface ICourseService {

	/**
	*根据id获取
	**/
	Course getById(Long id);

	/**
	*获取所有
	**/
	List<Course> queryList(CourseQueryDto queryEntity);

	/**
	*分页获取
	**/
	TailPage<Course> queryPage(Course queryEntity, TailPage<Course> page);

	/**
	*创建
	**/
	void createSelectivity(Course entity);

	/**
	*根据id 进行可选性更新
	**/
	void updateSelectivity(Course entity);

	/**
	*物理删除
	**/
	void delete(Course entity);

	/**
	*逻辑删除
	**/
	void deleteLogic(Course entity);
}