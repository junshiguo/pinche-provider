package impl;

import java.util.List;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Orders;
import domain.OrdersActive;
import domain.Request;
import domain.RequestActive;
import domain.User;
import interf.OrdersAspect;
import test.MySessionFactory;

public class OrderAspectImpl implements OrdersAspect{

	/**
	 * 获取账号为phoneNumber的所有订单，包括已完成和未完成
	 * status = 1, 返回成功
	 * status = -1, 未知错误
	 * @param phoneNumber
	 * @return　JSONObject格式字符串，包含status, message, detail
	 */
	public String getAllOrders(String phoneNumber) {
		JSONObject ret = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);

		List queryResult = session.createQuery("from domain.Orders as o where o.userId1= ?").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			Orders order = (Orders) o;
			JSONObject res = new JSONObject();
			String requestid1 = order.getRequestId1();
			try{
		    	Request request = (Request) session.get(Request.class, requestid1);
			    if (request!= null){
				    res.put("sourceName", request.getSourceName());
				    res.put("destinationName", request.getDestinationName());
		    	}else{
		    		RequestActive requestActive = (RequestActive) session.get(RequestActive.class, requestid1);
		    		if (requestActive!= null){
			    		res.put("sourceName", requestActive.getSourceName());
		    			res.put("destinationName", requestActive.getDestinationName());
		    		}					
	    		}
		   // add order status;
			ret.put(order.getOrderId(),res);
			}catch (JSONException e) {
				e.printStackTrace();
			}
		

		
	
		
		return null;
	}

}
