package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static com.alpar.szabados.client.beans.ActivityBean.isOk;
import static com.alpar.szabados.client.utils.Utils.HOST;
import static com.alpar.szabados.client.utils.Utils.PORT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class UserBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/user/";

    public ClientResponse createUser(User user) throws IOException {
        return Client.create().resource(ROOT + "createUser/")
                                        .type(APPLICATION_JSON)
                                        .put(ClientResponse.class, user);
    }

    public ClientResponse validateUser(User user) throws IOException {
        return Client.create().resource(ROOT + "validateUser/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, user);
    }
}
