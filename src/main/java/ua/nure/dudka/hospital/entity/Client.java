package ua.nure.dudka.hospital.entity;

import ua.nure.dudka.hospital.constants.Role;

public class Client {
    private int id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private String additionalInfo;

    public Client() {
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        String client = "Client name: " + name + ", surname: " + surname + " role: " + role.getName();
        if (additionalInfo != null && !additionalInfo.isEmpty()) {
            client += " additional info: " + additionalInfo;
        }

        return client;
    }

    public int compareTo(Client client) {
        return this.getName().compareToIgnoreCase(client.getName());
    }
}
