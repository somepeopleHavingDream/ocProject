package com.online.college.core.user.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseSectionDao {

	/**
	 * 根据id获取
	 **/
	UserCourseSection getById(Long id);

	/**
	 * 获取所有
	 **/
	List<UserCourseSection> queryAll(UserCourseSection queryEntity);

	/**
	 * 获取最新的学习记录
	 */
	UserCourseSection queryLatest(UserCourseSection queryEntity);

	/**
	 * 获取总数量
	 **/
	Integer getTotalItemsCount(UserCourseSection queryEntity);

	/**
	 * 分页获取
	 **/
	List<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page);

	/**
	 * 创建新记录
	 **/
	void createSelectivity(UserCourseSection entity);

	/**
	 * 根据id更新
	 **/
	void update(UserCourseSection entity);

	/**
	 * 根据id选择性更新自动
	 **/
	void updateSelectivity(UserCourseSection entity);

	/**
	 * 物理删除
	 **/
	void delete(UserCourseSection entity);

	/**
	 * 逻辑删除
	 **/
	void deleteLogic(UserCourseSection entity);
}
