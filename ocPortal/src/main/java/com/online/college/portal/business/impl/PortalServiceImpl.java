package com.online.college.portal.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalService;
import com.online.college.portal.vo.ConstsClassifyVO;
import com.online.college.portal.vo.CourseSectionVO;

/**
 * 首页业务层
 * @author Admin!
 */
@Service
public class PortalServiceImpl implements IPortalService{

	@Autowired
	private IConstsClassifyService constsClassifyService;
	
	@Autowired
	private ICourseSectionService courseSectionService;
	
	@Autowired
	private ICourseService courseService;
	
	/**
	 * 获取所有，包括一级分类&二级分类
	 */
	@Override
	public List<ConstsClassifyVO> queryAllClassify() {
		List<ConstsClassifyVO> resultList = new ArrayList<ConstsClassifyVO>();
		for (ConstsClassifyVO vo : this.queryAllClassifyMap().values()) {
			resultList.add(vo);
		}
		return resultList;
	}

	/**
	 * 获取所有分类
	 */
	@Override
	public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
		Map<String, ConstsClassifyVO> resultMap = new LinkedHashMap<String, ConstsClassifyVO>();
		Iterator<ConstsClassify> it = constsClassifyService.queryAll().iterator();
		while (it.hasNext()) {
			ConstsClassify c = it.next();
			if ("0".equals(c.getParentCode())) {	// 一级目录
				ConstsClassifyVO vo = new ConstsClassifyVO();
				try {
					BeanUtils.copyProperties(c, vo);
					resultMap.put(vo.getCode(), vo);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				if (null != resultMap.get(c.getParentCode())) {
					resultMap.get(c.getParentCode()).getSubClassifyList().add(c);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 获取课程章节
	 */
	@Override
	public List<CourseSectionVO> queryCourseSection(Long courseId) {
		List<CourseSectionVO> resultList = new ArrayList<CourseSectionVO>();
		CourseSection queryEntity = new CourseSection();
		queryEntity.setCourseId(courseId);
		queryEntity.setOnsale(CourseEnum.ONSALE.value());	// 上架
		
		Map<Long, CourseSectionVO> tmpMap = new LinkedHashMap<Long, CourseSectionVO>();
		Iterator<CourseSection> it = courseSectionService.queryAll(queryEntity).iterator();
		while (it.hasNext()) {
			CourseSection item = it.next();
			if (Long.valueOf(0).equals(item.getParentId())) {	// 章
				CourseSectionVO vo = new CourseSectionVO();
				try {
					BeanUtils.copyProperties(item, vo);
					tmpMap.put(vo.getId(), vo);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				tmpMap.get(item.getParentId()).getSections().add(item);	// 小节添加到大章中
			}
		}
		for (CourseSectionVO vo : tmpMap.values()) {
			resultList.add(vo);
		}
		return resultList;
	}

	/**
	 * 为分类设置课程推荐
	 * @param classifyVoList
	 */
	@Override
	public void prepareRecomdCourses(List<ConstsClassifyVO> classifyVoList) {
		if (CollectionUtils.isNotEmpty(classifyVoList)) {
			for (ConstsClassifyVO item : classifyVoList) {
				CourseQueryDto queryEntity = new CourseQueryDto();
				queryEntity.setCount(5);
				queryEntity.ascSortField("weight");
				queryEntity.setClassify(item.getCode());// 分类code
				List<Course> tmpList = this.courseService.queryList(queryEntity);
				if (CollectionUtils.isNotEmpty(tmpList)) {
					item.setRecomdCourseList(tmpList);
				}
			}
		}
	}
	
}
