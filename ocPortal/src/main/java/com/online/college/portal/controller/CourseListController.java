package com.online.college.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.consts.service.IConstsClassifyService;
import com.online.college.core.course.domain.Course;
import com.online.college.portal.business.IPortalBusiness;
import com.online.college.portal.vo.ConstsClassifyVO;

/**
 * 课程分类页
 */
@Controller
@RequestMapping("/course")
public class CourseListController {

	@Autowired
	private IConstsClassifyService constsClassifyService;
	
	@Autowired
	private IPortalBusiness portalBusiness;
	
	/**
	 * 课程分类页
	 * @param c 分类code
	 * @param sort 排序
	 * @param page 分页
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(String c, String sort, TailPage<Course> page) {
		ModelAndView mv = new ModelAndView("list");
		String curCode = "-1";	// 当前方向code
		String curSubCode = "-2";	// 当前分类code
		
		// 加载所有课程分类
		Map<String, ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		// 所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for (ConstsClassifyVO vo : classifyMap.values()) {
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
		
		// 当前分类
		ConstsClassify curClassify = constsClassifyService.getByCode(c);
		
		if (null == curClassify) {	// 没有此分类，加载所有二级分类
			List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
			for (ConstsClassifyVO vo : classifyMap.values()) {
				subClassifys.addAll(vo.getSubClassifyList());
			}
			mv.addObject("subClassifys", subClassifys);
		} else {
			if (!"0".endsWith(curClassify.getParentCode())) {
				// 当前是二级分类
				curSubCode = curClassify.getCode();
				curCode = curClassify.getParentCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getParentCode()).getSubClassifyList());	// 此分类平级的二级分类
			} else {	// 当前是一级分类
				curCode = curClassify.getCode();
				mv.addObject("subClassifys", classifyMap.get(curClassify.getCode()).getSubClassifyList());	// 此分类下的二级分类
			}
		}
		mv.addObject("curCode", curCode);
		mv.addObject("curSubCode", curSubCode);
		
		return mv;
	}
}
