package com.online.college.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.core.consts.domain.ConstsSiteCarousel;
import com.online.college.core.consts.service.IConstsSiteCarouselService;
import com.online.college.portal.business.IPortalBusiness;
import com.online.college.portal.vo.ConstsClassifyVO;

/**
 * 网站主页
 */
@Controller
@RequestMapping()
public class PortalController {

	@Autowired
	private IPortalBusiness portalBusiness;
	
	@Autowired
	private IConstsSiteCarouselService siteCarouselService;
	
	/**
	 * 首页
	 */
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		
		// 加载轮播
		List<ConstsSiteCarousel> carouselList = siteCarouselService.queryCarousels(4);
		mv.addObject("carouselList", carouselList);
		
		// 课程分类（一级分类）
		List<ConstsClassifyVO> classifys = portalBusiness.queryAllClassify();
		
		// 课程推荐
		portalBusiness.prepareRecomdCourse(classifys);
		mv.addObject("classifys", classifys);
		
		return mv;
	}
}
