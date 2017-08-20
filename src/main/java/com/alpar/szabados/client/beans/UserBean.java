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

/**
 * Manages all User related CRUD operations
 */
@Component
public class UserBean {
    private static final String ROOT = "http://" + HOST + ":" + PORT + "/user/";

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * Sends user creation request to the server
     * The password is encoded here
     *
     * @param user to be created and added to the database
     * @return server response
     */
    public ClientResponse createUser(User user) {
        return Client.create().resource(ROOT + "createUser/")
                     .type(APPLICATION_JSON)
                     .put(ClientResponse.class, encodePassword(user));
    }

    /**
     * Sends user validation request to the server
     *
     * @param user to be validated
     * @return server response
     */
    public ClientResponse validateUser(User user) {
        return Client.create().resource(ROOT + "validateUser/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, user);
    }

    /**
     * Sends user deletion request to the server
     *
     * @param user to be deleted
     * @return server response
     */
    public ClientResponse deleteUser(User user) {
        return Client.create().resource(ROOT + "deleteUser/")
                     .type(APPLICATION_JSON)
                     .delete(ClientResponse.class, user);
    }

    /**
     * Sends password update request to the server
     * The new password is encoded here
     *
     * @param user with new password
     * @return server response
     */
    public ClientResponse updateUserPassword(User user) {
        return Client.create().resource(ROOT + "updateUserPassword/")
                     .type(APPLICATION_JSON)
                     .post(ClientResponse.class, encodePassword(user));
    }

    //Helper used for encoding
    private User encodePassword(User user) {
        user.setPassword(ENCODER.encode(user.getPassword()));
        return user;
    }
}
