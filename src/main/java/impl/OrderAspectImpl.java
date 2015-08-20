package impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.Order;

import org.hibernate.Session;
import org.json.JSONArray;
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
	 * 获取账号为phoneNumber的所有正在进行中的请求
	 * status = 1, 返回成功
	 * status = -1, 未知错误
	 * @param phoneNumber
	 * @return　JSONObject格式字符串，包含status, message, detail	   */
	public String getActiveRequest(String phoneNumber){
		JSONObject ret = new JSONObject();
//		JSONObject detail = new JSONObject();
		JSONArray JArray=new JSONArray();	
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
	
		List queryResult = session.createQuery("from domain.RequestActive as r where r.userId= ? order by r.requestTime desc").setString(0, phoneNumber).list();
		for(Object o : queryResult){
			RequestActive requestActive = (RequestActive) o;
			JSONObject jsontemp = new JSONObject();
	        if (requestActive!= null && requestActive.getState()!=4){
				try{
					jsontemp.put("requestId",requestActive.getRequestId());
					jsontemp.put("sourceName", requestActive.getSourceName());
					jsontemp.put("destinationName", requestActive.getDestinationName());
					jsontemp.put("status",requestActive.getState());
					jsontemp.put("orderTime",requestActive.getRequestTime());
					JArray.put(jsontemp);
				}catch (JSONException e) {				
					e.printStackTrace();
				    try {
				    	ret.put("status", -1);
				    	ret.put("message", e.toString());
				    	ret.put("detail", new JSONArray());
				    	return ret.toString();
				    	} catch (JSONException e1) {
				    		e1.printStackTrace();
				    	}
				    }
				}
	        }
		queryResult = session.createQuery("from domain.Request as r where r.userId= ? and r.state = ? order by r.requestTime desc").setString(0, phoneNumber).setByte(1, Request.STATE_ME_C_PARTNER_C).list();
		for(Object o : queryResult){
			Request request = (Request) o;
			JSONObject jsontemp = new JSONObject();
	        if (request!= null){
	    		List queryResultTemp = session.createQuery("from domain.Orders as oa where oa.requestId1= ? or oa.requestId2= ?").setString(0, request.getRequestId()).setString(1, request.getRequestId()).list();
	    		for(Object ot : queryResultTemp){
	    			Orders order = (Orders) ot;
	    			if (request.getRequestId().equals(order.getRequestId1()) || request.getRequestId().equals(order.getRequestId2())){	    		
	    				try{
	    					jsontemp.put("requestId",request.getRequestId());
	    					jsontemp.put("sourceName", request.getSourceName());
	    					jsontemp.put("destinationName", request.getDestinationName());
	    					jsontemp.put("status",request.getState());
	    					jsontemp.put("orderTime",request.getRequestTime());
	    					JArray.put(jsontemp);
	     				}catch (JSONException e) {				
	    					e.printStackTrace();
	    					try {
	    						ret.put("status", -1);
	    						ret.put("message", e.toString());
	    						ret.put("detail", new JSONArray());
	    						return ret.toString();
	    					} catch (JSONException e1) {
	    						e1.printStackTrace();
	    					}
	    				}
	    			}
	    		}
	        }
		}
		try{
			ret.put("status",1);
			ret.put("message","成功");
			ret.put("detail",JArray);
		}catch(JSONException e){
			e.printStackTrace();
		}
		session.close();
		return ret.toString();
	}

	/**
	 * 获取账号为phoneNumber的所有订单，包括已完成和未完成
	 * status = 1, 返回成功
	 * status = -1, 未知错误
	 * @param phoneNumber
	 * @return　JSONObject格式字符串，包含status, message, detail
	 */
	@SuppressWarnings("unchecked")
	public String getFinishedOrders(String phoneNumber) {
		JSONObject ret = new JSONObject();
//		JSONObject detail = new JSONObject();
		JSONArray JArray=new JSONArray();	
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		
		List<Request> queryResult = session
				.createQuery(
						"from domain.Request  as r  where r.userId= ? and (r.state = ? or r.state = ? or r.state = ?) order by r.requestTime desc")
				.setString(0, phoneNumber)
				.setByte(1, Request.STATE_ORDER_SUCCESS)
				.setByte(2, Request.STATE_CANCELED_AFTER_SUCCESS)
				.setByte(3, Request.STATE_CANCELED_BY_THE_OTHER).list();
		for(Object o : queryResult){
			Request request = (Request) o;
			JSONObject jsontemp = new JSONObject();
	        if (request!= null){
				try{
		    		List queryResultTemp = session.createQuery("from domain.Orders as o where o.requestId1= ? or o.requestId2= ?").setString(0, request.getRequestId()).setString(1, request.getRequestId()).list();
		    		for(Object ot : queryResultTemp){
		    			Orders order = (Orders) ot;
		    			if (request.getRequestId().equals(order.getRequestId1()) || request.getRequestId().equals(order.getRequestId2())){	    		
		    				jsontemp.put("requestId",request.getRequestId());					
		    				jsontemp.put("sourceName", request.getSourceName());
		    				jsontemp.put("destinationName", request.getDestinationName());
		    				jsontemp.put("status",request.getState());
		    				jsontemp.put("orderTime",request.getRequestTime());
		    				List queryTemp = session.createQuery("from domain.Rating  as r where r.commentorId= ? and r.orderId= ?").setString(0, request.getUserId()).setString(1, order.getOrderId()).list();
		    				Rating rating=null;
		    				for(Object or : queryTemp){
				    			rating = (Rating) or;
  				    		}
//		    				System.out.println(request.getRequestId());
		    				if (rating != null) jsontemp.put("rating",(int)rating.getRating());
		    				else jsontemp.put("rating",-1);				
					
	    					JArray.put(jsontemp);
		    				}
		    			}
		    		}catch (JSONException e) {				
					e.printStackTrace();
				    try {
				    	ret.put("status", -1);
						ret.put("message", e.toString());
						ret.put("detail", new JSONArray());
				    	return ret.toString();
				    	} catch (JSONException e1) {
				    		e1.printStackTrace();
				    	}
				    }
				}
	        }
		try{
			ret.put("status",1);
			ret.put("message","成功");
			ret.put("detail",JArray);
		}catch(JSONException e){
			e.printStackTrace();
		}
		session.close();
		return ret.toString();
	}
	
	/**
	 * 
	 * @param phoneNumber
	 * @param orderId
	 * @return
	 */
	public String queryOrder(String phoneNumber, String requestId){
		JSONObject ret = new JSONObject();
		JSONObject detail = new JSONObject();	
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		
		List queryResult = session.createQuery("from domain.Orders  as o  where o.requestId1= ? or o.requestId2= ?").setString(0, requestId).setString(1, requestId).list();
		if(queryResult.size()==0){
			ret=util.Util.buildJson(-1, "找不到与用户或请求相对应的订单", "");
			return ret.toString();
		}
		Orders order=(Orders) queryResult.get(0);
		Request myRequest=new Request();
		User partner=new User();
		Rating rating=null;
		
		if (order.getUserId1().equals(phoneNumber)) {
			myRequest = (Request) session.get(Request.class, order.getRequestId1());
			partner = (User) session.get(User.class, order.getUserId2());
			List queryTemp = session.createQuery("from domain.Rating  as r where r.commentorId= ? and r.orderId= ?").setString(0, phoneNumber).setString(1, order.getOrderId()).list();
			for(Object or : queryTemp){
    			rating = (Rating) or;
	    		}
		}else if (order.getUserId2().equals(phoneNumber)) {
			myRequest = (Request) session.get(Request.class, order.getRequestId2());
			partner = (User) session.get(User.class, order.getUserId1());
			List queryTemp = session.createQuery("from domain.Rating  as r where r.commentorId= ? and r.orderId= ?").setString(0, phoneNumber).setString(1, order.getOrderId()).list();
			for(Object or : queryTemp){
    			rating = (Rating) or;
	    		}
		}
		JSONObject jsonMe = new JSONObject();	
		JSONObject jsonPartner = new JSONObject();	
		try{
			detail.put("orderId", order.getOrderId());
			if (myRequest != null) {
				jsonMe.put("sourceName", myRequest.getSourceName());
				jsonMe.put("destinationName", myRequest.getDestinationName());
				jsonMe.put("leavingTime",myRequest.getLeavingTime());
			}
			if (partner != null) {
				jsonPartner.put("name", partner.getName());
				jsonPartner.put("phoneNumber", partner.getPhoneNumber());
				jsonPartner.put("photo", partner.getPhoto());
			}
			detail.put("me", jsonMe);
			detail.put("partner", jsonPartner);
			if (rating != null) {
				detail.put("rating", (int) rating.getRating());
			}else{
				detail.put("rating", -1);				
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		ret=util.Util.buildJson(1, "成功", detail);
		session.close();
		return ret.toString();
	}
	
	public String addRating(String orderId, String myPhoneNumber, String partnerPhoneNumber, int rating){
		JSONObject ret = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();

		List queryTemp = session.createQuery("from domain.Rating  as r where r.commentorId= ? and r.orderId= ?").setString(0, myPhoneNumber).setString(1, orderId).list();
		if (queryTemp.isEmpty() == false){
			ret=util.Util.buildJson(-1, "已进行评价", "");
		}else{
			Rating ratingObject = new Rating();
			ratingObject.setOrderId(orderId);
			ratingObject.setCommentorId(myPhoneNumber);
			ratingObject.setUserId(partnerPhoneNumber);
			ratingObject.setRating((byte) rating);
			ratingObject.setRatingTime(new Timestamp(System.currentTimeMillis()));

			session.save(ratingObject);
			session.getTransaction().commit();
			
			ret=util.Util.buildJson(1, "成功", "");
		}
		session.close();
		return ret.toString();
		
	}

}
