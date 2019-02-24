package ua.nure.dudka.hospital.constants;

public enum DoctorCategory {
    EMERGENCY_MEDICINE_SPECIALIST("Emergency Medicine Specialist"), DERMATOLOGIST("Dermatologist"),
    CARDIOLOGIST("Cardiologist"), IMMUNOLOGIST("Immunologist"), NEUROLOGIST("Neurologist");

    private String category;

    DoctorCategory(String category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return category;
    }
}
