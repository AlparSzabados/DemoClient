package com.alpar.szabados.client.controllers;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.User;
import com.alpar.szabados.client.utils.LoadDataUtils;
import org.ocpsoft.rewrite.annotation.Join;

import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.util.List;

import static com.alpar.szabados.client.utils.Utils.getUserName;

@ManagedBean(name = "finishTaskController")
@Join(path = "/activities", to = "/activities.jsf")
public class FinishTaskController {
    private Activity[] userActivities = LoadDataUtils.getUserActivities(new User(getUserName()));
    private List<String> activities = LoadDataUtils.getActivityNames(userActivities);
    private String[] selectedActivities = LoadDataUtils.getFinishedActivities(userActivities);

    public FinishTaskController() throws IOException {
    }

    public String completeTask() throws IOException {
        boolean isOk = new ActivityBean().completeTask(selectedActivities, new User(getUserName()));
        assert isOk; // TODO validate
        return "/activity-history.xhtml?faces-redirect=true";
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String[] getSelectedActivities() {
        return selectedActivities;
    }

    public void setSelectedActivities(String[] selectedActivities) {
        this.selectedActivities = selectedActivities;
    }
}
