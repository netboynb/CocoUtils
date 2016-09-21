package com.coco.utils.web;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.coco.utils.NoNullFieldStringStyle;

/**
* @author wanglin/netboy
* @version 创建时间：2016年9月7日 下午1:42:59
* @func 
*/
public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}
