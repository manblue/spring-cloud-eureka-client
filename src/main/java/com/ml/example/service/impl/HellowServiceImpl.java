package com.ml.example.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.annotation.DistLock;
import com.ml.example.service.HellowService;

@Service
public class HellowServiceImpl implements HellowService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private  int aaa = 30;

	@DistLock
	public String home(String name) {
		logger.error("class:{} home {} {} leave:{}", this,name,Thread.currentThread().getName(), aaa--);
//		try {
//			Thread.currentThread().sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return name;
	}
	
	public void home1(String... strings) {
		// TODO Auto-generated method stub
		
	}
	public List<String> home2(String[] strings) {
		// TODO Auto-generated method stub
		return null;
	}
	public Map<String, Object> home3(List<String> strings) {
		// TODO Auto-generated method stub
		return null;
	}
	public Map home4(List strings) {
		// TODO Auto-generated method stub
		return null;
	}
}
