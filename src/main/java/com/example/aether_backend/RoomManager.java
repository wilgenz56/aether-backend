package com.example.aether_backend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import com.example.aether_backend.Entities.Device;

import org.springframework.stereotype.Component;

@Component
public class RoomManager {
    
    // Il nostro grande "mobile a cassetti" thread-safe
    // Chiave: Indirizzo IP (String)
    // Valore: Lista di dispositivi (List<Device>)
    private final ConcurrentHashMap<String, List<Device>> rooms = new ConcurrentHashMap<>();

    // Metodo che chiameremo quando un WebSocket si connette
    public void addDeviceToRoom(String ipAddress, Device newDevice) {
        
        // 1. Se è il primo dispositivo di questo IP, crea un nuovo "cassetto" (una nuova lista)
        rooms.putIfAbsent(ipAddress, new ArrayList<>());
        
        // 2. Prendi il cassetto di questo IP e inserisci il dispositivo
        rooms.get(ipAddress).add(newDevice);
    }

    // Metodo per rimuovere il dispositivo quando chiude la pagina
    public void removeDevice(String ipAddress, Device deviceToRemove) {
        if (rooms.containsKey(ipAddress)) {
            rooms.get(ipAddress).remove(deviceToRemove);
            
            // Se la stanza è vuota, eliminiamo il cassetto per risparmiare RAM
            if (rooms.get(ipAddress).isEmpty()) {
                rooms.remove(ipAddress);
            }
        }
    }
    
    // Metodo per vedere chi c'è nella stanza
    public List<Device> getDevicesInRoom(String ipAddress) {
        return rooms.getOrDefault(ipAddress, new ArrayList<>());
    }

}
