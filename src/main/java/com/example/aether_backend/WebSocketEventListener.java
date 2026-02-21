package com.example.aether_backend; 

import com.example.aether_backend.Entities.Device;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final RoomManager roomManager;
    private final SimpMessagingTemplate messagingTemplate;

    // Spring inietta questi componenti in automatico
    public WebSocketEventListener(RoomManager roomManager, SimpMessagingTemplate messagingTemplate) {
        this.roomManager = roomManager;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Recuperiamo i dati dalla sessione interrotta
        String realIp = (String) headerAccessor.getSessionAttributes().get("ip");
        Device device = (Device) headerAccessor.getSessionAttributes().get("device");

        if (realIp != null && device != null) {
            System.out.println("Dispositivo DISCONNESSO: " + device.getNome());
            
            // Lo rimuoviamo dalla stanza
            roomManager.removeDevice(realIp, device);
            
            // Inviamo a tutti quelli rimasti in quella stanza la nuova lista aggiornata (senza di lui!)
            messagingTemplate.convertAndSend("/topic/room", roomManager.getDevicesInRoom(realIp));
        }
    }
}