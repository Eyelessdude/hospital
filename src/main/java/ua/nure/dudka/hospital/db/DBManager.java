package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class);
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
        Connection connection = null;

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource dataSource = (DataSource) envContext.lookup("jdbc/Hospital");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            logger.error("Cannot obtain a connection from the pool", e);
        }

        return connection;
    }
}
