package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.handlers.MessageFactory;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Objects;

import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

@ManagedBean(name = "deleteUserController")
@Join(path = "/delete-profile", to = "/delete-user.jsf")
public class DeleteUser {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String delete() throws IOException {
        if (isMatching()) {
            ClientResponse response = userBean.deleteUser(user);
            return validate(response);
        } else {
            MessageFactory.error("INVALID USERNAME");
            return "";
        }
    }

    private String validate(ClientResponse response) {
        if (isOk(response)) {
            getSession().invalidate();
            return "login.xhtml";
        } else {
            return handleResponse(response, null);
        }
    }

    private boolean isMatching() {
        return Objects.equals(user.getUserName(), getSessionUserName());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

