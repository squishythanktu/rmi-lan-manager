package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import impls.ClientImpl;
import interfaces.ClientIntf;
import interfaces.ServerIntf;

public class Login {
	public static void main(String[] args) {
		try {
			
<<<<<<< HEAD
			Registry registry = LocateRegistry.getRegistry("10.10.59.121", 2022);
=======
			Registry registry = LocateRegistry.getRegistry("192.168.9.25", 2022);
>>>>>>> 836a77c (done)
			ServerIntf demoIntf = (ServerIntf) registry.lookup(ServerIntf.class.getSimpleName());
			String clientIp=  demoIntf.login(InetAddress.getLocalHost().getHostName());
			
			System.setProperty("java.rmi.client.hostname", clientIp);
			registry = LocateRegistry.createRegistry(2023);
			registry.rebind(ClientIntf.class.getSimpleName(), new ClientImpl());
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		System.out.println("asd");
	}
}
