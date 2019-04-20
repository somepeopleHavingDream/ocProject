package com.online.college.common.web.auth;

import java.util.Set;

/**
 * 权限用户
 * @author yx
 * @createtime 2019/04/20 16:05
 */
public interface SessionUser {
    String getUsername();
    Long getUserId();
    Set<String> getPermissions();
}