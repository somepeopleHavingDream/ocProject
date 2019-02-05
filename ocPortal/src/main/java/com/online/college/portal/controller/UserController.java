package com.online.college.portal.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.util.EncryptUtil;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.user.domain.UserCollections;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.service.IUserCollectionsService;
import com.online.college.core.user.service.IUserCourseSectionService;
import com.online.college.core.user.service.IUserFollowsService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserFollowsService userFollowsService;
	
	@Autowired
	private IAuthUserService authUserService;
	
	@Autowired
	private IUserCourseSectionService userCourseSectionService;
	
	@Autowired
	private IUserCollectionsService userCollectionsService;
	
	/**
	 * 首页
	 */
	@RequestMapping("/home")
	public ModelAndView index(TailPage<UserFollowStudyRecord> page){
		ModelAndView mv = new ModelAndView("user/home");
		mv.addObject("curNav","home");
		
		//加载关注用户的动态
		UserFollowStudyRecord queryEntity = new UserFollowStudyRecord();
		queryEntity.setUserId(SessionContext.getUserId());
		page = userFollowsService.queryUserFollowStudyRecordPage(queryEntity, page);
		
		//处理用户头像
		for(UserFollowStudyRecord item : page.getItems()){
			if(StringUtils.isNotEmpty(item.getHeader())){
				item.setHeader(QiniuStorage.getUrl(item.getHeader()));
			}
		}
		mv.addObject("page", page);
		
		return mv;
	}
	
	/**
	 * 我的课程
	 */
	@RequestMapping("/course")
	public ModelAndView course(TailPage<UserCourseSectionDto> page){
		ModelAndView mv = new ModelAndView("user/course");
		mv.addObject("curNav","course");
		
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
	public ModelAndView collect(TailPage<UserCollections> page){
		ModelAndView mv = new ModelAndView("user/collect");
		mv.addObject("curNav","collect");
		UserCollections queryEntity = new UserCollections();
		queryEntity.setUserId(SessionContext.getUserId());
		page = userCollectionsService.queryPage(queryEntity, page);
		
		mv.addObject("page", page);
		return mv;
	}
	
}
