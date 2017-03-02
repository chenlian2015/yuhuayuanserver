package com.yuhuayuan.api.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.yuhuayuan.api.controller.Login;

@Controller
public class UploadFile {
	
	 private static final Logger logger = Logger.getLogger(UploadFile.class);
		@RequestMapping(value="/uploadOneFile", method=RequestMethod.POST)
	    public @ResponseBody String handleFileUpload(
	            @RequestParam("file") MultipartFile file){
	        if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                return uploadFileToAli(bytes, file.getContentType());
	            } catch (Exception e) {
	                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
	            }
	        } else {
	            return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
	        }
	    }
		
		public static String bucketName = "yuhuayuan";
		private String uploadFileToAli(byte [] data, String type)
		{
			String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
			// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeyId = "LTAIXjdMD9qIc9Wl";
			String accessKeySecret = "wb5Dhb2t9OPYqBK5LQrMkfv4T61acF";
			// 创建OSSClient实例
			OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(type);
		
			
			// 使用访问OSS
		    String key = ""+System.currentTimeMillis()+""+Math.decrementExact(100000000);
		    PutObjectResult re= client.putObject(bucketName, key, new ByteArrayInputStream(data), meta);
		    
		    Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		    
            String url = client.generatePresignedUrl(bucketName, key, expiration).toString();
			// 关闭client
			client.shutdown();
			return url;
		}
}
