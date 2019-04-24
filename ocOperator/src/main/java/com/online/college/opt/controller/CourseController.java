package com.online.college.opt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.JsonView;
import com.online.college.core.consts.domain.ConstsClassify;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.service.ICourseService;
import com.online.college.opt.business.IPortalBusiness;
import com.online.college.opt.vo.ConstsClassifyVO;
import com.online.college.opt.vo.CourseSectionVO;

/**
 * 课程管理
 */
@Controller
@RequestMapping("/course")
public class CourseController {
	
	@Resource
	private ICourseService courseService;
	
	@Autowired
	private IPortalBusiness portalBusiness;
	
	/**
	 * 课程管理
	 */
	@RequestMapping("/pagelist")
	public ModelAndView list(Course queryEntity,TailPage<Course> page){
		ModelAndView mv = new ModelAndView("cms/course/pagelist");
		
		if(StringUtils.isNotEmpty(queryEntity.getName())){
			queryEntity.setName(queryEntity.getName().trim());
		}else{
			queryEntity.setName(null);
		}
		
		page.setPageSize(5);
		page = courseService.queryPage(queryEntity, page);
		mv.addObject("page", page);
		mv.addObject("queryEntity", queryEntity);
		mv.addObject("curNav", "course");
		return mv;
	}
	
	/**
	 * 课程上下架
	 */
	@RequestMapping("/doSale")
	@ResponseBody
	public String doSale(Course entity){
		courseService.updateSelectivity(entity);
		//更新课程总时长
		
		return new JsonView().toString();
	}
	
	/**
	 * 课程删除
	 */
	@RequestMapping("/doDelete")
	@ResponseBody
	public String doDelete(Course entity){
		courseService.delete(entity);
		return new JsonView().toString();
	}
	
	/**
	 * 根据id获取
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public String getById(Long id){
		return JsonView.render(courseService.getById(id));
	}
	
	/**
	 * 课程详情
	 */
	@RequestMapping("/read")
	public ModelAndView courseRead(Long id){
		Course course = courseService.getById(id);
		if(null == course)
			return new ModelAndView("error/404"); 
		
		ModelAndView mv = new ModelAndView("cms/course/read");
		mv.addObject("curNav", "course");
		mv.addObject("course", course);
		
		//课程章节
		List<CourseSectionVO> chaptSections = this.portalBusiness.queryCourseSection(id);
		mv.addObject("chaptSections", chaptSections);
		
		//课程分类
		Map<String,ConstsClassifyVO> classifyMap = portalBusiness.queryAllClassifyMap();
		//所有一级分类
		List<ConstsClassifyVO> classifysList = new ArrayList<ConstsClassifyVO>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			classifysList.add(vo);
		}
		mv.addObject("classifys", classifysList);
		
		List<ConstsClassify> subClassifys = new ArrayList<ConstsClassify>();
		for(ConstsClassifyVO vo : classifyMap.values()){
			subClassifys.addAll(vo.getSubClassifyList());
		}
		mv.addObject("subClassifys", subClassifys);//所有二级分类
		
		return mv;
	}
	
	/**
	 * 添加、修改课程
	 */
	@RequestMapping("/doMerge")
	@ResponseBody
	public String doMerge(Course entity,@RequestParam MultipartFile pictureImg){
		String key = null;
		try {
			if (null != pictureImg && pictureImg.getBytes().length > 0) {
				key = QiniuStorage.uploadImage(pictureImg.getBytes());//七牛上传图片
				entity.setPicture(key);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(null != entity.getId()){
			courseService.updateSelectivity(entity);
		}else{
			courseService.createSelectivity(entity);
		}
		return JsonView.render(entity).toString();
	}
	
}
