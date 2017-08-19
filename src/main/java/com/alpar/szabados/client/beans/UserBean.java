package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.entities.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.alpar.szabados.client.utils.HostConfig.HOST;
import static com.alpar.szabados.client.utils.HostConfig.PORT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
public class UserBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/user/";

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public ClientResponse createUser(User user) throws IOException {
        return Client.create().resource(ROOT + "createUser/")
                     .type(APPLICATION_JSON)
                     .put(ClientResponse.class, encodePassword(user));
    }

    public ClientResponse validateUser(User user) throws IOException {
        return Client.create().resource(ROOT + "validateUser/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, user);
    }

    private User encodePassword(User user) {
        user.setPassword(ENCODER.encode(user.getPassword()));
        return user;
    }
}
