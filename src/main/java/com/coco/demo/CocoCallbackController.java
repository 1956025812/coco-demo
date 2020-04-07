package com.coco.demo;

import com.alibaba.fastjson.JSONObject;
import com.coco.demo.callback.CocoCallbackService;
import com.coco.demo.cocoenum.CocoEventEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/coco")
@Slf4j
public class CocoCallbackController {

    @Autowired
    private CocoCallbackService cocoCallbackService;

    @RequestMapping(value = "callback", method = {RequestMethod.GET, RequestMethod.POST})
    public String testCoco(@RequestParam(required = false) Map<String, String> params) {
        log.info("测试回调,参数为：{}", JSONObject.toJSONString(params));

        String result = null;
        String event = params.get("Event");
        if (StringUtils.isEmpty(event)) {
            return null;
        }

        CocoEventEnum cocoEventEnum = CocoEventEnum.getCocoEventEnum(event);
        if (null == cocoEventEnum) {
            log.info("当前事件event：{} 并未做监听处理", event);
            return null;
        }

        switch (cocoEventEnum) {
            case JOIN_CHATROOM:
                break;

            case PUSH_LOGIN_URL:
                break;

            case RECEIVE_SYS_MSG:
                break;

            case RECEIVE_VERIFY_MSG:
                break;

            case RECEIVE_TRANSFER_MSG:
                break;

            case RECEIVE_QR_PAY_MSG:
                break;

            case RECEIVE_HB_MSG:
                break;

            case RECEIVE_MESSAGE:
                result = this.cocoCallbackService.handleReceiveMessage(params);
                break;

            default:
                log.info("当前事件event: {} 未作处理", event);
        }


        return result;
    }


}
