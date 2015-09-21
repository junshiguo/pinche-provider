package core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import module.EasemodNotifier;

import org.hibernate.Session;

import test.MySessionFactory;
import util.RandomUtil;
import domain.Orders;
import domain.Request;

public class RequestMatching {
	public static double SAVE_THRESHOLD = 0.3;
	public static void setSaveThreshold(double d){
		SAVE_THRESHOLD = d;
	}
	
	public void doMatching(){
		Session session = MySessionFactory.getSessionFactory().openSession();
		session.beginTransaction();
		ArrayList<Request> requests = fetchRequests(session);
		ArrayList<ArrayList<Request>> filteredRequest = LocationFilter.filterByLocation(requests);
		for(ArrayList<Request> list : filteredRequest){
			while(list.size() >= 2){
				Request r1 = list.get(0);
				if(r1.getState() == Request.STATE_HANDLING || r1.getState() == Request.STATE_ME_NC_PARTNER_NC){
					list.remove(r1);
					continue;
				}
				Route maxRoute = null;
				Request maxR = null;
				for(int i = 1; i < list.size(); i++){
					Request r2 = list.get(i);
					if(r2.getState() == Request.STATE_HANDLING){
						list.remove(r2);
						continue;
					}
					//pruning
					if(r1.getUserId().equals(r2.getUserId())){
						continue;
					}
//					if(r1.getState() == RequestActive.STATE_OLD_REQUEST && r2.getState() == RequestActive.STATE_OLD_REQUEST){
//						continue;
//					}
					if((r2.getUserGender() != r1.getExpGender() && r1.getExpGender() != 2) || (r1.getUserGender() != r2.getExpGender() && r2.getExpGender() != 2)){
						continue;
					}
					if(r1.getUserAge() < r2.getExpAgeMin() || r1.getUserAge() > r2.getExpAgeMax() || r2.getUserAge() < r1.getExpAgeMin() || r2.getUserAge() > r1.getExpAgeMax()){
						continue;
					}
//					//same cell: direction
//					if ((r1.getDestinationX() - r1.getSourceX()) * (r2.getDestinationX() - r2.getSourceX())
//							+ (r1.getDestinationY() - r1.getSourceY()) * (r2.getDestinationY() - r2.getSourceY()) < 0) {
//						continue;
//					}
					Route route = new Route(r1.sourceCoord(), r1.getSourceName(), r1.destinationCoord(), r1.getDestinationName(), r2.sourceCoord(), r2.getSourceName(), r2.destinationCoord(), r2.getDestinationName());
					if(maxRoute == null || maxRoute.getSavePercent() < route.getSavePercent()){
						maxRoute = route;
						maxR = r2;
					}
				}
				if(maxRoute == null || maxRoute.getSavePercent() < RequestMatching.SAVE_THRESHOLD){ // no matching
					if(r1.getState() == Request.STATE_NEW_REQUEST)	r1.setState(Request.STATE_OLD_REQUEST);
					session.update(r1);
					list.remove(r1);
				}else{ //match success
					if(r1.getState() == Request.STATE_NORMAL_CANCELED){
						list.remove(r1);
						continue;
					}
					if(maxR.getState() == Request.STATE_NORMAL_CANCELED){
						list.remove(maxR);
						continue;
					}
					r1.setState(Request.STATE_ME_NC_PARTNER_NC);
					maxR.setState(Request.STATE_ME_NC_PARTNER_NC);
					Orders order = new Orders(RandomUtil.randomOrderId(session), r1.getRequestId(), maxR.getRequestId(), r1.getUserId(), maxR.getUserId(), new Timestamp(System.currentTimeMillis()), maxRoute.getSavePercent(), maxRoute.getRoute(), maxRoute.getRouteNames());
					session.save(order);
					session.update(r1);
					session.update(maxR);
					list.remove(r1);
					list.remove(maxR);
					EasemodNotifier.addToSend(r1.getUserId());
					EasemodNotifier.addToSend(maxR.getUserId());
				}
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	/**
	 * get all requests that need to be considered
	 * TODO: consider leaving time
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Request> fetchRequests(Session session){
		String hql = "from domain.Request as ra where ra.state = ?";
		List<Request> list0 = session.createQuery(hql).setByte(0, Request.STATE_NEW_REQUEST).list();
		List<Request> list1 = session.createQuery(hql).setByte(0, Request.STATE_OLD_REQUEST).list();
		ArrayList<Request> ret = new ArrayList<Request>();
		ret.addAll(list0);
		ret.addAll(list1);
		ArrayList<Request> expired = new ArrayList<Request>();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		for(Request request : ret){
			if(request.getLeavingTime().before(now)){
				request.setState(Request.STATE_TIME_EXPIRED);
				expired.add(request);
			}
		}
		ret.removeAll(expired);
		new TimeExpireNotifier(expired).start();
		return ret;
	}
	
}
