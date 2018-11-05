package com.ml.example;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.ml.example.asm.ClassPrinter;
import com.ml.example.asm.DefaultInterface2Impl;
import com.ml.example.asm.InterfaceHandler4Asm;
import com.ml.example.service.HellowService;
import com.ml.example.service.RobbService;
import com.ml.example.service.impl.HellowServiceImpl;
import com.ml.example.service.impl.RobbService1Impl;
import com.ml.example.service.impl.RobbService2Impl;

//@EnableEurekaClient
//@SpringBootApplication(scanBasePackages={"com.example","com.ml"})
public class MyEurekaClient {

	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		SpringApplication.run(MyEurekaClient.class, args);
		HellowService hService = (HellowService) InterfaceHandler4Asm.buildClass(HellowService.class);
//		hService.home("123");
		System.out.println(hService.getClass().getSimpleName());
		System.out.println(hService.getClass().getName());
		System.out.println(hService.getClass().getResource("/").getPath());
		
		ClassPrinter printer = ClassPrinter.getNewPrinter();
		InputStream is = ClassLoader.getSystemResourceAsStream(HellowServiceImpl.class.getName().replace(".", "/")+".class");
		ClassReader cReader = new ClassReader(is);
		cReader.accept(printer, 0);
		is.close();
		System.out.println("------------------------------");
		 is = ClassLoader.getSystemResourceAsStream(HellowService.class.getName().replace(".", "/")+".class");
		 cReader = new ClassReader(is);
			cReader.accept(printer, 0);
			is.close();
			System.out.println("------------------------------");

//			RobbService robbService1 = (RobbService) RobbServiceImpl.class.getConstructor(null).newInstance(null);
//			robbService1.home("123");
//			System.out.println("------------------------------");


			System.out.println("-----"+Type.getType(RobbService.class).getDescriptor());
			System.out.println("-----"+Type.getType(RobbService.class).getClassName());
			System.out.println("-----"+Type.getType(RobbService.class).getDimensions());
			for (Method mh : RobbService.class.getDeclaredMethods()) {
				System.out.println("--"+Type.getMethodDescriptor(mh)+"--"+Type.getType(mh).getDescriptor());
				
				for (Type type : Type.getArgumentTypes(mh)) {
					System.out.println("-----"+type.getDescriptor());
				}
			}
			System.out.println("------------------------------");
			
			Class clz = DefaultInterface2Impl.buildImplClass(RobbService.class);
			System.out.println("------------------------------");
			 is = ClassLoader.getSystemResourceAsStream("RobbServiceImpl.class");
			 cReader = new ClassReader(is);
				cReader.accept(printer, 0);
				is.close();
				System.out.println("------------------------------");
				
			System.out.println(clz.getSimpleName());
			System.out.println(clz.getName());
			System.out.println(clz.getClassLoader());
			System.out.println(RobbService.class.getClassLoader());
			
			RobbService robbService = (RobbService) clz.getConstructor(null).newInstance(null);
			robbService.setService(new RobbService1Impl());
			robbService.home("123");
			robbService.setService(new RobbService2Impl());
			robbService.home("123");
	}

}
