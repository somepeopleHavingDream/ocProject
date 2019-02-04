package com.online.college.portal.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.SessionContext;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.service.IUserFollowsService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserFollowsService userFollowsService;
	
	/**
	 * 首页
	 */
	@RequestMapping("/home")
	public ModelAndView index(TailPage<UserFollowStudyRecord> page){
		ModelAndView mv = new ModelAndView("user/home");
		mv.addObject("curNav","home");
		
		return mv;
	}
	
}
