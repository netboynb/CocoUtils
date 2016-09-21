package com.coco.utils.web;


public final class Proto extends BaseDO {

	public int code; // 应答码
	public String msg; // 描述信息
	public Object data;// 返回的数据内容

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Proto() {
	}

	public Proto(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Proto(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
