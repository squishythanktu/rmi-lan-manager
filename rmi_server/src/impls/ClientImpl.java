package impls;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import interfaces.ClientIntf;
import models.Computer;
import models.IpAddress;
import models.Room;
import repos.ComputerRepository;
import repos.RoomRepository;
import ui.Main;

public class ClientImpl extends UnicastRemoteObject implements ClientIntf {
	private ComputerRepository computerRepo;
	private RoomRepository roomRepo;
	private Main main;

	public ClientImpl(Main main) throws RemoteException {
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
	public boolean login(String deviceName) throws RemoteException {
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
				return true;
			} else {
				System.out.println("Login failed from " + clientIp.toString());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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

	@Override
	public byte[] getDesktop(){
		try
		{
			GraphicsEnvironment env=GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice screen=env.getDefaultScreenDevice();
			Robot robot=new Robot(screen);
			Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage capture=robot.createScreenCapture(new Rectangle(0,0,d.width,d.height));
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			ImageIO.write(capture, "jpg", bytes);
			bytes.flush();
			byte[] data = bytes.toByteArray();
			bytes.close();
			return data;
		}
		catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
}
