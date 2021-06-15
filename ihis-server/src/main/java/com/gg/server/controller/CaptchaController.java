package com.gg.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 验证码
 * @author: GG
 * @date: 2021/2/27 12:28 下午
 */
@RestController
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha")
    public String captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义 response 输出类型为 image/jpeg 类型
        String method = request.getMethod();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.addHeader("Access-Control-Expose-Headers","captcha");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成验证码
        String text = defaultKaptcha.createText();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        BASE64Encoder encoder = new BASE64Encoder(); // 获取验证码文本内容
        response.setHeader("captcha", encoder.encode(bytes));
        // 生成验证码图片
        BufferedImage image = defaultKaptcha.createImage(text); // 根据文本内容创建图形验证码
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", outputStream);
            outputStream.flush();
            String base64 = encoder.encode(outputStream.toByteArray());
            return "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
