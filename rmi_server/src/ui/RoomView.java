package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.Room;
import repos.RoomRepository;
import repos.ZoneRepository;

public class RoomView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoomRepository roomRepo;
	private ZoneRepository zoneRepo;
	private List<Room> rooms;
	private JList<Room> list;
	private int zoneId;

	/**
	 * Create the panel.
	 * 
	 */
	public RoomView(Main main, int zoneId) {
		this.zoneId = zoneId;
		roomRepo = new RoomRepository();
		zoneRepo = new ZoneRepository();
		// get all room by zone
		rooms = roomRepo.getByZone(zoneId);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeLayout(new ZoneView(main));
			}
		});
		
		JLabel lblRoomView = new JLabel("Room View (" + zoneRepo.getZoneName(zoneId) + ")");
		lblRoomView.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addComponent(lblRoomView, GroupLayout.PREFERRED_SIZE, 344, GroupLayout.PREFERRED_SIZE)))
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBack)
						.addComponent(lblRoomView, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
				list = new JList<>(new Vector<Room>(rooms));
				scrollPane.setViewportView(list);
				list.setFont(new Font("Tahoma", Font.BOLD, 15));
				list.setVisibleRowCount(10);
				
				list.setCellRenderer(new DefaultListCellRenderer() {
					private static final long serialVersionUID = 1L;

					@Override
					public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
							boolean cellHasFocus) {
						Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
						if (renderer instanceof JLabel && value instanceof Room) {
							Room r = (Room) value;
							String text = r.getName() + " (" 
										+ r.getStartIp() + " - "
										+ r.getEndIp()
										+ ") - total: " + r.getSumComputer() + ", online: " + r.getOnline();
							((JLabel) renderer).setText(text);
						}
						return renderer;
					}
				});
				
						list.addMouseListener(new MouseAdapter() {
							public void mouseClicked(MouseEvent evt) {
								JList l = (JList) evt.getSource();
								if (evt.getClickCount() == 2) {
									main.changeLayout(new ComputerView(main, zoneId, list.getSelectedValue().getId()));
								}
							}
						});
						
								list.addListSelectionListener(new ListSelectionListener() {
						
									@Override
									public void valueChanged(ListSelectionEvent e) {
										if (!e.getValueIsAdjusting()) {
						//					kkmain.changeLayout(new ComputerView(main, zoneId, list.getSelectedValue().getId()));
										}
									}
								});
		setLayout(groupLayout);

	}

	@Override
	public void reload() {
		DefaultListModel<Room> dlm = new DefaultListModel<>();
		rooms = roomRepo.getByZone(zoneId);
		for (Room r : rooms) {
			dlm.addElement(r);
		}

		list.setModel(dlm);
	}
}
