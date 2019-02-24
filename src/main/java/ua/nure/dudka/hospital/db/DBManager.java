package ua.nure.dudka.hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String CONNECTION_PROPERTY = "connection.url";
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }

        return instance;
    }

    private DBManager() {
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBUtil.getProperty(CONNECTION_PROPERTY));
    }
}
