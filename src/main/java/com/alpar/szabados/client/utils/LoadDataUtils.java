package com.alpar.szabados.client.utils;

import com.alpar.szabados.client.beans.ActivityBean;
import com.alpar.szabados.client.pojos.Activity;
import com.alpar.szabados.client.pojos.TaskStatus;
import com.alpar.szabados.client.pojos.User;
import com.sun.jersey.api.client.ClientResponse;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.alpar.szabados.client.handlers.ResponseHandler.isOk;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

public class LoadDataUtils {

    /**
     * @param userActivities received from the server
     * @return list of unique activity names
     */
    public static List<String> getActivityNames(Activity[] userActivities) {
        return Arrays.stream(userActivities)
                     .map(Activity::getActivityName)
                     .distinct()
                     .collect(Collectors.toList());
    }

    /**
     * @param userActivities received from the server
     * @return array of finished activities that have the current date
     */
    public static String[] getFinishedActivities(Activity[] userActivities) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);

        return Arrays.stream(userActivities)
                     .filter(activity -> Objects.equals(activity.getActivityDate(), now))
                     .filter(activity -> activity.getTaskStatus() == TaskStatus.COMPLETED)
                     .map(Activity::getActivityName).map(String::toString).toArray(String[]::new);
    }
}
