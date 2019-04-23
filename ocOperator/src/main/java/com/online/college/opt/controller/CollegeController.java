package com.online.college.opt.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.web.JsonView;
import com.online.college.core.consts.domain.ConstsCollege;
import com.online.college.core.consts.service.IConstsCollegeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 网校管理
 *
 * @author yx
 * @createtime 2019/04/23 15:16
 */
@Controller
@RequestMapping("/college")
public class CollegeController {
    private final IConstsCollegeService entityService;

    @Autowired
    public CollegeController(IConstsCollegeService entityService) {
        this.entityService = entityService;
    }

    /**
     * 查询页面
     */
    @RequestMapping(value = "/queryPageList")
    public ModelAndView queryPage(ConstsCollege queryEntity, TailPage<ConstsCollege> page) {
        ModelAndView mv = new ModelAndView("cms/college/collegePageList");
        mv.addObject("curNav", "college");

        if (StringUtils.isNotEmpty(queryEntity.getName())) {
            queryEntity.setName(queryEntity.getName().trim());
        } else {
            queryEntity.setName(null);
        }

        page = entityService.queryPage(queryEntity, page);
        mv.addObject("page", page);
        mv.addObject("queryEntity", queryEntity);
        return mv;
    }

    /**
     * 合并
     */
    @RequestMapping(value = "/doMerge")
    @ResponseBody
    public String doMerge(ConstsCollege entity) {
        if (entity.getId() == null) {
            ConstsCollege tmpEntity = entityService.getByCode(entity.getCode());
            if (tmpEntity != null) {
                return JsonView.render(1, "此编码已存在");
            }
            entityService.createSelectivity(entity);
        } else {
            ConstsCollege tmpEntity = entityService.getByCode(entity.getCode());
            // 这里的判断总觉得有问题
            if (tmpEntity != null && !tmpEntity.getId().equals(entity.getId())) {
                return JsonView.render(1, "此编码已存在");
            }
            entityService.updateSelectivity(entity);
        }
        return new JsonView().toString();
    }

    /**
     * 逻辑删除
     */
    @RequestMapping(value = "/deleteLogic")
    @ResponseBody
    public String deleteLogic(ConstsCollege entity) {
        entityService.deleteLogic(entity);
        return new JsonView().toString();
    }
}
