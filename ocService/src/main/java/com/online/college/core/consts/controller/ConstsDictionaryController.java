package com.online.college.core.consts.controller;

import java.util.List;
import com.online.college.common.page.TailPage;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.online.college.core.consts.domain.ConstsDictionary;
import com.online.college.core.consts.service.IConstsDictionaryService;


@Controller
@RequestMapping("/consts/constsDictionary")
public class ConstsDictionaryController{

    @Autowired
    private IConstsDictionaryService entityService;

    @RequestMapping(value = "/getById")
    public ModelAndView getById(Long id){
        ModelAndView mv = new ModelAndView("consts/constsDictionary");
        mv.addObject("entity",entityService.getById(id));
        return mv;
    }

    @RequestMapping(value = "/queryAll")
    public  ModelAndView queryAll(ConstsDictionary queryEntity){
        ModelAndView mv = new ModelAndView("consts/constsDictionaryList");
        List<ConstsDictionary> list = entityService.queryAll(queryEntity);
        mv.addObject("list",list);
        return mv;
    }

    @RequestMapping(value = "/queryPage")
    public  ModelAndView queryPage(ConstsDictionary queryEntity , TailPage<ConstsDictionary> page){
        ModelAndView mv = new ModelAndView("consts/constsDictionaryPage");
        page = entityService.queryPage(queryEntity,page);
        mv.addObject("page",page);
        mv.addObject("queryEntity",queryEntity);
        return mv;
    }

    @RequestMapping(value = "/toMerge")
    public ModelAndView toMerge(ConstsDictionary entity){
        ModelAndView mv = new ModelAndView("consts/constsDictionaryMerge");
        if(entity.getId() != null){
            entity = entityService.getById(entity.getId());
        }
        mv.addObject("entity",entity);
        return mv;
    }

    @RequestMapping(value = "/doMerge")
    public ModelAndView doMerge(ConstsDictionary entity){
        if(entity.getId() == null){
            entityService.create(entity);
        }else{
            entityService.update(entity);
        }
        return new ModelAndView("redirect:/consts/constsDictionary/queryPage.html");
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(ConstsDictionary entity){
        entityService.delete(entity);
        return new ModelAndView("redirect:/consts/constsDictionary/queryPage.html");
    }

    @RequestMapping(value = "/deleteLogic")
    public ModelAndView deleteLogic(ConstsDictionary entity){
        entityService.deleteLogic(entity);
        return new ModelAndView("redirect:/consts/constsDictionary/queryPage.html");
    }



}

