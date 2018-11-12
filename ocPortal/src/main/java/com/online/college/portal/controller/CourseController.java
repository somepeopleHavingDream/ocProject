package com.online.college.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.portal.business.IPortalService;
import com.online.college.portal.vo.ConstsClassifyVO;
import com.online.college.portal.vo.CourseSectionVO;

/**
 * 课程管理
 */
@Controller
@RequestMapping("/course")
public class CourseController {
	
	@Resource
	private ICourseService courseService;
	
	@Autowired
	private IPortalService portalService;
	
	/**
	 * 课程管理
	 */
	@RequestMapping("/pagelist")
	public ModelAndView list(Course queryEntity, TailPage<Course> page) {
		ModelAndView mv = new ModelAndView("cms/course/pagelist");
		
		page.setPageSize(5);
		page = courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		
		mv.addObject("curNav", "course");
		return mv;
	}
	
	/**
	 * 课程编辑
	 */
	@RequestMapping("/read")
	public ModelAndView courseRead(Long id) {
		Course course = courseService.getById(id);
		if (null == course) {
			return new ModelAndView("error/404");
		}
		
		ModelAndView mv = new ModelAndView("cms/course/read");
		mv.addObject("curNav", "course");
		mv.addObject("course", course);
		
		List<CourseSectionVO> chaptSections = this.portalService.queryCourseSection(1L);
		mv.addObject("chaptSections", chaptSections);
		
		Map<String, ConstsClassifyVO> classifyMap = portalService.queryAllClassifyMap();
		
		// 所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for (ConstsClassifyVO vo : classifyMap.values()) {
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
		
		List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
		for (ConstsClassifyVO vo : classifyMap.values()) {
			subClassifys.addAll(vo.getSubClassifyList());
		}
		mv.addObject("subClassifys", subClassifys);	// 所有二级分类
		
		return mv;
	}
	
	/**
	 * 课程上下架
	 */
	@RequestMapping("/doSale")
	@ResponseBody
	public String doSale(Course entity) {
		courseService.updateSelectivity(entity);	// 更新课程总时长
		
		return new JsonView().toString();
	}
	
	/**
	 * 课程上下架
	 * @param entity
	 * @return
	 */
	@RequestMapping("/doDelete")
	@ResponseBody
	public String doDelete(Course entity) {
		courseService.delete(entity);
		
		return new JsonView().toString();
	}
	
	/**
	 * 根据id获取
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public String getById(Long id) {
		return JsonView.render(courseService.getById(id));
	}
	
	/**
	 * 添加课程
	 */
	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("cms/course/add");
		mv.addObject("curNav", "course");
		return mv;
	}
}
