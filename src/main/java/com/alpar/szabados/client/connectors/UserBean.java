package com.alpar.szabados.client.connectors;

import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

public class UserBean {
	private static final String SERVER_PATH = "http://localhost:8090/user/";

	public String createUser(String userName, String password) throws IOException {

		String path = SERVER_PATH + "createUser/" + userName + "." + password;

		Client client = Client.create();

		WebResource webResource = client.resource(path);

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);

		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = mapper.writeValueAsString(user);

		ClientResponse response = webResource.type("application/json").put(ClientResponse.class, jsonInString);
		if (response.getStatus() == 200) {
			JSONObject myObject = response.getEntity(JSONObject.class);
			mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);

			User returnedUser = mapper.readValue(myObject.toString(), User.class);

			System.out.println(" Name:" + returnedUser.getUserName() + " Password: " + returnedUser.getPassword());
			return "success";
		}

		return "success";
	}

	public boolean validateUser(String userName, String password) throws IOException {

		String path = SERVER_PATH + "validateUser/" + userName + "." + password;

		Client client = Client.create();

		WebResource webResource = client.resource(path);

		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);

		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = mapper.writeValueAsString(user);

		ClientResponse response = webResource.type("application/json").put(ClientResponse.class, jsonInString);
		if (response.toString() != null) {
			JSONObject myObject = response.getEntity(JSONObject.class);
			mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);

			User returnedUser = mapper.readValue(myObject.toString(), User.class);

			System.out.println(" Name:" + returnedUser.getUserName() + " Password: " + returnedUser.getPassword());
			return true;
		}

		return false;
	}

	public boolean findUserByName(String userName) throws IOException {

		String path = SERVER_PATH + "findUserByName/" + userName;

		Client client = Client.create();

		WebResource webResource = client.resource(path);

		User user = new User();
		user.setUserName(userName);

		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = mapper.writeValueAsString(user);

		ClientResponse response = webResource.type("application/json").put(ClientResponse.class, jsonInString);
		if (response.toString() != null) {
			JSONObject myObject = response.getEntity(JSONObject.class);
			mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);

			User returnedUser = mapper.readValue(myObject.toString(), User.class);

			System.out.println(" Name:" + returnedUser.getUserName() + " Password: " + returnedUser.getPassword());
			return true;
		}

		return false;
	}
}
