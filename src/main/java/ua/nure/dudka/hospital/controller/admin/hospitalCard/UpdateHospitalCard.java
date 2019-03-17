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

@WebServlet("/admin/updateHospitalCard")
public class UpdateHospitalCard extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(UpdateHospitalCard.class);
    private HospitalCardService hospitalCardService = new HospitalCardService();
    private UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/admin/updateHospitalCard");
        int id = Integer.parseInt(req.getParameter("id"));
        HospitalCard hospitalCard = hospitalCardService.findCardById(id);
        if (hospitalCard == null) {
            LOG.error("Cant' find hospital card with id: " + id);
            req.setAttribute("error", "Can't find hospital card!");
            req.getRequestDispatcher("/WEB-INF/view/errorPage.jsp").forward(req, resp);
            return;
        }
        req.setAttribute("hospitalCard", hospitalCard);
        utilServlet.setRequestListForHospitalCard(req);

        req.getRequestDispatcher("/WEB-INF/view/addNewHospitalCard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HospitalCard hospitalCard = utilServlet.setHospitalCard(req);
        if (!hospitalCardService.updateHospitalCard(hospitalCard)) {
            LOG.error("Can't update hospital card: " + hospitalCard);
            req.setAttribute("error", "Can't update hospital card!");
            req.getRequestDispatcher("/WEB-INF/view/errorPage.jsp").forward(req, resp);
            return;
        } else {
            LOG.info("Hospital card was successfully updated");
        }

        resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
    }
}
