package interf;

// 服务器应提供的接口
public interface UserAspect {
  // 仅仅用来测试与服务器的连通性
  int addOne(int x);
  
  /**
   * 向指定的phoneNumber发送验证码
   * 服务端应随机生成验证码，在StatusAndString的对象
   * status == 1: 成功，result应为生成的验证码
   * status == -1: 失败，手机号已经注册
   * status == -2: 失败，短信功能暂时不可用
   * status == -3: 失败，手机号非法
   * 其他状态待定
  */
  String sendVerifyCode(String phoneNumber);
  
  /**
   * 用户注册。客户端已经对用户两次输入的密码进行了比对，因此传个服务器的password是最终结果
   * status == 1: 成功注册一个新的用户
   * status == -1: 失败，手机号已经注册
   * status == -2: 失败，有非法参数（手机号不存在、年龄不合理等等）
   */
  String register(String phoneNumber, String password, String username, Integer gender, String job, Integer age);
  
  /**
   * 用户登陆
   * status == 1: 成功登陆
   * status == -1: 密码错误
   * status == -2: 用户没有注册
   */
  String logIn(String phoneNumber, String password);
  
  /**
   * 更改密码
   * status == 1: 更改成功
   * status == -1: 原密码错误
   * status == -2: 手机号未注册
   */
  String changePassword(String phoneNumber, String oldPassword, String newPassword);

  /**
   * 用户忘记了密码，需要找回
   * status == 1： 找回成功， result应未用户的密码
   * status == -1: 失败，手机号未注册
   */
  String findPassword(String phoneNumber);
  
  /**
   * 更改昵称
   * status == 1: 更改成功
   * status == -1: 更改失败
   */
  String changeNickName(String phoneNumber,	String newNickName);
  
  /**
   * 更改职业
   * status == 1: 更改成功
   * status == -1: 更改失败
   */
  String changeJob(String phoneNumber,	String newJob);
}