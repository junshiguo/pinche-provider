package core;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.hibernate.Session;

import domain.Payment;
import domain.Request;
import module.EasemodMsgModule;
import test.MySessionFactory;

public class TimeExpireNotifier extends Thread {
	
	public ArrayList<Request> request;
	
	public TimeExpireNotifier(ArrayList<Request> request){
		this.request = request;
	}
	
	public void run(){
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		for(Request r : request){
			Payment payment = (Payment) session.get(Payment.class, r.getRequestId());
			payment.setState(Payment.STATE_WAIT_REFUNDING);
			payment.setExpRefundTime(new Timestamp(System.currentTimeMillis() + 7*24*60*1000));
			session.flush();
			EasemodMsgModule.sendMsg(r.getUserId(), r.getRequestId());
		}
		session.getTransaction().commit();
		session.close();
	}

}
