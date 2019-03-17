package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.entity.HospitalCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalCardManager {
    private static final String FIND_ALL_DOCTOR_PATIENTS = "SELECT cl.id, cl.login, cl.password, cl.name, cl.surname, r.name role_name, additional_info FROM hospital_card LEFT JOIN client cl on hospital_card.patient_id = cl.id LEFT JOIN role r on cl.role_id = r.id WHERE doctor_id=?";
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
    private static final String FIND_HOSPITAL_CARD_BY_ID = "SELECT hosp.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, " +
            "pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, " +
            "nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, " +
            "hosp.patient_status,hosp.patient_diagnosis, hosp.patient_final_diagnosis, hosp.patient_medicine, hosp.patient_operations, hosp.patient_procedures " +
            "FROM hospital_card hosp " +
            "LEFT JOIN client doc ON hosp.doctor_id = doc.id " +
            "LEFT JOIN client pat ON hosp.patient_id = pat.id " +
            "LEFT JOIN client nurse ON hosp.nurse_id = nurse.id " +
            "LEFT JOIN role doc_role ON doc.role_id = doc_role.id " +
            "LEFT JOIN role pat_role ON pat.role_id = pat_role.id " +
            "LEFT JOIN role nurse_role ON nurse.role_id = nurse_role.id WHERE hosp.id=?";

    private static final String FIND_HOSPITAL_CARD_BY_DOCTOR_ID = "SELECT hosp.id, doc.id, doc.login, doc.password, doc.name,  doc.surname, doc_role.name role_name, doc.additional_info, " +
            "pat.id, pat.login, pat.password, pat.name, pat.surname,  pat_role.name role_name, pat.additional_info, " +
            "nurse.id, nurse.login, nurse.password, nurse.name, nurse.surname, nurse_role.name role_name, nurse.additional_info, " +
            "hosp.patient_status,hosp.patient_diagnosis, hosp.patient_final_diagnosis, hosp.patient_medicine, hosp.patient_operations, hosp.patient_procedures " +
            "FROM hospital_card hosp " +
            "LEFT JOIN client doc ON hosp.doctor_id = doc.id " +
            "LEFT JOIN client pat ON hosp.patient_id = pat.id " +
            "LEFT JOIN client nurse ON hosp.nurse_id = nurse.id " +
            "LEFT JOIN role doc_role ON doc.role_id = doc_role.id " +
            "LEFT JOIN role pat_role ON pat.role_id = pat_role.id " +
            "LEFT JOIN role nurse_role ON nurse.role_id = nurse_role.id WHERE hosp.doctor_id=?";
    private static final String CREATE_HOSPITAL_CARD = "INSERT INTO hospital_card VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_HOSPITAL_CARD = "UPDATE hospital_card SET doctor_id = ?, patient_id = ?, nurse_id = ?, patient_status = ?, patient_diagnosis = ?, patient_final_diagnosis = ?, patient_medicine = ?, patient_operations = ?, patient_procedures = ? WHERE id=?";
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private static final Logger LOG = Logger.getLogger(HospitalCardManager.class);
    private static final String SEPARATOR = "/";
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
        List<HospitalCard> hospitalCards = new ArrayList<>();
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

    public List<Client> findAllDoctorPatients(int id) throws DBException {
        List<Client> clients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_DOCTOR_PATIENTS);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(DBUtil.extractClient(resultSet));
            }

            if (clients.size() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LOG.error("Can't find any patients for doctor with id: " + id + e);
            throw new DBException("Can't find any patients for doctor with id: " + id, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return clients;
    }

    public boolean createHospitalCard(HospitalCard hospitalCard) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result = false;
        int k = 1;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CREATE_HOSPITAL_CARD, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(k++, hospitalCard.getDoctor().getId());
            preparedStatement.setInt(k++, hospitalCard.getPatient().getId());
            if (hospitalCard.getNurse() != null) {
                preparedStatement.setInt(k++, hospitalCard.getNurse().getId());
            } else {
                preparedStatement.setNull(k++, Types.INTEGER);
            }
            preparedStatement.setString(k++, hospitalCard.getPatientStatus().getName());
            preparedStatement.setString(k++, hospitalCard.getPatientDiagnosis().getName());
            preparedStatement.setString(k++, hospitalCard.getPatientFinalDiagnosis().getName());

            String medicine = String.join(SEPARATOR, hospitalCard.getPatientMedicines());
            preparedStatement.setString(k++, medicine);

            String operations = String.join(SEPARATOR, hospitalCard.getPatientOperations());
            preparedStatement.setString(k++, operations);

            String procedures = String.join(SEPARATOR, hospitalCard.getPatientProcedures());
            preparedStatement.setString(k, procedures);

            if (preparedStatement.executeUpdate() > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int generatedClientId = resultSet.getInt(1);
                    hospitalCard.setId(generatedClientId);
                    result = true;
                }
            }
        } catch (SQLException e) {
            LOG.error("Can't create hospital card! Cause: " + e);
            throw new DBException("Can't create hospital card!", e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return result;
    }

    public HospitalCard findHospitalCardById(Integer id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HospitalCard hospitalCard = new HospitalCard();

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_HOSPITAL_CARD_BY_ID);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hospitalCard = DBUtil.extractHospitalCard(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital card with such id: " + id + "Error: " + e);
            throw new DBException("Can't find hospital card with such id: " + id, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return hospitalCard;
    }

    public List<HospitalCard> findHospitalCardsByDoctorId(Integer id) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<HospitalCard> hospitalCards = new ArrayList<>();

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(FIND_HOSPITAL_CARD_BY_DOCTOR_ID);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                hospitalCards.add(DBUtil.extractHospitalCard(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Can't find hospital cards with such doctor id: " + id + "Error: " + e);
            throw new DBException("Can't find hospital cards with such doctor id: " + id, e);
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return hospitalCards;
    }

    public boolean updateHospitalCard(HospitalCard hospitalCard) throws DBException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean result = false;
        int k = 1;

        try {
            connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_HOSPITAL_CARD);

            preparedStatement.setInt(k++, hospitalCard.getDoctor().getId());
            preparedStatement.setInt(k++, hospitalCard.getPatient().getId());
            if (hospitalCard.getNurse() != null) {
                preparedStatement.setInt(k++, hospitalCard.getNurse().getId());
            } else {
                preparedStatement.setNull(k++, Types.INTEGER);
            }
            preparedStatement.setString(k++, hospitalCard.getPatientStatus().getName());
            preparedStatement.setString(k++, hospitalCard.getPatientDiagnosis().getName());
            preparedStatement.setString(k++, hospitalCard.getPatientFinalDiagnosis().getName());

            String medicine = String.join(SEPARATOR, hospitalCard.getPatientMedicines());
            preparedStatement.setString(k++, medicine);

            String operations = String.join(SEPARATOR, hospitalCard.getPatientOperations());
            preparedStatement.setString(k++, operations);

            String procedures = String.join(SEPARATOR, hospitalCard.getPatientProcedures());
            preparedStatement.setString(k++, procedures);

            preparedStatement.setInt(k, hospitalCard.getId());

            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error("Can't update hospital card with id: " + hospitalCard.getId());
            throw new DBException("Can't update hospital card with id: " + hospitalCard.getId(), e);
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }

        return result;
    }
}
