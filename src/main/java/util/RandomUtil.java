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
	
	public static long hash(long val){
		return FNVhash64(val);
	}
	
	 public static final long FNV_offset_basis_64=0xCBF29CE484222325L;
     public static final long FNV_prime_64=1099511628211L;
     
     /**
      * 64 bit FNV hash. Produces more "random" hashes than (say) String.hashCode().
      * 
      * @param val The value to hash.
      * @return The hash value
      */
	public static long FNVhash64(long val) {
		// from http://en.wikipedia.org/wiki/Fowler_Noll_Vo_hash
		long hashval = FNV_offset_basis_64;

		for (int i = 0; i < 8; i++) {
			long octet = val & 0x00ff;
			val = val >> 8;

			hashval = hashval ^ octet;
			hashval = hashval * FNV_prime_64;
			// hashval = hashval ^ octet;
		}
		return Math.abs(hashval);
	}
	
}
