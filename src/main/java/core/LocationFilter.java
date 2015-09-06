package core;

import java.util.ArrayList;

import domain.Request;

public class LocationFilter {
	public static double GRID_RADIUS = 10000.; //meters
	public static double ONE_LAT_LENGTH = 111319.0; //meters
	
	public static void main(String args[]){
		System.out.println(LocationFilter.getDirectDistance(0, 1, 0, 0));
	}
	
	/**
	 * split requests into different smaller cells. Cells are not strictly divided. Each request belongs to 4*4 cells.
	 * @param requests
	 * @return
	 */
	public static ArrayList<ArrayList<Request>> filterByLocation(ArrayList<Request> requests){
		//TODO
		double minLat = Double.MAX_VALUE, maxLat = Double.MIN_VALUE;
		double minLng = Double.MAX_VALUE, maxLng = Double.MIN_VALUE;
		for(Request ra : requests){
			if(ra.getSourceX() < minLat)	minLat = ra.getSourceX();
			if(ra.getDestinationX() < minLat) 	minLat = ra.getDestinationX();
			if(ra.getSourceX() > maxLat)	maxLat = ra.getSourceX();
			if(ra.getDestinationX() > maxLat)	maxLat = ra.getDestinationX();
			if(ra.getSourceY() < minLng)	minLng = ra.getSourceY();
			if(ra.getDestinationY() < minLng)	minLng = ra.getDestinationY();
			if(ra.getSourceY() > maxLng)	maxLng = ra.getSourceY();
			if(ra.getDestinationY() > maxLng)	maxLng = ra.getDestinationY();
		}
		double radiusLng = GRID_RADIUS / getDirectDistance((maxLat+minLat)/2, 0, (maxLat+minLat)/2+1.0, 0);
		double radiusLat = GRID_RADIUS / ONE_LAT_LENGTH;
		int cellNumberLat = (int) (Math.ceil((maxLat - minLat)/radiusLat));
		int cellNumberLng = (int) (Math.ceil((maxLng - minLng)/radiusLng));
		int cellNumber = (cellNumberLat + 2) * (cellNumberLng + 2);
		ArrayList<ArrayList<ArrayList<Request>>> grid = new ArrayList<ArrayList<ArrayList<Request>>>();
		for(int srcIndex = 0; srcIndex < cellNumber; srcIndex++){
			ArrayList<ArrayList<Request>> srcRow = new ArrayList<ArrayList<Request>>();
			for (int destIndex = 0; destIndex < cellNumber; destIndex++) {
				srcRow.add(new ArrayList<Request>());
			}
			grid.add(srcRow);
		}
		for(Request ac : requests){
			int srcLatIndex = (int) Math.ceil((ac.getSourceX() - minLat)/radiusLat);
			int srcLngIndex = (int) Math.ceil((ac.getSourceY() - minLng)/radiusLng);
			int destLatIndex = (int) Math.ceil((ac.getDestinationX() - minLat)/radiusLat);
			int destLngIndex = (int) Math.ceil((ac.getDestinationY() - minLng)/radiusLng);
			ArrayList<Integer> srcIndex = new ArrayList<Integer>();
			srcIndex.add(srcLatIndex * cellNumberLng + srcLngIndex);
			srcIndex.add(srcLatIndex * cellNumberLng + srcLngIndex + 1);
			srcIndex.add((srcLatIndex + 1) * cellNumberLng + srcLngIndex);
			srcIndex.add((srcLatIndex+1)*cellNumberLng+srcLngIndex + 1);
			ArrayList<Integer> destIndex = new ArrayList<Integer>();
			destIndex.add(destLatIndex * cellNumberLng + destLngIndex);
			destIndex.add(destLatIndex * cellNumberLng + destLngIndex + 1);
			destIndex.add((destLatIndex + 1) * cellNumberLng + destLngIndex);
			destIndex.add((destLatIndex+1)*cellNumberLng+destLngIndex + 1);
			for(int src : srcIndex)
				for(int dest : destIndex)
					grid.get(src).get(dest).add(ac);
		}
		ArrayList<ArrayList<Request>> ret = new ArrayList<ArrayList<Request>>();
		for(ArrayList<ArrayList<Request>> samesrc : grid){
			for(ArrayList<Request> samecell : samesrc){
				if(samecell.size() > 1){
					ret.add(samecell);
				}
			}
		}
		return ret;
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
