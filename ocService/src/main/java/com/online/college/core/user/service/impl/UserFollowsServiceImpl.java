package com.online.college.core.user.service.impl;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.dao.UserFollowsDao;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.domain.UserFollows;
import com.online.college.core.user.service.IUserFollowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserFollowsServiceImpl implements IUserFollowsService {
    private final UserFollowsDao entityDao;

    @Autowired
    public UserFollowsServiceImpl(UserFollowsDao entityDao) {
        this.entityDao = entityDao;
    }

    public UserFollows getById(Long id) {
        return entityDao.getById(id);
    }

    public List<UserFollows> queryAll(UserFollows queryEntity) {
        return entityDao.queryAll(queryEntity);
    }

    public TailPage<UserFollows> queryPage(UserFollows queryEntity, TailPage<UserFollows> page) {
        Integer itemsTotalCount = entityDao.getTotalItemsCount(queryEntity);
        List<UserFollows> items = entityDao.queryPage(queryEntity, page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    /**
     * 分页获取
     */
    public TailPage<UserFollowStudyRecord> queryUserFollowStudyRecordPage(UserFollowStudyRecord queryEntity,
                                                                          TailPage<UserFollowStudyRecord> page) {
        Integer itemsTotalCount = entityDao.getFollowStudyRecordCount(queryEntity);
        List<UserFollowStudyRecord> items = entityDao.queryFollowStudyRecord(queryEntity, page);
        page.setItemsTotalCount(itemsTotalCount);
        page.setItems(items);
        return page;
    }

    public void createSelectivity(UserFollows entity) {
        entityDao.createSelectivity(entity);
    }

    public void update(UserFollows entity) {
        entityDao.update(entity);
    }

    public void updateSelectivity(UserFollows entity) {
        entityDao.updateSelectivity(entity);
    }

    public void delete(UserFollows entity) {
        entityDao.delete(entity);
    }

    public void deleteLogic(UserFollows entity) {
        entityDao.deleteLogic(entity);
    }


}

