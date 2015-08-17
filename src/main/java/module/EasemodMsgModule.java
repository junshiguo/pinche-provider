package module;

import java.util.ArrayList;

import com.easemob.server.example.httpclient.apidemo.EasemobMessages;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EasemodMsgModule {
	
	public static String sendMsg(ArrayList<String> user,String message){
	    String from = "fuwuqi";
	    String targetTypeus = "users";
	    ObjectNode ext = JsonNodeFactory.instance.objectNode();
	    ArrayNode targetusers = JsonNodeFactory.instance.arrayNode();
	    for(String u : user)
	    	targetusers.add(u);

	    ObjectNode txtmsg = JsonNodeFactory.instance.objectNode();
	    txtmsg.put("msg", message.toString());
	    txtmsg.put("type", "txt");
//	    ext.put("message", message.toString());
	    
	    ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(targetTypeus, targetusers, txtmsg, from, ext);
	    if (null != sendTxtMessageusernode) {
	      System.out.println("给用户发一条文本消息: " + sendTxtMessageusernode.toString());
	      return sendTxtMessageusernode.toString();
	    }
	    return null;
	}
	
	public static String sendMsg(String user,String message){
	    String from = "fuwuqi";
	    String targetTypeus = "users";
	    ObjectNode ext = JsonNodeFactory.instance.objectNode();
	    ArrayNode targetusers = JsonNodeFactory.instance.arrayNode();
	    targetusers.add(user);

	    ObjectNode txtmsg = JsonNodeFactory.instance.objectNode();
	    txtmsg.put("msg", message.toString());
	    txtmsg.put("type", "txt");
//	    ext.put("message", message.toString());
	    
	    ObjectNode sendTxtMessageusernode = EasemobMessages.sendMessages(targetTypeus, targetusers, txtmsg, from, ext);
	    if (null != sendTxtMessageusernode) {
	      System.out.println("给用户发一条文本消息: " + sendTxtMessageusernode.toString());
	      return sendTxtMessageusernode.toString();
	    }
	    return null;
	}
	
}
