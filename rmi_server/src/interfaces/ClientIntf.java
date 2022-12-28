package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import models.Computer;

public interface ClientIntf extends Remote{
	public byte[] getDesktop() throws RemoteException;
}
