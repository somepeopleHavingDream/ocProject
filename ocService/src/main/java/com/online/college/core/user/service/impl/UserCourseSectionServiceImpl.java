package com.online.college.core.user.service.impl;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.dao.UserCourseSectionDao;
import com.online.college.core.user.domain.UserCourseSection;
import com.online.college.core.user.domain.UserCourseSectionDto;
import com.online.college.core.user.service.IUserCourseSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户课程章节服务层类
 *
 * @author yx
 * @createtime 2019/04/21 12:42
 */
@Service
public class UserCourseSectionServiceImpl implements IUserCourseSectionService {
    private final UserCourseSectionDao entityDao;

    @Autowired
    public UserCourseSectionServiceImpl(UserCourseSectionDao entityDao) {
        this.entityDao = entityDao;
    }

    public UserCourseSection getById(Long id) {
        return entityDao.getById(id);
    }

    public List<UserCourseSection> queryAll(UserCourseSection queryEntity) {
        return entityDao.queryAll(queryEntity);
    }

    /**
     * 查询用户最近的课程章节学习记录
     */
    public UserCourseSection queryLatest(UserCourseSection queryEntity) {
        return entityDao.queryLatest(queryEntity);
    }

    public TailPage<UserCourseSectionDto> queryPage(UserCourseSection queryEntity, TailPage<UserCourseSectionDto> page) {
        Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
        List<UserCourseSectionDto> items = entityDao.queryPage(queryEntity, page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public void createSelectivity(UserCourseSection entity) {
        entityDao.createSelectivity(entity);
    }

    public void update(UserCourseSection entity) {
        entityDao.update(entity);
    }

    public void updateSelectivity(UserCourseSection entity) {
        entityDao.updateSelectivity(entity);
    }

    public void delete(UserCourseSection entity) {
        entityDao.delete(entity);
    }

    public void deleteLogic(UserCourseSection entity) {
        entityDao.deleteLogic(entity);
    }
}