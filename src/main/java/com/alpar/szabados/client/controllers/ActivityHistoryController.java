package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.utils.SessionUtils;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SessionScoped
@ManagedBean(name = "history")
@Join(path = "/history", to = "/activity-history.jsf")
public class ActivityHistoryController {
	private List<Activity> activities;

	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() throws IOException {
		ActivityBean activityBean = new ActivityBean();
		Activity[] userActivities = activityBean.findUserActivities(SessionUtils.getUserName());
		activities = Arrays.stream(userActivities).collect(Collectors.toList());
	}

	public List<Activity> getActivities() {
		return activities;
	}
}
