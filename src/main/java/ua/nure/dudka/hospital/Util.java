package ua.nure.dudka.hospital;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.DoctorCategory;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {
    private static Logger logger = Logger.getLogger(Util.class);

    public static boolean parseClientAdditionalInfo(Client client) {
        boolean isValid = false;
        int clientRoleId = client.getRoleId();
        String clientAdditionalInfo = client.getAdditionalInfo();

        if (clientRoleId == Role.PACIENT.getId()) {
            isValid = parseDate(clientAdditionalInfo);
        } else if (clientRoleId == Role.DOCTOR.getId()) {
            isValid = parseCategory(clientAdditionalInfo);
        } else if (clientRoleId == Role.ADMIN.getId() || clientRoleId == Role.NURSE.getId()) {
            isValid = clientAdditionalInfo.isEmpty();
        }

        return isValid;
    }

    private static boolean parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            logger.error("Invalid date type was inserted: " + date);
        }
        return false;
    }

    private static boolean parseCategory(String clientCategory) {
        for (DoctorCategory category : DoctorCategory.values()) {
            if (category.toString().equals(clientCategory)) {
                return true;
            }
        }

        return false;
    }
}
