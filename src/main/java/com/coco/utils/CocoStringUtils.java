package com.coco.utils;

/**
 * Hello world!
 *
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
