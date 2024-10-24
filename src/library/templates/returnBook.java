package library.templates;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import library.dbhelper.DatabaseHelper;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Point;

public class returnBook extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblNewLabel_2;
	private JComboBox<String> titleField;
	private JComboBox<String> memberField;
	private JButton addStaffButton;
	private JButton btnClose;
	private static JDateChooser dateChooser;
	private static JDateChooser dateChooser_1;
	private static Date issueDate;
	private static Date dueDate;
	private static String title;
	private static String memberName;
	private Connection conn;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				returnBook frame = new returnBook();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public returnBook() {
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setResizable(false);
		setTitle("Staff Panel - Return Book");
		setBounds(100, 100, 651, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		titleField = new JComboBox<>();
		titleField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateDates();
			}
		});

		titleField.setFont(new Font("Arial", Font.PLAIN, 20));
		titleField.setBounds(295, 81, 168, 32);

		// Initialize lblNewLabel_2 before setting up the action listener
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_2.setFont(new Font("Aparajita", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(160, 300, 314, 32);

		memberField = new JComboBox<>();
		memberField.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateDates();
			}
		});
		memberField.setFont(new Font("Arial", Font.PLAIN, 20));
		memberField.setBounds(296, 134, 164, 32);

		getContentPane().add(lblNewLabel_2);

		// Load books and members into the combo boxes
		loadBooksAndMembers();

		getContentPane().add(titleField);
		getContentPane().add(memberField);

		JLabel nameLabel = new JLabel("Title:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		nameLabel.setBounds(165, 79, 55, 35);
		getContentPane().add(nameLabel);

		JLabel contactLabel = new JLabel("Member Name:");
		contactLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contactLabel.setBounds(149, 133, 141, 32);
		getContentPane().add(contactLabel);

		addStaffButton = new JButton("Return");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(e -> addBook());
		addStaffButton.setBounds(201, 335, 103, 40);
		getContentPane().add(addStaffButton);

		btnClose = new JButton("Close");
		btnClose.addActionListener(e -> setVisible(false));
		btnClose.setFont(new Font("Arial", Font.PLAIN, 20));
		btnClose.setBounds(348, 336, 92, 40);
		getContentPane().add(btnClose);

		JLabel lblNewLabel_1 = new JLabel("Return Book");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1.setBounds(250, 10, 135, 40);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Issue Date");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel.setBounds(149, 189, 96, 35);
		getContentPane().add(lblNewLabel);

		// Set up the date chooser
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setEnabled(false);
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser.setBounds(295, 192, 164, 32);
		dateChooser.setDateFormatString("yyyy-MM-dd");

		getContentPane().add(dateChooser);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDueDate.setBounds(152, 248, 96, 35);
		getContentPane().add(lblDueDate);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.getCalendarButton().setEnabled(false);
		dateChooser_1.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(295, 248, 164, 32);

		getContentPane().add(dateChooser_1);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(issueBook.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_3.setBounds(-569, -90, 1422, 775);
		getContentPane().add(lblNewLabel_3);
	}

	private void loadBooksAndMembers() {
		try (Connection connection = DatabaseHelper.connect()) {
			String sqlBooks = "SELECT b.id, b.title FROM books b inner JOIN transactions t  ON b.id = t.book_id where t.status =0 order by t.id asc";
			String sqlMembers = "SELECT m.id, m.name FROM members m inner JOIN transactions t ON m.id = t.member_id ORDER BY t.id asc";

			// Prepare SQL statements
			PreparedStatement psBooks = connection.prepareStatement(sqlBooks);
			PreparedStatement psMembers = connection.prepareStatement(sqlMembers);

			// Execute queries
			ResultSet rsBooks = psBooks.executeQuery();
			ResultSet rsMembers = psMembers.executeQuery();

			// Add "select" option in the dropdowns
			titleField.addItem("select");
			memberField.addItem("select");

			// Populate nameField with book titles
			while (rsBooks.next()) {
				titleField.addItem(rsBooks.getString("title"));
			}

			// Populate contactField with member names
			while (rsMembers.next()) {
				memberField.addItem(rsMembers.getString("name"));
			}

			// Close ResultSets and PreparedStatements
			rsBooks.close();
			rsMembers.close();
			psBooks.close();
			psMembers.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addBook() {
		String title = (String) titleField.getSelectedItem();
		String memberName = (String) memberField.getSelectedItem();
		java.util.Date issueUtilDate = dateChooser.getDate();
		java.util.Date dueUtilDate = dateChooser_1.getDate();

		// Validate input selections
		if (title == null || title.equals("select") || memberName == null || memberName.equals("select")) {
			JOptionPane.showMessageDialog(this, "Please select both book and member!", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (issueUtilDate == null || dueUtilDate == null) {
			JOptionPane.showMessageDialog(this, "Please select issue and due dates.", "Date Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Convert dates to SQL Date format
		java.sql.Date issueDate = new java.sql.Date(issueUtilDate.getTime());
		java.sql.Date dueDate = new java.sql.Date(dueUtilDate.getTime());

		try {
			conn = DatabaseHelper.connect();
			String sql1 = "UPDATE transactions SET status=1 WHERE book_id = ? AND member_id = ? AND return_date = ? AND borrow_date = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			
			System.out.println(getBookId(title));
			System.out.println(getMemberId(memberName));
			System.out.println(issueDate);
			System.out.println(dueDate);
			

			
			pstmt.setInt(1, getBookId(title));
			pstmt.setInt(2, getMemberId(memberName));
			pstmt.setDate(3, issueDate);
			pstmt.setDate(4, dueDate);
			System.out.println("SQL Query: " + pstmt.toString());
			int updated = pstmt.executeUpdate();
		
			System.out.println(updated);
			if (updated > 0) {
				JOptionPane.showMessageDialog(this, "Book returned successfully!");
				StaffHome.loadMemberData();
				StaffHome.loadBookData();
				setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "Error returning book. No matching transaction found.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error processing the return. Please try again.");
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Placeholder methods to retrieve IDs (implement these methods as needed)
	private int getBookId(String title) {
		int res = 0;
		try {
			conn = DatabaseHelper.connect();
			String sql = "select id from books where title=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, title);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) { // Moves the cursor to the first row, if present
				res = rs.getInt("id");
			} else {
				System.out.println("No data found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while getting id from books");
		}
		return res; // Placeholder return
	}

	private int getMemberId(String memberName) {
		int res = 0;
		try {
			conn = DatabaseHelper.connect();
			String sql = "select id from members where name=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
			} else {
				System.out.println("Invalid member ID.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while getting id from members");
		}
		return res; // Placeholder return
	}

	public int findBookCount(String member) {
		int res = 0;
		try {
			conn = DatabaseHelper.connect();
			String sql = "select count(member_id) from transactions t inner join members m on t.member_id=m.id where m.name=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, member);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				res = rs.getInt(1);
				System.out.println(res);
			} else {
				System.out.println("Invalid count of member.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error while getting count how book borrow member");
		}
		return res;
	}

	private void updateDates() {
		// Get selected title and member
		title = (String) titleField.getSelectedItem();
		memberName = (String) memberField.getSelectedItem();

		// Check if either title or member is not selected
		if (title == null || title.equals("select") || memberName == null || memberName.equals("select")) {
			issueDate = null;
			dueDate = null;
			return;
		}

		try {
			conn = DatabaseHelper.connect();

			// Query to get issue and due dates for the selected book and member
			String sql = "SELECT borrow_date, return_date FROM transactions t "
					+ "INNER JOIN books b ON t.book_id = b.id " + "INNER JOIN members m ON t.member_id = m.id "
					+ "WHERE b.title = ? AND m.name = ? AND t.status = 0";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, memberName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				dateChooser_1.setDate(rs.getDate(1));
				dateChooser.setDate(rs.getDate(2));

			} else {
				dateChooser_1.setDate(null);
				dateChooser.setDate(null);

			}

			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error fetching date data", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			// Ensure connection is closed
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
