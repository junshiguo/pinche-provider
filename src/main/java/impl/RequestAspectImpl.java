package impl;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import domain.OrdersActive;
import domain.RequestActive;
import domain.User;
import test.MySessionFactory;
import util.RandomUtil;
import interf.RequestAspect;

public class RequestAspectImpl implements RequestAspect {

	/**
	   * 位置采用double类型的经纬度表示，经度lng，纬度lat
	   * expectTime的格式: "yyyy-mm-dd HH:mm:ss"
	   * expectAgeMin: 期望对方的年龄下限
	   * expectAgeMax: 期望对方的年龄上限
	   * expectGender:  
	   *                0=>我要男的
	   *                1=>我要妹子
	   *                2=>不限
	   * 需要返回：
	   *   status: int: 1=>添加成功， 2=>失败
	   *   result： 如果status==1: 返回此次请求的ID(供客户端查询使用)和添加进数据表的时间
	   *                          id: String
	   *                          time: "yyyy-mm-dd HH:mm:ss"
	   *            如果失败，返回失败原因
	   *            "..."             
	   */
	public String addRequest(String phoneNumber, int age, int gender, double src_lng,
			double src_lat,String src_name, double dest_lng, double dest_lat,
			String dest_name, String expectTime, int expectAgeMin,
			int expectAgeMax, int expectGender) {
		JSONObject ret = new JSONObject();
		try{
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
			request.setActive(RequestActive.ACTIVE);
			session.save(request);
			session.getTransaction().commit();
			JSONObject result = new JSONObject();
			result.put("id", request.getRequestId());
			result.put("time", RequestActive.FORMAT.format(request.getRequestTime()));
			ret.put("status", 1);
			ret.put("message", "拼单请求发送成功");
			ret.put("detail", result);
			session.close();
		}catch (Exception e){
			try {
				ret.put("status", 2);
				ret.put("message", "发生未知错误，请重新发送请求.");
				ret.put("detail", e.toString());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		return ret.toString();
	}

	/**
	   * 客户端询问之前发出的请求的处理状态
	   * 返回json字符串，status和result
	   * status: 1=>处理成功
	   *         2=>正在处理
	   *         3=>无此请求，应该是服务器出错啦！
	   *         
	   * result: 如果status==1， 表示拼单成功，应返回拼单对象的个人信息，等待用户确认
	   *         如果status!=1， 客户端相应处理，应该不会出现status==3的情况
	   */
	public String queryRequest(String requestId, String phoneNumber) {
		JSONObject ret = new JSONObject();
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		@SuppressWarnings("rawtypes")
		List queryResult = session.createQuery("from domain.OrdersActive as oa where oa.requestId1 = ?").setString(0, requestId).list();
		for(Object o : queryResult){
			OrdersActive order = (OrdersActive) o;
			String requestid2 = order.getRequestId2();
			String userId2 = order.getUserId2();
			RequestActive request = (RequestActive) session.get(RequestActive.class, requestid2);
			User user = (User) session.get(User.class, userId2);
			if (request != null && user != null) {
				try {
					ret.put("status", 1);
					ret.put("message", "query success");
					JSONObject result = new JSONObject(request.toQueryJson().toString());
					JSONObject obj = user.toQueryJson();
					for(String key : JSONObject.getNames(obj))
						result.put(key, obj.get(key));
					obj = order.toQueryJson1();
					for(String key : JSONObject.getNames(obj))
						result.put(key, obj.get(key));
					ret.put("detail", result);
					return ret.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		queryResult = session.createQuery("from domain.OrdersActive as oa where oa.requestId2 = ?").setString(0, requestId).list();
		for(Object o : queryResult){
			OrdersActive order = (OrdersActive) o;
			String requestid1 = order.getRequestId1();
			String userId1 = order.getUserId1();
			RequestActive request = (RequestActive) session.get(RequestActive.class, requestid1);
			User user = (User) session.get(User.class, userId1);
			if (request != null && user != null) {
				try {
					ret.put("status", 1);
					ret.put("message", "query success");
					JSONObject result = new JSONObject(request.toQueryJson().toString());
					JSONObject obj = user.toQueryJson();
					for(String key : JSONObject.getNames(obj))
						result.put(key, obj.get(key));
					obj = order.toQueryJson2();
					for(String key : JSONObject.getNames(obj))
						result.put(key, obj.get(key));
					ret.put("detail", result);
					return ret.toString();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			ret.put("status", 2);
			ret.put("message", "still handling");
			ret.put("detail", "handling");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		session.close();
		return ret.toString();
	}

}
