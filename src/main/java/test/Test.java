package test;

import java.io.IOException;
import java.util.Date;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import domain.Rating;
import domain.RatingPK;

public class Test {
	
    
    public static void main(String[] args) throws IOException {
    	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"provider.xml"});
    	context.start();
    	System.in.read();
    	
//        Test mgr = new Test();
//        mgr.createAndStoreOrder(1, new Date());
//        MySessionFactory.getSessionFactory().close();
    }

    private void createAndStoreOrder(int id, Date theDate) {
        Session session = MySessionFactory.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Rating rating = new Rating();
//        rating.setId(new RatingPK("1","1"));
        rating.setUserId("2");
        rating.setOrderId("3");
        rating.setCommentorId("2");
        rating.setRating(5);
        rating.setComment("very good");
        session.save(rating);

        session.getTransaction().commit();
        System.out.println("done");
    }

}
