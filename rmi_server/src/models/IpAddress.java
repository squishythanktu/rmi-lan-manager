package models;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IpAddress {
	private int[] ipAddress;

	public IpAddress() {
		setIpAddress(new int[4]);
	}

	public IpAddress(String ipStr) throws Exception {
		setIpAddress(new int[4]);
		String[] arr = ipStr.split("\\.");
		for (int i = 0; i < 4; i++) {
			ipAddress[i] = Integer.parseInt(arr[i]);
			if (ipAddress[i] < 0 || ipAddress[i] > 255) {
				throw new Exception("Invalid ip");
			}
		}
	}

	private String numberToBinary(long n) {
		String rs = "";
		while (n > 0) {
			rs = (char) (n % 2 + 48) + rs;
			n /= 2;
		}
		while (rs.length() < 8)
			rs = "0" + rs;
		return rs;
	}

	public String ipToBinary() {
		String rs = "";
		for (int i = 0; i < 4; i++) {
			rs += numberToBinary(ipAddress[i]);
		}
		return rs;
	}
	// Tinh khoang cach 2 ip
	public long subtract(IpAddress ipB) {
		return Long.parseLong(ipToBinary(), 2) - Long.parseLong(ipB.ipToBinary(), 2);
	}

	public int[] getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(int[] ipAddress) {
		this.ipAddress = ipAddress;
	}
	// So sanh 2 ip
	public boolean compareTo(IpAddress ipB) {
		for (int i = 0; i < 4; i++) {
			if (ipAddress[i] < ipB.getIpAddress()[i])
				return false;
		}
		return true;
	}

	public void plus() {
		ipAddress[3]++;
		for (int i = 3; i >= 0; i--) {
			if (ipAddress[i] > 255) {
				ipAddress[i] = ipAddress[i] - 256;
				ipAddress[i - 1]++;
			}
		}
	}
	
	private String longToIp(long n) {
		String rs = numberToBinary(n);
		// Lap day gia tri
		while (rs.length() < 32) {
			rs = "0" + rs;
		}
		
		String ip = "";
		for (int i=0; i<4; i++) {
			ip += String.valueOf(Long.parseLong(rs.substring(i*8, (i+1)*8), 2));
			ip += ".";
		}
		return ip.substring(0, ip.length()-1);
	}

	public IpAddress add(long n) {
		try {
			long added = Long.parseLong(ipToBinary(), 2) + n;
			return new IpAddress(longToIp(added));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return Arrays.stream(ipAddress).mapToObj(String::valueOf).collect(Collectors.joining("."));
	}

}
