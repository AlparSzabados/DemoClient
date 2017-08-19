package com.alpar.szabados.client.handlers;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.Response;

import static com.alpar.szabados.client.handlers.MessageFactory.*;

public class ResponseHandler {

    public static String handleResponse(ClientResponse response) {
        if (isBadRequest(response)) {
            warn("WARN", "WRONG PASSWORD");
        } else if (isServerError(response)) {
            error("ERROR", "USER NOT FOUND");
        }
        return "";
    }

    private static boolean isServerError(ClientResponse response) {
        return response.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }

    private static boolean isBadRequest(ClientResponse response) {
        return response.getStatus() == Response.Status.BAD_REQUEST.getStatusCode();
    }

    public static boolean isOk(ClientResponse response) { // TODO move to helper
        return response.getStatus() == Response.Status.OK.getStatusCode();
    }
}
