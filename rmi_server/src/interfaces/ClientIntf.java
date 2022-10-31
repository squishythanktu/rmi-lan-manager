package interfaces;

import java.rmi.Remote;

public interface ClientIntf extends Remote{
	public byte[] getDesktop();
}
