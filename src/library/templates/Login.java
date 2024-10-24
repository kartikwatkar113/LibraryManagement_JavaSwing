package library.templates;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Cursor;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import library.dbhelper.DatabaseHelper;
import java.awt.Component;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Login() {
        setMinimumSize(new Dimension(1366, 768));
        setMaximumSize(new Dimension(1366, 768));
        setSize(new Dimension(1366, 768));
        setPreferredSize(new Dimension(1366, 768));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 653, 435);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Password");
        lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        lblNewLabel.setBounds(801, 331, 97, 25);
        contentPane.add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 20));
        lblUsername.setBounds(801, 256, 97, 25);
        contentPane.add(lblUsername);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setBackground(SystemColor.text);
        textField.setBounds(943, 258, 178, 28);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnNewButton = new JButton("Login");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Use getPassword() instead of getText()
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);
                
                if (textField.getText().equals("admin") && passwordString.equals("admin")) {
                    setVisible(false);
                    new Home().setVisible(true);
                } else if (checkStaffMember(textField.getText(), passwordString)) {
                    setVisible(false);
                    new StaffHome().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect details 😐");
                }
            }
        });
        btnNewButton.setFont(new Font("Arial", Font.PLAIN, 20));
        btnNewButton.setBounds(864, 406, 97, 37);
        contentPane.add(btnNewButton);

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnClose.setFont(new Font("Arial", Font.PLAIN, 20));
        btnClose.setBounds(997, 406, 97, 37);
        contentPane.add(btnClose);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordField.setFocusTraversalKeysEnabled(false);
        passwordField.setBorder(new LineBorder(new Color(70, 130, 180), 2));
        passwordField.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        passwordField.setEchoChar('*'); // Mask the password input
        passwordField.setForeground(SystemColor.menu);
        passwordField.setBackground(SystemColor.activeCaption);
        passwordField.setBounds(943, 325, 178, 28);
        contentPane.add(passwordField);

        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setIcon(new ImageIcon(Login.class.getResource("/images/Login (3).jpg")));
        lblNewLabel_1.setBounds(0, -15, 1352, 784);
        contentPane.add(lblNewLabel_1);
        contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblNewLabel, lblUsername, textField, btnNewButton, btnClose, passwordField, lblNewLabel_1}));
    }

    private boolean checkStaffMember(String sname, String spass) {
        try {
            Connection conn = DatabaseHelper.connect();
            String sql = "SELECT name, contact, role FROM staff WHERE name = ? AND contact = ? AND role = 'Assistant Librarian'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sname);
            stmt.setString(2, spass);

            ResultSet resultSet = stmt.executeQuery(); // Use executeQuery for SELECT

            // If resultSet has a result, that means the staff member exists
            return resultSet.next(); // Simplified

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
