package com.online.college.portal.business;

import java.util.List;
import java.util.Map;

import com.online.college.portal.vo.ConstsClassifyVO;
import com.online.college.portal.vo.CourseSectionVO;

public interface IPortalService {
	
	/**
	 * 获取所有，包括一级分类&二级分类
	 */
	public List<ConstsClassifyVO> queryAllClassify();
	
	/**
	 * 获取所有分类
	 */
	public Map<String, ConstsClassifyVO> queryAllClassifyMap();
	
	/**
	 * 获取课程章节
	 * @param courseId
	 * @return
	 */
	public List<CourseSectionVO> queryCourseSection(Long courseId);
	
	/**
	 * 为分类设置课程推荐
	 * @param classifyVoList
	 */
	public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList);
}
