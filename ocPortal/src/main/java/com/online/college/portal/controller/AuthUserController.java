package com.online.college.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;

@Controller
@RequestMapping("/user")
public class AuthUserController {

	@Autowired
	private IAuthUserService entityService;
	
	@RequestMapping(value = "/getById")
	@ResponseBody
	public String getById(Long id) {
		AuthUser user = entityService.getById(id);
		return JsonView.render(user);
	}
	
	@RequestMapping(value = "/teacherPageList")
	public ModelAndView queryPage(AuthUser queryEntity, TailPage<AuthUser> page) {
		ModelAndView mv = new ModelAndView("cms/user/teacherPageList");
		mv.addObject("curNav", "teacher");
		
		page = entityService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		mv.addObject("queryEntity", queryEntity);
		
		return mv;
	}
	
	@RequestMapping(value = "/studentPageList")
	public ModelAndView studentPageList(AuthUser queryEntity, TailPage<AuthUser> page) {
		ModelAndView mv = new ModelAndView("cms/user/studentPageList");
		mv.addObject("curNav", "student");
		
		page = entityService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		mv.addObject("queryEntity", queryEntity);
		
		return mv;
	}
	
	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AuthUser entity) {
		ModelAndView mv = new ModelAndView("auth/authUserMerge");
		if (entity.getId() != null) {
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity", entity);
		return mv;
	}
	
	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(AuthUser entity) {
		if (entity.getId() == null) {
			entityService.create(entity);
		} else {
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/auth/authUser/queryPage.html");
	}
	
	@RequestMapping(value = "/delete")
	public ModelAndView delete(AuthUser entity) {
		entityService.delete(entity);
		return new ModelAndView("rediect:/auth/authUser/queryPage.html");
	}
	
	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(AuthUser entity) {
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/auth/authUser/queryPage.html");
	}
}
