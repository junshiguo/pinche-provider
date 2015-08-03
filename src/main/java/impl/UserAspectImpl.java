package impl;

import java.util.Random;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;

import domain.User;
import test.MySessionFactory;
import interf.UserAspect;

public class UserAspectImpl implements UserAspect {

	public int addOne(int x) {
		return x+1;
	}

	/**
	   * 向指定的phoneNumber发送验证码
	   * 服务端应随机生成验证码，在StatusAndString的对象
	   * status == 1: 成功，result应为生成的验证码
	   * status == -1: 失败，手机号已经注册
	   * status == -2: 失败，短信功能暂时不可用
	   * status == -3: 失败，手机号非法
	   * 其他状态待定
	   * caution: 错误状态-2, -3不一定正确，可能是其他错误！
	  */
	public String sendVerifyCode(String phoneNumber) {
		String code = UserAspectImpl.randomVerifyCode();
		int status;
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);
		if(user != null){
			status = -1;
			code = "手机号码已注册！";
		}else{
			status = 1;
//			String text = "【齐拼网络】您的齐拼验证码是"+code;
//			try {
//				String result = MsgModule.sendSms(text, phoneNumber);
//				if(result.startsWith("{\"code\":0")){
//					status = 1;
//				}else if(result.startsWith("{\"code\":2")){
//					status = -3;
//				}else{
//					status = -2;
//					code = result;
//				}
//			} catch (IOException e) {
//				status = -2;
//			}
		}
		System.out.println(code);
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("result", code);
		} catch (JSONException e) {
			return ""+status;
		}
		return ret.toString();
	}

	 /**
	   * 用户注册。客户端已经对用户两次输入的密码进行了比对，因此传个服务器的password是最终结果
	   * status == 1: 成功注册一个新的用户
	   * status == -1: 失败，手机号已经注册
	   * status == -2: 失败，有非法参数(年龄不合理等等）
	   */
	public String register(String phoneNumber, String password,
			String username, Integer gender, String job, Integer age) {
		int status; 
		String result;
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);
		if(user == null){
			user = new User();
			user.setPhoneNumber(phoneNumber);
			user.setPassword(password);
			user.setName(username);
			user.setGender(gender);
			user.setJob(job);
			user.setAge(age);
			session.save(user);
			status = 1;
			result = "注册成功！";
		}else{
			status = -1;
			result = "手机号码已注册！";
		}
		session.getTransaction().commit();
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("result", result);
		} catch (JSONException e) {
			return ""+status;
		}
		return ret.toString();
	}

	/**
	   * 用户登陆
	   * status == 1: 成功登陆
	   * status == -1: 密码错误
	   * status == -2: 用户没有注册
	   */
	public String logIn(String phoneNumber, String password) {
		int status; 
		String result;
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);
		if(user == null){
			status = -2;
			result = "该号码尚未注册！";
		}else if(user.getPassword().equals(password) == false){
			status = -1;
			result = "密码错误，请重新输入！";
		}else{
			status = 1;
			result = "登陆成功！";
		}
		session.getTransaction().commit();
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("result", result);
		} catch (JSONException e) {
			return ""+status;
		}
		return ret.toString();
	}

	/**
	   * 更改密码
	   * status == 1: 更改成功
	   * status == -1: 原密码错误
	   * status == -2: 手机号未注册
	   */
	public String changePassword(String phoneNumber,
			String oldPassword, String newPassword) {
		int status; 
		String result;
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);
		if(user == null){
			status = -2;
			result = "该号码尚未注册！";
		}else if(user.getPassword().equals(oldPassword) == false){
			status = -1;
			result = "原密码错误！";
		}else{
			user.setPassword(newPassword);
			session.flush();
			status = 1;
			result = "密码修改成功";
		}
		session.getTransaction().commit();
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("result", result);
		} catch (JSONException e) {
			return ""+status;
		}
		return ret.toString();
	}

	/**
	   * 用户忘记了密码，需要找回
	   * status == 1： 找回成功， result应未用户的密码
	   * status == -1: 失败，手机号未注册
	   */
	public String findPassword(String phoneNumber) {
		int status; 
		String result;
		Session session = MySessionFactory.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		User user = (User) session.get(User.class, phoneNumber);
		if(user == null){
			status = -1;
			result = "手机号码尚未注册！";
		}else{
			status = 1;
			result = user.getPassword();
		}
		JSONObject ret = new JSONObject();
		try {
			ret.put("status", status);
			ret.put("result", result);
		} catch (JSONException e) {
			return ""+status;
		}
		return ret.toString();
	}
	
	/**
	 * generate six digital verify code
	 * @return
	 */
	public static String randomVerifyCode(){
		int length = 6;
		Random ran = new Random(System.currentTimeMillis());
		String code = "";
		for(int i = 0; i < length; i++){
			code += ran.nextInt(10);
		}
		return code;
	}

}
