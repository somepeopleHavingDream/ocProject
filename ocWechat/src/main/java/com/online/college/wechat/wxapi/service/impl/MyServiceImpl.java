package com.online.college.wechat.wxapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.online.college.wechat.wxapi.process.MpAccount;
import com.online.college.wechat.wxapi.process.MsgResponseNews;
import com.online.college.wechat.wxapi.process.MsgType;
import com.online.college.wechat.wxapi.process.MsgXmlUtil;
import com.online.college.wechat.wxapi.service.MyService;
import com.online.college.wechat.wxapi.vo.Article;
import com.online.college.wechat.wxapi.vo.MsgNews;
import com.online.college.wechat.wxapi.vo.MsgRequest;

/**
 * 业务消息处理
 * 开发者根据自己的业务自行处理消息的接收与回复
 */
@Service
public class MyServiceImpl implements MyService {

	/**
	 * 处理消息
	 * 开发者可以根据用户发送的消息和自己的业务，自行返回合适的消息
	 * @param msgRequest：接收到的消息
	 * @param appId：appId
	 * @param appSecret：appSecret
	 */
	@Override
	public String processMsg(MsgRequest msgRequest, MpAccount mpAccout) {
		List<MsgNews> msgNews = new ArrayList<MsgNews>();
		return MsgXmlUtil.newsToXml(getMsgResponseNews(msgRequest, msgNews));
	}

	/**
	 * 构造一条图文消息
	 * @param msgRequest
	 * @param msgNews
	 * @return
	 */
	private MsgResponseNews getMsgResponseNews(MsgRequest msgRequest, List<MsgNews> msgNews) {
		if (msgNews != null && msgNews.size() > 0) {
			MsgResponseNews responseNews = new MsgResponseNews();
			responseNews.setToUserName(msgRequest.getFromUserName());
			responseNews.setFromUserName(msgRequest.getToUserName());
			responseNews.setMsgType(MsgType.News.toString());
			responseNews.setCreateTime(new Date().getTime());
			responseNews.setArticleCount(msgNews.size());
			List<Article> articles = new ArrayList<Article>(msgNews.size());
			for (MsgNews n : msgNews) {
				Article a = new Article();
				a.setTitle(n.getTitle());
				a.setPicUrl(n.getPicpath());
				if (StringUtils.isEmpty(n.getFromurl())) {
					a.setUrl(n.getUrl());
				} else {
					a.setUrl(n.getFromurl());
				}
				a.setDescription(n.getBrief());
				articles.add(a);
			}
			responseNews.setArticles(articles);
			return responseNews;
		} else {
			return null;
		}
	}
}
