package com.online.college.web;

import com.online.college.common.web.SessionContext;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码生成器
 *
 * @author yx
 * @createtime 2019/04/21 21:45
 */
@Controller
@RequestMapping("/tools/identiry")
public class IdentifyCodeController {
    @RequestMapping("/code")
    public void init(HttpServletRequest request, HttpServletResponse response) {
        // 设置session环境，将验证码作为session的键值对注册进去
        String random = RandomStringUtils.randomAlphanumeric(4);
        SessionContext.setAttribute(request, SessionContext.IDENTIFY_CODE_KEY, random);

        // 设置响应头信息
        response.setContentType("image/jpeg");
        response.addHeader("pragma", "NO-cache");
        response.addHeader("Cache-Control", "no-cache");
        response.addDateHeader("Expries", 0);

        int width = 110, height = 33;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        //以下填充背景色
        g.setColor(new Color(225, 225, 225));
        Font DeFont = new Font("SansSerif", Font.PLAIN, 26);
        g.setFont(DeFont);
        g.fillRect(0, 0, width, height);

        //设置字体色
        g.setColor(Color.BLACK);
        g.drawString(random, 20, 25);
        g.dispose();

        // 将信息写入流中
        try {
            ServletOutputStream outStream = response.getOutputStream();
            ImageIO.write(image, "JPG", outStream);
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
