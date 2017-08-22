package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.dtos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.util.Objects;

import static com.alpar.szabados.client.handlers.MessageFactory.error;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSession;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

/**
 * Controller for deleting user profiles
 */
@ManagedBean(name = "deleteUserController")
@Join(path = "/delete-profile", to = "/delete-user.jsf")
public class DeleteUserController {
    private User user = new User();
    private UserBean userBean = new UserBean();
    private ActivityBean activityBean = new ActivityBean();

    /**
     * Validates the current User before sending the delete request to the server
     *
     * @return the response from the server
     */
    public String delete() {
        if (Objects.equals(user.getUserName(), getSessionUserName())) {
            ClientResponse deleteActivityResponse = activityBean.deleteActivities(user);
            ClientResponse deleteUserResponse = userBean.deleteUser(user);

            if (isOk(deleteActivityResponse) && isOk(deleteUserResponse)) {
                getSession().invalidate();
                return "login.xhtml";
            }
            return handleResponse(deleteActivityResponse, null);
        }
        error("INVALID USERNAME");
        return "";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

