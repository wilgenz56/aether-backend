package com.example.aether_backend.Controllers;

import com.example.aether_backend.Entities.Device;
import com.example.aether_backend.Entities.SignalMessage;
import com.example.aether_backend.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private RoomManager roomManager;

    // Strumento di Spring per inviare messaggi su canali dinamici (es. canali privati)
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // --- IL MEGAFONO: Quando qualcuno entra o esce dalla stanza ---
    @MessageMapping("/join")
    @SendTo("/topic/room")
    public List<Device> joinRoom(@Payload Device newDevice, SimpMessageHeaderAccessor headerAccessor) {
        String realIp = (String) headerAccessor.getSessionAttributes().get("ip");
        if (realIp == null) realIp = "ip-sconosciuto";

        headerAccessor.getSessionAttributes().put("device", newDevice);
        roomManager.addDeviceToRoom(realIp, newDevice);
        
        return roomManager.getDevicesInRoom(realIp);
    }

    // --- IL CENTRALINO: Smista le chiamate dirette tra due dispositivi (Signaling) ---
    @MessageMapping("/signal")
    public void handleSignaling(@Payload SignalMessage signalMessage) {
        
        // Creiamo un canale privato basato sull'ID del destinatario.
        // Se il targetId è "123", il messaggio andrà solo a chi ascolta su "/topic/peer/123"
        String canalePrivato = "/topic/peer/" + signalMessage.getTargetId();
        
        messagingTemplate.convertAndSend(canalePrivato, signalMessage);
    }
}