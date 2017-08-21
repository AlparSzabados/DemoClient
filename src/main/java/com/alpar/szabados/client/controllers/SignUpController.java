package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.dtos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;

import static com.alpar.szabados.client.handlers.MessageFactory.info;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;

/**
 * Controller managing sign-up
 */
@ManagedBean(name = "signUpController")
@Join(path = "/sign-up", to = "/sign-up.jsf")
public class SignUpController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    /**
     * Checks for empty fields, if any of the fields is empty, an `info` message will pop up.
     * If there are no null values, a new User, containing the username and password, will be sent to the UserBean
     * user creation.
     *
     * @return server response message or redirection to the activities page
     */
    public String create() {
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            info("FIELD CAN'T BE EMPTY");
            return "";
        }

        // If the server returns an OK response, the User's username will be added to the current session
        ClientResponse response = userBean.createUser(this.user);
        if (isOk(response)) {
            getSession().setAttribute("username", this.user.getUserName());
            return "activities.xhtml";
        }
        return handleResponse(response, null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
