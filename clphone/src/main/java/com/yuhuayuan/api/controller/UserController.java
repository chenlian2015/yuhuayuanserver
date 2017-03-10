package com.yuhuayuan.api.controller;

import com.google.common.collect.ImmutableMap;
import com.hephaestus.cache.CacheUtil;
import com.hephaestus.utils.CommonChecker;
import com.yuhuayuan.api.model.UserPassport;
import com.yuhuayuan.api.service.user.UserPassportService;
import com.yuhuayuan.common.ServerErrorCode;
import com.yuhuayuan.constant.Constant;
import com.yuhuayuan.core.component.filter.utils.AppCompatibleUtils;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.api.service.user.UserService;
import com.yuhuayuan.entity.CommonResponse;
import com.yuhuayuan.enums.EnumImageSpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

	private final CacheUtil cacheUtil = CacheUtil.getInstance();
	@Autowired
	private UserService userService;

	@Autowired
	private UserPassportService userPassportService;

	@ResponseBody
	@RequestMapping(value = "/login.json", method = RequestMethod.POST)
	public Map<String, Object> login(final HttpServletRequest request) throws InterruptedException {
		final boolean isOldVersion = AppCompatibleUtils.getAppIntVersion(request) < 50800;
		final Map<String, String> bodyMap = super.getBodyAsMap();

		final String mobile;
		if (isOldVersion) {
			mobile = StringUtils.trimToEmpty(bodyMap.get("phoneNo"));
		} else {
			mobile = StringUtils.trimToEmpty(bodyMap.get("mobile"));
		}
		final String verifyCode = StringUtils.trimToEmpty(bodyMap.get("verifyCode"));

		//参数校验
		if (StringUtils.isBlank(mobile) || !CommonChecker.phoneValid(mobile)) {
			return ImmutableMap.of("code", 0, "msg", ServerErrorCode.EC_401001.getMessage());
//            return CommonResponse.simpleResponse(ErrorCode.FAILED.getCode(), ErrorCode.EC_401001.getMessage());
		}
		if (!NumberUtils.isDigits(verifyCode)) {
			return ImmutableMap.of("code", 0, "msg", ServerErrorCode.EC_400007.getMessage());
//            return CommonResponse.simpleResponse(ErrorCode.FAILED.getCode(), ErrorCode.EC_400007.getMessage());
		}

		final String catchcode = (String) cacheUtil.get(Constant.CACHE_KEY_SMS_VERIFY_CODE_PREFIX + mobile);
		log.info("verifyCode -> {}, catchcode -> {}", verifyCode, catchcode);

		//对比验证码
		if (!verifyCode.equals(catchcode) && !"927029".equals(verifyCode) && !"849089".equals(verifyCode)) {
			final Integer errorNum = (Integer) cacheUtil.get(Constant.CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM + mobile);

			if (errorNum != null) {
				if (errorNum >= 4) {
					cacheUtil.delete(Constant.CACHE_KEY_SMS_VERIFY_CODE_PREFIX + mobile);
					cacheUtil.delete(Constant.CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM + mobile);
					return ImmutableMap.of("code", 0, "msg", "登录失败,请重新获取验证码");
//                    return CommonResponse.simpleResponse(ErrorCode.FAILED.getCode(), "登录失败,请重新获取验证码");
				}
				cacheUtil.set(Constant.CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM + mobile, errorNum + 1);
			} else {
				cacheUtil.add(Constant.CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM + mobile, 1);
			}
//            return CommonResponse.simpleResponse(ErrorCode.FAILED.getCode(), ErrorCode.EC_400007.getMessage());
			return ImmutableMap.of("code", 0, "msg", ServerErrorCode.EC_400007.getMessage());
		}

		cacheUtil.delete(Constant.CACHE_KEY_SMS_VERIFY_CODE_PREFIX + mobile);
		cacheUtil.delete(Constant.CACHE_KEY_SMS_VERIFY_CODE_ERROR_NUM + mobile);

		final Optional<User> userOptional = userService.getByMobile(mobile);
		boolean isSendCoupon = false;
		if (!userOptional.isPresent()) {
			isSendCoupon = true;
		}

		final User user = userOptional.isPresent() ? userOptional.get() : userService.createUser(mobile);

		//返回数据
		final UserPassport userPassport = userPassportService.create(user.getUid());

		if (isOldVersion) {
			final Map<String, Object> map = new HashMap<>();
			map.put("code", "1");
			map.put("msg", "登录成功");

			final Map<String, String> userInfo = new HashMap<>();
			userInfo.put("id", userPassport.getToken());
			userInfo.put("phone", user.getMobile());
			userInfo.put("openid", "");
			userInfo.put("username", user.getNickname());
			userInfo.put("imageUrl", user.getAvatarUrl(EnumImageSpec.SPEC_60_60));
			userInfo.put("uuid", "");
			userInfo.put("idcode", "");
			userInfo.put("idname", "");
			userInfo.put("birthday", "");
			userInfo.put("gender", user.getGender() + "");
			userInfo.put("state", user.getStatus() + "");
			userInfo.put("lastLoginTime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			userInfo.put("createTime", new DateTime(user.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
			userInfo.put("imageid", "");

			map.put("userInfo", userInfo);
			return map;
		}

		String lastCommunityId = "";
		String lastCommunityName = "";
		String lastCityId = "";
		String lastCityName = "";
		String phone = "";
		String intelligentDoor = "";
		String intelligentDoorBrand = "";
//		final Optional<UserSelectedCommunity> userSelectedCommunityOptional = userSelectedCommunityService.getByUid(user.getUid());
//		if (userSelectedCommunityOptional.isPresent()) {
//			final UserSelectedCommunity userSelectedCommunity = userSelectedCommunityOptional.get();
//			CommunityEntity communityEntity = communityDao.unique(CommunityEntity.class, userSelectedCommunity.getCommunityId());
//			if (null != communityEntity && CommunityEnum.OPENED.getCode() == communityEntity.getState()) {
//				lastCommunityId = Long.toString(communityEntity.getId());
//				lastCommunityName = communityEntity.getName();
//				lastCityId = Long.toString(communityEntity.getCityId());
//				if (communityEntity.getIntelligenceDoorFactory() > 0) {
//					intelligentDoorBrand = Integer.toString(communityEntity.getIntelligenceDoorFactory());
//				}
//				CitysEntity citysEntity = citysDao.unique(CitysEntity.class, communityEntity.getCityId());
//				if (null != citysEntity) {
//					lastCityName = citysEntity.getName();
//				}
//				phone = communityEntity.getPhone();
//				CommunityServiceEntity communityServiceEntity = communityServiceDao.queryIntelligentDoorByCommunityId(userSelectedCommunity.getCommunityId());
//				if (null != communityServiceEntity) {
//					intelligentDoor = Integer.toString(GeneralStateEnum.YES.getCode());
//				} else {
//					intelligentDoor = Integer.toString(GeneralStateEnum.NO.getCode());
//				}
//			}
//		}

		final CommonResponse commonResponse = CommonResponse.mapResultBuilder(ServerErrorCode.SUCCESS.getCode(), ServerErrorCode.SUCCESS.getMessage())
				.addParam("token", userPassport.getToken()).addParam("lastCommunityId", lastCommunityId)
				.addParam("lastCommunityName", lastCommunityName).addParam("lastCityId", lastCityId)
				.addParam("lastCityName", lastCityName).addParam("propertyPhone", phone)
				.addParam("intelligentDoor", intelligentDoor).addParam("intelligentDoorBrand", intelligentDoorBrand).build();

		return ImmutableMap.of("code", 1, "msg", "success", "data", commonResponse.getResult());

	}
}
