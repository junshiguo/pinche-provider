package core;

import java.util.ArrayList;

import domain.RequestActive;

public class LocationFilter {
	public static double GRID_RADIUS = 5000.; //meters
	public static double ONE_LAT_LENGTH = 111319.0; //meters
	
	public static void main(String args[]){
		System.out.println(LocationFilter.getDirectDistance(0, 1, 0, 0));
	}
	
	public static ArrayList<ArrayList<ArrayList<RequestActive>>> filterByLocation(ArrayList<RequestActive> requests){
		//TODO
		double minLat = Double.MAX_VALUE, maxLat = Double.MIN_VALUE;
		double minLng = Double.MAX_VALUE, maxLng = Double.MIN_VALUE;
		for(RequestActive ra : requests){
			if(ra.getSourceX() < minLat)	minLat = ra.getSourceX();
			if(ra.getDestinationX() < minLat) 	minLat = ra.getDestinationX();
			if(ra.getSourceY() > maxLat)	maxLat = ra.getSourceY();
			if(ra.getDestinationY() > maxLat)	maxLat = ra.getDestinationY();
			if(ra.getSourceY() < minLng)	minLng = ra.getSourceY();
			if(ra.getDestinationY() < minLng)	minLng = ra.getDestinationY();
			if(ra.getSourceY() > maxLng)	maxLng = ra.getSourceY();
			if(ra.getDestinationY() > maxLng)	maxLng = ra.getDestinationY();
		}
		double radiusLng = GRID_RADIUS / getDirectDistance((maxLat+minLat)/2, 0, (maxLat+minLat)/2+1.0, 0);
		double radiusLat = GRID_RADIUS / ONE_LAT_LENGTH;
		int cellNumberLat = (int) (Math.ceil(maxLat - minLat)/radiusLat);
		int cellNumberLng = (int) (Math.ceil(maxLng - minLng)/radiusLng);
		ArrayList<ArrayList<ArrayList<RequestActive>>> grid = new ArrayList<ArrayList<ArrayList<RequestActive>>>();
		for(int srcIndex = 0; srcIndex < cellNumberLng*cellNumberLat; srcIndex++){
			ArrayList<ArrayList<RequestActive>> srcRow = new ArrayList<ArrayList<RequestActive>>();
			for(int destIndex = 0; destIndex < cellNumberLng*cellNumberLat; destIndex++){
				srcRow.add(new ArrayList<RequestActive>());
			}
			grid.add(srcRow);
		}
		for(RequestActive ac : requests){
			int srcLatIndex = (int) Math.ceil((ac.getSourceX() - minLat)/radiusLat);
			int srcLngIndex = (int) Math.ceil((ac.getSourceY() - minLng)/radiusLng);
			int destLatIndex = (int) Math.ceil((ac.getDestinationX() - minLat)/radiusLat);
			int destLngIndex = (int) Math.ceil((ac.getDestinationY() - minLng)/radiusLng);
			grid.get(srcLatIndex*cellNumberLng+srcLngIndex).get(destLatIndex*cellNumberLng+destLngIndex).add(ac);
		}
		return grid;
	}
	
	/** 
	 * google maps的脚本里代码 
	 */    
	private static double EARTH_RADIUS = 6378137; 
	private static double rad(double d) 
	{ 
	     return d * Math.PI / 180.0; 
	}  
	/** 
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米 
	 */ 
	public static double getDirectDistance(double lat1, double lng1, double lat2, double lng2) 
	{ 
	    double radLat1 = rad(lat1); 
	    double radLat2 = rad(lat2); 
	    double a = radLat1 - radLat2; 
	    double b = rad(lng1) - rad(lng2); 
	    double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))); 
	    s = s * EARTH_RADIUS; 
	    s = Math.round(s * 10000) / 10000; 
	    return s; 
	} 
	
}
