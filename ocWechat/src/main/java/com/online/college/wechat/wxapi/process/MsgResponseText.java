package com.online.college.wechat.wxapi.process;

import com.online.college.wechat.wxapi.vo.MsgResponse;

/**
 * 公众号回复给用户的消息-文本消息
 */
public class MsgResponseText extends MsgResponse {

	private static final long serialVersionUID = 485549320025817942L;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
