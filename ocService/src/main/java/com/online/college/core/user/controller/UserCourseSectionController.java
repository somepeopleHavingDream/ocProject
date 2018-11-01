package com.online.college.core.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import com.online.college.core.user.service.IUserCourseSectionService;


@Controller
@RequestMapping("/user/userCourseSection")
public class UserCourseSectionController{

	@Autowired
	private IUserCourseSectionService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(Long id){
		ModelAndView mv = new ModelAndView("user/userCourseSection");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/queryAll")
	public  ModelAndView queryAll(UserCourseSection queryEntity){
		ModelAndView mv = new ModelAndView("user/userCourseSectionList");
		List<UserCourseSection> list = entityService.queryAll(queryEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/queryPage")
	public  ModelAndView queryPage(UserCourseSection queryEntity , TailPage<UserCourseSectionDto> page){
		ModelAndView mv = new ModelAndView("user/userCourseSectionPage");
		page = entityService.queryPage(queryEntity,page);
		mv.addObject("page",page);
		mv.addObject("queryEntity",queryEntity);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(UserCourseSection entity){
		ModelAndView mv = new ModelAndView("user/userCourseSectionMerge");
		if(entity.getId() != null){
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity",entity);
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(UserCourseSection entity){
		if(entity.getId() == null){
			entityService.create(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/user/userCourseSection/queryPage.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(UserCourseSection entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/user/userCourseSection/queryPage.html");
	}

	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(UserCourseSection entity){
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/user/userCourseSection/queryPage.html");
	}



}

