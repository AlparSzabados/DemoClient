package com.alpar.szabados.client.utils;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.entities.TaskStatus;
import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

public class LoadDataUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().configure(FAIL_ON_EMPTY_BEANS, false);

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
        ClientResponse response = new ActivityBean().findUserActivities(user);
        return OBJECT_MAPPER.readValue(response.getEntityInputStream(), Activity[].class);
    }
}
