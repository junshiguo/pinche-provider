package util;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
	
	public static JSONObject buildJson(int status, String message, String detail){
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("message", message);
			ret.put("detail", detail);
		} catch (JSONException e) {
		};
		return ret;
	}
	
	public static JSONObject buildJson(int status, String message, JSONObject detail){
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("message", message);
			ret.put("detail", detail);
		} catch (JSONException e) {
		};
		return ret;
	}

}
