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
import com.toedter.calendar.JDateChooser;


public class edit_event extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String ev_id;
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
					edit_event frame = new edit_event("", "");
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
	public edit_event(String ev_id, String accessLevel) {
		this.ev_id = ev_id;
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
		
		
		JLabel lblEventsEditEvent = new JLabel("Events: Edit Event");
		lblEventsEditEvent.setHorizontalAlignment(SwingConstants.CENTER);
		lblEventsEditEvent.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEventsEditEvent.setBounds(203, 41, 179, 22);
		contentPane.add(lblEventsEditEvent);
		
		evTitleField = new JTextField();
		evTitleField.setColumns(10);
		evTitleField.setBounds(262, 99, 145, 22);
		contentPane.add(evTitleField);
		
		evDescField = new JTextField();
		evDescField.setColumns(10);
		evDescField.setBounds(262, 132, 145, 22);
		contentPane.add(evDescField);
		
		evLocField = new JTextField();
		evLocField.setColumns(10);
		evLocField.setBounds(262, 165, 145, 22);
		contentPane.add(evLocField);
		
		evDurField = new JTextField();
		evDurField.setColumns(10);
		evDurField.setBounds(262, 198, 145, 22);
		contentPane.add(evDurField);
		
		evDateField = new JDateChooser(); // Initialize the class field properly
		evDateField.setBounds(262, 231, 145, 22);
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
		backBtn.setBounds(154, 301, 86, 30);
		contentPane.add(backBtn);
		
		JButton addEventBtn = new JButton("Update");
		addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					db_conn db = new db_conn();
					db.connect();

					String sql = "UPDATE events_tbl SET ev_title = ?, ev_desc = ?, ev_loc = ?, ev_dur = ?, ev_date = ? WHERE ev_id = ?";
					db.prep = db.con.prepareStatement(sql);

					db.prep.setString(1, evTitleField.getText());
					db.prep.setString(2, evDescField.getText());
					db.prep.setString(3, evLocField.getText());
					db.prep.setString(4, evDurField.getText());

					java.util.Date utilDate = evDateField.getDate();
					if (utilDate != null) {
					    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
					    db.prep.setDate(5, sqlDate);
					} else {
					    db.prep.setDate(5, null);
					}
					db.prep.setString(6, ev_id); // This is the missing line


					int rowsUpdated = db.prep.executeUpdate();

					if (rowsUpdated > 0) {
						javax.swing.JOptionPane.showMessageDialog(null, "Event updated successfully!");
						dispose();

		                // Create a new instance of the master list frame
		                events eventsFrame = new events(accessLevel);
		                eventsFrame.setVisible(true); // Show the master list
					} else {
						javax.swing.JOptionPane.showMessageDialog(null, "No event found to update.", "Update Failed", javax.swing.JOptionPane.WARNING_MESSAGE);
					}

					db.prep.close();
					db.con.close();

				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		addEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addEventBtn.setBackground(Color.WHITE);
		addEventBtn.setBounds(257, 301, 179, 30);
		contentPane.add(addEventBtn);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Title:");
		lblNewJgoodiesLabel.setBounds(182, 103, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Description:");
		lblNewJgoodiesLabel_1.setBounds(182, 136, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		JLabel lblNewJgoodiesLabel_2 = DefaultComponentFactory.getInstance().createLabel("Location:");
		lblNewJgoodiesLabel_2.setBounds(182, 169, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_2);
		
		JLabel lblNewJgoodiesLabel_3 = DefaultComponentFactory.getInstance().createLabel("Duration:");
		lblNewJgoodiesLabel_3.setBounds(182, 202, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_3);
		
		JLabel lblNewJgoodiesLabel_4 = DefaultComponentFactory.getInstance().createLabel("Date:");
		lblNewJgoodiesLabel_4.setBounds(182, 235, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_4);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(154, 77, 282, 197);
		contentPane.add(panel);
		loadEventData(ev_id);
	}
	private void loadEventData(String ev_id) {
		try {
			db_conn db = new db_conn();
			db.connect();
			
			String sql = "SELECT * FROM events_tbl WHERE ev_id = ?";
			db.prep = db.con.prepareStatement(sql);
			db.prep.setString(1, ev_id);
			db.result = db.prep.executeQuery();
			
			if (db.result.next()) {
				evTitleField.setText(db.result.getString("ev_title"));
				evDescField.setText(db.result.getString("ev_desc"));
				evLocField.setText(db.result.getString("ev_loc"));
				evDurField.setText(db.result.getString("ev_dur"));
				evDateField.setDate(db.result.getDate("ev_date"));
			} else {
				System.out.println("No student found with ID: " + ev_id);
			}
			db.prep.close();
			db.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
