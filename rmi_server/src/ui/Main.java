package ui;

import java.awt.EventQueue;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import helpers.IpAddressHelper;
import impls.ServerImpl;
import interfaces.ServerIntf;
import models.Computer;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public List<Computer> computers;
	
	public void changeLayout(JPanel panel) {
		Main.this.getContentPane().removeAll();
		Main.this.getContentPane().add(panel);
		Main.this.getContentPane().repaint();
		Main.this.getContentPane().revalidate();
	}

	public void reload() {
		((View) getContentPane().getComponent(0)).reload();
	}

	/**
	 * Launch the application.
	 * @throws UnknownHostException 
	 * @throws ServerNotActiveException 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.changeLayout(new ZoneView(frame));

					// start server rmi
					System.setProperty("java.rmi.server.hostname", "172.20.10.10");
					Registry registry = LocateRegistry.createRegistry(2022);
					registry.rebind(ServerIntf.class.getSimpleName(), new ServerImpl(frame));
					System.out.println("Server started");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Server");
		computers = new ArrayList<>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 489);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		setLocationRelativeTo(null);
	}

}
