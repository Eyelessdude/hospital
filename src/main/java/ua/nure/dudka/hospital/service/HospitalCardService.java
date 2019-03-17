package ua.nure.dudka.hospital.service;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.db.DBException;
import ua.nure.dudka.hospital.db.HospitalCardManager;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.entity.HospitalCard;

import java.util.ArrayList;
import java.util.List;

public class HospitalCardService {
    private static final HospitalCardManager hospitalCardManager = HospitalCardManager.getInstance();
    private static final Logger LOG = Logger.getLogger(HospitalCardService.class);

    public List<HospitalCard> getAllCards() {
        List<HospitalCard> hospitalCards = new ArrayList<>();

        try {
            hospitalCards = hospitalCardManager.findAllHospitalCards();
        } catch (DBException e) {
            LOG.error(e.getMessage());
        }

        return hospitalCards;
    }

    public boolean insertHospitalCard(HospitalCard hospitalCard) {
        boolean result = false;

        try {
            result = hospitalCardManager.createHospitalCard(hospitalCard);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return result;
    }

    public HospitalCard findCardById(Integer id) {
        HospitalCard hospitalCard = null;

        try {
            hospitalCard = hospitalCardManager.findHospitalCardById(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return hospitalCard;
    }

    public boolean updateHospitalCard(HospitalCard hospitalCard) {
        boolean result = false;

        try {
            result = hospitalCardManager.updateHospitalCard(hospitalCard);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Client> findAllDoctorClients(int docId) {
        List<Client> clients = null;

        try {
            clients = hospitalCardManager.findAllDoctorPatients(docId);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public List<HospitalCard> findAllHospitalCardsByDoctorId(int id) {
        List<HospitalCard> hospitalCards = null;

        try {
            hospitalCards = hospitalCardManager.findHospitalCardsByDoctorId(id);
        } catch (DBException e) {
            e.printStackTrace();
        }

        return hospitalCards;
    }
}
