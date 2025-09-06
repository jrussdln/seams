package seams1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
	
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import java.awt.Component;
import javax.swing.JOptionPane;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;


public class att_log extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTable attLogTable;
    private DefaultTableModel model;
	private static String ev_id;
	private static String ev_title;
	private static String ev_location;
	private JTextField searchBarField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					att_log frame = new att_log(ev_id, ev_title, ev_location, "");
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
	public att_log(String ev_id, String ev_title, String ev_location, String accessLevel) {
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
		
		
		JLabel lblAttendanceLog = new JLabel("Attendance Log");
		lblAttendanceLog.setHorizontalAlignment(SwingConstants.LEFT);
		lblAttendanceLog.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAttendanceLog.setBounds(33, 11, 300, 30);
		contentPane.add(lblAttendanceLog);
		
		JLabel eventTitleLbl = new JLabel(ev_title);
		eventTitleLbl.setHorizontalAlignment(SwingConstants.LEFT);
		eventTitleLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		eventTitleLbl.setBounds(33, 41, 416, 21);
		contentPane.add(eventTitleLbl);
		
		JLabel eventLocationLbl = new JLabel(ev_location);
		eventLocationLbl.setHorizontalAlignment(SwingConstants.LEFT);
		eventLocationLbl.setFont(new Font("Tahoma", Font.ITALIC, 13));
		eventLocationLbl.setBounds(33, 60, 337, 14);
		contentPane.add(eventLocationLbl);
		
		// Table for displaying students
		attLogTable = new JTable();
		attLogTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow selecting multiple rows
        JScrollPane scrollPane = new JScrollPane(attLogTable);
        scrollPane.setBounds(33, 83, 523, 253);
        contentPane.add(scrollPane);

        displayAttLogs(ev_id);
        
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
		backBtn.setBounds(33, 347, 109, 30);
		contentPane.add(backBtn);
		
		JButton editBtn = new JButton("Edit");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = attLogTable.getSelectedRows();

                if (selectedRows.length == 1) {
                    String att_log_id = attLogTable.getValueAt(selectedRows[0], 0).toString(); // Column 0 = Student ID
                    edit_att_log edit_att_logFrame = new edit_att_log(att_log_id, ev_id, ev_title, ev_location, accessLevel); // Open the edit form
                    edit_att_logFrame.setVisible(true);
                    dispose(); // Close current window only after validation
                } else if (selectedRows.length > 1) {
                    JOptionPane.showMessageDialog(null, "You can't edit more than one event at a time.", "Multiple Selections", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an event to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
			}
		});
		editBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editBtn.setBackground(Color.WHITE);
		editBtn.setBounds(271, 347, 109, 30);
		contentPane.add(editBtn);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int[] selectedRows = attLogTable.getSelectedRows();

		        if (selectedRows.length > 0) {
		            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected attendance log(s)?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
		            if (confirm == JOptionPane.YES_OPTION) {
		                db_conn db = new db_conn();
		                db.connect();

		                try {
		                    Statement stmt = db.con.createStatement();
		                    for (int row : selectedRows) {
		                        String att_log_id = attLogTable.getValueAt(row, 0).toString(); // Assuming att_log_id is in the 4th column (index 3)
		                        stmt.executeUpdate("DELETE FROM att_log_tbl WHERE att_log_id = " + att_log_id); // No quotes if it's an integer
		                    }
		                    JOptionPane.showMessageDialog(null, "Attendance log(s) deleted successfully.");
		                    displayAttLogs(ev_id); // Refresh table
		                } catch (Exception ex) {
		                    ex.printStackTrace();
		                    JOptionPane.showMessageDialog(null, "Error deleting attendance log(s): " + ex.getMessage());
		                } finally {
		                    try {
		                        db.con.close();
		                    } catch (Exception ex) {
		                        ex.printStackTrace();
		                    }
		                }
		            }
		        } else {
		            JOptionPane.showMessageDialog(null, "Please select at least one Attendance log to delete.");
		        }
		    }
		});

		deleteBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		deleteBtn.setBackground(Color.WHITE);
		deleteBtn.setBounds(152, 347, 109, 30);
		contentPane.add(deleteBtn);
		
		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Get current timestamp
		            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

		            // Use the actual event title passed to the constructor
		            String eventTitle = ev_title; // Use the ev_title variable

		            // Construct the file name with event title and timestamp
		            String fileName = "C:\\Users\\hp\\Downloads\\" + eventTitle + "_Attendance_Log_" + timestamp + ".csv";
		            
		            FileWriter csvWriter = new FileWriter(fileName);

		            // Write header
		            for (int col = 0; col < model.getColumnCount(); col++) {
		                csvWriter.append(model.getColumnName(col));
		                if (col < model.getColumnCount() - 1) {
		                    csvWriter.append(",");
		                }
		            }
		            csvWriter.append("\n");

		            // Write rows
		            for (int row = 0; row < model.getRowCount(); row++) {
		                for (int col = 0; col < model.getColumnCount(); col++) {
		                    Object value = model.getValueAt(row, col);
		                    csvWriter.append(value != null ? value.toString().replace(",", " ") : "");
		                    if (col < model.getColumnCount() - 1) {
		                        csvWriter.append(",");
		                    }
		                }
		                csvWriter.append("\n");
		            }

		            csvWriter.flush();
		            csvWriter.close();

		            JOptionPane.showMessageDialog(null, "Exported successfully to " + fileName);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Export failed: " + ex.getMessage());
		        }
		    }
		});


		exportBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		exportBtn.setBackground(Color.WHITE);
		exportBtn.setBounds(390, 347, 166, 30);
		contentPane.add(exportBtn);
		
		searchBarField = new JTextField();
		searchBarField.setColumns(10);
		searchBarField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
		    public void insertUpdate(javax.swing.event.DocumentEvent e) {
		        filterTable(searchBarField.getText());
		    }

		    public void removeUpdate(javax.swing.event.DocumentEvent e) {
		        filterTable(searchBarField.getText());
		    }

		    public void changedUpdate(javax.swing.event.DocumentEvent e) {
		        filterTable(searchBarField.getText());
		    }
		});

		searchBarField.setBounds(419, 61, 137, 14);
		contentPane.add(searchBarField);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Search");
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesLabel.setBounds(363, 61, 54, 14);
		contentPane.add(lblNewJgoodiesLabel);
	}
	private void filterTable(String query) {
	    DefaultTableModel originalModel = (DefaultTableModel) attLogTable.getModel();
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(originalModel);
	    attLogTable.setRowSorter(sorter);

	    if (query.trim().length() == 0) {
	        sorter.setRowFilter(null); // Show all if search is empty
	    } else {
	        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
	    }
	}

	private void displayAttLogs(String ev_id) {
	    model = new DefaultTableModel();

	    model.addColumn("ID");
	    model.addColumn("Name");
	    model.addColumn("AM In");
	    model.addColumn("AM Out");
	    model.addColumn("PM In");
	    model.addColumn("PM Out");
	    model.addColumn("Date Logged");

	    db_conn db = new db_conn();
	    db.connect();

	    try {
	        String sql = "SELECT s.stud_lname, s.stud_fname, s.stud_ename, " +
	                     "a.att_log_id, a.am_in, a.am_out, a.pm_in, a.pm_out, a.created_at " +
	                     "FROM att_log_tbl a " +
	                     "JOIN student_tbl s ON a.stud_sch_id = s.stud_sch_id " +
	                     "WHERE a.ev_id = ?";
	        db.prep = db.con.prepareStatement(sql);
	        db.prep.setString(1, ev_id);
	        db.result = db.prep.executeQuery();

	        while (db.result.next()) {
	            Vector<Object> row = new Vector<>();
	            row.add(db.result.getString("att_log_id")); // Hidden column
	            String fullName = db.result.getString("stud_lname") + " " +
	                              db.result.getString("stud_fname") + " " +
	                              db.result.getString("stud_ename");

	            row.add(fullName);
	            row.add(db.result.getTimestamp("am_in") != null ? "Present" : "");
	            row.add(db.result.getTimestamp("am_out") != null ? "Present" : "");
	            row.add(db.result.getTimestamp("pm_in") != null ? "Present" : "");
	            row.add(db.result.getTimestamp("pm_out") != null ? "Present" : "");
	            row.add(db.result.getTimestamp("created_at"));

	            model.addRow(row);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            db.con.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }

	    attLogTable.setModel(model);

	    // 🔒 Hide the first column (ID)
	    attLogTable.getColumnModel().getColumn(0).setMinWidth(0);
	    attLogTable.getColumnModel().getColumn(0).setMaxWidth(0);
	    attLogTable.getColumnModel().getColumn(0).setWidth(0);
	}

}
