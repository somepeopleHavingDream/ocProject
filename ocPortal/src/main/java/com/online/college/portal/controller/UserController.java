package com.online.college.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.util.EncryptUtil;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import com.online.college.core.user.service.IUserCourseSectionService;

/**
 * 用户中心
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IAuthUserService authUserService;
	
	@Autowired
	private IUserCourseSectionService userCourseSectionService;
	
	/**
	 * 主页
	 */
	@RequestMapping("/home")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("user/home");
		mv.addObject("curNav", "home");
		return mv;
	}
	
	/**
	 * 我的课程
	 */
	@RequestMapping("/course")
	public ModelAndView course(TailPage<UserCourseSectionDto> page) {
		ModelAndView mv = new ModelAndView("user/course");
		mv.addObject("curNav", "course");
		
		UserCourseSection queryEntity = new UserCourseSection();
		queryEntity.setUserId(SessionContext.getUserId());
		page = userCourseSectionService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		
		return mv;
	}
	
	/**
	 * 我的收藏
	 */
	@RequestMapping("/collect")
	public ModelAndView collect() {
		ModelAndView mv = new ModelAndView("user/collect");
		mv.addObject("curNav", "collect");
		return mv;
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info")
	public ModelAndView info() {
		ModelAndView mv = new ModelAndView("user/info");
		mv.addObject("curNav", "info");
		
		AuthUser authUser = authUserService.getById(SessionContext.getUserId());
		mv.addObject("authUser", authUser);
		return mv;
	}
	
	/**
	 * 保存信息
	 */
	@RequestMapping("/saveInfo")
	@ResponseBody
	public String saveInfo(AuthUser authUser) {
		authUser.setId(SessionContext.getUserId());
		authUserService.updateSelectivity(authUser);
		return new JsonView().toString();
	}
	
	/**
	 * 密码
	 */
	@RequestMapping("/passwd")
	public ModelAndView passwd() {
		ModelAndView mv = new ModelAndView("user/passwd");
		mv.addObject("curNav", "passwd");
		return mv;
	}
	
	/**
	 * 密码
	 */
	@RequestMapping("/savePasswd")
	@ResponseBody
	public String savePasswd(String oldPassword, String password, String rePassword) {
		AuthUser currentUser = authUserService.getById(SessionContext.getUserId());
		if (null == currentUser) {
			return JsonView.render(1, "用户不存在");
		}
		oldPassword = EncryptUtil.encodedByMD5(oldPassword.trim());
		if (!oldPassword.equals(currentUser.getPassword())) {
			return JsonView.render(1, "旧密码不正确！");
		}
		if (StringUtils.isEmpty(password.trim())) {
			return JsonView.render(1, "新密码不能为空！");
		}
		if (!password.trim().equals(rePassword.trim())) {
			return JsonView.render(1, "新密码与重复密码不一致！");
		}
		currentUser.setPassword(EncryptUtil.encodedByMD5(password));
		authUserService.updateSelectivity(currentUser);
		return new JsonView().toString();
	}
}
