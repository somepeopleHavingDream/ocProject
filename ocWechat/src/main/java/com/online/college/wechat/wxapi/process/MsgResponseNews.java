package com.online.college.wechat.wxapi.process;

import java.util.List;

import com.online.college.wechat.wxapi.vo.Article;
import com.online.college.wechat.wxapi.vo.MsgResponse;

/**
 * 公众号回复给用户的消息-图文消息
 */
public class MsgResponseNews extends MsgResponse {

	private static final long serialVersionUID = -5997088176473964702L;

	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
