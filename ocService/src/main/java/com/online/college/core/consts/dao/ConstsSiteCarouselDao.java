package com.online.college.core.consts.dao;

import java.util.List;

import com.online.college.common.page.TailPage;
import com.online.college.core.consts.domain.ConstsSiteCarousel;
import org.springframework.stereotype.Repository;

/**
 * 轮播图Dao层类
 *
 * @author yx
 * @createtime 2019/04/20 21:21
 */
@Repository
public interface ConstsSiteCarouselDao {

    /**
     * 根据id获取
     **/
    ConstsSiteCarousel getById(Long id);

    /**
     * 获取轮播
     */
    List<ConstsSiteCarousel> queryCarousels(Integer count);

    /**
     * 获取所有
     **/
    List<ConstsSiteCarousel> queryAll(ConstsSiteCarousel queryEntity);

    /**
     * 获取总数量
     **/
    Integer getTotalItemsCount(ConstsSiteCarousel queryEntity);

    /**
     * 分页获取
     **/
    List<ConstsSiteCarousel> queryPage(ConstsSiteCarousel queryEntity, TailPage<ConstsSiteCarousel> page);

    /**
     * 创建新记录
     **/
    void create(ConstsSiteCarousel entity);

    /**
     * 创建新记录
     */
    void createSelectivity(ConstsSiteCarousel entity);

    /**
     * 根据id更新
     **/
    void update(ConstsSiteCarousel entity);

    /**
     * 根据id选择性更新自动
     **/
    void updateSelectivity(ConstsSiteCarousel entity);

    /**
     * 物理删除
     **/
    void delete(ConstsSiteCarousel entity);

    /**
     * 逻辑删除
     **/
    void deleteLogic(ConstsSiteCarousel entity);
}

