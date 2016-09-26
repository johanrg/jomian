package se.lexicon.jomian.filter;

import se.lexicon.jomian.controller.LoginController;

import javax.inject.Inject;
import javax.security.auth.login.LoginContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Only allow logged in users to access the restricted area.
 *
 * @author Johan Gustafsson
 * @since 2016-09-08.
 */
@WebFilter(urlPatterns = {"/restricted/*"})
public class RestrictedAccessFilter implements Filter {
    @Inject
    LoginController loginController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!loginController.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() + "/login.xhtml");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
