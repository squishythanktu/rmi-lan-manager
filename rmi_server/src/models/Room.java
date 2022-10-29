package models;

import java.util.Date;

public class Room extends Zone {
	private int zoneId;
	private int sumComputer;
	private int online;
	private int offline;

	public Room(int id, String name, String startIp, String endIp, int zoneId, Date created, Date updated) {
		super(id, name, startIp, endIp, created, updated);
		setZoneId(zoneId);
	}

	public Room(int id, String name, String startIp, String endIp, int zoneId, Date created, Date updated,
			int countComputer, int online) {
		super(id, name, startIp, endIp, created, updated);
		setSumComputer(countComputer);
		setOnline(online);
		setOffline(getSumComputer() - getOnline());
	}

	public Room(String name, String startIp, String endIp, int zoneId) {
		super(name, startIp, endIp);
		setZoneId(zoneId);
	}

	public int getZoneId() {
		return zoneId;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public int getSumComputer() {
		return sumComputer;
	}

	public void setSumComputer(int sumComputer) {
		this.sumComputer = sumComputer;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getOffline() {
		return offline;
	}

	public void setOffline(int offline) {
		this.offline = offline;
	}

}
