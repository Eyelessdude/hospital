package ua.nure.dudka.hospital.entity;

import ua.nure.dudka.hospital.constants.PatientStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HospitalCard {
    private static final String SPLITTER = "/";
    private int id;
    private Client doctor;
    private Client patient;
    private int nurseId;
    private PatientStatus patientStatus;
    private String patientDiagnosis;
    private String patientFinalDiagnosis;
    private List<String> patientProcedures;
    private List<String> patientMedicines;
    private List<String> patientOperations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getDoctor() {
        return doctor;
    }

    public void setDoctorId(Client doctor) {
        this.doctor = doctor;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatientId(Client patient) {
        this.patient = patient;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public String getPatientDiagnosis() {
        return patientDiagnosis;
    }

    public void setPatientDiagnosis(String patientDiagnosis) {
        this.patientDiagnosis = patientDiagnosis;
    }

    public String getPatientFinalDiagnosis() {
        return patientFinalDiagnosis;
    }

    public void setPatientFinalDiagnosis(String patientFinalDiagnosis) {
        this.patientFinalDiagnosis = patientFinalDiagnosis;
    }

    public List<String> getPatientProcedures() {
        return patientProcedures;
    }

    public void setPatientProcedures(List<String> patientProcedures) {
        this.patientProcedures = patientProcedures;
    }

    public List<String> getPatientMedicines() {
        return patientMedicines;
    }

    public void setPatientMedicines(List<String> patientMedicines) {
        this.patientMedicines = patientMedicines;
    }

    public List<String> getPatientOperations() {
        return patientOperations;
    }

    public void setPatientOperations(List<String> patientOperations) {
        this.patientOperations = patientOperations;
    }

    private static List<String> parseData(String data) {
        List<String> proceduresList = new ArrayList<>();

        if (!data.isEmpty()) {
            proceduresList = Arrays.asList(data.split(SPLITTER));
        }

        return proceduresList;
    }
}
