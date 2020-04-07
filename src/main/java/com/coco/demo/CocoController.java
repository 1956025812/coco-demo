package com.coco.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/coco")
@Slf4j
public class CocoController {

    @GetMapping("/sendTextMsg")
    public void sendTextMsg(HttpServletResponse response, String toUser, String content) throws IOException {
        log.info("发送文本信息，参数为：[toUser: {}, content= {}]", toUser, content);

        StringBuilder textMsgUrlBuilder = new StringBuilder();
        textMsgUrlBuilder.append("http://localhost:8080/?a=")
                .append("<%26%26>SendMessage")
                .append("<%26>").append(toUser)
                .append("<%26>").append(URLEncoder.encode(content, "UTF-8"))
                .append("<%26>")
                .append("<%26>").append("1");
        String textMsgUrl = textMsgUrlBuilder.toString();
        log.info("拼接callback回调数据给机器人的URL串为：{}", textMsgUrl);

        response.sendRedirect(textMsgUrl);
    }


}
