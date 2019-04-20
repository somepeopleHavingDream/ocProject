package com.online.college.core.user.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户学习课程dto
 *
 * @author yx
 * @createtime 2019/04/20 17:46
 */
@Getter
@Setter
public class UserCourseSectionDto extends UserCourseSection {
    
    private static final long serialVersionUID = 608405844566660424L;

    /**
     * 用户名
     */
    private String username;
    
    /**
     * 课程名
     */
    private String courseName;
    
    /**
     * 章节名
     */
    private String sectionName;
    
    /**
     * 用户头像
     */
    private String header;
}
