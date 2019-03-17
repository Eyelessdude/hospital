package ua.nure.dudka.hospital.controller.admin.hospitalCard;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.controller.UtilServlet;
import ua.nure.dudka.hospital.entity.HospitalCard;
import ua.nure.dudka.hospital.service.HospitalCardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/addHospitalCard")
public class AddHospitalCard extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AddHospitalCard.class);
    private HospitalCardService hospitalCardService = new HospitalCardService();
    private UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/admin/addHospitalCard");
        utilServlet.setRequestListForHospitalCard(req);

        req.getRequestDispatcher("/WEB-INF/view/addNewHospitalCard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HospitalCard hospitalCard = utilServlet.setHospitalCard(req);
        if (!hospitalCardService.insertHospitalCard(hospitalCard)) {
            LOG.error("Can't insert hospital card: " + hospitalCard);
            req.setAttribute("error", "Can't insert hospital card!");
            req.getRequestDispatcher("/WEB-INF/view/errorPage.jsp").forward(req, resp);
            return;
        } else {
            LOG.info("Hospital card was successfully inserted");
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
    }
}
