package com.online.college.core.course.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.course.domain.CourseComment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程评论&答疑Dao
 *
 * @author yx
 * @createtime 2019/04/21 22:05
 */
@Repository
public interface CourseCommentDao {

    /**
     * 根据id获取
     **/
    CourseComment getById(Long id);

    /**
     * 获取所有
     **/
    List<CourseComment> queryAll(CourseComment queryEntity);

    /**
     * 获取总数量
     **/
    Integer getTotalItemsCount(CourseComment queryEntity);

    /**
     * 分页获取
     **/
    List<CourseComment> queryPage(CourseComment queryEntity, TailPage<CourseComment> page);


    /**
     * 获取总数量
     **/
    Integer getMyQAItemsCount(CourseComment queryEntity);

    /**
     * 分页获取
     **/
    List<CourseComment> queryMyQAItemsPage(CourseComment queryEntity, TailPage<CourseComment> page);

    /**
     * 创建新记录
     **/
    void create(CourseComment entity);

    /**
     * 创建新记录
     */
    void createSelectivity(CourseComment entity);

    /**
     * 根据id更新
     **/
    void update(CourseComment entity);

    /**
     * 根据id选择性更新自动
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

