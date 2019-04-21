package com.online.college.portal.controller;

import com.online.college.common.page.TailPage;
import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.util.EncryptUtil;
import com.online.college.common.web.JsonView;
import com.online.college.common.web.SessionContext;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.course.domain.CourseComment;
import com.online.college.core.course.service.ICourseCommentService;
import com.online.college.core.user.domain.UserCollections;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.service.IUserCollectionsService;
import com.online.college.core.user.service.IUserCourseSectionService;
import com.online.college.core.user.service.IUserFollowsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Objects;

/**
 * 用户控制类
 *
 * @author yx
 * @createtime 2019/04/20 15:30
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final IUserFollowsService userFollowsService;
    private final IAuthUserService authUserService;
    private final IUserCourseSectionService userCourseSectionService;
    private final IUserCollectionsService userCollectionsService;
    private final ICourseCommentService courseCommentService;

    @Autowired
    public UserController(IUserFollowsService userFollowsService, IAuthUserService authUserService,
                          IUserCourseSectionService userCourseSectionService,
                          IUserCollectionsService userCollectionsService,
                          ICourseCommentService courseCommentService) {
        this.userFollowsService = userFollowsService;
        this.authUserService = authUserService;
        this.userCourseSectionService = userCourseSectionService;
        this.userCollectionsService = userCollectionsService;
        this.courseCommentService = courseCommentService;
    }

    /**
     * 首页
     */
    @RequestMapping("/home")
    public ModelAndView index(TailPage<UserFollowStudyRecord> page) {
        ModelAndView mv = new ModelAndView("user/home");
        mv.addObject("curNav", "home");

        //加载关注用户的动态
        UserFollowStudyRecord queryEntity = new UserFollowStudyRecord();
        queryEntity.setUserId(SessionContext.getUserId());
        page = userFollowsService.queryUserFollowStudyRecordPage(queryEntity, page);

        //处理用户头像
        for (UserFollowStudyRecord item : page.getItems()) {
            if (StringUtils.isNotEmpty(item.getHeader())) {
                item.setHeader(QiniuStorage.getUrl(item.getHeader()));
            }
        }
        mv.addObject("page", page);

        return mv;
    }

    /**
     * 我的课程
     */
    @RequestMapping("/course")
    public ModelAndView course(TailPage<UserCourseSectionDto> page) {
        ModelAndView mv = new ModelAndView("user/course");
        mv.addObject("curNav", "course");

        UserCourseSection queryEntity = new UserCourseSection();
        queryEntity.setUserId(SessionContext.getUserId());
        page = userCourseSectionService.queryPage(queryEntity, page);
        mv.addObject("page", page);

        return mv;
    }

    /**
     * 我的收藏
     */
    @RequestMapping("/collect")
    public ModelAndView collect(TailPage<UserCollections> page) {
        ModelAndView mv = new ModelAndView("user/collect");
        mv.addObject("curNav", "collect");
        UserCollections queryEntity = new UserCollections();
        queryEntity.setUserId(SessionContext.getUserId());
        page = userCollectionsService.queryPage(queryEntity, page);

        mv.addObject("page", page);
        return mv;
    }

    /**
     * 信息
     */
    @RequestMapping("/info")
    public ModelAndView info() {
        ModelAndView mv = new ModelAndView("user/info");
        mv.addObject("curNav", "info");

        AuthUser authUser = authUserService.getById(SessionContext.getUserId());
        if (null != authUser && StringUtils.isNotEmpty(authUser.getHeader())) {
            authUser.setHeader(QiniuStorage.getUrl(authUser.getHeader()));
        }
        mv.addObject("authUser", authUser);

        return mv;
    }

    /**
     * 保存信息
     */
    @RequestMapping("/saveInfo")
    @ResponseBody
    public String saveInfo(AuthUser authUser, @RequestParam MultipartFile pictureImg) {
        try {
            authUser.setId(SessionContext.getUserId());
            if (null != pictureImg && pictureImg.getBytes().length > 0) {
                String key = QiniuStorage.uploadImage(pictureImg.getBytes());
                authUser.setHeader(key);
            }
            authUserService.updateSelectivity(authUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new JsonView().toString();
    }

    /**
     * 密码
     */
    @RequestMapping("/passwd")
    public ModelAndView passwd() {
        ModelAndView mv = new ModelAndView("user/passwd");
        mv.addObject("curNav", "passwd");

        return mv;
    }

    /**
     * 保存密码
     * @param oldPassword 旧密码
     * @param password 第一次输入的新密码
     * @param rePassword 第二次输入的新密码
     */
    @RequestMapping("/savePasswd")
    @ResponseBody
    public String savePasswd(String oldPassword, String password, String rePassword) {
        // 获取当前用户对象
        AuthUser currentUser = authUserService.getById(SessionContext.getUserId());
        if (null == currentUser) {
            return JsonView.render(1, "用户不存在！");
        }

        // 密码判断
        oldPassword = EncryptUtil.encodedByMD5(oldPassword.trim());
        if (!Objects.equals(oldPassword, currentUser.getPassword())) {
            return JsonView.render(1, "旧密码不正确！");
        }
        if (StringUtils.isEmpty(password.trim())) {
            return JsonView.render(1, "新密码不能为空！");
        }
        if (!password.trim().equals(rePassword.trim())) {
            return JsonView.render(1, "新密码与重复密码不一致！");
        }

        // 将新密码更新后存入数据库
        currentUser.setPassword(EncryptUtil.encodedByMD5(password));
        authUserService.updateSelectivity(currentUser);

        return new JsonView().toString();
    }

    /**
     * 答疑
     */
    @RequestMapping("/qa")
    public ModelAndView qa(TailPage<CourseComment> page) {
        ModelAndView mv = new ModelAndView("user/qa");
        mv.addObject("curNav", "qa");

        CourseComment queryEntity = new CourseComment();
        queryEntity.setUsername(SessionContext.getUsername());
        page = courseCommentService.queryMyQAItemsPage(queryEntity, page);
        mv.addObject("page", page);

        return mv;
    }
}
