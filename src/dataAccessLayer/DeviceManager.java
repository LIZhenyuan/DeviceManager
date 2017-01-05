package dataAccessLayer;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DeviceManager {
	
	public static SessionFactory factory;
	
	static{
		// Read default configure file "hibernate.cfg.xml"
		Configuration cfg = new Configuration().configure();
		
		// New a SessionFactory 
	    factory = cfg.buildSessionFactory();
	}
	
	public static void main(String[] args) {
		// only for test!
		DeviceManager um = new DeviceManager();
		System.out.print(um.getDeviceList(0,1).get(0).getDevicename());
	}
	
	List<Device> getDeviceList(int firstNum,int totalNum) {
		
		List<Device> deviceList = null;
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			String hql = "select dev from Device where rownum >= 0";//meixie wan !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			deviceList = (List<Device>)session.createQuery(hql).list();
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (session != null){
				if (session.isOpen()){
					session.close();
				}
			}
		}
		
		return deviceList;
	}
	
}
