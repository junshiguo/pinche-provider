package domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RatingPK implements Serializable {
	/**
	 * default number
	 */
	private static final long serialVersionUID = 1L;
	protected String userId;
	protected String orderId;
	
	public RatingPK(){}
	
	public RatingPK(String uid, String oid){
		this.userId = uid;
		this.orderId = oid;
	}

	@Override
	public int hashCode() {
		return userId.hashCode() * 13 + orderId.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof RatingPK)) return false;
		RatingPK toCompair = (RatingPK) obj;
		if(toCompair.orderId.equals(this.orderId) && toCompair.userId.equals(this.userId))
			return true;
		return false;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
