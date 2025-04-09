package com.teletrader.stockexchange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //Registracija endpoint-a koji ce klijent koristiti za povezivanje na WebSocket
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Konfiguracija message broker-a koji ce omoguciti routing poruka
        registry.enableSimpleBroker("/topic"); //topic koji se koristi za slanje poruka klijentima
        registry.setApplicationDestinationPrefixes("/app"); // Prefix za poruke koje šalju klijenti
    }

    }

