package com.coco.utils.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author wanglin/netboy
* @version 创建时间：2016年9月21日 下午3:23:43
* @func 
*/
public abstract class Protos {
    private static final Logger LOGGER = LoggerFactory.getLogger(Protos.class);
    // 系统级错误
    public static final Proto OK = new Proto(0, "ok");
    public static final Proto SYSTEM_ERROR = new Proto(101, "system error");


    // 参数非法
    public static Proto createInvalidParam(String param) {
        String info = String.format("无效参数:%s", param);
        LOGGER.error(info);
        return new Proto(101, info);
    }

    public static Proto InvalidRequest(String msg) {
        String info = String.format("非法请求:%s", msg);
        LOGGER.error(info);
        return new Proto(102, info);
    }

    public static Proto createOk(Object data) {
        return new Proto(0, null, data);
    }
}
