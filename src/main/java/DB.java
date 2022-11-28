import java.sql.*;
import java.util.Properties;

public class DB {
    private final String DB_NAME;
    private final String DB_USERNAME;
    private final String DB_PASS;

    private final Connection db;

    public DB(String dbname, String dbusername, String dbpass) throws SQLException{
        this.DB_NAME = dbname;
        this.DB_USERNAME = dbusername;
        this.DB_PASS = dbpass;

        System.out.println("Starting db connection to " + dbname);
        this.db = this.connectInit();
        this.testQuary();
        System.out.println("Connection is successful");
    }

    private Connection connectInit() throws SQLException {
        String url = "jdbc:postgresql://localhost/" + this.DB_NAME;
        Properties props = new Properties();
        props.setProperty("user", this.DB_USERNAME);
        props.setProperty("password", this.DB_PASS);
//        props.setProperty("ssl", "true");
        Connection db = DriverManager.getConnection(url, props);
        return db;
    }

    public boolean isUserExist(Long id) throws SQLException {
        // Check if user already exist in db
        System.out.println("Checking out user = " + id + " in db");
        Statement st = db.createStatement();
        String sql = "SELECT COUNT(*) FROM bot_user WHERE userid = " + id;
        ResultSet rs = st.executeQuery(sql);
        rs.next(); // Initially cursor is before first row, so we need to move it to first row
        boolean answer = rs.getLong(1) != 0;
        rs.close();
        st.close();
        return answer;
    }

    public void addUser(Long id, String inst, int course, String group) throws SQLException {
        // Check if user already exist in db
        if (!this.isUserExist(id)) {
            // If user doesn't exist, when we add him to db
            System.out.println("User isn't exist in db. Trying to insert " + id + " " + inst
                    + " " + course + " " + group);
            String sql = "INSERT INTO bot_user(userid, institute, course, group_name) VALUES (?, ?, ?, ?);";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setLong(1, id);
            pst.setString(2, inst);
            pst.setInt(3, course);
            pst.setString(4, group);
            pst.executeUpdate();
            System.out.println("Insert successful");

            pst.close();
        } else {
            // TODO: make exceptions and throw here
            System.out.println("User already exist in db.");
        }
    }

    public void delUser(Long id) throws SQLException {
        // Check if user not exist in db
        if (this.isUserExist(id)) {
            // If user exist, when we delete him from db
            System.out.println("User exist in db. Trying to delete " + id);
            String sql = "DELETE FROM bot_user WHERE userid = ?;";
            PreparedStatement pst = db.prepareStatement(sql);
            pst.setLong(1, id);
            pst.executeUpdate();
            System.out.println("Delete successful");

            pst.close();
        } else {
            // TODO: make exceptions and throw here
            System.out.println("User already exist in db.");
        }
    }

    public String getUserString(Long id) throws SQLException {
        String userStr = null;
        String sql = "SELECT * FROM bot_user WHERE userid = ?;";
        PreparedStatement pst = db.prepareStatement(sql);
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            userStr = rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4);
        }
        rs.close();
        pst.close();
        if (userStr == null) throw new SQLException("User " + id + "isn't exist in db");
        return userStr;
    }

    public void testQuary() throws SQLException {
        Statement st = db.createStatement();
        ResultSet rs = st.executeQuery("SELECT VERSION()");
        while (rs.next()) {
            System.out.println("Version: " + rs.getString(1));
        }
        rs.close();
        st.close();
    }
}
