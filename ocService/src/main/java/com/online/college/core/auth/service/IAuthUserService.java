package com.online.college.core.auth.service;

import com.online.college.common.page.TailPage;
import com.online.college.core.auth.domain.AuthUser;

import java.util.List;

/**
 * 用户服务层接口
 *
 * @author yx
 * @createtime 2019/04/20 18:18
 */
public interface IAuthUserService {

    /**
     * 根据username获取
     **/
    AuthUser getByUsername(String username);

    /**
     * 创建
     **/
    void createSelectivity(AuthUser entity);

    /**
     * 根据id获取
     **/
    AuthUser getById(Long id);

    /**
     * 根据username和password获取
     **/
    AuthUser getByUsernameAndPassword(AuthUser authUser);

    /**
     * 获取首页推荐5个讲师
     **/
    List<AuthUser> queryRecomd();

    /**
     * 分页获取
     **/
    TailPage<AuthUser> queryPage(AuthUser queryEntity, TailPage<AuthUser> page);

    /**
     * 根据id更新
     **/
    void update(AuthUser entity);

    /**
     * 根据id 进行可选性更新
     **/
    void updateSelectivity(AuthUser entity);

    /**
     * 物理删除
     **/
    void delete(AuthUser entity);

    /**
     * 逻辑删除
     **/
    void deleteLogic(AuthUser entity);

}
