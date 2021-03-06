package ua.nure.dudka.hospital.controller.admin.doctor;

import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/doctors")
public class ReadAllDoctors extends HttpServlet {
    private static final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Client> doctorsList = clientService.getAllByRole(Role.DOCTOR);
        req.setAttribute("clientsList", doctorsList);
        req.setAttribute("clientGetRole", Role.DOCTOR.getName());
        req.setAttribute("action", "/admin/sortDoctors");
        //TODO Add back attribute like in example to create back button

        req.getRequestDispatcher("/WEB-INF/view/allClientsByRole.jsp").forward(req, resp);
    }
}
