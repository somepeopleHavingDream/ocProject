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

import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalBusiness;
import com.online.college.portal.vo.ConstsClassifyVO;

/**
 * 首页业务层
 */
@Service
public class PortalBusinessImpl implements IPortalBusiness {

	@Autowired
	private IConstsClassifyService constsClassifyService;

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
			if ("0".equals(c.getParentCode())) { // 一级分类
				ConstsClassifyVO vo = new ConstsClassifyVO();
				try {
//					BeanUtils.copyProperties(c, vo);	// 这个地方不太明白，这个地方源码写错了！
					BeanUtils.copyProperties(vo, c);
					resultMap.put(vo.getCode(), vo);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else { // 二级分类
				if (null != resultMap.get(c.getParentCode())) {
					resultMap.get(c.getParentCode()).getSubClassifyList().add(c); // 添加到子分类中
				}
			}
		}
		return resultMap;
	}

	/**
	 * 为分类设置课程推荐
	 */
	@Override
	public void prepareRecomdCourse(List<ConstsClassifyVO> classifyVoList) {
		if (CollectionUtils.isNotEmpty(classifyVoList)) {
			for (ConstsClassifyVO item : classifyVoList) {
				CourseQueryDto queryEntity = new CourseQueryDto();
				queryEntity.setCount(5);
				queryEntity.descSortField("weight");
				queryEntity.setClassify(item.getCode());// 分类code

				List<Course> tmpList = this.courseService.queryList(queryEntity);
				if (CollectionUtils.isNotEmpty(tmpList)) {
					item.setRecomdCourseList(tmpList);
				}
			}
		}
	}
}
