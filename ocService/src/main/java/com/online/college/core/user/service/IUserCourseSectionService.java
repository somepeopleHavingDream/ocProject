package com.online.college.core.user.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;

import java.util.List;

/**
 * 用户课程章节服务层类
 * 
 * @author yx
 * @createtime 2019/04/21 12:41
 */
public interface IUserCourseSectionService {

	/**
	 * 根据id获取
	 **/
	UserCourseSection getById(Long id);

	/**
	 * 获取所有
	 **/
	List<UserCourseSection> queryAll(UserCourseSection queryEntity);

	/**
	 * 获取最新的
	 */
	UserCourseSection queryLatest(UserCourseSection queryEntity);

	/**
	 * 分页获取
	 **/
	TailPage<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page);

	/**
	 * 创建
	 **/
	void createSelectivity(UserCourseSection entity);

	/**
	 * 根据id更新
	 **/
	void update(UserCourseSection entity);

	/**
	 * 根据id 进行可选性更新
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