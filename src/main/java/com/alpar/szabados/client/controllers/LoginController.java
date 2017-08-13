package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.connectors.UserBean;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@SessionScoped
@ManagedBean(name = "loginController")
@Join(path = "/login", to = "/login.jsf")
public class LoginController {
	private long userId;
	private String userName;
	private String password;

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

	private UserBean userBean = new UserBean();

	public String validateUsernamePassword() throws IOException {
		if (userBean.validateUser(userName, userName)) {
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("username", userName);
			return "/hello.xhtml?faces-redirect=true";
		} else {
			return "/login.xhtml?faces-redirect=true";
		}
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
}
