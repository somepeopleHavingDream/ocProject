package com.online.college.wechat.wxapi.process;

import java.io.UnsupportedEncodingException;

import com.online.college.wechat.wxapi.vo.AccountFans;

import net.sf.json.JSONObject;

/**
 * 微信 客户端，统一处理微信相关接口
 */
public class WxApiClient {
	
	/**
	 * 获取accessToken
	 * @param mpAccount
	 * @return
	 */
	public static String getAccessToken(MpAccount mpAccount) {
		// 获取唯一的accessToken，如果是多账号，请自行处理
		AccessToken token = WxMemoryCacheClient.getSingleAccessToken();
		if (token != null && !token.isExpires()) {	// 不为空，并且没有过期
			return token.getAccessToken();
		} else {
			token = WxApi.getAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret());
			if (token != null) {
				if (token.getErrcode() != null) {	// 获取失败
					System.out.println("## getAccessToken Error = " + token.getErrmsg());
				} else {
					WxMemoryCacheClient.addAccessToken(mpAccount.getAccount(), token);
					return token.getAccessToken();
				}
			}
			return null;
		}
	}
	
	/**
	 * 刷新accessToken
	 * @param mpAccount
	 * @return
	 */
	public static String refreshAccessToken(MpAccount mpAccount) {
		// 获取唯一的accessToken，如果是多账号，请自行处理
		AccessToken token = WxApi.getAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret());
		if (token != null) {
			if (token.getErrcode() != null) {	// 获取失败
				System.out.println("## getAccessToken Error = " + token.getErrmsg());
			} else {WxMemoryCacheClient.addAccessToken(mpAccount.getAccount(), token);
				return token.getAccessToken();
			}
		}
		return null;
	}
	
	/**
	 * 获取jsTicket
	 * @param mpAccount
	 * @return
	 */
	public static String getJSTicket(MpAccount mpAccount) {
		// 获取唯一的JSTicket，如果是多账号，请自行处理
		JSTicket jsTicket = WxMemoryCacheClient.getSingleJSTicket();
		if (jsTicket != null && !jsTicket.isExpires()) {
			// 不为空，并且没有过期
			return jsTicket.getTicket();
		} else {
			String token = getAccessToken(mpAccount);
			jsTicket = WxApi.getJSTicket(token);
			if (jsTicket != null) {
				if (jsTicket.getErrcode() != null) {	// 获取失败
					System.out.println("## getJSTicket Error = " +jsTicket.getErrmsg());
				} else {
					WxMemoryCacheClient.addJSTicket(mpAccount.getAccount(), jsTicket);
					return jsTicket.getTicket();
				}
			}
			return null;
		}
	}
	
	/**
	 * 获取OAuthAccessToken
	 * @param mpAccount
	 * @param code
	 * @return
	 */
	public static OAuthAccessToken getOAuthAccessToken(MpAccount mpAccount, String code) {
		// 获取唯一的accessToken，如果是多账号，请自行处理
		OAuthAccessToken token = WxMemoryCacheClient.getSingleOAuthAccessToken();
		if (token != null && !token.isExpires()) {	// 不为空，并且没有过期
			return token;
		} else {
			token = WxApi.getOAuthAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret(), code);
			if (token != null) {
				if (token.getErrcode() != null) {	// 获取失败
					System.out.println("## getOAuthAccessToken Error = " + token.getErrmsg());
				} else {
					token.setOpenid(null);
					// 获取OAuthAccessToken的时候设置openid为null，不同用户openid缓存
					WxMemoryCacheClient.addOAuthAccessToken(mpAccount.getAccount(), token);
					return token;
				}
			}
			return null;
		}
	}
	
	/**
	 * 获取openId
	 * @param mpAccount
	 * @param code
	 * @return
	 */
	public static String getOAuthOpenId(MpAccount mpAccount, String code) {
		OAuthAccessToken token = WxApi.getOAuthAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret(), code);
		if (token != null) {
			if (token.getErrcode() != null) {	// 获取失败
				System.out.println("## getOAuthAccessToke Error = " + token.getErrmsg());
			} else {
				return token.getOpenid();
			}
		}
		return null;
	}
	
	/**
	 * 根据openId获取粉丝信息
	 * @param openId
	 * @param mpAccount
	 * @return
	 */
	public static AccountFans syncAccountFans(String openId, MpAccount mpAccount) {
		String accessToken = getAccessToken(mpAccount);
		String url = WxApi.getFansInfoUrl(accessToken, openId);
		JSONObject jsonObj = WxApi.httpsRequest(url, "GET", null);
		if (null != jsonObj) {
			if (jsonObj.containsKey("errcode")) {
				int errorCode = jsonObj.getInt("errcode");
				System.out.println(String.format("获取用户信息失败 errcode:{} errmsg:{}", errorCode, ErrCode.errMsg(errorCode)));
				return null;
			} else {
				AccountFans fans = new AccountFans();
				fans.setOpenId(jsonObj.getString("openid"));	// 用户的标识
				fans.setSubscribeStatus(new Integer(jsonObj.getInt("subscribe")));// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				if (jsonObj.containsKey("subscribe_time")) {
					fans.setSubscribeTime(jsonObj.getString("subscribe_time"));	// 用户关注时间
				}
				if (jsonObj.containsKey("nickname")) {	// 昵称
					try {
						String nickname = jsonObj.getString("nickname");
						fans.setNickname(nickname.getBytes("UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (jsonObj.containsKey("sex")) {	// 用户的性别（1是男性，2是女性，0是未知
					fans.setGender(jsonObj.getInt("sex"));
				}
				if (jsonObj.containsKey("language")) {	// 用户的语言，简体中文为zh_CN
					fans.setLanguage(jsonObj.getString("language"));
				}
				if (jsonObj.containsKey("country")) {	// 用户所在国家
					fans.setCountry(jsonObj.getString("country"));
				}
				if (jsonObj.containsKey("province")) {	// 用户所在省份
					fans.setCity(jsonObj.getString("city"));
				}
				if (jsonObj.containsKey("city")) {	// 所在城市
					fans.setCity(jsonObj.getString("city"));
				}
				if (jsonObj.containsKey("headimgurl")) {	// 用户头像
					fans.setHeadimgurl(jsonObj.getString("headimgurl"));
				}
				if (jsonObj.containsKey("remark")) {
					fans.setRemark(jsonObj.getString("remark"));
				}
				return fans;
			}
		}
		return null;
	}
}
