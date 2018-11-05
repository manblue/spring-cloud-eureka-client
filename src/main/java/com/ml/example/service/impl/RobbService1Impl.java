package com.ml.example.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ml.example.service.HellowService;
import com.ml.example.service.RobbService;

public class RobbService1Impl implements RobbService {

	private RobbService robbService;
	private HellowService hellowService;
	public String home(String name) {
		// TODO Auto-generated method stub
//		hellowService.home(name);
		String ss = "this is "+getClass()+" home("+name+")";
		System.out.println(ss);
		return ss;
	}

	public void home1(String... strings) {
		robbService.home1(strings);
	}

	public List<String> home2(String[] strings) {
		// TODO Auto-generated method stub
		return robbService.home2(strings);
	}

	public Map<String, Object> home3(List<String> strings) {
		// TODO Auto-generated method stub
		return robbService.home3(strings);
	}

	public Map home4(List strings) throws IOException, RuntimeException {
		// TODO Auto-generated method stub
		return robbService.home4(strings);
	}

	public String home5(int a1, Long a2, BigDecimal a3) {
		// TODO Auto-generated method stub
		return robbService.home5(a1, a2, a3);
	}

	public String home6(boolean a1, char a2, byte a3, short a4, int a5,
			float a6, double a7, int[] a8, String[] a9,Integer a10,long a11) {
		// TODO Auto-generated method stub
		return robbService.home6(a1, a2, a3, a4, a5, a6, a7, a8, a9,a10,a11);
	}

	public String home8() {
		// TODO Auto-generated method stub
		String ss = "this is "+getClass()+" home8";
		System.out.println(ss);

		return ss;
	}

	public void setService(RobbService robbService) {
		this.robbService = robbService;
	}
}
