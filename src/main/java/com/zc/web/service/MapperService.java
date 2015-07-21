package com.zc.web.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperService {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext*.xml");
	
	public static Object getMapper(String mapperName){
		if(context == null){
			return new Object();
		}
		Object obj = context.getBean(mapperName);
		return obj==null ? new Object() : obj;
	}
}
