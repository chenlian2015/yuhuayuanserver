package com.yuhuayuan.core.service.impl.weixin;

import com.alibaba.fastjson.JSON;
import com.yuhuayuan.constant.WeiXinConstant;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.core.dto.weixin.QRCodeRequest;
import com.yuhuayuan.core.dto.weixin.UserBaseInfo;
import com.yuhuayuan.core.persistence.UserMapper;
import com.yuhuayuan.core.service.redis.RedisCacheService;
import com.yuhuayuan.core.service.user.UserService;
import com.yuhuayuan.core.service.weixin.WeiXinService;
import com.yuhuayuan.tool.ImageGenerator;
import com.yuhuayuan.tool.net.http.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class WeiXinServiceImpl implements WeiXinService{

	private static final Logger logger = Logger.getLogger(WeiXinServiceImpl.class);

	@Autowired
	private UserService userServiceImpl;

	@Autowired
	protected RedisCacheService cacheService;

	@Autowired
	protected UserMapper userMapper;

	// 返回宣传图片demo
	public String DealRequest(String request) {
		org.json.JSONObject joROOT = XML.toJSONObject(request);
		org.json.JSONObject joXML = (JSONObject) joROOT.get("xml");
		String FromUserName = (String) joXML.get("ToUserName");
		String ToUserName = (String) joXML.get("FromUserName");

		String CreateTime = "" + System.currentTimeMillis();
		String template = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[%4$s]]></MediaId></Image></xml>";

		String result = String.format(template, ToUserName, FromUserName, CreateTime,
				"2cjk8UDYVvkl1sZLSmbqoFCkf0hxAT8uscn_fCpDzLxP0S2mjWwCGCykuXlpmko9");
		return result;
	}

	public String getWeiXinAccessToken() {
		return "";
	}

	/**
	 * 微信关注事件
	 */
	public String weixinMessage(String request) {
		org.json.JSONObject joROOT = XML.toJSONObject(request);
		org.json.JSONObject joXML = (JSONObject) joROOT.get("xml");
		String FromUserName = (String) joXML.get("ToUserName");
		String ToUserName = (String) joXML.get("FromUserName");
		String MsgType = (String) joXML.get("MsgType");


		// 事件消息
		if (!StringUtils.isEmpty(MsgType) && WeiXinConstant.MSG_TYPE_EVENT.equals(MsgType)) {

			String event = (String) joXML.get("Event");
			// 事件为关注事件
			if (!StringUtils.isEmpty(event) && WeiXinConstant.MSG_TYPE_EVENT_SUBSCRIBE.equals(event)) {

				try {
					// 表示所扫描二维码的参数
					String eventKey = (String) joXML.get("EventKey");
					if (!StringUtils.isEmpty(eventKey)) {
						eventKey = eventKey.replace("qrscene_", "");
					}

					User usr = new User();
					usr.setOpenid(ToUserName);
					usr.setShareFromOpenId(eventKey);

					UserBaseInfo userBaseInfo = getWeiXinUserBaseInfo(ToUserName);
					String headImageUrl = userBaseInfo.getHeadimgurl();
					usr.setHeadImageUrl(headImageUrl);
					usr.setNickName(userBaseInfo.getNickname());
					String qrCodeImage = getUserQCode(ToUserName);

					File fCombined = ImageGenerator.generateImage(headImageUrl, qrCodeImage, userBaseInfo.getNickname(),
							userBaseInfo.getOpenid());
					// 上传合成后的宣传图片到微信后台，拿到id后，存于数据库
					String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="
							+ cacheService.get("access_token");

					Map<String, String> textMap = new HashMap<String, String>();
					Map<String, String> fileMap = new HashMap<String, String>();

					fileMap.put("userfile", fCombined.getPath());
					String strUploadImageResult = HttpUtils.formUpload(url, textMap, fileMap);
					fCombined.delete();

					com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject) JSON
							.parse(strUploadImageResult);
					String imageUploaded = (String) jo.get("url");

					usr.setSharePicWithZCode(imageUploaded);

					boolean b = true;
					b = userServiceImpl.insert(usr);
				} catch (Exception e) {
					logger.error("weixinMessage", e);
				}
			} else if ("CLICK".equals(event)) {
				User uGet = userMapper.selectByOpenid(ToUserName);
				// 表示用户点击时间的id
				String eventKey = (String) joXML.get("EventKey");
				if ("VKEY_SHARE_SHANMEI".equals(eventKey)) {
					if (null != uGet) {
						String CreateTime = "" + System.currentTimeMillis();
						String template = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>1</ArticleCount><Articles><item><Title><![CDATA[分享我的善美,获得更多美丽枝叶]]></Title> <Description><![CDATA[分享出去，精彩继续]]></Description><PicUrl><![CDATA[%4$s]]></PicUrl><Url><![CDATA[%5$s]]></Url></item></Articles></xml>";

						try {

							String result = String.format(template, ToUserName, FromUserName, CreateTime,
									uGet.getSharePicWithZCode(), uGet.getSharePicWithZCode());
							return result;
						} catch (Exception e) {
							logger.error("weixinMessage", e);
						}
					}
				} else if ("VKEY_MY_MEMBERS".equals(eventKey)) {

					String CreateTime = "" + System.currentTimeMillis();
					String template = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>%4$s</ArticleCount><Articles>%5$s</Articles></xml>";

					List<User> lstLeaves = null;
					try {
						lstLeaves = userMapper.selectChildUsers(ToUserName);

					} catch (Exception e) {
						logger.error(e.toString());
					}
					
					if (lstLeaves.size() > 0) {
						Iterator<User> itLeaf = lstLeaves.iterator();
						StringBuffer articlesContent = new StringBuffer();
						while (itLeaf.hasNext()) {
							String articleItemTemplate = "<item><Title><![CDATA[%1$s]]></Title> <Description><![CDATA[]]></Description><PicUrl><![CDATA[%2$s]]></PicUrl><Url><![CDATA[%3$s]]></Url></item>";
							User u = itLeaf.next();
							articlesContent.append(String.format(articleItemTemplate, u.getNickName(),
									u.getHeadImageUrl(), u.getHeadImageUrl()));
						}
						String articleContent = String.format(template, ToUserName, FromUserName, CreateTime,
								"" + lstLeaves.size(), articlesContent.toString());

						logger.error(articleContent);
						return articleContent;
					} else {

						String createTime = "" + System.currentTimeMillis();
						String templateNoLeaf = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[news]]></MsgType><ArticleCount>1</ArticleCount><Articles><item><Title><![CDATA[分享我的善美,获得更多美丽枝叶]]></Title> <Description><![CDATA[分享出去，精彩继续]]></Description><PicUrl><![CDATA[%4$s]]></PicUrl><Url><![CDATA[%5$s]]></Url></item></Articles></xml>";

						try {
							String result = String.format(template, ToUserName, FromUserName, CreateTime,
									WeiXinConstant.logoImage, "");
							return result;
						} catch (Exception e) {
							logger.error("weixinMessage", e);
						}
					}
				}

			}
		} else {
			String CreateTime = "" + System.currentTimeMillis();
			String template = "<xml><ToUserName><![CDATA[%1$s]]></ToUserName><FromUserName><![CDATA[%2$s]]></FromUserName><CreateTime>%3$s</CreateTime><MsgType><![CDATA[image]]></MsgType><Image><MediaId><![CDATA[%4$s]]></MediaId></Image></xml>";

			String result = String.format(template, ToUserName, FromUserName, CreateTime,
					"2cjk8UDYVvkl1sZLSmbqoFCkf0hxAT8uscn_fCpDzLxP0S2mjWwCGCykuXlpmko9");
			return result;
		}

		return "";
	}

	public UserBaseInfo getWeiXinUserBaseInfo(String openid) {

		String access_token = "";
		try {
			access_token = cacheService.get("access_token");
			String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid="
					+ openid + "&lang=zh_CN";
			String str = HttpUtils.get4String(url, new HashMap<String, String>());
			str = new String(str.getBytes("ISO-8859-1"), "UTF-8");
			UserBaseInfo userBaseInfo = JSON.parseObject(str, UserBaseInfo.class);
			return userBaseInfo;
		} catch (Exception e) {
			logger.error("getWeiXinUserBaseInfo:" + e.toString());
		}
		return null;
	}

	public String getUserQCode(String openid) {
		String access_token = "";
		try {

			access_token = cacheService.get("access_token");
			String urlQRCodeRequest = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token;

			QRCodeRequest qrCodeRequest = new QRCodeRequest();

			QRCodeRequest.Scene scene = new QRCodeRequest.Scene();
			scene.setScene_str(openid);

			QRCodeRequest.ActionInfo actionInfo = new QRCodeRequest.ActionInfo();
			actionInfo.setScene(scene);

			qrCodeRequest.setAction_name("QR_LIMIT_STR_SCENE");
			qrCodeRequest.setAction_info(actionInfo);

			String jsonQRCodeRequest = JSON.toJSONString(qrCodeRequest);
			String jsonQRCodeResult = HttpUtils.postString(urlQRCodeRequest, jsonQRCodeRequest);

			com.alibaba.fastjson.JSONObject jo = (com.alibaba.fastjson.JSONObject) JSON.parse(jsonQRCodeResult);
			String qrCodeTicket = (String) jo.get("ticket");

			return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + qrCodeTicket;
		} catch (Exception e) {
			logger.error("getWeiXinUserBaseInfo:" + e.toString());
			return "";
		}
	}

}
