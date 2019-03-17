package ua.nure.dudka.hospital.controller.admin.doctor;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.controller.UtilServlet;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/addClient")
public class AddClient extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AddClient.class);
    private ClientService clientService = new ClientService();
    private UtilServlet utilServlet = new UtilServlet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/admin/addClient");
        utilServlet.setRequestListForClient(req);

        req.getRequestDispatcher("/WEB-INF/view/addNewClient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = utilServlet.setClient(req);
        if (!clientService.insertClient(client)) {
            req.setAttribute("error", "Can't insert user! User may already be registered!");
            LOG.error("Can't insert client: " + client);
            req.getRequestDispatcher("/WEB-INF/view/errorPage.jsp").forward(req, resp);
            return;
        } else {
            LOG.info("Client was successfully inserted");

        }
        resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
    }
}
