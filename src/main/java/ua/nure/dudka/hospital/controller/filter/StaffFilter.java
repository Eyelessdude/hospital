package ua.nure.dudka.hospital.controller.filter;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "StaffFilter", urlPatterns = "/staff/*")
public class StaffFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(StaffFilter.class);
    private static final String DOCTOR_ROLE = Role.DOCTOR.getName();
    private static final String NURSE_ROLE = Role.NURSE.getName();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();

        String clientRole = String.valueOf(session.getAttribute("role"));

        LOG.info("Client role:" + clientRole);

        if (clientRole.equals(DOCTOR_ROLE) || clientRole.equals(NURSE_ROLE)) {
            filterChain.doFilter(req, servletResponse);
        } else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(servletRequest.getServletContext().getContextPath() + "/logout");
        }
    }
}
