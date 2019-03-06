package ua.nure.dudka.hospital.controller.filter;

import org.apache.log4j.Logger;
import ua.nure.dudka.hospital.constants.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AdminFilter.class);
    private static final String ADMIN_ROLE = Role.ADMIN.getName();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();

        String clientRole = String.valueOf(session.getAttribute("role"));

        LOG.info("Client role: " + clientRole);

        if (clientRole.equals(ADMIN_ROLE)) {
            filterChain.doFilter(req, servletResponse);
        } else {
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            resp.sendRedirect(servletRequest.getServletContext().getContextPath() + "/logout");
        }
    }
}
