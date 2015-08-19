package module;

import java.util.HashMap;
import java.util.Map;

import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;

public class PingxxPaymentModule {
	public static String API_URL = "https://api.pingxx.com";
	public static String apiKey = "sk_test_z5eDWL5en9qTrXrPmDv5y9mL";
	public static String appId = "app_G4inTOnL0iH8uzfr";
	
	public static void main(String[] args){
		Pingpp.apiKey = apiKey;
		Map<String, Object> chargeParams = new HashMap<String, Object>();
		  chargeParams.put("order_no",  "12345678");
		  chargeParams.put("amount", 100);
		  Map<String, String> app = new HashMap<String, String>();
		  app.put("id", appId);
		  chargeParams.put("app", app);
		  chargeParams.put("channel",  "wx");
		  chargeParams.put("currency", "cny");
		  chargeParams.put("client_ip",  "127.0.0.1");
		  chargeParams.put("subject",  "title");
		  chargeParams.put("body",  "Your Body");
		try {
			Charge charge = Charge.create(chargeParams);
			charge = Charge.retrieve(charge.getId());
			charge.getAmount();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
