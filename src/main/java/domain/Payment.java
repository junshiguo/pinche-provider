package domain;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

public class Payment {
	public static final int DEFAULT_DEPOSIT = 1000;
	
	String chargeId;
	String requestId;
	String userId;
	int deposit;
	int tip;
	int deduction;
	byte returned;
	Timestamp payTime;
	Timestamp expRefundTime;
	Timestamp refundTime;
	
	public JSONObject toQueryJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("chargeId", chargeId);
			json.put("deposit", deposit);
			json.put("tip", tip);
			json.put("deduction", deduction);
			json.put("returned", returned);
			json.put("payTime", payTime);
			json.put("expRefundTime", expRefundTime);
			json.put("refundTime", refundTime);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	public Payment(){}

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
		return returned;
	}

	public void setReturned(byte returned) {
		this.returned = returned;
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
	

}
