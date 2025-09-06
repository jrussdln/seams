package seams1;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.EncodeHintType;

import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class check_qr extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField schIdNumField;
    private JLabel qrLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                check_qr frame = new check_qr();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public check_qr() {
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
		
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            dispose();
            login loginFrame = new login();
            loginFrame.setVisible(true);
        });
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        backBtn.setBackground(Color.WHITE);
        backBtn.setBounds(25, 44, 70, 30);
        contentPane.add(backBtn);

        JButton checkQrBtn = new JButton("Check");
        checkQrBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        checkQrBtn.setBackground(Color.WHITE);
        checkQrBtn.setBounds(105, 44, 109, 30);
        contentPane.add(checkQrBtn);

        JLabel lblQrCodeChecker = new JLabel("QR CODE CHECKER");
        lblQrCodeChecker.setHorizontalAlignment(SwingConstants.LEFT);
        lblQrCodeChecker.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblQrCodeChecker.setBounds(25, 11, 300, 30);
        contentPane.add(lblQrCodeChecker);

        schIdNumField = new JTextField();
        schIdNumField.setBounds(225, 44, 324, 30);
        contentPane.add(schIdNumField);
        schIdNumField.setColumns(10);

        qrLabel = new JLabel("");
        qrLabel.setForeground(Color.GRAY);
        qrLabel.setBounds(25, 85, 300, 300); // QR label size
        contentPane.add(qrLabel);
        
        JLabel lastnameLbl = new JLabel("LASTNAME");
        lastnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
        lastnameLbl.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lastnameLbl.setBounds(359, 146, 196, 30);
        contentPane.add(lastnameLbl);
        
        JLabel firstnameLbl = new JLabel("FIRSTNAME");
        firstnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
        firstnameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        firstnameLbl.setBounds(359, 166, 202, 30);
        contentPane.add(firstnameLbl);
        
        JLabel courseLbl = new JLabel("COURSE");
        courseLbl.setHorizontalAlignment(SwingConstants.LEFT);
        courseLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        courseLbl.setBounds(359, 207, 202, 30);
        contentPane.add(courseLbl);
        
        JLabel yearLevelLbl = new JLabel("Year Level");
        yearLevelLbl.setHorizontalAlignment(SwingConstants.LEFT);
        yearLevelLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
        yearLevelLbl.setBounds(359, 232, 202, 30);
        contentPane.add(yearLevelLbl);

        checkQrBtn.addActionListener(e -> {
            String schoolId = schIdNumField.getText();
            if (!schoolId.isEmpty()) {
                fetchStudentInfo(schoolId, firstnameLbl, lastnameLbl, courseLbl, yearLevelLbl);
            }
        });
    }

    /**
     * Generate QR code based on the provided text (school ID)
     * and display it on the qrLabel.
     */
    private void generateQRCode(String data) {
        try {
            // Create QR code writer and hints for margin reduction
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1); // Reduce default margin from 4 to 1

            int size = 300;
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size, hints);

            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    int color = bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF; // black or white
                    bufferedImage.setRGB(x, y, color);
                }
            }

            ImageIcon qrCodeIcon = new ImageIcon(bufferedImage);
            qrLabel.setIcon(qrCodeIcon);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void fetchStudentInfo(String schoolId, JLabel firstnameLbl, JLabel lastnameLbl, JLabel courseLbl, JLabel yearLevelLbl) {
        db_conn db = new db_conn();
        db.connect();

        try {
            String query = "SELECT stud_fname, stud_lname, stud_course, stud_year_level FROM student_tbl WHERE stud_sch_id = ?";
            db.prep = db.con.prepareStatement(query);
            db.prep.setString(1, schoolId);
            db.result = db.prep.executeQuery();

            if (db.result.next()) {
                String firstname = db.result.getString("stud_fname");
                String lastname = db.result.getString("stud_lname");
                String course = db.result.getString("stud_course");
                String yearLevel = db.result.getString("stud_year_level");

                firstnameLbl.setText("First Name: " + firstname);
                lastnameLbl.setText("Last Name: " + lastname);
                courseLbl.setText("Course: " + course);
                yearLevelLbl.setText("Year Level: " + yearLevel);
                generateQRCode(schoolId);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!", "Error", JOptionPane.ERROR_MESSAGE);
                firstnameLbl.setText("FIRSTNAME");
                lastnameLbl.setText("LASTNAME");
                courseLbl.setText("COURSE");
                yearLevelLbl.setText("Year Level");
                qrLabel.setIcon(null); // clear QR code
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching student info: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (db.result != null) db.result.close();
                if (db.prep != null) db.prep.close();
                if (db.con != null) db.con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

