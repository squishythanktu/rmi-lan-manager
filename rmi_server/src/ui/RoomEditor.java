//package ui;
//
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.List;
//
//import javax.swing.GroupLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//import models.Room;
//import models.Zone;
//import repos.RoomRepository;
//import repos.ZoneRepository;
//
//public class RoomEditor extends View{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private JTextField tfId;
//	private JTextField tfName;
//
//	/**
//	 * Create the panel.
//	 */
//	public RoomEditor(Main main, Room room) {
//		int oldZoneId = room.getZoneId();
//		RoomRepository roomRepo = new RoomRepository();
//		ZoneRepository zoneRepo = new ZoneRepository();
//		JLabel lblNewLabel = new JLabel("Edit Room");
//		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		
//		JLabel lblNewLabel_1 = new JLabel("ID");
//		
//		tfId = new JTextField(String.valueOf(room.getId()));
//		tfId.setEditable(false);
//		tfId.setColumns(10);
//		
//		JLabel lblNewLabel_2 = new JLabel("Name");
//		
//		JLabel lblNewLabel_3 = new JLabel("Zone");
//		
//		tfName = new JTextField(room.getName());
//		tfName.setColumns(10);
//		
//		List<Zone> zones = zoneRepo.getAll();
//		String[] titles = new String[zones.size()];
//		int index = 0;
//		for (int i=0; i<zones.size(); i++) {
//			titles[i] = zones.get(i).getName();
//			if (zones.get(i).getId() == room.getZoneId()) {
//				index = i;
//			}
//		}
//
//		JComboBox cbZone = new JComboBox(titles);
//		cbZone.setSelectedIndex(index);
//		
//		JButton btnNewButton = new JButton("Cancel");
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				main.changeLayout(new RoomView(main, oldZoneId));
//			}
//		});
//		
//		JButton btnNewButton_1 = new JButton("Save");
//		btnNewButton_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int selectedIndex = cbZone.getSelectedIndex();
//				String name = tfName.getText().trim();
//				if (name.isEmpty()) {
//					JOptionPane.showMessageDialog(new JFrame(), "Name is empty");
//					return;
//				}
//				room.setZoneId(zones.get(selectedIndex).getId());
//				room.setName(name);
//				if (roomRepo.update(room)) {
//					JOptionPane.showMessageDialog(new JFrame(), "Update success");
//					main.changeLayout(new RoomView(main, room.getZoneId()));
//				} else {
//					JOptionPane.showMessageDialog(new JFrame(), "Update failed");
//					main.changeLayout(new RoomView(main, oldZoneId));
//				}
//			}
//		});
//		GroupLayout groupLayout = new GroupLayout(this);
//		groupLayout.setHorizontalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(218)
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
//								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
//								.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
//							.addGap(54)
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
//								.addComponent(tfName)
//								.addComponent(tfId)
//								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
//								.addComponent(cbZone, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(238)
//							.addComponent(btnNewButton)
//							.addGap(45)
//							.addComponent(btnNewButton_1)))
//					.addContainerGap(350, Short.MAX_VALUE))
//		);
//		groupLayout.setVerticalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addGap(53)
//					.addComponent(lblNewLabel)
//					.addGap(49)
//					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(lblNewLabel_1)
//						.addComponent(tfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(18)
//					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(lblNewLabel_2)
//						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(18)
//					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(lblNewLabel_3)
//						.addComponent(cbZone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
//					.addGap(26)
//					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
//						.addComponent(btnNewButton_1)
//						.addComponent(btnNewButton))
//					.addContainerGap(265, Short.MAX_VALUE))
//		);
//		setLayout(groupLayout);
//
//	}
//}
