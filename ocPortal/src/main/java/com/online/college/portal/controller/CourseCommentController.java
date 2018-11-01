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
@RequestMapping("/courseCommet")
public class CourseCommentController {

	@Autowired
	private ICourseCommentService courseCommentService;
	
	/**
	 * 课程管理
	 */
	@RequestMapping("/pagelist")
	public ModelAndView commentSegment(CourseComment queryEntity, TailPage<CourseComment> page) {
		ModelAndView mv = new ModelAndView("cms/course/readComment");
		queryEntity.setCourseId(1L);
		TailPage<CourseComment> commentPage = this.courseCommentService.queryPage(queryEntity, page);
		mv.addObject("page", commentPage);
		mv.addObject("queryEntity", queryEntity);
		return mv;
	}
}
