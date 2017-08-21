package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.dtos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;

import static com.alpar.szabados.client.handlers.MessageFactory.*;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;

/**
 * Controller managing login and logout
 */
@ManagedBean(name = "loginController")
@Join(path = "/", to = "/login.jsf")
public class LoginController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    /**
     * Checks for empty fields, if any of the fields is empty, an `info` message will pop up.
     * If there are no null values, sends the current user to the UserBean for validation
     * If the user is valid, it will be redirected to the activities page, if not, a response message will pop up
     * containing information about the validation
     *
     * @return server response message or redirection to the activities page
     */
    public String validateUsernamePassword() {
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            info("FIELD CAN'T BE EMPTY"); return "";
        }

        // If the server returns an OK response, the User's username will be added to the current session
        ClientResponse response = userBean.validateUser(user);
        if (isOk(response)) {
            getSession().setAttribute("username", user.getUserName());
            return "activities.xhtml";
        }
        return handleResponse(response, null);
    }

    /**
     * Invalidates the current session
     *
     * @return the user to the login page
     */
    public String logout() {
        getSession().invalidate();
        return "login.xhtml";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
