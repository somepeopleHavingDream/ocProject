package com.online.college.core.course.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程Dao
 *
 * @author yx
 * @createtime 2019/04/20 18:02
 */
@Repository
public interface CourseDao {

	/**
	 * 根据id获取
	 **/
	Course getById(Long id);

	/**
	 * 根据条件获取所有， queryEntity：查询条件；
	 **/
	List<Course> queryList(CourseQueryDto queryEntity);

	/**
	 * 获取总数量
	 **/
	Integer getTotalItemsCount(Course queryEntity);

	/**
	 * 分页获取
	 **/
	List<Course> queryPage(Course queryEntity, TailPage<Course> page);

	/**
	 * 创建新记录
	 **/
	void create(Course entity);

	void createSelectivity(Course entity);

	/**
	 * 根据id更新
	 **/
	void update(Course entity);

	/**
	 * 根据id选择性更新自动
	 **/
	void updateSelectivity(Course entity);

	/**
	 * 物理删除
	 **/
	void delete(Course entity);

	/**
	 * 逻辑删除
	 **/
	void deleteLogic(Course entity);
}
