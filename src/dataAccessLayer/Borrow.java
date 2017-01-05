package dataAccessLayer;

import java.sql.Time;

public class Borrow {
	
	private String username;
	private String devicename;
	private Time borrowtime;
	private Time returnTime;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public Time getBorrowtime() {
		return borrowtime;
	}
	public void setBorrowtime(Time borrowtime) {
		this.borrowtime = borrowtime;
	}
	public Time getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Time returnTime) {
		this.returnTime = returnTime;
	}
	
}
