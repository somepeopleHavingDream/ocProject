package com.online.college.portal.business;

import com.online.college.portal.vo.CourseSectionVO;

import java.util.List;

/**
 * @author yx
 * @createtime 2019/04/20 18:10
 */
public interface ICourseBusiness {

	/**
	 * 获取课程章节
	 */
	List<CourseSectionVO> queryCourseSection(Long courseId);
}