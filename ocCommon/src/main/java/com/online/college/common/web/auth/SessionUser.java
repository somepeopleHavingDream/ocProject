package com.online.college.common.web.auth;

import java.util.Set;

/**
 * 权限用户
 * 
 * @author yx
 */
public interface SessionUser {
    String getUsername();

    Long getUserId();

    Set<String> getPermissions();
}