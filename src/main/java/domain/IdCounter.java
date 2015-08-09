package domain;

public class IdCounter {
	public static String ORDER_ID_KEY = "order_id_counter";
	public static String REQUEST_ID_KEY = "request_id_counter";
	
	String idKey;
	long idValue;
	
	public String getIdKey() {
		return idKey;
	}
	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}
	public long getIdValue() {
		return idValue;
	}
	public void setIdValue(long idValue) {
		this.idValue = idValue;
	}

}
