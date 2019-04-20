package com.online.college.core.course.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseSection;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程章节Dao
 *
 * @author yx
 * @createtime 2019/04/20 18:04
 */
@Repository
public interface CourseSectionDao {

	/**
	*根据id获取
	**/
	CourseSection getById(Long id);

	/**
	*获取所有
	**/
	List<CourseSection> queryAll(CourseSection queryEntity);

	/**
	 * 
	 */
	Integer getMaxSort(Long courseId);
	
	/**
	*获取总数量
	**/
	Integer getTotalItemsCount(CourseSection queryEntity);

	/**
	*分页获取
	**/
	List<CourseSection> queryPage(CourseSection queryEntity , TailPage<CourseSection> page);

	/**
	*创建新记录
	**/
	void createSelectivity(CourseSection entity);
	
	/**
	 * 批量创建
	 */
	void createList(List<CourseSection> entityList);

	/**
	*根据id更新
	**/
	void update(CourseSection entity);

	/**
	*根据id选择性更新自动
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

	/**
	*物理删除课程对应的章节
	**/
	void deleteByCourseId(CourseSection entity);

	/**
	*逻辑删除课程对应的章节
	**/
	void deleteLogicByCourseId(CourseSection entity);
}