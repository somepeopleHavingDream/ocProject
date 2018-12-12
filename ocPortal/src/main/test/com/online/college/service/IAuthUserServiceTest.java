package com.online.college.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.online.college.common.storage.QiniuStorage;
import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;

import junit.framework.TestCase;

/**
 * IAuthUserService的测试类
 */
public class IAuthUserServiceTest extends TestCase {

	Logger log = Logger.getLogger(IAuthUserServiceTest.class);
	
	@Autowired
	private IAuthUserService authUserService;

	public void testGetUserByUsername() {
		// 获取讲师
		// course: {id=22, username="wangyangming"}
		System.out.println("这是authUserService.getByUsername之前的一条语句。");
		AuthUser courseTeacher = authUserService.getByUsername("wangyangming");
		System.out.println("这是authUserService.getByUsername之后的一条语句。");
		
		System.out.println("courseTeacher is null: " + (courseTeacher == null));
		if (StringUtils.isNotEmpty(courseTeacher.getHeader())) {
			String url = QiniuStorage.getUrl(courseTeacher.getHeader());
			System.out.println(url);
			courseTeacher.setHeader(QiniuStorage.getUrl(courseTeacher.getHeader()));
		}
	}
}
