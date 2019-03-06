package ua.nure.dudka.hospital.controller;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(LogOutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    LOG.info("JSESSIONID: " + cookie.getValue());
                    break;
                }
            }
        }

        HttpSession session = req.getSession(false);
        LOG.info("Client = " + session.getAttribute("login"));
        if (session != null) {
            session.invalidate();
        }
        System.out.println(getServletContext().getContextPath());
        resp.sendRedirect(getServletContext().getContextPath() + "/login");
    }
}
