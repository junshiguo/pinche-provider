package domain;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

public class Orders {
	
	String orderId;
	/**
	 * each order is related to two requests
	 */
	String requestId1;
	/**
	 * each order is related to two requests
	 */
	String requestId2;
	/**
	 * each order is related to two users
	 */
	String userId1;
	/**
	 * each order is related to two users
	 */
	String userId2;
	/**
	 * whether the first user has confirmed
	 */
	short confirmedUser1;
	/**
	 * whether the sencond user has confirmed
	 */
	short confirmedUser2;
	/**
	 * order time, if current time expires 1 minute than this time, a message should be sent
	 */
	Timestamp orderTime;
	/**
	 * same for both
	 */
	double savePercent;
	String routePoint1;
	String routePoint2;
	String routePoint3;
	String routePoint4;
	
	public JSONObject toQueryJson1(){
		JSONObject order = new JSONObject();
		try {
			order.put("confirmed", confirmedUser2);
			order.put("orderTime", orderTime);
			order.put("savePercent", savePercent);
			order.put("route", routePoint1+","+routePoint2+","+routePoint3+","+routePoint4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	public JSONObject toQueryJson2(){
		JSONObject order = new JSONObject();
		try {
			order.put("confirmed", confirmedUser1);
			order.put("orderTime", orderTime);
			order.put("savePercent", savePercent);
			order.put("route", routePoint1+","+routePoint2+","+routePoint3+","+routePoint4);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return order;
	}

	public Orders(){}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRequestId1() {
		return requestId1;
	}

	public void setRequestId1(String requestId1) {
		this.requestId1 = requestId1;
	}

	public String getRequestId2() {
		return requestId2;
	}

	public void setRequestId2(String requestId2) {
		this.requestId2 = requestId2;
	}

	public String getUserId1() {
		return userId1;
	}

	public void setUserId1(String userId1) {
		this.userId1 = userId1;
	}

	public String getUserId2() {
		return userId2;
	}

	public void setUserId2(String userId2) {
		this.userId2 = userId2;
	}

	public short getConfirmedUser1() {
		return confirmedUser1;
	}

	public void setConfirmedUser1(short confirmedUser1) {
		this.confirmedUser1 = confirmedUser1;
	}

	public short getConfirmedUser2() {
		return confirmedUser2;
	}

	public void setConfirmedUser2(short confirmedUser2) {
		this.confirmedUser2 = confirmedUser2;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public double getSavePercent() {
		return savePercent;
	}

	public void setSavePercent(double savePercent) {
		this.savePercent = savePercent;
	}

	public String getRoutePoint1() {
		return routePoint1;
	}

	public void setRoutePoint1(String routePoint1) {
		this.routePoint1 = routePoint1;
	}

	public String getRoutePoint2() {
		return routePoint2;
	}

	public void setRoutePoint2(String routePoint2) {
		this.routePoint2 = routePoint2;
	}

	public String getRoutePoint3() {
		return routePoint3;
	}

	public void setRoutePoint3(String routePoint3) {
		this.routePoint3 = routePoint3;
	}

	public String getRoutePoint4() {
		return routePoint4;
	}

	public void setRoutePoint4(String routePoint4) {
		this.routePoint4 = routePoint4;
	}
	
}
