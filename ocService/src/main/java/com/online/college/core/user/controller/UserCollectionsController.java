package com.online.college.core.user.controller;

import java.util.List;
import com.online.college.common.page.TailPage;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.online.college.core.user.domain.UserCollections;
import com.online.college.core.user.service.IUserCollectionsService;


@Controller
@RequestMapping("/user/userCollections")
public class UserCollectionsController{

	@Autowired
	private IUserCollectionsService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(Long id){
		ModelAndView mv = new ModelAndView("user/userCollections");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/queryAll")
	public  ModelAndView queryAll(UserCollections queryEntity){
		ModelAndView mv = new ModelAndView("user/userCollectionsList");
		List<UserCollections> list = entityService.queryAll(queryEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/queryPage")
	public  ModelAndView queryPage(UserCollections queryEntity , TailPage<UserCollections> page){
		ModelAndView mv = new ModelAndView("user/userCollectionsPage");
		page = entityService.queryPage(queryEntity,page);
		mv.addObject("page",page);
		mv.addObject("queryEntity",queryEntity);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(UserCollections entity){
		ModelAndView mv = new ModelAndView("user/userCollectionsMerge");
		if(entity.getId() != null){
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity",entity);
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(UserCollections entity){
		if(entity.getId() == null){
			entityService.create(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/user/userCollections/queryPage.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(UserCollections entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/user/userCollections/queryPage.html");
	}

	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(UserCollections entity){
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/user/userCollections/queryPage.html");
	}



}

