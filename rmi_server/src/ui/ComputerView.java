package ui;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.mysql.cj.xdevapi.ClientImpl;

import interfaces.ClientIntf;
import interfaces.ServerIntf;
import models.Computer;
import repos.ComputerRepository;
import repos.RoomRepository;
import repos.ZoneRepository;

import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class ComputerView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComputerRepository computerRepo;
	private RoomRepository roomRepo;
	private ZoneRepository zoneRepo;
	private List<Computer> computers;
	private JList<Computer> list;
	private int roomId;
	private int zoneId;

	/**
	 * Create the panel.
	 * 
	 */
	public ComputerView(Main main, int zoneId, int roomId) {
		this.roomId = roomId;
		this.zoneId = zoneId;
		computerRepo = new ComputerRepository();
		roomRepo = new RoomRepository();
		zoneRepo = new ZoneRepository();
		computers = computerRepo.getAllByRoom(roomId);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeLayout(new RoomView(main, zoneId));
			}
		});
		
		JButton btnCapture = new JButton("Capture img");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Computer c = list.getSelectedValue();
				if(c == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose 1 computer to take screenshot");
					return;
				}
				else if(!c.isOnline()) {
					JOptionPane.showMessageDialog(new JFrame(), "Client computer is offline!");
					return;	
				}
				else {
					try {
						Registry registry = LocateRegistry.getRegistry(c.getIpAddress(), 2023);
						ClientIntf demoIntf = (ClientIntf) registry.lookup(ClientIntf.class.getSimpleName());
						JLabel picLabel = new JLabel(new ImageIcon(demoIntf.getDesktop()));
						JOptionPane.showMessageDialog(null, picLabel, "Capture img", JOptionPane.PLAIN_MESSAGE, null);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnCapture.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblNewLabel = new JLabel("Computer View ("
										+ roomRepo.getRoomName(roomId) + " - "
										+ zoneRepo.getZoneName(zoneId) + ")");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(27, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCapture, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
					.addGap(21))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(45)
								.addComponent(btnCapture, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnBack)))
					.addGap(27))
		);
		list = new JList<>(new Vector<Computer>(computers));
		scrollPane.setViewportView(list);
		list.setFont(new Font("Tahoma", Font.BOLD, 15));
		list.setVisibleRowCount(10);
		list.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof Computer) {
					Computer c = (Computer) value;
					((JLabel) renderer).setText(c.getName() + "("+ c.getIpAddress() + ")" + ": " + (c.isOnline() ? "online" : "offline"));
				}
				return renderer;
			}
		});
		
				list.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
						}
					}
				});
		setLayout(groupLayout);

	}

	private void updateJlist(List<Computer> l) {
		DefaultListModel<Computer> dlm = new DefaultListModel<>();
		l.forEach(l1 -> dlm.addElement(l1));
		list.setModel(dlm);
	}
	public void reload() {
		System.out.println("reload computer");
		computers = computerRepo.getAllByRoom(this.roomId);
		updateJlist(computers);
	}
}