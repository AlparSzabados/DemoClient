package com.alpar.szabados.client.utils;

public class HostConfig {
    public static final String HOST = System.getProperty("server.host", "localhost");
    public static final String PORT = System.getProperty("server.port", "8090");
}
