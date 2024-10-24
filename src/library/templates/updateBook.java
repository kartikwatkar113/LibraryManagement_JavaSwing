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

public class updateBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameField;
	private JTextField contactField;
	private JButton addStaffButton;
	private JButton btnClose;
	private static int bid;
	private static String title;
	private static String author;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					updateBook frame = new updateBook();
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
		bid = id;
		new updateBook().setVisible(true);
	}

	public updateBook() {

		try {
			Connection connection = DatabaseHelper.connect();
			String sql = "SELECT title, author FROM books WHERE id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, bid);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				title = resultSet.getString("title"); // Column name instead of index
				author = resultSet.getString("author");

			} else {
				System.out.println("No book found with ID: " + bid);
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
		setTitle("Staff Panel - Update Book");
		setSize(666, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel nameLabel = new JLabel("Book Title:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(160, 110, 140, 40);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameField.setBounds(302, 110, 187, 40);
		nameField.setText(title);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Author:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(165, 176, 109, 46);
		getContentPane().add(contactLabel);

		contactField = new JTextField();
		contactField.setFont(new Font("Arial", Font.PLAIN, 20));
		contactField.setBounds(302, 179, 187, 40);
		contactField.setText(author);
		getContentPane().add(contactField);

		addStaffButton = new JButton("Update");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBookByid();
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

		lblNewLabel = new JLabel("Update Book");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel.setBounds(236, 10, 187, 40);
		getContentPane().add(lblNewLabel);

		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(updateStaff.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_1.setBounds(-558, -31, 1379, 653);
		getContentPane().add(lblNewLabel_1);
	}

	private void updateBookByid() {
		String title = nameField.getText();
		String author = contactField.getText();

		try {

			if (!(title.isEmpty() || author.isEmpty())) {
				if (!author.matches("^[A-Za-z][A-Za-z ]*$")) {
					JOptionPane.showMessageDialog(this,
							"Invalid author. It should not start with a space, digit, or special character.");
					return;
				}

				if (title.startsWith(" ")) {
					JOptionPane.showMessageDialog(this, "Book title should not start with a space.");
					return;
				}
				Connection conn = DatabaseHelper.connect();

				String sql = "update books set title=?, author=? where id=?";
				// String sql = "INSERT INTO staff(name, contact, role) VALUES(?, ?, ?) where
				// id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, author);

				pstmt.setInt(3, bid);
				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "book updated successfully!");
				StaffHome.loadMemberData();
				StaffHome.loadBookData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please Fillup the data!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error updating book.");
		}
	}

}
