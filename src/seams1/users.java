package seams1;

import java.awt.EventQueue;
import java.awt.Component;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
public class users extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable userTable;
	private JTextField searchBarField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					users frame = new users("");
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
	public users(String accessLevel) {
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
		btnDashboard.setBounds(27, 36, 132, 30);
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
		recordAttBtn.setBounds(163, 36, 83, 30);
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
		masterlistBtn.setBounds(251, 36, 90, 30);
		contentPane.add(masterlistBtn);
		
		JButton eventsBtn = new JButton("Events");
		eventsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                events eventsFrame = new events(accessLevel);
                eventsFrame.setVisible(true);
			}
		});
		eventsBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		eventsBtn.setBackground(Color.WHITE);
		eventsBtn.setBounds(351, 36, 91, 30);
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
		logoutBtn.setBounds(452, 36, 101, 30);
		contentPane.add(logoutBtn);
		
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(27, 94, 526, 248);
        contentPane.add(scrollPane);
		
        if ("admin".equals(accessLevel)) {
		JButton addStudentBtn = new JButton("Add");
		addStudentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
                dispose(); // Close current window only after validation
                add_user add_userFrame = new add_user(accessLevel); // Open the edit form
                add_userFrame.setVisible(true);
			}
		});
		addStudentBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		addStudentBtn.setBackground(Color.WHITE);
		addStudentBtn.setBounds(124, 353, 109, 30);
		contentPane.add(addStudentBtn);
		
		JButton editStudentBtn = new JButton("Edit");
		editStudentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = userTable.getSelectedRows();

                if (selectedRows.length == 1) {
                    String user_id = userTable.getValueAt(selectedRows[0], 0).toString(); // Column 0 = Student ID
                    edit_user edit_userFrame = new edit_user(user_id, accessLevel); // Open the edit form
                    edit_userFrame.setVisible(true);
                    dispose(); // Close current window only after validation
                } else if (selectedRows.length > 1) {
                    JOptionPane.showMessageDialog(null, "You can't edit more than one user at a time.", "Multiple Selections", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
		editStudentBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editStudentBtn.setBackground(Color.WHITE);
		editStudentBtn.setBounds(243, 353, 109, 30);
		contentPane.add(editStudentBtn);
		
		JButton deleteStudentBtn = new JButton("Delete");
		deleteStudentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Get all selected rows
                int[] selectedRows = userTable.getSelectedRows();

                if (selectedRows.length > 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) userTable.getModel();

                        // Connect to the database
                        db_conn db = new db_conn();
                        db.connect();
                        try {
                            // Loop through selected rows and delete each student
                            java.util.Arrays.sort(selectedRows);  // Ensure rows are sorted
                            for (int i = selectedRows.length - 1; i >= 0; i--) {  // Start from the last selected row
                                int rowIndex = selectedRows[i];

                                // Get the Student ID from column index 0 (first column)
                                String user_id = model.getValueAt(rowIndex, 0).toString();

                                // Delete from database
                                Statement stmt = db.con.createStatement();
                                int rowsAffected = stmt.executeUpdate("DELETE FROM user_tbl WHERE user_id = '" + user_id + "'");

                                if (rowsAffected > 0) {
                                    model.removeRow(rowIndex); // Remove from table if successfully deleted
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to delete user with ID: " + user_id);
                                }
                            }

                            JOptionPane.showMessageDialog(null, "Selected students deleted successfully.");

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error occurred while deleting students.");
                        } finally {
                            try {
                                db.con.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select at least one student to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
			}
		});
		deleteStudentBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		deleteStudentBtn.setBackground(Color.WHITE);
		deleteStudentBtn.setBounds(358, 353, 109, 30);
		contentPane.add(deleteStudentBtn);
        }
        
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("User List");
		lblNewJgoodiesLabel.setBounds(27, 77, 101, 14);
		contentPane.add(lblNewJgoodiesLabel);
		
		JLabel lblAdminDashboardUsers = new JLabel("SEAMS Dashboard: Users");
		lblAdminDashboardUsers.setHorizontalAlignment(SwingConstants.LEFT);
		lblAdminDashboardUsers.setForeground(Color.DARK_GRAY);
		lblAdminDashboardUsers.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAdminDashboardUsers.setBounds(10, 0, 490, 30);
		contentPane.add(lblAdminDashboardUsers);
		
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

		searchBarField.setBounds(416, 77, 137, 14);
		contentPane.add(searchBarField);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Search");
		lblNewJgoodiesLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewJgoodiesLabel_1.setBounds(321, 77, 92, 14);
		contentPane.add(lblNewJgoodiesLabel_1);
		
		displayUsers();
	}
	private void filterTable(String query) {
	    DefaultTableModel originalModel = (DefaultTableModel) userTable.getModel();
	    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(originalModel);
	    userTable.setRowSorter(sorter);

	    if (query.trim().length() == 0) {
	        sorter.setRowFilter(null); // Show all if search is empty
	    } else {
	        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
	    }
	}

	public void displayUsers() {
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("User ID");
	    model.addColumn("Username");
	    model.addColumn("Full Name");
	    model.addColumn("Access Level");
	    
	    // Connect to the database
	    db_conn db = new db_conn();
	    db.connect();
	    
	    String query = "SELECT * FROM user_tbl";
	    
	    try {
	        Statement stmt = db.con.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	            Vector<String> row = new Vector<>();
	            row.add(rs.getString("user_id")); // Will be hidden
	            row.add(rs.getString("user_username"));
	            row.add(rs.getString("user_fname") + " " + rs.getString("user_lname"));
	            row.add(rs.getString("user_access_level"));
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
	    
	    // Set the model to the JTable
	    userTable.setModel(model);

	    // 🔒 Hide the first column (User ID)
	    userTable.getColumnModel().getColumn(0).setMinWidth(0);
	    userTable.getColumnModel().getColumn(0).setMaxWidth(0);
	    userTable.getColumnModel().getColumn(0).setWidth(0);
	}

}
