package seams1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class db_conn {

    private final String url = "jdbc:mysql://localhost:3306/attendance_system";
    private final String username = "root";
    private final String password = "";

    public Connection con;
    public PreparedStatement prep;
    public ResultSet result;

    public static void main(String[] args) {
        db_conn db = new db_conn();
        db.connect();
    }
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, username, password);
            
        } catch (Exception e) {
            
        }
    }
}
