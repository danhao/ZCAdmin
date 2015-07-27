package com.zc.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class Config {
	private static Properties config = new Properties();

	static {
		try {
			InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");
			config.load(inputStream, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getConfig() {
		return config;
	}
}
