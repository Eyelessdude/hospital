package ua.nure.dudka.hospital.constants;

public enum Diagnosis {
    //TODO Add realization
    ASTHMA("Asthma"), DEPRESSION("DEPRESSION"), HEADACHE("Headache"), HYPERTENSION("HYPERTENSION");

    private String diagnosis;

    Diagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    @Override
    public String toString() {
        return diagnosis;
    }
}
