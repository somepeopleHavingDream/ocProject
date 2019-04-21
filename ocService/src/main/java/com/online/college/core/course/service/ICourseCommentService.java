package com.online.college.core.course.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseComment;

import java.util.List;

/**
 * 课程评论&答疑服务层接口
 *
 * @author yx
 * @createtime 2019/04/21 21:50
 */
public interface ICourseCommentService {

    /**
     * 根据id获取
     **/
    CourseComment getById(Long id);

    /**
     * 获取所有
     **/
    List<CourseComment> queryAll(CourseComment queryEntity);

    /**
     * 分页获取
     **/
    TailPage<CourseComment> queryPage(CourseComment queryEntity, TailPage<CourseComment> page);

    /**
     * 分页获取我的所有课程的qa
     */
    TailPage<CourseComment> queryMyQAItemsPage(CourseComment queryEntity, TailPage<CourseComment> page);

    /**
     * 创建
     **/
    void create(CourseComment entity);

    /**
     * 创建
     */
    void createSelectivity(CourseComment entity);

    /**
     * 根据id更新
     **/
    void update(CourseComment entity);

    /**
     * 根据id 进行可选性更新
     **/
    void updateSelectivity(CourseComment entity);

    /**
     * 物理删除
     **/
    void delete(CourseComment entity);

    /**
     * 逻辑删除
     **/
    void deleteLogic(CourseComment entity);
}

