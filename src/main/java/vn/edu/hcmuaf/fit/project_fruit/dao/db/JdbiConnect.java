package vn.edu.hcmuaf.fit.project_fruit.dao.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;

public class JdbiConnect {
    static Jdbi jdbi;
    static String url = "jdbc:mysql://" + DbProperties.host() + ":"+ DbProperties.port() + "/" + DbProperties.dbname() + "?" + DbProperties.option();

    public static Jdbi get(){
        if(jdbi == null) makeConnect();
        return jdbi;
    }

    private static void makeConnect() {
        MysqlDataSource source = new MysqlDataSource();
        source.setURL(url);
        source.setUser(DbProperties.username());
        source.setPassword(DbProperties.password());

        try {
            source.setUseCompression(true);
            source.setAutoReconnect(true);
        } catch (SQLException e) {
        }
        jdbi = Jdbi.create(source);
    }

    public static void main(String[] args) {
        JdbiConnect.get();
    }
}
