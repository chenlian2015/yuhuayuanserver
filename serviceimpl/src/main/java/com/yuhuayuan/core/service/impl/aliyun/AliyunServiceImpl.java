package com.yuhuayuan.core.service.impl.aliyun;

import com.yuhuayuan.core.aliyun.ObjectUpLoad;
import com.yuhuayuan.core.service.aliyun.AliyunService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AliyunServiceImpl implements AliyunService{

	public boolean uploadObjectToAliyun()
	{
		try {
			ObjectUpLoad.upLoadAliyunTest();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		return true;
	}
}
