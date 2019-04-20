package com.online.college.core.course.domain;

import com.online.college.common.orm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程章节
 *
 * @author yx
 * @createtime 2019/04/20 18:11
 */
@Getter
@Setter
public class CourseSection extends BaseEntity {

    private static final long serialVersionUID = -7261405404725335316L;

    /**
     * 归属课程id
     **/
    private Long courseId;

    /**
     * 父章节id，（只有2级）
     **/
    private Long parentId;

    /**
     * 章节名称
     **/
    private String name;

    /**
     * 排序
     **/
    private Integer sort;

    /**
     * 时长
     **/
    private String time;

    /**
     * 未上架（0）、上架（1）
     **/
    private Integer onsale;

    /**
     * 视频url
     */
    private String videoUrl;
}
