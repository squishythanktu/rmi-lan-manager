package repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Room;

public class RoomRepository {
	private Connection conn;

	public RoomRepository() {
		this.conn = DatabaseConnector.getConnection();
	}

	public boolean create(Room room) {
		String sql = "INSERT INTO room(name, start_ip, end_ip, zone_id) values(?, ?, ?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setString(2, room.getStartIp().toString());
			ps.setString(3, room.getEndIp().toString());
			ps.setInt(4, room.getZoneId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Room> getByZone(int zoneId) {
		String sql = "SELECT r.*,\r\n" + "       count(c.room_id) as count_computer,\r\n"
				+ "       (SELECT COUNT(c.room_id)\r\n" + "        from computer as c\r\n"
				+ "        where c.room_id = r.id\r\n" + "          and c.online = 1)\r\n"
				+ "                        as online_computer\r\n" + "FROM room AS r\r\n"
				+ "         LEFT OUTER JOIN computer AS c\r\n" + "                         ON c.room_id = r.id\r\n"
				+ "where r.zone_id = ?\r\n" + "GROUP BY r.id;\r\n";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, zoneId);
			ResultSet rs = ps.executeQuery();
			List<Room> rooms = new ArrayList<>();
			while (rs.next()) {
				rooms.add(new Room(rs.getInt("id"), rs.getString("name"), rs.getString("start_ip"),
						rs.getString("end_ip"), rs.getInt("zone_id"), rs.getDate("created"), rs.getDate("updated"),
						rs.getInt("count_computer"), rs.getInt("online_computer")));
			}
			return rooms;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Room> getAll() {
		String sql = "SELECT * FROM room";
		List<Room> rooms = new ArrayList<>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rooms.add(new Room(rs.getInt("id"), rs.getString("name"), rs.getString("start_ip"),
						rs.getString("end_ip"), rs.getInt("zone_id"), rs.getDate("created"), rs.getDate("updated")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rooms;
	}

	public boolean update(Room room) {
		String sql = "UPDATE room set name=?, zone_id=? where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, room.getName());
			ps.setInt(2, room.getZoneId());
			ps.setInt(3, room.getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(int roomId) {
		String sql = "DELETE FROM room where id=?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, roomId);
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getRoomName(int roomId) {
		String sql = "SELECT * FROM room WHERE id=?";
		String name = "";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, roomId);
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
