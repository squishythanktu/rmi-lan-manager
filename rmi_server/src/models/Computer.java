package models;

import java.util.Date;

public class Computer extends Model {
	private boolean online;
	private int roomId;
	private String ipAddress;

	public Computer() {
	}

	public Computer(int id, String name, boolean online, String ipAddress, int roomId, Date created, Date updated) {
		super(id, name, created, updated);
		setOnline(online);
		setIpAddress(ipAddress);
		setRoomId(roomId);
	}
	
	public Computer(String name, int roomId, String ip) {
		setName(name);
		setRoomId(roomId);
		setIpAddress(ip);
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

}
