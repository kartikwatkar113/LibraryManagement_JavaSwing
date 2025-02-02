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
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Point;

public class addBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField contactField;
	private JButton addStaffButton;
	private JButton btnClose;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					addBook frame = new addBook();
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
	public addBook() {
		setAlwaysOnTop(true);
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setResizable(false);
		setTitle("Staff Panel - Add Book");
		setSize(652, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel nameLabel = new JLabel("Title:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(165, 127, 141, 40);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 20));
		nameField.setBounds(284, 127, 187, 40);
		getContentPane().add(nameField);

		JLabel contactLabel = new JLabel("Author:");
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
				addBook();
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

		lblNewLabel_1 = new JLabel("Add Books");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1.setBounds(244, 10, 135, 52);
		getContentPane().add(lblNewLabel_1);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(addBook.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel.setBounds(-569, -11, 1280, 623);
		getContentPane().add(lblNewLabel);
	}

	private void addBook() {
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
				String sql = "INSERT INTO books(title, author) VALUES(?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, title);
				pstmt.setString(2, author);

				pstmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "Book added successfully!");
				StaffHome.loadMemberData();
				StaffHome.loadBookData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Please Fillup the data!");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding Books.");
		}
	}

}
