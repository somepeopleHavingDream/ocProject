package com.online.college.core.user.controller;

import java.util.List;
import com.online.college.common.page.TailPage;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.online.college.core.user.domain.UserFollows;
import com.online.college.core.user.service.IUserFollowsService;


@Controller
@RequestMapping("/user/userFollows")
public class UserFollowsController{

	@Autowired
	private IUserFollowsService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(Long id){
		ModelAndView mv = new ModelAndView("user/userFollows");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/queryAll")
	public  ModelAndView queryAll(UserFollows queryEntity){
		ModelAndView mv = new ModelAndView("user/userFollowsList");
		List<UserFollows> list = entityService.queryAll(queryEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/queryPage")
	public  ModelAndView queryPage(UserFollows queryEntity , TailPage<UserFollows> page){
		ModelAndView mv = new ModelAndView("user/userFollowsPage");
		page = entityService.queryPage(queryEntity,page);
		mv.addObject("page",page);
		mv.addObject("queryEntity",queryEntity);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(UserFollows entity){
		ModelAndView mv = new ModelAndView("user/userFollowsMerge");
		if(entity.getId() != null){
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity",entity);
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(UserFollows entity){
		if(entity.getId() == null){
			entityService.create(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/user/userFollows/queryPage.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(UserFollows entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/user/userFollows/queryPage.html");
	}

	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(UserFollows entity){
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/user/userFollows/queryPage.html");
	}



}

