package com.online.college.core.consts.service;

import java.util.List;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.domain.ConstsCollege;

/**
 * 网校服务层类
 *
 * @author yx
 * @createtime 2019/04/23 15:48
 */
public interface IConstsCollegeService {

    /**
     * 根据id获取
     **/
    ConstsCollege getById(Long id);

    /**
     * 根据code获取
     */
    ConstsCollege getByCode(String code);

    /**
     * 获取所有
     **/
    List<ConstsCollege> queryAll(ConstsCollege queryEntity);

    /**
     * 分页获取
     **/
    TailPage<ConstsCollege> queryPage(ConstsCollege queryEntity, TailPage<ConstsCollege> page);

    /**
     * 创建
     **/
    void create(ConstsCollege entity);

    /**
     * 创建网校
     */
    void createSelectivity(ConstsCollege entity);

    /**
     * 根据id更新
     **/
    void update(ConstsCollege entity);

    /**
     * 根据id 进行可选性更新
     **/
    void updateSelectivity(ConstsCollege entity);

    /**
     * 物理删除
     **/
    void delete(ConstsCollege entity);

    /**
     * 逻辑删除
     **/
    void deleteLogic(ConstsCollege entity);


}

