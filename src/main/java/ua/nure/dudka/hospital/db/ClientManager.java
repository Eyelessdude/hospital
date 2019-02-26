package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.Util;
import ua.nure.dudka.hospital.constants.DoctorCategory;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    private static final Logger logger = Logger.getLogger(ClientManager.class);
    private static final String CREATE_CLIENT = "INSERT INTO client VALUES(DEFAULT, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_ALL_BY_ROLE = "SELECT * FROM client WHERE role_id = ?";
    private static final String FIND_CLIENT_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String FIND_ALL_DOCTOR_PATIENTS = "SELECT client.* FROM client, hospital_card WHERE hospital_card.doctor_id = ? AND client.id = hospital_card.patient_id";
    private DBManager dbManager = DBManager.getInstance();
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
            logger.error("Invalid format of additional data: " + client.getAdditionalInfo() + " for client" + client);
            throw new DBException("Invalid format of additional data for this user:  Date format: dd-MM-yyyy or doctor category");
        }

        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(k++, client.getLogin());
            preparedStatement.setString(k++, client.getPassword());
            preparedStatement.setString(k++, client.getName());
            preparedStatement.setString(k++, client.getSurname());
            preparedStatement.setInt(k++, client.getRoleId());
            preparedStatement.setString(k++, client.getAdditionalInfo());

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedClientId = resultSet.getInt(1);
                    client.setId(generatedClientId);
                    result = true;
                }
            }

        } catch (SQLException e) {
            logger.error("Can't create client: " + client.getLogin() + e);
            throw new DBException("Can't create client: " + client.getLogin(), e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return result;
    }

    public Client findById(int id) throws DBException {
        Client client = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_CLIENT_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                client = DBUtil.extractClient(resultSet);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Can't find client with id: " + id + e);
            throw new DBException("Can't find client with id:" + id, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return client;
    }

    //TODO Move this method to HospitalCardManager
    public List<Client> findAllDoctorPatients(Client client) throws DBException {
        if (client.getRoleId() != Role.DOCTOR.getId()) {
            logger.error("Trying to find patients not for doctor: " + client);
            throw new DBException("This client isn't a doctor!");
        }
        
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_DOCTOR_PATIENTS);
            preparedStatement.setInt(1, client.getId());

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                clients.add(DBUtil.extractClient(resultSet));
            }

            if (clients.size() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Can't find any patients for doctor: " + client + e);
            throw new DBException("Can't find any patients for doctor: " + client, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return clients;
    }

    public List<Client> findAllByRole(Role role) throws DBException {
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dbManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_BY_ROLE);

            preparedStatement.setInt(1, role.getId());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clients.add(DBUtil.extractClient(resultSet));
            }

            if (clients.size() == 0) {
                logger.error("Can't find any users with role: " + role.toString());
                throw new DBException("Can't find any users with role: " + role.toString());
            }
        } catch (SQLException e) {
            logger.error("Can't find client with role name: " + role.toString() + " and id: " + role.getId() + e);
            throw new DBException("Can't find client with role: " + role.toString(), e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return clients;
    }

    public static void main(String[] args) throws DBException {
        ClientManager clientManager = ClientManager.getInstance();
        Client client = new Client("testAdmin", "test", "and this?", "hope so", 1, "");
        clientManager.createClient(client);
    }
}
