package ua.nure.dudka.hospital.controller;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.entity.Client;
import ua.nure.dudka.hospital.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);
    private ClientService clientService = new ClientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Client client = clientService.getClientByLoginAndPassword(login, password);

        if (client == null) {
            LOG.error("Can't find user with such combination of login and password. Login: " + login);
            req.setAttribute("error", "Can't find user with such login and password!");
            req.getRequestDispatcher("/WEB-INF/view/errorPage.jsp").forward(req, resp);
            return;
        } else {
            LOG.info("Client: " + login + " successfully logged in");
        }

        HttpSession session = req.getSession();
        String role = client.getRole().getName();

        session.setAttribute("client_id", client.getId());
        session.setAttribute("role", role);
        session.setAttribute("login", client.getLogin());

        session.setMaxInactiveInterval(15 * 60);

        if (role.equals("admin")) {
            resp.sendRedirect(getServletContext().getContextPath() + "/admin/page");
        } else if (role.equals("doctor") || role.equals("nurse")) {
            resp.sendRedirect(getServletContext().getContextPath() + "/staff/patients");
        } else if (role.equals("patient")) {
            resp.sendRedirect(getServletContext().getContextPath() + "/client/page");
        }
    }
}
