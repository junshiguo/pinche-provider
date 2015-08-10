package interf;

public interface RequestAspect {
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
	  String addRequest(String phoneNumber, int age, int gender,
	      double src_lng, double src_lat, String src_name,
	      double dest_lng, double dest_lat, String dest_name, 
	      String expectTime, int expectAgeMin, int expectAgeMax, int expectGender);
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
	  String queryRequest(String requestId, String phoneNumber);
}
