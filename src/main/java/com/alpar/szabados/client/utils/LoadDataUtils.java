package com.alpar.szabados.client.utils;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.entities.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LoadDataUtils {
    public static List<String> getActivityNames(Activity[] userActivities) {
        return Arrays.stream(userActivities)
                     .map(Activity::getActivityName)
                     .distinct()
                     .collect(Collectors.toList());
    }

    public static String[] getFinishedActivities(Activity[] userActivities) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);

        return Arrays.stream(userActivities)
                     .filter(activity -> Objects.equals(activity.getActivityDate(), now))
                     .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                     .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);
    }

    public static Activity[] getUserActivities(User user) throws IOException {
        return new ActivityBean().findUserActivities(user);
    }
}
