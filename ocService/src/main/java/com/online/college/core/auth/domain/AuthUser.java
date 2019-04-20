package com.online.college.core.auth.domain;

import com.online.college.common.orm.BaseEntity;
import com.online.college.common.web.auth.SessionUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

/**
 * 系统用户实体类
 *
 * @author yx
 * @createtime 2019/04/20 18:19
 */
@Getter
@Setter
public class AuthUser extends BaseEntity implements SessionUser {

    private static final long serialVersionUID = 94044276250229411L;

    /**
     * 登录用户名
     **/
    private String realname;

    /**
     * 真实姓名
     **/
    private String username;

    /**
     * 密码
     **/
    private String password;

    /**
     * 性别
     **/
    private Integer gender;

    /**
     * 头像
     **/
    private String header;

    /**
     * 手机号码
     **/
    private String mobile;

    /**
     * 状态：待审核（0），审核通过（1），默认（2），审核未通过（3），禁用（5）
     **/
    private Integer status;

    /**
     * 生日
     **/
    private Date birthday;

    /**
     * 学历：大专、本科、硕士、博士、博士后
     **/
    private String education;

    /**
     * 大学编号
     */
    private String collegeCode;

    /**
     * 大学名称
     */
    private String collegeName;

    /**
     * 资格证书编号
     **/
    private String certNo;

    /**
     * 头衔
     **/
    private String title;

    /**
     * 签名
     **/
    private String sign;

    /**
     * 微信公众号openid
     **/
    private String openId;

    /**
     * 微信号
     **/
    private String wechatId;

    /**
     * qq号
     **/
    private String qq;

    /**
     * 最后一次登录时间
     **/
    private Date loginTime;

    /**
     * 最后一次登录IP
     **/
    private String ip;

    /**
     * 所在省份
     **/
    private String province;

    /**
     * 所在城市
     **/
    private String city;

    /**
     * 所在地区
     **/
    private String district;

    /**
     * 推荐权重
     */
    private Integer weight;

    @Override
    public Long getUserId() {
        return this.getId();
    }

    @Override
    public Set<String> getPermissions() {
        return null;
    }

}

