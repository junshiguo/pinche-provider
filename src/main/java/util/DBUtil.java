package util;

import org.hibernate.Session;

import domain.IdCounter;
import test.MySessionFactory;

public class DBUtil {
	public static long ID_BLOCK_SIZE = 100;
	
	/**
	 * the applied id range is [ret, ret + ID_BLOCK_SIZE)
	 * @return
	 */
	public static synchronized long applyOrderIdBlock(Session session){
		IdCounter counter = (IdCounter) session.get(IdCounter.class, IdCounter.ORDER_ID_KEY);
		long ret = counter.getIdValue();
		counter.setIdValue(ret + ID_BLOCK_SIZE);
		session.flush();
		return ret;
	}
	
	/**
	 * the applied id range is [ret, ret + ID_BLOCK_SIZE)
	 * @return
	 */
	public static synchronized long applyRequestIdBlock(Session session){
		IdCounter counter = (IdCounter) session.get(IdCounter.class, IdCounter.REQUEST_ID_KEY);
		long ret = counter.getIdValue();
		counter.setIdValue(ret + ID_BLOCK_SIZE);
		session.flush();
		return ret;
	}
	
}
