package com.coco.utils;

/**
 * @author wanglin/netboy
 * @version 创建时间：2016年08月15日 下午8:30:46
 */
public class CocoStringUtils 
{
    /**
     * 
     * TODO: 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

}
