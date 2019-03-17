package ua.nure.dudka.hospital.controller;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Diagnosis;
import ua.nure.dudka.hospital.constants.DoctorCategory;
import ua.nure.dudka.hospital.constants.PatientStatus;
import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.entity.HospitalCard;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class UtilServlet {
    private ClientService clientService = new ClientService();
    private static final Logger LOG = Logger.getLogger(UtilServlet.class);

    public void setRequestListForClient(HttpServletRequest req) {
        req.setAttribute("roles", Role.values());
        req.setAttribute("categories", DoctorCategory.values());
    }

    public void setRequestListForHospitalCard(HttpServletRequest req) {
        req.setAttribute("doctors", getListByRole(Role.DOCTOR));
        req.setAttribute("patients", getListByRole(Role.PATIENT));
        req.setAttribute("nurses", getListByRole(Role.NURSE));
        req.setAttribute("statuses", getStatusesList());
        req.setAttribute("diagnoses", getDiagnosesList());
    }

    private List<Diagnosis> getDiagnosesList() {
        return Arrays.asList(Diagnosis.values());
    }

    private List<PatientStatus> getStatusesList() {
        return Arrays.asList(PatientStatus.values());

    }

    private List<Client> getListByRole(Role role) {
        return clientService.getAllByRole(role);
    }

    public Client setClient(HttpServletRequest req) {
        Client client = new Client();
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Role role = Role.getRoleByName(req.getParameter("role"));
        String additionalInfo = req.getParameter("additionalInfo");
        DoctorCategory doctorCategory = DoctorCategory.getDoctorCategoryByName(req.getParameter("docCategory"));

        client.setName(name);
        client.setSurname(surname);
        client.setLogin(login);
        client.setPassword(password);
        client.setRole(role);
        if (role.equals(Role.DOCTOR)) {
            client.setAdditionalInfo(String.valueOf(doctorCategory));
        } else {
            client.setAdditionalInfo(additionalInfo);
        }

        return client;
    }

    public HospitalCard setHospitalCard(HttpServletRequest req) {
        HospitalCard hospitalCard = new HospitalCard();
        int id = 0;

        String idReq = req.getParameter("id");

        if (idReq != null && !idReq.isEmpty()) {
            id = Integer.parseInt(req.getParameter("id"));
        }
        String nurseLogin = req.getParameter("nurse");

        Client doctor = clientService.getClientByLogin(req.getParameter("doctor"));
        Client patient = clientService.getClientByLogin(req.getParameter("patient"));
        Client nurse = null;
        if (nurseLogin != null) {
            nurse = clientService.getClientByLogin(nurseLogin);
        }
        PatientStatus patientStatus = PatientStatus.getStatusByName(req.getParameter("status"));
        Diagnosis currentDiagnosis = Diagnosis.getDiagnosisByName(req.getParameter("currentDiagnosis"));
        Diagnosis finalDiagnosis = Diagnosis.getDiagnosisByName(req.getParameter("finalDiagnosis"));
        String medicine = req.getParameter("medicine");
        String operation = req.getParameter("operation");
        String procedure = req.getParameter("procedure");

        hospitalCard.setId(id);
        hospitalCard.setDoctor(doctor);
        hospitalCard.setPatient(patient);
        hospitalCard.setNurse(nurse);
        hospitalCard.setPatientStatus(patientStatus);
        hospitalCard.setPatientDiagnosis(currentDiagnosis);
        hospitalCard.setPatientFinalDiagnosis(finalDiagnosis);
        hospitalCard.setPatientMedicines(HospitalCard.parseData(medicine));
        hospitalCard.setPatientOperations(HospitalCard.parseData(operation));
        hospitalCard.setPatientProcedures(HospitalCard.parseData(procedure));

        return hospitalCard;
    }
}
