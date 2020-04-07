package com.coco.demo.callback;

import java.util.Map;

public interface CocoCallbackService {

    /**
     *
     * @param params
     * @return
     */
    String handleReceiveMessage(Map<String, String> params);
}
