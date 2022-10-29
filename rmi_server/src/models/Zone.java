package models;

import java.util.Date;

public class Zone extends Model {
	private int countComputer;
	private IpAddress startIp;
	private IpAddress endIp;

	public Zone(int id, String name, String startIp, String endIp, Date created, Date updated) {
		super(id, name, created, updated);
		setStartIp(startIp);
		setEndIp(endIp);
	}

	public Zone(int id, String name, String startIp, String endIp, Date created, Date updated, int countComputer) {
		super(id, name, created, updated);
		setCountComputer(countComputer);
		setStartIp(startIp);
		setEndIp(endIp);
	}
	
	public Zone(String name, String startIp, String endIp) {
		setName(name);
		setStartIp(startIp);
		setEndIp(endIp);
	}

	public int getCountComputer() {
		return countComputer;
	}

	public void setCountComputer(int countComputer) {
		this.countComputer = countComputer;
	}

	public IpAddress getStartIp() {
		return startIp;
	}

	public void setStartIp(String startIp) {
		try {
			this.startIp = new IpAddress(startIp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IpAddress getEndIp() {
		return endIp;
	}

	public void setEndIp(String endIp) {
		try {
			this.endIp = new IpAddress(endIp);
		} catch (Exception e) {
		}
	}

}
