package interfaces;

import java.awt.image.BufferedImage;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIntf extends Remote{
	public boolean login(String deviceName) throws RemoteException;
	public boolean logout() throws RemoteException;
	public byte[] getDesktop() throws RemoteException;
}
