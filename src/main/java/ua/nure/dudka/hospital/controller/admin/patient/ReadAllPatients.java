package ua.nure.dudka.hospital.controller.admin.patient;

import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/patients")
public class ReadAllPatients extends HttpServlet {
    private static final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Client> patientList = clientService.getAllByRole(Role.PATIENT);
        session.setAttribute("clientsList", patientList);
        session.setAttribute("clientGetRole", Role.PATIENT.getName());

        req.getRequestDispatcher("/WEB-INF/view/allClientsByRole.jsp").forward(req, resp);
    }
}
