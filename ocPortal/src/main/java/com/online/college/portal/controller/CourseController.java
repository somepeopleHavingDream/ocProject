package com.online.college.portal.controller;

import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.web.JsonView;
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
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * 课程详情信息
 *
 * @author yx
 * @createtime 2019/04/20 17:57
 */
@Controller
@RequestMapping("/course")
@Slf4j
public class CourseController {
    private final ICourseBusiness courseBusiness;
    private final ICourseService courseService;
    private final IAuthUserService authUserService;
    private final ICourseSectionService courseSectionService;
    private final IUserCourseSectionService userCourseSectionService;

    @Autowired
    public CourseController(ICourseBusiness courseBusiness, ICourseService courseService, IAuthUserService authUserService, ICourseSectionService courseSectionService, IUserCourseSectionService userCourseSectionService) {
        this.courseBusiness = courseBusiness;
        this.courseService = courseService;
        this.authUserService = authUserService;
        this.courseSectionService = courseSectionService;
        this.userCourseSectionService = userCourseSectionService;
    }


    /**
     * 课程章节页面
     */
    @RequestMapping("/learn/{courseId}")
    public ModelAndView learn(@PathVariable Long courseId) {
        log.info("learn -> courseId: [{}]", courseId);

        // 判空处理
        if (null == courseId)
            return new ModelAndView("error/404");

        // 获取课程
        Course course = courseService.getById(courseId);
        if (null == course)
            return new ModelAndView("error/404");

        // 获取所有课程章节
        ModelAndView mv = new ModelAndView("learn");
        List<CourseSectionVO> chaptSections = this.courseBusiness.queryCourseSection(courseId);
        mv.addObject("course", course);
        mv.addObject("chaptSections", chaptSections);

        //获取讲师
        AuthUser courseTeacher = this.authUserService.getByUsername(course.getUsername());
        if (StringUtils.isNotEmpty(courseTeacher.getHeader())) {
            courseTeacher.setHeader(QiniuStorage.getUrl(courseTeacher.getHeader()));
        }
        mv.addObject("courseTeacher", courseTeacher);

        //获取推荐课程
        CourseQueryDto queryEntity = new CourseQueryDto();
        queryEntity.descSortField("weight");
        queryEntity.setCount(5);//5门推荐课程
        queryEntity.setSubClassify(course.getSubClassify());
        List<Course> recomdCourseList = this.courseService.queryList(queryEntity);
        mv.addObject("recomdCourseList", recomdCourseList);

        //当前学习的章节
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
     */
    @RequestMapping("/video/{sectionId}")
    public ModelAndView video(@PathVariable Long sectionId) {
        if (null == sectionId)
            return new ModelAndView("error/404");

        // 课程章节
        CourseSection courseSection = courseSectionService.getById(sectionId);
        if (null == courseSection)
            return new ModelAndView("error/404");

        //课程章节
        ModelAndView mv = new ModelAndView("video");
        List<CourseSectionVO> chaptSections = this.courseBusiness.queryCourseSection(courseSection.getCourseId());
        mv.addObject("courseSection", courseSection);
        mv.addObject("chaptSections", chaptSections);

        //学习记录
        UserCourseSection userCourseSection = new UserCourseSection();
        userCourseSection.setUserId(SessionContext.getUserId());
        userCourseSection.setCourseId(courseSection.getCourseId());
        userCourseSection.setSectionId(courseSection.getId());
        UserCourseSection result = userCourseSectionService.queryLatest(userCourseSection);

        if (null == result) {//如果没有，插入
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

    @RequestMapping(value = "/getCurLeanInfo")
    @ResponseBody
    public String getCurLeanInfo() {
        JsonView jv = new JsonView();
        //加载当前用户学习最新课程
        if (SessionContext.isLogin()) {
            UserCourseSection userCourseSection = new UserCourseSection();
            userCourseSection.setUserId(SessionContext.getUserId());
            userCourseSection = this.userCourseSectionService.queryLatest(userCourseSection);
            if (null != userCourseSection) {
                JSONObject jsObj = new JSONObject();
                CourseSection curCourseSection = this.courseSectionService.getById(userCourseSection.getSectionId());
                jsObj.put("curCourseSection", curCourseSection);
                Course curCourse = courseService.getById(userCourseSection.getCourseId());
                jsObj.put("curCourse", curCourse);
                jv.setData(jsObj);
            }
        }
        System.out.println("这是jv.toString()之前的一句话。");
        return jv.toString();
    }

}
