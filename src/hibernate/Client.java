package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.cfgxml.spi.CfgXmlAccessService;
import org.hibernate.cfg.Configuration;

public class Client {
	
	public static void main(String[] args) {

		try {
			Configuration cfg = new Configuration().configure();
			
			SessionFactory factory = cfg.buildSessionFactory();
			SessionFactory session = null;
			
			try{
				session.openSession();
				
			} catch(Exception e){
				e.printStackTrace();
				session.
				
			}
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
