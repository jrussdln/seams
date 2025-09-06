package seams1;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.sql.Statement;
import java.util.Vector;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import com.jgoodies.forms.factories.DefaultComponentFactory;

public class admin_dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel usersCountLbl; // Correctly declared as JLabel
    private JLabel studentCountLbl;
    private JLabel eventCountLbl;
    private JTable upcoEventsTable;
    private DefaultTableModel model;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    admin_dashboard frame = new admin_dashboard("Admin");
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
    public admin_dashboard(String accessLevel) {
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
        recordAttBtn.setBounds(37, 36, 105, 68);
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
        masterlistBtn.setBounds(152, 36, 133, 30);
        contentPane.add(masterlistBtn);
        
        
            JButton usersBtn = new JButton("Users");
            usersBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    users usersFrame = new users(accessLevel);
                    usersFrame.setVisible(true);
                }
            });
            usersBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
            usersBtn.setBackground(Color.WHITE);
            usersBtn.setBounds(152, 74, 270, 30);
            contentPane.add(usersBtn);

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
        eventsBtn.setBounds(295, 36, 133, 30);
        contentPane.add(eventsBtn);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setBounds(432, 36, 105, 68);
        contentPane.add(logoutBtn);
        logoutBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                login loginFrame = new login();
                loginFrame.setVisible(true);
            }
        });
        
        JLabel titleLabel = new JLabel("SEAMS Dashboard");
        titleLabel .setForeground(Color.DARK_GRAY);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        titleLabel.setBounds(10, 0, 490, 30);
        contentPane.add(titleLabel);
        
        JLabel lblNoOfUsers = new JLabel("Number of Users");
        lblNoOfUsers.setBackground(SystemColor.text);
        lblNoOfUsers.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNoOfUsers.setForeground(Color.BLACK);
        lblNoOfUsers.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNoOfUsers.setBounds(114, 171, 133, 24);
        contentPane.add(lblNoOfUsers);
        
        usersCountLbl = new JLabel("0"); // Initialize usersCountLbl
        usersCountLbl.setBackground(SystemColor.text);
        usersCountLbl.setHorizontalAlignment(SwingConstants.CENTER);
        usersCountLbl.setForeground(Color.BLACK);
        usersCountLbl.setFont(new Font("Tahoma", Font.ITALIC, 34));
        usersCountLbl.setBounds(152, 130, 95, 40);
        contentPane.add(usersCountLbl);
        
        JLabel userLbl = new JLabel("");
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\hp\\eclipse-workspace\\seams1\\src\\seams1\\1.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        userLbl.setIcon(scaledIcon);
        userLbl.setBounds(51, 130, 60, 60);
        contentPane.add(userLbl);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBounds(37, 115, 227, 85);
        contentPane.add(panel);
        
        studentCountLbl = new JLabel("0");
        studentCountLbl.setHorizontalAlignment(SwingConstants.CENTER);
        studentCountLbl.setForeground(Color.BLACK);
        studentCountLbl.setFont(new Font("Tahoma", Font.ITALIC, 34));
        studentCountLbl.setBackground(SystemColor.text);
        studentCountLbl.setBounds(152, 220, 95, 40);
        contentPane.add(studentCountLbl);
        
        JLabel lblNoOfUsers_2 = new JLabel("Number of Students");
        lblNoOfUsers_2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNoOfUsers_2.setForeground(Color.BLACK);
        lblNoOfUsers_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNoOfUsers_2.setBackground(SystemColor.text);
        lblNoOfUsers_2.setBounds(114, 261, 133, 24);
        contentPane.add(lblNoOfUsers_2);
        
        JLabel studLbl = new JLabel("");
        ImageIcon studentIcon = new ImageIcon("C:\\Users\\hp\\eclipse-workspace\\seams1\\src\\seams1\\2.jpg");
        Image studentScaledImage = studentIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon studentScaledIcon = new ImageIcon(studentScaledImage);
        studLbl.setIcon(studentScaledIcon);
        studLbl.setBounds(51, 220, 60, 60);
        contentPane.add(studLbl);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.WHITE);
        panel_1.setBounds(37, 205, 227, 85);
        contentPane.add(panel_1);
        
        JLabel userLbl_1 = new JLabel("");
        userLbl_1.setBounds(72, 252, 60, 60);
        contentPane.add(userLbl_1);
        
        JLabel lblNoOfUsers_2_1 = new JLabel("Number of Events");
        lblNoOfUsers_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNoOfUsers_2_1.setForeground(Color.BLACK);
        lblNoOfUsers_2_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNoOfUsers_2_1.setBackground(SystemColor.text);
        lblNoOfUsers_2_1.setBounds(114, 352, 133, 24);
        contentPane.add(lblNoOfUsers_2_1);
        
        eventCountLbl = new JLabel("0");
        eventCountLbl.setHorizontalAlignment(SwingConstants.CENTER);
        eventCountLbl.setForeground(Color.BLACK);
        eventCountLbl.setFont(new Font("Tahoma", Font.ITALIC, 34));
        eventCountLbl.setBackground(SystemColor.text);
        eventCountLbl.setBounds(152, 311, 95, 40);
        contentPane.add(eventCountLbl);
        
        JLabel studLbl_1 = new JLabel("");
        ImageIcon eventIcon = new ImageIcon("C:\\Users\\hp\\eclipse-workspace\\seams1\\src\\seams1\\3.jpg");
        Image eventScaledImage = eventIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon eventScaledIcon = new ImageIcon(eventScaledImage);
        studLbl_1.setIcon(eventScaledIcon);
        studLbl_1.setBounds(51, 311, 60, 60);
        contentPane.add(studLbl_1);
        
        JPanel panel_1_1 = new JPanel();
        panel_1_1.setBackground(Color.WHITE);
        panel_1_1.setBounds(37, 296, 227, 85);
        contentPane.add(panel_1_1);

     // Table for displaying students
        upcoEventsTable = new JTable();
        upcoEventsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Allow selecting multiple rows
        JScrollPane scrollPane = new JScrollPane(upcoEventsTable);
        scrollPane.setBounds(274, 130, 263, 251);
        contentPane.add(scrollPane);
        
        JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("UPCOMING EVENTS");
        lblNewJgoodiesLabel.setBounds(275, 116, 157, 14);
        contentPane.add(lblNewJgoodiesLabel);

        displayUpcoEvents();
        userCount(); // Call to update user count
        studentCount();
        eventCount();
    }
    private void displayUpcoEvents() {
    	model = new DefaultTableModel();
    	
        model.addColumn("Title");
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
               
                row.add(rs.getString("ev_title"));
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

        
        upcoEventsTable.setModel(model);
    }
    private void userCount() {
        // Connect to the database
        db_conn db = new db_conn();
        db.connect();
        
        String query = "SELECT COUNT(*) AS userCount FROM user_tbl"; // Query to count users
        
        try {
            // Execute the query
            Statement stmt = db.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next()) {
                int count = rs.getInt("userCount"); // Get the count from the ResultSet
                usersCountLbl.setText(String.valueOf(count)); // Update the JLabel with the count
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
    }
    private void studentCount() {
        db_conn db = new db_conn();
        db.connect();
        String query = "SELECT COUNT(*) AS studentCount FROM student_tbl";
        try (Statement stmt = db.con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int count = rs.getInt("studentCount");
                studentCountLbl.setText(String.valueOf(count));
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                db.con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void eventCount() {
        db_conn db = new db_conn();
        db.connect();
        String query = "SELECT COUNT(*) AS eventCount FROM events_tbl";
        try (Statement stmt = db.con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int count = rs.getInt("eventCount");
                eventCountLbl.setText(String.valueOf(count));
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                db.con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
