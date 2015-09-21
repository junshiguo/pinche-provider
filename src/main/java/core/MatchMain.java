package core;

import module.EasemodMsgModule;
import module.EasemodNotifier;
import test.MySessionFactory;
import util.RandomUtil;

public class MatchMain {
	public static long RUN_CYCLE = 10*1000;
	public static double SAVE_THRESHOLD = 0.15;
	public static boolean RUNNING = true;
	
	public static void main(String[] args){
		RandomUtil.currentOrderId = 100;
		if(args.length > 0){
			RandomUtil.currentOrderId = Long.parseLong(args[0]);
		}
		RequestMatching.setSaveThreshold(SAVE_THRESHOLD);
		EasemodNotifier notifier = new EasemodNotifier();
		notifier.start();
		while(true){
			long start = System.currentTimeMillis();
			new RequestMatching().doMatching();
			long end = System.currentTimeMillis();
			System.out.println("Time: "+(end-start)/1000.0 +" s");
			if(end - start < RUN_CYCLE){
				try {
					Thread.sleep(RUN_CYCLE - end + start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			RUNNING = false;
//			MySessionFactory.getSessionFactory().close();
	}
	}
	
}
