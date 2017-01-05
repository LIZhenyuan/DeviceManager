package dataAccessLayer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Import {
	
	public static SessionFactory factory;
	
	static{
		// Read default configure file "hibernate.cfg.xml"
		Configuration cfg = new Configuration().configure();
		
		// New a SessionFactory 
	    factory = cfg.buildSessionFactory();
	}
	
	
	public static void main(String[] args) {
		
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
	
	public void importSingleDevice(){
		//meixie wan !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}
	
	
}
