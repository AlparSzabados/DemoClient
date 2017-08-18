package com.alpar.szabados.client.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class Utils {
    public static final String HOST = System.getProperty("server.host", "localhost");
    public static final String PORT = System.getProperty("server.port", "8090");

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static String getUserName() {
        return getSession().getAttribute("username").toString();
    }
}
