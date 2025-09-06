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
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
public class add_event extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField evTitleField;
	private JTextField evDescField;
	private JTextField evLocField;
	private JTextField evDurField;
	private JDateChooser evDateField;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					add_event frame = new add_event("");
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
	public add_event(String accessLevel) {
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
		
		JLabel lblEventsAddEvent = new JLabel("Events: Add Event");
		lblEventsAddEvent.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventsAddEvent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEventsAddEvent.setBounds(201, 39, 179, 22);
		contentPane.add(lblEventsAddEvent);
		
		evTitleField = new JTextField();
		evTitleField.setColumns(10);
		evTitleField.setBounds(258, 89, 145, 22);
		contentPane.add(evTitleField);
		
		evDescField = new JTextField();
		evDescField.setColumns(10);
		evDescField.setBounds(258, 122, 145, 22);
		contentPane.add(evDescField);
		
		evLocField = new JTextField();
		evLocField.setColumns(10);
		evLocField.setBounds(258, 155, 145, 22);
		contentPane.add(evLocField);
		
		evDurField = new JTextField();
		evDurField.setColumns(10);
		evDurField.setBounds(258, 188, 145, 22);
		contentPane.add(evDurField);
		
		evDateField = new JDateChooser();
		evDateField.setBounds(258, 221, 145, 22);
		contentPane.add(evDateField);

		
		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Close current window
				events eventsFrame = new events(accessLevel); // Open events frame
				eventsFrame.setVisible(true);
			}
		});
		backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backBtn.setBackground(Color.WHITE);
		backBtn.setBounds(162, 290, 100, 30);
		contentPane.add(backBtn);
		
		JButton addEventBtn = new JButton("Add");
		addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String evTitle = evTitleField.getText();
				String evDesc = evDescField.getText();
				String evLoc = evLocField.getText();
				String evDur = evDurField.getText();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String evDate = sdf.format(evDateField.getDate());

				
				if(evTitle.isEmpty() || evLoc.isEmpty()) {
					 JOptionPane.showMessageDialog(null, "Required fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
			         return; 
				}
				 try {
			            db_conn db = new db_conn();
			            db.connect();
	
			            String sql = "INSERT INTO events_tbl (ev_title, ev_desc, ev_loc, ev_dur, ev_date) VALUES (?, ?, ?, ?, ?)";
			            db.prep = db.con.prepareStatement(sql);
			            db.prep.setString(1, evTitle);
			            db.prep.setString(2, evDesc);
			            db.prep.setString(3, evLoc);
			            db.prep.setString(4, evDur);
			            db.prep.setString(5, evDate);
	
			            int result = db.prep.executeUpdate();
	
			            if (result > 0) {
			                // Show success message using JOptionPane
			                JOptionPane.showMessageDialog(null, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	
			                // Code to clear the form inputs after adding the student
			                evTitleField.setText("");
			                evDescField.setText("");
			                evLocField.setText("");
			                evDurField.setText("");
			                evDateField.setDate(null);
			               
			                // Close the current window
			                dispose();
	
			                // Create a new instance of the master list frame
			                events eventsFrame = new events(accessLevel);
			                eventsFrame.setVisible(true); // Show the master list
	
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
		addEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addEventBtn.setBackground(Color.WHITE);
		addEventBtn.setBounds(272, 290, 147, 30);
		contentPane.add(addEventBtn);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Title:");
		lblNewJgoodiesLabel.setBounds(181, 93, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Description:");
		lblNewJgoodiesLabel_1.setBounds(181, 126, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Location:");
		lblNewJgoodiesLabel_2.setBounds(181, 159, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Duration:");
		lblNewJgoodiesLabel_3.setBounds(181, 192, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Time:");
		lblNewJgoodiesLabel_4.setBounds(181, 225, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(162, 72, 257, 191);
		contentPane.add(panel);
	}
}
