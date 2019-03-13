package ua.nure.dudka.hospital.constants;

public enum PatientStatus {
    UNDETERMINED("Undetermined"), GOOD("Good"), FAIR("Fair"), SERIOUS("Serious"),
    CRITICAL("Critical"), DEAD("Dead"), WRITTEN_OUT("Written out");

    private String status;

    PatientStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return status;
    }

    public static PatientStatus getStatusByName(String name) {
        PatientStatus findStatus = null;
        for (PatientStatus patientStatus : values()) {
            if (patientStatus.getName().equals(name)) {
                findStatus= patientStatus;
            }
        }

        return findStatus;
    }

    private String getName() {
        return status;
    }
}
