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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import library.dbhelper.DatabaseHelper;

import java.awt.Component;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private static DefaultTableModel model;
	private static JButton updateButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
		setSize(new Dimension(1366, 768));
		setPreferredSize(new Dimension(1366, 768));
		setTitle("AdminPannel-Library Management System");
		setResizable(false);
		setMinimumSize(new Dimension(1366, 768));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(277, 156, 1366, 768); // Set centered position

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton_1 = new JButton("Add Staff");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new addStaff().setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 25));
		btnNewButton_1.setBounds(37, 47, 199, 55);
		contentPane.add(btnNewButton_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setSize(new Dimension(200, 200));
		scrollPane.setMaximumSize(new Dimension(200, 200));
		scrollPane.setMinimumSize(new Dimension(200, 200));
		scrollPane.setPreferredSize(new Dimension(200, 200));
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 20));
		scrollPane.setBounds(37, 158, 945, 227);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int id = (int) model.getValueAt(row, 0);
				JOptionPane.showMessageDialog(null, id);

				int response = JOptionPane.showConfirmDialog(null, "Do you want to update the staff details?",
						"Confirm Update", JOptionPane.YES_NO_OPTION);

				// If the user clicks 'Yes', proceed to update
				if (response == JOptionPane.YES_OPTION) {
					updateStaff.fetchId(id);
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
		model = new DefaultTableModel(new Object[] { "ID", "Name", "Contact", "Role" }, 0);
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
		loadStaffData();

		JButton btnNewButton_1_1 = new JButton("Logout");
		btnNewButton_1_1.addActionListener(new ActionListener() {
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
		btnNewButton_1_1.setFont(new Font("Arial", Font.PLAIN, 25));
		btnNewButton_1_1.setBounds(281, 47, 199, 55);
		contentPane.add(btnNewButton_1_1);
		
		JLabel label = new JLabel("New label");
		label.setIcon(new ImageIcon(Home.class.getResource("/images/home.jpg")));
		label.setBounds(-235, -337, 1638, 1101);
		contentPane.add(label);
		contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnNewButton_1, scrollPane, table, btnNewButton_1_1, label}));
	}

	// Method to load staff data into the JTable
	public static void loadStaffData() {
		try {
			// JDBC connection
			Connection con = DatabaseHelper.connect();
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM staff";
			ResultSet rs = stmt.executeQuery(query);

			// Clear existing rows
			model.setRowCount(0);

			// Populate table with data
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String contact = rs.getString("contact");
				String role = rs.getString("role");

				model.addRow(new Object[] { id, name, contact, role });
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
