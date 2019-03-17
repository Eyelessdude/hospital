package ua.nure.dudka.hospital.controller.staff;

import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.HospitalCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff/patients")
public class StaffManePage extends HttpServlet {
    private HospitalCardService hospitalCardService = new HospitalCardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int docId = (int) req.getSession().getAttribute("client_id");
        List<Client> clients = hospitalCardService.findAllDoctorClients(docId);
        req.setAttribute("clientsList", clients);
        req.getRequestDispatcher("/WEB-INF/view/staffPage.jsp").forward(req, resp);
    }
}
