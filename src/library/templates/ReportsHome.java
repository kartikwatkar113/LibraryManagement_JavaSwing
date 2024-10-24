package library.templates;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.toedter.calendar.JDateChooser;

import library.dbhelper.DatabaseHelper;
import java.awt.Component;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ReportsHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table1;
	private JTable table2;
	private static DefaultTableModel model;
	private static DefaultTableModel model1;
	private static DefaultTableModel model2;
	private JDateChooser dateChooser;
	private JDateChooser dateChooser_1;
	private static JComboBox<String> comboBox;
	private static JComboBox<String> comboBox_1;
	private Connection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportsHome frame = new ReportsHome();
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
	public ReportsHome() {
		setSize(new Dimension(1366, 768));
		setPreferredSize(new Dimension(1366, 768));
		setTitle("Staff Pannel-Library Management System (Reports)");
		setResizable(false);
		setMinimumSize(new Dimension(1366, 768));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(277, 156, 1366, 768); // Set centered position

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton addMemberbtn = new JButton("Add Member");
		addMemberbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new addMember().setVisible(true);
			}
		});
		addMemberbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		addMemberbtn.setBounds(37, 46, 199, 55);
		contentPane.add(addMemberbtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(200, 200));
		scrollPane.setMaximumSize(new Dimension(200, 200));
		scrollPane.setMinimumSize(new Dimension(200, 200));
		scrollPane.setPreferredSize(new Dimension(200, 200));
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setBounds(304, 146, 1038, 155);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(false);
		table.setCellSelectionEnabled(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setSize(new Dimension(200, 200));
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setViewportView(table);

		// Initialize table model with column names
		model = new DefaultTableModel(new Object[] { "Member ID", "Name", "Contact", "Book Title", "Book Status",
				"Issue Date", "Return Date" }, 0);
		table.setModel(model);

		// Set font size for table row contents
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(40); // Set row height for better readability

		// Set font size for table header contents
		table.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
		table.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Optional: increase header height

		// Optional: Auto-resize columns to fit the table
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add the table to the scroll pane
		scrollPane.setViewportView(table);

		// Optional: Set focus traversal policy if needed
		scrollPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { table }));

		// Load staff data into the table

		/* ======================================= */

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setSize(new Dimension(200, 200));
		scrollPane1.setMaximumSize(new Dimension(200, 200));
		scrollPane1.setMinimumSize(new Dimension(200, 200));
		scrollPane1.setPreferredSize(new Dimension(200, 200));
		scrollPane1.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane1.setBounds(304, 346, 1038, 155);
		contentPane.add(scrollPane1);

		table1 = new JTable();
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table1.setEnabled(false);
		table1.setCellSelectionEnabled(true);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table1.setSize(new Dimension(200, 200));
		table1.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane1.setViewportView(table1);

		// Initialize table model with column names
		model1 = new DefaultTableModel(
				new Object[] { "Book ID", "Book Title", "Book Status", "Member", "Issue Date", "Return Date" }, 0);
		table1.setModel(model1);

		// Set font size for table row contents
		table1.setFont(new Font("Arial", Font.PLAIN, 20));
		table1.setRowHeight(40); // Set row height for better readability

		// Set font size for table header contents
		table1.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
		table1.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Optional: increase header height

		// Optional: Auto-resize columns to fit the table
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add the table to the scroll pane
		scrollPane1.setViewportView(table1);

		// Optional: Set focus traversal policy if needed
		scrollPane1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { table1 }));

		// Load staff data into the table
		// loadBookData();

		/* ========================================================================= */

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setSize(new Dimension(200, 200));
		scrollPane2.setMaximumSize(new Dimension(200, 200));
		scrollPane2.setMinimumSize(new Dimension(200, 200));
		scrollPane2.setPreferredSize(new Dimension(200, 200));
		scrollPane2.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane2.setBounds(304, 553, 1038, 155);
		contentPane.add(scrollPane2);

		table2 = new JTable();
		table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table2.setEnabled(false);
		table2.setCellSelectionEnabled(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table2.setSize(new Dimension(200, 200));
		table2.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane2.setViewportView(table2);

		// Initialize table model with column names
		model2 = new DefaultTableModel(
				new Object[] { "Book ID", "Book Title", "Book Status", "Member", "Issue Date", "Return Date" }, 0);
		table2.setModel(model2);

		// Set font size for table row contents
		table2.setFont(new Font("Arial", Font.PLAIN, 20));
		table2.setRowHeight(40); // Set row height for better readability

		// Set font size for table header contents
		table2.getTableHeader().setFont(new Font("Arial", Font.PLAIN, 20));
		table2.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Optional: increase header height

		// Optional: Auto-resize columns to fit the table
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add the table to the scroll pane
		scrollPane2.setViewportView(table2);

		// Optional: Set focus traversal policy if needed
		scrollPane2.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { table2 }));

		// Load staff data into the table

		JButton addBookbtn = new JButton("Add Books");
		addBookbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new addBook().setVisible(true);
			}
		});

		addBookbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		addBookbtn.setBounds(258, 47, 199, 55);
		contentPane.add(addBookbtn);

		JButton issuebtn = new JButton("Issue Book");
		issuebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new issueBook().setVisible(true);
			}
		});
		issuebtn.setFont(new Font("Arial", Font.PLAIN, 25));
		issuebtn.setBounds(480, 47, 199, 55);
		contentPane.add(issuebtn);

		JButton returnbtn = new JButton("Return Book");
		returnbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new returnBook().setVisible(true);
			}
		});
		returnbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		returnbtn.setBounds(701, 47, 199, 55);
		contentPane.add(returnbtn);

		JLabel lblNewLabel = new JLabel("Date Range");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNewLabel.setBounds(304, 315, 114, 24);
		contentPane.add(lblNewLabel);

		// Set up the date chooser
		dateChooser = new JDateChooser();
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser.setBounds(64, 380, 164, 32);
		dateChooser.setDateFormatString("yyyy-MM-dd");
		getContentPane().add(dateChooser);

		dateChooser_1 = new JDateChooser();
		dateChooser_1.setFont(new Font("Arial", Font.PLAIN, 20));
		dateChooser_1.setDateFormatString("yyyy-MM-dd");
		dateChooser_1.setBounds(64, 421, 164, 32);
		contentPane.add(dateChooser_1);

		JLabel lblBooks = new JLabel("Members");
		lblBooks.setFont(new Font("Arial", Font.BOLD, 20));
		lblBooks.setBounds(304, 117, 114, 24);
		contentPane.add(lblBooks);

		JButton reportbtn = new JButton("Reports");
		reportbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		reportbtn.setBounds(922, 47, 199, 55);
		contentPane.add(reportbtn);

		JButton logoutbtn = new JButton("Logout");
		logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Logout?", "Confirm Update",
						JOptionPane.YES_NO_OPTION);

				// If the user clicks 'Yes', proceed to update
				if (response == JOptionPane.YES_OPTION) {
					setVisible(false);
					new Login().setVisible(true);
				}
			}
		});
		logoutbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		logoutbtn.setBounds(1143, 47, 199, 55);
		contentPane.add(logoutbtn);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StaffHome().setVisible(true);
				setVisible(false);
			}
		});
		btnNewButton.setIcon(new ImageIcon(ReportsHome.class.getResource("/images/back.png")));
		btnNewButton.setBounds(0, 0, 39, 37);
		contentPane.add(btnNewButton);

		JLabel lblSelectMember = new JLabel("Select Member");
		lblSelectMember.setFont(new Font("Arial", Font.BOLD, 20));
		lblSelectMember.setBounds(64, 144, 151, 24);
		contentPane.add(lblSelectMember);

		JLabel lblSelectDateRange = new JLabel("Select Date Range");
		lblSelectDateRange.setFont(new Font("Arial", Font.BOLD, 20));
		lblSelectDateRange.setBounds(55, 349, 182, 24);
		contentPane.add(lblSelectDateRange);

		JLabel lblBookDetails = new JLabel("Book Details");
		lblBookDetails.setFont(new Font("Arial", Font.BOLD, 20));
		lblBookDetails.setBounds(304, 521, 121, 24);
		contentPane.add(lblBookDetails);

		JLabel lblSelectBook = new JLabel("Select Book");
		lblSelectBook.setFont(new Font("Arial", Font.BOLD, 20));
		lblSelectBook.setBounds(76, 557, 182, 24);
		contentPane.add(lblSelectBook);

		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				loadMemberData();
			}
		});
		comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBox.setBounds(25, 178, 226, 37);

		loadSelectedMemberData();
		contentPane.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				loadBookData();
			}
		});
		comboBox_1.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBox_1.setBounds(19, 591, 248, 37);

		loadSelectedBookData();
		contentPane.add(comboBox_1);

		JLabel lblNewLabel_1 = new JLabel("To");
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 20));
		lblNewLabel_1.setBounds(27, 384, 29, 24);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("From");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.ITALIC, 20));
		lblNewLabel_1_1.setBounds(10, 425, 54, 24);
		contentPane.add(lblNewLabel_1_1);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDateSelectedBookData();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(ReportsHome.class.getResource("/images/search1.png")));
		btnNewButton_1.setBounds(125, 459, 39, 36);
		contentPane.add(btnNewButton_1);

		JButton exposrtmembers = new JButton("Export");
		exposrtmembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportTableDataToCSV(table, "MemberDetails");
			}
		});
		exposrtmembers.setFont(new Font("Arial", Font.PLAIN, 18));
		exposrtmembers.setBounds(405, 120, 96, 21);
		contentPane.add(exposrtmembers);

		JButton exportdaterange = new JButton("Export");
		exportdaterange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportTableDataToCSV(table1, "DateRange");
			}
		});
		exportdaterange.setFont(new Font("Arial", Font.PLAIN, 18));
		exportdaterange.setBounds(427, 318, 96, 21);
		contentPane.add(exportdaterange);

		JButton exportbookdetails = new JButton("Export");
		exportbookdetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportTableDataToCSV(table2, "BookDetails");
			}
		});
		exportbookdetails.setFont(new Font("Arial", Font.PLAIN, 18));
		exportbookdetails.setBounds(435, 522, 88, 21);
		contentPane.add(exportbookdetails);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(ReportsHome.class.getResource("/images/home.jpg")));
		lblNewLabel_2.setBounds(-189, -38, 1573, 794);
		contentPane.add(lblNewLabel_2);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{addMemberbtn, scrollPane, table, addBookbtn, issuebtn, returnbtn, scrollPane1, table1, lblNewLabel, lblBooks, reportbtn, logoutbtn, btnNewButton, lblSelectMember, lblSelectDateRange, scrollPane2, table2, lblBookDetails, lblSelectBook, comboBox, comboBox_1, dateChooser_1, lblNewLabel_1, lblNewLabel_1_1, btnNewButton_1, exposrtmembers, exportdaterange, exportbookdetails, lblNewLabel_2}));
	}

	// Method to load staff data into the JTable
	private static void loadSelectedMemberData() {
		try {
			// JDBC connection
			Connection conn = DatabaseHelper.connect();
			Statement stmt = conn.createStatement();
			// "Member ID", "Name", "Contact", "Book Title", "Book Status",
			// "Issue Date", "Return Date"
			String query = "SELECT m.id, m.name from transactions t inner join members m on t.member_id=m.id";
			ResultSet rs = stmt.executeQuery(query);

			comboBox.addItem("select");
			// Populate table with data
			while (rs.next()) {
				comboBox.addItem(rs.getString(2));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadMemberData() {

		String memberName = comboBox.getSelectedItem().toString();
		System.out.println(memberName);
		try {
			// JDBC connection
			conn = DatabaseHelper.connect();
			String sql = "SELECT m.id, m.name, m.contact, b.title, t.status, t.borrow_date, t.return_date from transactions t inner join members m on t.member_id=m.id inner join books b on b.id=t.book_id where m.name= ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName); // Assuming memberId is an integer
			ResultSet rs = pstmt.executeQuery();

			// Clear existing rows
			model.setRowCount(0);

			// Populate table with data
			while (rs.next()) {
				int mid = rs.getInt(1);
				String name = rs.getString(2);
				String contact = rs.getString(3);
				String title = rs.getString(4);
				int status = rs.getInt(5);
				Date issueDate = rs.getDate(6);
				Date returnDate = rs.getDate(6);

				model.addRow(new Object[] { mid, name, contact, title, status == 1 ? " returned" : " issued", issueDate,
						returnDate });
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to load books data into the JTable
	private static void loadSelectedBookData() {

		try {
			// JDBC connection
			Connection conn = DatabaseHelper.connect();
			Statement stmt = conn.createStatement();
			String query = "SELECT b.id, b.title from transactions t inner join books b on t.book_id=b.id";
			ResultSet rs = stmt.executeQuery(query);

			comboBox_1.addItem("select");
			// Populate table with data
			while (rs.next()) {
				comboBox_1.addItem(rs.getString(2));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadBookData() {

		String bookName = comboBox_1.getSelectedItem().toString();
		try {
			// JDBC connection
			conn = DatabaseHelper.connect();
			String sql = "SELECT b.id, b.title, t.status, m.name, t.borrow_date, t.return_date from transactions t inner join members m on t.member_id=m.id inner join books b on b.id=t.book_id where b.title= ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookName); // Assuming memberId is an integer
			ResultSet rs = pstmt.executeQuery();

			// Clear existing rows
			model2.setRowCount(0);

			// Populate table with data
			while (rs.next()) {
				int bid = rs.getInt(1);
				String title = rs.getString(2);
				int status = rs.getInt(3);
				String name = rs.getString(4);
				Date issueDate = rs.getDate(5);
				Date returnDate = rs.getDate(6);

				model2.addRow(new Object[] { bid, title, status == 1 ? " returned" : " issued", name, issueDate,
						returnDate });
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadDateSelectedBookData() {

		Date selectedDate = dateChooser.getDate();
		Date dueDate = dateChooser_1.getDate();

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
			String sql = "SELECT b.id, b.title, t.status, m.name, t.borrow_date, t.return_date from transactions t inner join members m on t.member_id=m.id inner join books b on b.id=t.book_id WHERE t.borrow_date BETWEEN ? AND ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(selectedDate.getTime()));
			pstmt.setDate(2, new java.sql.Date(dueDate.getTime()));

			ResultSet rs = pstmt.executeQuery();
			// Clear existing rows
			model1.setRowCount(0);

			// Populate table with data
			while (rs.next()) {
				int bid = rs.getInt(1);
				String title = rs.getString(2);
				int status = rs.getInt(3);
				String name = rs.getString(4);
				Date issueDate = rs.getDate(5);
				Date returnDate = rs.getDate(6);

				model1.addRow(new Object[] { bid, title, status == 1 ? " returned" : " issued", name, issueDate,
						returnDate });
			}

			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error issuing book.");
		}

	}

//	private void csvForMember() {
//		
//	}
//	
//	private void csvForDateRange() {
//		
//	}
//	
//	private void csvForBookDetails() {
//		
//	}
//	

	private void exportTableDataToCSV(JTable table, String fileName) {

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String formattedDateTime = dtf.format(now);
		String csvFile = fileName + "_" + formattedDateTime + ".csv"; // e.g., library_report_20241024_145000.csv

		try {

			// Open a FileWriter for the CSV file

			FileWriter csvWriter = new FileWriter(
					"C:\\Users\\Kartik\\eclipse-workspace-SpringQuotationProject\\LibraryManagement\\" + csvFile);

			// Write the column names (header)
			TableModel model = table.getModel();
			for (int i = 0; i < model.getColumnCount(); i++) {
				csvWriter.write(model.getColumnName(i) + ",");
			}
			csvWriter.write("\n");

			// Write the rows of the table
			for (int row = 0; row < model.getRowCount(); row++) {
				for (int col = 0; col < model.getColumnCount(); col++) {
					csvWriter.write(model.getValueAt(row, col).toString() + ",");
				}
				csvWriter.write("\n");
			}

			// Close the writer
			csvWriter.flush();
			csvWriter.close();

			// Show a success message
			JOptionPane.showMessageDialog(null, "Data exported successfully to " + csvFile);
		} catch (IOException e) {
			e.printStackTrace();
			// Show an error message
			JOptionPane.showMessageDialog(null, "Error exporting data: " + e.getMessage());
		}
	}
}
