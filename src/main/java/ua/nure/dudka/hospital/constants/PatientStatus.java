package ua.nure.dudka.hospital.constants;

public enum PatientStatus {
    UNDETERMINED("undetermined"), GOOD("good"), FAIR("fair"), SERIOUS("serious"),
    CRITICAL("critical"), DEAD("dead"), WRITTEN_OUT("written out");

    private String status;

    PatientStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return status;
    }
}
