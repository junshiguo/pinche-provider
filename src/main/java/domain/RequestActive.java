package domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestActive {
	public static Byte STATE_NEW_REQUEST = 0;
	public static Byte STATE_OLD_REQUEST = 1;
	public static Byte STATE_HANDLING = 2;
	public static Byte STATE_ORDER_SUCCESS = 3;
	public static Byte STATE_NORMAL_CANCELED = 4;
	public static Byte STATE_CANCELED_AFTER_SUCCESS = 5;
	public static Byte STATE_CANCELED_BY_THE_OTHER = 6;
//	public static Byte STATE_SECOND_HANDLING = 7;
//	public static Byte STATE_TWO_REJECTIONS = 8;
//	public static Byte STATE_OLD_REQUEST = 9;
	public static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Byte DEFAULT_MAX_CHANCE = 2;
	
	/**
	 * return JsonObject format String, containing  sourceName, destinationName, leavingTime
	 */
	public JSONObject toQueryJson(){
		JSONObject ret = new JSONObject();
		try {
			ret.put("sourceName", sourceName);
			ret.put("destinationName", destinationName);
			ret.put("leavingTime", leavingTime);
			ret.put("state", state);
			ret.put("source", sourceX+","+sourceY);
			ret.put("destination", destinationX+","+destinationY);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ret;
	}
	public String sourceCoord(){
		return sourceX+","+sourceY;
	}
	public String destinationCoord(){
		return destinationX+","+destinationY;
	}
	
	String requestId;
	String userId;
	byte userGender;
	byte userAge;
	byte state;
	/**
	 * lat, weidu
	 */
	double sourceX;
	/**
	 * lng, jingdu
	 */
	double sourceY;
	String sourceName;
	double destinationX;
	double destinationY;
	String destinationName;
	Timestamp leavingTime;
	byte expGender;
	byte expAgeMin;
	byte expAgeMax;
	Timestamp requestTime;
	byte remainChance;
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public double getSourceX() {
		return sourceX;
	}
	public void setSourceX(double sourceX) {
		this.sourceX = sourceX;
	}
	public double getSourceY() {
		return sourceY;
	}
	public void setSourceY(double sourceY) {
		this.sourceY = sourceY;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public double getDestinationX() {
		return destinationX;
	}
	public void setDestinationX(double destinationX) {
		this.destinationX = destinationX;
	}
	public double getDestinationY() {
		return destinationY;
	}
	public void setDestinationY(double destinationY) {
		this.destinationY = destinationY;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	public Timestamp getLeavingTime() {
		return leavingTime;
	}
	public void setLeavingTime(Timestamp leavingTime) {
		this.leavingTime = leavingTime;
	}
	public byte getExpGender() {
		return expGender;
	}
	public void setExpGender(byte expGender) {
		this.expGender = expGender;
	}
	public byte getExpAgeMin() {
		return expAgeMin;
	}
	public void setExpAgeMin(byte expAgeMin) {
		this.expAgeMin = expAgeMin;
	}
	public byte getExpAgeMax() {
		return expAgeMax;
	}
	public void setExpAgeMax(byte expAgeMax) {
		this.expAgeMax = expAgeMax;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public byte getUserGender() {
		return userGender;
	}
	public void setUserGender(byte userGender) {
		this.userGender = userGender;
	}
	public byte getUserAge() {
		return userAge;
	}
	public void setUserAge(byte userAge) {
		this.userAge = userAge;
	}
	public byte getRemainChance() {
		return remainChance;
	}
	public void setRemainChance(byte remainChance) {
		this.remainChance = remainChance;
	}
	
}
