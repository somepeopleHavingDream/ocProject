package com.online.college.core.course.domain;

import com.online.college.common.orm.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程评论&QA
 *
 * @author yx
 * @createtime 2019/04/21 21:57
 */
@Getter
@Setter
public class CourseComment extends BaseEntity{

    private static final long serialVersionUID = 789165716801545108L;
    
    /**
    *用户username
    **/
    private String username;

    /**
    *评论对象username
    **/
    private String toUsername;

    /**
    *课程id
    **/
    private Long courseId;

    /**
    *章节id
    **/
    private Long sectionId;

    /**
    *章节标题
    **/
    private String sectionTitle;

    /**
    *评论内容
    **/
    private String content;

    /**
    *引用id
    **/
    private Long refId;

    /**
    *引用内容
    **/
    private String refContent;

    /**
    *类型：0-评论；1-答疑QA
    **/
    private Integer type;
    
    
    /**
     * 用户头像
     */
    private String header;
    
    /**
     * 课程名称
     */
    private String courseName;
}

