package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.ServerIntf;

public class Logout {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.1.8", 2022);
			ServerIntf demoIntf = (ServerIntf) registry.lookup(ServerIntf.class.getSimpleName());
			System.out.println(demoIntf.logout());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
