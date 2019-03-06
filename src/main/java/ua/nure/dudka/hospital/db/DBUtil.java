package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Fields;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class DBUtil {
    private static final Logger LOG = Logger.getLogger(DBUtil.class);

    public static Client extractClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        int k = 1;
        client.setId(resultSet.getInt(k++));
        client.setLogin(resultSet.getString(k++));
        client.setPassword(resultSet.getString(k++));
        client.setName(resultSet.getString(k++));
        client.setSurname(resultSet.getString(k++));
        Role role = Role.getRoleByName((resultSet.getString(k++)));
        client.setRole(role);
        client.setAdditionalInfo(resultSet.getString(k));

        return client;
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LOG.error("Can't close the resource: " + closeable.toString());
            }
        }
    }
}
