package dataAccessLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
		Import im = new Import();
		
//		im.importSingleUser("xiaowan", "lzy950727"); // successed!
//		im.importSingleDevice(0, "test", "test", "test", "test", "test"); // successed!
		
		String devXls = "C:\\Users\\lzy\\Desktop\\device.xls";
		im.importDeviceFormXls(devXls);
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
	
	public void importSingleDevice(int num, String devicename, String pic, String location, String para, String onwho) {
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
			dev.setOnwho(onwho);
			
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

			int num = (int) devRow.getCell(0).getNumericCellValue();
			String devicename = devRow.getCell(1).getStringCellValue();
			String pic = devRow.getCell(2).getStringCellValue();
			String location = devRow.getCell(3).getStringCellValue();
			String para = devRow.getCell(4).getStringCellValue();
			String onwho = devRow.getCell(5).getStringCellValue();
			
			importSingleDevice(num, devicename, pic, location, para, onwho);
		}
		
	}
	
}
