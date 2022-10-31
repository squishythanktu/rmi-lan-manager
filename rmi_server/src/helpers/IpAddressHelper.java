package helpers;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import models.IpAddress;
import models.Room;

public class IpAddressHelper {
	public static boolean inRoom(Room room, IpAddress ipAddress) {
		if (ipAddress.compareTo(room.getStartIp()) && room.getEndIp().compareTo(ipAddress))
			return true;
		return false;
	}

	public static String getMyIp() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface current = interfaces.nextElement();
				// System.out.println(current);
				if (!current.isUp() || current.isLoopback() || current.isVirtual())
					continue;
				Enumeration<InetAddress> addresses = current.getInetAddresses();
				while (addresses.hasMoreElements()) {
					InetAddress current_addr = addresses.nextElement();
					if (current_addr.isLoopbackAddress())
						continue;
					if (current_addr instanceof Inet4Address) {
						return current_addr.getHostAddress();
					}
				}
			}
		} catch (SocketException SE) {
			SE.printStackTrace();
		}
		return null;
	}
}
