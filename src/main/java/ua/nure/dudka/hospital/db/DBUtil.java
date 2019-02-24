package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Fields;
import ua.nure.dudka.hospital.entity.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public final class DBUtil {
    private static final Logger logger = Logger.getLogger(DBUtil.class);
    private static final String PROPERTIES_FILE_NAME = "app.properties";

    public static String getProperty(String property) {
        Properties properties = new Properties();
        String result = null;

        try (InputStream input = new FileInputStream(PROPERTIES_FILE_NAME)) {
            properties.load(input);

            result = properties.getProperty(property);
        } catch (IOException e) {
            logger.error("Can't get property: " + property + " from file: " + PROPERTIES_FILE_NAME);
        }

        return result;
    }

    public static Client extractClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setId(resultSet.getInt(Fields.ID));
        client.setLogin(resultSet.getString(Fields.LOGIN));
        client.setPassword(resultSet.getString(Fields.PASSWORD));
        client.setName(resultSet.getString(Fields.NAME));
        client.setSurname(resultSet.getString(Fields.SURNAME));
        client.setRoleId(resultSet.getInt(Fields.ROLE_ID));
        client.setAdditionalInfo(resultSet.getString(Fields.ADDITIONAL_INFO));

        return client;
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                logger.error("Can't close the resource: " + closeable.toString());
            }
        }
    }
}
