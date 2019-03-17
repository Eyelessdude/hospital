package ua.nure.dudka.hospital.constants;

public enum Diagnosis {

    ASTHMA("Asthma"), DEPRESSION("Depression"), HEADACHE("Headache"), HYPERTENSION("Hypertension");

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

    public String getName() {
        return diagnosis;
    }
}
