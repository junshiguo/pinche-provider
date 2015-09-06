package module;

import java.util.ArrayList;

import core.MatchMain;

public class Notifier extends Thread {
	public static ArrayList<String> toSend = new ArrayList<String>();	
	public static synchronized String modifyToSend(int action, String node){
		if(action == 0){
			String n = toSend.get(0);
			toSend.remove(0);
			return n;
		}else{
			toSend.add(node);
		}
		return null;
	}
	public static boolean hasNextToSend(){
		if(toSend.size() > 0)	return true;
		return false;
	}
	public static String nextToSend(){
		return modifyToSend(0, null);
	}
	public static void addToSend(String node){
		modifyToSend(1, node);
	}
	
	public void run(){
		while(true){
			if(hasNextToSend()){
				EasemodMsgModule.sendMsg(nextToSend(), "0");
//				ArrayList<String> tmp = toSend;
//				toSend = new ArrayList<String>();
//				if(EasemodMsgModule.sendMsg(tmp, "0") == null){
////					Notifier.addToSend(userId);
//				}
			}else{
				try {
					sleep(100);
					if(MatchMain.RUNNING == false){
						break;
					}
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
}
