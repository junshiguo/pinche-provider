package test;

import java.io.IOException;

import module.Notifier;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import util.RandomUtil;

public class Main {
	public static void main(String[] args) throws IOException {
		
		RandomUtil.currentRequestId = 2100;
		Notifier notifier = new Notifier();
		notifier.start();
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"provider.xml"});
    	context.start();
    	System.out.println("server started...");
    	System.in.read();
    	MySessionFactory.getSessionFactory().close();
    }
}
