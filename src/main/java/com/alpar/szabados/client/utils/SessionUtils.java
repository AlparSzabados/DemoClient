package com.alpar.szabados.client.utils;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Helper class for session management
 */
public class SessionUtils {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static String getSessionUserName() {
        return getSession().getAttribute("username").toString();
    }
}
