package com.m3alem.m3alem_back_end.websockets;

import lombok.AllArgsConstructor;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@ConfigurationProperties
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(WebSocketProperties.class)
@AllArgsConstructor
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    private WebSocketProperties properties;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         * Les prefix que le server utilise pour envoyer un message => les client
         * peuvent ecouter ces canaux (souscrire)
         */
        registry.enableSimpleBroker(properties.getSubscribtionPrefix());

        /*
         * Le prefix qu'un utilisateur doit utiliser pour envoyer un message
         */
        registry.setApplicationDestinationPrefixes(properties.getApplicationPrefix());
        /// registry.setApplicationDestinationPrefixes(properties.getDriverWaitPrefix());

        // Pour permettre d'envoyer une message specifique
        registry.setUserDestinationPrefix(properties.getUserDestinationPrefix());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /*
         * Le mot clé situé après le url pour se connecter au server
         */
        registry.addEndpoint(properties.getEndpoint());
        // registry.addEndpoint(properties.getEndpoint()).setAllowedOrigins(properties.getAllowedOrigins()).withSockJS();
    }

  /*  @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // TODO Auto-generated method stub
       // WebSocketMessageBrokerConfigurer.super.configureClientInboundChannel(registration);
       registration.setInterceptors(new UserInterceptor());
    }

    public class UserInterceptor extends ChannelInterceptorAdapter {

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
    
            StompHeaderAccessor accessor =
                    MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    
            if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                Object raw = message
                        .getHeaders()
                        .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
    
                if (raw instanceof Map) {
                    Object name = ((Map) raw).get("name");
    
                    if (name instanceof LinkedList) {
                        accessor.setUser(new User(((LinkedList) name).get(0).toString()));
                    }
                }
            }
            return message;
        }
    }*/
}