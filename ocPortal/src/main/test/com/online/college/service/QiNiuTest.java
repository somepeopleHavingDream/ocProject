package com.online.college.service;

import com.online.college.common.storage.QiniuStorage;
import com.online.college.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * 七牛云服务器图片测试
 *
 * @author yx
 * @createtime 2019/04/20 20:44
 */
@Slf4j
public class QiNiuTest {
    /**
     * 测试本类方法
     */
    public static void main(String[] args) {
        new QiNiuTest().testBatchImages();
    }

    /**
     * 测试批量文件上传
     */
    private void testBatchImages() {
        // 批量文件图片上传
        File rootDir = new File("/media/yx/新加卷1/上传到七牛云的用户头像");
        if (rootDir.isDirectory()) {
            File[] files = rootDir.listFiles();
            if (null != files) {
                for (File file : files) {
                    testImages(file);
                }
            }
        }
    }

    /**
     * 带参数的图片文件上传
     */
    private void testImages(File file) {
        // 测试上传代码，单个文件图片上传
        byte[] buff = CommonUtil.getFileBytes(file);
        String key = QiniuStorage.uploadImage(buff);
        log.info("testImages -> key: [{}]", key);
    }

    /**
     * 无参的图片文件上传
     */
    public void testImages() {
        // 测试上传代码，单个文件图片上传
        byte[] buff = CommonUtil.getFileBytes(new File("/media/yx/新加卷1/上传到七牛云的图片/丁当和小老虎在上网.jpg"));
        String key = QiniuStorage.uploadImage(buff);
        System.out.println("key = " + key);

        /*
         * String key = "/default/all/0/db919738859f402e855b826a78c51bc4.png"; // 测试下载图片
         * String url = QiniuStorage.getUrl(key); System.out.println("url = " + url);
         * 
         * // 测试下载不同大小的图片 url = QiniuStorage.getUrl(key, ThumbModel.THUMB_256);
         * System.out.println("url = " + url);
         */
    }
}
