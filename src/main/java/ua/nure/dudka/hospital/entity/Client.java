package ua.nure.dudka.hospital.entity;

public class Client {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private int roleId;
    private String additionalInfo;

    public Client() {}

    public Client(String login, String password, String name, String surname, int roleId, String additionalInfo) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
        this.additionalInfo = additionalInfo;
    }

    public Client(int id, String login, String password, String name, String surname, int roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
    }

    public Client(int id, String login, String password, String name, String surname, int roleId, String additionalInfo) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roleId = roleId;
        this.additionalInfo = additionalInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        String client = "Client name: " + name + ", surname: " + surname + " role_id: " + roleId;
        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            client += " additional info: " + additionalInfo;
        }

        return client;
    }
}
