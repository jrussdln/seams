package seams1;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

public class login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField unameField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                login frame = new login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public login() {
    	setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 577, 409);
        setLocationRelativeTo(null); 
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.inactiveCaption);
        contentPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 3),
            new EmptyBorder(5, 5, 5, 5)
        ));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Optional close button
        JButton closeButton = new JButton("X");
        closeButton.setBounds(520, 11, 47, 14);
        closeButton.addActionListener(e -> System.exit(0));
        contentPane.add(closeButton);
        
        JLabel titleLabel = new JLabel("SCHOOL EVENTS AMS");
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 31));
        titleLabel.setBounds(29, 11, 372, 48);
        contentPane.add(titleLabel);
        
        JTextPane loginLbl_1 = new JTextPane();
        loginLbl_1.setText("ATTENDANCE MONITORING SYSTEM");
        loginLbl_1.setForeground(Color.DARK_GRAY);
        loginLbl_1.setFont(new Font("Tahoma", Font.ITALIC, 16));
        loginLbl_1.setBackground(SystemColor.inactiveCaption);
        loginLbl_1.setBounds(29, 48, 307, 26);
        contentPane.add(loginLbl_1);
        
        JTextPane unameLbl = new JTextPane();
        unameLbl.setForeground(Color.DARK_GRAY);
        unameLbl.setBackground(Color.WHITE);
        unameLbl.setText("Username:");
        unameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        unameLbl.setBounds(163, 165, 79, 20);
        contentPane.add(unameLbl);
        
        JTextPane passwordLbl = new JTextPane();
        passwordLbl.setForeground(Color.DARK_GRAY);
        passwordLbl.setBackground(Color.WHITE);
        passwordLbl.setText("Password:");
        passwordLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passwordLbl.setBounds(170, 196, 72, 20);
        contentPane.add(passwordLbl);
        
        unameField = new JTextField();
        unameField.setBounds(267, 165, 168, 20);
        contentPane.add(unameField);
        unameField.setColumns(10);
        
        passwordField = new JPasswordField();
        passwordField.setToolTipText("Enter your password");
        passwordField.setBounds(267, 197, 168, 20);
        contentPane.add(passwordField);
        
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(Color.DARK_GRAY);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBounds(281, 251, 154, 36);
        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usernameInput = unameField.getText();
                String passwordInput = new String(passwordField.getPassword());
                
                db_conn db = new db_conn();
                db.connect();
                
                String sql = "SELECT * FROM user_tbl WHERE user_username = ? AND user_password = ?";
                
                try {
                    db.prep = db.con.prepareStatement(sql);
                    db.prep.setString(1, usernameInput);
                    db.prep.setString(2, passwordInput);
                    
                    db.result = db.prep.executeQuery ();
                    
                    if (db.result.next()) {
                        String accessLevel = db.result.getString("user_access_level");
                        String userFname = db.result.getString("user_fname");
                        
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        dispose(); 
                        if (accessLevel.equalsIgnoreCase("admin")) {
                        	new admin_dashboard(accessLevel).setVisible(true);

                        } else if (accessLevel.equalsIgnoreCase("officer")) {
                            new admin_dashboard(accessLevel).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Unknown access level!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });
        contentPane.add(loginBtn);
        
        JButton checkQrBtn = new JButton("CHECK QR");
        checkQrBtn.setBackground(Color.LIGHT_GRAY);
        checkQrBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                check_qr check_qrFrame = new check_qr();
                check_qrFrame.setVisible(true);
            }
        });
        checkQrBtn.setBounds(155, 251, 115, 36);
        contentPane.add(checkQrBtn);
        JLabel lblSams = new JLabel("SE-AMS");
        lblSams.setBackground(Color.WHITE);
        lblSams.setHorizontalAlignment(SwingConstants.CENTER);
        lblSams.setForeground(Color.DARK_GRAY);
        lblSams.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblSams.setBounds(155, 96, 280, 48);
        contentPane.add(lblSams);
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.text);
        panel.setBounds(125, 96, 340, 213);
        contentPane.add(panel);
        
        
        
    }
}