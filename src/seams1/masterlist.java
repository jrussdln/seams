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
import java.sql.PreparedStatement;
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
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class masterlist extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable studentTable;
    private DefaultTableModel model;
    private JTextField searchBarField;

    // Launch the application
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    masterlist frame = new masterlist("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Create the frame
    public masterlist(String accessLevel) {
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

        // Buttons setup
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
        recordAttBtn.setBounds(177, 36, 77, 36);
        contentPane.add(recordAttBtn);

        JButton masterlistBtn = new JButton("Masterlist");
        masterlistBtn.setForeground(Color.WHITE);
        masterlistBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        masterlistBtn.setBackground(Color.DARK_GRAY);
        masterlistBtn.setBounds(259, 36, 90, 36);
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
        eventsBtn.setBounds(359, 36, 91, 36);
        contentPane.add(eventsBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setBounds(460, 36, 97, 36);
        contentPane.add(logoutBtn);
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                login loginFrame = new login();
                loginFrame.setVisible(true);
            }
        });

        // Dashboard button
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
        btnDashboard.setBounds(30, 36, 137, 36);
        contentPane.add(btnDashboard);

        // Table label
        JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Masterlist");
        lblNewJgoodiesLabel.setBounds(30, 83, 97, 14);
        contentPane.add(lblNewJgoodiesLabel);

        // Button to add, edit, delete students
        JButton addStudentBtn = new JButton("Add");
        addStudentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                add_student add_studentFrame = new add_student(accessLevel);
                add_studentFrame.setVisible(true);
            }
        });
        addStudentBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        addStudentBtn.setBackground(Color.WHITE);
        addStudentBtn.setBounds(30, 356, 109, 30);
        contentPane.add(addStudentBtn);

        JButton editStudentBtn = new JButton("Edit");
        editStudentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = studentTable.getSelectedRows();

                if (selectedRows.length == 1) {
                    String studId = studentTable.getValueAt(selectedRows[0], 0).toString(); // Column 0 = Student ID
                    edit_student editStudentFrame = new edit_student(studId, accessLevel); // Open the edit form
                    editStudentFrame.setVisible(true);
                    dispose(); // Close current window only after validation
                } else if (selectedRows.length > 1) {
                    JOptionPane.showMessageDialog(null, "You can't edit more than one student at a time.", "Multiple Selections", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a student to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        editStudentBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        editStudentBtn.setBackground(Color.WHITE);
        editStudentBtn.setBounds(149, 356, 109, 30);
        contentPane.add(editStudentBtn);

        JButton deleteStudentBtn = new JButton("Delete");
        deleteStudentBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get all selected rows
                int[] selectedRows = studentTable.getSelectedRows();

                if (selectedRows.length > 0) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected students?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

                        // Connect to the database
                        db_conn db = new db_conn();
                        db.connect();
                        try {
                            // Loop through selected rows and delete each student
                            java.util.Arrays.sort(selectedRows);  // Ensure rows are sorted
                            for (int i = selectedRows.length - 1; i >= 0; i--) {  // Start from the last selected row
                                int rowIndex = selectedRows[i];

                                // Get the Student ID from column index 0 (first column)
                                String studentId = model.getValueAt(rowIndex, 0).toString();

                                // Delete from database
                                Statement stmt = db.con.createStatement();
                                int rowsAffected = stmt.executeUpdate("DELETE FROM student_tbl WHERE stud_sch_id = '" + studentId + "'");

                                if (rowsAffected > 0) {
                                    model.removeRow(rowIndex); // Remove from table if successfully deleted
                                } else {
                                    JOptionPane.showMessageDialog(null, "Failed to delete student with ID: " + studentId);
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
        deleteStudentBtn.setBounds(267, 356, 109, 30);
        contentPane.add(deleteStudentBtn);

        // Table for displaying students
        studentTable = new JTable();
        studentTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow selecting multiple rows
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(28, 99, 530, 246);
        contentPane.add(scrollPane);
        
        JLabel lblAdminDashboardMasterlist = new JLabel("SEAMS Dashboard: Masterlist");
        lblAdminDashboardMasterlist.setHorizontalAlignment(SwingConstants.LEFT);
        lblAdminDashboardMasterlist.setForeground(Color.DARK_GRAY);
        lblAdminDashboardMasterlist.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAdminDashboardMasterlist.setBounds(10, 0, 490, 30);
        contentPane.add(lblAdminDashboardMasterlist);
        
        JButton createAccountBtn = new JButton("Create Account");
        createAccountBtn.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = studentTable.getSelectedRows();

        if (selectedRows.length > 0) {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to create account for the selected student as Officer?", "Confirm Officer", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

                db_conn db = new db_conn();
                db.connect();

                try {
                    for (int i = 0; i < selectedRows.length; i++) {
                        int rowIndex = selectedRows[i];
                        String studentId = model.getValueAt(rowIndex, 0).toString();

                        // Fetch student info
                        PreparedStatement ps = db.con.prepareStatement("SELECT stud_fname, stud_lname, stud_ename FROM student_tbl WHERE stud_sch_id = ?");
                        ps.setString(1, studentId);
                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {
                            String fname = rs.getString("stud_fname");
                            String lname = rs.getString("stud_lname");
                            String ename = rs.getString("stud_ename");

                            String username = fname; // You can use more unique logic here if needed

                            // Check if username already exists
                            PreparedStatement checkPs = db.con.prepareStatement("SELECT COUNT(*) AS count FROM user_tbl WHERE user_username = ?");
                            checkPs.setString(1, username);
                            ResultSet checkRs = checkPs.executeQuery();
                            checkRs.next();
                            int count = checkRs.getInt("count");
                            checkRs.close();
                            checkPs.close();

                            if (count > 0) {
                                JOptionPane.showMessageDialog(null, "User already exists for student ID: " + studentId + ". Skipping...");
                                continue;
                            }

                            // Insert into user_tbl
                            PreparedStatement insertPs = db.con.prepareStatement(
                                "INSERT INTO user_tbl (user_fname, user_lname, user_ename, user_dob, user_sex, user_username, user_password, user_access_level) " +
                                "VALUES (?, ?, ?, NULL, NULL, ?, ?, 'officer')"
                            );
                            insertPs.setString(1, fname);
                            insertPs.setString(2, lname);
                            insertPs.setString(3, ename);
                            insertPs.setString(4, username); // user_username
                            insertPs.setString(5, lname);    // user_password

                            insertPs.executeUpdate();
                            insertPs.close();
                        } else {
                            JOptionPane.showMessageDialog(null, "Student ID not found: " + studentId);
                        }

                        rs.close();
                        ps.close();
                    }

                    JOptionPane.showMessageDialog(null, "Selected students' accounts created successfully (existing ones skipped).");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while creating student accounts.");
                } finally {
                    try {
                        db.con.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select at least one student for account creation.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
});


        createAccountBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        createAccountBtn.setBackground(Color.WHITE);
        createAccountBtn.setBounds(386, 356, 171, 30);
        contentPane.add(createAccountBtn);
        
        searchBarField = new JTextField();
        searchBarField.setBounds(420, 83, 137, 14);
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

        contentPane.add(searchBarField);
        searchBarField.setColumns(10);
        
        JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Search");
        lblNewJgoodiesLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewJgoodiesLabel_1.setBounds(359, 83, 59, 14);
        contentPane.add(lblNewJgoodiesLabel_1);

        displayStudents();
    }
    private void filterTable(String query) {
        DefaultTableModel originalModel = (DefaultTableModel) studentTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(originalModel);
        studentTable.setRowSorter(sorter);

        if (query.trim().length() == 0) {
            sorter.setRowFilter(null); // Show all if search is empty
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query)); // Case-insensitive search
        }
    }

    // Method to display students
    public void displayStudents() {
        // Create the table model without the checkbox column
        model = new DefaultTableModel();

        model.addColumn("Student ID");
        model.addColumn("Full Name");
        model.addColumn("Year Level");
        model.addColumn("Course");

        // Connect to the database
        db_conn db = new db_conn();
        db.connect();

        String query = "SELECT * FROM student_tbl";

        try {
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();

                row.add(rs.getString("stud_sch_id"));
                row.add(rs.getString("stud_lname") + " " + rs.getString("stud_fname") + " " + rs.getString("stud_ename"));
                row.add(rs.getString("stud_year_level"));
                row.add(rs.getString("stud_course"));
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

        // Set the model to the table
        studentTable.setModel(model);
    }
}