package seams1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class edit_user extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String user_id;
	private JTextField userFnameField;
	private JTextField userLnameField;
	private JTextField userEnameField;
	private JTextField userSexField;
	private JTextField userDobField;
	private JTextField userUnameField;
	private JTextField userPassField;
	private JTextField userAcLevField;
	private JTextField userIdField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					edit_user frame = new edit_user("", "");
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
	public edit_user(String user_id, String accessLevel) {
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
		
		
		JLabel lblUsersUsers = new JLabel("Users: Edit Users");
		lblUsersUsers.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsersUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsersUsers.setBounds(191, 45, 200, 22);
		contentPane.add(lblUsersUsers);
		
		userFnameField = new JTextField();
		userFnameField.setColumns(10);
		userFnameField.setBounds(69, 121, 145, 22);
		contentPane.add(userFnameField);
		
		userLnameField = new JTextField();
		userLnameField.setColumns(10);
		userLnameField.setBounds(224, 121, 145, 22);
		contentPane.add(userLnameField);
		
		userEnameField = new JTextField();
		userEnameField.setColumns(10);
		userEnameField.setBounds(379, 121, 145, 22);
		contentPane.add(userEnameField);
		
		userSexField = new JTextField();
		userSexField.setColumns(10);
		userSexField.setBounds(69, 163, 145, 22);
		contentPane.add(userSexField);
		
		userDobField = new JTextField();
		userDobField.setColumns(10);
		userDobField.setBounds(224, 163, 145, 22);
		contentPane.add(userDobField);
		
		userUnameField = new JTextField();
		userUnameField.setColumns(10);
		userUnameField.setBounds(224, 204, 145, 22);
		contentPane.add(userUnameField);
		
		JButton editUserBtn = new JButton("Update");
		editUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					db_conn db = new db_conn();
					db.connect();

					String sql = "UPDATE user_tbl SET user_fname = ?, user_lname = ?, user_ename = ?, user_sex = ?, user_dob = ?,  user_username = ?, user_password = ?, user_access_level = ? WHERE user_id = ?";
					db.prep = db.con.prepareStatement(sql);

					db.prep.setString(1, userFnameField.getText());
					db.prep.setString(2, userLnameField.getText());
					db.prep.setString(3, userEnameField.getText());
					db.prep.setString(4, userSexField.getText());
					db.prep.setString(5, userDobField.getText());
					db.prep.setString(6, userUnameField.getText());
					db.prep.setString(7, userPassField.getText());
					db.prep.setString(8, userAcLevField.getText());
					db.prep.setString(9, userIdField.getText());

					int rowsUpdated = db.prep.executeUpdate();

					if (rowsUpdated > 0) {
						javax.swing.JOptionPane.showMessageDialog(null, "User updated successfully!");
						dispose();

		                // Create a new instance of the master list frame
		                users usersFrame = new users(accessLevel);
		                usersFrame.setVisible(true); // Show the master list
					} else {
						javax.swing.JOptionPane.showMessageDialog(null, "No user found to update.", "Update Failed", javax.swing.JOptionPane.WARNING_MESSAGE);
					}

					db.prep.close();
					db.con.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		editUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editUserBtn.setBackground(Color.WHITE);
		editUserBtn.setBounds(250, 265, 176, 30);
		contentPane.add(editUserBtn);
		
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
		backBtn.setBounds(152, 265, 92, 30);
		contentPane.add(backBtn);
		
		userPassField = new JTextField();
		userPassField.setColumns(10);
		userPassField.setBounds(379, 204, 145, 22);
		contentPane.add(userPassField);
		
		userAcLevField = new JTextField();
		userAcLevField.setColumns(10);
		userAcLevField.setBounds(379, 163, 145, 22);
		contentPane.add(userAcLevField);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Firstname");
		lblNewJgoodiesLabel.setBounds(69, 107, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Lastname");
		lblNewJgoodiesLabel_1.setBounds(224, 107, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Ext. Name");
		lblNewJgoodiesLabel_2.setBounds(379, 107, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Sex");
		lblNewJgoodiesLabel_3.setBounds(69, 150, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Birthdate");
		lblNewJgoodiesLabel_4.setBounds(224, 150, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("Access Level");
		lblNewJgoodiesLabel_5.setBounds(379, 150, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_5);
		
		JLabel lblNewJgoodiesLabel_6 = DefaultComponentFactory.getInstance().createLabel("Username");
		lblNewJgoodiesLabel_6.setBounds(224, 191, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_6);
		
		JLabel lblNewJgoodiesLabel_7 = DefaultComponentFactory.getInstance().createLabel("Password");
		lblNewJgoodiesLabel_7.setBounds(379, 191, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_7);
		
		userIdField = new JTextField();
		userIdField.setEditable(false);
		userIdField.setColumns(10);
		userIdField.setBounds(69, 204, 145, 22);
		contentPane.add(userIdField);
		
		JLabel lblNewJgoodiesLabel_8 = DefaultComponentFactory.getInstance().createLabel("User ID");
		lblNewJgoodiesLabel_8.setBounds(69, 191, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_8);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(43, 87, 501, 166);
		contentPane.add(panel);
		
		loadUserData(user_id);
	}
	private void loadUserData(String user_id) {
		try {
			db_conn db = new db_conn();
			db.connect();
			
			String sql = "SELECT * FROM user_tbl WHERE user_id = ?";
			db.prep = db.con.prepareStatement(sql);
			db.prep.setString(1, user_id);
			db.result = db.prep.executeQuery();
			
			if (db.result.next()) {
				userFnameField.setText(db.result.getString("user_fname"));
				userLnameField.setText(db.result.getString("user_lname"));
				userEnameField.setText(db.result.getString("user_ename"));
				userSexField.setText(db.result.getString("user_sex"));
				userDobField.setText(db.result.getString("user_dob"));
				userUnameField.setText(db.result.getString("user_username"));
				userPassField.setText(db.result.getString("user_password"));
				userAcLevField.setText(db.result.getString("user_access_level"));
				userIdField.setText(db.result.getString("user_id"));
				
			} else {
				System.out.println("No student found with ID: " + user_id);
			}
			db.prep.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
