package com.m3alem.m3alem_back_end.websockets;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("app.websocket")
public class WebSocketProperties {
    /**
     * Prefix used for WebSocket destination mappings
     */
    private String[] applicationPrefix = {"/course" };
    /**
     * Prefix used by topics
     */
    private String subscribtionPrefix = "/course";
    /**
     * Endpoint that can be used to connect to
     */
    private String endpoint = "/ws";
    /**
     * Allowed origins
     */
    private String[] allowedOrigins = new String[0];
    private String userDestinationPrefix = "/user";

}