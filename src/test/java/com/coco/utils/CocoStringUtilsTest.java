package com.coco.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
* @author wanglin/netboy
* @version 创建时间：2016年7月28日 下午1:20:41
* @func 
*/
public class CocoStringUtilsTest extends TestCase {

    public void testIsNumber() {
        Assert.assertTrue(!CocoStringUtils.isNumber(""));
        Assert.assertTrue(CocoStringUtils.isNumber("11"));
        Assert.assertTrue(!CocoStringUtils.isNumber(".11"));
        Assert.assertTrue(!CocoStringUtils.isNumber("11.22.11"));
    }

}
