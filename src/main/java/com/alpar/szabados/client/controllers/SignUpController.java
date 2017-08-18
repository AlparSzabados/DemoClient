package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;

import static com.alpar.szabados.client.utils.Utils.getSession;

@ManagedBean(name = "signUpController")
@Join(path = "/sign-up", to = "/sign-up.jsf")
public class SignUpController {
    private User user = new User();
    private UserBean userBean = new UserBean();

    public String create() throws IOException {
        getSession().setAttribute("username", user.getUserName());
        if (userBean.createUser(user)) {
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
