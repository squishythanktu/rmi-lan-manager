package repos;

import java.awt.image.BufferedImage;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import interfaces.ClientIntf;
import models.Computer;

public class ComputerRepository {
	private Connection conn;

	public ComputerRepository() {
		conn = DatabaseConnector.getConnection();
	}

	public List<Computer> getAllByRoom(int roomId) {
		List<Computer> list = new ArrayList<>();
		String sql = "SELECT * FROM computer where room_id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, roomId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getBoolean("online"),
						rs.getString("ip_address"), rs.getInt("room_id"), rs.getDate("created"),
						rs.getDate("updated")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean create(Computer computer) {
		String sql = "INSERT INTO computer(name, ip_address, room_id) VALUES(?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, computer.getName());
			ps.setString(2, computer.getIpAddress());
			ps.setInt(3, computer.getRoomId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOnline(String ipAddress, int online) {
		String sql = "UPDATE computer SET online = ? where ip_address=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, online);
			ps.setString(2, ipAddress);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean login(String ipAddress) {
		return updateOnline(ipAddress, 1);
	}

	public boolean logout(String ipAddress) {
		return delete(ipAddress);
	}
	
	public boolean delete(String ipAddress) {
		String sql = "delete from computer where ip_address=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, ipAddress);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
//	
//	public boolean update(Computer computer) {
//		String sql = "update computer set name=?, room_id=?, ip_address=? where id=?";
//		try {
//			PreparedStatement ps = conn.prepareStatement(sql);
//			ps.setString(1, computer.getName());
//			ps.setInt(2, computer.getRoomId());
//			ps.setString(3, computer.getIpAddress());
//			ps.setInt(4, computer.getId());
//			ps.execute();
//			return true;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
}
