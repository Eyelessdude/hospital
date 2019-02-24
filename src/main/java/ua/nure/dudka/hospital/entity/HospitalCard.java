package ua.nure.dudka.hospital.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HospitalCard {
    private static final String SPLITTER = "/";
    private int id;
    private int doctorId;
    private int patientId;
    private int nurseId;
    private String patientStatus;
    private String patientDiagnosis;
    private String patientFinalDiagnosis;
    private List<String> patientProcedures;
    private List<String> patientMedicines;
    private List<String> patientOperations;

    public HospitalCard(int id, int patientId, String patientStatus) {
        this.id = id;
        this.patientId = patientId;
        this.patientStatus = patientStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getNurseId() {
        return nurseId;
    }

    public void setNurseId(int nurseId) {
        this.nurseId = nurseId;
    }

    public String getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(String patientStatus) {
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
