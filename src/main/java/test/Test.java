package test;

import impl.RequestAspectImpl;
import impl.UserAspectImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;

import org.hibernate.Session;

import domain.Rating;
import domain.RequestActive;
import domain.User;

public class Test {
	
    
    public static void main(String[] args) throws IOException {
//    	Session session = MySessionFactory.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		RequestActive request = (RequestActive) session.get(RequestActive.class, "444");
//		String sourcename = request.getSourceName();
//		System.out.println(sourcename);
//		System.out.println(request.getDestinationName());
    	
    	RequestAspectImpl request = new RequestAspectImpl();
    	System.out.println(request.addRequest("18801735863", 1.0, 1.2, "复旦大学", 2.0, 2.2, "张江", "2015-8-6 10:10:10", 20, 30, 0));
    	
//        Test mgr = new Test();
//        mgr.createAndStoreOrder(1, new Date());
//    	UserAspectImpl test = new UserAspectImpl();
//    	System.out.println(test.sendVerifyCode("18801735863").toString());
//    	String result = test.register("18801735863", "123456", "jsguo", User.GENDER_MALE, null, 24);
//    	System.out.println(result.toString());
//    	
//    	result = test.logIn("18801735863", "123456");
//    	System.out.println(result.toString());
//    	
//    	result = test.changePassword("18801735863", "1234", "123456");
//    	System.out.println(result.toString());
//    	
//    	result = test.findPassword("1880173863");
//    	System.out.println(result.toString());
        MySessionFactory.getSessionFactory().close();
    }

    private void createAndStoreOrder(int id, Date theDate) {
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
