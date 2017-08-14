package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;

@Component
@SessionScoped
@ManagedBean(name = "addActivity")
@Join(path = "/addactivity", to = "/add-activity.jsf")
public class AddActivityController {
	private String activityName;

	public String createActivity() throws IOException {
		ActivityBean activityBean = new ActivityBean();
		activityBean.createActivity(activityName, SessionUtils.getUserName());
		return ""; //TODO redirect accordingly
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
}
