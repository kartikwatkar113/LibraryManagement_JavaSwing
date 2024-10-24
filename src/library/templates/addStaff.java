package library.templates;

import java.awt.EventQueue;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import library.dbhelper.DatabaseHelper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Point;

public class addStaff extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField contactField;
	private JComboBox roleField;
	private JButton addStaffButton;
	private JButton btnClose;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addStaff frame = new addStaff();
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
	public addStaff() {
		setAlwaysOnTop(true);
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setResizable(false);
		setTitle("Admin Panel - Add Staff");
		setSize(666, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel nameLabel = new JLabel("Staff Name:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(165, 110, 141, 40);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameField.setBounds(284, 110, 187, 40);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(165, 176, 109, 46);
		getContentPane().add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("Arial", Font.PLAIN, 20));
		contactField.setBounds(284, 179, 187, 40);
		getContentPane().add(contactField);

		JLabel roleLabel = new JLabel("Role:");
		roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		roleLabel.setBounds(165, 252, 92, 46);
		getContentPane().add(roleLabel);

		roleField = new JComboBox();
		roleField.setFont(new Font("Arial", Font.PLAIN, 20));
		roleField.addItem("select");
		roleField.addItem("Assistant Librarian");
		roleField.addItem("Library Clerk");
		roleField.addItem("Library Technician");
		roleField.addItem("IT Specialist");
		roleField.addItem("Volunteer");
		roleField.addItem("Security Guard");

		roleField.setBounds(284, 252, 187, 46);
		getContentPane().add(roleField);

		addStaffButton = new JButton("Add");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addStaff();
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

		lblNewLabel = new JLabel("Add Staff");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(256, 23, 167, 30);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(addStaff.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_1.setBounds(-565, -16, 1353, 640);
		getContentPane().add(lblNewLabel_1);
	}

	private void addStaff() {
		String name = nameField.getText();
		String contact = contactField.getText();
		String role = (String) roleField.getSelectedItem();

		try {
			if (!(name.isEmpty() || contact.isEmpty() || role.isBlank())) {
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
				String sql = "INSERT INTO staff(name, contact, role) VALUES(?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, name);
				pstmt.setString(2, contact);
				pstmt.setString(3, role);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "Staff added successfully!");
				Home.loadStaffData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please fill up the data!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding staff.");
		}
	}

}
