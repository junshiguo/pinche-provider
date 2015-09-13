package module;

import java.io.IOException;
import java.util.ArrayList;

import core.MatchMain;

public class MessageNotifier extends Thread {
	public static ArrayList<MessageNode> toSend = new ArrayList<MessageNode>();	
	public static synchronized MessageNode modifyToSend(int action, MessageNode node){
		if(action == 0){
			MessageNode n = toSend.get(0);
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
	public static MessageNode nextToSend(){
		return modifyToSend(0, null);
	}
	
	public static void addToSend(MessageNode node){
		modifyToSend(1, node);
	}
	public static void addVerifycode(String mobile, String verifycode){
		addToSend(new MessageNode(mobile, verifycode));
	}
	public static void addMatchSuccess(String mobile){
		addToSend(new MessageNode(mobile));
	}
	public static void addPartnerPhone(String mobile, String orderId, String phone){
		addToSend(new MessageNode(mobile, orderId, phone));
	}
	public static void addRejection(String mobile, String orderId){
		addToSend(new MessageNode(mobile, orderId, MessageNode.TYPE_REJECTION));
	}
	
	public void run(){
		while(true){
			if(hasNextToSend()){
				MessageNode node = nextToSend();
					try {
						if(node.type == MessageNode.TYPE_VERIFYCODE){
							YunpianMsgModule.sendVerifyCode(node.verifycode, node.mobile);
						}else if(node.type ==MessageNode.TYPE_MATCH_SUCCESS ){
							YunpianMsgModule.sendMatchSuccess(node.mobile);
						}else if(node.type == MessageNode.TYPE_PARTNER_PHONE){
							YunpianMsgModule.sendPartnerPhone(node.orderId, node.phone, node.mobile);
						}else if(node.type == MessageNode.TYPE_REJECTION){
							YunpianMsgModule.sendRejection(node.orderId, node.mobile);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
			}else{
				try {
					sleep(100);
					if(MatchMain.RUNNING == false){
						break;
					}
				} catch (InterruptedException e) {}
			}
		}
	}
	
}

