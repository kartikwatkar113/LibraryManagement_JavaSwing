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

public class updateMember extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField contactField;
	private JButton addStaffButton;
	private JButton btnClose;
	private static int mid;
	private static String mname;
	private static String mcontact;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateMember frame = new updateMember();
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
		mid = id;
		new updateMember().setVisible(true);
	}

	public updateMember() {

		try {
			Connection connection = DatabaseHelper.connect();
			String sql = "SELECT name, contact FROM members WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, mid);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				mname = resultSet.getString("name"); // Column name instead of index
				mcontact = resultSet.getString("contact");

			} else {
				System.out.println("No Member found with ID: " + mid);
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
		setTitle("Staff Panel - Update member");
		setSize(666, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel nameLabel = new JLabel("Member Name:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(160, 110, 140, 40);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameField.setBounds(302, 110, 187, 40);
		nameField.setText(mname);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Contact:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(165, 176, 109, 46);
		getContentPane().add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("Arial", Font.PLAIN, 20));
		contactField.setBounds(302, 179, 187, 40);
		contactField.setText(mcontact);
		getContentPane().add(contactField);

		addStaffButton = new JButton("Update");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateMemberByid();
			}
		});
		addStaffButton.setBounds(214, 335, 109, 40);
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

		lblNewLabel = new JLabel("Update Member");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(236, 10, 187, 40);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(updateStaff.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_1.setBounds(-558, -31, 1379, 653);
		getContentPane().add(lblNewLabel_1);
	}

	private void updateMemberByid() {
		String name = nameField.getText();
		String contact = contactField.getText();
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

				String sql = "update members set name=?, contact=? where id=?";
				// String sql = "INSERT INTO staff(name, contact, role) VALUES(?, ?, ?) where
				// id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, name);
				pstmt.setString(2, contact);

				pstmt.setInt(3, mid);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "member updated successfully!");
				StaffHome.loadMemberData();
				StaffHome.loadBookData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please Fillup the data!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error updating member.");
		}
	}

}
