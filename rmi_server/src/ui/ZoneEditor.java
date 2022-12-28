package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Zone;
import repos.ZoneRepository;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ZoneEditor extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfID;
	private JTextField tfName;

	/**
	 * Create the panel.
	 */
	public ZoneEditor(Main main, Zone zone) {
		ZoneRepository zoneRepo = new ZoneRepository();
		JLabel lblNewLabel = new JLabel("Edit Zone");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tfID = new JTextField(String.valueOf(zone.getId()));
		tfID.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfID.setEditable(false);
		tfID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		tfName = new JTextField(zone.getName());
		tfName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tfName.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = tfName.getText().trim();
				if (name.isEmpty()) {
					JOptionPane.showMessageDialog(new JFrame(), "Name is empty!");
					return;
				}

				zone.setName(name);
				if (zoneRepo.update(zone)) {
					JOptionPane.showMessageDialog(new JFrame(), "Update success!");
					main.changeLayout(new ZoneView(main));
				}
			}
		});
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main.changeLayout(new ZoneView(main));
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(236)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(238, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(166)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tfName)
						.addComponent(tfID, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))
					.addContainerGap(150, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(207)
					.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(27)
					.addComponent(btnNewButton)
					.addGap(212))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addComponent(lblNewLabel)
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tfID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(btnNewButton))
					.addContainerGap(139, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}