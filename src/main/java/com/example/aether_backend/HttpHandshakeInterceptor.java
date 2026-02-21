package com.example.aether_backend;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        
        // Catturiamo l'indirizzo IP reale dalla richiesta HTTP iniziale
        String ipAddress = request.getRemoteAddress().getAddress().getHostAddress();
        
        // Se stai testando in locale, l'IP IPv6 di localhost è 0:0:0:0:0:0:0:1. Lo convertiamo per comodità.
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            ipAddress = "127.0.0.1";
        }
        
        // Salviamo l'IP negli "attributi" della sessione WebSocket, così il Controller potrà leggerlo
        attributes.put("ip", ipAddress);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, 
                               WebSocketHandler wsHandler, Exception exception) {
        // Non ci serve fare nulla dopo l'handshake
    }
}