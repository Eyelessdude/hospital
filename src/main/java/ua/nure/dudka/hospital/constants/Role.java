package ua.nure.dudka.hospital.constants;

public enum Role {
    ADMIN(1, "admin"), DOCTOR(2, "doctor"), NURSE(3, "nurse"), PATIENT(4, "patient");

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role getRoleByName(String name) {
        Role findRole = null;
        for (Role role : values()) {
            if (role.getName().equals(name)) {
                findRole = role;
            }
        }

        return findRole;
    }

    @Override
    public String toString() {
        return name;
    }
}
