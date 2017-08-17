package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean(name = "signUpController")
@Join(path = "/sign-up", to = "/sign-up.jsf")
public class SignUpController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String create() throws IOException {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", user.getUserName());
        if (userBean.createUser(user.getUserName(), user.getPassword())) {
            return "/add-activity.xhtml?faces-redirect=true";
        } else {
            return "/sign-up.xhtml?faces-redirect=true";
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
