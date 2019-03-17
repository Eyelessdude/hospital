package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.Util;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    private static final Logger LOG = Logger.getLogger(ClientManager.class);
    private static final String FIND_CLIENT_BY_LOGIN_AND_PASSWORD = "SELECT client.id, client.login, client.password, client.name, client.surname, role.name role_name, additional_info " +
            "FROM client LEFT JOIN role ON client.role_id = role.id WHERE login=? AND password=?";
    private static final String CREATE_CLIENT = "INSERT INTO client VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_BY_ROLE = "SELECT client.id, client.login, client.password, client.name, client.surname, role.name role_name, additional_info FROM client " +
            "LEFT JOIN role ON client.role_id = role.id WHERE role.name=?";
    private static final String FIND_CLIENT_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String FIND_CLIENT_BY_LOGIN = "SELECT client.id, client.login, client.password, client.name, client.surname, role.name role_name, additional_info" +
            " FROM client LEFT JOIN role ON client.role_id = role.id WHERE login = ?";
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static ClientManager instance;

    public static synchronized ClientManager getInstance() {
        if (instance == null) {
            instance = new ClientManager();
        }

        return instance;
    }

    private ClientManager() {
    }

    public boolean createClient(Client client) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        int k = 1;

        if (!Util.parseClientAdditionalInfo(client)) {
            LOG.error("Invalid format of additional data: " + client.getAdditionalInfo() + " for client" + client);
            throw new DBException("Invalid format of additional data for this user:  Date format: dd-MM-yyyy or doctor category");
        }

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(k++, client.getLogin());
            preparedStatement.setString(k++, client.getPassword());
            preparedStatement.setString(k++, client.getName());
            preparedStatement.setString(k++, client.getSurname());
            preparedStatement.setInt(k++, client.getRole().getId());
            preparedStatement.setString(k, client.getAdditionalInfo());

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedClientId = resultSet.getInt(1);
                    client.setId(generatedClientId);
                    result = true;
                }
            }

        } catch (SQLException e) {
            LOG.error("Can't create client: " + client.getLogin() + e);
            throw new DBException("Can't create client: " + client.getLogin(), e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return result;
    }

    public Client findByLogin(String login) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                client = DBUtil.extractClient(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOG.error("Can't find client with login: " + login + e);
            throw new DBException("Can't find client with login:" + login, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return client;
    }

    public List<Client> findAllByRole(Role role) throws DBException {
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_BY_ROLE);

            preparedStatement.setString(1, role.getName());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clients.add(DBUtil.extractClient(resultSet));
            }

            if (clients.size() == 0) {
                LOG.error("Can't find any users with role: " + role.toString());
                throw new DBException("Can't find any users with role: " + role.toString());
            }
        } catch (SQLException e) {
            LOG.error("Can't find client with role name: " + role.toString() + " and id: " + role.getId() + e);
            throw new DBException("Can't find client with role: " + role.toString(), e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return clients;
    }

    public Client findByLoginAndPassword(String login, String password) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Client client = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_LOGIN_AND_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                client = DBUtil.extractClient(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOG.error("Can't find client with such combination of login and password");
            throw new DBException("Can't find client with such combination of login and password");
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return client;
    }
}
