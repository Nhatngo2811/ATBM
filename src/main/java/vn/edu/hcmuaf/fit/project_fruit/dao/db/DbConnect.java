package vn.edu.hcmuaf.fit.project_fruit.dao.db;

import java.sql.*;

public class DbConnect {
    static String url = "jdbc:mysql://" + DbProperties.host() + ":"+ DbProperties.port() + "/" + DbProperties.dbname() + "?" + DbProperties.option();
    static Connection conn;

    public static Statement get(){
        try {
            if (conn == null || conn .isClosed()){
                makeConnect();
            }
            return conn.createStatement();
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
    // Trả về đối tượng PreparedStatement
    public static PreparedStatement getPreparedStatement(String query, boolean returnGeneratedKeys) {
        try {
            if (conn == null || conn.isClosed()) {
                makeConnect();
            }
            if (returnGeneratedKeys) {
                return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            } else {
                return conn.prepareStatement(query);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    // Trả về một Connection
    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                makeConnect();
            }
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void makeConnect() throws ClassNotFoundException , SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, DbProperties.username(), DbProperties.password());
    }
}

