package domain;

import java.sql.Timestamp;

public class Orders {
//	public static int STATE_SEARCHING = 0;
//	public static int STATE_MATCHED = 1;
//	public static int STATE_CANCELED = 2;
	
	String orderId;
	/**
	 * the user who gives the order
	 */
	String userId;
	/**
	 * maybe null when the request failed
	 */
	int partnerId;
	/**
	 * the order state may be searching, matched or canceled
	 */
	int state;
	/**
	 * the source and destination location, each represented by X and Y axises
	 */
	float sourceX;
	float sourceY;
	float destinationX;
	float destinationY;
	Timestamp requestTime;

	public Orders(){}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getSourceX() {
		return sourceX;
	}

	public void setSourceX(float sourceX) {
		this.sourceX = sourceX;
	}

	public float getSourceY() {
		return sourceY;
	}

	public void setSourceY(float sourceY) {
		this.sourceY = sourceY;
	}

	public float getDestinationX() {
		return destinationX;
	}

	public void setDestinationX(float destinationX) {
		this.destinationX = destinationX;
	}

	public float getDestinationY() {
		return destinationY;
	}

	public void setDestinationY(float destinationY) {
		this.destinationY = destinationY;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp time) {
		this.requestTime = time;
	};
	
}
