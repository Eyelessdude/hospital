package ua.nure.dudka.hospital.constants;

public enum Role {
    ADMIN(1, "admin"), DOCTOR(2, "doctor"), NURSE(3, "nurse"), PACIENT(4, "pacient");

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
