package com.online.college.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;

@Controller
@RequestMapping("/courseSection")
public class CourseSectionController {

	@Autowired
	private ICourseSectionService entityService;
	
	@RequestMapping(value = "/getById")
	@ResponseBody
	public String getById(Long id) {
		return JsonView.render(entityService.getById(id));
	}
	
	@RequestMapping(value = "/queryAll")
	public ModelAndView queryAll(CourseSection queryEntity) {
		ModelAndView mv = new ModelAndView("course/courseSectionList");
		List<CourseSection> list = entityService.queryAll(queryEntity);
		mv.addObject("list", list);
		return mv;
	}
	
	@RequestMapping(value = "/queryPage")
	public ModelAndView queryPage(CourseSection queryEntity, TailPage<CourseSection> page) {
		ModelAndView mv = new ModelAndView("course/courseSectionPage");
		page = entityService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		mv.addObject("queryEntity", queryEntity);
		return mv;
	}
	
	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(CourseSection entity) {
		ModelAndView mv = new ModelAndView("course/courseSectionMerge");
		if (entity.getId() != null) {
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity", entity);
		return mv;
	}
	
	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(CourseSection entity) {
		if (entity.getId() == null) {
			entityService.create(entity);
		} else {
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/course/courseSection/queryPage.html");
	}
	
	@RequestMapping(value = "/delete")
	public ModelAndView delete(CourseSection entity) {
		entityService.delete(entity);
		return new ModelAndView("redirect:/course/courseSection/queryPage.html");
	}
	
	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(CourseSection entity) {
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/course/courseSection/queryPage.html");
	}
}
