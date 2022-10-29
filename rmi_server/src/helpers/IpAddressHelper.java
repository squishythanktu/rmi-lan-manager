package helpers;

import models.IpAddress;
import models.Room;

public class IpAddressHelper {
	public static boolean inRoom(Room room, IpAddress ipAddress) {
		if (ipAddress.compareTo(room.getStartIp()) && room.getEndIp().compareTo(ipAddress))
			return true;
		return false;
	}
}
