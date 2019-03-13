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

    public static DoctorCategory getDoctorCategoryByName(String name) {
        DoctorCategory findCategory = null;
        for (DoctorCategory category : values()) {
            if (category.getName().equals(name)) {
                findCategory = category;
            }
        }

        return findCategory;
    }

    private String getName() {
        return category;
    }
}
