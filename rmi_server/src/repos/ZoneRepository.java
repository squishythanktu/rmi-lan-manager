package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Model;
import models.Zone;

public class ZoneRepository {
	private Connection conn;

	public ZoneRepository() {
		this.conn = DatabaseConnector.getConnection();
	}

	public Zone create(Zone zone) {
		String sql = "INSERT INTO zone(name, start_ip, end_ip) VALUES(?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, zone.getName());
			ps.setString(2, zone.getStartIp().toString());
			ps.setString(3, zone.getEndIp().toString());
			ps.execute();
			
			List<Zone> zones = getAll();
			return zones.get(zones.size()-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Zone> getAll() {
		List<Zone> zones = new ArrayList<>();
//		String sql = "SELECT * FROM zone";
		String sql = "SELECT z.*, count(c.room_id) as count_computer\r\n" + "FROM zone as z\r\n"
				+ "LEFT OUTER JOIN room as r\r\n" + "LEFT OUTER JOIN computer as c\r\n"
				+ "ON c.room_id = r.id on r.zone_id = z.id\r\n" + "GROUP BY z.id;\r\n";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Zone z = new Zone(rs.getInt("id"), rs.getString("name"), rs.getString("start_ip"),
						rs.getString("end_ip"), rs.getDate("created"), rs.getDate("updated"),
						rs.getInt("count_computer"));
				zones.add(z);
			}
			return zones;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean update(Zone zone) {
		String sql = "UPDATE zone SET name=? WHERE id=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, zone.getName());
			ps.setInt(2, zone.getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int id) {
		String sql = "DELETE FROM zone WHERE id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws ClassNotFoundException {
		new ZoneRepository().getAll().forEach(System.out::println);
	}
	public String getZoneName(int zoneId) {
		String sql = "SELECT * FROM zone WHERE id=?";
		String name = "";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, zoneId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				name = rs.getString(2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}
}
