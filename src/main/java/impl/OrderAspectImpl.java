package impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import domain.Orders;
import domain.OrdersActive;
import domain.Rating;
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
	public String getFinishedOrders(String phoneNumber) {
		JSONObject ret = new JSONObject();
		JSONObject detail = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		
		List queryResult = session.createQuery("from domain.Request  as o  where o.userId= ?").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			Request request = (Request) o;
			JSONObject jsontemp = new JSONObject();
	        if (request!= null){
				try{
		    		List queryResultTemp = session.createQuery("from domain.Orders as o where o.requestId1= ? or o.requestId2= ?").setString(0, phoneNumber).setString(1, phoneNumber).list();
		    		for(Object ot : queryResultTemp){
		    			Orders order = (Orders) ot;
		    			if (request.getRequestId().equals(order.getRequestId1()) || request.getRequestId().equals(order.getRequestId2())){	    		
					
		    				jsontemp.put("sourceName", request.getSourceName());
		    				jsontemp.put("destinationName", request.getDestinationName());
		    				jsontemp.put("state",request.getState());
		    				jsontemp.put("orderTime",order.getOrderTime());
					
		    				Rating ratingPrimaryKey= new Rating();
		    				ratingPrimaryKey.setOrderId(order.getOrderId());
		    				ratingPrimaryKey.setUserId(request.getUserId());
					
		    				Rating rating = (Rating) session.get(Rating.class, (Serializable) ratingPrimaryKey);
					
		    				if (rating != null) jsontemp.put("rating",rating.getRating());
		    				else jsontemp.put("rating",-1);				
					
		    				detail.put(request.getRequestId(),jsontemp);
		    				}
		    			}
		    		}catch (JSONException e) {				
					e.printStackTrace();
				    try {
				    	ret.put("status", -1);
				    	ret.put("message", "未知错误");
				    	ret.put("detail", e.toString());
				    	return ret.toString();
				    	} catch (JSONException e1) {
				    		e1.printStackTrace();
				    	}
				    }
				}
	        }

		session.close();
		return ret.toString();
	}
}
