package com.online.college.core.user.controller;

import java.util.List;
import com.online.college.common.page.TailPage;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.online.college.core.user.domain.UserMessage;
import com.online.college.core.user.service.IUserMessageService;


@Controller
@RequestMapping("/user/userMessage")
public class UserMessageController{

	@Autowired
	private IUserMessageService entityService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(Long id){
		ModelAndView mv = new ModelAndView("user/userMessage");
		mv.addObject("entity",entityService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/queryAll")
	public  ModelAndView queryAll(UserMessage queryEntity){
		ModelAndView mv = new ModelAndView("user/userMessageList");
		List<UserMessage> list = entityService.queryAll(queryEntity);
		mv.addObject("list",list);
		return mv;
	}

	@RequestMapping(value = "/queryPage")
	public  ModelAndView queryPage(UserMessage queryEntity , TailPage<UserMessage> page){
		ModelAndView mv = new ModelAndView("user/userMessagePage");
		page = entityService.queryPage(queryEntity,page);
		mv.addObject("page",page);
		mv.addObject("queryEntity",queryEntity);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(UserMessage entity){
		ModelAndView mv = new ModelAndView("user/userMessageMerge");
		if(entity.getId() != null){
			entity = entityService.getById(entity.getId());
		}
		mv.addObject("entity",entity);
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(UserMessage entity){
		if(entity.getId() == null){
			entityService.create(entity);
		}else{
			entityService.update(entity);
		}
		return new ModelAndView("redirect:/user/userMessage/queryPage.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(UserMessage entity){
		entityService.delete(entity);
		return new ModelAndView("redirect:/user/userMessage/queryPage.html");
	}

	@RequestMapping(value = "/deleteLogic")
	public ModelAndView deleteLogic(UserMessage entity){
		entityService.deleteLogic(entity);
		return new ModelAndView("redirect:/user/userMessage/queryPage.html");
	}



}

