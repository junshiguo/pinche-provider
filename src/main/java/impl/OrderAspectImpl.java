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
		JSONObject detail = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
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
				    res.put("state",request.getState());
		    	}else{
		    		RequestActive requestActive = (RequestActive) session.get(RequestActive.class, requestid1);
		    		if (requestActive!= null){
			    		res.put("sourceName", requestActive.getSourceName());
		    			res.put("destinationName", requestActive.getDestinationName());
					    res.put("state",requestActive.getState());
		    		}					
	    		}
			detail.put(order.getOrderId(),res);
			}catch (JSONException e) {
				e.printStackTrace();
				try {
					ret.put("status", -1);
					ret.put("message", "未知错误");
					ret.put("detail", e.toString());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		queryResult = session.createQuery("from domain.Orders as o where o.userId2= ?").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			Orders order = (Orders) o;
			JSONObject res = new JSONObject();
			String requestid2 = order.getRequestId2();
			try{
		    	Request request = (Request) session.get(Request.class, requestid2);
			    if (request!= null){
				    res.put("sourceName", request.getSourceName());
				    res.put("destinationName", request.getDestinationName());
				    res.put("state",request.getState());
		    	}else{
		    		RequestActive requestActive = (RequestActive) session.get(RequestActive.class, requestid2);
		    		if (requestActive!= null){
			    		res.put("sourceName", requestActive.getSourceName());
		    			res.put("destinationName", requestActive.getDestinationName());
					    res.put("state",requestActive.getState());
		    		}					
	    		}
			detail.put(order.getOrderId(),res);
			}catch (JSONException e) {
				e.printStackTrace();
				try {
					ret.put("status", -1);
					ret.put("message", "未知错误");
					ret.put("detail", e.toString());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		queryResult = session.createQuery("from domain.OrdersActive as o where o.userId1= ?").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			OrdersActive orderActive = (OrdersActive) o;
			JSONObject res = new JSONObject();
			String requestid1 = orderActive.getRequestId1();
			try{
		    	Request request = (Request) session.get(Request.class, requestid1);
			    if (request!= null){
				    res.put("sourceName", request.getSourceName());
				    res.put("destinationName", request.getDestinationName());
				    res.put("state",request.getState());
		    	}else{
		    		RequestActive requestActive = (RequestActive) session.get(RequestActive.class, requestid1);
		    		if (requestActive!= null){
			    		res.put("sourceName", requestActive.getSourceName());
		    			res.put("destinationName", requestActive.getDestinationName());
					    res.put("state",requestActive.getState());
		    		}					
	    		}
			detail.put(orderActive.getOrderId(),res);
			}catch (JSONException e) {
				e.printStackTrace();
				try {
					ret.put("status", -1);
					ret.put("message", "未知错误");
					ret.put("detail", e.toString());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		queryResult = session.createQuery("from domain.OrdersActive as o where o.userId2= ?").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			OrdersActive orderActive = (OrdersActive) o;
			JSONObject res = new JSONObject();
			String requestid2 = orderActive.getRequestId2();
			try{
		    	Request request = (Request) session.get(Request.class, requestid2);
			    if (request!= null){
				    res.put("sourceName", request.getSourceName());
				    res.put("destinationName", request.getDestinationName());
				    res.put("state",request.getState());
		    	}else{
		    		RequestActive requestActive = (RequestActive) session.get(RequestActive.class, requestid2);
		    		if (requestActive!= null){
			    		res.put("sourceName", requestActive.getSourceName());
		    			res.put("destinationName", requestActive.getDestinationName());
					    res.put("state",requestActive.getState());
		    		}					
	    		}
			detail.put(orderActive.getOrderId(),res);
			}catch (JSONException e) {
				e.printStackTrace();
				try {
					ret.put("status", -1);
					ret.put("message", "未知错误");
					ret.put("detail", e.toString());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		try{
			ret.put("status",1);
			ret.put("message","成功");
			ret.put("detail",detail);
		}catch(JSONException e){
			e.printStackTrace();
		}
		return ret.toString();
	}

}
