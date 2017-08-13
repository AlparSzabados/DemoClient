package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.connectors.UserBean;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Component
@SessionScoped
@ManagedBean(name = "welcome")
@Join(path = "/hello", to = "/hello.jsf")
public class Welcome {

	public String session() {
		UserBean userBean = new UserBean();
		System.out.println(SessionUtils.getUserName());
	// TODO
		return SessionUtils.getUserName();
	}
}
