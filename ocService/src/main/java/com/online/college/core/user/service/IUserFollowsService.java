package com.online.college.core.user.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.domain.UserFollows;

import java.util.List;

/**
 * 用户记录服务类
 * @author yx
 * @createtime 2019/04/20 16:07
 */
public interface IUserFollowsService {

    /**
     * 根据id获取
     **/
    UserFollows getById(Long id);

    /**
     * 获取所有
     **/
    List<UserFollows> queryAll(UserFollows queryEntity);

    /**
     * 分页获取
     **/
    TailPage<UserFollows> queryPage(UserFollows queryEntity, TailPage<UserFollows> page);

    /**
     * 分页获取
     **/
    TailPage<UserFollowStudyRecord> queryUserFollowStudyRecordPage(UserFollowStudyRecord queryEntity,
                                                                   TailPage<UserFollowStudyRecord> page);

    /**
     * 创建
     **/
    void createSelectivity(UserFollows entity);

    /**
     * 根据id更新
     **/
    void update(UserFollows entity);

    /**
     * 根据id 进行可选性更新
     **/
    void updateSelectivity(UserFollows entity);

    /**
     * 物理删除
     **/
    void delete(UserFollows entity);

    /**
     * 逻辑删除
     **/
    void deleteLogic(UserFollows entity);
}