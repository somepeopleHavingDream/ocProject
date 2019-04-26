package com.online.college.portal.business.impl;

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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页业务层
 * @author Admin!
 */
@Service
public class PortalServiceImpl implements IPortalService{

	private final IConstsClassifyService constsClassifyService;
	
	private final ICourseSectionService courseSectionService;
	
	private final ICourseService courseService;

	@Autowired
	public PortalServiceImpl(IConstsClassifyService constsClassifyService, ICourseSectionService courseSectionService, ICourseService courseService) {
		this.constsClassifyService = constsClassifyService;
		this.courseSectionService = courseSectionService;
		this.courseService = courseService;
	}

	/**
	 * 获取所有，包括一级分类&二级分类
	 */
	@Override
	public List<ConstsClassifyVO> queryAllClassify() {
		return new ArrayList<>(this.queryAllClassifyMap().values());
	}

	/**
	 * 获取所有分类
	 */
	@Override
	public Map<String, ConstsClassifyVO> queryAllClassifyMap() {
		Map<String, ConstsClassifyVO> resultMap = new LinkedHashMap<>();
		for (ConstsClassify c : constsClassifyService.queryAll()) {
			if ("0".equals(c.getParentCode())) {    // 一级目录
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
		CourseSection queryEntity = new CourseSection();
		queryEntity.setCourseId(courseId);
		queryEntity.setOnsale(CourseEnum.ONSALE.value());	// 上架
		
		Map<Long, CourseSectionVO> tmpMap = new LinkedHashMap<>();
		for (CourseSection item : courseSectionService.queryAll(queryEntity)) {
			if (Long.valueOf(0).equals(item.getParentId())) {    // 章
				CourseSectionVO vo = new CourseSectionVO();
				try {
					BeanUtils.copyProperties(item, vo);
					tmpMap.put(vo.getId(), vo);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				tmpMap.get(item.getParentId()).getSections().add(item);    // 小节添加到大章中
			}
		}
		return new ArrayList<>(tmpMap.values());
	}

	/**
	 * 为分类设置课程推荐
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
