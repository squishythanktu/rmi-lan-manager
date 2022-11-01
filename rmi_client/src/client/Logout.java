package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.ServerIntf;

public class Logout {
	public static void main(String[] args) {
		try {
<<<<<<< HEAD
			Registry registry = LocateRegistry.getRegistry("10.10.59.121", 2022);
			ServerIntf demoIntf = (ServerIntf) registry.lookup(ServerIntf.class.getSimpleName());
			System.out.println(demoIntf.logout());
=======
			Registry registry = LocateRegistry.getRegistry("192.168.9.25", 2022);
			ServerIntf demoIntf = (ServerIntf) registry.lookup(ServerIntf.class.getSimpleName());
			System.out.println(demoIntf.logout());
			
>>>>>>> 836a77c (done)
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
