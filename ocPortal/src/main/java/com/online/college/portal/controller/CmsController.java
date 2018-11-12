package com.online.college.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 后台管理
 */
@Controller
@RequestMapping("/cms")
public class CmsController {

	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("cms/index");
		mv.addObject("curNav", "home");
		return mv;
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("/password")
	public ModelAndView password() {
		ModelAndView mv = new ModelAndView("cms/password");
		mv.addObject("curNav", "password");
		return mv;
	}
}
