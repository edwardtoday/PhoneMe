package org.kde9.util;

public class ConfigFactory {
	static Configuration configuration = new Configuration();
	
	public static Configuration creatConfig() {
		return configuration;
	}
	
//	public static void main(String args[]) {
//		Configuration c = ConfigFactory.creatConfig();
//		System.out.println(c.original);
//		System.out.println(c.config);
//	}
}
