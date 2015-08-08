package util;

import java.util.Random;

public class RandomUtil {
	/**
	 * generate six digital verify code
	 * @return
	 */
	public static String randomVerifyCode(){
		int length = 6;
		Random ran = new Random(System.currentTimeMillis());
		String code = "";
		for(int i = 0; i < length; i++){
			code += ran.nextInt(10);
		}
		return code;
	}
	
	public static int currentRequestId = 10;
	public static String randomRequestId(){
		//TODO
		return ""+(currentRequestId++);
	}
	
	public static int currentOrderId = 1;
	public static String randomOrderId(){
		//TODO
		return ""+(currentOrderId++);
	}
	
}
