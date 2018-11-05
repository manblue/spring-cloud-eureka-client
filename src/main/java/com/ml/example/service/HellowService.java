package com.ml.example.service;

import java.util.List;
import java.util.Map;

public interface HellowService {

	 String home(String name);
	 void home1(String...strings );
	 List<String> home2(String[] strings );
	 Map<String,Object> home3(List<String> strings );
	 Map home4(List strings );

}
