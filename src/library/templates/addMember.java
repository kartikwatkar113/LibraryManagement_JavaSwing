package library.templates;

import java.awt.EventQueue;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import library.dbhelper.DatabaseHelper;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Point;

public class addMember extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField nameField;
	private JTextField contactField;
	private JButton addStaffButton;
	private JButton btnClose;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private static String barcode;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addMember frame = new addMember();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public addMember() {
		setAlwaysOnTop(true);
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setResizable(false);
		setTitle("Staff Panel - Add Member");
		setSize(638, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(165, 127, 141, 40);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameField.setBounds(284, 127, 187, 40);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(165, 227, 109, 46);
		getContentPane().add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("Arial", Font.PLAIN, 20));
		contactField.setBounds(284, 230, 187, 40);
		getContentPane().add(contactField);

		addStaffButton = new JButton("Add");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addMember();
			}
		});
		addStaffButton.setBounds(214, 335, 92, 40);
		getContentPane().add(addStaffButton);

		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setFont(new Font("Arial", Font.PLAIN, 20));
		btnClose.setBounds(348, 336, 92, 40);
		getContentPane().add(btnClose);

		lblNewLabel_1 = new JLabel("Adding Members");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1.setBounds(214, 10, 204, 46);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(addMember.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel.setBounds(-570, 0, 1306, 602);
		getContentPane().add(lblNewLabel);
	}

	private void addMember() {
		String name = nameField.getText();
		String contact = contactField.getText();

		barcode = UUID.randomUUID().toString().substring(0, 8);
		try {
			if (!(name.isEmpty() || contact.isEmpty())) {
				// Validation for name field (should not start with space, digit, or special
				// character)
				if (!name.matches("^[A-Za-z][A-Za-z ]*$")) {
					JOptionPane.showMessageDialog(this,
							"Invalid name. It should not start with a space, digit, or special character.");
					return;
				}

				// Validation for contact field (should start with 7-9 and be 10 digits long)
				if (!contact.matches("^[7-9][0-9]{9}$")) {
					JOptionPane.showMessageDialog(this,
							"Invalid contact. It should start with 7-9 and be exactly 10 digits long.");
					return;
				}
				Connection conn = DatabaseHelper.connect();
				String sql1 = "select count(*) from members where barcode='?'";
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				ResultSet rs = pstmt1.executeQuery();
				if (rs.getInt(1) != 0) {
					System.out.println();
					barcode = UUID.randomUUID().toString().substring(0, 8);
				}
				String sql = "INSERT INTO members(name, contact, barcode) VALUES(?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);
				pstmt.setString(2, contact);
				pstmt.setString(3, barcode);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "Member added successfully!");
				StaffHome.loadMemberData();
				StaffHome.loadBookData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please Fillup the data!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding Members.");
		}
	}

}
