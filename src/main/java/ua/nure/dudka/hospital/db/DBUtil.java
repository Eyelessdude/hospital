package ua.nure.dudka.hospital.db;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Diagnosis;
import ua.nure.dudka.hospital.constants.Fields;
import ua.nure.dudka.hospital.constants.PatientStatus;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.entity.HospitalCard;

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

    public static HospitalCard extractHospitalCard(ResultSet resultSet) throws SQLException {
        HospitalCard hospitalCard = new HospitalCard();
        Client doctor = new Client();
        Client patient = new Client();
        Client nurse = new Client();
        int k = 1;

        hospitalCard.setId(resultSet.getInt(k++));

        k = extractClientByCounter(resultSet, doctor, k);
        hospitalCard.setDoctor(doctor);

        k = extractClientByCounter(resultSet, patient, k);

        hospitalCard.setPatient(patient);

        k = extractClientByCounter(resultSet, nurse, k);
        hospitalCard.setNurse(nurse);

        PatientStatus patientStatus = PatientStatus.getStatusByName(resultSet.getString(k++));
        hospitalCard.setPatientStatus(patientStatus);
        Diagnosis diagnosis = Diagnosis.getDiagnosisByName(resultSet.getString(k++));
        hospitalCard.setPatientDiagnosis(diagnosis);
        Diagnosis finalDiagnosis = Diagnosis.getDiagnosisByName(resultSet.getString(k++));
        hospitalCard.setPatientFinalDiagnosis(finalDiagnosis);

        hospitalCard.setPatientMedicines(HospitalCard.parseData(resultSet.getString(k++)));
        hospitalCard.setPatientOperations(HospitalCard.parseData(resultSet.getString(k++)));
        hospitalCard.setPatientProcedures(HospitalCard.parseData(resultSet.getString(k)));

        return hospitalCard;
    }

    private static int extractClientByCounter(ResultSet resultSet, Client patient, int k) throws SQLException {
        Role role;
        patient.setId(resultSet.getInt(k++));
        patient.setLogin(resultSet.getString(k++));
        patient.setPassword(resultSet.getString(k++));
        patient.setName(resultSet.getString(k++));
        patient.setSurname(resultSet.getString(k++));
        role = Role.getRoleByName((resultSet.getString(k++)));
        patient.setRole(role);
        patient.setAdditionalInfo(resultSet.getString(k++));
        return k;
    }


}
