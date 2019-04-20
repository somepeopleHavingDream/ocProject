package com.online.college.core.user.dao;

import com.online.college.common.page.TailPage;
import com.online.college.core.user.domain.UserFollowStudyRecord;
import com.online.college.core.user.domain.UserFollows;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户记录Dao类
 * @author yx
 * @createtime 2019/04/20 16:11
 */
@Repository
public interface UserFollowsDao {

    /**
     * 根据id获取
     **/
    UserFollows getById(Long id);

    /**
     * 获取所有
     **/
    List<UserFollows> queryAll(UserFollows queryEntity);

    /**
     * 获取总数量
     **/
    Integer getTotalItemsCount(UserFollows queryEntity);

    /**
     * 分页获取
     **/
    List<UserFollows> queryPage(UserFollows queryEntity, TailPage<UserFollows> page);

    /**
     * 获取总数量
     **/
    Integer getFollowStudyRecordCount(UserFollowStudyRecord queryEntity);

    /**
     * 分页获取
     **/
    List<UserFollowStudyRecord> queryFollowStudyRecord(UserFollowStudyRecord queryEntity,
                                                       TailPage<UserFollowStudyRecord> page);

    /**
     * 创建新记录
     **/
    void createSelectivity(UserFollows entity);

    /**
     * 根据id更新
     **/
    void update(UserFollows entity);

    /**
     * 根据id选择性更新自动
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