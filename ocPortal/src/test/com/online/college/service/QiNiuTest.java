package com.online.college.service;

import java.io.File;

import org.apache.log4j.Logger;

import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.util.CommonUtil;

import junit.framework.TestCase;

public class QiNiuTest extends TestCase {

	Logger log = Logger.getLogger(QiNiuTest.class);
	
	public void testImages() {
		// 测试上传代码
		byte[] buff = CommonUtil.getFileBytes(new File("E://大学//实训//最后项目//七牛云图片//丁当和小老虎在上网.jpg"));
		String key = QiniuStorage.uploadImage(buff);
		System.out.println("key = " + key);
		
		/*String key = "/default/all/0/db919738859f402e855b826a78c51bc4.png";
		// 测试下载图片
		String url = QiniuStorage.getUrl(key);
		System.out.println("url = " + url);
		
		// 测试下载不同大小的图片
		url = QiniuStorage.getUrl(key, ThumbModel.THUMB_256);
		System.out.println("url = " + url);*/
	}
}
