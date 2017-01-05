package deviceManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Import {
	
	public static void main(String[] args) {
		importSingleRow();
	}
	
	public static void importSingleRow() {

		try {
			
			// Read default configure file "hibernate.cfg.xml"
			Configuration cfg = new Configuration().configure();
			
			// New a SessionFactory 
			SessionFactory factory = cfg.buildSessionFactory();
			
			Session session = null;	
			try {		
				session = factory.openSession();
				session.beginTransaction();
				
				//Transaction content
				User user = new User();
				user.setUsername("admin");
				user.setNum("0001");
				user.setPasswd("123456");
				
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
			
			
			
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
