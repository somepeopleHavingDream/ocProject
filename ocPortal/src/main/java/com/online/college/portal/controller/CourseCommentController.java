package com.online.college.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseComment;
import com.online.college.core.course.service.ICourseCommentService;

/**
 * 课程评论管理
 */
@Controller
@RequestMapping("/courseComment")
public class CourseCommentController {
	
	@Autowired
	private ICourseCommentService courseCommentService;
	
	/**
	 * @param queryEntity
	 * @param page
	 * springmvc接收learn.html中_queryPage中json格式的数据，并且将数据自动封装进对象中
	 * @return
	 */
	@RequestMapping("/segment")
	public ModelAndView segment(CourseComment queryEntity, TailPage<CourseComment> page) {
		if (null == queryEntity.getCourseId() || queryEntity.getType() == null) {
			return new ModelAndView("error/404");
		}
		
		ModelAndView mv = new ModelAndView("commentSegment");
		TailPage<CourseComment> commentPage = this.courseCommentService.queryPage(queryEntity, page);
		mv.addObject("page", commentPage);
		return mv;
	}
}