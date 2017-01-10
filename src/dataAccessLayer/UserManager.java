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
		
//		System.out.print(um.getPasswd("admin"));
		
//		um.importSingleUser("xiaowan2", "lzy950727"); // successed!
		
		um.changePasswd("xiaowan2", "234567");
	}
	
	
	public void importSingleUser(String username, String passwd) {

		Session session = null;	
		
		try {		
			session = factory.openSession();
			session.beginTransaction();
			
			//Transaction content
			User user = new User();
			user.setUsername(username);
			user.setPasswd(passwd);
			
			session.save(user);
			
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
	}
	
	
	@SuppressWarnings("unchecked")
	public String getPasswd(String username) {
		
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
	
	
	// 0 : successful
	// 1 : can find the user
	public int changePasswd(String username, String passwd) {
		
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			String hql = "update User u set u.passwd = :passwd where username = :username";
			session.createQuery(hql).setParameter("passwd", passwd).setParameter("username", username).executeUpdate();	
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
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
