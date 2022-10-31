package impls;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.ImageIO;

import interfaces.ClientIntf;

public class ClientImpl extends UnicastRemoteObject implements ClientIntf{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
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
