package com.online.college.opt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.opt.business.IPortalBusiness;
import com.online.college.opt.vo.ConstsClassifyVO;

/**
 * 课程分类管理
 *
 * @author yx
 * @createtime 2019/04/23 20:44
 */
@Controller
@RequestMapping("/classify")
public class ClassifyController {
    private final IConstsClassifyService entityService;
    private final IPortalBusiness portalBusiness;

    @Autowired
    public ClassifyController(IConstsClassifyService entityService, IPortalBusiness portalBusiness) {
        this.entityService = entityService;
        this.portalBusiness = portalBusiness;
    }


    @RequestMapping(value = "/getById")
    @ResponseBody
    public String getById(Long id) {
        return JsonView.render(entityService.getById(id));
    }

    @RequestMapping(value = "/index")
    public ModelAndView classifyIndex(ConstsClassify queryEntity, TailPage<ConstsClassify> page) {
        ModelAndView mv = new ModelAndView("cms/classify/classifyIndex");
        mv.addObject("curNav", "classify");
        Map<String, ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();

        //所有一级分类
        List<ConstsClassifyVO> classifysList = new ArrayList<>(classifyMap.values());
        mv.addObject("classifys", classifysList);

        //二级分类
        List<ConstsClassify> subClassifys = new ArrayList<>();
        for (ConstsClassifyVO vo : classifyMap.values()) {
            subClassifys.addAll(vo.getSubClassifyList());
        }
        mv.addObject("subClassifys", subClassifys);//所有二级分类

        return mv;
    }

    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(ConstsClassify entity) {
        if (entity.getId() == null) {
            ConstsClassify tmpEntity = entityService.getByCode(entity.getCode());
            if (tmpEntity != null) {
                return JsonView.render(1, "此编码已存在");
            }
            entityService.createSelectivity(entity);
        } else {
            entityService.updateSelectivity(entity);
        }
        return new JsonView().toString();
    }

    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(ConstsClassify entity) {
        entityService.deleteLogic(entity);
        return new JsonView().toString();
    }


}

