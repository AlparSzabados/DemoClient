package com.alpar.szabados.client.handlers;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.Response;

import static com.alpar.szabados.client.handlers.MessageFactory.*;

public class ResponseHandler {

    public static String handleResponse(ClientResponse response, String isOkDetail) {
        if (isOk(response)) {
            info(isOkDetail);
        } else if (isBadRequest(response)) {
            error(response.getEntity(String.class));
        } else if (isUnauthorized(response)) {
            warn(response.getEntity(String.class));
        } else {
            fatal(response.getEntity(String.class));
        }
        return "";
    }

    public static boolean isOk(ClientResponse response) { // TODO move to helper
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }

    private static boolean isBadRequest(ClientResponse response) {
        return response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode();
    }

    private static boolean isUnauthorized(ClientResponse response) {
        return response.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode();
    }
}
