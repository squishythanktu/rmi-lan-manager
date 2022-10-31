package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIntf extends Remote {
	public String login(String deviceName) throws RemoteException;
	public boolean logout() throws RemoteException;
}
