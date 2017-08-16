package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.alpar.szabados.client.utils.SessionUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import java.io.IOException;

import static com.alpar.szabados.client.entities.TaskStatus.NOT_COMPLETED;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

// TODO make it work for spaces also
public class ActivityBean {
	// Path to REST Service URI
	private static final String SERVER_PATH = "http://localhost:8090/activity/";

	public Activity[] findUserActivities(String userName) throws IOException {
		String path = SERVER_PATH + "findActivities/" + userName;
		WebResource webResource = Client.create().resource(path);
		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
		if (response.getStatus() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			JSONArray myObject = response.getEntity(JSONArray.class);
			mapper.configure(FAIL_ON_EMPTY_BEANS, false);
			//TODO refactor
			return mapper.readValue(myObject.toString(), Activity[].class);
		}
		return new Activity[0];
	}

	public boolean createActivity(String activityName, String userName) throws IOException {
		String path = SERVER_PATH + "createActivity/" + activityName + "." + NOT_COMPLETED + "." + userName;
		WebResource webResource = Client.create().resource(path);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class);
		return response.getStatus() == 200;
	}


	public boolean completeTask(String[] selectedActivities) {
		int responseStatus = 0;
		for (String selectedActivity : selectedActivities) {
			String path = SERVER_PATH + "completeTask/" + selectedActivity + "." + SessionUtils.getUserName();
			WebResource webResource = Client.create().resource(path);
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class);
			responseStatus = response.getStatus();
		}
		return responseStatus == 200;
	}
}


