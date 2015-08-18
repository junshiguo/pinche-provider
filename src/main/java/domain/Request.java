package domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {
	public static final Byte STATE_NEW_REQUEST = 0;
	public static final Byte STATE_OLD_REQUEST = 1;
	public static final Byte STATE_HANDLING = 2; //should not appear
	public static final Byte STATE_ORDER_SUCCESS = 3;
	public static final Byte STATE_NORMAL_CANCELED = 4;
	public static final Byte STATE_CANCELED_AFTER_SUCCESS = 5;
	public static final Byte STATE_CANCELED_BY_THE_OTHER = 6;
	public static final Byte STATE_TOO_MANY_REJECTS = 7;
	public static final Byte STATE_ME_NC_PARTNER_NC = 20;
	public static final Byte STATE_ME_NC_PARTNER_C = 21;
	public static final Byte STATE_ME_NC_PARTNER_R = 22;
	public static final Byte STATE_ME_C_PARTNER_NC = 23;
	public static final Byte STATE_ME_C_PARTNER_C = 24;//before ORDER_SUCCESS
	public static final Byte STATE_ME_C_PARTNER_R = 25;
	public static final Byte STATE_ME_R_PARTNER_NC = 26; //
	public static final Byte STATE_ME_R_PARTNER_C = 27;//
	public static final Byte STATE_ME_R_PARTNER_R = 28;//
	
	public static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * return JsonObject format String, containing  sourceName, destinationName, leavingTime
	 */
	public JSONObject toQueryJson(){
		JSONObject ret = new JSONObject();
		try {
			ret.put("sourceName", sourceName);
			ret.put("destinationName", destinationName);
			ret.put("leavingTime", leavingTime);
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
	 * lat weidu
	 */
	double sourceX;
	/**
	 * lng jingdu
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
	
	public Request(RequestActive re){
		this.requestId = re.getRequestId();
		this.userId = re.getUserId();
		this.userGender = re.getUserGender();
		this.userAge = re.getUserAge();
		this.state = re.getState();
		this.sourceX = re.getSourceX();
		this.sourceY = re.getSourceY();
		this.sourceName = re.getSourceName();
		this.destinationX = re.getDestinationX();
		this.destinationY = re.getDestinationY();
		this.destinationName = re.getDestinationName();
		this.leavingTime = re.getLeavingTime();
		this.expGender = re.getExpGender();
		this.expAgeMin = re.getExpAgeMin();
		this.expAgeMax = re.getExpAgeMax();
		this.remainChance = re.getRemainChance();
		this.requestTime = re.getRequestTime();
	}
	public Request(){}
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
