package dataAccessLayer;

import java.sql.Time;

public class Borrow {
	
	private String id;
	private String username;
	private String devicenum;
	private Time borrowtime;
	private Time returntime;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Time getBorrowtime() {
		return borrowtime;
	}
	public void setBorrowtime(Time borrowtime) {
		this.borrowtime = borrowtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Time getReturntime() {
		return returntime;
	}
	public void setReturntime(Time returntime) {
		this.returntime = returntime;
	}
	public String getDevicenum() {
		return devicenum;
	}
	public void setDevicenum(String devicenum) {
		this.devicenum = devicenum;
	}
	
}
