package com.alpar.szabados.client.beans;

import com.alpar.szabados.client.pojos.User;
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

    public ClientResponse deleteUser(User user) throws IOException {
        return Client.create().resource(ROOT + "deleteUser/")
                     .type(APPLICATION_JSON)
                     .delete(ClientResponse.class, user);
    }

    public ClientResponse updateUserPassword(User user) throws IOException {
        return Client.create().resource(ROOT + "updateUserPassword/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, encodePassword(user));
    }

    private User encodePassword(User user) {
        user.setPassword(ENCODER.encode(user.getPassword()));
        return user;
    }
}
