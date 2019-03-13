package ua.nure.dudka.hospital.controller.admin.hospitalCard;

import ua.nure.dudka.hospital.entity.HospitalCard;
import ua.nure.dudka.hospital.service.HospitalCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/hospitalCard")
public class readAllHospitalCards extends HttpServlet {
    private static final HospitalCardService hospitalCardService = new HospitalCardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<HospitalCard> hospitalCards = hospitalCardService.getAllCards();
        session.setAttribute("hospitalCards", hospitalCards);

        req.getRequestDispatcher("/WEB-INF/view/allHospitalCards.jsp").forward(req, resp);
    }
}
