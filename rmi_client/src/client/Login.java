package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import impls.ClientImpl;
import interfaces.ClientIntf;
import interfaces.ServerIntf;


public class Login {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		try {
			Registry registry = LocateRegistry.getRegistry("192.168.1.8", 2022);
			ServerIntf demoIntf = (ServerIntf) registry.lookup(ServerIntf.class.getSimpleName());
			// Login voi devicename = InetAdress.getLocalHost().getHostName();
			String clientIp=  demoIntf.login(InetAddress.getLocalHost().getHostName());
			
			System.setProperty("java.rmi.client.hostname", clientIp);
			registry = LocateRegistry.createRegistry(2023);
			registry.rebind(ClientIntf.class.getSimpleName(), new ClientImpl());
			
			System.out.println("Login successfully!");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
