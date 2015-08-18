package test;

import impl.OrderAspectImpl;
import impl.RequestAspectImpl;
import impl.UserAspectImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import module.EasemodMsgModule;

import org.hibernate.Session;

import util.RandomUtil;
import domain.Rating;
import domain.User;

public class Test {
	
	public static void main(String[] args){
//		RequestAspectImpl test = new RequestAspectImpl();
//		System.out.println(test.queryRequest("R000045774583492855434", "18817361981"));
//		testAddRequest();
		EasemodMsgModule.sendMsg("18817361981", "0");
		MySessionFactory.getSessionFactory().close();
	}
    
    public static void fnvTest() throws IOException {
    	PreparedStatement stmt = null;
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "remote", "remote");
			stmt = conn.prepareStatement("insert into fnv values (?, ?)");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	for(long i = 1000000L; i < 100000000L; i++){
    		long hashed = RandomUtil.FNVhash64(i);
    		try {
				stmt.setLong(1, hashed);
				stmt.setString(2, "x");
			} catch (SQLException e) {
				e.printStackTrace();
			}
    		try {
				stmt.execute();
			} catch (SQLException e) {
				System.out.println(i+": "+hashed);
				e.printStackTrace();
			}
    		if((i+1) % 10000 == 0){
    			System.out.println("*: "+i/10000);
    		}
    	}
    		
    }
    
    public static void testAddRequest(){
    	RequestAspectImpl request = new RequestAspectImpl();
    	System.out.println(request.addRequest("18801735863", 20, 0, 121.499209,31.300753, "大柏树", 121.325297, 31.204188, "虹桥火车站", "2015-8-16 10:10:10", 0, 100, 2));
//    	System.out.println(request.addRequest("18817361981", 21, 0, 121.515019, 31.304208, "复旦大学", 121.352318, 31.199246, "虹桥机场", "2015-8-16 10:10:10", 0, 100, 2));
//    	System.out.println(request.addRequest("18801735864", 20, 0, 121.5212,31.303838, "五角场", 121.559,31.221854, "世纪公园", "2015-8-16 10:10:10", 0, 100, 2));
//    	System.out.println(request.addRequest("18817361982", 21, 0, 121.515019, 31.304208, "复旦大学", 121.605425, 31.195045, "复旦大学张江校区", "2015-8-16 10:10:10", 0, 100, 2));
//    	System.out.println(request.addRequest("18801735865", 20, 0, 121.463349,31.256187, "上海火车站", 121.81031,31.156115, "上海浦东国际机场", "2015-8-16 10:10:10", 0, 100, 2));
//    	System.out.println(request.addRequest("18817361983", 21, 0, 121.479087,31.239022, "人民广场", 121.81031,31.156115, "上海浦东国际机场", "2015-8-16 10:10:10", 0, 100, 2));
    	MySessionFactory.getSessionFactory().close();
    }
    
    public static void testUser(){
    	UserAspectImpl test = new UserAspectImpl();
    	System.out.println(test.sendVerifyCode("18801735863").toString());
    	String result = test.register("18801735863", "123456", "jsguo", User.GENDER_MALE, null, 24);
    	System.out.println(result.toString());
    	
    	result = test.logIn("18801735863", "123456");
    	System.out.println(result.toString());
    	
    	result = test.changePassword("18801735863", "1234", "123456");
    	System.out.println(result.toString());
    	
    	result = test.findPassword("1880173863");
    	System.out.println(result.toString());
        MySessionFactory.getSessionFactory().close();
    }

    public static void createAndStoreOrder(int id, Date theDate) {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Rating rating = new Rating();
//        rating.setId(new RatingPK("1","1"));
        rating.setUserId("2");
        rating.setOrderId("3");
        rating.setCommentorId("2");
        rating.setRating((byte) 5);
        rating.setComment("very good");
        session.save(rating);

        session.getTransaction().commit();
        System.out.println("done");
    }
    
    public static void testGetRequest(){
        OrderAspectImpl order = new OrderAspectImpl();
      	System.out.println(order.getActiveRequest("18801735863"));
    	System.out.println(order.getActiveRequest("18817361981"));
       	System.out.println(order.getFinishedOrders("18801735863"));
    	System.out.println(order.getFinishedOrders("18817361981"));
    	
    	MySessionFactory.getSessionFactory().close();
    }
}
