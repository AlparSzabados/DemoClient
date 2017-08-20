package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.handlers.MessageFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;

@ManagedBean(name = "signUpController")
@Join(path = "/sign-up", to = "/sign-up.jsf")
public class SignUpController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String create() throws IOException {
        if (user.getUserName().isEmpty() || user.getPassword().isEmpty()) {
            MessageFactory.info("FIELD CAN'T BE EMPTY");
            return "";
        }

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
