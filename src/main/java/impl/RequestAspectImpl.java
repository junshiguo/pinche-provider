package impl;

import java.sql.Timestamp;
import java.util.List;

import module.EasemodMsgModule;
import module.Notifier;
import module.PingxxPaymentModule;

import javax.persistence.criteria.Order;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;

import domain.Orders;
import domain.OrdersActive;
import domain.Request;
import domain.RequestActive;
import domain.User;
import test.MySessionFactory;
import util.RandomUtil;
import util.Util;
import interf.RequestAspect;

public class RequestAspectImpl implements RequestAspect {

	/**
	 * 位置采用double类型的经纬度表示，经度lng，纬度lat expectTime的格式: "yyyy-mm-dd HH:mm:ss"
	 * expectAgeMin: 期望对方的年龄下限 expectAgeMax: 期望对方的年龄上限 expectGender: 0=>我要男的
	 * 1=>我要妹子 2=>不限 需要返回： status: int: 1=>添加成功， 2=>失败 result： 如果status==1:
	 * 返回此次请求的ID(供客户端查询使用)和添加进数据表的时间 id: String time: "yyyy-mm-dd HH:mm:ss"
	 * 如果失败，返回失败原因 "..."
	 */
	public String addRequest(String phoneNumber, int age, int gender, double src_lng, double src_lat, String src_name,
			double dest_lng, double dest_lat, String dest_name, String expectTime, int expectAgeMin, int expectAgeMax,
			int expectGender) {
		JSONObject ret = new JSONObject();
		try {
			Session session = MySessionFactory.getSessionFactory().openSession();
			session.beginTransaction();
			domain.RequestActive request = new RequestActive();
			request.setRequestId(RandomUtil.randomRequestId(session));
			request.setUserId(phoneNumber);
			request.setUserAge((byte) age);
			request.setUserGender((byte) gender);
			request.setSourceX(src_lat);
			request.setSourceY(src_lng);
			request.setSourceName(src_name);
			request.setDestinationX(dest_lat);
			request.setDestinationY(dest_lng);
			request.setDestinationName(dest_name);
			request.setLeavingTime(Timestamp.valueOf(expectTime));
			request.setExpGender((byte) expectGender);
			request.setExpAgeMin((byte) expectAgeMin);
			request.setExpAgeMax((byte) expectAgeMax);
			request.setState(RequestActive.STATE_NEW_REQUEST);
			request.setRequestTime(new Timestamp(System.currentTimeMillis()));
			request.setRemainChance(RequestActive.DEFAULT_MAX_CHANCE);
//			request.setActive(RequestActive.ACTIVE);
			session.save(request);
			session.getTransaction().commit();
			JSONObject result = new JSONObject();
			result.put("id", request.getRequestId());
			result.put("time", RequestActive.FORMAT.format(request.getRequestTime()));
			result.put("charge", new JSONObject(PingxxPaymentModule.getCharge(1000, "wx", request.getRequestId()).toString()));
			ret.put("status", 1);
			ret.put("message", "拼单请求发送成功");
			ret.put("detail", result);
			session.close();
		} catch (Exception e) {
			try {
				ret.put("status", 2);
				ret.put("message", "发生未知错误，请重新发送请求.");
				ret.put("detail", e.toString());
			} catch (JSONException e1) {
			}
		}
		return ret.toString();
	}

	public static Byte STATE_BOTH_NOC = 1;
	public static Byte STATE_ME_NOC_PARTNER_R = 2;
	public static Byte STATE_ME_NOC_PARTNER_C = 3;
	public static Byte STATE_ME_C_PARTNER_NOC = 4;
	public static Byte STATE_ME_C_PARTNER_R = 5;
	public static Byte STATE_BOTH_C = 6;
	public static Byte STATE_HANDLING = 7;
	public static Byte STATE_ERROR = 8;
	/**
	   * 客户端询问之前发出的请求的处理状态
	   * 返回json字符串，status和result
	   * status:　0=>查询成功，返回请求信息
	   * 		 1=>处理成功：寻找到一起拼车的对象，而且对方没有确认 0,0
	   *         2=>处理成功：对方已经拒绝 0,2
	   *         3=>处理成功：对方已经确认 0,1
	   *         4=>处理成功：自己确认了，但是对方还没有确认 1,0
	   *         5=>处理成功：自己确认，但是对方已经拒绝 1,2
	   *         6=>处理成功：自己确认，而且对方已经确认 1,1
	   *         7=>正在处理：算法正在寻找匹配
	   *         8=>无此请求，应该是服务器出错啦！
	   *         
	   * detail: 如果status==1， 表示拼单成功，应返回拼单对象的个人信息
	   *         （对方的昵称、起点和终点、对方希望的出发时间；用户自己的起点和终点，希望出发的时间；自己剩余的次数），等待用户确认
	   *         partnerNickName, partnerSrc, partnerDest, partnerTime
	   *         myNickName, mySrc, myDest, myTime, myRemainChance
	   *       
	   *         如果status==2，对方已经确认， 返回同status == 1
	   *         if status==3 同上
	   * 如果服务器内部出错则返回null
	   */
	public String queryRequest(String requestId, String phoneNumber) {
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		Object request = (RequestActive) session.get(RequestActive.class, requestId);
		if(request == null || ((RequestActive) request).getUserId().equals(phoneNumber) == false){
			request = (Request) session.get(Request.class, requestId);
			if(request == null || ((Request) request).getUserId().equals(phoneNumber) == false){
				session.getTransaction().commit();
				session.close();
				return Util.buildJson(STATE_ERROR, "错误的请求", "request not exist or request id and phone number do not match!").toString();
			}
		}
		String ret;
		if(request.getClass() == RequestActive.class)	ret = queryRequestActive(requestId, (RequestActive) request, session);
		else ret = queryRequestPermanent(requestId, (Request) request, session);
		session.getTransaction().commit();
		session.close();
		return ret;
	}
	public String queryRequestActive(String requestId, RequestActive request, Session session){
		int status = -1;
		String message = "";
		Object detail = "";
		Byte mystate = request.getState();
		if (mystate == RequestActive.STATE_NEW_REQUEST
				|| mystate == RequestActive.STATE_OLD_REQUEST
				|| mystate == RequestActive.STATE_HANDLING) {
			status = STATE_HANDLING;
			message = "服务器正在紧张处理请求中";
			detail = new JSONObject();
			try {
				((JSONObject) detail).put("me",request.toQueryJson());
			} catch (JSONException e) {
			}
		} else if (mystate == RequestActive.STATE_ORDER_SUCCESS
				|| mystate == RequestActive.STATE_NORMAL_CANCELED
				|| mystate == RequestActive.STATE_CANCELED_AFTER_SUCCESS
				|| mystate == RequestActive.STATE_CANCELED_BY_THE_OTHER
				|| mystate == RequestActive.STATE_TOO_MANY_REJECTS) {
			//TODO: ?
		} else if (mystate == RequestActive.STATE_ME_NC_PARTNER_NC
				|| mystate == RequestActive.STATE_ME_NC_PARTNER_C
				|| mystate == RequestActive.STATE_ME_NC_PARTNER_R
				|| mystate == RequestActive.STATE_ME_C_PARTNER_C
				|| mystate == RequestActive.STATE_ME_C_PARTNER_NC
				|| mystate == RequestActive.STATE_ME_C_PARTNER_R) {
			@SuppressWarnings("rawtypes")
			List queryResult = session.createQuery("from domain.OrdersActive as oa where oa.requestId1 = ? or oa.requestId2 = ?").setString(0, requestId).setString(1, requestId).list();
			if(queryResult.size() == 0){
				return Util.buildJson(STATE_ERROR, "错误的请求", "request state not agree with order state, order not found").toString();
			}
			OrdersActive order = (OrdersActive) queryResult.get(0);
			RequestActive partnerRequest;
			User partner;
			byte partnerConfirmed = -1;
			if(order.getRequestId1().equals(requestId)){
				partnerRequest = (RequestActive) session.get(RequestActive.class, order.getRequestId2());
			}else{
				partnerRequest = (RequestActive) session.get(RequestActive.class, order.getRequestId1());
			}
			if(mystate == RequestActive.STATE_ME_NC_PARTNER_NC && partnerRequest.getState() == RequestActive.STATE_ME_NC_PARTNER_NC){
				status = STATE_BOTH_NOC;
				message = "new order";
				partnerConfirmed = 0;
			}else if(mystate == RequestActive.STATE_ME_NC_PARTNER_C && partnerRequest.getState() == RequestActive.STATE_ME_C_PARTNER_NC){
				status = STATE_ME_NOC_PARTNER_C;
				message = "please confirm";
				partnerConfirmed = 1;
			}else if(mystate == RequestActive.STATE_ME_NC_PARTNER_R){
				status = STATE_ME_C_PARTNER_R;
				message = "you are rejected";
				partnerConfirmed = 2;
			}else if(mystate == RequestActive.STATE_ME_C_PARTNER_C){
				status = STATE_BOTH_C;
				partnerConfirmed = 1;
			}else if(mystate == RequestActive.STATE_ME_C_PARTNER_NC){
				status = STATE_ME_C_PARTNER_NOC;
				partnerConfirmed = 0;
			}else if(mystate == RequestActive.STATE_ME_C_PARTNER_R){
				status = STATE_ME_C_PARTNER_R;
				partnerConfirmed = 2;
			}else{
				return Util.buildJson(STATE_ERROR, "错误的请求", "request state not agree with order state").toString();
			}
			partner = (User) session.get(User.class, partnerRequest.getUserId());
			try {
				JSONObject partnerInfo = partner.toQueryJson();
				JSONObject prInfo = partnerRequest.toQueryJson();
				for (String key : JSONObject.getNames(prInfo))
					partnerInfo.put(key, prInfo.get(key));
				partnerInfo.put("confirmed", partnerConfirmed);
				detail = order.toQueryJson();
				((JSONObject) detail).put("partner", partnerInfo);
				((JSONObject) detail).put("me", request.toQueryJson().put("remainChance", request.getRemainChance()));
			} catch (JSONException e) {
			}
		}else if(mystate == RequestActive.STATE_ME_R_PARTNER_C
				|| mystate == RequestActive.STATE_ME_R_PARTNER_NC
				|| mystate == RequestActive.STATE_ME_R_PARTNER_R){
			status = STATE_ERROR;
			detail = "should not be reached. Users must choose to continue matching or give up the request after rejecting.";
		}
		if(detail.getClass() == JSONObject.class)
			return Util.buildJson(status, message, (JSONObject) detail).toString();
		else
			return Util.buildJson(status, message, (String) detail).toString();
	}

	public String queryRequestPermanent(String requestId, Request request, Session session){
		int status = -1;
		String message = "";
		Object detail = "";
		Byte mystate = request.getState();
		if (mystate == Request.STATE_NEW_REQUEST
				|| mystate == Request.STATE_OLD_REQUEST
				|| mystate == Request.STATE_HANDLING) {
			status = STATE_HANDLING;
			message = "服务器正在紧张处理请求中";
			detail = new JSONObject();
			try {
				((JSONObject) detail).put("me",request.toQueryJson());
			} catch (JSONException e) {
			}
		} else if (mystate == Request.STATE_ORDER_SUCCESS
				|| mystate == Request.STATE_NORMAL_CANCELED
				|| mystate == Request.STATE_CANCELED_AFTER_SUCCESS
				|| mystate == Request.STATE_CANCELED_BY_THE_OTHER
				|| mystate == Request.STATE_TOO_MANY_REJECTS) {
			//TODO: ?
		} else if (mystate == Request.STATE_ME_NC_PARTNER_NC
				|| mystate == Request.STATE_ME_NC_PARTNER_C
				|| mystate == Request.STATE_ME_NC_PARTNER_R
				|| mystate == Request.STATE_ME_C_PARTNER_C
				|| mystate == Request.STATE_ME_C_PARTNER_NC
				|| mystate == Request.STATE_ME_C_PARTNER_R) {
			@SuppressWarnings("rawtypes")
			List queryResult = session.createQuery("from domain.Orders as oa where oa.requestId1 = ? or oa.requestId2 = ?").setString(0, requestId).setString(1, requestId).list();
			if(queryResult.size() == 0){
				return Util.buildJson(STATE_ERROR, "错误的请求", "request state not agree with order state, order not found").toString();
			}
			Orders order = (Orders) queryResult.get(0);
			Request partnerRequest;
			User partner;
			byte partnerConfirmed = -1;
			if(order.getRequestId1().equals(requestId)){
				partnerRequest = (Request) session.get(Request.class, order.getRequestId2());
			}else{
				partnerRequest = (Request) session.get(Request.class, order.getRequestId1());
			}
			if(mystate == Request.STATE_ME_NC_PARTNER_NC && partnerRequest.getState() == Request.STATE_ME_NC_PARTNER_NC){
				status = STATE_BOTH_NOC;
				message = "new order";
				partnerConfirmed = 0;
			}else if(mystate == Request.STATE_ME_NC_PARTNER_C && partnerRequest.getState() == Request.STATE_ME_C_PARTNER_NC){
				status = STATE_ME_NOC_PARTNER_C;
				message = "please confirm";
				partnerConfirmed = 1;
			}else if(mystate == Request.STATE_ME_NC_PARTNER_R){
				status = STATE_ME_C_PARTNER_R;
				message = "you are rejected";
				partnerConfirmed = 2;
			}else if(mystate == Request.STATE_ME_C_PARTNER_C){
				status = STATE_BOTH_C;
				partnerConfirmed = 1;
			}else if(mystate == Request.STATE_ME_C_PARTNER_NC){
				status = STATE_ME_C_PARTNER_NOC;
				partnerConfirmed = 0;
			}else if(mystate == Request.STATE_ME_C_PARTNER_R){
				status = STATE_ME_C_PARTNER_R;
				partnerConfirmed = 2;
			}else{
				return Util.buildJson(STATE_ERROR, "错误的请求", "request state not agree with order state").toString();
			}
			partner = (User) session.get(User.class, partnerRequest.getUserId());
			try {
				JSONObject partnerInfo = partner.toQueryJson();
				JSONObject prInfo = partnerRequest.toQueryJson();
				for (String key : JSONObject.getNames(prInfo))
					partnerInfo.put(key, prInfo.get(key));
				partnerInfo.put("confirmed", partnerConfirmed);
				detail = order.toQueryJson();
				((JSONObject) detail).put("partner", partnerInfo);
				((JSONObject) detail).put("me", request.toQueryJson().put("remainChance", request.getRemainChance()));
			} catch (JSONException e) {
			}
		}else if(mystate == Request.STATE_ME_R_PARTNER_C
				|| mystate == Request.STATE_ME_R_PARTNER_NC
				|| mystate == Request.STATE_ME_R_PARTNER_R){
			status = STATE_ERROR;
			detail = "should not be reached. Users must choose to continue matching or give up the request after rejecting.";
		}
		if(detail.getClass() == JSONObject.class)
			return Util.buildJson(status, message, (JSONObject) detail).toString();
		else
			return Util.buildJson(status, message, (String) detail).toString();
	}

	/**
	   * 在算法为此次请求寻找到匹配之前，用户不想等待而取消拼车请求
	   * 取消拼车请求不会有第二次机会
	   * 发送要取消的requestId
	   * return:
	   *   status = 1, 取消成功； -1 失败
	   *   失败时附加result: String, 失败原因
	   * 服务器内部出错则返回null 
	   */
	public String cancelRequest(String myrequestId) {
		JSONObject ret = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		RequestActive myRequest = (RequestActive) session.get(RequestActive.class, myrequestId);
		if(myRequest != null){
			myRequest.setState(RequestActive.STATE_NORMAL_CANCELED);
			ret = Util.buildJson(1, "", "");
		}else{
			ret = Util.buildJson(-1, "该拼车请求不存在", "request not exist error");
		}
		session.getTransaction().commit();
		session.close();
		return ret.toString();
	}

	public static final int RESPONSE_ACCEPT = 1;
	public static final int RESPONSE_REJECT = 0;
	/**
	   * 算法寻找到合适的匹配之后，询问用户是否接受拼车对象
	   * response: 1: 接受
	   *           0: 拒绝
	   * 返回：
	   *   status == 1 -> 操作成功, 如果是接受且此时双方都接受，则返回对方的手机号
	   *   status == -1 -> 操作失败
	   * 接受的话会进入等待对方接受或成功拼单的状态
	   * 决绝（重试一次）会扣除一次剩余机会，下次再拒绝的话会销毁请求
	   * null
	   */
	public String responseToOpposite(String myRequestId, int response) {
		String ret = null;
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List queryResult = session.createQuery("from domain.OrdersActive as oa where oa.requestId1 = ? or oa.requestId2 = ?").setString(0, myRequestId).setString(1, myRequestId).list();
		if(queryResult.size() == 0){
			session.close();
			return Util.buildJson(-1, "", "request not matched in orders").toString();
		} else {
			OrdersActive order = (OrdersActive) queryResult.get(0);
			if(response == RESPONSE_ACCEPT){
				ret = actionConfirm(myRequestId, order, session);
			}else{
				ret = actionReject(myRequestId, order, session);
			}
		}
		session.getTransaction().commit();
		session.close();
		return ret;
	}
	
	/**
	 * params should be checked outside
	 * @param requestId
	 * @param order
	 * @param session
	 * @return
	 */
	public String actionConfirm(String requestId, OrdersActive order, Session session){
		int status = 1;
		String detail = "";
		try{
		String partnerRequestId = null;
		if(order.getRequestId1().equals(requestId)){
			partnerRequestId = order.getRequestId2();
		}else{
			partnerRequestId = order.getRequestId1();
		}
		RequestActive myRequest = (RequestActive) session.get(RequestActive.class, requestId);
		RequestActive partnerRequest = (RequestActive) session.get(RequestActive.class, partnerRequestId);
		byte myState = myRequest.getState();
		byte partnerState = partnerRequest.getState();
		if(myState == RequestActive.STATE_ME_NC_PARTNER_C && partnerState == RequestActive.STATE_ME_C_PARTNER_NC){
			myRequest.setState(RequestActive.STATE_ME_C_PARTNER_C);
			partnerRequest.setState(RequestActive.STATE_ME_C_PARTNER_C);
			Request request = new Request(myRequest);
			Request pre = new Request(partnerRequest);
			Orders orderDone = new Orders(order);
			session.save(request);
			session.save(pre);
			session.save(orderDone);
			session.delete(order);
			session.delete(myRequest);
			session.delete(partnerRequest);
			session.flush();
			status = 1;
		}else if(myState == RequestActive.STATE_ME_NC_PARTNER_NC && partnerState == RequestActive.STATE_ME_NC_PARTNER_NC){
			myRequest.setState(RequestActive.STATE_ME_C_PARTNER_NC);
			partnerRequest.setState(RequestActive.STATE_ME_NC_PARTNER_C);
			status = 1;
		}else if(myState == RequestActive.STATE_ME_NC_PARTNER_R){
			status = -1;
			detail = "you are already rejected. do not confirm again.";
		}else{
			status = -1;
			detail = "request states do not match; or you have already confirmed or rejected";
		}
//		EasemodMsgModule.sendMsg(partnerRequest.getUserId(), "1");
		Notifier.addToSend(partnerRequest.getUserId());
//		if(status == 1){
//			if(partnerRequest.getActive() == RequestActive.ACTIVE){
//				EasemodMsgModule.sendMsg(partnerRequest.getUserId(), "1");
//			}else{
//				//TODO: push or text
//			}
//		}
		}catch(Exception e){
			
		}
		return Util.buildJson(status, "", detail).toString();
	}

	/**
	 * 
	 * @param requestId
	 * @param order
	 * @param session
	 * @return
	 */
	public String actionReject(String requestId, OrdersActive order, Session session){
		int status = 1;
		String detail = "";
		String partnerRequestId = null;
		if(order.getRequestId1().equals(requestId)){
			partnerRequestId = order.getRequestId2();
		}else{
			partnerRequestId = order.getRequestId1();
		}
		RequestActive myRequest = (RequestActive) session.get(RequestActive.class, requestId);
		RequestActive partnerRequest = (RequestActive) session.get(RequestActive.class, partnerRequestId);
		byte myState = myRequest.getState();
		byte partnerState = partnerRequest.getState();
		byte remainChance = myRequest.getRemainChance();
		if(remainChance <= 0){
			return Util.buildJson(-1, "", "no remain chance to reject").toString();
		}
		if(myState == RequestActive.STATE_ME_NC_PARTNER_C && partnerState == RequestActive.STATE_ME_C_PARTNER_NC){
			myRequest.setState(RequestActive.STATE_ME_R_PARTNER_C);
			partnerRequest.setState(RequestActive.STATE_ME_C_PARTNER_R);
			status = 1;
		}else if(myState == RequestActive.STATE_ME_NC_PARTNER_NC && partnerState == RequestActive.STATE_ME_NC_PARTNER_NC){
			myRequest.setState(RequestActive.STATE_ME_R_PARTNER_NC);
			partnerRequest.setState(RequestActive.STATE_ME_NC_PARTNER_R);
			status = 1;
		}else if(myState == RequestActive.STATE_ME_NC_PARTNER_R){
			status = -1;
			detail = "should not be reached";
		}else{
			status = -1;
			detail = "request states do not match";
		}
		if(status == 1){
			myRequest.setRemainChance((byte) (remainChance - 1));
			detail = ""+myRequest.getRemainChance();
			if(myRequest.getRemainChance() <= 0){
				myRequest.setState(RequestActive.STATE_TOO_MANY_REJECTS);
			}
//			EasemodMsgModule.sendMsg(partnerRequest.getUserId(), "1");
			Notifier.addToSend(partnerRequest.getUserId());
//			if(partnerRequest.getActive() == RequestActive.ACTIVE){
////				EasemodMsgModule.sendMsg(partnerRequest.getUserId(), "1");
//			}else{
//				//TODO: push or text
//			}
		}
		return Util.buildJson(status, "", detail).toString();
	}

	/**
	   * 拒绝拼单对象后重新请求匹配
	   * status : 1 成功
	   *          0 重新匹配请求失败
	   * detail : status == 0 时返回失败原因
	   * @param myRequestId
	   */
	  public String rematch(String myRequestId){
		  int status = 0;
		  String detail = "";
		  Session session = MySessionFactory.getSessionFactory().openSession();
		  session.beginTransaction();
		  RequestActive request = (RequestActive) session.get(RequestActive.class, myRequestId);
		  if(request == null){
			  status = 0;
			  detail = "request not exits";
		  }else{
			if (request.getState() == RequestActive.STATE_ME_R_PARTNER_C
					|| request.getState() == RequestActive.STATE_ME_R_PARTNER_NC
					|| request.getState() == RequestActive.STATE_ME_R_PARTNER_R) {
				if (request.getRemainChance() > 0) {
					request.setState(RequestActive.STATE_NEW_REQUEST);
					status = 1;
				} else {
					status = 0;
					detail = "should not be reached. the request has no remain chance to rematch";
				}
			  }else{
				  status = 0;
				  detail = "should not be reached. wrong request state";
			  }
		  }
		  session.getTransaction().commit();
		  return Util.buildJson(status, "", detail).toString();
	  }
	
	  public String confirmOffBoard(String myRequestId){
		  int status;
		  String detail;
		  Session session = MySessionFactory.getSessionFactory().openSession();
		  session.beginTransaction();
		  Request request = (Request) session.get(Request.class, myRequestId);
		  if(request == null){
			  status = -1;
			  detail = "no request found";
		  }else{
			  request.setState(Request.STATE_ORDER_SUCCESS);
			  status = 1;
			  detail = myRequestId;
		  }
		  session.getTransaction().commit();
		  session.close();
		  return Util.buildJson(status, "", detail).toString();
	  }
	  
	public String getChargeInfo(int amount, String channel, String requestId) {
		String detail;
		int status;
		Charge  charge = PingxxPaymentModule.getCharge(amount, channel, requestId);
		if(charge != null){
			status = 1;
			detail = charge.toString();
		}else{
			status = -1;
			detail = "failed to get charge object";
		}
		return Util.buildJson(status, "", detail).toString();
	}
	
	public String queryPayResult(String chargeId) {
		int status = 1;
		String detail = "";
		try {
			Charge charge = Charge.retrieve(chargeId);
			if(charge.getPaid() == false)	status = -1;
		} catch (AuthenticationException e) {
			status = -1;
			detail = e.toString();
		} catch (InvalidRequestException e) {
			status = -1;
			detail = e.toString();
		} catch (APIConnectionException e) {
			status = -1;
			detail = e.toString();
		} catch (APIException e) {
			status = -1;
			detail = e.toString();
		}
		return Util.buildJson(status, "", detail).toString();
	}

}
