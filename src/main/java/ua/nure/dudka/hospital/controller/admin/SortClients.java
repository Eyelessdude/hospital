package ua.nure.dudka.hospital.controller.admin;

import ua.nure.dudka.hospital.constants.Role;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/admin/sortClients")
public class SortClients extends HttpServlet {
    private static final ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = Role.getRoleByName(req.getParameter("clientGetRole"));
        List<Client> patientList = clientService.getAllByRole(role);
        Comparator<Client> comparator = Client::compareTo;
        patientList.sort(comparator);
        req.setAttribute("clientsList", patientList);
        req.setAttribute("clientGetRole", role.getName());

        req.getRequestDispatcher("/WEB-INF/view/allClientsByRole.jsp").forward(req, resp);
    }
}
