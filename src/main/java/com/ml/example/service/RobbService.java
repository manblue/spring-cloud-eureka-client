package com.ml.example.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RobbService {

	 String home(String name);
	 void home1(String...strings );
	 List<String> home2(String[] strings );
	 Map<String,Object> home3(List<String> strings );
	 Map home4(List strings ) throws IOException,RuntimeException;
	 
	 String home5(int a1,Long a2,BigDecimal a3);
	 String home6(boolean a1,char a2,byte a3,short a4,int a5,float a6,
			 double a7,int[] a8 ,String[] a9,Integer a10,long a11);
	 String home8();
	 
	 void setService(RobbService robbService);
}
