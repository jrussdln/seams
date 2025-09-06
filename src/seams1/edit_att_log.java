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
import javax.swing.JOptionPane;
public class edit_att_log extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField evNameField;
	private JTextField amInField;
	private JTextField amOutField;
	private JTextField pmInField;
	private JTextField pmOutField;
	private JTextField studNameField;
	private String ev_id;
    private String ev_title;
    private String ev_location;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					edit_att_log frame = new edit_att_log("", "" , "" , "", "");
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
	public edit_att_log(String att_log_id, String ev_id, String ev_title, String ev_location, String accessLevel) {
        this.ev_id = ev_id;
        this.ev_title = ev_title;
        this.ev_location = ev_location;
		
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
        
        evNameField = new JTextField();
        evNameField.setColumns(10);
        evNameField.setBounds(255, 124, 145, 22);
        contentPane.add(evNameField);
        
        amInField = new JTextField();
        amInField.setColumns(10);
        amInField.setBounds(255, 157, 145, 22);
        contentPane.add(amInField);
        
        amOutField = new JTextField();
        amOutField.setColumns(10);
        amOutField.setBounds(255, 190, 145, 22);
        contentPane.add(amOutField);
        
        pmInField = new JTextField();
        pmInField.setColumns(10);
        pmInField.setBounds(255, 223, 145, 22);
        contentPane.add(pmInField);
        
        JButton editAttLogBtn = new JButton("Update");
        editAttLogBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Gather data from input fields
                String studentName = studNameField.getText().trim();
                String eventId = evNameField.getText().trim();
                String amIn = amInField.getText().trim();
                String amOut = amOutField.getText().trim();
                String pmIn = pmInField.getText().trim();
                String pmOut = pmOutField.getText().trim();

                // Perform basic validation (you can expand on this)
                if (studentName.isEmpty() || eventId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Student and Event fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Update the attendance log in the database
                try {
                    db_conn db = new db_conn();
                    db.connect();

                    // Prepare SQL update statement
                    String updateSql = "UPDATE att_log_tbl SET ev_id = ?, am_in = ?, am_out = ?, pm_in = ?, pm_out = ? " +
                                       "WHERE att_log_id = ?";

                    db.prep = db.con.prepareStatement(updateSql);
                    db.prep.setString(1, eventId);
                    db.prep.setString(2, amIn.isEmpty() ? null : amIn);
                    db.prep.setString(3, amOut.isEmpty() ? null : amOut);
                    db.prep.setString(4, pmIn.isEmpty() ? null : pmIn);
                    db.prep.setString(5, pmOut.isEmpty() ? null : pmOut);
                    db.prep.setString(6, att_log_id); // You need to have att_log_id as a class member or accessible here

                    int rowsUpdated = db.prep.executeUpdate();

                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(null, "Attendance log updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to update attendance log. Please check the details.", "Update Failed", JOptionPane.ERROR_MESSAGE);
                    }

                    db.prep.close();
                    db.con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while updating the attendance log: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editAttLogBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        editAttLogBtn.setBackground(Color.WHITE);
        editAttLogBtn.setBounds(248, 313, 179, 30);
        contentPane.add(editAttLogBtn);
        
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                att_log att_logFrame = new att_log(ev_id, ev_title, ev_location, accessLevel); // Pass the parameters
                att_logFrame.setVisible(true);
            }
        });
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        backBtn.setBackground(Color.WHITE);
        backBtn.setBounds(156, 313, 82, 30);
        contentPane.add(backBtn);
        
        JLabel lblEventsEditEvent = new JLabel("Events: Edit Attendance Log");
        lblEventsEditEvent.setHorizontalAlignment(SwingConstants.CENTER);
        lblEventsEditEvent.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblEventsEditEvent.setBounds(160, 41, 267, 22);
        contentPane.add(lblEventsEditEvent);
        
        pmOutField = new JTextField();
        pmOutField.setColumns(10);
        pmOutField.setBounds(255, 256, 145, 22);
        contentPane.add(pmOutField);
        
        JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Student");
        lblNewJgoodiesLabel.setBounds(184, 95, 92, 14);
        contentPane.add(lblNewJgoodiesLabel);
        
        studNameField = new JTextField();
        studNameField.setColumns(10);
        studNameField.setBounds(255, 91, 145, 22);
        contentPane.add(studNameField);
        
        JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Event");
        lblNewJgoodiesLabel_1.setBounds(184, 128, 92, 14);
        contentPane.add(lblNewJgoodiesLabel_1);
        
        JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("AM - IN");
        lblNewJgoodiesLabel_2.setBounds(184, 161, 92, 14);
        contentPane.add(lblNewJgoodiesLabel_2);
        
        JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("AM - OUT");
        lblNewJgoodiesLabel_3.setBounds(184, 194, 92, 14);
        contentPane.add(lblNewJgoodiesLabel_3);
        
        JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("PM - IN");
        lblNewJgoodiesLabel_4.setBounds(184, 227, 92, 14);
        contentPane.add(lblNewJgoodiesLabel_4);
        
        JLabel lblNewJgoodiesLabel_5 = DefaultComponentFactory.getInstance().createLabel("PM - OUT");
        lblNewJgoodiesLabel_5.setBounds(184, 260, 92, 14);
        contentPane.add(lblNewJgoodiesLabel_5);
        
        JPanel panel = new JPanel();
        panel.setBounds(156, 73, 276, 223);
        contentPane.add(panel);
        loadAttLog(att_log_id);
	}
	private void loadAttLog(String att_log_id) {
		try {
			db_conn db = new db_conn();
			db.connect();
			
			String sql = "SELECT * FROM att_log_tbl WHERE att_log_id = ?";
			db.prep = db.con.prepareStatement(sql);
			db.prep.setString(1, att_log_id);
			db.result = db.prep.executeQuery();
			
			if (db.result.next()) {
				studNameField.setText(db.result.getString("stud_sch_id"));
				evNameField.setText(db.result.getString("ev_id"));
				amInField.setText(db.result.getString("am_in"));
				amOutField.setText(db.result.getString("am_in"));
				pmInField.setText(db.result.getString("pm_in"));
				pmInField.setText(db.result.getString("pm_out"));
				
			} else {
				System.out.println("No student found with ID: " + att_log_id);
			}
			db.prep.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
