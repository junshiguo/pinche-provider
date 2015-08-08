package impl;

import java.sql.Timestamp;
import java.util.Iterator;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import domain.OrdersActive;
import domain.RequestActive;
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
	public String addRequest(String phoneNumber, double src_lng,
			double src_lat,String src_name, double dest_lng, double dest_lat,
			String dest_name, String expectTime, int expectAgeMin,
			int expectAgeMax, int expectGender) {
		JSONObject ret = new JSONObject();
		try{
			Session session = MySessionFactory.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			domain.RequestActive request = new RequestActive();
			request.setRequestId(RandomUtil.randomRequestId());
			request.setUserId(phoneNumber);
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
			session.save(request);
			session.getTransaction().commit();
			JSONObject result = new JSONObject();
			result.put("id", request.getRequestId());
			result.put("time", RequestActive.FORMAT.format(request.getRequestTime()));
			ret.put("status", 1);
			ret.put("result", result);
		}catch (Exception e){
			try {
				ret.put("status", 2);
				ret.put("result", e.toString());
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
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Iterator iter = session.createQuery("from domain.OrdersActive as oa where oa.requestId1 = ?").setString(0, requestId).iterate();
		if(iter.hasNext()){
			OrdersActive order = (OrdersActive) iter.next();
			String requestid2 = order.getRequestId2();
			String userId2 = order.getUserId2();
			
		}
		
		return null;
	}

}
