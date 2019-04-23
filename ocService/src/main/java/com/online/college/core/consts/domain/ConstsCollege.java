package com.online.college.core.consts.domain;

import com.online.college.common.orm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 网校实体类
 *
 * @author yx
 * @createtime 2019/04/23 15:29
 */
@Getter
@Setter
public class ConstsCollege extends BaseEntity {

    private static final long serialVersionUID = -7643904360103197835L;

    /**
     * 名称
     **/
    private String name;

    /**
     * 编码
     **/
    private String code;

    /**
     * 图片
     **/
    private String picture;
}

