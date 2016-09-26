package se.lexicon.jomian.filter;

import se.lexicon.jomian.controller.LoginController;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Only allow users with admin rights to access admin pages.
 *
 * @author Johan Gustafsson
 * @since 2016-09-08.
 */
@WebFilter(urlPatterns = {"/admin/*"})
public class AdminAccessFilter implements Filter {
    @Inject
    LoginController loginController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!loginController.isLoggedIn() || loginController.getLoggedInAccount() == null || !loginController.getLoggedInAccount().isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/WEB-INF/errorpage/404.xhtml");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
