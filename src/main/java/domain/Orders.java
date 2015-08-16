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
	 * order time, if current time expires 1 minute than this time, a message should be sent
	 */
	Timestamp orderTime;
	/**
	 * same for both
	 */
	double savePercent;
	String route;
	String routeNames;
	
	public Orders(String orderId, String requestId1, String requestId2,
			Timestamp orderTime, double savePercent,
			String route, String routeNames) {
		super();
		this.orderId = orderId;
		this.requestId1 = requestId1;
		this.requestId2 = requestId2;
		this.orderTime = orderTime;
		this.savePercent = savePercent;
		this.route = route;
		this.routeNames = routeNames;
	}

	public JSONObject toQueryJson(){
		JSONObject order = new JSONObject();
		try {
			order.put("orderTime", orderTime);
			order.put("savePercent", savePercent);
			order.put("route", route);
			order.put("routeNames", routeNames);
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

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteNames() {
		return routeNames;
	}

	public void setRouteNames(String routeNames) {
		this.routeNames = routeNames;
	}

	
}
