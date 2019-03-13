package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.entity.HospitalCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalCardManager {
    private static final String FIND_ALL_DOCTOR_PATIENTS = "SELECT client.* FROM client, hospital_card WHERE hospital_card.doctor_id = ? AND client.id = hospital_card.patient_id";
    private static final String FIND_ALL_HOSPITAL_CARDS = "SELECT hosp.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, " +
            "pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, " +
            "nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, " +
            "hosp.patient_status,hosp.patient_diagnosis, hosp.patient_final_diagnosis, hosp.patient_medicine, hosp.patient_operations, hosp.patient_procedures " +
            "FROM hospital_card hosp " +
            "LEFT JOIN client doc ON hosp.doctor_id = doc.id " +
            "LEFT JOIN client pat ON hosp.patient_id = pat.id " +
            "LEFT JOIN client nurse ON hosp.nurse_id = nurse.id " +
            "LEFT JOIN role doc_role ON doc.role_id = doc_role.id " +
            "LEFT JOIN role pat_role ON pat.role_id = pat_role.id " +
            "LEFT JOIN role nurse_role ON nurse.role_id = nurse_role.id";
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final Logger LOG = Logger.getLogger(HospitalCardManager.class);
    private static HospitalCardManager instance;

    public static synchronized HospitalCardManager getInstance() {
        if (instance == null) {
            instance = new HospitalCardManager();
        }

        return instance;
    }

    private HospitalCardManager() {
    }

    public List<HospitalCard> findAllHospitalCards() throws DBException {
        List<HospitalCard>  hospitalCards = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = connectionManager.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL_HOSPITAL_CARDS);

            while (resultSet.next()) {
                hospitalCards.add(DBUtil.extractHospitalCard(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital cards");
            throw new DBException("Can't find hospital cards", e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }

        return hospitalCards;
    }

    public List<Client> findAllDoctorPatients(Client client) throws DBException {
        if (client.getRole() != Role.DOCTOR) {
            LOG.error("Trying to find patients not for doctor: " + client);
            throw new DBException("This client isn't a doctor!");
        }

        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_DOCTOR_PATIENTS);
            preparedStatement.setInt(1, client.getId());

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(DBUtil.extractClient(resultSet));
            }

            if (clients.size() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOG.error("Can't find any patients for doctor: " + client + e);
            throw new DBException("Can't find any patients for doctor: " + client, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return clients;
    }
}
