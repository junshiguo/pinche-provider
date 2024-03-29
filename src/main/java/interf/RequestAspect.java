package interf;

import java.sql.Timestamp;

//服务器自己出错（异常）包括：1.后台数据库操作、逻辑出错； 2.远程调用出现异常； 3.环信出错 等情况。
//此时客户端请求的“事务”终止，返回null。服务器把error信息记录到日志
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
	   * status: 1=>处理成功：寻找到一起拼车的对象，而且对方没有确认 
	   *         2=>处理成功：对方已经取消 
	   *         3=>处理成功：对方已经确认 
	   *         4=>处理成功：自己确认了，但是对方还没有确认
	   *         5=>处理成功：自己确认，但是对方已经拒绝
	   *         6=>处理成功：自己确认，而且对方已经确认
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
	   *         if status==4 同上
	   *         
	   *         如果status==5，用户自己的起点、终点和出发时间
	   *         如果status==4
	   * 如果服务器内部出错则返回null
	   */
	  String queryRequest(String requestId, String phoneNumber);
	  
	  /**
	   * 在算法为此次请求寻找到匹配之前，用户不想等待而取消拼车请求
	   * 取消拼车请求不会有第二次机会
	   * 发送要取消的requestId
	   * return:
	   *   status = 1, 取消成功； -1 失败
	   *   失败时附加result: String, 失败原因
	   * 服务器内部出错则返回null 
	   */
	  String cancelRequest(String myrequestId);
	  
	  /**
	   * 算法寻找到合适的匹配之后，询问用户是否接受拼车对象
	   * response: 1: 接受
	   *           0: 拒绝
	   * 返回：
	   *   status == 1 -> 操作成功
	   *   status == -1 -> 操作失败
	   * 接受的话会进入等待对方接受或成功拼单的状态
	   * 决绝（重试一次）会扣除一次剩余机会，下次再拒绝的话会销毁请求
	   * null
	   */
	  String responseToOpposite(String myRequestId, int response);
	  
	  /**
	   * 拒绝拼单对象后重新请求匹配
	   * status : 1 成功
	   *          0 重新匹配请求失败
	   * detail : status == 0 时返回失败原因
	   * @param myRequestId
	   */
	  String rematch(String myRequestId);
	  
	  String confirmOffBoard(String myRequestId);
	  
	  /**
	   * 获取charge对象用于支付．
	   * status : 1　成功
	   * 		　　0 失败
	   * detail :　charge对象的json格式
	   * @param amount 单位为分
	   * @param channel　默认微信"wx"
	   * @param requestId
	   * @return
	   */
	  String getChargeInfo(int amount, String channel, String requestId);
	  
	  /**
	   * status : 1 支付成功
	   * 	　　　　　　0 支付失败
	   * @param chargeId
	   * @return
	   */
	  String queryPayResult(String chargeId);
	  
	  String confirmPayment(String requestId, String chargeId, String userId, int deposit);
}
