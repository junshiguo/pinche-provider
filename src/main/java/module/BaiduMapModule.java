package module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.HttpClientUtil;

public class BaiduMapModule {
	public static void main(String[] args) throws JSONException {
		ArrayList<Long> ret = getRouteMatrix("39.915285,116.403857%7C东方明珠", "40.056878,116.30815");
		for(long l : ret){
			System.out.println(l);
		}
	}
	
	public static String ROUTE_MATRIX_URL = "http://api.map.baidu.com/direction/v1/routematrix";
	/**
	 * the access key authorized by baidu. Each key can process 100,000 queries per day.
	 */
	public static String ak = "byzNowlMyh8UFe2fMyqKUL9f";
	
	/**
	 * 
	 * @param origins one origin point represented by lat<纬度>,lng<经度>.
	 * @param destinations one destination point represented by lat<纬度>,lng<经度>.
	 * @return the distance between the two points
	 */
	public static long getP2PDistance(String origin, String destination){
		Map<String, String> params = new HashMap<String, String>();
		params.put("origins", origin);
		params.put("destinations", destination);
		params.put("output", "json");
		params.put("ak", ak);
		String results = HttpClientUtil.get(ROUTE_MATRIX_URL, params);
		long routeDistance = 0;
		try {
			JSONObject jsonResult = new JSONObject(results);
			JSONArray elements = jsonResult.getJSONObject("result").getJSONArray("elements");
			for (int i = 0; i < elements.length(); i++) {
				JSONObject distance = elements.getJSONObject(i).getJSONObject("distance");
				routeDistance = distance.getLong("value");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routeDistance;
	}

	/**
	 * 
	 * @param origins a list of origin points. Each point is represented by lat<纬度>,lng<经度>.
	 * @param destinations a list of destination points. Each point is represented by lat<纬度>,lng<经度>.
	 * @return a list of distance by meters. this is a one-dimension representation of the two-dimensional distance matrix
	 */
	public static ArrayList<Long> getRouteMatrix(ArrayList<String> origins, ArrayList<String> destinations){
		if(origins.size() < 1 || destinations.size() < 1)	return null;
		String originsStr = "", destinationsStr = "";
		for(String o : origins)
			originsStr += o + "%7C";
		originsStr = originsStr.substring(0, originsStr.length()-3);
		for(String d : destinations)
			destinationsStr += d + "%7C";
		destinationsStr = destinationsStr.substring(0, destinationsStr.length()-3);
		return getRouteMatrix(originsStr, destinationsStr);
	}
	/**
	 * call baidu map route matrix api. Use default driving mode and shortest time tactics.
	 * points can be represented by Chinese characters. not suggested though.
	 * @param origins a string of up to 5 origins, each represented by lat<纬度>,lng<经度> and divided by %7C which is url code for "|"
	 * @param destinations same as origins
	 * @return a list of distance by meters. this is a one-dimension representation of the two-dimensional distance matrix
	 */
	public static ArrayList<Long> getRouteMatrix(String origins, String destinations){
		Map<String, String> params = new HashMap<String, String>();
		params.put("origins", origins);
		params.put("destinations", destinations);
		params.put("output", "json");
		params.put("ak", ak);
		String results = HttpClientUtil.get(ROUTE_MATRIX_URL, params);
		ArrayList<Long> routeDistance = new ArrayList<Long>();
		try {
			JSONObject jsonResult = new JSONObject(results);
			JSONArray elements = jsonResult.getJSONObject("result").getJSONArray("elements");
			for (int i = 0; i < elements.length(); i++) {
				JSONObject distance = elements.getJSONObject(i).getJSONObject("distance");
				routeDistance.add(distance.getLong("value"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return routeDistance;
	}
	
}
