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
		
		//加载关注用户的动态
		UserFollowStudyRecord queryEntity = new UserFollowStudyRecord();
		queryEntity.setUserId(SessionContext.getUserId());
		page.setPageSize(2);
		page = userFollowsService.queryUserFollowStudyRecordPage(queryEntity, page);
		
		//处理用户头像
		for(UserFollowStudyRecord item : page.getItems()){
			if(StringUtils.isNotEmpty(item.getHeader())){
				item.setHeader(QiniuStorage.getUrl(item.getHeader()));
			}
		}
		System.out.println("UserController类中index方法中，page是否为空？" + (page == null));
		System.out.println("UserController类中index方法中，page.items是否为空？" + (page.getItems() == null));
		mv.addObject("page", page);
		
		return mv;
	}
	
}
