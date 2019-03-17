package ua.nure.dudka.hospital.controller.staff;

import ua.nure.dudka.hospital.entity.HospitalCard;
import ua.nure.dudka.hospital.service.HospitalCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff/hospitalCard")
public class ReadAllHospitalCards extends HttpServlet {
    private static final HospitalCardService hospitalCardService = new HospitalCardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int docId = (int) req.getSession().getAttribute("client_id");
        List<HospitalCard> hospitalCards = hospitalCardService.findAllHospitalCardsByDoctorId(docId);
        req.setAttribute("hospitalCards", hospitalCards);

        req.getRequestDispatcher("/WEB-INF/view/allHospitalCards.jsp").forward(req, resp);
    }
}
