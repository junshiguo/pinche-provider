package core.refund;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;

import com.pingplusplus.model.Refund;

import domain.Payment;
import module.PingxxPaymentModule;
import test.MySessionFactory;

public class RefundMain {
	
	public static void main(String[] args){
		while(true){
			long start = System.currentTimeMillis();
			refund();
			long end = System.currentTimeMillis();
			if(end - start < 6*60*60*1000){
				try {
					Thread.sleep(6*60*60*1000 - end + start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void refund(){
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		String hql = "from domain.Payment as p where p.state = ?";
		List<Payment> payments = session.createQuery(hql).setByte(0, Payment.STATE_WAIT_REFUNDING).list();
		int count = 0;
		for(Payment payment : payments){
			//TODO: time control
			Refund refund = PingxxPaymentModule.getRefund(payment.getChargeId(), payment.getDeposit()-payment.getDeduction()-payment.getTip());
			payment.setRefundId(refund.getId());
			payment.setRefundTime(new Timestamp(System.currentTimeMillis()));
			if(count >= 100){
				session.flush();
				count = 0;
			}
		}
		session.getTransaction().commit();
		session.close();
	}

}
