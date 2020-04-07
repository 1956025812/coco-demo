package com.coco.demo.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@Slf4j
public class CocoCallbackServiceImpl implements CocoCallbackService {

    @Override
    public String handleReceiveMessage(Map<String, String> params) {
        String fromId = params.get("FromId");

        StringBuilder result = new StringBuilder();
        if (null != fromId) {
            result.append("<&&>SendMessage")
                    .append("<&>").append(fromId)
                    .append("<&>").append("你也好啊")
                    .append("<&>")
                    .append("<&>").append("1");
            log.info("拼接callback回调数据给机器人的结果串为：{}", result);
        }

        return result.toString();
    }
}
