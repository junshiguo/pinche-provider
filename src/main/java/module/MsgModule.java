package module;

import util.HttpClientUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信http接口的java代码调用示例
 * 基于Apache HttpClient 4.3
 * code = -3: 无权限
 * code = 0:　成功
 * code = 2: 手机号码错误
 * @author songchao
 * @since 2015-04-03
 */
public class MsgModule {

    // 查账户信息的http地址
    private static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";

    //通用发送接口的http地址
    private static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";

    // 模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";

    // 发送语音验证码接口的http地址
    private static String URI_SEND_VOICE = "http://yunpian.com/v1/voice/send.json";

    /**
     * specific to account
     */
    public static String apikey = "f071e7d035d3ab77cb41a730613885f7";

    /**
     * 取账户信息
     *
     * @return json格式字符串
     * @throws java.io.IOException
     */
    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        return HttpClientUtil.post(URI_GET_USER_INFO, params);
    }
    
    public static String sendSms(String text, String mobile) throws IOException {
    	Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return HttpClientUtil.post(URI_SEND_SMS, params);
    }

    /**
     * 通用接口发短信
     *
     * @param apikey apikey
     * @param text   　短信内容
     * @param mobile 　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String sendSms(String apikey, String text, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        return HttpClientUtil.post(URI_SEND_SMS, params);
    }

    /**
     * 通过模板发送短信(不推荐)
     *
     * @param apikey    apikey
     * @param tpl_id    　模板id
     * @param tpl_value 　模板变量值
     * @param mobile    　接受的手机号
     * @return json格式字符串
     * @throws IOException
     */
    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("tpl_id", String.valueOf(tpl_id));
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return HttpClientUtil.post(URI_TPL_SEND_SMS, params);
    }

    /**
    * 通过接口发送语音验证码
    * @param apikey apikey
    * @param mobile 接收的手机号
    * @param code   验证码
    * @return
    */
    public static String sendVoice(String apikey, String mobile, String code) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("mobile", mobile);
        params.put("code", code);
        return HttpClientUtil.post(URI_SEND_VOICE, params);
    }

}
