package com.example.aether_backend; // Controlla che il package sia giusto

import com.example.aether_backend.Entities.Nickname;
import com.example.aether_backend.Repositories.NicknameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataSeeder implements CommandLineRunner {

    private final NicknameRepository nicknameRepository;

    public DataSeeder(NicknameRepository nicknameRepository) {
        this.nicknameRepository = nicknameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // Se la tabella è vuota, partiamo con l'importazione dal file testuale
        if (nicknameRepository.count() == 0) {
            System.out.println("⏳ Database vuoto. Inizio l'importazione da nomi.txt...");

            // Cerca il file "nomi.txt" nella cartella src/main/resources
            ClassPathResource resource = new ClassPathResource("Clean-Surnames.txt");
            
            // Leggiamo il file riga per riga (UTF-8 evita problemi con gli accenti)
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                
                String linea;
                int contatore = 0;
                
                while ((linea = reader.readLine()) != null) {
                    String nomePulito = linea.trim(); // Toglie gli spazi vuoti extra
                    
                    // Se la riga non è vuota, salvala nel database
                    if (!nomePulito.isEmpty()) {
                        Nickname nuovoNome = new Nickname();
                        nuovoNome.setNome(nomePulito);
                        nuovoNome.setUsato(false);
                        nicknameRepository.save(nuovoNome);
                        contatore++;
                    }
                }
                System.out.println("✅ Importazione completata! Aggiunti " + contatore + " nomi al database.");
            } catch (Exception e) {
                System.err.println("❌ Errore durante la lettura del file: " + e.getMessage());
            }
        } else {
            System.out.println("✅ Il database contiene già " + nicknameRepository.count() + " nomi. Salto l'importazione.");
        }
    }
}