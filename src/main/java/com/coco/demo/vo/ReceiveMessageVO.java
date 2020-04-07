package com.coco.demo.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 接受消息类型的VO对象
 */
public class ReceiveMessageVO extends CocoVO implements Serializable {

    private static final long serialVersionUID = -6840844910925797447L;

    private String event;
    private String atList;
    private String content;
    private Date createTime;
    private String exInfo;
    private String fromId;



}
