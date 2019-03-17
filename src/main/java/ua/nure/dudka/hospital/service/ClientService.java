package ua.nure.dudka.hospital.service;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.db.ClientManager;
import ua.nure.dudka.hospital.db.DBException;
import ua.nure.dudka.hospital.entity.Client;

import java.util.List;

public class ClientService {
    private static final ClientManager clientManager = ClientManager.getInstance();
    private static final Logger LOG = Logger.getLogger(ClientService.class);

    public boolean insertClient(Client client) {
        boolean result = false;

        try {
            result = clientManager.createClient(client);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Client getClientByLoginAndPassword(String login, String password) {
        Client client = null;

        try {
            client = clientManager.findByLoginAndPassword(login, password);
        } catch (DBException e) {
            LOG.error(e.getMessage());
        }

        return client;
    }

    public List<Client> getAllByRole(Role role) {
        List<Client> clients = null;

        try {
            clients = clientManager.findAllByRole(role);
        } catch (DBException e) {
            LOG.error(e.getMessage());
        }

        return clients;
    }

    public Client getClientByLogin(String login) {
        Client client = null;

        try {
            client = clientManager.findByLogin(login);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return client;
    }
}
