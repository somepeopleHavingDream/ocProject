package com.online.college.core.course.dao;

import java.util.List;
import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseSection;


public interface CourseSectionDao {

	/**
	*根据id获取
	**/
	public CourseSection getById(Long id);

	/**
	*获取所有
	**/
	public List<CourseSection> queryAll(CourseSection queryEntity);

	/**
	*获取总数量
	**/
	public Integer getTotalItemsCount(CourseSection queryEntity);

	/**
	*分页获取
	**/
	public List<CourseSection> queryPage(CourseSection queryEntity , TailPage<CourseSection> page);

	/**
	*创建新记录
	**/
	public void create(CourseSection entity);

	/**
	*根据id更新
	**/
	public void update(CourseSection entity);

	/**
	*根据id选择性更新自动
	**/
	public void updateSelectivity(CourseSection entity);

	/**
	*物理删除
	**/
	public void delete(CourseSection entity);

	/**
	*逻辑删除
	**/
	public void deleteLogic(CourseSection entity);

	/**
	*物理删除课程对应的章节
	**/
	public void deleteByCourseId(CourseSection entity);

	/**
	*逻辑删除课程对应的章节
	**/
	public void deleteLogicByCourseId(CourseSection entity);
}

