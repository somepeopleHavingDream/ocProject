package com.online.college.core.user.domain;

import com.online.college.common.orm.BaseEntity;
import lombok.Getter;
import lombok.Setter;


/**
 * 用户课程章节实体类
 *
 * @author yx
 * @createtime 2019/04/20 17:47
 */
@Setter
@Getter
public class UserCourseSection extends BaseEntity{

    private static final long serialVersionUID = 5447461555053008202L;

    /**
    *用户id
    **/
    private Long userId;
    
    /**
    *课程id
    **/
    private Long courseId;
    
    /**
    *章节id
    **/
    private Long sectionId;
    
    /**
    *状态：0-学习中；1-学习结束
    **/
    private Integer status;
    
    /**
     * 进度
     */
    private Integer rate;
}

