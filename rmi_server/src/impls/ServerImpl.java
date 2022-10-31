package impls;


import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import interfaces.ServerIntf;
import models.Computer;
import models.IpAddress;
import models.Room;
import repos.ComputerRepository;
import repos.RoomRepository;
import ui.Main;

public class ServerImpl extends UnicastRemoteObject implements ServerIntf {
	private ComputerRepository computerRepo;
	private RoomRepository roomRepo;
	private Main main;

	public ServerImpl(Main main) throws RemoteException {
		super();
		this.main = main;
		this.computerRepo = new ComputerRepository();
		this.roomRepo = new RoomRepository();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String login(String deviceName) throws RemoteException {
		try {
			System.out.println(deviceName);
			IpAddress clientIp = null;
			try {
				clientIp = new IpAddress(RemoteServer.getClientHost());
			} catch (Exception e) {
				e.printStackTrace();
			}

			int roomId = -1;
			List<Room> rooms = roomRepo.getAll();
			for (Room r : rooms)
				if (clientIp.compareTo(r.getStartIp()) && r.getEndIp().compareTo(clientIp)) {
					roomId = r.getId();
				}
			System.out.println(roomId);
			computerRepo.create(new Computer(deviceName, roomId, clientIp.toString()));
			if (computerRepo.login(clientIp.toString())) {
				System.out.println("Login success from " + clientIp.toString() + " - " + deviceName);
				main.reload();
				return clientIp.toString();
			} else {
				System.out.println("Login failed from " + clientIp.toString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean logout() throws RemoteException {
		try {
			String clientHost = RemoteServer.getClientHost();
			if (computerRepo.logout(clientHost)) {
				System.out.println("Logout success from " + clientHost);
				main.reload();
				return true;
			} else {
				System.out.println("Logout failed from " + clientHost);
				return false;
			}
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		return false;
	}

}
