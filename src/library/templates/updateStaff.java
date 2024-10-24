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
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Point;

public class updateStaff extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField contactField;
	private JComboBox roleField;
	private JButton addStaffButton;
	private JButton btnClose;
	private static int sid;
	private static String sname;
	private static String scontact;
	private static String srole;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateStaff frame = new updateStaff();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param id
	 */
	public static void fetchId(int id) {
		sid = id;
		new updateStaff().setVisible(true);
	}

	public updateStaff() {

		try {
			Connection connection = DatabaseHelper.connect();
			String sql = "SELECT name, contact, role FROM staff WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, sid);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				sname = resultSet.getString("name"); // Column name instead of index
				scontact = resultSet.getString("contact");
				srole = resultSet.getString("role");

			} else {
				System.out.println("No staff found with ID: " + sid);
			}

			// Close the connection
			resultSet.close();
			preparedStatement.close();
			connection.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		setAlwaysOnTop(true);
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setResizable(false);
		setTitle("Admin Panel - Update Staff");
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
		nameField.setText(sname);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(165, 176, 109, 46);
		getContentPane().add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("Arial", Font.PLAIN, 20));
		contactField.setBounds(284, 179, 187, 40);
		contactField.setText(scontact);
		getContentPane().add(contactField);

		JLabel roleLabel = new JLabel("Role:");
		roleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		roleLabel.setBounds(165, 252, 92, 46);
		getContentPane().add(roleLabel);

		roleField = new JComboBox();
		roleField.setFont(new Font("Arial", Font.PLAIN, 20));
		roleField.addItem(srole);
		roleField.addItem("Assistant Librarian");
		roleField.addItem("Library Clerk");
		roleField.addItem("Library Technician");
		roleField.addItem("IT Specialist");
		roleField.addItem("Volunteer");
		roleField.addItem("Security Guard");

		roleField.setBounds(284, 252, 187, 46);
		getContentPane().add(roleField);

		addStaffButton = new JButton("Update");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateStaffByid();
			}
		});
		addStaffButton.setBounds(214, 335, 103, 40);
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

		lblNewLabel = new JLabel("Update Staff");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(253, 10, 187, 40);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(updateStaff.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_1.setBounds(-558, -31, 1379, 653);
		getContentPane().add(lblNewLabel_1);
	}

	private void updateStaffByid() {
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

				String sql = "update staff set name=?, contact=?, role=? where id=?";
				// String sql = "INSERT INTO staff(name, contact, role) VALUES(?, ?, ?) where
				// id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, name);
				pstmt.setString(2, contact);
				pstmt.setString(3, role);
				pstmt.setInt(4, sid);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "Staff update successfully!");
				Home.loadStaffData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please Fillup the data!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error updating staff.");
		}
	}

}
