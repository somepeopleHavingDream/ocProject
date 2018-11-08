package com.online.college.wechat.wxapi.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.online.college.wechat.wxapi.process.MpAccount;
import com.online.college.wechat.wxapi.process.MsgXmlUtil;
import com.online.college.wechat.wxapi.process.SignUtil;
import com.online.college.wechat.wxapi.process.WxMemoryCacheClient;
import com.online.college.wechat.wxapi.service.impl.MyServiceImpl;
import com.online.college.wechat.wxapi.vo.MsgRequest;

/**
 * 微信与开发者服务器接口交互接口
 */
@Controller
@RequestMapping("/wxapi")
public class WxApiCtrl {

	@Autowired
	private MyServiceImpl myService;
	
	/**
	 * GET请求：进行URL、Token认证：
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行shal加密
	 * 3. 开发者获得加密后的字符串的字符串可与signature对比，标识该请求来源于请求
	 */
	@RequestMapping(value="/{accout}/message", method=RequestMethod.GET)
	public @ResponseBody String doGet(HttpServletRequest request, @PathVariable String account) {
		// 如果是多账号，根据url中的account参数获取对应的MpAccount处理即可
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
		// 获取缓存中的唯一账号
		if (mpAccount != null) {
			String token = mpAccount.getToken();	// 获取token，进行验证
			String signature = request.getParameter("signature");	// 微信加密签名
			String timestamp = request.getParameter("timestamp");	// 时间戳
			String nonce = request.getParameter("nonce");	// 随机数
			String echostr = request.getParameter("echostr");	// 随机字符串
			
			// 校验成功返回 echostr，成功成为开发者，否则返回error，接入失败
			if (SignUtil.validSign(signature, token, timestamp, nonce)) {
				return echostr;
			}
		}
		return "error";
	}
	
	/**
	 * POST请求：进行消息处理
	 */
	@RequestMapping(value = "/{account}/message", method=RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request, @PathVariable String account, HttpServletResponse response) {
		// 处理用户和微信公众账号交互消息
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
		try {
			MsgRequest msgRequest = MsgXmlUtil.parseXml(request);
			// 获取发送的消息
			return myService.processMsg(msgRequest, mpAccount);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
}
