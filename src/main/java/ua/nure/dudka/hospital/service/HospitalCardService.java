package ua.nure.dudka.hospital.service;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.db.DBException;
import ua.nure.dudka.hospital.db.HospitalCardManager;
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
}
