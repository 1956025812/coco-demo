package com.coco.demo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * Coco的基础VO对象
 */
@Data
public class CocoVO implements Serializable {

    private static final long serialVersionUID = 5534809466186638426L;

    private String robot;
    private String key;


}
