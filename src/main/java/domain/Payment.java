package domain;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment {
	public static final int DEFAULT_DEPOSIT = 1000;
	public static final byte STATE_WAIT_FOR_PAYMENT= 0;
	public static final byte STATE_PAID = 1;
	public static final byte STATE_WAIT_REFUNDING = 2;
	public static final byte STATE_REFUNDED = 3;
	
	String requestId;
	String chargeId;
	String refundId;
	String userId;
	int deposit;
	int tip;
	int deduction;
	byte state;
	Timestamp payTime;
	Timestamp expRefundTime;
	Timestamp refundTime;
	Timestamp refundFinishTime;
	
	public JSONObject toQueryJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("chargeId", chargeId);
			json.put("deposit", deposit);
			json.put("tip", tip);
			json.put("deduction", deduction);
			if(state == STATE_REFUNDED){
				json.put("returned", 1);
			}else{
				json.put("returned", 0);
			}
			json.put("payTime", payTime);
			json.put("expRefundTime", expRefundTime);
			json.put("refundTime", refundTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	public Payment(){}
	

	public Payment(String requestId, String userId, int deposit, int tip, int deduction, byte state) {
		super();
		this.chargeId = null;
		this.requestId = requestId;
		this.refundId = null;
		this.userId = userId;
		this.deposit = deposit;
		this.tip = tip;
		this.deduction = deduction;
		this.state = state;
		this.payTime = null;
		this.expRefundTime = null;
		this.refundTime = null;
		this.refundFinishTime = null;
	}
	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

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

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public int getDeduction() {
		return deduction;
	}

	public void setDeduction(int deduction) {
		this.deduction = deduction;
	}

	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}

	public byte getReturned() {
		return state;
	}

	public void setReturned(byte returned) {
		this.state = returned;
	}

	public Timestamp getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Timestamp refundTime) {
		this.refundTime = refundTime;
	}

	public Timestamp getExpRefundTime() {
		return expRefundTime;
	}

	public void setExpRefundTime(Timestamp expRefundTime) {
		this.expRefundTime = expRefundTime;
	}
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public Timestamp getRefundFinishTime() {
		return refundFinishTime;
	}
	public void setRefundFinishTime(Timestamp refundFinishTime) {
		this.refundFinishTime = refundFinishTime;
	}
	

}
