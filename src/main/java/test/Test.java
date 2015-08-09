package test;

import impl.RequestAspectImpl;
import impl.UserAspectImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;

import util.RandomUtil;
import domain.Rating;
import domain.User;

public class Test {
	
	public static void main(String[] args){
		for(int i = 0; i < 10; i++){
			System.out.println(RandomUtil.randomOrderId());
		}
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
    	System.out.println(request.addRequest("18801735863", 1.0, 1.2, "复旦大学", 2.0, 2.2, "张江", "2015-8-6 10:10:10", 20, 30, 0));
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

}
