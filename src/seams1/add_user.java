package seams1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.BorderFactory;
public class add_user extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField userFnameField;
	private JTextField userLnameField;
	private JTextField userEnameField;
	private JTextField userSexField;
	private JDateChooser userDobField;
	private JTextField userAcLevField;
	private JTextField userPassField;
	private JTextField userUnameField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					add_user frame = new add_user("");
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
	public add_user(String accessLevel) {
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
		
		JLabel lblUsersAddUser = new JLabel("Users: Add User");
		lblUsersAddUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersAddUser.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsersAddUser.setBounds(197, 51, 200, 22);
		contentPane.add(lblUsersAddUser);
		
		userFnameField = new JTextField();
		userFnameField.setColumns(10);
		userFnameField.setBounds(69, 114, 145, 22);
		contentPane.add(userFnameField);
		
		userLnameField = new JTextField();
		userLnameField.setColumns(10);
		userLnameField.setBounds(224, 114, 145, 22);
		contentPane.add(userLnameField);
		
		userEnameField = new JTextField();
		userEnameField.setColumns(10);
		userEnameField.setBounds(379, 114, 145, 22);
		contentPane.add(userEnameField);
		
		String[] sexOptions = {"Male", "Female", "Other"}; // Add/remove options as needed
		JComboBox<String> userSexDropdown = new JComboBox<>(sexOptions);
		userSexDropdown.setBounds(69, 156, 145, 22);
		contentPane.add(userSexDropdown);

		
		userDobField = new JDateChooser(); // Initialize the class field properly
		userDobField.setBounds(224, 156, 145, 22);
		contentPane.add(userDobField);
		
		String[] accessLevels = {"admin", "officer"}; // Replace with your actual options
		JComboBox<String> userAcLevDropdown = new JComboBox<>(accessLevels);
		userAcLevDropdown.setBounds(379, 156, 145, 22);
		contentPane.add(userAcLevDropdown);

		
		userPassField = new JTextField();
		userPassField.setColumns(10);
		userPassField.setBounds(305, 200, 145, 22);
		contentPane.add(userPassField);
		
		userUnameField = new JTextField();
		userUnameField.setColumns(10);
		userUnameField.setBounds(150, 200, 145, 22);
		contentPane.add(userUnameField);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                users usersFrame = new users(accessLevel);
                usersFrame.setVisible(true);
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(Color.WHITE);
		backBtn.setBounds(169, 265, 92, 30);
		contentPane.add(backBtn);
		
		JButton addUserBtn = new JButton("Add");
		addUserBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String fname = userFnameField.getText();
		        String lname = userLnameField.getText();
		        String ename = userEnameField.getText();
		        String sex = (String) userSexDropdown.getSelectedItem(); // changed
		        String accLev = (String) userAcLevDropdown.getSelectedItem(); // changed
		        String username = userUnameField.getText();
		        String password = userPassField.getText();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        String dob = sdf.format(userDobField.getDate());

		        // Check if required fields are empty
		        if (fname.isEmpty() || lname.isEmpty() || accLev.isEmpty() || username.isEmpty() || password.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Required fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
		            return;
		        }

		        try {
		            db_conn db = new db_conn();
		            db.connect();

		            String sql = "INSERT INTO user_tbl (user_fname, user_lname, user_ename, user_sex, user_dob, user_access_level, user_username, user_password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		            db.prep = db.con.prepareStatement(sql);
		            db.prep.setString(1, fname);
		            db.prep.setString(2, lname);
		            db.prep.setString(3, ename);
		            db.prep.setString(4, sex);
		            db.prep.setString(5, dob);
		            db.prep.setString(6, accLev);
		            db.prep.setString(7, username);
		            db.prep.setString(8, password);

		            int result = db.prep.executeUpdate();

		            if (result > 0) {
		                JOptionPane.showMessageDialog(null, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

		                // Clear all fields
		                userFnameField.setText("");
		                userLnameField.setText("");
		                userEnameField.setText("");
		                userSexDropdown.setSelectedIndex(0); // reset dropdown
		                userDobField.setDate(null);
		                userAcLevDropdown.setSelectedIndex(0); // reset dropdown
		                userUnameField.setText("");
		                userPassField.setText("");

		                dispose(); // close current window
		                users usersFrame = new users(accessLevel);
		                usersFrame.setVisible(true);
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
		            }

		            db.prep.close();
		            db.con.close();

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		addUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addUserBtn.setBackground(Color.WHITE);
		addUserBtn.setBounds(277, 265, 145, 30);
		contentPane.add(addUserBtn);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Firstname");
		lblNewJgoodiesLabel.setBounds(69, 99, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Lastname");
		lblNewJgoodiesLabel_1.setBounds(224, 99, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Ext. Name");
		lblNewJgoodiesLabel_2.setBounds(379, 99, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Sex");
		lblNewJgoodiesLabel_3.setBounds(69, 142, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Birthdate");
		lblNewJgoodiesLabel_4.setBounds(224, 142, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("Access Level");
		lblNewJgoodiesLabel_5.setBounds(379, 142, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_5);
		
		JLabel lblNewJgoodiesLabel_6 = DefaultComponentFactory.getInstance().createLabel("Username");
		lblNewJgoodiesLabel_6.setBounds(150, 186, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_6);
		
		JLabel lblNewJgoodiesLabel_7 = DefaultComponentFactory.getInstance().createLabel("Password");
		lblNewJgoodiesLabel_7.setBounds(305, 186, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_7);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(51, 84, 492, 157);
		contentPane.add(panel);
	}
}
