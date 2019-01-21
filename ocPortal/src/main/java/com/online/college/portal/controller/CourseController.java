package com.online.college.portal.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.domain.CourseSection;
import com.online.college.core.course.service.ICourseSectionService;
import com.online.college.core.course.service.ICourseService;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.service.IUserCourseSectionService;
import com.online.college.portal.business.ICourseBusiness;
import com.online.college.portal.vo.CourseSectionVO;

/**
 * 课程详情页
 */
@Controller
@RequestMapping("/course")
public class CourseController {
	
	@Autowired
	private ICourseBusiness courseBusiness;
	
	@Autowired
	private ICourseService courseService;
	
	@Autowired
	private IAuthUserService authUserService;
	
	@Autowired
	private ICourseSectionService courseSectionService;
	
	@Autowired
	private IUserCourseSectionService userCourseSectionService;
	
	/**
	 * 课程学习页
	 * @param courseId
	 * @return
	 */
	@RequestMapping("/learn/{courseId}")
	public ModelAndView learn(@PathVariable Long courseId) {
		if (null == courseId) {
			return new ModelAndView("error/404");
		}
		
		// 获取课程
		Course course = courseService.getById(courseId);
		if (null == course) {
			return new ModelAndView("error/404");
		}
		
		// 获取课程章节
		ModelAndView mv = new ModelAndView("learn");
		List<CourseSectionVO> chaptSections = this.courseBusiness.queryCourseSection(courseId);
		mv.addObject("course", course);	// 向learn.html页面传递参数1：课程对象course
		mv.addObject("chaptSections", chaptSections);	// 向learn.html页面传递参数2：章节集合对象chaptSections
		
		// 获取讲师
		AuthUser courseTeacher = this.authUserService.getByUsername(course.getUsername());
		System.out.println("这是authUserService.getByUsername方法后的一句话。");
		if (StringUtils.isNotEmpty(courseTeacher.getHeader())) {
			courseTeacher.setHeader(QiniuStorage.getUrl(courseTeacher.getHeader()));
			System.out.println("courseTeacher.getHeader(): " + courseTeacher.getHeader());
		}
		mv.addObject("courseTeacher", courseTeacher);	// 向learn.html页面传递参数3：讲师对象courseTeacher
		
		// 获取推荐课程
		CourseQueryDto queryEntity = new CourseQueryDto();
		queryEntity.descSortField("weight");
		queryEntity.setCount(5);// 5门推荐课程
		queryEntity.setSubClassify(course.getSubClassify());
		List<Course> recomdCourseList = this.courseService.queryList(queryEntity);
		mv.addObject("recomdCourseList", recomdCourseList);	// 向learn.html页面传递参数4：推荐课程recomdCourseList集合对象
		
		// 当前学习的章节
		UserCourseSection userCourseSection = new UserCourseSection();
		userCourseSection.setCourseId(course.getId());
		userCourseSection.setUserId(SessionContext.getUserId());
		userCourseSection = this.userCourseSectionService.queryLatest(userCourseSection);
		if (null != userCourseSection) {
			CourseSection curCourseSection = this.courseSectionService.getById(userCourseSection.getSectionId());
			mv.addObject("curCourseSection", curCourseSection);
		}
		
		return mv;
	}
	
	/**
	 * 视频学习页面
	 * @param sectionId
	 * @return
	 */
	@RequestMapping("/video/{sectionId}")
	public ModelAndView video(@PathVariable Long sectionId) {
		if (null == sectionId) {
			return new ModelAndView("error/404");
		}
		
		CourseSection courseSection = courseSectionService.getById(sectionId);
		if (null == courseSection) {
			return new ModelAndView("error/404");
		}
		
		// 课程章节
		ModelAndView mv = new ModelAndView("video");
		// courseBusiness.queryCourseSection方法其实是将t_course_section中所有记录做了一个数据归类。
		List<CourseSectionVO> chaptSections = this.courseBusiness.queryCourseSection(courseSection.getCourseId());
		mv.addObject("courseSection", courseSection);
		mv.addObject("chaptSections", chaptSections);
		
		// 学习记录
		UserCourseSection userCourseSection = new UserCourseSection();
		userCourseSection.setUserId(SessionContext.getUserId());
		userCourseSection.setCourseId(courseSection.getCourseId());
		userCourseSection.setSectionId(courseSection.getId());
		UserCourseSection result = userCourseSectionService.queryLatest(userCourseSection);
		
		if (null == result) {	// 如果没有，插入
			userCourseSection.setCreateTime(new Date());
			userCourseSection.setCreateUser(SessionContext.getUsername());
			userCourseSection.setUpdateTime(new Date());
			userCourseSection.setUpdateUser(SessionContext.getUsername());
			
			userCourseSectionService.createSelectivity(userCourseSection);
		} else {
			result.setUpdateTime(new Date());
			userCourseSectionService.update(result);
		}
		
		return mv;
	}
}