package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Objects;

import static com.alpar.szabados.client.handlers.MessageFactory.error;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

@ManagedBean(name = "deleteUserController")
@Join(path = "/delete-profile", to = "/delete-user.jsf")
public class DeleteUserController {
    private User user = new User();
    private UserBean userBean = new UserBean();
    private ActivityBean activityBean = new ActivityBean();

    public String delete() throws IOException {
        if (Objects.equals(user.getUserName(), getSessionUserName())) {
            ClientResponse response = userBean.validateUser(user);
            return validate(response);
        } else {
            error("INVALID USERNAME"); return "";
        }
    }

    private String validate(ClientResponse response) throws IOException {
        if (isOk(response)) {
            activityBean.deleteActivities(user);
            if (isOk(userBean.deleteUser(user))) {
                getSession().invalidate(); return "login.xhtml";
            }
            error("SOMETHING WENT WRONG"); return "";
        } else {
            return handleResponse(response, null);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

