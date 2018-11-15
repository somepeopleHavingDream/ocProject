package com.online.college.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.web.JsonView;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;

/**
 * 用户登录&注册
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private IAuthUserService authUserService;

	/**
	 * 注册页面
	 */
	@RequestMapping(value = "/register")
	public ModelAndView register() {
		return new ModelAndView("auth/register");
	}

	/**
	 * 实现注册
	 */
	@RequestMapping(value = "/doRegister")
	@ResponseBody
	public String doRegister(AuthUser authUser) {
		AuthUser tmpUser = authUserService.getByUsername(authUser.getUsername());
		if (tmpUser != null) {
			return JsonView.render(1);
		} else {
			authUserService.createSelectivity(authUser);
			return JsonView.render(0);
		}
	}
}
