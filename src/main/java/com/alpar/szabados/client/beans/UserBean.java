package com.alpar.szabados.client.beans;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.alpar.szabados.client.beans.ActivityBean.isOk;

// TODO make it work for spaces also
public class UserBean {
    private static final String SERVER_PATH = "http://localhost:8090/user/";

    public boolean createUser(String userName, String password) throws IOException {
        String path = SERVER_PATH + "createUser/" + userName + "." + password;
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type("application/json").put(ClientResponse.class);
        return isOk(response);
    }

    public boolean validateUser(String userName, String password) throws IOException {
        String path = SERVER_PATH + "validateUser/" + userName + "." + password;
        WebResource webResource = Client.create().resource(path);
        ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
        return isOk(response);
    }
}
