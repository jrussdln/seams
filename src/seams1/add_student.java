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
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

public class add_student extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField studIdField;
	private JTextField fnameField;
	private JTextField lnameField;
	private JTextField enameField;
	private JTextField yearLevelField;
	private JTextField courseField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					add_student frame = new add_student("");
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
	public add_student(String accessLevel) {
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
		
		JLabel lblAdminDashboardMasterlistadd = new JLabel("Masterlist: Add Student");
		lblAdminDashboardMasterlistadd.setBounds(181, 41, 200, 22);
		lblAdminDashboardMasterlistadd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminDashboardMasterlistadd.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblAdminDashboardMasterlistadd);
		
		studIdField = new JTextField();
		studIdField.setBounds(249, 92, 145, 22);
		contentPane.add(studIdField);
		studIdField.setColumns(10);
		
		fnameField = new JTextField();
		fnameField.setColumns(10);
		fnameField.setBounds(249, 125, 145, 22);
		contentPane.add(fnameField);
		
		lnameField = new JTextField();
		lnameField.setColumns(10);
		lnameField.setBounds(249, 158, 145, 22);
		contentPane.add(lnameField);
		
		enameField = new JTextField();
		enameField.setColumns(10);
		enameField.setBounds(249, 191, 145, 22);
		contentPane.add(enameField);
		
		yearLevelField = new JTextField();
		yearLevelField.setColumns(10);
		yearLevelField.setBounds(249, 224, 145, 22);
		contentPane.add(yearLevelField);
		
		courseField = new JTextField();
		courseField.setColumns(10);
		courseField.setBounds(249, 257, 145, 22);
		contentPane.add(courseField);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Student ID:");
		lblNewJgoodiesLabel.setBounds(175, 96, 75, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("First Name:");
		lblNewJgoodiesLabel_1.setBounds(175, 129, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Last Name: ");
		lblNewJgoodiesLabel_2.setBounds(175, 162, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Ext. Name: ");
		lblNewJgoodiesLabel_3.setBounds(175, 195, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Year Level:");
		lblNewJgoodiesLabel_4.setBounds(175, 228, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("Course:");
		lblNewJgoodiesLabel_5.setBounds(175, 261, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_5);
		
		JButton addStudBtn = new JButton("Add");
		addStudBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String studId = studIdField.getText();
		        String fname = fnameField.getText();
		        String lname = lnameField.getText();
		        String ename = enameField.getText();
		        String yearLevel = yearLevelField.getText();
		        String course = courseField.getText();

		        // Check if any field is empty
		        if (studId.isEmpty() || fname.isEmpty() || lname.isEmpty() || yearLevel.isEmpty() || course.isEmpty()) {
		            // Show a message if any field is empty
		            JOptionPane.showMessageDialog(null, "Required fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
		            return; // Exit the action method without performing the insert
		        }

		        try {
		            db_conn db = new db_conn();
		            db.connect();

		            String sql = "INSERT INTO student_tbl (stud_sch_id, stud_fname, stud_lname, stud_ename, stud_year_level, stud_course) VALUES (?, ?, ?, ?, ?, ?)";
		            db.prep = db.con.prepareStatement(sql);
		            db.prep.setString(1, studId);
		            db.prep.setString(2, fname);
		            db.prep.setString(3, lname);
		            db.prep.setString(4, ename);
		            db.prep.setString(5, yearLevel);
		            db.prep.setString(6, course);

		            int result = db.prep.executeUpdate();

		            if (result > 0) {
		                // Show success message using JOptionPane
		                JOptionPane.showMessageDialog(null, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

		                // Code to clear the form inputs after adding the student
		                studIdField.setText("");
		                fnameField.setText("");
		                lnameField.setText("");
		                enameField.setText("");
		                yearLevelField.setText("");
		                courseField.setText("");

		                // Close the current window
		                dispose();

		                // Create a new instance of the master list frame
		                masterlist masterlistFrame = new masterlist(accessLevel);
		                masterlistFrame.setVisible(true); // Show the master list

		            } else {
		                // Show failure message using JOptionPane
		                JOptionPane.showMessageDialog(null, "Failed to add student.", "Error", JOptionPane.ERROR_MESSAGE);
		            }

		            // Optional: Close connection
		            db.prep.close();
		            db.con.close();

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            // Show error message if an exception occurs
		            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});



		addStudBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addStudBtn.setBackground(Color.WHITE);
		addStudBtn.setBounds(277, 315, 138, 30);
		contentPane.add(addStudBtn);
		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                masterlist masterlistFrame = new masterlist(accessLevel);
                masterlistFrame.setVisible(true);
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(Color.WHITE);
		backBtn.setBounds(158, 315, 109, 30);
		contentPane.add(backBtn);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(158, 74, 257, 230);
		contentPane.add(panel);
	}
}
