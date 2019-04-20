package com.online.college.core.user.domain;

import lombok.Data;

import java.util.Date;

/**
 * 关注的用户学习记录dto
 * @author yx
 * @createtime 2019/04/20 16:01
 */
@Data
public class UserFollowStudyRecord {
    private Long courseId; // 课程Id
    private Long sectionId; // 章节Id
    private Long userId; // 用户Id
    private String username; // 登录用户名
    private String header; // 用户头像
    private Long followId; // 关注用户Id
    private String courseName; // 课程名称
    private String sectionName; // 章节名称
    private Date createTime; // 创建时间
}
