package com.alpar.szabados.client.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtils {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static String getSessionUserName() {
        return getSession().getAttribute("username").toString();
    }
}
