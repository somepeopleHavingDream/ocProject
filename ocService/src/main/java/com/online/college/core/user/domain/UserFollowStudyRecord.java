package com.online.college.core.user.domain;

import java.util.Date;

/**
 * 关注的用户学习记录dto
 * 
 * @author yx
 */
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Long getFollowId() {
        return followId;
    }

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
