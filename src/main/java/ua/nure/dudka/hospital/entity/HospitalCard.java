package ua.nure.dudka.hospital.entity;

import ua.nure.dudka.hospital.constants.Diagnosis;
import ua.nure.dudka.hospital.constants.PatientStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HospitalCard {
    private static final String SPLITTER = "/";
    private static final int STRING_START = 0;
    private int id;
    private Client doctor;
    private Client patient;
    private Client nurse;
    private PatientStatus patientStatus;
    private Diagnosis patientDiagnosis;
    private Diagnosis patientFinalDiagnosis;
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

    public void setDoctor(Client doctor) {
        this.doctor = doctor;
    }

    public Client getPatient() {
        return patient;
    }

    public void setPatient(Client patient) {
        this.patient = patient;
    }

    public Client getNurse() {
        return nurse;
    }

    public void setNurse(Client nurse) {
        this.nurse = nurse;
    }

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public Diagnosis getPatientDiagnosis() {
        return patientDiagnosis;
    }

    public void setPatientDiagnosis(Diagnosis patientDiagnosis) {
        this.patientDiagnosis = patientDiagnosis;
    }

    public Diagnosis getPatientFinalDiagnosis() {
        return patientFinalDiagnosis;
    }

    public void setPatientFinalDiagnosis(Diagnosis patientFinalDiagnosis) {
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

    public static List<String> parseData(String data) {
        List<String> proceduresList = new ArrayList<>();

        if (data != null && !data.isEmpty()) {
            if (SPLITTER.equals(data.substring(data.length() - 1))) {
                data = data.substring(STRING_START, data.length() - 1);
            }

            proceduresList = Arrays.asList(data.split(SPLITTER));
        }

        return proceduresList;
    }
}
