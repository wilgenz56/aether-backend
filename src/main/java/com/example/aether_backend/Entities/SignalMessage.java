package com.example.aether_backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignalMessage {
    private String senderId;   // Chi invia il bigliettino
    private String targetId;   // A chi è destinato
    private String type;       // Tipo di bigliettino ("offer", "answer", o "candidate")
    private Object data;       // Il contenuto tecnico della connessione
}