package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;

import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.Utils.getSession;

@SessionScoped
@ManagedBean(name = "loginController")
@Join(path = "/", to = "/login.jsf")
public class LoginController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String validateUsernamePassword() throws IOException {
        ClientResponse response = userBean.validateUser(user);
        if (isOk(response)) {
            getSession().setAttribute("username", user.getUserName());
            return "activities.xhtml";
        }
        return handleResponse(response, null);
    }

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
