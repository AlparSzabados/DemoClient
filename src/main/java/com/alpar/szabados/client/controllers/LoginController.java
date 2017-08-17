package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean(name = "loginController")
@Join(path = "/", to = "/login.jsf")
public class LoginController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String validateUsernamePassword() throws IOException {
        if (userBean.validateUser(user.getUserName(), user.getPassword())) {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user.getUserName());
            return "/add-activity.xhtml?faces-redirect=true";
        } else {
            return "/login.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/login.xhtml?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
