package module;

public class MessageNode {
	public static final int TYPE_VERIFYCODE = 1;
	public static final int TYPE_MATCH_SUCCESS = 2;
	public static final int TYPE_PARTNER_PHONE = 3;
	public static final int TYPE_REJECTION = 4;
	int type;
	String verifycode;
	String phone;
	String orderId;
	String mobile;

	public MessageNode(String mobile, String verifycode) {
		this.type = TYPE_VERIFYCODE;
		this.mobile = mobile;
		this.verifycode = verifycode;
	}

	public MessageNode(String mobile) {
		this.type = TYPE_MATCH_SUCCESS;
		this.mobile = mobile;
	}

	public MessageNode(String mobile, String orderId, String phone) {
		this.type = TYPE_PARTNER_PHONE;
		this.mobile = mobile;
		this.orderId = orderId;
		this.phone = phone;
	}
	
	public MessageNode(String mobile, String orderId, int type){
		this.type = type;
		this.mobile = mobile;
		this.orderId = orderId;
	}
}
