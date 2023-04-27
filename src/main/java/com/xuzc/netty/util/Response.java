package com.xuzc.netty.util;

public class Response {
	private Long id;
	private Object result;
	private String code="00000";//00000为成功
	private String msg;//失败原因
	public void setId(Long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
