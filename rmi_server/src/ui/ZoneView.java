package ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.IpAddress;
import models.Room;
import models.Zone;
import repos.RoomRepository;
import repos.ZoneRepository;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;

public class ZoneView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZoneRepository zoneRepo;
	private Main main;
	private String page = "";
	private JList<Zone> list;
	private List<Zone> zones;
	private JTextField tfName;
	private JTextField tfStartIp;
	private JTextField tfEndIp;
	private JTextField tfNumberOfRoom;
	private RoomRepository roomRepo;

	/**
	 * Create the panel.
	 * 
	 */
	public ZoneView(Main main) {
		this.main = main;
		zoneRepo = new ZoneRepository();
		roomRepo = new RoomRepository();
		zones = zoneRepo.getAll();
		list = new JList<>(new Vector<Zone>(zones));
		list.setFont(new Font("Tahoma", Font.BOLD, 15));
		list.setVisibleRowCount(10);
		list.setCellRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof Zone) {
					// Here value will be of the Type 'CD'
					Zone z = (Zone) value;
					((JLabel) renderer).setText(z.getName() + ": " + z.getCountComputer() + " computers");
				}
				return renderer;
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JList l = (JList) evt.getSource();
				if (evt.getClickCount() == 2) {
//		            int index = l.locationToIndex(evt.getPoint());
					main.changeLayout(new RoomView(main, list.getSelectedValue().getId()));
				}
			}
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Zone list");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Zone z = list.getSelectedValue();
				if (z == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose zone for delete!");
					return;
				}

				if (zoneRepo.delete(z.getId())) {
					JOptionPane.showMessageDialog(new JFrame(), "Delete success");
					reload();
				}
			}
		});

		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Zone z = list.getSelectedValue();
				if (z == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Please choose zone for edit!");
					return;
				}

				main.changeLayout(new ZoneEditor(main, z));
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00EAm Zone", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 122, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(7)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 321, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnEdit, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		panel.setLayout(null);
		
		tfName = new JTextField();
		tfName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfName.setBounds(10, 43, 189, 26);
		panel.add(tfName);
		tfName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tên Zone");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 24, 180, 13);
		panel.add(lblNewLabel_1);
				
		tfStartIp = new JTextField();
		tfStartIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfStartIp.setBounds(10, 102, 189, 26);
		panel.add(tfStartIp);
		tfStartIp.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("IP bắt đầu");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(10, 79, 175, 13);
		panel.add(lblNewLabel_1_1);
						
		tfEndIp = new JTextField();
		tfEndIp.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfEndIp.setBounds(10, 161, 189, 26);
		panel.add(tfEndIp);
		tfEndIp.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("IP kết thúc");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1_1.setBounds(10, 138, 175, 13);
		panel.add(lblNewLabel_1_1_1);
								
		tfNumberOfRoom = new JTextField();
		tfNumberOfRoom.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfNumberOfRoom.setBounds(10, 223, 189, 26);
		panel.add(tfNumberOfRoom);
		tfNumberOfRoom.setColumns(10);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Số phòng");
		lblNewLabel_1_1_1_1.setBounds(10, 197, 175, 19);
		panel.add(lblNewLabel_1_1_1_1);
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
										
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(43, 267, 156, 30);
		panel.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = tfName.getText();
				if (name.trim().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Name is empty!");
					return;
				}
				IpAddress startIp, endIp;
				try {
					startIp = new IpAddress(tfStartIp.getText());
					endIp = new IpAddress(tfEndIp.getText());
					if (startIp.compareTo(endIp)) {
						JOptionPane.showMessageDialog(new JFrame(), "Invalid ip address!");
						return;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(new JFrame(), "Invalid ip address!");
					return;
				}
				int numbersOfRoom;
				try {
					numbersOfRoom = Integer.parseInt(tfNumberOfRoom.getText());
				} catch (Exception e1) {
					return;
				}

				Zone z = zoneRepo.create(new Zone(name, startIp.toString(), endIp.toString()));

				if (z != null) {
					JOptionPane.showMessageDialog(new JFrame(), "Create success!");
					tfName.setText("");

					long sub = z.getEndIp().subtract(z.getStartIp());

					IpAddress start = z.getStartIp(), end = z.getStartIp().add(sub / numbersOfRoom - 1);

					for (int i = 0; i < numbersOfRoom; i++) {
						Room r = new Room("Room " + (i+1), start.toString(), end.toString(), z.getId());
						end.plus();
						start = end;
						end = start.add(sub/numbersOfRoom - 1);
						roomRepo.create(r);
					}
					ZoneView.this.reload();
				}
			}
		});
		setLayout(groupLayout);

	}

	@Override
	public void reload() {
		System.out.println("reload zone");
		DefaultListModel<Zone> dlm = new DefaultListModel<>();
		zones = zoneRepo.getAll();
		for (Zone z : zones) {
			dlm.addElement(z);
		}
		list.setModel(dlm);
	}
}
