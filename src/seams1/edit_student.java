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


public class edit_student extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String StudId;
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
					edit_student frame = new edit_student("", "");
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
	public edit_student(String studId, String accessLevel) {
		this.StudId = studId;
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
		
		
		JLabel lblMasterlisteditStudent = new JLabel("Masterlist/Edit Student");
		lblMasterlisteditStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblMasterlisteditStudent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMasterlisteditStudent.setBounds(204, 45, 179, 22);
		contentPane.add(lblMasterlisteditStudent);
		
		studIdField = new JTextField();
		studIdField.setColumns(10);
		studIdField.setBounds(267, 96, 145, 22);
		contentPane.add(studIdField);
		
		fnameField = new JTextField();
		fnameField.setColumns(10);
		fnameField.setBounds(267, 129, 145, 22);
		contentPane.add(fnameField);
		
		lnameField = new JTextField();
		lnameField.setColumns(10);
		lnameField.setBounds(267, 162, 145, 22);
		contentPane.add(lnameField);
		
		enameField = new JTextField();
		enameField.setColumns(10);
		enameField.setBounds(267, 195, 145, 22);
		contentPane.add(enameField);
		
		yearLevelField = new JTextField();
		yearLevelField.setColumns(10);
		yearLevelField.setBounds(267, 228, 145, 22);
		contentPane.add(yearLevelField);
		
		courseField = new JTextField();
		courseField.setColumns(10);
		courseField.setBounds(267, 261, 145, 22);
		contentPane.add(courseField);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					db_conn db = new db_conn();
					db.connect();

					String sql = "UPDATE student_tbl SET stud_fname = ?, stud_lname = ?, stud_ename = ?, stud_year_level = ?, stud_course = ? WHERE stud_id = ?";
					db.prep = db.con.prepareStatement(sql);

					db.prep.setString(1, fnameField.getText());
					db.prep.setString(2, lnameField.getText());
					db.prep.setString(3, enameField.getText());
					db.prep.setString(4, yearLevelField.getText());
					db.prep.setString(5, courseField.getText());
					db.prep.setString(6, studIdField.getText());

					int rowsUpdated = db.prep.executeUpdate();

					if (rowsUpdated > 0) {
						javax.swing.JOptionPane.showMessageDialog(null, "Student updated successfully!");
						dispose();

		                // Create a new instance of the master list frame
		                masterlist masterlistFrame = new masterlist(accessLevel);
		                masterlistFrame.setVisible(true); // Show the master list
					} else {
						javax.swing.JOptionPane.showMessageDialog(null, "No student found to update.", "Update Failed", javax.swing.JOptionPane.WARNING_MESSAGE);
					}

					db.prep.close();
					db.con.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		});


		updateBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updateBtn.setBackground(Color.WHITE);
		updateBtn.setBounds(248, 327, 192, 30);
		contentPane.add(updateBtn);
		
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
		backBtn.setBounds(151, 327, 84, 30);
		contentPane.add(backBtn);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Student Id:");
		lblNewJgoodiesLabel.setBounds(182, 100, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("First Name:");
		lblNewJgoodiesLabel_1.setBounds(182, 133, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Last Name:");
		lblNewJgoodiesLabel_2.setBounds(182, 166, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Ext. Name:");
		lblNewJgoodiesLabel_3.setBounds(182, 199, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Year Level");
		lblNewJgoodiesLabel_4.setBounds(182, 232, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("Course:");
		lblNewJgoodiesLabel_5.setBounds(182, 265, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_5);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(151, 85, 289, 214);
		contentPane.add(panel);
		loadStudentData(studId);
	}
	private void loadStudentData(String studId) {
		try {
			db_conn db = new db_conn();
			db.connect();
			
			String sql = "SELECT * FROM student_tbl WHERE stud_sch_id = ?";
			db.prep = db.con.prepareStatement(sql);
			db.prep.setString(1, studId);
			db.result = db.prep.executeQuery();
			
			if (db.result.next()) {
				studIdField.setText(db.result.getString("stud_id"));
				fnameField.setText(db.result.getString("stud_fname"));
				lnameField.setText(db.result.getString("stud_lname"));
				enameField.setText(db.result.getString("stud_ename"));
				yearLevelField.setText(db.result.getString("stud_year_level"));
				courseField.setText(db.result.getString("stud_course"));
			} else {
				System.out.println("No student found with ID: " + studId);
			}
			db.prep.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
