package com.alpar.szabados.client.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO check if is necessary
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {
    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (xxx(ses, reqURI)) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml?faces-redirect=true");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean xxx(HttpSession ses, String reqURI) { // TODO rename
        return reqURI.contains("/login.xhtml")
                || (ses != null && ses.getAttribute("username") != null)
                || reqURI.contains("/public/")
                || reqURI.contains("javax.faces.resource");
    }

    @Override
    public void destroy() {
    }
}
