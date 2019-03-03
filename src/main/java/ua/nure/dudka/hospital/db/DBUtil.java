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
        client.setId(resultSet.getInt(Fields.ID));
        client.setLogin(resultSet.getString(Fields.LOGIN));
        client.setPassword(resultSet.getString(Fields.PASSWORD));
        client.setName(resultSet.getString(Fields.NAME));
        client.setSurname(resultSet.getString(Fields.SURNAME));
        Role role = Role.getRoleByName((resultSet.getString(Fields.ROLE)));
        client.setRole(role);
        client.setAdditionalInfo(resultSet.getString(Fields.ADDITIONAL_INFO));

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
