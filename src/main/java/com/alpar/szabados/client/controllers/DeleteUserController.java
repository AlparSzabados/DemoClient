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
            ClientResponse response = userBean.validateUser(user);
            return validate(response);
        } else {
            error("INVALID USERNAME");
            return "";
        }
    }

    /**
     * If the User is a valid user, a request will be sent to the ActivityBean to delete all Activities.
     * The User deletion request is sent second. If it returns an OK response, the session is invalidated and
     * the user is redirected to the login page.
     *
     * @param response from the user validation
     * @return the response from the server or redirection to the login page.
     */
    private String validate(ClientResponse response) {
        if (isOk(response)) {
            activityBean.deleteActivities(user);
            if (isOk(userBean.deleteUser(user))) {
                getSession().invalidate();
                return "login.xhtml";
            }
            error("SOMETHING WENT WRONG");
            return "";
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

