package dataAccessLayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserManager {
	
	public static SessionFactory factory;
	
	static{
		// Read default configure file "hibernate.cfg.xml"
		Configuration cfg = new Configuration().configure();
		
		// New a SessionFactory 
	    factory = cfg.buildSessionFactory();
	}
	
	public static void main(String[] args) {
		// only for test!
		UserManager um = new UserManager();
		System.out.print(um.getPasswd("admin"));
	}
	
	
	String getPasswd(String username) {
		
		Session session = null;	
		String passwd = null;
		
		try {		
			session = factory.openSession();
			
			String hql = "select user from User user where user.username=" + "'" + username + "'";
			List<User> userList = (List<User>)session.createQuery(hql).list();
			
			return userList.get(0).getPasswd();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (session != null){
				if (session.isOpen()){
					session.close();
				}
			}
		}
		
		return passwd;
	}
	
}
