package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.UserBean;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@SessionScoped
@ManagedBean(name = "signUpController")
@Join(path = "/sign-up", to = "/sign-up.jsf")
public class SignUpController {
    private long userId;
    private String userName;
    private String password;
    private UserBean userBean = new UserBean();

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String create() throws IOException {
        UserBean userBean = new UserBean();
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", userName);
        if (userBean.createUser(userName, password)) {
            return "/add-activity.xhtml?faces-redirect=true";
        } else {
            return "/sign-up.xhtml?faces-redirect=true";
        }
    }
}
