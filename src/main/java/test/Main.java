package test;

import java.io.IOException;

import module.EasemodNotifier;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.RandomUtil;

public class Main {
	public static void main(String[] args) throws IOException {
		
		RandomUtil.currentRequestId = 100;
		EasemodNotifier notifier = new EasemodNotifier();
		notifier.start();
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"provider.xml"});
    	context.start();
    	System.out.println("server started...");
    	System.in.read();
    	MySessionFactory.getSessionFactory().close();
    	
//		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"provider.xml"});
//	        context.start();
//	        System.out.println("server started...");
//	        System.in.read(); // 按任意键退出
    }
}
