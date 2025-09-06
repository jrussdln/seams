package seams1;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import javax.swing.border.LineBorder;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.SwingUtilities;

public class scanner extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel cameraDialog;
	private JLabel scannedQrLbl;
	private JLabel statusLbl;
	private VideoCapture camera;
	private JLabel lastnameLbl;
	private JLabel firstnameLbl;
	private JLabel courseLbl;
	private JLabel yearLevelLbl;

	private JComboBox<String> eventsDropdown;
	private JComboBox<String> attTypeDropdown;

	static {
		// Load OpenCV native library
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				scanner frame = new scanner("");
				frame.setVisible(true);

				// Start the camera feed in a separate thread
				new Thread(frame::startCamera).start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public scanner(String accessLevel) {
		setTitle("OpenCV Camera Dialog");
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


		cameraDialog = new JLabel();
		cameraDialog.setBounds(206, 54, 350, 319);
		contentPane.add(cameraDialog);

		JLabel lblAttendanceScanner = new JLabel("ATTENDANCE SCANNER");
		lblAttendanceScanner.setHorizontalAlignment(SwingConstants.LEFT);
		lblAttendanceScanner.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAttendanceScanner.setBounds(21, 21, 232, 22);
		contentPane.add(lblAttendanceScanner);

		eventsDropdown = new JComboBox<>();
		eventsDropdown.setBounds(21, 253, 157, 30);
		contentPane.add(eventsDropdown);

		loadEvents(eventsDropdown); // Populate data here

		attTypeDropdown = new JComboBox<>();
		attTypeDropdown.setBounds(21, 294, 157, 30);
		contentPane.add(attTypeDropdown);

		// Add "AM" and "IN" to the dropdown
		attTypeDropdown.addItem("AM - IN");
		attTypeDropdown.addItem("AM - OUT");
		attTypeDropdown.addItem("PM - IN");
		attTypeDropdown.addItem("PM - OUT");


		JButton exitBtn = new JButton("Exit");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Stop and release the camera when exiting
				if (camera != null && camera.isOpened()) {
					camera.release(); // Close the camera
				}

				dispose();
				admin_dashboard admin_dashboardFrame = new admin_dashboard(accessLevel);
				admin_dashboardFrame.setVisible(true);
			}
		});
		exitBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		exitBtn.setBackground(Color.WHITE);
		exitBtn.setBounds(21, 343, 157, 30);
		contentPane.add(exitBtn);

		scannedQrLbl = DefaultComponentFactory.getInstance().createLabel("");
		scannedQrLbl.setFont(new Font("Tahoma", Font.PLAIN, 17));
		scannedQrLbl.setHorizontalAlignment(SwingConstants.LEFT);
		scannedQrLbl.setForeground(Color.BLUE);
		scannedQrLbl.setBounds(24, 190, 172, 22);
		contentPane.add(scannedQrLbl);

		JLabel lblScanned = new JLabel("Scanned Information:");
		lblScanned.setHorizontalAlignment(SwingConstants.LEFT);
		lblScanned.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblScanned.setBounds(21, 54, 166, 22);
		contentPane.add(lblScanned);

		lastnameLbl = new JLabel("Lastname");
		lastnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		lastnameLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lastnameLbl.setBounds(21, 87, 172, 22);
		contentPane.add(lastnameLbl);

		firstnameLbl = new JLabel("Firstname");
		firstnameLbl.setHorizontalAlignment(SwingConstants.LEFT);
		firstnameLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		firstnameLbl.setBounds(21, 106, 179, 22);
		contentPane.add(firstnameLbl);

		courseLbl = new JLabel("Course");
		courseLbl.setHorizontalAlignment(SwingConstants.LEFT);
		courseLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		courseLbl.setBounds(21, 139, 179, 22);
		contentPane.add(courseLbl);

		yearLevelLbl = new JLabel("Year Level");
		yearLevelLbl.setHorizontalAlignment(SwingConstants.LEFT);
		yearLevelLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		yearLevelLbl.setBounds(21, 157, 179, 22);
		contentPane.add(yearLevelLbl);

		statusLbl = new JLabel("");
		statusLbl.setForeground(Color.GREEN);
		statusLbl.setHorizontalAlignment(SwingConstants.LEFT);
		statusLbl.setFont(new Font("Tahoma", Font.ITALIC, 15));
		statusLbl.setBounds(21, 220, 179, 22);
		contentPane.add(statusLbl);

	}

	/**
	 * Start capturing from webcam and display on JLabel
	 */
	public void startCamera() {
		camera = new VideoCapture(0); // default camera

		if (!camera.isOpened()) {
			System.out.println("Error: Camera not detected!");
			return;
		}

		Mat frame = new Mat();

		while (true) {
			if (camera.read(frame)) {
				BufferedImage image = matToBufferedImage(frame);

				if (image != null) {
					ImageIcon icon = new ImageIcon(image.getScaledInstance(
							cameraDialog.getWidth(), cameraDialog.getHeight(), Image.SCALE_SMOOTH));
					SwingUtilities.invokeLater(() -> cameraDialog.setIcon(icon));

					try {
						LuminanceSource source = new BufferedImageLuminanceSource(image);
						BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						Result result = new MultiFormatReader().decode(bitmap);

						if (result != null) {
							String scannedText = result.getText();

							// Use instance variables instead of getComponent()
							processScannedQR(scannedText, (String) eventsDropdown.getSelectedItem(), (String) attTypeDropdown.getSelectedItem());

							SwingUtilities.invokeLater(() -> {
								statusLbl.setText("Attendance Logged!");
								cameraDialog.setBorder(new LineBorder(Color.GREEN, 4));
								scannedQrLbl.setText(scannedText);
							});

							Thread.sleep(200); // Optional: short delay
						} else {
							SwingUtilities.invokeLater(() -> statusLbl.setText("Failed to Log!"));
						}

						// Optional: Add small delay so it doesn't reset too fast
						Thread.sleep(200);
					} catch (NotFoundException e) {
						SwingUtilities.invokeLater(() -> cameraDialog.setBorder(null));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			try {
				Thread.sleep(30); // simulate ~30 FPS
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void processScannedQR(String scannedId, String selectedEvent, String attType) {
		try {
			db_conn db = new db_conn();
			db.connect();

			// Step 1: Get the event ID
			String evIdQuery = "SELECT ev_id FROM events_tbl WHERE ev_title = ?";
			PreparedStatement evStmt = db.con.prepareStatement(evIdQuery);
			evStmt.setString(1, selectedEvent);
			ResultSet evRs = evStmt.executeQuery();

			int evId = -1;
			if (evRs.next()) {
				evId = evRs.getInt("ev_id");
			} else {
				System.out.println("Event not found.");
				evRs.close();
				evStmt.close();
				db.con.close();
				return;
			}
			evRs.close();
			evStmt.close();

			// Step 2: Check if record exists
			String checkQuery = "SELECT * FROM att_log_tbl WHERE ev_id = ? AND stud_sch_id = ?";
			PreparedStatement checkStmt = db.con.prepareStatement(checkQuery);
			checkStmt.setInt(1, evId);
			checkStmt.setString(2, scannedId);
			ResultSet checkRs = checkStmt.executeQuery();

			boolean exists = checkRs.next();
			checkRs.close();
			checkStmt.close();

			// Step 3: Decide column to update
			String column = "";
			switch (attType) {
				case "AM - IN":
					column = "am_in";
					break;
				case "AM - OUT":
					column = "am_out";
					break;
				case "PM - IN":
					column = "pm_in";
					break;
				case "PM - OUT":
					column = "pm_out";
					break;
				default:
					// fallback or invalid
					column = "";
			}

			if (column.isEmpty()) {
				SwingUtilities.invokeLater(() -> statusLbl.setText("Invalid Attendance Type!"));
				db.con.close();
				return;
			}

			if (!exists && (attType.equals("AM - IN") || attType.equals("PM - IN"))) {
				// Insert if AM IN or PM IN and no record exists
				String insertQuery = "INSERT INTO att_log_tbl (ev_id, stud_sch_id, " + column + ") VALUES (?, ?, CURRENT_TIMESTAMP)";
				PreparedStatement insertStmt = db.con.prepareStatement(insertQuery);
				insertStmt.setInt(1, evId);
				insertStmt.setString(2, scannedId);
				insertStmt.executeUpdate();
				insertStmt.close();
				SwingUtilities.invokeLater(() -> statusLbl.setText("Attendance Logged!"));
			} else if (exists) {
				// Update for any other type
				String updateQuery = "UPDATE att_log_tbl SET " + column + " = CURRENT_TIMESTAMP WHERE ev_id = ? AND stud_sch_id = ?";
				PreparedStatement updateStmt = db.con.prepareStatement(updateQuery);
				updateStmt.setInt(1, evId);
				updateStmt.setString(2, scannedId);
				updateStmt.executeUpdate();
				updateStmt.close();
				SwingUtilities.invokeLater(() -> statusLbl.setText("Attendance Logged!"));
			} else {
				SwingUtilities.invokeLater(() -> statusLbl.setText("Invalid Insert Attempt!"));
			}
			// Step 4: Fetch student details
			String studentQuery = "SELECT stud_fname, stud_lname, stud_ename, stud_course, stud_year_level FROM student_tbl WHERE stud_sch_id = ?";
			PreparedStatement studStmt = db.con.prepareStatement(studentQuery);
			studStmt.setString(1, scannedId);
			ResultSet studRs = studStmt.executeQuery();

			if (studRs.next()) {
			    String fname = studRs.getString("stud_fname");
			    String lname = studRs.getString("stud_lname");
			    String ename = studRs.getString("stud_ename");
			    String course = studRs.getString("stud_course");
			    String yearLevel = studRs.getString("stud_year_level");

			    // Update labels on the UI thread
			    SwingUtilities.invokeLater(() -> {
			        lastnameLbl.setText("Lastname: " + lname);
			        firstnameLbl.setText("Firstname: " + fname + " " + ename);
			        courseLbl.setText("Course: " + course);
			        yearLevelLbl.setText("Year Level: " + yearLevel);
			    });
			} else {
			    SwingUtilities.invokeLater(() -> {
			        lastnameLbl.setText("Lastname: N/A");
			        firstnameLbl.setText("Firstname: N/A");
			        courseLbl.setText("Course: N/A");
			        yearLevelLbl.setText("Year Level: N/A");
			    });
			}

			studRs.close();
			studStmt.close();

			db.con.close();

		} catch (Exception e) {
			e.printStackTrace();
			SwingUtilities.invokeLater(() -> statusLbl.setText("Error Logging Attendance!"));
		}
	}

	/**
	 * Convert Mat to BufferedImage
	 */
	private BufferedImage matToBufferedImage(Mat mat) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (mat.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		BufferedImage img = new BufferedImage(mat.width(), mat.height(), type);
		mat.get(0, 0, ((DataBufferByte) img.getRaster().getDataBuffer()).getData());
		return img;
	}

	private void loadEvents(JComboBox<String> dropdown) {
		try {
			// Create an instance of db_conn to connect to the database
			db_conn db = new db_conn();
			db.connect(); // Assuming db.connect() sets db.con

			// SQL query to get event names
			String query = "SELECT ev_title FROM events_tbl";

			// Prepare the statement
			PreparedStatement stmt = db.con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();

			// Loop through the result set and add each event to the dropdown
			while (rs.next()) {
				dropdown.addItem(rs.getString("ev_title"));
			}

			rs.close();
			stmt.close();
			db.con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

