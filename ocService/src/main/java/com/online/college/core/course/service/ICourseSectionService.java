package com.online.college.core.course.service;

import java.util.List;
import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseSection;


public interface ICourseSectionService {

	/**
	*根据id获取
	**/
	public CourseSection getById(Long id);

	/**
	*获取所有
	**/
	public List<CourseSection> queryAll(CourseSection queryEntity);

	/**
	*分页获取
	**/
	public TailPage<CourseSection> queryPage(CourseSection queryEntity ,TailPage<CourseSection> page);

	/**
	*创建
	**/
	public void create(CourseSection entity);

	/**
	*根据id更新
	**/
	public void update(CourseSection entity);

	/**
	*根据id 进行可选性更新
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



}

