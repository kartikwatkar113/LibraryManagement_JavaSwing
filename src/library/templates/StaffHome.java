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
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import library.dbhelper.DatabaseHelper;
import java.awt.Component;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffHome extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table1;
	private static DefaultTableModel model;
	private static DefaultTableModel model1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StaffHome frame = new StaffHome();
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
	public StaffHome() {
		setSize(new Dimension(1366, 768));
		setPreferredSize(new Dimension(1366, 768));
		setTitle("Staff Pannel-Library Management System");
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
		addMemberbtn.setBounds(37, 47, 199, 55);
		contentPane.add(addMemberbtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(200, 200));
		scrollPane.setMaximumSize(new Dimension(200, 200));
		scrollPane.setMinimumSize(new Dimension(200, 200));
		scrollPane.setPreferredSize(new Dimension(200, 200));
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setBounds(37, 468, 945, 227);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int id = (int) model.getValueAt(row, 0);
				JOptionPane.showMessageDialog(null, id);

				int response = JOptionPane.showConfirmDialog(null, "Do you want to update the Member details?",
						"Confirm Update", JOptionPane.YES_NO_OPTION);

				// If the user clicks 'Yes', proceed to update
				if (response == JOptionPane.YES_OPTION) {
					updateMember.fetchId(id);
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setEnabled(false);
		table.setCellSelectionEnabled(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setSize(new Dimension(200, 200));
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setViewportView(table);

		// Initialize table model with column names
		model = new DefaultTableModel(new Object[] { "Member ID", "Member Name", "Member Contact","Member Barcode" }, 0);
		table.setModel(model);

		// Set font size for table row contents
		table.setFont(new Font("Arial", Font.PLAIN, 20));
		table.setRowHeight(40); // Set row height for better readability

		// Set font size for table header contents
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Optional: increase header height

		// Optional: Auto-resize columns to fit the table
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add the table to the scroll pane
		scrollPane.setViewportView(table);

		// Optional: Set focus traversal policy if needed
		scrollPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { table }));

		// Load staff data into the table
		loadMemberData();
		/* ======================================= */
//		JButton addBookbtn = new JButton("Add Books");
//		addBookbtn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int response = JOptionPane.showConfirmDialog(null, "Do you want to update the staff details?",
//						"Confirm Update", JOptionPane.YES_NO_OPTION);
//
//				// If the user clicks 'Yes', proceed to update
//				if (response == JOptionPane.YES_OPTION) {
//					new Login().setVisible(true);
//				}
//			}
//		});
		
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setSize(new Dimension(200, 200));
		scrollPane1.setMaximumSize(new Dimension(200, 200));
		scrollPane1.setMinimumSize(new Dimension(200, 200));
		scrollPane1.setPreferredSize(new Dimension(200, 200));
		scrollPane1.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane1.setBounds(37, 184, 945, 227);
		contentPane.add(scrollPane1);

		table1 = new JTable();
		table1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table1.rowAtPoint(e.getPoint());
				int id = (int) model1.getValueAt(row, 0);
				JOptionPane.showMessageDialog(null, id);

				int response = JOptionPane.showConfirmDialog(null, "Do you want to update the book details?",
						"Confirm Update", JOptionPane.YES_NO_OPTION);

				// If the user clicks 'Yes', proceed to update
				if (response == JOptionPane.YES_OPTION) {
					updateBook.fetchId(id);
				}
			}
		});
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table1.setEnabled(false);
		table1.setCellSelectionEnabled(true);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table1.setSize(new Dimension(200, 200));
		table1.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane1.setViewportView(table1);

		// Initialize table model with column names
		model1 = new DefaultTableModel(new Object[] { "Book ID", "Book Title", "Book Author"}, 0);
		table1.setModel(model1);

		// Set font size for table row contents
		table1.setFont(new Font("Arial", Font.PLAIN, 20));
		table1.setRowHeight(40); // Set row height for better readability

		// Set font size for table header contents
		table1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
		table1.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Optional: increase header height

		// Optional: Auto-resize columns to fit the table
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// Add the table to the scroll pane
		scrollPane1.setViewportView(table1);

		// Optional: Set focus traversal policy if needed
		scrollPane1.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { table1 }));

		// Load staff data into the table
		loadBookData();

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
		
		JLabel lblNewLabel = new JLabel("Members");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel.setBounds(37, 438, 114, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblBooks = new JLabel("Books");
		lblBooks.setFont(new Font("Arial", Font.PLAIN, 20));
		lblBooks.setBounds(37, 150, 114, 24);
		contentPane.add(lblBooks);
		
		JButton reportbtn = new JButton("Reports");
		reportbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReportsHome().setVisible(true);
				setVisible(false);
			}
		});
		reportbtn.setFont(new Font("Arial", Font.PLAIN, 25));
		reportbtn.setBounds(922, 47, 199, 55);
		contentPane.add(reportbtn);
		
		JButton logoutbtn = new JButton("Logout");
		logoutbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Do you want to Logout?",
						"Confirm Update", JOptionPane.YES_NO_OPTION);

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
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(StaffHome.class.getResource("/images/home.jpg")));
		lblNewLabel_1.setBounds(-171, -196, 1561, 982);
		contentPane.add(lblNewLabel_1);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{addMemberbtn, scrollPane, table, addBookbtn, issuebtn, returnbtn, scrollPane1, table1, lblNewLabel, lblBooks, reportbtn, logoutbtn, lblNewLabel_1}));
	}

	// Method to load staff data into the JTable
	public static void loadMemberData() {
		try {
			// JDBC connection
			Connection con = DatabaseHelper.connect();
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM members";
			ResultSet rs = stmt.executeQuery(query);

			// Clear existing rows
			model.setRowCount(0);

			// Populate table with data
			while (rs.next()) {
				int mid = rs.getInt("id");
				String name = rs.getString("name");
				String contact = rs.getString("contact");
				String barcode = rs.getString("barcode");

				model.addRow(new Object[] {mid, name, contact, barcode });
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method to load books data into the JTable
		public static void loadBookData() {
			try {
				// JDBC connection
				Connection con = DatabaseHelper.connect();
				Statement stmt = con.createStatement();
				String query = "SELECT * FROM books";
				ResultSet rs = stmt.executeQuery(query);

				// Clear existing rows
				model1.setRowCount(0);

				// Populate table with data
				while (rs.next()) {
					int bid = rs.getInt("id");
					String title = rs.getString("title");
					String author = rs.getString("author");
					

					model1.addRow(new Object[] { bid, title, author });
				}

				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}








