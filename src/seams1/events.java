package seams1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
import javax.swing.RowFilter;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import java.awt.Component;
import javax.swing.JOptionPane;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
public class events extends JFrame {

	private static final long serialVersionUID = 1L;
	protected static final String String = null;
	private JPanel contentPane;
    private JTable eventTable;
    private DefaultTableModel model;
    private JTextField searchBarField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					events frame = new events("");
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
	public events(String accessLevel) {
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
		
		JButton btnDashboard = new JButton("Dashboard");
		btnDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                admin_dashboard admin_dashboardFrame = new admin_dashboard(accessLevel);
                admin_dashboardFrame.setVisible(true);
			}
		});
		btnDashboard.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDashboard.setBackground(Color.WHITE);
		btnDashboard.setBounds(27, 36, 133, 30);
		contentPane.add(btnDashboard);
		
		JButton recordAttBtn = new JButton("Scan");
		recordAttBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                scanner scannerFrame = new scanner(accessLevel);
                scannerFrame.setVisible(true);
                
             // Start camera after setting the frame visible
                new Thread(() -> scannerFrame.startCamera()).start();
			}
		});
		recordAttBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		recordAttBtn.setBackground(Color.WHITE);
		recordAttBtn.setBounds(170, 36, 77, 30);
		contentPane.add(recordAttBtn);
		
		JButton masterlistBtn = new JButton("Masterlist");
		masterlistBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                masterlist masterlistFrame = new masterlist(accessLevel);
                masterlistFrame.setVisible(true);
			}
		});
		masterlistBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		masterlistBtn.setBackground(Color.WHITE);
		masterlistBtn.setBounds(252, 36, 90, 30);
		contentPane.add(masterlistBtn);
		
		JButton eventsBtn = new JButton("Events");
		eventsBtn.setForeground(Color.WHITE);
		eventsBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eventsBtn.setBackground(Color.DARK_GRAY);
		eventsBtn.setBounds(352, 36, 91, 30);
		contentPane.add(eventsBtn);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                login loginFrame = new login();
                loginFrame.setVisible(true);
			}
		});
		logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		logoutBtn.setBackground(Color.WHITE);
		logoutBtn.setBounds(453, 36, 97, 30);
		contentPane.add(logoutBtn);
		
		// Table for displaying students
        eventTable = new JTable();
        eventTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow selecting multiple rows
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBounds(27, 91, 523, 253);
        contentPane.add(scrollPane);

        displayEvents();
        
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Event List");
		lblNewJgoodiesLabel.setBounds(27, 77, 92, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JButton addEventBtn = new JButton("Add");
		addEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                add_event add_eventFrame = new add_event(accessLevel);
                add_eventFrame.setVisible(true);
			}
		});
		addEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addEventBtn.setBackground(Color.WHITE);
		addEventBtn.setBounds(27, 355, 119, 30);
		contentPane.add(addEventBtn);
		
		JButton editEventBtn = new JButton("Edit");
		editEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = eventTable.getSelectedRows();

                if (selectedRows.length == 1) {
                    String ev_id = eventTable.getValueAt(selectedRows[0], 0).toString(); // Column 0 = Student ID
                    edit_event edit_eventFrame = new edit_event(ev_id, accessLevel); // Open the edit form
                    edit_eventFrame.setVisible(true);
                    dispose(); // Close current window only after validation
                } else if (selectedRows.length > 1) {
                    JOptionPane.showMessageDialog(null, "You can't edit more than one event at a time.", "Multiple Selections", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an event to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
			}
		});
		editEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editEventBtn.setBackground(Color.WHITE);
		editEventBtn.setBounds(156, 355, 120, 30);
		contentPane.add(editEventBtn);
		
		JButton deleteEventBtn = new JButton("Delete");
		deleteEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteEventBtn.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        int[] selectedRows = eventTable.getSelectedRows();

				        if (selectedRows.length > 0) {
				            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected event(s)?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
				            if (confirm == JOptionPane.YES_OPTION) {
				                db_conn db = new db_conn();
				                db.connect();

				                try {
				                    Statement stmt = db.con.createStatement();
				                    for (int row : selectedRows) {
				                        String ev_id = eventTable.getValueAt(row, 0).toString();
				                        stmt.executeUpdate("DELETE FROM events_tbl WHERE ev_id = '" + ev_id + "'");
				                    }
				                    JOptionPane.showMessageDialog(null, "Event(s) deleted successfully.");
				                    displayEvents(); // Refresh table
				                } catch (Exception ex) {
				                    ex.printStackTrace();
				                } finally {
				                    try {
				                        db.con.close();
				                    } catch (Exception ex) {
				                        ex.printStackTrace();
				                    }
				                }
				            }
				        } else {
				            JOptionPane.showMessageDialog(null, "Please select at least one event to delete.");
				        }
				    }
				});

			}
		});
		deleteEventBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		deleteEventBtn.setBackground(Color.WHITE);
		deleteEventBtn.setBounds(286, 355, 77, 30);
		contentPane.add(deleteEventBtn);
		
		JButton attLogBtn = new JButton("Attendance Log");
		attLogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = eventTable.getSelectedRows();

                if (selectedRows.length == 1) {
                	String ev_id = eventTable.getValueAt(selectedRows[0], 0).toString();        
                	String ev_title = eventTable.getValueAt(selectedRows[0], 1).toString();   
                	String ev_location = eventTable.getValueAt(selectedRows[0], 2).toString();

                    
                    att_log att_logFrame = new att_log(ev_id, ev_title, ev_location, accessLevel); 
                    att_logFrame.setVisible(true);
                    dispose(); 
                } else if (selectedRows.length > 1) {
                    JOptionPane.showMessageDialog(null, "You can't view more than one event at a time.", "Multiple Selections", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an event to view.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
			}
		});
		attLogBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		attLogBtn.setBackground(Color.WHITE);
		attLogBtn.setBounds(373, 355, 177, 30);
		contentPane.add(attLogBtn);
		
		JLabel lblAdminDashboardEvents = new JLabel("SEAMS Dashboard: Events");
		lblAdminDashboardEvents.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdminDashboardEvents.setForeground(Color.DARK_GRAY);
		lblAdminDashboardEvents.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAdminDashboardEvents.setBounds(10, 0, 490, 30);
		contentPane.add(lblAdminDashboardEvents);
		
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
		searchBarField.setBounds(413, 77, 137, 14);
		contentPane.add(searchBarField);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Search");
		lblNewJgoodiesLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesLabel_1.setBounds(333, 77, 77, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
	}
	 private void filterTable(String query) {
	        DefaultTableModel originalModel = (DefaultTableModel) eventTable.getModel();
	        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(originalModel);
	        eventTable.setRowSorter(sorter);

	        if (query.trim().length() == 0) {
	            sorter.setRowFilter(null); // Show all if search is empty
	        } else {
	            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
	        }
	    }
	 private void displayEvents() {
		    model = new DefaultTableModel();

		    model.addColumn("Event ID");
		    model.addColumn("Title");
		    model.addColumn("Location");
		    model.addColumn("Duration");
		    model.addColumn("Date");

		    // Connect to the database
		    db_conn db = new db_conn();
		    db.connect();

		    String query = "SELECT * FROM events_tbl";
		    try {
		        Statement stmt = db.con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		            Vector<Object> row = new Vector<>();
		            row.add(rs.getString("ev_id")); // Hidden
		            row.add(rs.getString("ev_title"));
		            row.add(rs.getString("ev_loc"));
		            row.add(rs.getString("ev_dur"));
		            row.add(rs.getString("ev_date"));
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

		    eventTable.setModel(model);

		    // 🔒 Hide the first column (Event ID)
		    eventTable.getColumnModel().getColumn(0).setMinWidth(0);
		    eventTable.getColumnModel().getColumn(0).setMaxWidth(0);
		    eventTable.getColumnModel().getColumn(0).setWidth(0);
		}

}
