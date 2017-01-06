package dataAccessLayer;

import java.sql.Time;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BorrowManager {

	public static SessionFactory factory;
	
	static{
		// Read default configure file "hibernate.cfg.xml"
		Configuration cfg = new Configuration().configure();
		
		// New a SessionFactory 
	    factory = cfg.buildSessionFactory();
	}
	
	
	public static void main(String[] args) {
		// only for test!
		BorrowManager bm = new BorrowManager();
		
		@SuppressWarnings("deprecation")
		Time time = new Time(12,3,15);
//		System.out.print(bm.borrowDevice("xiaowan", "N2", time));
		System.out.print(bm.returnDevice("N1", time));
	}
	
	
	// return 
	// 0 : success
	// 1 : device have be borrowed
	int borrowDevice(String username, String devicenum, Time borrowtime) {
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			// check if the device have be borrowed
			String hql = "select dev.onwho from Device dev where num = :devicenum";
			String onwho = (String) session.createQuery(hql).setParameter("devicenum", devicenum).list().get(0);
			if(! onwho.equals("null")){
				return 1;
			}
			
			session.beginTransaction();
			
			//Transaction content
			Borrow borrow = new Borrow();
			borrow.setBorrowtime(borrowtime);
			borrow.setDevicenum(devicenum);
			borrow.setUsername(username);
			session.save(borrow);
			
			hql = "update Device d set d.onwho = :username where num = :devicenum";
			session.createQuery(hql).setParameter("username", username).setParameter("devicenum", devicenum).executeUpdate();
					
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally{
			if (session != null){
				if (session.isOpen()){
					session.close();
				}
			}
		}
		
		return 0; 
	}
	
	
	// return 
	// 0 : success
	// 1 : device have be returned
	int returnDevice(String devicenum, Time returntime) {
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			// check if the device have be borrowed
			String hql = "select dev.onwho from Device dev where num = :devicenum";
			String onwho = (String) session.createQuery(hql).setParameter("devicenum", devicenum).list().get(0);
			if(onwho.equals("null")){
				return 1;
			}
			
			session.beginTransaction();
			
			//Transaction content
			hql = "update Borrow b set b.returntime = :returntime where devicenum = :devicenum";
			session.createQuery(hql).setParameter("returntime", returntime).setParameter("devicenum", devicenum).executeUpdate();
			
			hql = "update Device d set d.onwho = :username where num = :devicenum";
			session.createQuery(hql).setParameter("username", "null").setParameter("devicenum", devicenum).executeUpdate();
					
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally{
			if (session != null){
				if (session.isOpen()){
					session.close();
				}
			}
		}
		
		return 0; 
	}
}
