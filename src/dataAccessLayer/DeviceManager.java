package dataAccessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
		DeviceManager dm = new DeviceManager();
		
//		System.out.print(um.getDeviceList(0,5).get(3).getDevicename());
		
//		System.out.print(um.queryDevice("devicename", "IPS3000").get(0).getDevicename());
		
//		String devXls = "C:\\Users\\lzy\\Desktop\\device.xls";
//		dm.importDeviceFormXls(devXls);
		
		System.out.print(dm.countDevice());
	}
	
	
	public int countDevice() {
		Session session = null;	
		int deviceNum = 0;
		
		try {		
			session = factory.openSession();
			
			String hql = "select count(*) from Device as d";
			deviceNum = ((Long) session.createQuery(hql).uniqueResult()).intValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (session != null){
				if (session.isOpen()){
					session.close();
				}
			}
		}
		
		return deviceNum;
	}
	
	
	public void importSingleDevice(String num, String devicename, String pic, String location, String para, String onwho) {
		Session session = null;	
		
		try {		
			session = factory.openSession();
			session.beginTransaction();
			
			//Transaction content
			Device dev = new Device();
			dev.setNum(num);
			dev.setDevicename(devicename);
			dev.setPic(pic);
			dev.setLocation(location);
			dev.setPara(para);
//			dev.setOnwho(onwho);
			
			session.save(dev);
			
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
	
	
	public void importDeviceFormXls(String devXls) {
		
		File devFile = new java.io.File(devXls);

		// get the dev sheet
		HSSFSheet devSheet = null;
		try {
			POIFSFileSystem devFileSystem = new POIFSFileSystem(new FileInputStream(devFile));
			HSSFWorkbook devWorkbook = new HSSFWorkbook(devFileSystem);
			devSheet = devWorkbook.getSheetAt(0);
			devWorkbook.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		// use importSingleDevice(...) import every row of device
		for(int i = devSheet.getFirstRowNum(); i <= devSheet.getLastRowNum(); i++){
			
			HSSFRow devRow = devSheet.getRow(i);

			String num = devRow.getCell(0).getStringCellValue();
			String devicename = devRow.getCell(1).getStringCellValue();
			String pic = devRow.getCell(2).getStringCellValue();
			String location = devRow.getCell(3).getStringCellValue();
			String para = devRow.getCell(4).getStringCellValue();
			String onwho = devRow.getCell(5).getStringCellValue();
			
			importSingleDevice(num, devicename, pic, location, para, onwho);
		}
		
	}
	

	@SuppressWarnings("unchecked")
	public List<Device> getDeviceList(int firstNum, int totalNum) {
		
		List<Device> deviceList = null;
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			String hql = "select dev from Device dev";
			deviceList = (List<Device>) session.createQuery(hql).setFirstResult(firstNum).setMaxResults(totalNum).list();
	
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
	
	
	@SuppressWarnings("unchecked")
	public List<Device> queryDevice(String propName, String propValue) {
		
		List<Device> deviceList = null;
		Session session = null;	
		
		try {		
			session = factory.openSession();
			
			String hql = "select dev from Device dev where " + propName + " like :propName";
			deviceList = (List<Device>) session.createQuery(hql).setParameter("propName", propValue).list();
	
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
