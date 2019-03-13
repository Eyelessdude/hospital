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

    public static Diagnosis getDiagnosisByName(String name) {
        Diagnosis findDiagnosis = null;
        for (Diagnosis diagnosis : values()) {
            if (diagnosis.getName().equals(name)) {
                findDiagnosis = diagnosis;
            }
        }

        return findDiagnosis;
    }

    private String getName() {
        return diagnosis;
    }
}
