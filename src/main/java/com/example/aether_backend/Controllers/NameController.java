//classe del contoller che gestisce tutte le richieste http
package com.example.aether_backend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aether_backend.Entities.Nickname;
import com.example.aether_backend.Repositories.NicknameRepository;

import ch.qos.logback.classic.Logger;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "*") // Consente le richieste da localhost:5173
public class NameController {

    @Autowired
    NicknameRepository nicknameRepository;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World sucuni!";
    }

    @GetMapping("/get-name")
    public String getRandomName() {
        
        // 1. Peschiamo un record intero libero
        Nickname recordLibero = nicknameRepository.trovaNomeLiberoCasuale();

        System.err.println("Record libero trovato: " + recordLibero);
        
        if (recordLibero != null) {
            // 2. Cambiamo lo stato e salviamo (SQLite farà un UPDATE automatico)
            recordLibero.setUsato(true);
            nicknameRepository.save(recordLibero);
            
            // 3. Restituiamo a React solo il testo
            return recordLibero.getNome();
        }

        // Fallback nel caso rarissimo in cui tutti i nomi del DB siano occupati
        return "Ospite " + (int)(Math.random() * 1000);
    }
    
    
}
