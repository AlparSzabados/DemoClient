package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

import static com.alpar.szabados.client.utils.Utils.getSession;

@ManagedBean(name = "loginController")
@Join(path = "/", to = "/login.jsf")
public class LoginController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String validateUsernamePassword() throws IOException {
        if (userBean.validateUser(user)) {
            getSession().setAttribute("username", user.getUserName());
            return "/add-activity.xhtml?faces-redirect=true";
        } else {
            return "/login.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        getSession().invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
