package com.yuhuayuan.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuhuayuan.common.ServerErrorCode;
import com.yuhuayuan.core.dto.city.City;
import com.yuhuayuan.core.dto.city.CityList;
import com.yuhuayuan.core.dto.user.User;
import com.yuhuayuan.core.dto.version.AppVersion;
import com.yuhuayuan.core.persistence.UserMapper;
import com.yuhuayuan.core.service.appversion.VersionService;
import com.yuhuayuan.core.service.redis.RedisCacheService;
import com.yuhuayuan.core.service.user.UserService;
import com.yuhuayuan.tool.ControllTool;
import com.yuhuayuan.tool.returngson.GsonResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class Login {
	
	@Autowired
    private UserService userServiceImpl;
	
    @Autowired
    private  HttpServletRequest request;
    
    private static final Logger logger = Logger.getLogger(Login.class);
    
	@Autowired
	protected UserMapper userMapper;
	
    @Autowired
    protected RedisCacheService cacheService;

	@Autowired
	VersionService versionService;

    void test()
	{

		AppVersion versionDto = new AppVersion();
		versionDto.setChannel("a");
		versionDto.setName("a");
		versionDto.setContent("c");
		versionDto.setFileId(1);
		versionDto.setCreateTime(new Date());
		versionDto.setFileUrl("www");
		versionDto.setFileName("filename");
		versionService.addVersion(versionDto);

	}

    @RequestMapping(value = "test.do")
    private @ResponseBody void test(HttpServletRequest request, HttpServletResponse response)
	{

		test();
    	String strResult = "";
    	try {

				int n=0;
				CityList cityList = new CityList();
				List<City> lstCity =  new ArrayList();
				
				while(n++<10)
				{
					City city = new City();
					city.setName("name"+n);
					city.setCountry("c:"+n);
					city.setCity("city:"+n);
					lstCity.add(city);
				}
				cityList.setRecords(lstCity);
				
				response.setContentType("text/json;charset=utf-8");
				PrintWriter pw = response.getWriter();
				strResult = JSON.toJSONString(cityList); 
				com.alibaba.fastjson.JSONObject jo = new JSONObject();
				
				pw.print(strResult);
				pw.flush();
				pw.close();
				pw = null;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
	}
    

	@RequestMapping(value = "noticeWeiXin.do", method=RequestMethod.POST)
	private @ResponseBody GsonResult login(HttpServletRequest request, HttpServletResponse response)
	{
		User u = null;
		//ControllTool.LogRequest(request, logger);
		try
		{
			
		String strRequest = ControllTool.getRequestBody(request);
		logger.error(strRequest);

		
		User usr = JSON.parseObject(strRequest, User.class);
		
		boolean b = true;
		b = userServiceImpl.insert(usr);
		  
		}catch(Exception e)
		{
			return new GsonResult(u, ServerErrorCode.FAILED.getCode(), "");
		}
		
		return new GsonResult(u, ServerErrorCode.SUCCESS.getCode(), "");  
	}

	

	
	/***
	 * 保存文件
	 * @param file
	 * @return
	 */
	private boolean saveFile(MultipartFile file) {
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				// 文件保存路径
				
				String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"
						+ file.getOriginalFilename();
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}



}
