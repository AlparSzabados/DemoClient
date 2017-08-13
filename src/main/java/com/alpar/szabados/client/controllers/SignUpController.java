package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.connectors.UserBean;
import com.alpar.szabados.client.entities.User;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;

@Component
@SessionScoped
@ManagedBean(name = "signUpController")
@Join(path = "/signup", to = "/signup.jsf")
public class SignUpController {
	private User user = new User();

	public String create() throws IOException {
		UserBean userBean = new UserBean();
		userBean.createUser(user.getUserName(), user.getPassword());
		return "/hello.xhtml?faces-redirect=true";
	}

	public User getUser() {
		return user;
	}
}
