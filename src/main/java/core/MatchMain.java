package core;

import test.MySessionFactory;

public class MatchMain {
	public static long RUN_CYCLE = 5*1000;
	public static double SAVE_THRESHOLD = 0.3;
	
	public static void main(String[] args){
		RequestMatching.setSaveThreshold(SAVE_THRESHOLD);
//		while(true){
			long start = System.currentTimeMillis();
			new RequestMatching().doMatching();
			long end = System.currentTimeMillis();
			System.out.println("Time: "+(end-start)/1000.0 +" s");
			MySessionFactory.getSessionFactory().close();
//			if(end - start < RUN_CYCLE){
//				try {
//					Thread.sleep(RUN_CYCLE - end + start);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}

}
