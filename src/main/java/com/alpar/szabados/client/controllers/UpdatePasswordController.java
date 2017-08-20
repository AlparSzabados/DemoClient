package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.pojos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.Objects;

import static com.alpar.szabados.client.handlers.MessageFactory.error;
import static com.alpar.szabados.client.handlers.ResponseHandler.handleResponse;
import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static com.alpar.szabados.client.utils.SessionUtils.getSessionUserName;

/**
 * Controller managing password change
 */
@ManagedBean(name = "updatePasswordController")
@Join(path = "/update-password", to = "/update-password.jsf")
public class UpdatePasswordController {
    private String oldPassword;
    private String newPassword;

    private UserBean userBean = new UserBean();

    /**
     * Checks for empty fields, old password is valid, old password != new password, error messages will be returned
     * if any of these restrictions are not met
     * In case of successful validations, and password update a confirmation message will be returned
     *
     * @return server response message
     */
    public String update() {
        User user = new User(getSessionUserName(), oldPassword);

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            error("FIELD CAN'T BE EMPTY"); return "";
        } else if (Objects.equals(oldPassword, newPassword)) {
            error("NEW PASSWORD MUST BE DIFFERENT"); return "";
        } else if (isOk(userBean.validateUser(user))) {
            ClientResponse response = userBean.updateUserPassword(new User(getSessionUserName(), newPassword));
            return validate(response);
        } else {
            error("OLD PASSWORD MISMATCH"); return "";
        }
    }

    private String validate(ClientResponse response) {
        return handleResponse(response, "PASSWORD UPDATED SUCCESSFULLY");
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}