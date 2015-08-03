package module;

import java.util.HashMap;
import java.util.Map;

import util.HttpClientUtil;

public class MapModule {
	public static void main(String[] args){
		System.out.println(getRouteMatrix());
	}
	
	public static String ROUTE_MATRIX_URL = "http://api.map.baidu.com/direction/v1/routematrix";
	public static String ak = "byzNowlMyh8UFe2fMyqKUL9f";
	
	public static String getRouteMatrix(){
		 Map<String, String> params = new HashMap<String, String>();
		 params.put("origins", "39.915285,116.403857");
		 params.put("destinations", "40.056878,116.30815");
		 params.put("output", "xml");
		 params.put("ak", ak);
		 return HttpClientUtil.get(ROUTE_MATRIX_URL, params);
	}

}
