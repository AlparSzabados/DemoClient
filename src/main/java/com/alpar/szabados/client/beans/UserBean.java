package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import static com.alpar.szabados.client.beans.ActivityBean.isOk;

public class UserBean {
    private static final String SERVER_PATH = "http://localhost:8090/user/";

    public boolean createUser(User user) throws IOException {
        String path = SERVER_PATH + "createUser/";
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type("application/json").put(ClientResponse.class, user);
        return isOk(response);
    }

    public boolean validateUser(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(user);
        String path = SERVER_PATH + "validateUser/";
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, jsonInString);
        return isOk(response);
    }
}
