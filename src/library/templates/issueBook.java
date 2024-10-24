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

public class issueBook extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel lblNewLabel_2;
	private JComboBox<String> titleField;
	private JComboBox<String> memberField;
	private JButton addStaffButton;
	private JButton btnClose;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private Connection conn;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				issueBook frame = new issueBook();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public issueBook() {
		setLocation(new Point(325, 125));
		setUndecorated(true);
		setTitle("Staff Panel - Issue Book");
		setBounds(100, 100, 651, 514);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		titleField = new JComboBox<>();

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
				
				int res = findBookCount(memberField.getSelectedItem().toString());
				if (res > 0) {
					lblNewLabel_2.setText("This member currently has " + res + " book borrowed !!");
				}else {
					lblNewLabel_2.setText(" ");
					
				}
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

		addStaffButton = new JButton("Issue");
		addStaffButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		addStaffButton.addActionListener(e -> addBook());
		addStaffButton.setBounds(201, 335, 92, 40);
		getContentPane().add(addStaffButton);

		btnClose = new JButton("Close");
		btnClose.addActionListener(e -> setVisible(false));
		btnClose.setFont(new Font("Arial", Font.PLAIN, 20));
		btnClose.setBounds(348, 336, 92, 40);
		getContentPane().add(btnClose);

		JLabel lblNewLabel_1 = new JLabel("Issue Book");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_1.setBounds(250, 10, 135, 40);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("Issue Date");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel.setBounds(149, 189, 96, 35);
		getContentPane().add(lblNewLabel);

		// Set up the date chooser
		dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser.setBounds(295, 192, 164, 32);
		dateChooser.setDateFormatString("yyyy-MM-dd");

		// Set maximum selectable date to today
		dateChooser.setMaxSelectableDate(new Date());

		// Set default date to today's date
		dateChooser.setDate(new Date());

		getContentPane().add(dateChooser);

		JLabel lblDueDate = new JLabel("Due Date");
		lblDueDate.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDueDate.setBounds(152, 248, 96, 35);
		getContentPane().add(lblDueDate);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(295, 248, 164, 32);

		// Calculate tomorrow's date (today + 1 day)
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();

		// Set tomorrow's date as the default for the due date chooser
		dateChooser_1.setDate(tomorrow);

		// Set the minimum selectable date for the due date chooser (cannot select past
		// dates)
		dateChooser_1.setMinSelectableDate(tomorrow);

		getContentPane().add(dateChooser_1);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(issueBook.class.getResource("/images/addStaff1.jpg")));
		lblNewLabel_3.setBounds(-569, -90, 1422, 775);
		getContentPane().add(lblNewLabel_3);
	}

	private void loadBooksAndMembers() {
		try (Connection connection = DatabaseHelper.connect()) {
			String sqlBooks = "SELECT b.id, b.title FROM books b LEFT JOIN transactions t  ON b.id = t.book_id where t.book_id is null or t.status !=0";
			String sqlMembers = "SELECT m.id, m.name FROM members m LEFT JOIN transactions t ON m.id = t.member_id GROUP BY m.id, m.name HAVING COUNT(t.book_id) < 3 ORDER BY m.id DESC";

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
		Date selectedDate = dateChooser.getDate();
		Date dueDate = dateChooser_1.getDate();
		// Validation for title and member name
		if (title == null || title.equals("select") || memberName == null || memberName.equals("select")) {
			JOptionPane.showMessageDialog(this, "Please select both book and member!", "Input Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validation for date
		if (selectedDate == null) {
			JOptionPane.showMessageDialog(this, "Please select issue date...", "Date Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Validation for date
		if (dueDate == null) {
			JOptionPane.showMessageDialog(this, "Please select due date...", "Date Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Proceed with issuing the book
		try {
			conn = DatabaseHelper.connect();
			String sql = "INSERT INTO transactions (member_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// Assuming that you have methods to get IDs from titles and names
			int bookId = getBookId(title);
			int memberId = getMemberId(memberName);

			pstmt.setInt(1, memberId);
			pstmt.setInt(2, bookId);
			pstmt.setDate(3, new java.sql.Date(selectedDate.getTime()));
			pstmt.setDate(4, new java.sql.Date(dueDate.getTime()));
			
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "Book issued successfully!");
			StaffHome.loadMemberData();
			StaffHome.loadBookData();
			setVisible(false);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error issuing book.");
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
}
