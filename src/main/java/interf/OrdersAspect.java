package interf;

public interface OrdersAspect {
	/**
	 * 获取账号为phoneNumber的所有正在进行中的请求
	 * status = 1, 返回成功
	 * status = -1, 未知错误
	 * @param phoneNumber
	 * @return　JSONObject格式字符串，包含status, message, detail	   */
	String getActiveRequest(String phoneNumber);
	
	/**
	 * 获取账号为phoneNumber的所有订单，包括已完成和未完成
	 * status = 1, 返回成功
	 * status = -1, 未知错误
	 * @param phoneNumber
	 * @return　JSONObject格式字符串，包含status, message, detail
	 */
	String getFinishedOrders(String phoneNumber) ;
	
	/**
	 * 
	 * @param phoneNumber
	 * @param orderId
	 * @return
	 */
	String queryOrder(String phoneNumber, String orderId);
	
	/**
	 * 
	 * @param phoneNumber
	 * @param orderId
	 * @return
	 */
	String addRating(String orderId, String myPhoneNumber, String partnerPhoneNumber, int rating);

}
