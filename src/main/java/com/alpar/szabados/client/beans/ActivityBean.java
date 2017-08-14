package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.Activity;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import java.io.IOException;
import java.util.Arrays;

import static com.alpar.szabados.client.entities.TaskStatus.NOT_COMPLETED;
import static org.codehaus.jackson.map.SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS;

public class ActivityBean {
	// Path to REST Service URI
	private static final String SERVER_PATH = "http://localhost:8090/activity/";

	public boolean findUserActivities(String userName) throws IOException {
		String path = SERVER_PATH + "findActivities/" + userName;
		WebResource webResource = Client.create().resource(path);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class);
		if (response.getStatus() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			JSONArray myObject = response.getEntity(JSONArray.class);
			mapper.configure(FAIL_ON_EMPTY_BEANS, false);

			//TODO refactor
			Activity[] activityResponse = mapper.readValue(myObject.toString(), Activity[].class);

			System.out.println(Arrays.toString(activityResponse));
			return true;
		}
		return false;
	}

	public boolean createActivity(String activityName, String userName) throws IOException {
		String path = SERVER_PATH + "createActivity/" + activityName + "." + NOT_COMPLETED + "." + userName;
		WebResource webResource = Client.create().resource(path);
		ClientResponse response = webResource.type("application/json").put(ClientResponse.class);
		return response.getStatus() == 200;
	}
}


