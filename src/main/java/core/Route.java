package core;

import java.util.ArrayList;

import module.BaiduMapModule;

/**
 * one route is composed of 4 points
 * used in core.MatchMain
 * @author guojunshi
 *
 */
public class Route {
	String point1;
	String point2;
	String point3;
	String point4;
	double savePercent;
	
	/**
	 * automatically find the best route
	 * @param s1
	 * @param d1
	 * @param s2
	 * @param d2
	 */
	public Route(String s1, String d1, String s2, String d2){
		ArrayList<String> points = new ArrayList<String>();
		points.add(s1);
		points.add(d1);
		points.add(s2);
		points.add(d2);
		ArrayList<Long> distances = BaiduMapModule.getRouteMatrix(points, points);
		/*
		 *		s1	d1	s2	d2
		 * s1	0	1	2	3
		 * d1	4	5	6	7
		 * s2	8	9	10	11
		 * d2	12	13	14	15
		 */
		long distance11 = distances.get(1);
		long distance22 = distances.get(11);
		long[] routeDistance = new long[4];//s1-s2-d1-d2, s1-s2-d2-d1, s2-s1-d1-d2, s2-s1-d2-d1
		routeDistance[0] = distances.get(2)+distances.get(9)+distances.get(7); //s1-s2-d1-d2
		routeDistance[1] = distances.get(2)+distances.get(11)+distances.get(13); //s1-s2-d2-d1
		routeDistance[2] = distances.get(8)+distances.get(1)+distances.get(7); //s2-s1-d1-d2
		routeDistance[3] = distances.get(8)+distances.get(3)+distances.get(13); //s2-s1-d2-d1
		long min = Long.MAX_VALUE; 
		int minIndex = -1;
		for(int i = 0; i < 4; i++){
			if(min >= routeDistance[i]){
				min = routeDistance[i];
				minIndex = i;
			}
		}
		long originalDistance = distance11 + distance22;
		if(originalDistance != 0){
			this.savePercent = (originalDistance - routeDistance[minIndex]) / originalDistance;
		}
		switch(minIndex){
		case 0:
			this.point1 = s1;
			this.point2 = s2;
			this.point3 = d1;
			this.point4 = d2;
			break;
		case 1:
			this.point1 = s1;
			this.point2 = s2;
			this.point3 = d2;
			this.point4 = d1;
			break;
		case 2:
			this.point1 = s2;
			this.point2 = s1;
			this.point3 = d1;
			this.point4 = d2;
			break;
		case 3:
			this.point1 = s2;
			this.point2 = s1;
			this.point3 = d2;
			this.point4 = d1;
			break;
			default:
		}
	}
	
	public String getPoint1() {
		return point1;
	}
	public void setPoint1(String point1) {
		this.point1 = point1;
	}
	public String getPoint2() {
		return point2;
	}
	public void setPoint2(String point2) {
		this.point2 = point2;
	}
	public String getPoint3() {
		return point3;
	}
	public void setPoint3(String point3) {
		this.point3 = point3;
	}
	public String getPoint4() {
		return point4;
	}
	public void setPoint4(String point4) {
		this.point4 = point4;
	}

	public double getSavePercent() {
		return savePercent;
	}

	public void setSavePercent(double savePercent) {
		this.savePercent = savePercent;
	}
	
}
